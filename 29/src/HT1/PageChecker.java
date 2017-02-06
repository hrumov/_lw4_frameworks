
/**
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PageChecker
{
    HttpURLConnection urlConnection;
    String contentStr;

    public boolean open(String strUrl, String timeout)
    {
        try
        {
            int timeoutInt = Integer.parseInt(timeout);
            URL url = new URL(strUrl);
            this.urlConnection = (HttpURLConnection) url.openConnection();
            this.urlConnection.connect();
            this.urlConnection.setConnectTimeout(timeoutInt);

            //get content from the page
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.urlConnection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
            this.contentStr = content.toString();

            return HttpURLConnection.HTTP_OK == this.urlConnection.getResponseCode();
        }
        catch (java.net.SocketTimeoutException e)
        {
            return false;
        }
        catch (IOException e)
        {
//                System.out.println("Error creating HTTP connection");
//                e.printStackTrace();
//                throw e;
            return false;
        }
    }

    public boolean checkLinkPresentByHref(String linkStr)
    {
        String linkConcat = "href=\"" + linkStr + "\"";
        boolean resultLink = this.contentStr.contains(linkConcat);
        return resultLink;
    }

    public boolean checkLinkPresentByName(String nameStr)
    {
        boolean resultLinkByName = this.contentStr.matches("(?s).*<a[^>]*>" + nameStr + "</a>(?s).*");
        return resultLinkByName;
    }

    public boolean checkPageTitle(String pageTitle)
    {
        String titleConcat = "<title>" + pageTitle + "</title>";
        boolean resultPageTitle = this.contentStr.contains(titleConcat);
        return resultPageTitle;
    }

    public boolean checkPageContains(String text)
    {
        boolean resultPageContains = this.contentStr.contains(text);
        return resultPageContains;
    }
}
