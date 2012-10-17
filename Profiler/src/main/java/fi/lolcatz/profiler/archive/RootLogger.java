package fi.lolcatz.profiler.archive;

import java.io.IOException;
import java.util.logging.*;

public class RootLogger {
    private static Logger rootLogger;
    private static Handler loggerHandler;

    static {
        rootLogger = Logger.getLogger("");
        loggerHandler = new ConsoleHandler();
        rootLogger.addHandler(loggerHandler);
        rootLogger.setLevel(Level.WARNING);
    }

    public static void setLoggingLevel(Level level) {
        rootLogger.setLevel(level);
    }

    public static void logToFile(String filename) {
        try {
            Handler fileHandler = new FileHandler(filename);
            switchLoggerHandlerTo(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logToConsole() {
        switchLoggerHandlerTo(new ConsoleHandler());
    }

    private static void switchLoggerHandlerTo(Handler newHandler) {
        rootLogger.removeHandler(loggerHandler);
        loggerHandler = newHandler;
        rootLogger.addHandler(newHandler);
    }
}
