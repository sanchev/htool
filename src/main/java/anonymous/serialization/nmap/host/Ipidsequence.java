package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ipidsequence")
public class Ipidsequence {
    @XmlAttribute(name = "values")
    private String values;
    @XmlAttribute(name = "class")
    private String clazz;

    public Ipidsequence() {
    }

    public Ipidsequence(String values, String clazz) {
        this.values = values;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<ipidsequence values=\"")
                .append(values)
                .append("\" class=\"")
                .append(clazz)
                .append("\"></ipidsequence>");
        return stringBuilder.toString();
    }
}