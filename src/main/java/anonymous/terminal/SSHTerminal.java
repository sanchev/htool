package anonymous.terminal;

public abstract class SSHTerminal implements Terminal {
    @Override
    public abstract boolean connect(String host, int port, String login, String password);

    @Override
    public abstract boolean disconnect();

    @Override
    public abstract Object execute(String... args);
}