
import java.io.*;
import java.net.*;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class webTestingFramework 
{
	String webPage = "";
	double lastTestTime = 0;

	public double getLastTestTime()
	{
		return lastTestTime;
	}
	
	public boolean open(String surl, int timeout) throws IOException, URISyntaxException
	{
		URL url = new URL(surl);
		String line = "";
		long timeA = (new Date()).getTime();
		boolean res = false;
		
  
		URLConnection con = url.openConnection();
		con.setConnectTimeout(timeout * 1000);
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null) 
        {
            webPage += line;
        }
		
        if (is != null)
        {
        	is.close();
        	res = true;
        }

        lastTestTime = ((new Date()).getTime() - timeA);
        
		return res;
	}
	
	public boolean checkLinkPresentByHref(String href)
	{
		long timeA = (new Date()).getTime();
		boolean res = webPage.contains("href=\"" + href + "\"");
		
		lastTestTime = ((new Date()).getTime() - timeA);
		
		return res;
	}
	
	public boolean checkLinkPresentByName(String linkName)
	{
		long timeA = (new Date()).getTime();
		boolean res = false;
		String TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
		Pattern tagPattern = Pattern.compile(TAG_PATTERN);
		
		
		Matcher tagMatcher = tagPattern.matcher(webPage);
		
		while(tagMatcher.find())
		{
			if((tagMatcher.group(2)).equalsIgnoreCase(linkName))
			{
				res = true;
				break;
			}
		}
		
		lastTestTime = ((new Date()).getTime() - timeA);
		
		return res;
	}
	
	public boolean checkPageTitle(String title)
	{
		long timeA = (new Date()).getTime();
		boolean res = webPage.contains("<title>" + title + "</title>");
		
		lastTestTime = ((new Date()).getTime() - timeA);
		
		return res;
	}
	
	public boolean checkPageContains(String text)
	{
		long timeA = (new Date()).getTime();
		boolean res = webPage.contains(text);
		lastTestTime = ((new Date()).getTime() - timeA);
		
		return res;
	}
}


