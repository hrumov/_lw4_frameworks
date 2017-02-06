
import java.util.regex.Pattern;
import java.io.PrintWriter;
import java.util.regex.Matcher;

public class Utils {
	public static boolean validateUrl(String url) {
		Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher m = p.matcher(url);
		return m.matches();
	}

	public static void exit(String log, PrintWriter writer) {
		log += "Total tests: " + 0 + "\n";
		log += "Passed/Failed: " + 0 + "/" + 0 + "\n";
		log += "Total time: " + 0 + "\n";
		log += "Average time: " + 0;
		writer.write(log);
		writer.close();
		System.exit(0);
	}
}
