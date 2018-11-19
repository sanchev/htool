package anonymous.base;

import anonymous.serialization.Exclude;
import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "device")
public class Device implements Serializable {
    private static final String DEVICE_ID_TAG = "device_id";
    private static final String JOIN_COLUMN_TAG = "host_id";
    private static final String VENDOR_TAG = "vendor";
    private static final String HARDWARE_TAG = "hardware";
    private static final String SOFTWARE_TAG = "software";
    private static final String SERVICE_LIST_TAG = "serviceList";

    @SerializedName(DEVICE_ID_TAG)
    @Id
    @Column(name = DEVICE_ID_TAG)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = JOIN_COLUMN_TAG, nullable = false)
    private Host host;

    @SerializedName(VENDOR_TAG)
    @Column(name = VENDOR_TAG)
    private String vendor;

    @SerializedName(HARDWARE_TAG)
    @Column(name = HARDWARE_TAG)
    private String hardware;

    @SerializedName(SOFTWARE_TAG)
    @Column(name = SOFTWARE_TAG)
    private String software;

    @SerializedName(SERVICE_LIST_TAG)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Service> serviceList = new ArrayList<>();

    public Device() {
    }

    public Device(long id, Host host, String vendor, String hardware, String software) {
        this.id = id;
        this.host = host;
        this.vendor = vendor;
        this.hardware = hardware;
        this.software = software;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", vendor='" + vendor + '\'' +
                ", hardware='" + hardware + '\'' +
                ", software='" + software + '\'' +
                ", serviceList=" + serviceList +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = prime + Long.valueOf(id).hashCode();
        hash = prime * hash + ((vendor == null) ? 0 : vendor.hashCode());
        hash = prime * hash + ((hardware == null) ? 0 : hardware.hashCode());
        hash = prime * hash + ((software == null) ? 0 : software.hashCode());
        if (serviceList != null)
            for (Service service : serviceList)
                hash = prime * hash + (service == null ? 0 : service.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Device device = (Device) obj;
        return id == device.getId()
                && (vendor == device.getVendor() || (vendor != null && vendor.equals(device.getVendor())))
                && (hardware == device.getHardware() || (hardware != null && hardware.equals(device.getHardware())))
                && (software == device.getSoftware() || (software != null && software.equals(device.getSoftware())))
                && serviceListEquals(device.getServiceList());
    }

    private boolean serviceListEquals(List<Service> services) {
        if (serviceList == services)
            return true;

        Iterator<Service> serviceIterator1 = serviceList.iterator();
        Iterator<Service> serviceIterator2 = services.iterator();

        while (serviceIterator1.hasNext() && serviceIterator2.hasNext()) {
            Service service1 = serviceIterator1.next();
            Service service2 = serviceIterator2.next();

            if (!(service1 == null ? service2 == null : service1.equals(service2)))
                return false;
        }

        return !(serviceIterator1.hasNext() || serviceIterator2.hasNext());
    }
}