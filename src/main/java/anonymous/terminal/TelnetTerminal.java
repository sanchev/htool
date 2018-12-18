package anonymous.terminal;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class TelnetTerminal implements Terminal {
    public static final int DEFAULT_PORT = 23;
    private static final Logger LOGGER = LogManager.getLogger(TelnetTerminal.class.getName());
    private TelnetClient telnetClient;

    private InputStream in;
    private OutputStream out;

    private String loginPattern;
    private String passwordPattern;
    private String cmdPattern;

    public TelnetTerminal(String loginPattern, String passwordPattern, String cmdPattern) {
        this.loginPattern = loginPattern;
        this.passwordPattern = passwordPattern;
        this.cmdPattern = cmdPattern;
    }

    @Override
    public boolean connect(String host, int port, String login, String password) {
        try {
            telnetClient = new TelnetClient();
            telnetClient.connect(host, port);

            in = telnetClient.getInputStream();
            out = telnetClient.getOutputStream();

            doCMD(loginPattern, login);
            doCMD(passwordPattern, password);
            getResult(cmdPattern);

            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            telnetClient.disconnect();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Object execute(String... args) {

        try {
            StringBuilder stringBuilder = doCMD(cmdPattern, args[0]);
            stringBuilder.append(getResult(cmdPattern));

            String result = stringBuilder.toString();
            result = result.replaceAll("\r", "\n");
            String pattern = new StringBuilder(cmdPattern).append(".*").toString();
            result = result.replaceAll(pattern, "");
            result = result.replaceAll("\n[\n]+", "\n");
            result = result.replaceAll("^\n", "");
            return new ArrayList<String>(Arrays.asList(result.split("\n")));
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private String receive() throws IOException, InterruptedException {
        StringBuffer strBuffer = new StringBuffer();
        byte[] buf = new byte[512];
        int len;
        Thread.sleep(750L);
        while ((len = in.read(buf)) != 0) {
            strBuffer.append(new String(buf, 0, len));
            //Thread.sleep(750L);
            if (in.available() == 0)
                break;
        }
        return strBuffer.toString();
    }

    private void send(String data) throws IOException {
        byte[] bytes = (data + "\r\n").getBytes();
        out.write(bytes);
        out.flush();
    }

    private StringBuilder doCMD(String pattern, String cmd) throws IOException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            stringBuilder.append(receive());
            String[] results = stringBuilder.toString().replaceAll("\r", "\n").replaceAll("\n[\n]+", "\n").replaceAll("^\n", "").split("\n");
            for (String result : results) {
                if (result.matches(pattern +"$")) {
                    send(cmd);
                    return stringBuilder;
                }
            }
        }
    }

    private StringBuilder getResult(String pattern) throws IOException, InterruptedException {
        return doCMD(pattern, "");
    }

    private void changeLoggerConfig(String fileName) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        context.setConfigLocation(file.toURI());
    }
}