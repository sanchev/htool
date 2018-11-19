package anonymous.serialization.nmap.converter;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.util.HashSet;
import java.util.List;

public class StateValidator implements IValueValidator<List<String>> {
    public void validate(String name, List<String> value) throws ParameterException {
        HashSet<String> states = new HashSet<>();
        states.add("open");
        states.add("closed");
        states.add("filtered");
        states.add("unfiltered");
        states.add("open|filtered");
        states.add("closed|filtered");
        for (String state : value) {
            if (!states.contains(state)) {
                throw new ParameterException("Parameter " + name + " should be [open],[closed],[filtered],[unfiltered],[open|filtered],[closed|filtered] (found " + state + ")");
            }
        }
    }
}