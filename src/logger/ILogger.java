package logger;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.*;

public interface ILogger {
    public void debug(String message) throws IOException;
    public void info(String message) throws IOException;
    public void warn(String message) throws IOException;

    public void error(String message) throws IOException;
    public void fatal(String message) throws IOException;

    public void configure();

    public ILogger configure(Map<String , String> configuration) throws IOException;
}
