package anonymous.terminal;

import anonymous.terminal.paramvalidator.FTPTerminalCMDValidator;
import com.beust.jcommander.Parameter;

public class FTPTerminalArgs {
    @Parameter(validateWith = FTPTerminalCMDValidator.class, required = true, description = "FTP command")
    private String cmd;

    @Parameter(names = {"-remoteFile", "-remote", "-rf", "-r"}, required = true, description = "Remote file path")
    private String remoteFilePath;

    @Parameter(names = {"-localFile", "-local", "-lf", "-l"}, description = "Local file path")
    private String localFilePath;

    public String getCmd() {
        return cmd;
    }

    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }
}