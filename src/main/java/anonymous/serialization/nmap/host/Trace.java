package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "trace")
public class Trace {
    @XmlAttribute(name = "port")
    private String port;
    @XmlAttribute(name = "proto")
    private String proto;
    @XmlElement(name = "hop")
    private List<Hop> hopList;

    public Trace() {
        hopList = new ArrayList<>();
    }

    public Trace(String port, String proto, List<Hop> hopList) {
        this.port = port;
        this.proto = proto;
        this.hopList = hopList;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<trace port=\"")
                .append(port)
                .append("\" proto=\"")
                .append(proto)
                .append("\">");
        for (Hop hop : hopList)
            stringBuilder.append(hop);
        stringBuilder
                .append("\n\t\t")
                .append("</trace>");
        return stringBuilder.toString();
    }
}