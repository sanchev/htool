package terminal;

import anonymous.base.Host;
import anonymous.terminal.MikroTikTerminator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MikroTikTerminatorTest {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminatorTest.class.getName());

    @Test
    public void testExecute() {
        LOGGER.info("testExecute()");
        String host = "10.0.0.50";
        String username = "admin";
        String password = "120960";

        String cmd = "/system/identity/print";

        MikroTikTerminator terminator = new MikroTikTerminator(host, username, password);

        terminator.run();

        //LOGGER.info(terminator.execute());
    }
}