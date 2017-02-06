
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Commands {
	
	public static BufferedReader open(String ur) throws IOException {
		URL url = new URL(ur);
		BufferedReader reader = null;
		try{
		URLConnection connect = (URLConnection) url.openConnection();
		connect.connect();
		reader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
		}
		catch(java.net.UnknownHostException e){
			System.out.println("Can not connect.");
			System.exit(0);
		}
		return reader;
	}

	public static boolean checkLinkPresentByHref(String href, String input) {
		String checkLinkHref = "href=\"" + href + "\"";
		return input.contains(checkLinkHref);
	}

	public static boolean checkLinkPresentByName(String name, String input) {
		String checkLinkName = ">" + name + "</a>";
		return input.contains(checkLinkName);
	}

	public static boolean checkPageTitle (String text, String input) {
		String checkPageTitle = "<title>" + text + "</title>";
		return input.contains(checkPageTitle);
	}

	public static boolean checkPageContains (String text, String input) {
		return input.contains(text);
	}
}
