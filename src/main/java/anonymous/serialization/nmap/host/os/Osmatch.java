package anonymous.serialization.nmap.host.os;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "osmatch")
public class Osmatch {
    @XmlAttribute(name = "line")
    private String line;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "accuracy")
    private String accuracy;
    @XmlElement(name = "osclass")
    private Osclass osclass;

    public Osmatch() {
    }

    public Osmatch(String line, String name, String accuracy, Osclass osclass) {
        this.line = line;
        this.name = name;
        this.accuracy = accuracy;
        this.osclass = osclass;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t")
                .append("<osmatch line=\"")
                .append(line)
                .append("\" name=\"")
                .append(name)
                .append("\" accuracy=\"")
                .append(accuracy)
                .append("\">")
                .append(osclass)
                .append("\n\t\t\t")
                .append("</osmatch>");
        return stringBuilder.toString();
    }
}