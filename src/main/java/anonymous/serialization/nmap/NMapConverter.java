package anonymous.serialization.nmap;

import anonymous.base.Device;
import anonymous.base.Host;
import anonymous.base.Service;
import anonymous.serialization.nmap.converter.NMapConverterArgs;
import anonymous.serialization.nmap.host.ports.Port;
import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NMapConverter {
    private NMapRun nmapRun;

    public NMapConverter(NMapRun nmapRun) {
        this.nmapRun = nmapRun;
    }

    private Collection<Host> convertHosts(List<anonymous.serialization.nmap.host.Host> hostList) {
        Collection<Host> result = new ArrayList<>();
        for (anonymous.serialization.nmap.host.Host nmapHost : hostList) {
            Host host = new Host();
            host.setIp(nmapHost.getAddress().getAddr());
            if (!nmapHost.getPorts().getPortList().isEmpty()) {
                List<Device> deviceList = new ArrayList<>();
                Device device = new Device();
                List<Service> serviceList = new ArrayList<>();
                for (Port port : nmapHost.getPorts().getPortList()) {
                    Service service = new Service();
                    service.setDevice(device);
                    service.setPort(port.getPortid());
                    service.setName(port.getService().getName());
                    serviceList.add(service);
                }
                device.setServiceList(serviceList);
                deviceList.add(device);
                host.setDeviceList(deviceList);
            }
            result.add(host);
        }
        return result;
    }

    public Collection<Host> getHosts(String ... args) {
        NMapConverterArgs converterArgs = new NMapConverterArgs();
        JCommander
                .newBuilder()
                .addObject(converterArgs)
                .build()
                .parse(args);

        List<anonymous.serialization.nmap.host.Host> hostList = nmapRun.getHostList();

        //filter by ip-address
        List<anonymous.serialization.nmap.host.Host> hostAddressList;
        if (converterArgs.getAddress().isEmpty())
            hostAddressList = hostList;
        else {
            hostAddressList = new ArrayList<>();
            for (String address : converterArgs.getAddress())
                for (anonymous.serialization.nmap.host.Host host : hostList)
                    if (address.equals(host.getAddress().getAddr()) && ! hostAddressList.contains(host))
                        hostAddressList.add(host);
        }

        //filter by port
        List<anonymous.serialization.nmap.host.Host> hostPortList = filterByPortState(hostAddressList, converterArgs.getPort());

        //filter by state
        List<anonymous.serialization.nmap.host.Host> hostStateList = filterByPortState(hostPortList, converterArgs.getState());

        return convertHosts(hostStateList);
    }

    private List<anonymous.serialization.nmap.host.Host> filterByPortState(List<anonymous.serialization.nmap.host.Host> hostList, List filterList) {
        if (filterList.isEmpty())
            return hostList;
        else {
            List<anonymous.serialization.nmap.host.Host> result = new ArrayList<>();
            for (anonymous.serialization.nmap.host.Host host : hostList) {
                for (Port port : new ArrayList<>(host.getPorts().getPortList()))
                    if ((filterList.get(0) instanceof Integer && !((List<Integer>) filterList).contains(port.getPortid()))
                            || (filterList.get(0) instanceof String && !((List<String>) filterList).contains(port.getState().getState()))
                    )
                        host.getPorts().getPortList().remove(port);
                if (!host.getPorts().getPortList().isEmpty() && !result.contains(host))
                    result.add(host);
            }
            return result;
        }
    }
}