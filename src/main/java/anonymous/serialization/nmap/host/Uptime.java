package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "uptime")
public class Uptime {
    @XmlAttribute(name = "lastboot")
    private String lastboot;
    @XmlAttribute(name = "seconds")
    private String seconds;

    public Uptime() {
    }

    public Uptime(String lastboot, String seconds) {
        this.lastboot = lastboot;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<uptime lastboot=\"")
                .append(lastboot)
                .append("\" seconds=\"")
                .append(seconds)
                .append("\"></uptime>");
        return stringBuilder.toString();
    }
}