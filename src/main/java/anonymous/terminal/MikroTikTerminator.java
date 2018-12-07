package anonymous.terminal;

import anonymous.base.Device;
import anonymous.base.Host;
import anonymous.base.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MikroTikTerminator {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminator.class.getName());

    private List<Host> hostList;
    private List<String> cmdList;

    public MikroTikTerminator(List<Host> hostList, List<String> cmdList) {
        this.hostList = hostList;
        this.cmdList = cmdList;
    }

    public Map<String, Map<String, List<Map<String, String>>>> execute() {
        Map<String, Map<String, List<Map<String, String>>>> result = new HashMap<>();
        for (Host host : hostList) {
            Terminal terminal = new MikroTikTerminal();
            String ip = host.getIp();
            String username = host.getLogin();
            String password = host.getPassword();
            int port = MikroTikTerminal.DEFAULT_PORT;
            Device mikroTikDevice = null;
            for (Device device : host.getDeviceList()) {
                if ("MikroTik".equals(device.getVendor())) {
                    mikroTikDevice = device;
                    break;
                }
            }
            if (mikroTikDevice != null) {
                if (!mikroTikDevice.getServiceList().isEmpty()) {
                    for (Service service : mikroTikDevice.getServiceList()) {
                        if ("api".equals(service.getName())) {
                            port = service.getPort();
                            break;
                        }
                    }
                }
            }
            if (terminal.connect(ip, port, username, password)) {
                LOGGER.info("Logged on " + ip);
                Map<String, List<Map<String, String>>> executeCmdListResult = new HashMap<>();
                for (String cmd : cmdList) {
                    List<Map<String, String>> executeCmdResult = terminal.execute(cmd);
                    executeCmdListResult.put(cmd, executeCmdResult);
                }
                if (terminal.disconnect()) {
                    LOGGER.info("Logout from " + ip);
                }
                result.put(ip, executeCmdListResult);
            } else {
                LOGGER.info("NOT logged on " + ip + ". Try again.");
                result.put(ip, null);
            }
         }
         return result;
    }
}