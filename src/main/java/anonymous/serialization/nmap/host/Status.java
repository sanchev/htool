package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "status")
public class Status {
    @XmlAttribute(name = "state")
    private String state;

    public Status() {
    }

    public Status(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<status state=\"")
                .append(state)
                .append("\"></status>");
        return stringBuilder.toString();
    }
}