package anonymous.serialization.nmap.host.ports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "port")
public class Port {
    @XmlAttribute(name = "protocol")
    private String protocol;
    @XmlAttribute(name = "portid")
    private int portid;
    @XmlElement(name = "state")
    private State state;
    @XmlElement(name = "service")
    private Service service;

    public Port() {
    }

    public Port(String protocol, int portid, State state, Service service) {
        this.protocol = protocol;
        this.portid = portid;
        this.state = state;
        this.service = service;
    }

    public int getPortid() {
        return portid;
    }

    public State getState() {
        return state;
    }

    public Service getService() {
        return service;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t")
                .append("<port")
                .append("protocol=\"")
                .append(protocol)
                .append("\" portid=\"")
                .append(portid)
                .append("\">")
                .append(state)
                .append(service)
                .append("\n\t\t\t")
                .append("</port>");
        return stringBuilder.toString();
    }
}