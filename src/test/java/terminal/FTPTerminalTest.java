package terminal;

import anonymous.terminal.FTPTerminal;
import anonymous.terminal.Terminal;
import com.beust.jcommander.ParameterException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FTPTerminalTest {
    private static final Logger LOGGER = LogManager.getLogger(FTPTerminalTest.class.getName());

    private static Terminal terminal = new FTPTerminal();
    private static String host = "10.0.8.47";
    private static String username = "admin";
    private static String password = "120960";

    @Before
    public void before() {
        terminal.connect(host, FTPTerminal.DEFAULT_PORT, username, password);
    }

    @After
    public void after() {
        terminal.disconnect();
    }

    @Test
    public void testExecute() throws IOException {
        LOGGER.info("testExecute()");

        String expectedFilePath = "src/test/resources/ftp-test";
        String actualFilePath = expectedFilePath + "-download";
        String remoteFilePath = "ftp-test";

        assertTrue((Boolean) terminal.execute("upload", "-r", remoteFilePath, "-l", expectedFilePath));

        File downLoad = (File) terminal.execute("download", "-r", remoteFilePath, "-l", actualFilePath);
        assertTrue(FileUtils.contentEquals(downLoad, new File(expectedFilePath)));

        assertTrue(downLoad.delete());
        assertTrue((Boolean) terminal.execute("delete", "-r", remoteFilePath));
    }

    @Test(expected = ParameterException.class)
    public void testExecute_EXCEPTION_WRONG_COMMAND() {
        LOGGER.info("testExecute_EXCEPTION_WRONG_COMMAND()");
        //wrong command
        terminal.execute("wrong", "-r", "abc");
    }

    @Test(expected = ParameterException.class)
    public void testExecute_EXCEPTION_NO_REMOTE() {
        LOGGER.info("testExecute_EXCEPTION_NO_REMOTE");
        //wrong command
        terminal.execute("upload");
    }

    @Test(expected = ParameterException.class)
    public void testExecute_EXCEPTION_NO_LOCAL() {
        LOGGER.info("testExecute_EXCEPTION_NO_LOCAL");
        //wrong command
        terminal.execute("upload", "-r", "test");
    }
}