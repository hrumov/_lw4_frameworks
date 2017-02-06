import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class Runner {

    static Logger log = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        MyFile myFile = new MyFile();
        String fileName = "./src/main/resources/test.txt";

        //Call help on the input format
        if ((args.length != 0) && (args[0] == "I need your help")) {
            myFile.Help();
        }

        if (args.length != 0 || new File(fileName).exists()) {

            //Input file name
            String testFile = (args.length != 0) ? args[0] : fileName;

            DecimalFormat df = new DecimalFormat("0.00");
            MyUrl myUrl = new MyUrl();


            //Reading from the input file and write to an array of row
            String instructionsFromFile = myFile.readInstructions(testFile);
            String[] instructions = instructionsFromFile.split("\r\n"); // разделяем текст по строкам

            double allTests = 0;
            int failure = 0;
            String url = "";


            //Running commands and counting runtime
            for (String instruction : instructions) {

                log.debug(instruction);

                double startTime = System.currentTimeMillis();

                String successful = "+";

                switch (myFile.parseOperation(instruction)) {

                    case "open":
                        url = myFile.parseFirst(instruction);
                        boolean openSite = myUrl.openUrl(url);
                        double timeGiven = myFile.parseSecond(instruction);
                        double timeToOpenSecs = (double) (System.currentTimeMillis() / 1000) - startTime / 1000;
                        if ((timeToOpenSecs > timeGiven) || (!openSite) || (url.equals("") || testFile.equals(0))) {
                            successful = "!";
                            failure++;
                        }
                        break;

                    case "checkLinkPresentByHref":
                        String href = myFile.parseFirst(instruction);
                        boolean checkLinkByHref = myUrl.checkLinkPresentByHref(url, href);
                        if (!checkLinkByHref || href.equals("")) {
                            successful = "!";
                            failure++;
                        }
                        break;

                    case "checkLinkPresentByName":
                        String text = myFile.parseFirst(instruction);
                        boolean checkLinkByName = myUrl.checkOnPage(url, text);
                        if (!checkLinkByName || text.equals("")) {
                            successful = "!";
                            failure++;
                        }
                        break;

                    case "checkPageTitle":
                        String title = myFile.parseFirst(instruction);
                        if (!myUrl.getPageTitle(url).equals(title) || title.equals("")) {
                            successful = "!";
                            failure++;
                        }
                        break;

                    case "checkPageContains":
                        String contain = myFile.parseFirst(instruction);
                        boolean check = myUrl.checkOnPage(url, contain);
                        if (!check || contain.equals("")) {
                            successful = "!";
                            failure++;
                        }
                        break;

                    //Call information on the format of the input file when the command is not defined
                    default:
                        String notCommand = myFile.parseOperation(instruction);
                        log.debug("Unknown command: " + notCommand);
                        myFile.OperatingProcedure();
                        successful = "!";
                        failure++;
                        break;
                }

                double endTime = System.currentTimeMillis();
                double diff = endTime / 1000 - startTime / 1000;

                allTests += diff;

                String output = df.format(diff);
                log.info(successful + " [" + instruction + "] " + output);
            }

            String outputTotal = df.format(allTests);
            String outAverageTime = df.format(allTests / instructions.length);

            log.info("Total tests: " + String.valueOf(instructions.length));
            log.info("Passed/Failed: " + String.valueOf(instructions.length - failure) + "/" + failure);
            log.info("Total time of tests: " + outputTotal);
            log.info("Average time of tests: " + String.valueOf(outAverageTime));

            //Writing data to a log file
        } else {
            myFile.Help();
        }
    }
}
