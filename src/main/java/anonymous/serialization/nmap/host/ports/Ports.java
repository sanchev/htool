package anonymous.serialization.nmap.host.ports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ports")
public class Ports {
    @XmlElement(name = "extraports")
    private List<Extraports> extraportsList;
    @XmlElement(name = "port")
    private List<Port> portList;

    public Ports() {
        this.extraportsList = new ArrayList<>();
        this.portList = new ArrayList<>();
    }

    public Ports(List<Extraports> extraportsList, List<Port> portList) {
        this.extraportsList = extraportsList;
        this.portList = portList;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<ports>");
        for (Extraports extraports : extraportsList)
            stringBuilder.append(extraports);
        for (Port port : portList)
            stringBuilder.append(port);
        stringBuilder
                .append("\n\t\t")
                .append("</ports>");
        return stringBuilder.toString();
    }
}