package anonymous.serialization.nmap.converter;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class NMapConverterArgs {
    @Parameter(names = {"-address", "-ip"}, validateValueWith = AddressValidator.class, description = "Comma-separated list of ip-addresses")
    private List<String> address = new ArrayList<>();

    @Parameter(names = {"-port", "-p"}, validateValueWith = PortValidator.class, description = "Comma-separated list of ports")
    private List<Integer> port = new ArrayList<>();

    @Parameter(names = {"-state" , "-s"}, validateValueWith = StateValidator.class, description = "Comma-separated list of port states ")
    private List<String> state = new ArrayList<>();

    public List<String> getAddress() {
        return address;
    }

    public List<Integer> getPort() {
        return port;
    }

    public List<String> getState() {
        return state;
    }
}