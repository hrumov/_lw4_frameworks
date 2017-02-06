
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckLinkPresentByName {

	public ArrayList<String> parts = new ArrayList<String>();

	public static final String REGEX_A_TAG = "(?i)<a([^>]+)>(.+?)</a>";

	// "^\\s*href\\s*=\\s*(\\\"([^\"]*\\\")|'[^']*'|([^'\">\\s]+))";
	public boolean findElements(String data, String str) {
		Pattern pattern = Pattern.compile(REGEX_A_TAG);

		Matcher matcher = pattern.matcher(data);

		while (matcher.find()) {

			parts.add(matcher.group(2));
		}

		Iterator<String> itr1 = parts.iterator();
		System.out.println("__________________outCheckLinkPresentByName_____________________");
		while (itr1.hasNext()) {

			System.out.println(itr1.next());

		}

		for (String link : parts) {

			if (link.indexOf(str) != -1) {
				System.out.println("Name of the link is found--> "+str );
				return true;
			}
		}
		return false;

	}

}
