package client;

import logger.DefaultLogger;
import logger.ILogger;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) {
        try
        {

            Map<String, String> configuration = new HashMap<>();
            configuration.put("ts_format","dd:mm:yyyy hh:mm:ss");
            configuration.put("log_level", "DEBUG");
            configuration.put("sink_type", "TEXTFILE");
            configuration.put("file_path", "/home/dimple.verma/logs/phonePesink.txt");
            configuration.put("file_size", "100000000");
            ILogger logger = DefaultLogger.getLogger("PHONEPE").configure(configuration);
            logger.debug("Debug statement!");
            logger.info("Informational message!");
            logger.warn("Warning message!");
            logger.error("Error message!");
        }
        catch (Throwable e)
        {
            System.out.println("Error occured : " +  e.getMessage());
        }

    }
}
