package anonymous.serialization.nmap;

import anonymous.serialization.nmap.host.Host;
import anonymous.serialization.nmap.runstats.Runstats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "nmaprun")
public class NMapRun {
    @XmlAttribute(name = "start")
    private String start;
    @XmlAttribute(name = "profile_name")
    private String profile_name;
    @XmlAttribute(name = "xmloutputversion")
    private String xmloutputversion;
    @XmlAttribute(name = "scanner")
    private String scanner;
    @XmlAttribute(name = "version")
    private String version;
    @XmlAttribute(name = "startstr")
    private String startstr;
    @XmlAttribute(name = "args")
    private String args;
    @XmlElement(name = "scaninfo")
    private Scaninfo scaninfo;
    @XmlElement(name = "verbose")
    private Verbose verbose;
    @XmlElement(name = "debugging")
    private Debugging debugging;
    @XmlElement(name = "output")
    private Output output;
    @XmlElement(name = "host")
    private List<Host> hostList;
    @XmlElement(name = "runstats")
    private Runstats runstats;

    public NMapRun() {
        hostList = new ArrayList<>();
    }

    public NMapRun(
            String start,
            String profile_name,
            String xmloutputversion,
            String scanner,
            String version,
            String startstr,
            String args,
            Scaninfo scaninfo,
            Verbose verbose,
            Debugging debugging,
            Output output,
            List<Host> hostList,
            Runstats runstats
    ) {
        this.start = start;
        this.profile_name = profile_name;
        this.xmloutputversion = xmloutputversion;
        this.scanner = scanner;
        this.version = version;
        this.startstr = startstr;
        this.args = args;
        this.scaninfo = scaninfo;
        this.verbose = verbose;
        this.debugging = debugging;
        this.output = output;
        this.hostList = hostList;
        this.runstats = runstats;
    }

    public List<Host> getHostList() {
        return hostList;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<nmaprun start=\"")
                .append(start)
                .append("\" profile_name=\"")
                .append(profile_name)
                .append("\" xmloutputversion=\"")
                .append(xmloutputversion)
                .append("\" scanner=\"")
                .append(scanner)
                .append("\" version=\"")
                .append(version)
                .append("\" startstr=\"")
                .append(startstr)
                .append("\" args=\"")
                .append(args)
                .append("\">")
                .append(scaninfo)
                .append(verbose)
                .append(debugging)
                .append(output);
        for (Host host : hostList)
            stringBuilder.append(host);
        stringBuilder
                .append(runstats)
                .append("\n")
                .append("</nmaprun>");
        return stringBuilder.toString();
    }
}