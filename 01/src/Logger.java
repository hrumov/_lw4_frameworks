import java.io.FileWriter;
import java.io.IOException;

/**
 */
class Logger {
    private FileWriter writer;

    Logger(String logFilePath) {
        try {
            this.writer = new FileWriter(logFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeLog(String message) {
        try {
            this.writer.write(message);
            this.writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
