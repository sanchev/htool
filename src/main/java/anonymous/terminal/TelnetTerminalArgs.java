package anonymous.terminal;

import com.beust.jcommander.Parameter;

/*
* Если последний символ параметра ' ' (пробел) и он обязателен,
* то его необходимо обернуть в двойные кавычки
* Например, параметр -startPattern имеет вид 'параметр '
* для передачи его из командной строки он должен иметь вид "параметр "
* а для програмной передачи как String он должен иметь вид "\"параметр "\"
*/

public class TelnetTerminalArgs {
    @Parameter(required = true, description = "Telnet command")
    private String cmd;

    @Parameter(names = {"-startPattern", "-start", "-sP", "-s"}, description = "Pattern of terminal output before command")
    private String startPattern;

    @Parameter(names = {"-endPattern", "-end", "-eP", "-e"}, description = "Pattern of terminal output after command")
    private String endPattern;

    public String getCmd() {
        return cmd;
    }

    public String getStartPattern() {
        return startPattern;
    }

    public String getEndPattern() {
        return endPattern;
    }
}