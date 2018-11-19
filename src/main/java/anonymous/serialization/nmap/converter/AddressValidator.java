package anonymous.serialization.nmap.converter;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidator implements IValueValidator<List<String>> {
    public void validate(String name, List<String> value) throws ParameterException {
        String IPv4RegExp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(IPv4RegExp);
        for (String address : value) {
            Matcher matcher = pattern.matcher(address);
            if (!matcher.matches()) {
                throw new ParameterException("Parameter " + name + " is not IPv4 (found " + address + ")");
            }
        }
    }
}