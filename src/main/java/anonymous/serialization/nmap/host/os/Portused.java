package anonymous.serialization.nmap.host.os;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "portused")
public class Portused {
    @XmlAttribute(name = "state")
    private String state;
    @XmlAttribute(name = "portid")
    private String portid;
    @XmlAttribute(name = "proto")
    private String proto;

    public Portused() {
    }

    public Portused(String state, String portid, String proto) {
        this.state = state;
        this.portid = portid;
        this.proto = proto;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t")
                .append("<portused state=\"")
                .append(state)
                .append("\" portid=\"")
                .append(portid)
                .append("\" proto=\"")
                .append(proto)
                .append("\"></portused>");
        return stringBuilder.toString();
    }
}