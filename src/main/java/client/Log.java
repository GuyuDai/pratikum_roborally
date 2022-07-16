package client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  setup for LogFiles
 */

public class Log {

    public static Logger logFile(String logName) {
        Logger logger = Logger.getLogger(logName);
        FileHandler fh;
        logger.setLevel(Level.ALL);
        try {
            // configuration of the logger with handler and formatter
            fh = new FileHandler("logs/" + logName);
            fh.setLevel(Level.INFO);
            logger.addHandler(fh);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

}
