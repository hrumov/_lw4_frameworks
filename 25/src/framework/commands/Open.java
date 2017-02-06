
import framework.input_output.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class Open {

    private Document doc;
    long time;
    public Document getDoc() {
        return doc;
    }

    public boolean openCommand(String openCommandString) {
        time = System.currentTimeMillis();
        String openCommandMass[] = openCommandString.split("[ \"]{1,3}"); //parsing of command and getting data to a search
        if (openCommandMass.length != 3) {
            time =0; //wrong number of arguments, no command execution, time is 0
            return false;
        } else {

            try {
                double timeOut = Double.parseDouble(openCommandMass[2])*1000; // timeout in milliseconds
                doc = Jsoup.connect(openCommandMass[1]).get(); //connection to the URL and reading the target page
                time = System.currentTimeMillis() - time; //result time of the connection
                if (timeOut < time) {
                    new Message("WARNING: Timeout is exceeded during the execution of the command [" +  openCommandString + "]").printMessage();
                    return false;
                }
            } catch (NumberFormatException ex) {
                new Message("WARNING: Error during the execution of the command [" +  openCommandString + "] because of incorrect timeout in the instruction").printMessage();
                time = 0; // no command execution, time is 0
                return false;
            } catch (IOException ex) {
                time =0; // no command execution, time is 0
                new Message("WARNING: Error during the execution of the command [" +  openCommandString + "] because of incorrect URL in the instruction or failed connection to the URL").printMessage();
                return false;
            }
            return true;
        }
    }
}