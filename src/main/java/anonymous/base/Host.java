package anonymous.base;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "host")
public class Host implements Serializable {
    private static final String HOST_ID_TAG = "host_id";
    private static final String IP_TAG = "ip";
    private static final String IDENTITY_TAG = "identity";
    private static final String DEVICE_LIST_TAG = "deviceList";
    private static final String LOGIN_TAG = "login";
    private static final String PASSWORD_TAG = "password";

    @SerializedName(HOST_ID_TAG)
    @Id
    @Column(name = HOST_ID_TAG)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @SerializedName(IP_TAG)
    @Column(name = IP_TAG)
    private String ip;

    @SerializedName(IDENTITY_TAG)
    @Column(name = IDENTITY_TAG)
    private String identity;


    @SerializedName(DEVICE_LIST_TAG)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "host", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Device> deviceList = new ArrayList<>();

    @SerializedName(LOGIN_TAG)
    @Column(name = LOGIN_TAG)
    private String login;

    @SerializedName(PASSWORD_TAG)
    @Column(name = PASSWORD_TAG)
    private String password;

    public Host() {
        this.setId(-1);
    }

    public Host(long id, String ip, String identity, String login, String password) {
        this.setId(id);
        this.setIp(ip);
        this.setIdentity(identity);
        this.setLogin(login);
        this.setPassword(password);
    }

    @SuppressWarnings("UnusedDeclaration")
    public long getId() {
        return id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setId(long id) {
        this.id = id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getIdentity() {
        return identity;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIp() {
        return ip;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<Device> getDeviceList() {
        return deviceList;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getLogin() {
        return login;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setLogin(String login) {
        this.login = login;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", identity='" + identity + '\'' +
                ", deviceList=" + deviceList +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = prime + Long.valueOf(id).hashCode();
        hash = prime * hash + ((ip == null) ? 0 : ip.hashCode());
        hash = prime * hash + ((identity == null) ? 0 : identity.hashCode());
        if (deviceList != null)
            for (Device device : deviceList)
                hash = prime * hash + (device == null ? 0 : device.hashCode());
        hash = prime * hash + ((login == null) ? 0 : login.hashCode());
        hash = prime * hash + ((password == null) ? 0 : password.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Host host = (Host) obj;
        return id == host.getId()
                && (ip == host.getIp() || (ip != null && ip.equals(host.getIp())))
                && (identity == host.getIdentity() || (identity != null && identity.equals(host.getIdentity())))
                && deviceListEquals(host.getDeviceList())
                && (login == host.getLogin() || (login != null && login.equals(host.getLogin())))
                && (password == host.getPassword() || (password != null && password.equals(host.getPassword())));
    }

    private boolean deviceListEquals(List<Device> devices) {
        if (deviceList == devices)
            return true;

        Iterator<Device> deviceIterator1 = deviceList.iterator();
        Iterator<Device> deviceIterator2 = devices.iterator();

        while (deviceIterator1.hasNext() && deviceIterator2.hasNext()) {
            Device device1 = deviceIterator1.next();
            Device device2 = deviceIterator2.next();

            if (!(device1 == null ? device2 == null : device1.equals(device2)))
                return false;
        }

        return !(deviceIterator1.hasNext() || deviceIterator2.hasNext());
    }
}