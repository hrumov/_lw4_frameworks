import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 */
public class Main {
    private static final String testFile = "task.txt";

    public static void main(String[] args) throws IOException {

        WebDriver driver = new FirefoxDriver();

        String[] instructions = readInstructions().split("\r\n");

        String log = "";
        long total = 0;
        int failed = 0;

        for(String instruction: instructions){

            long startTime = System.currentTimeMillis();

            String success = "+";


            switch(parseOperation(instruction)) {

                case "open":
                    String url = parseFirst(instruction);

                    openUrl(driver, url);
                    int timeGiven = Integer.parseInt(parseSecond(instruction));
                    int timeToOpenSecs = (int) (System.currentTimeMillis()/1000);
                    if(timeToOpenSecs>timeGiven) {
                        success = "!";
                        failed++;
                    }

                    break;

                case  "checkLinkPresentByHref":
                    String href = parseFirst(instruction);
                    try{
                        driver.findElement(By.xpath("//a[@href='"+href+"']"));
                    } catch (NoSuchElementException e) {
                        success = "!";
                        failed++;
                    }

                    break;

                case "checkLinkPresentByName":

                    String text = parseFirst(instruction);
                    try{
                        driver.findElement(By.xpath("//a[text()='"+text+"']"));
                    } catch (NoSuchElementException e) {
                        success = "!";
                        failed++;
                    }
                    break;

                case "checkPageTitle":
                    String aTtitle = parseFirst(instruction);
                    if(!driver.getTitle().equals(aTtitle)){
                        success = "!";
                        failed++;
                    }
                    break;

                case "checkPageContains":
                    String aContain = parseFirst(instruction);
                    if(!driver.getPageSource().contains(aContain)) {
                        success = "!";
                        failed++;
                    }
                    break;

            }

            long endTime = System.currentTimeMillis();
            long diff = endTime - startTime;

            total+=diff;

            log+= success + " [" + instruction +"] "+diff+"\n";

        }


        log+= "passed/fail: " +  String.valueOf(instructions.length - failed)+"/"+failed+"\n";
        log+= "total time ms: "+total+"\n";
        log+= "average time ms: "+ String.valueOf(total / instructions.length)+"\n";
        System.out.println(log);

        PrintWriter writer = new PrintWriter("log.txt", "UTF-8");
        writer.write(log);
        writer.close();

        driver.quit();
    }

    private static String readInstructions() throws IOException {

        FileInputStream inputStream = new FileInputStream(testFile);
        try {
            String everything = IOUtils.toString(inputStream);
            return everything;
        } finally {
            inputStream.close();
        }
    }

    private static String parseOperation(String instruction){
        String word = instruction.split("\\s+")[0];
        return word;
    }

    private static String parseFirst(String instruction) {
        return instruction.split("\\s+")[1];
    }

    private static String parseSecond(String instruction) {
        return instruction.split("\\s+")[2];
    }

    private static void openUrl(WebDriver driver, String url) throws IOException {
        driver.get(url);
    }
}
