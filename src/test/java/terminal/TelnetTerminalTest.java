package terminal;

import anonymous.terminal.TelnetTerminal;
import anonymous.terminal.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TelnetTerminalTest {
    private static final Logger LOGGER = LogManager.getLogger(TelnetTerminalTest.class.getName());

    private static Terminal terminal = new TelnetTerminal("Login: ", "Password: ", "\\[.+@.+\\][^>]+[>]+ ");
    private static String host = "10.0.8.47";
    private static String username = "admin";
    private static String password = "120960";

    @Test
    public void testExecute() {
        LOGGER.info("testExecute()");

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
}