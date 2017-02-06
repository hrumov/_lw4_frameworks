
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TextReader {
    private static String sCurrentLine;
    private static String[] part;
    public static double methodTime;

    public static void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("resources/instruction.txt")))
        {
            while ((sCurrentLine = br.readLine()) != null) {
                part = sCurrentLine.split("\\|");
                deleteQuotes(); //delete quotes in the 2nd readLine (input)
                if (part[0].equals("open")) {
                    long startTime = System.currentTimeMillis();
                    part[2] = part[2].substring(1, part[2].length() - 1); //delete quotes in the 3th readLine(input)
                    HTTPMessage.responseMessage(part[1]); // response message from given URL (in instruction.txt)
                    long endTime   = System.currentTimeMillis();
                    calculateTime(startTime, endTime);
                    checkOpen(part[2]);
                    checkFlag();
                }
                if (part[0].equals("checkLinkPresentByHref")) {
                    long startTime = System.currentTimeMillis();
                    SearchEngine.checkLinkPresentByHref(part[1]);
                    long endTime   = System.currentTimeMillis();
                    calculateTime(startTime, endTime);
                    checkFlag();
                }
                if (part[0].equals("checkLinkPresentByName")) {
                    long startTime = System.currentTimeMillis();
                    SearchEngine.checkLinkPresentByName(part[1]);
                    long endTime   = System.currentTimeMillis();
                    calculateTime(startTime, endTime);
                    checkFlag();
                }
                if (part[0].equals("checkPageTitle")) {
                    long startTime = System.currentTimeMillis();
                    SearchEngine.checkPageTitle(part[1]);
                    long endTime   = System.currentTimeMillis();
                    calculateTime(startTime, endTime);
                    checkFlag();
                }
                if (part[0].equals("checkPageContains")) {
                    long startTime = System.currentTimeMillis();
                    SearchEngine.checkPageContains(part[1]);
                    long endTime   = System.currentTimeMillis();
                    calculateTime(startTime, endTime);
                    checkFlag();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteQuotes() {
        part[1] = part[1].substring(1, part[1].length() - 1);
    }

    public static void calculateTime(Long startTime, Long endTime) {
        methodTime = endTime - startTime;
        methodTime = methodTime * 1.0 / 1000;
        StatisticsCalculator.totalTime += methodTime;
    }

    public static void checkFlag() {
        StringBuilder sb = new StringBuilder();
        StatisticsCalculator.totalTests += 1;
        if (SearchEngine.flag) {
            LogCreator.appendText(sb.append("+ ").append(Arrays.asList(part)).append(" ").append(methodTime).append("\n"));
            StatisticsCalculator.numPassedTests += 1;
        } else {
            LogCreator.appendText(sb.append("! ").append(Arrays.asList(part)).append(" ").append(methodTime).append("\n"));
            StatisticsCalculator.numFailedTests += 1;
        }
    }

    public static void checkOpen(String timeStr) {
        double time = Double.parseDouble(timeStr);
        if (time > methodTime) {
            SearchEngine.flag = true;
        } else SearchEngine.flag = false;
    }
}
