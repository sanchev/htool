package anonymous.serialization.nmap;;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "scaninfo")
public class Scaninfo {
    @XmlAttribute(name = "services")
    private String services;
    @XmlAttribute(name = "protocol")
    private String protocol;
    @XmlAttribute(name = "numservices")
    private String numservices;
    @XmlAttribute(name = "type")
    private String type;

    public Scaninfo() {
    }

    public Scaninfo(String services, String protocol, String numservices, String type) {
        this.services = services;
        this.protocol = protocol;
        this.numservices = numservices;
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<scaninfo services=\"")
                .append(services)
                .append("\" protocol=\"")
                .append(protocol)
                .append("\" numservices=\"")
                .append(numservices)
                .append("\" type=\"")
                .append(type)
                .append("\"></scaninfo>");
        return stringBuilder.toString();
    }
}