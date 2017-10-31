package alluxio.cli.admin;

import alluxio.cli.Command;
import alluxio.cli.CommandUtils;
import alluxio.cli.fs.FileSystemShell;

import java.util.Map;

public class AdminShellUtils {

    public static Map<String, Command> loadCommands() {
        return CommandUtils.loadCommands(FileSystemShell.class.getPackage().getName(),
                null, null);
    }
}
