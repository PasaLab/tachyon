package alluxio.cli.admin.command;

import alluxio.Configuration;
import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.cli.AbstractCommand;
import alluxio.wire.AlluxioMasterInfo;
import alluxio.wire.Capacity;
import alluxio.wire.WorkerInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.ByteStreams;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ReportCommand.class);
    private String  mHostname;
    private int mPort;
    protected static final Map<String, String> NO_PARAMS = new HashMap<>();
    public static final String GET_INFO = "info";
    public static final String GET_WORKER_TIER_INFO = "woker_tier_info";
    public static final String GET_LOST_BLOCKS = "lost_blocks_number";
      public static final String SERVICE_PREFIX = "master";
    private AlluxioMasterInfo mInfo;

    /**
     */
    public ReportCommand() {
        mHostname = Configuration.get(PropertyKey.MASTER_HOSTNAME);
        mPort = Configuration.getInt(PropertyKey.MASTER_WEB_PORT);
    }

    @Override
    public String getCommandName() {
        return "report";
    }

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

        for(WorkerInfo workerInfo:mInfo.getWorkers()) {
            getNodeInfo(workerInfo);
        }
        for(WorkerInfo workerInfo:mInfo.getLostWorkers()) {
            getNodeInfo(workerInfo);
        }

        return 0;
    }


    private void getClusterInfo() throws IOException {

        String result = new AdminRestCase(mHostname, mPort, getEndpoint(GET_INFO), NO_PARAMS  , "GET").call();
        mInfo = new ObjectMapper().readValue(result, AlluxioMasterInfo.class);

        Capacity capacity = mInfo.getCapacity();
        System.out.println(String.format("Configured capacity %s, Present capacity %d, Used capcity %d, remaining capacity %d",
            , capacity.getTotal(), capacity.getUsed(), capacity.getTotal()- capacity.getUsed());

        for(Map.Entry entry : mInfo.getTierCapacity().entrySet()) {
            String tier = entry.getKey().toString();
            Capacity capacity1 = (Capacity)entry.getValue();
            System.out.println(String.format("tier name %s, Configured capacity %s, Present capacity %d," +
                " Used capacity %d, remaining capacity %d",tier, , capacity.getTotal(), capacity.getUsed(), capacity.getTotal()- capacity.getUsed()));
        }

        String lostBlocksNum =
            new AdminRestCase(mHostname, mPort, getEndpoint(GET_LOST_BLOCKS), NO_PARAMS, "GET").call();
        System.out.println("lost blocks number: " + lostBlocksNum);

    }

    private String getConfigureCapacity(String tier) {
        String ConfRes = "";



    }

    private Map<String, Capacity> getTierInfo(long workerId, String host) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("worker_id", workerId+"");
        String result = new AdminRestCase(host, mPort, getEndpoint(GET_WORKER_TIER_INFO), params, "GET").call();
        return new ObjectMapper().readValue(result, Map.class);
    }

    private void getNodeInfo(WorkerInfo workerInfo) throws IOException {
        String hostName =  workerInfo.getAddress().getHost();
        System.out.println(String.format("Work Information: id %d, host: %s", workerInfo.getId(),hostName));
        System.out.println(String.format("Configured capacity %d, Used capacity %d",
            workerInfo.getCapacityBytes(), workerInfo.getUsedBytes()));

        Map<String, Capacity> tierInfo = getTierInfo(workerInfo.getId(), hostName);

        for (Map.Entry entry : tierInfo.entrySet()) {
            Capacity capacity = (Capacity) entry.getValue();
            System.out.println(String.format("tier name %s, Configured Capacity: %d, Used Capacity %d",
                entry.getKey(), capacity.getTotal(), capacity.getUsed()));
        }

        System.out.println("Last contact timestamp" + workerInfo.getLastContactSec());
    }


    private String getEndpoint(String suffix) {
        return SERVICE_PREFIX + "/" + suffix;
    }

    class AdminRestCase {

        private String mHostname;
        private int mPort;
        private String mEndpoint;
        private Map<String, String> mParameters;
        private String mMethod;

        AdminRestCase(String hostname, int port, String endpoint, Map<String, String> parameters,
                        String method) {
            mHostname = hostname;
            mPort = port;
            mEndpoint = endpoint;
            mParameters = parameters;
            mMethod = method;
        }

        URL createURL() throws IOException

        {
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

        String getResponse(HttpURLConnection connection) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            char[] buffer = new char[1024];
            int len;

            while ((len = br.read(buffer)) > 0) {
                sb.append(buffer, 0, len);

            }
            br.close();

            return sb.toString();
        }

        /**
         * Runs the test case and returns the {@link HttpURLConnection}.
         */
        public HttpURLConnection execute() throws IOException {
            HttpURLConnection connection = (HttpURLConnection) createURL().openConnection();
            connection.setRequestMethod(mMethod);


            connection.connect();
            if(connection.getResponseCode() !=200) {


                //TODO
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
