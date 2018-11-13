package anonymous.serialization.nmap.runstats;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "runstats")
public class Runstats {
    @XmlElement(name = "finished")
    private Finished finished;
    @XmlElement(name = "hosts")
    private Hosts hosts;

    public Runstats() {
    }

    public Runstats(Finished finished, Hosts hosts) {
        this.finished = finished;
        this.hosts = hosts;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<runstats>")
                .append(finished)
                .append(hosts)
                .append("\n\t")
                .append("</runstats>");
        return stringBuilder.toString();
    }
}