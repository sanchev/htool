package anonymous.serialization.nmap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "debugging")
public class Debugging {
    @XmlAttribute(name = "level")
    private String level;

    public Debugging() {
    }

    public Debugging(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<debugging level=\"")
                .append(level)
                .append("\"></debugging>");
        return stringBuilder.toString();
    }
}