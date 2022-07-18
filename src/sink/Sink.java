package sink;

import java.io.IOException;

public interface Sink {
    public void doSink(String message) throws IOException;
}
