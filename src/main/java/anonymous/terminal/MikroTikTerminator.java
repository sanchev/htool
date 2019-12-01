package anonymous.terminal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class MikroTikTerminator {
    static final Logger LOGGER = LogManager.getLogger(MikroTikTerminator.class.getName());

    String host;
    String username;
    String password;

    int mikroTikApiPort = MikroTikTerminal.DEFAULT_PORT;
    private int telnetPort = TelnetTerminal.DEFAULT_PORT;
    private int ftpPort = FTPTerminal.DEFAULT_PORT;

    private static final String LOGIN_PATTERN = "Login: ";
    private static final String PASSWORD_PATTERN = "Password: ";
    private static final String CMD_PATTERN = "\\[.+@.+\\][^>]+[>]+ ";

    private static final String LOCAL_PATH = "src/main/resources/";
    private static final String SCRIPTS_PATH = "src/main/resources/scripts/";

    private static final String FIRMWARE_VERSION  = "6.43.8";

    private static final String FIRMWARE = "routeros-mipsbe-" + FIRMWARE_VERSION + ".npk";

    static Terminal mikroTikTerminal = new MikroTikTerminal();
    private static Terminal telnetTerminal = new TelnetTerminal(LOGIN_PATTERN, PASSWORD_PATTERN, CMD_PATTERN);
    private static Terminal ftpTerminal = new FTPTerminal();

    public MikroTikTerminator(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public void setMikroTikApiPort(int mikroTikApiPort) {
        this.mikroTikApiPort = mikroTikApiPort;
    }

    public void setTelnetPort(int telnetPort) {
        this.telnetPort = telnetPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public void run() {
        try {
            if (connect()) {
                LOGGER.info("Logged on " + host);

                if (!needUpdate()) {
                    update();
                    needUpdate();
                }

                script("disableDiscovery");
                script("disableLog");
                script("disableMacServer");
                reIdentity();
                script("time");

                if (disconnect()) {
                    LOGGER.info("Logout from " + host);
                }
            } else {
                throw new RuntimeException("NOT logged on " + host + ". Try again.");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    boolean connect() {
        boolean connected = mikroTikTerminal.connect(host, mikroTikApiPort, username, password);
        if (!connected) {
            connected = telnetTerminal.connect(host, telnetPort, username, password);
            if (connected) {
                telnetTerminal.execute("ip service enable api");
                connected = mikroTikTerminal.connect(host, mikroTikApiPort, username, password);
            } else {
                return false;
            }
        }
        mikroTikTerminal.execute("/ip/service/enable numbers=ftp");
        ftpTerminal.connect(host, ftpPort, username, password);
        return connected;
    }

    boolean disconnect() {
        if (((TelnetTerminal) telnetTerminal).isConnected())
            return mikroTikTerminal.disconnect() && telnetTerminal.disconnect() && ftpTerminal.disconnect();
        else
            return mikroTikTerminal.disconnect() && ftpTerminal.disconnect();
    }

    private boolean needUpdate() {
        //LOGGER.info("needUpdate()");

        String cmd = "/system/resource/print";
        List<Map<String, String>> resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);

        String version =  resultList.get(0).get("version");

        LOGGER.info("Version: " + version);

        Pattern pattern = Pattern.compile("\\d+\\.\\d+(\\.\\d+)*");
        Matcher matcher = pattern.matcher(version);

        if (matcher.find()) {
            version = matcher.group();
        }

        return FIRMWARE_VERSION.equals(version);
    }

    private void script(String scriptName) throws InterruptedException  {
        String fileName = scriptName + ".rsc";

        //remove script if exist
        String  cmd = "/system/script/print";
        List<Map<String, String>> resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);

        String scriptId = null;
        for (Map<String, String> result : resultList) {
            if (scriptName.equals(result.get("name"))) {
                scriptId = result.get(".id");
                break;
            }
        }

        cmd = "/system/script/remove .id=" + scriptId;
        mikroTikTerminal.execute(cmd);

        //upload script file
        ftpTerminal.execute("upload", "-r", fileName, "-l", SCRIPTS_PATH + fileName);

        sleep(2000);

        cmd = "/import file-name=" + fileName;
        mikroTikTerminal.execute(cmd);

        sleep(2000);

        //run script
        cmd = "/system/script/print";
        resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);

        scriptId = null;
        for (Map<String, String> result : resultList) {
            if (scriptName.equals(result.get("name"))) {
                scriptId = result.get(".id");
                break;
            }
        }

        cmd = "/system/script/run .id=" + scriptId;
        mikroTikTerminal.execute(cmd);

        LOGGER.info("Script: " + scriptName + ": done");
    }

    private void update() {
        LOGGER.info("update()");

        ftpTerminal.execute("upload", "-r ", FIRMWARE, "-l", LOCAL_PATH + FIRMWARE);

        String cmd = "/system/reboot";
        mikroTikTerminal.execute(cmd);

        try {
            sleep(1*60*1000);
        } catch (Exception e) {
            connect();
        }

        connect();
    }

    private void reIdentity() throws InterruptedException, IOException {
        String scriptName = "identity";
        Path scriptFilePath = Paths.get(SCRIPTS_PATH + scriptName + ".rsc");

        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(scriptFilePath), charset);
        String newContent = content.replaceAll("X.X.X.X", host);
        Files.write(scriptFilePath, newContent.getBytes(charset));

        script("identity");

        Files.write(scriptFilePath, content.getBytes(charset));
    }
}