import framework.input_output.Message;
import org.jsoup.nodes.Document;


public class CheckPageContains {

    long time;

    public boolean checkPageContainsCommand(String checkPageContainsStr, Document doc) {
        time = System.currentTimeMillis();
        String checkPageContainsMass[] = checkPageContainsStr.split("[\"]");//parsing of command and getting data to a search
        String text, realHtml;
        if(checkPageContainsMass.length != 2) {
            time = 0;   //wrong number of arguments, no command execution, time is 0
            return false;
        } else {
            text = checkPageContainsMass[1];
            try {
                realHtml = doc.html();
            } catch (NullPointerException ex){
                new Message("WARNING: Error during the execution of the command [" + checkPageContainsStr + "] because of a failed connection to the URL").printMessage();
                time = 0;   //some troubles with doc creation, no command execution, time is 0
                return false;
            }
        }
        time = System.currentTimeMillis()-time; //result time of a search
        return realHtml.contains(text);
    }
}
