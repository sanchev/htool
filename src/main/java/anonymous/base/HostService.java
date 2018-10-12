package anonymous.base;

import java.util.Collection;

public interface HostService {
    Collection<Host> getFilteredHosts(String regex);
}