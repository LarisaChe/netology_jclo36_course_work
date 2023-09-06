package ru.netology.log;

import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    private static String logFileName = "transfer.log";
    private static Log INSTANCE = null;
    private Log() {}
    public static Log getInstance() {
        if (INSTANCE == null) {
            synchronized (Log.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Log();
                }
            }
        }
        return INSTANCE;
    }
    public void log(String type, String msg) throws IOException {
        String logMsg = "[" + LocalDateTime.now() + "] " + type + ": " + msg;
        boolean append = true;
        WorkWithFiles.saveToFile(logFileName, logMsg, append);
    }

    public void logInfo(String msg) throws IOException {
        log("INFO", msg);
    }

    public void logError(String msg) throws IOException {
        log("ERROR", msg);
    }
}
