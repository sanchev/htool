package db;

import anonymous.base.Host;
import anonymous.base.DBService;

import java.util.ArrayList;
import java.util.Collection;

public final class DBServiceDummy implements DBService {

    private static Collection<Host> allHosts = new ArrayList<>();

    static {
        allHosts.add(new  Host(1, "10.0.7.183/32", "shved_nataljya", "", "admin", "120960"));
        allHosts.add(new  Host(2, "10.0.7.186/32", "kurta_ivan", "", "admin", "120960"));
    }

    public Collection<Host> getAllHosts() {
        return allHosts;
    }
}