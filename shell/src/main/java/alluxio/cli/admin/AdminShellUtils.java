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

package alluxio.cli.admin;

import alluxio.cli.Command;
import alluxio.cli.CommandUtils;

import java.util.Map;

/** Utils of AdminShell.*/
public class AdminShellUtils {

  /**
   * Gets all {@link Command} instances in the same package as {@link AdminShell} and load them
   * into a map. Provides a way to gain these commands information by their CommandName.
   *
   * @return a mapping from command name to command instance
   */
  public static Map<String, Command> loadCommands() {
    return CommandUtils.loadCommands(AdminShell.class.getPackage().getName(),
        null, null);
  }
}
