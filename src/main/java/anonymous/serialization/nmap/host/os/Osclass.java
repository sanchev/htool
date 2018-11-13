package anonymous.serialization.nmap.host.os;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "osclass")
public class Osclass {
    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "osfamily")
    private String osfamily;
    @XmlAttribute(name = "vendor")
    private String vendor;
    @XmlAttribute(name = "osgen")
    private String osgen;
    @XmlAttribute(name = "accuracy")
    private String accuracy;

    public Osclass() {
    }

    public Osclass(String type, String osfamily, String vendor, String osgen, String accuracy) {
        this.type = type;
        this.osfamily = osfamily;
        this.vendor = vendor;
        this.osgen = osgen;
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t\t")
                .append("<osclass type=\"")
                .append(type)
                .append("\" osfamily=\"")
                .append(osfamily)
                .append("\" vendor=\"")
                .append(vendor)
                .append("\" osgen=\"")
                .append(osgen)
                .append("\" accuracy=\"")
                .append(accuracy)
                .append("\"></osclass>");
        return stringBuilder.toString();
    }
}