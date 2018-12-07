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
        List<Host> hostList = new ArrayList<>();
        Host host1 = new Host();
        host1.setIp("10.0.8.45");
        host1.setLogin("admin");
        host1.setPassword("120960");
        hostList.add(host1);

        List<String> cmdList = new ArrayList<>();
        cmdList.add("/system/identity/print");

        MikroTikTerminator terminator = new MikroTikTerminator(hostList, cmdList);

        LOGGER.info(terminator.execute());
    }
}