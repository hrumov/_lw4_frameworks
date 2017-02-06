import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class HTMLExtractor {
    public String extractLinkPresentByHref(String HTMLText, String Name) {
        String PATTERN = "(?i)href\\s*=\\s*(\"" + Name + "\"|'"+ Name + "')";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(HTMLText);

        String link = null;
        while (matcher.find()) {
            link = matcher.group();
        }
        return link;
    }
    public String extractPageTitle(String HTMLText, String Name) {
        String PATTERN = "(?i)<title>\\s*(" + Name + ")\\s*</title>";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(HTMLText);

        String link = null;
        while (matcher.find()) {
            link = matcher.group();
        }
        return link;
    }
    public String extractLinkPresentByName(String HTMLText, String Name) {
        String PATTERN = "(?i)<a([^>]+)>\\s*" + Name + "\\s*</a>";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(HTMLText);

        String link = null;
        while (matcher.find()) {
            link = matcher.group();
        }
        return link;
    }
}
