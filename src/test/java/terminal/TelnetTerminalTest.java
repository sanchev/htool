package terminal;

import anonymous.terminal.TelnetTerminal;
import anonymous.terminal.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TelnetTerminalTest {
    private static final Logger LOGGER = LogManager.getLogger(TelnetTerminalTest.class.getName());

    private Terminal terminal;
    private static String host = "10.0.8.47";
    private static String username = "admin";
    private static String password = "120960";

    @Before
    public void before() {
        terminal = new TelnetTerminal("Login: ", "Password: ", "\\[.+@.+\\][^>]+[>]+ ");
    }

    @Test
    public void testExecute_No_Param() {
        LOGGER.info("testExecute_No_Param()");

        assertTrue(terminal.connect(host, TelnetTerminal.DEFAULT_PORT, username, password));

        ArrayList<String> executeResults = (ArrayList<String>) terminal.execute("system resource print");

        String platform = null;
        for (String executeResult : executeResults) {
            LOGGER.info(executeResult);
            if (executeResult.contains("platform")) {
                platform = executeResult;
            }
        }

        assertNotNull(platform);
        assertTrue(platform.contains("MikroTik"));

        assertTrue(terminal.disconnect());
    }

    @Test
    public void testExecute_StartPattern() {
        LOGGER.info("testExecute_StartPattern()");

        assertTrue(terminal.connect(host, TelnetTerminal.DEFAULT_PORT, username, password));

        ArrayList<String> executeResults = (ArrayList<String>) terminal.execute("system resource print", "-s", "\"\\[.+@.+\\][^>]+[>]+ \"");

        String platform = null;
        for (String executeResult : executeResults) {
            LOGGER.info(executeResult);
            if (executeResult.contains("platform")) {
                platform = executeResult;
            }
        }

        assertNotNull(platform);
        assertTrue(platform.contains("MikroTik"));

        assertTrue(terminal.disconnect());
    }

    @Test
    public void testExecute_EndPattern() {
        LOGGER.info("testExecute_EndPattern()");

        assertTrue(terminal.connect(host, TelnetTerminal.DEFAULT_PORT, username, password));

        ArrayList<String> executeResults = (ArrayList<String>) terminal.execute("system resource print", "-e", "\"\\[.+@.+\\][^>]+[>]+ \"");

        String platform = null;
        for (String executeResult : executeResults) {
            LOGGER.info(executeResult);
            if (executeResult.contains("platform")) {
                platform = executeResult;
            }
        }

        assertNotNull(platform);
        assertTrue(platform.contains("MikroTik"));

        assertTrue(terminal.disconnect());
    }

    @Test
    public void testExecute_Start_EndPattern() {
        LOGGER.info("testExecute_Start_EndPattern()");

        assertTrue(terminal.connect(host, TelnetTerminal.DEFAULT_PORT, username, password));

        ArrayList<String> executeResults = (ArrayList<String>) terminal.execute("system resource print", "-s", "\"\\[.+@.+\\][^>]+[>]+ \"", "-e", "\"\\[.+@.+\\][^>]+[>]+ \"");

        String platform = null;
        for (String executeResult : executeResults) {
            LOGGER.info(executeResult);
            if (executeResult.contains("platform")) {
                platform = executeResult;
            }
        }

        assertNotNull(platform);
        assertTrue(platform.contains("MikroTik"));

        assertTrue(terminal.disconnect());
    }
}