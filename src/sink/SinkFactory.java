package sink;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class SinkFactory {
    public static Sink getSink(String sinkType, Map<String, String> configuration) throws IOException {

        switch (sinkType) {
            case "TEXTFILE":
                String fileName = configuration.get("file_path");
                Long maxFileSize = Long.parseLong(configuration.get("file_size"));
                return new FileSink(fileName, maxFileSize);
            case "DATABASE":
                String hostName = configuration.get("host_name");
                Integer port = Integer.parseInt(configuration.get("port"));
                String userName = configuration.get("user_name");
                String password = configuration.get("password");
                return new DBSink(hostName, port, userName, password);
            default:
                throw new RuntimeException("Sink type not supported!");
        }
    }
}
