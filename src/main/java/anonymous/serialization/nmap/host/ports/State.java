package anonymous.serialization.nmap.host.ports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "state")
public class State {
    @XmlAttribute(name = "reason")
    private String reason;
    @XmlAttribute(name = "state")
    private String state;
    @XmlAttribute(name = "reason_ttl")
    private String reason_ttl;

    public State() {
    }

    public State(String reason, String state, String reason_ttl) {
        this.reason = reason;
        this.state = state;
        this.reason_ttl = reason_ttl;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t\t")
                .append("<state reason=\"")
                .append(reason)
                .append("\" state=\"")
                .append(state)
                .append("\" reason_ttl=\"")
                .append(reason_ttl)
                .append("\"></state>");
        return stringBuilder.toString();
    }
}