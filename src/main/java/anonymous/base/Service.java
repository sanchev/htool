package anonymous.base;

import anonymous.serialization.Exclude;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "service")
public class Service {
    private static final String SERVICE_ID_TAG = "service_id";
    private static final String JOIN_COLUMN_TAG = "device_id";
    private static final String PORT_TAG = "port";
    private static final String NAME_TAG = "name";

    @SerializedName(SERVICE_ID_TAG)
    @Id
    @Column(name = SERVICE_ID_TAG)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = JOIN_COLUMN_TAG, nullable = false)
    private Device device;

    @SerializedName(PORT_TAG)
    @Column(name = PORT_TAG)
    private String port;

    @SerializedName(NAME_TAG)
    @Column(name = NAME_TAG)
    private String name;

    public Service() {
    }

    public Service(long id, Device device, String port, String name) {
        this.id = id;
        this.device = device;
        this.port = port;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", port='" + port + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = prime + Long.valueOf(id).hashCode();
        hash = prime * hash + ((port == null) ? 0 : port.hashCode());
        hash = prime * hash + ((name == null) ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Service service = (Service) obj;

        return id == service.getId()
                && (port == service.getPort() || (port != null && port.equals(service.getPort())))
                && (name == service.getName() || (name != null && name.equals(service.getName())));
    }
}