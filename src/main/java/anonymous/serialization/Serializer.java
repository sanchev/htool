package anonymous.serialization;

import anonymous.base.Host;

import java.util.Collection;

public interface Serializer {
    public Collection<Host> unserialize(String ... args);
}