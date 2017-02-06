import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class MyFile {

    static Logger log = Logger.getLogger(MyFile.class.getName());

    // Reading from the file
    public String readInstructions(String testFile) {
        String everything = "";

        try {
            FileInputStream inputStream = new FileInputStream(testFile);
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                sb.append((char) ch);
            }
            everything = sb.toString();

        } catch (IOException e) {
            log.debug("File " + testFile + " does not exist.");
        }
        return everything;
    }

    // instruction
    public String parseOperation(String instruction) {
        return divide(instruction)[0];
    }

    // First parameter
    public String parseFirst(String instruction) {
        String firstParameter = "";
        try {
            firstParameter = divide(instruction)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("You haven't input all parameters.");
        }
        return firstParameter;
    }

    // Second parameter
    public double parseSecond(String instruction) {
        String secondParameter = "";
        double second = 0;
        try {
            secondParameter = divide(instruction)[2];
            second = Integer.parseInt(secondParameter);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("You haven't input all parameters.");
        } catch (NumberFormatException e) {
            log.debug("Timeout is not a number");
            log.debug("Input file example:");
            log.debug("open [Page address] [Timeout]");
        }
        return second;
    }


    // Splitting a string into an array
    public static String[] divide(String s) {
        ArrayList<String> mas = new ArrayList<String>();
        int i = 0;
        boolean f = false;

        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == ' ' || s.charAt(j) == '\"') {
                if (j > i) {
                    mas.add(s.substring(i, j));
                }
                i = j + 1;
            }
        }

        if (i < s.length()) {
            mas.add(s.substring(i));
        }

        return mas.toArray(new String[mas.size()]);
    }

    public void Help() {
        log.debug("Run framework:[Main] [File path]");
        log.debug("Example: Main http://www.google.com");
        log.debug("You are allowed not to write [File path], if file name is ./src/main/resources/test.txt" +
                " and it is in a current folder.");

    }

    public void OperatingProcedure() {
        log.debug("open [Page address] [Timeout]");
        log.debug("checkLinkPresentByHref [Searched href]");
        log.debug("checkLinkPresentByName [Searched name of href]");
        log.debug("checkPageTitle [Expected title]");
        log.debug("checkPageContains [Searched text]");


    }
}
