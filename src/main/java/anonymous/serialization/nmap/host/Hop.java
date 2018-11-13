package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hop")
public class Hop {
    @XmlAttribute(name = "rtt")
    private String rtt;
    @XmlAttribute(name = "host")
    private String host;
    @XmlAttribute(name = "ipaddr")
    private String ipaddr;
    @XmlAttribute(name = "ttl")
    private String ttl;

    public Hop() {
    }

    public Hop(String rtt, String host, String ipaddr, String ttl) {
        this.rtt = rtt;
        this.host = host;
        this.ipaddr = ipaddr;
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t")
                .append("<hop rtt=\"")
                .append(rtt)
                .append("\" host=\"")
                .append(host)
                .append("\" ipaddr=\"")
                .append(ipaddr)
                .append("\" ttl=\"")
                .append(ttl)
                .append("\"></hop>");
        return stringBuilder.toString();
    }
}