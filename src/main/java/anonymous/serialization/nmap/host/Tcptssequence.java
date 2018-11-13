package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tcptssequence")
public class Tcptssequence {
    @XmlAttribute(name = "values")
    private String values;
    @XmlAttribute(name = "class")
    private String clazz;

    public Tcptssequence() {
    }

    public Tcptssequence(String values, String clazz) {
        this.values = values;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<tcptssequence values=\"")
                .append(values)
                .append("\" class=\"")
                .append(clazz)
                .append("\"></tcptssequence>");
        return stringBuilder.toString();
    }
}