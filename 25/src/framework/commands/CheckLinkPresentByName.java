
import framework.input_output.Message;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckLinkPresentByName {

    long time;
    public boolean checkLinkNameCommand(String checkLinkNameStr, Document doc) {
    time = System.currentTimeMillis();
        String checkLinkNameMass[] = checkLinkNameStr.split("[\"]"); //parsing of command and getting data to a search
        String linkByName;
        if(checkLinkNameMass.length != 2) {
            time = 0; //wrong number of arguments, no command execution, time is 0
            return false;
        } else {
            linkByName = checkLinkNameMass[1]; // link presented by name to a search on the target page
            try {
                Elements links = doc.getElementsByTag("a");
                for (Element с : links) {
                    if(linkByName.equals(с.text())) {
                        time = System.currentTimeMillis() - time; //the link has found
                        return true;
                    }
                }
            } catch (NullPointerException ex){
                new Message("WARNING: Error during the execution of the command ["+ checkLinkNameStr +"] because of a failed connection to the URL").printMessage();
                time =0; //some troubles with doc creation, no command execution, time is 0
                return false;
            }
        }
        time = System.currentTimeMillis() - time; //the link has not found
        return false;
    }
}