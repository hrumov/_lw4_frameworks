
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPageTitle {

	public ArrayList<String> parts = new ArrayList<String>();
	public final String REGEX_TITLE = "(?<=<title>)(.*)(?=</title>)";

	public boolean findElements(String data, String str) {

	
		Pattern pattern = Pattern.compile(REGEX_TITLE);
		String links = "";
		Matcher matcher = pattern.matcher(data);

		while (matcher.find()) {
			links = matcher.group();
			parts.add(links);
		}

		System.out.println("__________________outCheckPageTitle_____________________");
		System.out.println(parts.toString());
		for (String match : parts) {
			if (match.equalsIgnoreCase(str)) {
				System.out.println("Have such title!--> "+str);
				return true;
			}else {
				System.out.println("No such title!!");
			}
		}
	
		return false;
	}

}
