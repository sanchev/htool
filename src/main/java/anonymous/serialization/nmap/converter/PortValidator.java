package anonymous.serialization.nmap.converter;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.util.List;

public class PortValidator implements IValueValidator<List<Integer>> {
    public void validate(String name, List<Integer> value) throws ParameterException {
        for (Integer port : value) {
            if (port < 0 || port > 65535) {
                throw new ParameterException("Parameter " + name + " should be in range 0-65535 (found " + port + ")");
            }
        }
    }
}