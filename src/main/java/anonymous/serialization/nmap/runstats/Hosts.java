package anonymous.serialization.nmap.runstats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hosts")
public class Hosts {
    @XmlAttribute(name = "down")
    private String down;
    @XmlAttribute(name = "total")
    private String total;
    @XmlAttribute(name = "up")
    private String up;

    public Hosts() {
    }

    public Hosts(String down, String total, String up) {
        this.down = down;
        this.total = total;
        this.up = up;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<hosts down=\"")
                .append(down)
                .append("\" total=\"")
                .append(total)
                .append("\" up=\"")
                .append(up)
                .append("\"></hosts>");
        return stringBuilder.toString();
    }
}