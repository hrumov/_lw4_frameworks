/**
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HT1 {

    private static final String RESULT_TRUE = "+ ";
    private static final String RESULT_FALSE = "! ";
    private static final String OPEN = "open";
    private static final String CHECK_LINK_PRESENT_BY_NAME = "checkLinkPresentByName";
    private static final String CHECK_LINK_PRESENT_BY_HREF = "checkLinkPresentByHref";
    private static final String CHECK_PAGE_TITLE = "checkPageTitle";
    private static final String CHECK_PAGE_CONTAINS = "checkPageContains";


    public static void main(String[] args) throws IOException {

        List<Double> totalTime = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<String> trueTest = new ArrayList<>();
        ArrayList<String> falseTest = new ArrayList<>();
        FileWriter writer = new FileWriter("C:\\result.txt");

        List<String> file = readFile("C:\\manual.txt");
        String pageContent = "";
        for (String fileLine : file) {
            if (!fileLine.isEmpty() && fileLine.contains(" ")) {

                String[] parameters = fileLine.split(" \"");
                if (parameters.length > 1) {

                    String firstParameter = parameters[1].replace("\"", "").trim();
                    if (!firstParameter.isEmpty()) {

                        double startTimeOpen = 0;
                        double endTimeOpen = 0;
                        double totalTimeOpen = 0;

                        switch (parameters[0]) {

                            case OPEN:
                                int timeout = 0;
                                if (parameters.length == 3) {
                                    String timeoutString = parameters[2].replace("\"", "").trim();
                                    timeout = Integer.valueOf(timeoutString);
                                }
                                startTimeOpen = System.nanoTime();
                                pageContent = open(firstParameter, timeout);
                                endTimeOpen = System.nanoTime();
                                totalTimeOpen = (endTimeOpen - startTimeOpen) / 1000000;
                                totalTime.add(totalTimeOpen);
                                if (!pageContent.isEmpty()) {
                                    writer.write(RESULT_TRUE + "[" + OPEN + " \"" + firstParameter + "\" \"" + timeout + "\"] " + totalTimeOpen);
                                    trueTest.add("+");
                                } else {
                                    writer.write(RESULT_FALSE + "[" + OPEN + " \"" + firstParameter + "\" \"" + timeout + "\"] " + totalTimeOpen);
                                    falseTest.add("!");
                                }
                                stringArrayList.add("Test 1");
                                writer.write("\n");
                                break;

                            case CHECK_LINK_PRESENT_BY_HREF:
                                if (!pageContent.isEmpty()) {
                                    startTimeOpen = System.nanoTime();
                                    boolean checkLinkPresentByHref = checkLinkPresentByHref(pageContent, firstParameter);
                                    endTimeOpen = System.nanoTime();
                                    totalTimeOpen = (endTimeOpen - startTimeOpen) / 1000000;
                                    totalTime.add(totalTimeOpen);
                                    writeResults(writer, checkLinkPresentByHref, CHECK_LINK_PRESENT_BY_HREF, firstParameter, totalTimeOpen,
                                            trueTest, falseTest, stringArrayList);
                                }
                                break;

                            case CHECK_LINK_PRESENT_BY_NAME:
                                if (!pageContent.isEmpty()) {
                                    startTimeOpen = System.nanoTime();
                                    boolean checkLinkByName = checkLinkPresentByName(pageContent, firstParameter);
                                    endTimeOpen = System.nanoTime();
                                    totalTimeOpen = (endTimeOpen - startTimeOpen) / 1000000;
                                    totalTime.add(totalTimeOpen);
                                    writeResults(writer, checkLinkByName, CHECK_LINK_PRESENT_BY_HREF, firstParameter, totalTimeOpen,
                                            trueTest, falseTest, stringArrayList);
                                }
                                break;

                            case CHECK_PAGE_CONTAINS:
                                if (!pageContent.isEmpty()) {
                                    startTimeOpen = System.nanoTime();
                                    boolean checkPageContains = checkPageContains(pageContent, firstParameter);
                                    endTimeOpen = System.nanoTime();
                                    totalTimeOpen = (endTimeOpen - startTimeOpen) / 1000000;
                                    totalTime.add(totalTimeOpen);
                                    writeResults(writer, checkPageContains, CHECK_PAGE_CONTAINS, firstParameter, totalTimeOpen,
                                            trueTest, falseTest, stringArrayList);
                                }
                                break;

                            case CHECK_PAGE_TITLE:
                                if (!pageContent.isEmpty()) {
                                    startTimeOpen = System.nanoTime();
                                    boolean checkPageTitle = checkPageTitle(pageContent, firstParameter);
                                    endTimeOpen = System.nanoTime();
                                    totalTimeOpen = (endTimeOpen - startTimeOpen) / 1000000;
                                    totalTime.add(totalTimeOpen);
                                    writeResults(writer, checkPageTitle, CHECK_PAGE_TITLE, firstParameter, totalTimeOpen,
                                            trueTest, falseTest, stringArrayList);
                                }
                                break;
                        }
                    }
                }
            }
        }

        writer.write("Total tests: " + stringArrayList.size() + "\n");
        writer.write("Passed/Failed: " + trueTest.size() + "/" + falseTest.size() + "\n");
        double sum = 0;
        for (Double sumList : totalTime) {
            sum += sumList;
        }
        writer.write("Total time: " + sum + "\n");
        double average = 0;
        if (totalTime.size() > 0) {
            average = sum / totalTime.size();
        }
        writer.write("Average time: " + average);
        writer.close();

    }

    public static void writeResults(FileWriter writer, boolean result, String methodName, String parameter, double totalTime,
                                    ArrayList<String> trueTest, ArrayList<String> falseTest, List<String> stringArrayList) throws IOException {
        if (result) {
            writer.write(RESULT_TRUE + "[" + methodName + " \"" + parameter + "\"] " + totalTime);
            trueTest.add("+");
        } else {
            writer.write(RESULT_FALSE + "[" + methodName + " \"" + parameter + "\"] " + totalTime);
            falseTest.add("!");
        }
        stringArrayList.add("Test");
        writer.write("\n");
    }


    public static List<String> readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        ArrayList<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static String open(String urlValue, int timeout) throws IOException {
        String content = "";
        try {
            URL url = new URL(urlValue);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000 * timeout);
            urlConnection.connect();
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            while (scanner.hasNext()) {
                scanner.useDelimiter("\\Z");
                content += scanner.next();
            }
            if (content.isEmpty() || content.equals(" ")) {
                return " ";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            return content;
        }
    }

    public static boolean checkLinkPresentByHref(String content, String link) {
        boolean result = false;
        Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String href = m.group(1);
            if (href.contains(link)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean checkLinkPresentByName(String content, String link) {
        return checkContentByPattern(content, "<a href>(.*?)</a>", link);
    }

    public
    static boolean checkPageTitle(String content, String link) {
        return checkContentByPattern(content, "<title>(.*?)</title>", link);
    }

    public static boolean checkPageContains(String content, String link) {
        //Разметок для текста в html коде много, взял на примере <b> </b>
        return checkContentByPattern(content, "<b>(.*?)</b", link);
    }

    public static boolean checkContentByPattern(String content, String pattern, String stringToFind) {
        boolean result = false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String text = m.group(1);
            if (text.contains(stringToFind)) {
                result = true;
                break;
            }
        }
        return result;
    }


}