package anonymous.terminal;

import com.beust.jcommander.JCommander;
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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelnetTerminal implements Terminal {
    public static final int DEFAULT_PORT = 23;
    private static final Logger LOGGER = LogManager.getLogger(TelnetTerminal.class.getName());
    private String loginPattern;
    private String passwordPattern;
    private String cmdPattern;
    private TelnetClient telnetClient;
    private InputStream in;
    private OutputStream out;
    private boolean connected = false;


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

            connected = true;

            return connected;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            telnetClient.disconnect();
            connected = false;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Object execute(String... args) {
        TelnetTerminalArgs telnetTerminalArgs = new TelnetTerminalArgs();
        JCommander
                .newBuilder()
                .addObject(telnetTerminalArgs)
                .build()
                .parse(args);
        String cmd = telnetTerminalArgs.getCmd();
        String startPattern = telnetTerminalArgs.getStartPattern();
        String endPattern = telnetTerminalArgs.getEndPattern();

        StringBuilder stringBuilder;
        try {
            if (startPattern == null && endPattern == null) {
                stringBuilder = doCMD(cmdPattern, cmd);
                stringBuilder.append(getResult(cmdPattern));

                stringBuilder = removePattern(stringBuilder, cmdPattern);

                return convertToList(stringBuilder);
            } else if (startPattern != null && endPattern == null) {
                stringBuilder = doCMD(startPattern, cmd);
                stringBuilder.append(getResult(cmdPattern));

                stringBuilder = removePattern(stringBuilder, cmdPattern);
                stringBuilder = removePattern(stringBuilder, startPattern);

                return convertToList(stringBuilder);
            } else if (startPattern == null) {
                stringBuilder = doCMD(cmdPattern, cmd);
                stringBuilder.append(getResult(endPattern));

                stringBuilder = removePattern(stringBuilder, cmdPattern);
                stringBuilder = removePattern(stringBuilder, endPattern);

                return convertToList(stringBuilder);
            } else {
                stringBuilder = doCMD(startPattern, cmd);
                stringBuilder.append(getResult(endPattern));

                stringBuilder = removePattern(stringBuilder, startPattern);
                stringBuilder = removePattern(stringBuilder, endPattern);

                return convertToList(stringBuilder);
            }


        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private StringBuilder removePattern(StringBuilder stringBuilder, String pattern) {
        return new StringBuilder(stringBuilder.toString().replaceAll(pattern + ".*", ""));
    }

    private Object convertToList(StringBuilder stringBuilder) {
        String result = stringBuilder.toString().replaceAll("\r", "\n");
        result = result.replaceAll("\n[\n]+", "\n");
        result = result.replaceAll("^\n", "");
        return new ArrayList<>(Arrays.asList(result.split("\n")));
    }

    private StringBuilder doCMD(String pattern, String cmd) throws IOException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            stringBuilder.append(receiveString());
            String[] results = stringBuilder.toString().replaceAll("\r", "\n").replaceAll("\n[\n]+", "\n").replaceAll("^\n", "").split("\n");
            for (String result : results) {
                Pattern p = Pattern.compile(pattern + "$");
                Matcher m = p.matcher(result);
                if (m.find()) {
                    send(cmd);
                    return stringBuilder;
                }
            }
        }
    }

    private String getResult(String endPattern) throws IOException, InterruptedException {
        in.mark(0);
        StringBuilder stringBuilder = new StringBuilder();
        boolean match = false;
        int matchIndex = -1;
        while (!match) {
            stringBuilder.append(receiveString());
            String[] results = stringBuilder.toString().replaceAll("\r", "\n").replaceAll("\n[\n]+", "\n").replaceAll("^\n", "").split("\n");
            for (int i = 0; i < results.length; i++) {
                if (results[i].matches(endPattern + "$")) {
                    in.reset();
                    match = true;
                    matchIndex = i;
                }
            }
        }
        match = false;
        stringBuilder = new StringBuilder();
        while (!match) {
            stringBuilder.append(receiveChar());
            String[] results = stringBuilder.toString().replaceAll("\r", "\n").replaceAll("\n[\n]+", "\n").replaceAll("^\n", "").split("\n");
            for (int i = 0; i < results.length; i++) {
                if (i == matchIndex) {
                    in.reset();
                    stringBuilder.setLength(stringBuilder.length() - 1);
                    match = true;
                }
            }
        }
        return stringBuilder.toString();
    }

    private String receiveString() throws IOException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buf = new byte[512];
        int len;
        Thread.sleep(750L);
        while ((len = in.read(buf)) != 0) {
            stringBuilder.append(new String(buf, 0, len));
            //Thread.sleep(750L);
            if (in.available() == 0)
                break;
        }
        return stringBuilder.toString();
    }

    private char receiveChar() throws IOException {
        if (in.available() > 0) {
            in.mark(0);
            return (char) in.read();
        }
        return '\u0000';
    }

    private void send(String data) throws IOException {
        byte[] bytes = (data + "\r\n").getBytes();
        out.write(bytes);
        out.flush();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void changeLoggerConfig(String fileName) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        context.setConfigLocation(file.toURI());
    }

    public boolean isConnected(){
        return connected;
    }
}