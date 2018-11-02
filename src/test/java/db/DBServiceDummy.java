package db;

import anonymous.base.Device;
import anonymous.base.Host;
import anonymous.base.DBService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DBServiceDummy implements DBService {

    private static Collection<Host> hosts = new ArrayList<>();

    static {
        Host host1 = new Host(1, "10.0.0.1", "host_1", "login_1", "password_1");
        Host host2 = new Host(2, "10.0.0.2", "host_2", "login_2", "password_2");

        List<Device> host1DeviceList = host1.getDeviceList();
        host1DeviceList.add(new Device(1, host1, "vendor_1", "hardware_1", "software_1"));
        host1DeviceList.add(new Device(3, host1, "vendor_3", "hardware_3", "software_3"));

        List<Device> host2DeviceList = host2.getDeviceList();
        host2DeviceList.add(new Device(2, host2, "vendor_2", "hardware_2", "software_2"));

        hosts.add(host1);
        hosts.add(host2);
    }

    public Collection<Host> getAllHosts() {
        return hosts;
    }

    public long addHost(Host host) {
        hosts.add(host);
        return hosts.size();
    }

    public Host getById(long id) {
        for (Host host : hosts) {
            if (host.getId() == id)
                return host;
        }
        return null;
    }

    public boolean updateHost(Host host) {
        return true;
    }

    public boolean deleteHost(Host host) {
        return true;
    }
}