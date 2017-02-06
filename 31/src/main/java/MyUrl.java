import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUrl {

    static Logger log = Logger.getLogger(MyUrl.class.getName());

    //The method that defines page is opened or not
    public boolean openUrl(String urlString) {
        boolean internetStatus;
        try {
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            con.connect();
            internetStatus = true;
        } catch (MalformedURLException e) {
            internetStatus = false;
            log.debug("The URL (\"" + urlString + "\") is not in a valid form.");
        } catch (IOException e) {
            internetStatus = false;
            log.debug("The connection can't be established.");
        }
        return internetStatus;
    }

    public StringBuilder connectUrl(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            int n = 0;
            int totalRead = 0;
            char[] buf = new char[1024];

            while (totalRead < 8192 && (n = reader.read(buf, 0, buf.length)) != -1) {
                content.append(buf, 0, n);
                totalRead += n;
            }
            reader.close();
        } catch (MalformedURLException e) {
            log.debug("The URL (\"" + urlString + "\") is not in a valid form.");
        } catch (IOException e) {
            log.debug("The connection can't be established.");
        }

        return content;
    }


    //The method, which determines whether there is a link to the page
    public boolean checkLinkPresentByHref(String urlString, String href) {
        StringBuilder content = connectUrl(urlString);
        boolean checkLink = false;

        Pattern pattern = Pattern.compile("<a href=\"(.*?)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String link = matcher.group(1);
            if (link.equals(href))
                checkLink = true;
        }
        return checkLink;
    }

    // The method, which determines whether there is a reference to the name of the page
    public boolean checkLinkPresentByName(String urlString, String name) {
        boolean checkLink = false;
        StringBuilder content = connectUrl(urlString);

        Pattern pattern = Pattern.compile("\">(.*?)</a>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String link = matcher.group(1);
            if (link.equals(name))
                checkLink = true;
        }
        return checkLink;
    }


    // The method, which determines the Title for the requested page
    public String getPageTitle(String urlString) {
        String title = "";
        StringBuilder content = connectUrl(urlString);

        Pattern pattern = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            title = matcher.group(1);
        }
        return title;
    }


    //Find text on this page
    public boolean checkOnPage(String urlString, String name) {
        boolean check = false;

        try {
            URL url = new URL(urlString);
            String line;

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);

            while (((line = bufReader.readLine()) != null)) {
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    check = true;
                }
            }
        } catch (MalformedURLException e) {
            log.debug("The URL (\"" + urlString + "\") is not in a valid form.");
        } catch (IOException e) {
            log.debug("The connection can't be established.");
        }
        return check;
    }
}


