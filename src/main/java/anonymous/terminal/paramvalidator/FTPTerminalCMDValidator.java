package anonymous.terminal.paramvalidator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FTPTerminalCMDValidator implements IParameterValidator {
    private static final Set<String> COMMANDS = new HashSet<>(Arrays.asList("upload", "download", "delete"));

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!COMMANDS.contains(value))
            throw new ParameterException("Command should be [upload],[download],[delete] (found " + value + ")");
        if (value.equals("upload") || value.equals("download")) {

        }
    }
}