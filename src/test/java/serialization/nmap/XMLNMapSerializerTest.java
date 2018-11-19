package serialization.nmap;

import anonymous.serialization.nmap.NMapRun;
import anonymous.serialization.nmap.XMLNMapSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class XMLNMapSerializerTest {

    private static final Logger LOGGER = LogManager.getLogger(XMLNMapSerializerTest.class.getName());

    @Test
    public void testUnserialize_from_File() {
        LOGGER.info("testUnserialize_from_File()");
        String filePath = "src/test/resources/nmap-test.xml";
        File file = new File(filePath);
        NMapRun nmapRun = XMLNMapSerializer.unserialize(file);

        NMapDummy nmapDummy = new NMapDummy();
        NMapRun expectedNMapRun = nmapDummy.getNmapRun();

        LOGGER.info("Loaded nmap:\n" + nmapRun.toString());
        LOGGER.info("Expected nmap:\n" + expectedNMapRun.toString());

        assertTrue(expectedNMapRun.toString().equals(nmapRun.toString()));
    }
}