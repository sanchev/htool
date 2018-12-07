package anonymous.terminal;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.net.SocketFactory;
import java.io.*;
import java.util.List;
import java.util.Map;

public class MikroTikTerminal implements Terminal {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminal.class.getName());

    public static final int DEFAULT_PORT = 8728;
    public static final int DEFAULT_TLS_PORT = 8729;
    public static final int DEFAULT_FTP_PORT = 21;

    private ApiConnection connection;

    private FTPClient ftpClient;

    @Override
    public boolean connect(String host, int port, String username, String password) {
        try {
            connection = ApiConnection.connect(SocketFactory.getDefault(), host, port, ApiConnection.DEFAULT_COMMAND_TIMEOUT);
            connection.login(username, password);
            return true;
        } catch (MikrotikApiException e) {
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            connection.close();
            return true;
        } catch (ApiConnectionException e) {
            return false;
        }
    }

    @Override
    public List<Map<String, String>> execute(String cmd) {
        try {
            return connection.execute(cmd);
        } catch (MikrotikApiException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean ftpUpLoad(String remoteFilePath, String localFilePath) {
        try (InputStream inputStream = new FileInputStream(new File(localFilePath))) {
            ftpClient.storeFile(remoteFilePath, inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public File ftpDownLoad(String remoteFilePath, String localFilePath) {
        File result = new File(localFilePath);
        try (OutputStream outputStream = new FileOutputStream(result)) {
            ftpClient.retrieveFile(remoteFilePath, outputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return result;
    }

    @Override
    public boolean ftpDelete(String remoteFilePath) {
        try {
            ftpClient.deleteFile(remoteFilePath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    public void ftpConnect(String host, int port, String username, String password) {
        try{
            ftpClient = new FTPClient();
            int reply;
            ftpClient.connect(host, port);
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void ftpDisconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}