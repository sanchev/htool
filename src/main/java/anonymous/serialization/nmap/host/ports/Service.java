package anonymous.serialization.nmap.host.ports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "service")
public class Service {
    @XmlAttribute(name = "product")
    private String product;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "extrainfo")
    private String extrainfo;
    @XmlAttribute(name = "version")
    private String version;
    @XmlAttribute(name = "conf")
    private String conf;
    @XmlAttribute(name = "method")
    private String method;

    public Service() {
    }

    public Service(String product, String name, String extrainfo, String version, String conf, String method) {
        this.product = product;
        this.name = name;
        this.extrainfo = extrainfo;
        this.version = version;
        this.conf = conf;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t\t\t")
                .append("<service product=\"")
                .append(product)
                .append("\" name=\"")
                .append(name)
                .append("\" extrainfo=\"")
                .append(extrainfo)
                .append("\" version=\"")
                .append(version)
                .append("\" conf=\"")
                .append(conf)
                .append("\" method=\"")
                .append(method)
                .append("\"></service>");
        return stringBuilder.toString();
    }
}