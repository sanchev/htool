package anonymous.serialization.nmap.host;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
public class Address {
    @XmlAttribute(name = "addrtype")
    private String addrtype;
    @XmlAttribute(name = "vendor")
    private String vendor;
    @XmlAttribute(name = "addr")
    private String addr;

    public Address() {
    }

    public Address(String addrtype, String vendor, String addr) {
        this.addrtype = addrtype;
        this.vendor = vendor;
        this.addr = addr;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t\t")
                .append("<address addrtype=\"")
                .append(addrtype)
                .append("\" vendor=\"")
                .append(vendor)
                .append("\" addr=\"")
                .append(addr)
                .append("\"></address>");
        return stringBuilder.toString();
    }
}