
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    private static final Logger logger = Logger.getLogger(Command.class);
    static int countPassed = 0;
    static int countFailed = 0;
    private static URL url;
    private static URLConnection conn = null;
    private static boolean wasOpened = false;


    public static void open(String[] params) {
        String path = params[0];
        try {
            url = new URL(path);
            conn = url.openConnection();
            int timeout = Integer.parseInt(params[1]);
            conn.setConnectTimeout(timeout);
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wasOpened = true;
        if (wasOpened) {
            logger.info("+ ");
            countPassed++;
        } else {
            logger.info("! ");
            countFailed++;
        }
    }

    public static void checkLinkPresentByHref(String[] params) {
        if (!wasOpened) {
            logger.info("! ");
            countFailed++;
            return;
        }
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String inputText = "";
            while ((inputLine = in.readLine()) != null) {
                inputText += inputLine;
            }
            if (inputText.contains("href=\"" + params[0] + "\"")) {
                logger.info("+ ");
                countPassed++;
            } else {
                logger.info("! ");
                countFailed++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkLinkPresentByName(String[] params) {
        if (!wasOpened) {
            logger.info("The connection wasn't created");
            countFailed++;
            return;
        }
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String inputText = "";
            while ((inputLine = in.readLine()) != null) {
                inputText += inputLine;
            }
            if (getLinkName(inputText).contains(params[0])) {
                logger.info("+ ");
                countPassed++;
            } else {
                logger.info("! ");
                countFailed++;
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void checkPageTitle(String[] params) {
        if (!wasOpened) {
            logger.info("The connection wasn't created");
            countFailed++;
            return;
        }
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String inputText = "";
            while ((inputLine = in.readLine()) != null) {
                inputText += inputLine;
            }
            if (inputText.contains("<title>" + params[0] + "</title>")) {
                logger.info("+ ");
                countPassed++;
            } else {
                logger.info("! ");
                countFailed++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void checkPageContains(String[] params) {
        if (!wasOpened) {
            logger.info("The connection wasn't created");
            countFailed++;
            return;
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String inputText = "";
            while ((inputLine = in.readLine()) != null) {
                inputText += inputLine;
            }
            if (inputText.contains(params[0])) {
                logger.info("+ ");
                countPassed++;
            } else {
                logger.info("! ");
                countFailed++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Находит "человекочитаемую" часть ссылки с помощью регулярного выражения*/
    public static String getLinkName(String inputText) {
        String regex = "(<a href=\".*?\">\\w+.*</a>)+?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputText);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }
}