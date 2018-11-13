package anonymous.serialization.nmap.host.ports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "extraports")
public class Extraports {
    @XmlAttribute(name = "count")
    private String count;
    @XmlAttribute(name = "state")
    private String state;

    public Extraports() {
    }

    public Extraports(String count, String state) {
        this.count = count;
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t")
                .append("<extraports count=\"")
                .append(count)
                .append("\" state=\"")
                .append(state)
                .append("\"></extraports>");
        return stringBuilder.toString();
    }
}