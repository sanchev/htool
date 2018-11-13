package anonymous.serialization.nmap.runstats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "finished")
public class Finished {
    @XmlAttribute(name = "timestr")
    private String timestr;
    @XmlAttribute(name = "time")
    private String time;

    public Finished() {
    }

    public Finished(String timestr, String time) {
        this.timestr = timestr;
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<finished timestr=\"")
                .append(timestr)
                .append("\" time=\"")
                .append(time)
                .append("\"></finished>");
        return stringBuilder.toString();
    }
}
