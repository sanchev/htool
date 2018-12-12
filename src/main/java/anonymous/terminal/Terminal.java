package anonymous.terminal;

public interface Terminal {
    public boolean connect(String host, int port, String login, String password);
    public boolean disconnect();
    public Object execute(String... args);
}