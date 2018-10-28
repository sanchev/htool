package anonymous.base;

import java.util.Collection;

public interface DBService {
    Collection<Host> getAllHosts();
    long addHost(Host host);
    Host getById(long id);
    boolean updateHost(Host host);
    boolean deleteHost(Host host);
}