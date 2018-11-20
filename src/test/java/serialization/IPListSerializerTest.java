package serialization;

import anonymous.base.Host;
import anonymous.serialization.nmap.IPListSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IPListSerializerTest {
    private static final Logger LOGGER = LogManager.getLogger(IPListSerializerTest.class.getName());

    @Test
    public void testUnserialize_from_StringList() {
        LOGGER.info("testUnserialize_from_StringList()");
        IPListSerializer serializer = new IPListSerializer();
        Collection<Host> hostCollection = serializer.unserialize("-ip", "10.0.0.1,10.0.0.2,10.0.0.3");

        assertEquals(3, hostCollection.size());
        assertEquals("10.0.0.1", ((List<Host>) hostCollection).get(0).getIp());
        assertEquals("10.0.0.2", ((List<Host>) hostCollection).get(1).getIp());
        assertEquals("10.0.0.3", ((List<Host>) hostCollection).get(2).getIp());
    }

    @Test
    public void testUnserialize_from_File() {
        LOGGER.info("testUnserialize_from_File()");
        IPListSerializer serializer = new IPListSerializer();
        String filePath = "src/test/resources/ipList.txt";
        Collection<Host> hostCollection = serializer.unserialize("-f", filePath);

        assertEquals(10, hostCollection.size());
    }
}