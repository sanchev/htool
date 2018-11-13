package anonymous.serialization.nmap.host.os;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "os")
public class Os {
    @XmlElement(name = "portused")
    private List<Portused> portusedList;
    @XmlElement(name = "osmatch")
    private List<Osmatch> osmatchList;

    public Os() {
        this.portusedList = new ArrayList<>();
        this.osmatchList = new ArrayList<>();
    }

    public Os(List<Portused> portusedList, List<Osmatch> osmatchList) {
        this.portusedList = portusedList;
        this.osmatchList = osmatchList;
    }

    public void setPortusedList(List<Portused> portusedList) {
        this.portusedList = portusedList;
    }

    public void setOsmatchList(List<Osmatch> osmatchList) {
        this.osmatchList = osmatchList;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<os>");
        for (Portused portused : portusedList)
            stringBuilder.append(portused);
        for (Osmatch osmatch : osmatchList)
            stringBuilder.append(osmatch);
        stringBuilder
                .append("\n\t\t")
                .append("</os>");
        return stringBuilder.toString();
    }
}