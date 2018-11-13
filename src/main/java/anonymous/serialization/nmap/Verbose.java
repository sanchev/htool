package anonymous.serialization.nmap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verbose")
public class Verbose {
    @XmlAttribute(name = "level")
    private String level;

    public Verbose() {
    }

    public Verbose(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<verbose level=\"")
                .append(level)
                .append("\"></verbose>");
        return stringBuilder.toString();
    }
}