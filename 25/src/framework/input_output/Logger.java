
import java.io.*;
import java.text.DecimalFormat;

public class Logger {

    int totalTests, testsPassed;
    File logFile;
    DecimalFormat dFormat = new DecimalFormat("0.000");
    Writer outputLog;
    StringBuilder builder;
    double totalTime;

    public Logger(String[] args, int totalResult, int testsPassed, StringBuilder builder, double totalTime) {
        this.totalTime = totalTime;
        this.builder = builder;
        this.logFile = new File(args[1]);
        this.totalTests = totalResult;
        this.testsPassed = testsPassed;

        // if log.txt doesn't exist it will be created in the specified path
        if (!logFile.exists()) {
            new Message("Log file doesn't exist. New file will be created").printMessage();
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                new Message("Log file is not created").printMessage();
                System.exit(1);
            }
        }
        if (logFile.length() != 0) {
            new Message("Data in the log file is overwritten ").printMessage();
        }
    }

    public void writeLog() {
        int NULL_TIME = 0;
        try {
            outputLog = new BufferedWriter(new FileWriter(logFile));
            outputLog.write(builder.toString());
            outputLog.write("Total tests: " + totalTests + "\r\n");
            outputLog.write("Passed/Failed: " + testsPassed +"/" +(totalTests-testsPassed) +  "\r\n");
            outputLog.write("Total time: " + dFormat.format(totalTime) +  "\r\n");
            if (totalTests != 0) {
                outputLog.write("Average time: " + dFormat.format(totalTime / totalTests) + "\r\n");
            } else {
                outputLog.write("Average time: " + dFormat.format(NULL_TIME) + "\r\n"); // in order to avoid division by zero
            }
            outputLog.close();
            new Message("Log file is created").printMessage();
        } catch (IOException e) {
            new Message(e.getMessage()).printMessage();
            System.exit(1);
        }
    }

}