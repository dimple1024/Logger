package sink;

public class DBSink implements Sink{
    private String hostname;
    private Integer port;
    private String userName;
    private String password;

    public DBSink(String hostname, Integer port, String userName, String password)
    {
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void doSink(String message) {
        // creates entry in DB
    }
}
