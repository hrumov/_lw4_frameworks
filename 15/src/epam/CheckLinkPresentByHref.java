
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckLinkPresentByHref {
	public ArrayList<String> parts = new ArrayList<String>();
	

	public static final String REGEX_HREF ="\\s*https?:([^\"']*)";
	//regex full link--- href = "http://www.w3schools.com/html/"
	//"\\s*(?i)href\\s*=\\s*(\\\"([^\"]*\\\")|'[^']*'|([^'\">\\s]+))"; 
	public boolean findElements(String data, String str) {
		Pattern pattern = Pattern.compile(REGEX_HREF);
		String links = "";
		Matcher matcher = pattern.matcher(data);

		while (matcher.find()) {
			links = matcher.group();
			parts.add(links);
		}
	
		System.out.println("__________________outCheckLinkPresentByHref_____________________");
		for(String result:parts){
			System.out.println(result);
			if (result.equals(str)) {
				System.out.println("Have matches!!!");

				return true;
			}
		}
	
		return false;

	}

}
