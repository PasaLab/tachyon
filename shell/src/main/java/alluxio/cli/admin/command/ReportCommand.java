/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.cli.admin.command;

import alluxio.Configuration;
import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.cli.AbstractCommand;
import alluxio.wire.AlluxioMasterInfo;
import alluxio.wire.Capacity;
import alluxio.wire.WorkerInfo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/** The command report cluster and nodes information.*/
public class ReportCommand extends AbstractCommand {

  private static final Logger LOG = LoggerFactory.getLogger(ReportCommand.class);
  private String  mHostname;
  private int mPort;
  private AlluxioMasterInfo mInfo;

  private static final Map<String, String> NO_PARAMS = new HashMap<>();

  public static final String GET_INFO = "info";
  public static final String GET_WORKER_TIER_INFO = "worker_tier_info";
  public static final String GET_LOST_BLOCKS = "lost_blocks_number";
  public static final String SERVICE_PREFIX = "master";

  public static final String QUERY_WORKER_ID = "worker_id";

  /**
   * Constructor of class ReportCommand.
   */
  public ReportCommand() {
    mHostname = Configuration.get(PropertyKey.MASTER_HOSTNAME);
    mPort = Configuration.getInt(PropertyKey.MASTER_WEB_PORT);
  }

  @Override
  public String getCommandName() {
    return "report";
  }

  @Override
  protected int getNumOfArgs() {
    return 0;
  }

  @Override
  public String getUsage() {
    return "report";
  }

  @Override
  public String getDescription() {
    return "report cluster-wide information and Per-node information";
  }

  @Override
  public int run(CommandLine cl) throws IOException {
    getClusterInfo();

    System.out.println("Live workers information");
    for (WorkerInfo workerInfo:mInfo.getWorkers()) {
      getNodeInfo(workerInfo);
    }
    System.out.println("Dead workers information");
    for (WorkerInfo workerInfo:mInfo.getLostWorkers()) {
      getNodeInfo(workerInfo);
    }

    return 0;
  }

  private void getClusterInfo() throws IOException {
    System.out.println("Cluster-wide information");

    String result =
        new AdminRestCase(mHostname, mPort, getEndpoint(GET_INFO), NO_PARAMS  , "GET").call();
    mInfo = new ObjectMapper().readValue(result, AlluxioMasterInfo.class);

    Capacity capacity = mInfo.getCapacity();
    System.out.println(String.format("Present capacity %d, Used capcity %d, remaining capacity %d",
        capacity.getTotal(), capacity.getUsed(), capacity.getTotal() - capacity.getUsed()));

    for (Map.Entry entry : mInfo.getTierCapacity().entrySet()) {
      String tier = entry.getKey().toString();
      Capacity capacity1 = mInfo.getTierCapacity().get(tier);
      System.out.println(String.format("tier name %s, Present capacity %d,"
          + " Used capacity %d, remaining capacity %d", tier , capacity1.getTotal(),
              capacity1.getUsed(), capacity1.getTotal() - capacity1.getUsed()));
    }

    String lostBlocksNum =
        new AdminRestCase(mHostname, mPort, getEndpoint(GET_LOST_BLOCKS), NO_PARAMS, "GET").call();
    System.out.println("lost blocks number: " + lostBlocksNum);

  }

  private Map<String, Capacity> getTierInfo(long workerId, String host) throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put(QUERY_WORKER_ID, workerId + "");
    String result =
        new AdminRestCase(host, mPort, getEndpoint(GET_WORKER_TIER_INFO), params, "GET").call();
    ObjectMapper mapper = new ObjectMapper();
    JavaType javaType =
        mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Capacity.class);
    return mapper.readValue(result, javaType);
  }

  private void getNodeInfo(WorkerInfo workerInfo) throws IOException {
    String hostName =  workerInfo.getAddress().getHost();
    System.out.println(String.format("Work Information: id %d, host: %s",
        workerInfo.getId(), hostName));
    System.out.println(String.format("Configured capacity %d, Used capacity %d",
        workerInfo.getCapacityBytes(), workerInfo.getUsedBytes()));

    Map<String, Capacity> tierInfo = getTierInfo(workerInfo.getId(), hostName);

    for (Map.Entry entry : tierInfo.entrySet()) {
      String tierName = entry.getKey().toString();
      Capacity capacity = tierInfo.get(tierName);
      System.out.println(String.format("tier name %s, Configured Capacity: %d, Used Capacity %d",
          entry.getKey(), capacity.getTotal(), capacity.getUsed()));
    }

    System.out.println("Last contact timestamp" + workerInfo.getLastContactSec());
  }

  private String getEndpoint(String suffix) {
    return SERVICE_PREFIX + "/" + suffix;
  }

  /** Create Restful case of report shell. */
  private class AdminRestCase {

    private String mHostname;
    private int mPort;
    private String mEndpoint;
    private Map<String, String> mParameters;
    private String mMethod;
    private InputStream mInputStream;

    /**
     * Creates a new instance of {@link AdminRestCase} with JSON data.
     *
     * @param hostname the hostname to use
     * @param port the port to use
     * @param endpoint the endpoint to use
     * @param parameters the parameters to use
     * @param method the method to use
     */
    AdminRestCase(String hostname, int port, String endpoint, Map<String, String> parameters,
            String method) {
      mHostname = hostname;
      mPort = port;
      mEndpoint = endpoint;
      mParameters = parameters;
      mMethod = method;
    }

    /**
     * @return The URL which is created
     */
    URL createURL() throws IOException {
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> parameter : mParameters.entrySet()) {
        if (parameter.getValue() == null || parameter.getValue().isEmpty()) {
          sb.append(parameter.getKey());
        } else {
          sb.append(parameter.getKey() + "=" + parameter.getValue() + "&");
        }
      }
      return new URL(
          "http://" + mHostname + ":" + mPort + Constants.REST_API_PREFIX + "/" + mEndpoint
              + "?" + sb.toString());
    }

    /**
     * @param connection the HttpURLConnection
     * @return the String from the InputStream of HttpURLConnection
     */
    String getResponse(HttpURLConnection connection) throws IOException {
      StringBuilder sb = new StringBuilder();
      try {
        mInputStream = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(mInputStream));
        char[] buffer = new char[1024];
        int len;
        while ((len = br.read(buffer)) > 0) {
          sb.append(buffer, 0, len);

        }
        br.close();
      } finally {
        mInputStream.close();
      }

      return sb.toString();
    }

    /**
     * Runs the test case and returns the {@link HttpURLConnection}.
     */
    public HttpURLConnection execute() throws IOException {
      HttpURLConnection connection = (HttpURLConnection) createURL().openConnection();
      connection.setRequestMethod(mMethod);

      connection.connect();
      if (connection.getResponseCode() != 200) {
        throw new IOException(connection.getResponseMessage());
      }
      return connection;
    }

    /**
     * Runs the test case and returns the output.
     */
    public String call() throws IOException {
      return getResponse(execute());
    }

  }
}
