package anonymous.serialization.nmap.host;

import anonymous.serialization.nmap.host.os.Os;
import anonymous.serialization.nmap.host.ports.Ports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "host")
public class Host {
    @XmlAttribute(name = "comment")
    private String comment;
    @XmlElement(name = "status")
    private Status status;
    @XmlElement(name = "address")
    private Address address;
    @XmlElement(name = "hostnames")
    private String hostnames;
    @XmlElement(name = "ports")
    private Ports ports;
    @XmlElement(name = "os")
    private Os os;
    @XmlElement(name = "uptime")
    private Uptime uptime;
    @XmlElement(name = "tcpsequence")
    private Tcpsequence tcpsequence;
    @XmlElement(name = "ipidsequence")
    private Ipidsequence ipidsequence;
    @XmlElement(name = "tcptssequence")
    private Tcptssequence tcptssequence;
    @XmlElement(name = "trace")
    private Trace trace;

    public Host() {
    }

    public Host(
            String comment,
            Status status,
            Address address,
            String hostnames,
            Ports ports,
            Os os,
            Uptime uptime,
            Tcpsequence tcpsequence,
            Ipidsequence ipidsequence,
            Tcptssequence tcptssequence,
            Trace trace
    ) {
        this.comment = comment;
        this.status = status;
        this.address = address;
        this.hostnames = hostnames;
        this.ports = ports;
        this.os = os;
        this.uptime = uptime;
        this.tcpsequence = tcpsequence;
        this.ipidsequence = ipidsequence;
        this.tcptssequence = tcptssequence;
        this.trace = trace;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\t")
                .append("<host comment=\"")
                .append(comment)
                .append("\">")
                .append(status)
                .append(address)
                .append("\n\t\t<hostnames>")
                .append(hostnames)
                .append("</hostnames>")
                .append(ports)
                .append(os)
                .append(uptime)
                .append(tcpsequence)
                .append(ipidsequence)
                .append(tcptssequence)
                .append(trace)
                .append("\n\t")
                .append("</host>");
        return stringBuilder.toString();
    }
}