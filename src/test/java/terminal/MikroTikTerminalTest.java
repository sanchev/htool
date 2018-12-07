package terminal;

import anonymous.terminal.MikroTikTerminal;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MikroTikTerminalTest {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminalTest.class.getName());

    private static MikroTikTerminal terminal = new MikroTikTerminal();
    private static String host = "10.0.8.47";
    private static String username = "admin";
    private static String password = "120960";

    @Test
    public void testFtp() throws IOException {
        LOGGER.info("testFtp()");

        terminal.ftpConnect(host, MikroTikTerminal.DEFAULT_FTP_PORT, username, password);

        String expectedFilePath = "src/test/resources/ftp-test";
        String actualFilePath = expectedFilePath + "-download";
        String remoteFilePath = "ftp-test";

        assertTrue(terminal.ftpUpLoad(remoteFilePath, expectedFilePath));

        File downLoad = terminal.ftpDownLoad(remoteFilePath, actualFilePath);
        assertTrue(FileUtils.contentEquals(downLoad, new File(expectedFilePath)));

        downLoad.delete();
        assertTrue(terminal.ftpDelete(remoteFilePath));

        terminal.ftpDisconnect();
    }

    @Test
    public void testExecute() throws IOException {
        LOGGER.info("testExecute()");

        terminal.connect(host, MikroTikTerminal.DEFAULT_PORT, username, password);

        String cmd = "/system/resource/print";
        List<Map<String, String>> resultList = terminal.execute(cmd);

        String expectedPlatform = "MikroTik";
        String actualPlatform = resultList.get(0).get("platform");

        assertTrue(expectedPlatform.equals(actualPlatform));

        terminal.disconnect();
    }
}