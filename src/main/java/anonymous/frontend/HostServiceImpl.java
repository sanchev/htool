package anonymous.frontend;

import anonymous.base.HostService;
import anonymous.base.DBService;
import anonymous.base.Host;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class HostServiceImpl implements HostService {
    private static final Logger LOGGER = LogManager.getLogger(HostServiceImpl.class.getName());

    private final DBService dbService;

    public HostServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    public Collection<Host> getFilteredHosts(String regex) {
        LOGGER.info(String.format("regex: %s", regex));
        Collection<Host> hosts = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Collection<Host> allHosts = dbService.getAllHosts();
        LOGGER.info(String.format("All hosts from db: %s", allHosts));
        if (allHosts == null)
            return null;
        for (Host host : allHosts) {
            if (!pattern.matcher(host.getIdentity()).matches())
                hosts.add(host);
        }
        LOGGER.info(String.format("Filtered hosts: %s", hosts));
        return hosts;
    }
}