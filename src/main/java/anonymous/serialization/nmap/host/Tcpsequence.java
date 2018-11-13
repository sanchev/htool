package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tcpsequence")
public class Tcpsequence {
    @XmlAttribute(name = "index")
    private String index;
    @XmlAttribute(name = "values")
    private String values;
    @XmlAttribute(name = "difficulty")
    private String difficulty;

    public Tcpsequence() {
    }

    public Tcpsequence(String index, String values, String difficulty) {
        this.index = index;
        this.values = values;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<tcpsequence index=\"")
                .append(index)
                .append("\" values=\"")
                .append(values)
                .append("\" difficulty=\"")
                .append(difficulty)
                .append("\"></tcpsequence>");
        return stringBuilder.toString();
    }
}