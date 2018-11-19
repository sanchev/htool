package serialization.nmap;

import anonymous.base.Host;
import anonymous.serialization.nmap.NMapConverter;
import anonymous.serialization.nmap.NMapRun;
import anonymous.serialization.nmap.XMLNMapSerializer;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NMapConverterTest {
    private static final Logger LOGGER = LogManager.getLogger(NMapConverterTest.class.getName());

    private NMapConverter nmapConverter;

    @Before
    public void before() {
        String filePath = "src/test/resources/nmap-test2.xml";
        File file = new File(filePath);
        NMapRun nmapRun = XMLNMapSerializer.unserialize(file);
        nmapConverter = new NMapConverter(nmapRun);
    }

    @Test
    public void testGetAllHosts() {
        LOGGER.info("testGetAllHosts()");
        assertEquals(3, nmapConverter.getHosts().size());
    }

    @Test
    public void testGetHostsFilteredByAddress() {
        LOGGER.info("testGetHostsFilteredByAddress()");
        Collection<Host> hostCollection = nmapConverter.getHosts("-ip", "10.0.0.1,10.0.0.3");
        assertEquals(2, hostCollection.size());
        assertEquals("10.0.0.1", ((List<Host>) hostCollection).get(0).getIp());
        assertEquals("10.0.0.3", ((List<Host>) hostCollection).get(1).getIp());
    }

    @Test(expected = ParameterException.class)
    public void testGetHostsFilteredByAddress_EXCEPTION() {
        LOGGER.info("testGetHostsFilteredByAddress_EXCEPTION()");
        //wrong ip-address
        nmapConverter.getHosts("-ip", "10.0.0.1,10...3");
    }

    @Test
    public void testGetHostsFilteredByPort() {
        LOGGER.info("testGetHostsFilteredByPort()");
        Collection<Host> hostCollection = nmapConverter.getHosts("-p", "4");
        assertEquals(2, hostCollection.size());
        assertEquals("10.0.0.2", ((List<Host>) hostCollection).get(0).getIp());
        assertEquals(1, ((List<Host>) hostCollection).get(0).getDeviceList().get(0).getServiceList().size());
        assertEquals("10.0.0.3", ((List<Host>) hostCollection).get(1).getIp());
        assertEquals(1, ((List<Host>) hostCollection).get(1).getDeviceList().get(0).getServiceList().size());
    }

    @Test(expected = ParameterException.class)
    public void testGetHostsFilteredByPort_EXCEPTION() {
        LOGGER.info("testGetHostsFilteredByAddress_EXCEPTION()");
        //wrong port
        nmapConverter.getHosts("-p", "abc");
    }

    @Test
    public void testGetHostsFilteredByState() {
        LOGGER.info("testGetHostsFilteredByState()");
        Collection<Host> hostCollection = nmapConverter.getHosts("-s", "closed");
        assertEquals(2, hostCollection.size());
        assertEquals("10.0.0.1", ((List<Host>) hostCollection).get(0).getIp());
        assertEquals(2, ((List<Host>) hostCollection).get(0).getDeviceList().get(0).getServiceList().size());
        assertEquals("10.0.0.2", ((List<Host>) hostCollection).get(1).getIp());
        assertEquals(1, ((List<Host>) hostCollection).get(1).getDeviceList().get(0).getServiceList().size());
    }

    @Test(expected = ParameterException.class)
    public void testGetHostsFilteredByState_EXCEPTION() {
        LOGGER.info("testGetHostsFilteredByState_EXCEPTION()");
        //wrong port
        nmapConverter.getHosts("-s", "abc");
    }

    @Test
    public void testGetHostsFilteredByPortState() {
        LOGGER.info("testGetHostsFilteredByState()");
        Collection<Host> hostCollection = nmapConverter.getHosts("-s", "closed", "-p", "3,4");
        assertEquals(1, hostCollection.size());
        assertEquals("10.0.0.2", ((List<Host>) hostCollection).get(0).getIp());
        assertEquals(1, ((List<Host>) hostCollection).get(0).getDeviceList().get(0).getServiceList().size());
        assertEquals(3, ((List<Host>) hostCollection).get(0).getDeviceList().get(0).getServiceList().get(0).getPort());
    }
}