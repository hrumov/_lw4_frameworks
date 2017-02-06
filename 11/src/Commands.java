import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Commands 
{
	public static String page;
	static String url;

	public static boolean open(String url1, String timeout)
	{
		url = url1;
		Scanner sc = new Scanner(timeout);
		if (!sc.hasNextInt())
			return false;
		try {
			URL myURL = new URL(url);
			URLConnection uRLConnection = myURL.openConnection();
			uRLConnection.setConnectTimeout((Integer.parseInt(timeout)) * 1000);
			uRLConnection.connect();

			//saving web page to string "page"
			sc = new Scanner(new InputStreamReader(uRLConnection.getInputStream()));
			page = "";
			while (sc.hasNextLine()) 
			{
				page += sc.nextLine() + "\r\n";
			}
			sc.close();
			return true;
		} 
		catch (MalformedURLException e) { 
			// new URL() failed
			e.printStackTrace();
			return false;
		} 
		catch (IOException e) {   
			// openConnection() failed
			e.printStackTrace();
			return false;
		}
	}

	public static boolean checkLinkPresentByHref (String href) 
	{
		if (page.contains("href=\""+href+"/\""))
			return true;
		else
			return false;
	}

	public static boolean checkPageTitle (String title) 
	{
		if (page.contains("<title>"+title+"</title>"))
			return true;
		else
			return false;
	}

	public static boolean checkPageContains (String txt)
	{
		if (page.contains(txt))
			return true;
		else
			return false;
	}

	public static boolean checkLinkPresentByName (String name)
	{
		boolean nameCheck = false;
		String page1 = page;
		while (page1.indexOf("<a href") != -1)
		{
			page1 = page1.substring(page1.indexOf("<a"));
			if (page1.indexOf(name)>0 && page1.indexOf(name)<page1.indexOf("/a>"))
			{
				nameCheck = true;
				break;
			}
			page1 = page1.substring(page1.indexOf("/a>"));
		}
		return nameCheck;
	}
}
