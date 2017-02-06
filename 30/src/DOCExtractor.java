import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class DOCExtractor {

    public String extractLinkOpen(String DOCText) {
        String PATTERN = "(\"http([^\"]*\"))";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(DOCText);

        String link = null;
        while (matcher.find()) {
            link = matcher.group();
            link = link.replace("\"", "");
            link = link.replace("\"", "");
        }
        return link;
    }
    public Double extractTime (String DOCText) {
        String PATTERN = "(\"[0-9]+(.[0-9]+)?\")";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(DOCText);

        String link = null;
        double time = 0;
        while (matcher.find()) {
            link = matcher.group();
            link = link.replace("\"", "");
            link = link.replace("\"", "");
            time = Double.parseDouble(link);
        }
        return time;
    }
    public String extractLink(String DOCText) {
        String PATTERN = "(\"([^\"]*\"))";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(DOCText);

        String link = null;
        while (matcher.find()) {
            link = matcher.group();
            link = link.replace("\"", "");
            link = link.replace("\"", "");
        }
        return link;
    }
}
