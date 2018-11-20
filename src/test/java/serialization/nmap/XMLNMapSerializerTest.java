package serialization.nmap;

import anonymous.serialization.nmap.XMLNMapSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class XMLNMapSerializerTest {

    private static final Logger LOGGER = LogManager.getLogger(XMLNMapSerializerTest.class.getName());

    @Test
    public void testUnserialize_from_File() throws IOException {
        LOGGER.info("testUnserialize_from_File()");
        String filePath = "src/test/resources/nmap-test.xml";
        String actual = Objects.requireNonNull(XMLNMapSerializer.unserialize(new File(filePath))).toString();

        String expected = new String(Files.readAllBytes(Paths.get(filePath)));

        assertEquals(expected.substring(expected.indexOf('\n') + 1), actual);
    }
}