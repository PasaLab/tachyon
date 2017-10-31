package alluxio.cli.admin.command;

import alluxio.cli.AbstractCommand;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ReportCommand.class);

    /**
     */
    public ReportCommand() {}

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
    public int run(CommandLine cl) {

        return 0;
    }
}
