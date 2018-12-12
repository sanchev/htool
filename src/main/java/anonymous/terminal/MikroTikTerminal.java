package anonymous.terminal;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.SocketFactory;

public class MikroTikTerminal implements Terminal {
    private static final Logger LOGGER = LogManager.getLogger(MikroTikTerminal.class.getName());

    public static final int DEFAULT_PORT = 8728;
    public static final int DEFAULT_TLS_PORT = 8729;

    private ApiConnection connection;

    @Override
    public boolean connect(String host, int port, String username, String password) {
        try {
            connection = ApiConnection.connect(SocketFactory.getDefault(), host, port, 5*60*1000);
            connection.login(username, password);
            return true;
        } catch (MikrotikApiException e) {
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            connection.close();
            return true;
        } catch (ApiConnectionException e) {
            return false;
        }
    }

    @Override
    public Object execute(String... args) {
        try {
            String cmd = args[0];
            return connection.execute(cmd);
        } catch (MikrotikApiException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}