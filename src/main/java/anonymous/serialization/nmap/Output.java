package anonymous.serialization.nmap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "output")
public class Output {
    @XmlAttribute(name = "type")
    private String type;

    public Output() {
    }

    public Output(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<output type=\"")
                .append(type)
                .append("\"></output>");
        return stringBuilder.toString();
    }
}