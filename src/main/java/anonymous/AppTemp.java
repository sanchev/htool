package anonymous;

import anonymous.terminal.FTPTerminal;
import anonymous.terminal.MikroTikTerminal;
import anonymous.terminal.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class AppTemp {
    private static final Logger LOGGER = LogManager.getLogger(AppTemp.class.getName());

    private static final String HOST = "10.0.8.68";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "120960";

    private static final String LOCAL_PATH = "src/main/resources/";

    private static Terminal mikroTikTerminal = new MikroTikTerminal();
    private static Terminal ftpTerminal = new FTPTerminal();

    public static void main(String... args) throws InterruptedException {
        LOGGER.info("main()");

        connect();

        if (!isVersionSix()) {
            update();
        }

        disableLog();

        disconnect();
    }

    private static void connect() {
        mikroTikTerminal.connect(HOST, MikroTikTerminal.DEFAULT_PORT, USERNAME, PASSWORD);
        ftpTerminal.connect(HOST, FTPTerminal.DEFAULT_PORT, USERNAME, PASSWORD);
    }

    private static void disconnect() {
        mikroTikTerminal.disconnect();
        ftpTerminal.disconnect();
    }

    private static boolean isVersionSix() {
        LOGGER.info("isVersionSix()");

        String cmd = "/system/resource/print";
        List<Map<String, String>> resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);

        int version = Integer.valueOf(resultList.get(0).get("version").split("\\.")[0]);

        LOGGER.info(version);

        return version == 6;
    }

    private static void disableLog() throws InterruptedException  {
        LOGGER.info("disableLog()");
        String scriptName = "disableLog";
        String fileName = scriptName + ".rsc";

        ftpTerminal.execute("upload", "r " + fileName, "l " + LOCAL_PATH + fileName);

        sleep(2000);

        String cmd = "/import file-name=" + fileName;

        LOGGER.info(mikroTikTerminal.execute(cmd));

        sleep(2000);

        cmd = "/system/script/print";
        LOGGER.info(cmd);
        List<Map<String, String>> resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);
        LOGGER.info(resultList);

        String scriptId = null;
        for (Map<String, String> result : resultList) {
            if (scriptName.equals(result.get("name"))) {
                scriptId = result.get(".id");
                break;
            }
        }

        cmd = "/system/script/run .id=" + scriptId;
        resultList = (List<Map<String, String>>) mikroTikTerminal.execute(cmd);
    }

    private static void update() {
        LOGGER.info("update()");

        String fileName = "routeros-mipsbe-6.18.npk";

        ftpTerminal.execute("upload", "r " + fileName, "l " + LOCAL_PATH + fileName);

        String cmd = "/system/reboot";
        mikroTikTerminal.execute(cmd);

        try {
            sleep(2*60*1000);
        } catch (Exception e) {
            connect();
        }

        connect();
    }
}