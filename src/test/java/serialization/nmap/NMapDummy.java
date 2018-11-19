package serialization.nmap;

import anonymous.serialization.nmap.*;
import anonymous.serialization.nmap.host.*;
import anonymous.serialization.nmap.host.os.Os;
import anonymous.serialization.nmap.host.os.Osclass;
import anonymous.serialization.nmap.host.os.Osmatch;
import anonymous.serialization.nmap.host.os.Portused;
import anonymous.serialization.nmap.host.ports.*;
import anonymous.serialization.nmap.runstats.Finished;
import anonymous.serialization.nmap.runstats.Hosts;
import anonymous.serialization.nmap.runstats.Runstats;


import java.util.ArrayList;
import java.util.List;

public class NMapDummy {

    private NMapRun nmapRun;

    public NMapDummy() {
        String start = "";
        String profile_name = "";
        String xmloutputversion = "";
        String scanner = "";
        String version = "";
        String startstr = "";
        String args = "";

        String services = "";
        String protocol = "";
        String numservices = "";
        String type = "";
        Scaninfo scaninfo = new Scaninfo(services, protocol, numservices, type);

        String verboseLevel = "";
        Verbose verbose = new Verbose(verboseLevel);

        String debuggingLevel = "";
        Debugging debugging = new Debugging(debuggingLevel);

        String outputType = "";
        Output output = new Output(outputType);

        String comment = "";

        String state = "";
        Status status = new Status(state);

        String addrtype = "";
        String vendor = "";;
        String addr = "";;
        Address address = new Address(addrtype, vendor, addr);

        String hostnames = "";

        String count = "";
        String extraportsState = "";
        Extraports extraports = new Extraports(count, extraportsState);
        List<Extraports> extraportsList = new ArrayList<>();
        extraportsList.add(extraports);
        String portProtocol = "";
        int portid = -1;
        String reason = "";
        String portStateState = "";
        String reason_ttl = "";
        State portState = new State(reason, portStateState, reason_ttl);

        String product = "";
        String serviceName = "";
        String extrainfo = "";
        String serviceVersion = "";
        String conf = "";
        String method = "";
        Service service = new Service(product, serviceName, extrainfo, serviceVersion, conf, method);
        Port port = new Port(portProtocol, portid, portState, service);
        List<Port> portList = new ArrayList<>();
        portList.add(port);
        Ports ports = new Ports(extraportsList, portList);

        String portusedState = "";
        String portusedPortid = "";
        String portusedProto = "";
        Portused portused = new Portused(portusedState, portusedPortid, portusedProto);
        List<Portused> portusedList = new ArrayList<>();
        portusedList.add(portused);
        String line = "";
        String name = "";
        String osmatchAccuracy = "";
        String osclassType = "";
        String osfamily = "";
        String osclassVendor = "";
        String osgen = "";
        String osclassAccuracy = "";
        Osclass osclass = new Osclass(osclassType, osfamily, osclassVendor, osgen, osclassAccuracy);
        Osmatch osmatch = new Osmatch(line, name, osmatchAccuracy, osclass);
        List<Osmatch> osmatchList = new ArrayList<>();
        osmatchList.add(osmatch);
        Os os = new Os(portusedList, osmatchList);

        String lastboot = "";
        String seconds = "";
        Uptime uptime = new Uptime(lastboot, seconds);

        String index = "";
        String tcpsequenceValues = "";
        String difficulty = "";
        Tcpsequence tcpsequence = new Tcpsequence(index, tcpsequenceValues, difficulty);

        String IpidsequenceValues = "";
        String ipidsequenceClazz = "";
        Ipidsequence ipidsequence = new Ipidsequence(IpidsequenceValues, ipidsequenceClazz);

        String tcptssequenceValues = "";
        String tcptssequenceClazz = "";
        Tcptssequence tcptssequence = new Tcptssequence(tcptssequenceValues, tcptssequenceClazz);

        String tracePort = "";
        String traceProto = "";
        String rtt = "";
        String hopHost = "";
        String ipaddr = "";
        String ttl = "";
        Hop hop = new Hop(rtt, hopHost, ipaddr, ttl);
        List<Hop> hopList = new ArrayList<>();
        hopList.add(hop);
        Trace trace = new Trace(tracePort, traceProto, hopList);

        Host host = new Host(comment, status, address, hostnames, ports, os, uptime, tcpsequence, ipidsequence, tcptssequence, trace);
        List<Host> hostList = new ArrayList<>();
        hostList.add(host);

        String timestr = "";
        String time = "";
        Finished finished = new Finished(timestr, time);
        String down = "";
        String total = "";
        String up = "";
        Hosts hosts = new Hosts(down, total, up);
        Runstats runstats = new Runstats(finished, hosts);

        nmapRun = new NMapRun(
                start,
                profile_name,
                xmloutputversion,
                scanner,
                version,
                startstr,
                args,
                scaninfo,
                verbose,
                debugging,
                output,
                hostList,
                runstats
        );
    }

    public NMapRun getNmapRun() {
        return nmapRun;
    }
}