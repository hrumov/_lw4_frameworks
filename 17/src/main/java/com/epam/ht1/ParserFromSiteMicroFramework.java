
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * In this code, we get the information from external resource (text file i.e. input.txt),
 * connect to the Web site listed in the resource information and
 * compare the information with the expected date.
 * After that displays all the information needed in the form of a report
 * in an external resource (text file i.e. output.txt)
 */
//--------------------------------------main work version!!!!!!!!!!!----------------------------------------
public class ParserFromSiteMicroFramework {

    static Document doc;

    public static void main(String[] args) throws IOException {

        //converting data into an array
        String[] instructions = readInstructions().split("\r\n");


        //class PrintWriter use for writing results in new file
        try (PrintWriter out = new PrintWriter("D:\\output.txt")) {

            //time when operation begin
            long startTimeOperation;

            //time taken for operation
            long timeTakenOnOperation;

            //time taken for operation in double integers
            double timeTakenOnOperationInDouble;

            //information about results of tests
            String resultInfoAboutTest;

            //round time taken for open operation
            double roundTimeTakenOnOpen = 0;

            //round time taken for checkLinkPresentByHref operation
            double roundTimeTakenToCheckLinkPresentByHref = 0;

            //round time taken for checkLinkPresentByName operation
            double roundTimeTakenToCheckLinkPresentByName = 0;

            //round time taken for checkPageTitle operation
            double roundTimeTakenToCheckPageTitle = 0;

            //round time taken for checkPageContains operation
            double roundTimeTakenToCheckPageContains = 0;

            //the total time taken for tests
            double totalTimeForTests;

            //total amount of tests
            int amountAllTests = 0;

            //amount of tests that have passed
            int amountPassTests = 0;

            for (String instruction : instructions) {

                switch (parseMainOperation(instruction)) {

                    case "open":

                        amountAllTests++;

                        try {
                            startTimeOperation = System.nanoTime();

                            //converse string to integer where argument parseSecondArgument(instruction) is timeout
                            int timeoutInteger = Integer.parseInt(parseSecondArgument(instruction));

                            //connect to site where argument parseFirstArgument(instruction) is url
                            doc = Jsoup.connect(parseFirstArgument(instruction)).timeout(timeoutInteger * 1000).get();

                            timeTakenOnOperation = System.nanoTime() - startTimeOperation;

                            //converse time to double
                            timeTakenOnOperationInDouble = (double) timeTakenOnOperation / 1000000000.0;

                            //rounding to the thousandths
                            roundTimeTakenOnOpen = new BigDecimal(timeTakenOnOperationInDouble).setScale(3, RoundingMode.UP).doubleValue();

                            resultInfoAboutTest = "+ [open \"" + parseFirstArgument(instruction) + "\"] " + roundTimeTakenOnOpen;

                            //write information about test to output file
                            out.println(resultInfoAboutTest);

                            amountPassTests++;


                            //exception if don't enough time to connect
                        } catch (SocketTimeoutException e) {
                            System.out.println("timeout error");
                            //exception if website address does not exist
                        } catch (UnknownHostException exception) {
                            System.out.println("website address does not exist!");
                        }
                        break;

                    case "checkLinkPresentByHref":

                        amountAllTests++;

                        startTimeOperation = System.nanoTime();

                        //parseFirstArgument(instruction) is href i.e. "http://demoqa.com/wp-content/uploads/2014/08/pattern-14.png"
                        Elements elements = doc.select("a[href*=" + parseFirstArgument(instruction) + "]");
                        if (!elements.isEmpty()) {
                            resultInfoAboutTest = "+ [linkPresentByHref \"" + parseFirstArgument(instruction) + "\"] ";
                            amountPassTests++;
                        } else {
                            resultInfoAboutTest = "- [linkPresentByHref \"" + parseFirstArgument(instruction) + "\"] ";
                        }

                        timeTakenOnOperation = System.nanoTime() - startTimeOperation;

                        //converse to double
                        timeTakenOnOperationInDouble = (double) timeTakenOnOperation / 1000000000.0;
                        //rounding to the thousandths
                        roundTimeTakenToCheckLinkPresentByHref = new BigDecimal(timeTakenOnOperationInDouble).setScale(3, RoundingMode.UP).doubleValue();

                        String estimatedTimeOpenLinkPresentByHref = resultInfoAboutTest + roundTimeTakenToCheckLinkPresentByHref;

                        out.println(estimatedTimeOpenLinkPresentByHref);
                        break;

                    case "checkLinkPresentByName":

                        amountAllTests++;

                        startTimeOperation = System.nanoTime();

                        Elements links = doc.select("a[href]");
                        String linkHref = links.text();

                        //here parseFirstArgument(instruction) is name of link i.e. "Accordion"
                        if (linkHref.contains(parseFirstArgument(instruction))) {
                            resultInfoAboutTest = "+ [linkPresentByName \"" + parseFirstArgument(instruction) + "\"] ";
                            amountPassTests++;
                        } else {
                            resultInfoAboutTest = "- [linkPresentByname \"" + parseFirstArgument(instruction) + "\"] ";
                        }

                        timeTakenOnOperation = System.nanoTime() - startTimeOperation;

                        //converse to double
                        timeTakenOnOperationInDouble = (double) timeTakenOnOperation / 1000000000.0;
                        //rounding to the thousandths
                        roundTimeTakenToCheckLinkPresentByName = new BigDecimal(timeTakenOnOperationInDouble).setScale(3, RoundingMode.UP).doubleValue();

                        String estimatedTimeOpenLinkPresentByName = resultInfoAboutTest + roundTimeTakenToCheckLinkPresentByName;

                        out.println(estimatedTimeOpenLinkPresentByName);

                        break;

                    case "checkPageTitle":

                        amountAllTests++;

                        startTimeOperation = System.nanoTime();

                        String title = doc.title();

                        //here parseFirstArgument(instruction) is name of page title i.e. "Demoqa | Just another WordPress site"
                        if (title.equals(parseFirstArgument(instruction))) {
                            resultInfoAboutTest = "+ [checkPageTitle \"" + parseFirstArgument(instruction) + "\"] ";
                            amountPassTests++;
                        } else {
                            resultInfoAboutTest = "- [checkPageTitle \"" + parseFirstArgument(instruction) + "\"] ";
                        }

                        timeTakenOnOperation = System.nanoTime() - startTimeOperation;

                        //converse to double
                        timeTakenOnOperationInDouble = (double) timeTakenOnOperation / 1000000000.0;
                        //rounding to the thousandths
                        roundTimeTakenToCheckPageTitle = new BigDecimal(timeTakenOnOperationInDouble).setScale(3, RoundingMode.UP).doubleValue();

                        String estimatedTimeOpenPageTitle = resultInfoAboutTest + roundTimeTakenToCheckPageTitle;

                        out.println(estimatedTimeOpenPageTitle);

                        break;

                    case "checkPageContains":

                        amountAllTests++;

                        startTimeOperation = System.nanoTime();

                        //here parseFirstArgument(instruction) is text that contain web page i.e. "Aliquam hendrit rutrum iaculis nullam"
                        Elements elementText = doc.getElementsContainingText(parseFirstArgument(instruction));
                        if (!elementText.isEmpty()) {
                            resultInfoAboutTest = "+ [checkPageContains \"" + parseFirstArgument(instruction) + "\"] ";
                            amountPassTests++;
                        } else {
                            resultInfoAboutTest = "- [checkPageContains \"" + parseFirstArgument(instruction) + "\"] ";
                        }
                        timeTakenOnOperation = System.nanoTime() - startTimeOperation;

                        //converse to double
                        timeTakenOnOperationInDouble = (double) timeTakenOnOperation / 1000000000.0;

                        //rounding to the thousandths
                        roundTimeTakenToCheckPageContains = new BigDecimal(timeTakenOnOperationInDouble).setScale(3, RoundingMode.UP).doubleValue();

                        String estimatedTimeOpenpageContains = resultInfoAboutTest + roundTimeTakenToCheckPageContains;

                        out.println(estimatedTimeOpenpageContains);
                        break;
                }

            }

            //time taken for all operations
            totalTimeForTests = roundTimeTakenOnOpen +
                    roundTimeTakenToCheckLinkPresentByHref +
                    roundTimeTakenToCheckLinkPresentByName +
                    roundTimeTakenToCheckPageTitle +
                    roundTimeTakenToCheckPageContains;

            //round time taken for all operations
            double roundTotalTimeForTests = new BigDecimal(totalTimeForTests).setScale(3, RoundingMode.UP).doubleValue();

            //round average time taken for one operation
            double roundAverageTimeForTests = new BigDecimal(roundTotalTimeForTests / amountAllTests).setScale(3, RoundingMode.UP).doubleValue();


            //write general information to output file
            out.println(" ");
            out.println("Total information:");
            out.println("Total tests = " + amountAllTests);
            out.println("Passed/Failed: = " + amountPassTests + "/" + (amountAllTests - amountPassTests));
            out.println("Total time(in seconds) = " + roundTotalTimeForTests);
            out.println("Average time(in seconds) = " + roundAverageTimeForTests);


        }

    }

    //read data from source file by path
    public static String readInstructions() throws IOException {
        FileInputStream inputStream = new FileInputStream("D:\\input.txt");
        try {
            return IOUtils.toString(inputStream);
        } finally {
            inputStream.close();
        }

    }

    //read name of operation
    private static String parseMainOperation(String instruction) {
        return instruction.split("\\s+")[0];
    }

    //read first argument of operation
    private static String parseFirstArgument(String instruction) {
        return instruction.split("\"|\r?\n")[1];
    }

    //read second argument of operation
    private static String parseSecondArgument(String instruction) {
        return instruction.split("\"|\r?\n")[3];
    }

}

