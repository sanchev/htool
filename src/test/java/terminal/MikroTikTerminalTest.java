package terminal;

import anonymous.terminal.MikroTikTerminal;
import anonymous.terminal.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MikroTikTerminalTest {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminalTest.class.getName());

    private static Terminal terminal = new MikroTikTerminal();
    private static String host = "10.0.8.47";
    private static String username = "admin";
    private static String password = "120960";

    @Test
    public void testExecute() {
        LOGGER.info("testExecute()");

        terminal.connect(host, MikroTikTerminal.DEFAULT_PORT, username, password);

        String cmd = "/system/resource/print";

        Object result = terminal.execute(cmd);

        List<Map<String, String>> resultList = (List<Map<String, String>>) result;
        String actualPlatform = resultList.get(0).get("platform");

        String expectedPlatform = "MikroTik";

        assertEquals(expectedPlatform, actualPlatform);

        terminal.disconnect();
    }
}