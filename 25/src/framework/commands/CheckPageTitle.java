
import framework.input_output.Message;
import org.jsoup.nodes.Document;

public class CheckPageTitle {

    long time;

    public boolean checkPageTitleCommand(String checkByTitleStr, Document doc) {
        time = System.currentTimeMillis();
        String checkByTitleMass[] = checkByTitleStr.split("[\"]"); //parsing of command and getting data to a search
        if(checkByTitleMass.length != 2) {
            time = 0;     //wrong number of arguments, no command execution, time is 0
            return false;
        } else {
            String realTitle;
            String title = checkByTitleMass[1];
            try {
                realTitle = doc.title();
            } catch (NullPointerException ex){
                new Message("WARNING: Error during the execution of the command [" + checkByTitleStr + "] because of a failed connection to the URL").printMessage();
                time = 0; //some troubles with doc creation, no command execution, time is 0
                return false;
            }
            time = System.currentTimeMillis()-time; //result time of a search
            return title.equals(realTitle);
        }
    }
}

