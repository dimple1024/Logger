package logger;

import sink.*;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DefaultLogger implements ILogger{

    //TODO: static instance id
    public static final Map<LogLevel, List<LogLevel>> LOG_HIERRACHY = new HashMap<LogLevel, List<LogLevel>>() {
        {

            put(LogLevel.DEBUG, new ArrayList<>(Arrays.asList(
                    LogLevel.DEBUG,
                    LogLevel.INFO,
                    LogLevel.WARN,
                    LogLevel.ERROR,
                    LogLevel.FATAL
            )));
            put(LogLevel.INFO, new ArrayList<>(Arrays.asList(
                    LogLevel.INFO,
                    LogLevel.WARN,
                    LogLevel.ERROR,
                    LogLevel.FATAL
            )));
            put(LogLevel.WARN, new ArrayList<>(Arrays.asList(
                    LogLevel.WARN,
                    LogLevel.ERROR,
                    LogLevel.FATAL
            )));
            put(LogLevel.ERROR, new ArrayList<>(Arrays.asList(
                LogLevel.ERROR,
                LogLevel.FATAL
            )));
            put(LogLevel.FATAL, new ArrayList<>(Arrays.asList(
                    LogLevel.FATAL
            )));


        }
    };
    private String nameSpace;
    private Boolean debugLogEnabled;
    private Boolean infoLogEnabled;
    private Boolean warnLogEnabled;
    private Boolean errorLogEnabled;
    private Boolean isFatalLogEnabled;

    private static Map<String, DefaultLogger> loggerRepository = new HashMap<>();

    private Map<String,String> configuration;
    private Sink sink ;



    private DefaultLogger(String nameSpace)
    {
        this.nameSpace = nameSpace;
    }

    public static ILogger getLogger(String nameSpace)
    {
        if (null == loggerRepository.get(nameSpace))
        {
            synchronized (DefaultLogger.class)
            {
                if (null == loggerRepository.get(nameSpace)) {
                    loggerRepository.put(nameSpace, new DefaultLogger(nameSpace));
                }
            }
        }
        return loggerRepository.get(nameSpace);
    }

    private String getFormattedDate(String format) {
        return new SimpleDateFormat(format).format(new Date());

    }

    /**
     * Returns LogLevel : [Timestamp] : Namespace -  Message
     * @param message
     */
    private String getSinkMessage(String message, LogLevel logLevel) {
        return String.format("%s : [%s] : %s - %s", logLevel,
                this.getFormattedDate(this.configuration.get("ts_format")), this.nameSpace , message);
    }

    @Override
    public void debug(String message) throws IOException {
        if (isDebugLogEnabled()) {
            this.sink.doSink(getSinkMessage(message, LogLevel.DEBUG));
        }
    }

    @Override
    public void info(String message) throws IOException {
        if (isInfoLogEnabled()) {
            this.sink.doSink(getSinkMessage(message, LogLevel.INFO));
        }

    }

    @Override
    public void warn(String message) throws IOException {
        if (isWarnLogEnabled()) {
            this.sink.doSink(getSinkMessage(message,LogLevel.WARN));
        }

    }

    @Override
    public void error(String message) throws IOException {
        if (isErrorLogEnabled()) {
            this.sink.doSink(getSinkMessage(message,LogLevel.ERROR));
        }
    }

    @Override
    public void fatal(String message) throws IOException {
        this.sink.doSink(getSinkMessage(message, LogLevel.FATAL));
    }

    @Override
    public void configure() {

    }

    @Override
    public ILogger configure(Map<String, String> configuration) throws IOException {

        this.configuration = configuration;
        if (isMandatoryConfigPresent())
        {
            enableLogs(LogLevel.valueOf(configuration.get("log_level")));
            configureSink(configuration.get("sink_type"));
        }
        else {

            throw new InvalidObjectException("Configuration provided is not valid!");
        }
        return this;

    }

    private boolean isMandatoryConfigPresent() {
        if (null == this.configuration.get("log_level") ||
                null == this.configuration.get("ts_format")
        )
        {
            return false;
        }
        return true;
    }

    private void configureSink(String sink_type) throws IOException {
        setSink(SinkFactory.getSink(sink_type, configuration));
    }

    private void enableLogs(LogLevel logLevel) {
        List<LogLevel> logLevels = DefaultLogger.LOG_HIERRACHY.get(logLevel);
        for (LogLevel level : logLevels)
        {
            if(LogLevel.DEBUG.equals(level)) { setDebugLogEnabled(true); }
            if(LogLevel.INFO.equals(level)) { setInfoLogEnabled(true); }
            if(LogLevel.WARN.equals(level)) { setWarnLogEnabled(true); }
            if(LogLevel.ERROR.equals(level)) { setErrorLogEnabled(true); }
            if(LogLevel.FATAL.equals(level)) { setFatalLogEnabled(true); }
        }
    }

    public Boolean isDebugLogEnabled() {
        return debugLogEnabled;
    }

    public void setDebugLogEnabled(Boolean debugLogEnabled) {
        this.debugLogEnabled = debugLogEnabled;
    }

    public Boolean isInfoLogEnabled() {
        return infoLogEnabled;
    }

    public void setInfoLogEnabled(Boolean infoLogEnabled) {
        this.infoLogEnabled = infoLogEnabled;
    }

    public Boolean isWarnLogEnabled() {
        return warnLogEnabled;
    }

    public void setWarnLogEnabled(Boolean warnLogEnabled) {
        this.warnLogEnabled = warnLogEnabled;
    }

    public Boolean isErrorLogEnabled() {
        return errorLogEnabled;
    }

    public void setErrorLogEnabled(Boolean errorLogEnabled) {
        this.errorLogEnabled = errorLogEnabled;
    }

    public Boolean isFatalLogEnabled() {
        return isFatalLogEnabled;
    }

    public void setFatalLogEnabled(Boolean fatalLogEnabled) {
        isFatalLogEnabled = fatalLogEnabled;
    }
    public Sink getSink() {
        return sink;
    }

    public void setSink(Sink sink) {
        this.sink = sink;
    }
}
