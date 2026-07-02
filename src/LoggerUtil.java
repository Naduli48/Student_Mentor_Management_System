import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Utility class that provides a single shared logger instance for the entire application.
// Ensures all log messages are written consistently to the same log file (SMMS.log) instead of being scattered or duplicated.
public class LoggerUtil {

    // Holds the single shared logger instance
    private static Logger logger;

    // Returns the shared logger instance, creating and configuring it the first time it is requested.
    public static Logger getLogger(String name) {
        if (logger == null) {
            logger = Logger.getLogger(name);
            setupLogger();
        }
        return logger;
    }

    // Configures the logger to write to a file called SMMS.log using a simple, human-readable format.
    private static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("SMMS.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Logger setup failed: " + e.getMessage());
        }
    }
}