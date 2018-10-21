package db;

import anonymous.base.Host;
import anonymous.base.DBService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DBServiceDummy implements DBService {

    private static Collection<Host> hosts = new ArrayList<>();

    static {
        hosts.add(new  Host(1, "10.0.7.183/32", "shved_nataljya", null, "admin", "120960"));
        hosts.add(new  Host(2, "10.0.7.186/32", "kurta_ivan", null, "admin", "120960"));
    }

    public Collection<Host> getAllHosts() {
        return hosts;
    }

    public long addHost(Host host) {
        hosts.add(host);
        return hosts.size();
    }
}