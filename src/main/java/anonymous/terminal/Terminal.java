package anonymous.terminal;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Terminal {
    public boolean connect(String host, int port, String login, String password);
    public boolean disconnect();
    public List<Map<String, String>> execute(String cmd);
    public boolean ftpUpLoad(String remoteFilePath, String localFilePath);
    public File ftpDownLoad(String remoteFilePath, String localFilePath);
    public boolean ftpDelete(String remoteFilePath);
}