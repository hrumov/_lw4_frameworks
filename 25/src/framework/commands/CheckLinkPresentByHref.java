
import framework.input_output.Message;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckLinkPresentByHref {

    long time;

    public boolean checkLinkHrefCommand(String checkLinkHrefStr, Document doc) {
        time = System.currentTimeMillis();
        String checkLinkHrefMass[] = checkLinkHrefStr.split("[\"]"); //parsing of command and getting data to a search
        String linkByHref;
        if (checkLinkHrefMass.length != 2) {
            time = 0; //wrong number of arguments, no command execution, time is 0
            return false;
        } else {
            linkByHref = checkLinkHrefMass[1]; // link presented by href to a search on the target page
            try {
                Elements links = doc.getElementsByTag("a");
                for (Element с : links) {
                    if (linkByHref.equals(с.attr("href"))) {
                        time = System.currentTimeMillis() - time; //the link has found
                        return true;
                    }
                }
            } catch (NullPointerException ex) {
                new Message("WARNING: Error during the execution of the command [" + checkLinkHrefStr + "] because of a failed connection to the URL").printMessage();
                time =0; //some troubles with doc creation, no command execution, time is 0
                return false;
            }
        }
        time = System.currentTimeMillis() - time;  //the link has not found
        return false;
    }
}
