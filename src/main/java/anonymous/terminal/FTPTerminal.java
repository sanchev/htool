package anonymous.terminal;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FTPTerminal implements Terminal {
    private static final Logger LOGGER = LogManager.getLogger(FTPTerminal.class.getName());

    public static final int DEFAULT_PORT = 21;

    private FTPClient ftpClient;

    @Override
    public boolean connect(String host, int port, String login, String password) {
        try{
            ftpClient = new FTPClient();
            int reply;
            ftpClient.connect(host, port);
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            ftpClient.login(login, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public Object execute(String... args) {
        FTPTerminalArgs ftpTerminalArgs = new FTPTerminalArgs();
        JCommander
                .newBuilder()
                .addObject(ftpTerminalArgs)
                .build()
                .parse(args);
        String cmd = ftpTerminalArgs.getCmd();
        String remoteFilePath = ftpTerminalArgs.getRemoteFilePath();
        String localFilePath = ftpTerminalArgs.getLocalFilePath();
        if (!"delete".equals(cmd) && localFilePath == null) {
            throw new ParameterException("The following option is required: [-localFile | -local | -lf | -l]");
        }
        switch (cmd) {
            case "upload" : return upload(remoteFilePath, localFilePath);
            case "download" : return download(remoteFilePath, localFilePath);
            case "delete" : return delete(remoteFilePath);
        }
        return null;
    }

    private boolean upload(String remoteFilePath, String localFilePath) {
        try (InputStream inputStream = new FileInputStream(new File(localFilePath))) {
            ftpClient.storeFile(remoteFilePath, inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private File download(String remoteFilePath, String localFilePath) {
        File result = new File(localFilePath);
        try (OutputStream outputStream = new FileOutputStream(result)) {
            ftpClient.retrieveFile(remoteFilePath, outputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return result;
    }

    private boolean delete(String remoteFilePath) {
        try {
            ftpClient.deleteFile(remoteFilePath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }
}