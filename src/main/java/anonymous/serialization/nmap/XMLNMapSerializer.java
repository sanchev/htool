package anonymous.serialization.nmap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLNMapSerializer {
    private static final Logger LOGGER = LogManager.getLogger(XMLNMapSerializer.class.getName());

    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NMapRun.class);
            marshaller = jaxbContext.createMarshaller();
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    private static void serialize(NMapRun nmapRun, File file) throws JAXBException {
        marshaller.marshal(nmapRun, file);
    }

    @SuppressWarnings("unused")
    private static void serialize(NMapRun nmapRun, Writer writer) throws JAXBException {
        marshaller.marshal(nmapRun, writer);
    }

    @SuppressWarnings("unused")
    private static void serialize(NMapRun nmapRun, OutputStream outputStream) throws JAXBException {
        marshaller.marshal(nmapRun, outputStream);
    }

    public static NMapRun unserialize(File file) {
        try {
            return  (NMapRun) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unused")
    private static NMapRun unserialize(InputStream inputStream) throws JAXBException {
        return  (NMapRun) unmarshaller.unmarshal(inputStream);
    }

    @SuppressWarnings("unused")
    private static NMapRun unserialize(Reader reader) throws JAXBException {
        return  (NMapRun) unmarshaller.unmarshal(reader);
    }
}