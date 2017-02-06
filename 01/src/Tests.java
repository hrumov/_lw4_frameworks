import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
class Tests {

    private static String pageContent = new String();

    static boolean open(String url, int timeout) {
        URL page = null;

        try {
            page = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Incorrect URL format, request interrupted.");
            return false;
        }

        long finishTime = 0;
        long startTime = System.currentTimeMillis();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine + System.lineSeparator());
            }
            finishTime = System.currentTimeMillis();
            in.close();
            pageContent = content.toString();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        long downloadTime = finishTime - startTime;

        return (downloadTime / 1000.0) <= timeout;
    }

    static boolean checkLinkPresentByHref(String href) {
        return pageContent.contains("href=\"" + href + "\"");
    }

    static boolean checkLinkPresentByName(String linkName) {
        Pattern p = Pattern.compile("<a .*>" + linkName + "</a>");
        Matcher m = p.matcher(pageContent);

        return m.find();
    }

    static boolean checkPageTitle(String text) {
        return pageContent.contains("<title>" + text + "</title>");
    }

    static boolean checkPageContains(String text) {
        return pageContent.contains(text);
    }

}
