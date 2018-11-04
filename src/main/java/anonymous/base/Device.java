package anonymous.base;

import anonymous.serialization.Exclude;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device")
public class Device implements Serializable {
    private static final String DEVICE_ID_TAG = "device_id";
    private static final String JOIN_COLUMN_TAG = "host_id";
    private static final String VENDOR_TAG = "vendor";
    private static final String HARDWARE_TAG = "hardware";
    private static final String SOFTWARE_TAG = "software";

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

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", vendor='" + vendor + '\'' +
                ", hardware='" + hardware + '\'' +
                ", software='" + software + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = prime + Long.valueOf(id).hashCode();
        hash = prime * hash + ((vendor == null) ? 0 : vendor.hashCode());
        hash = prime * hash + ((hardware == null) ? 0 : hardware.hashCode());
        hash = prime * hash + ((software == null) ? 0 : software.hashCode());
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
                && (software == device.getSoftware() || (software != null && software.equals(device.getSoftware())));
    }
}