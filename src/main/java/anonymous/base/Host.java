package anonymous.base;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hosts")
public class Host implements Serializable {
    private static final String ID_TAG = "id";
    private static final String IP_TAG = "ip";
    private static final String IDENTITY_TAG = "identity";
    private static final String DEVICE_TAG = "device";
    private static final String LOGIN_TAG = "login";
    private static final String PASSWORD_TAG = "password";

    @SerializedName(ID_TAG)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @SerializedName(IP_TAG)
    @Column(name = "ip")
    private String ip;

    @SerializedName(IDENTITY_TAG)
    @Column(name = "identity")
    private String identity;


    @SerializedName(DEVICE_TAG)
    @Column(name = "device")
    private String device;

    @SerializedName(LOGIN_TAG)
    @Column(name = "login")
    private String login;

    @SerializedName(PASSWORD_TAG)
    @Column(name = "password")
    private String password;

    public Host() {
        this.setId(-1);
    }

    public Host(long id, String ip, String identity, String device, String login, String password) {
        this.setId(id);
        this.setIp(ip);
        this.setIdentity(identity);
        this.setDevice(device);
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
    public String getDevice() {
        return device;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setDevice(String device) {
        this.device = device;
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
        return String.format("id: %d\tip: %s\tdevice: %s\tlogin: %s\tpassword: %s", id, ip, device, login, password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = prime + Long.valueOf(id).hashCode();
        hash = prime * hash + ((ip == null) ? 0 : ip.hashCode());
        hash = prime * hash + ((identity == null) ? 0 : identity.hashCode());
        hash = prime * hash + ((device == null) ? 0 : device.hashCode());
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
                && (device == host.getDevice() || (device != null && device.equals(host.getDevice())))
                && (login == host.getLogin() || (login != null && login.equals(host.getLogin())))
                && (password == host.getPassword() || (password != null && password.equals(host.getPassword())));
    }
}