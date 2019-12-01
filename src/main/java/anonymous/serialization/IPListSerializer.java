package anonymous.serialization;

import anonymous.base.Host;
import anonymous.serialization.nmap.converter.AddressValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IPListSerializer implements Serializer {
    private static final Logger LOGGER = LogManager.getLogger(IPListSerializer.class.getName());

    @Parameter(names = {"-address", "-ip"}, validateValueWith = AddressValidator.class, description = "Comma-separated list of ip-addresses")
    private List<String> address = new ArrayList<>();

    @Parameter(names = {"-filePath", "-f"}, description = "File contains comma-separated list of ip-addresses")
    private String filePath;

    @Override
    public Collection<Host> unserialize(String ... args) {
        JCommander
                .newBuilder()
                .addObject(this)
                .build()
                .parse(args);
        List<Host> hostList = new ArrayList<>();
        if (address.isEmpty()) {
            if (filePath == null || filePath.length() == 0)
                return null;
            else {
                try {
                    String fileContent = new String(Files.readAllBytes(Paths.get(filePath))).replaceAll("\r", "\n").replaceAll("[\n]+", ",").replaceAll(",,+", ",");
                    JCommander
                            .newBuilder()
                            .addObject(this)
                            .build()
                            .parse("-ip", fileContent);
                    if (address.isEmpty()) {
                        return null;
                    } else {
                        for (String ip : address) {
                            Host host = new Host();
                            host.setIp(ip);
                            hostList.add(host);
                        }
                        return hostList;
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    return null;
                }
            }
        } else {
            for (String ip : address) {
                Host host = new Host();
                host.setIp(ip);
                hostList.add(host);
            }
            return hostList;
        }
    }
}