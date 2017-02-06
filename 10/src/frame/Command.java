
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Command {

	public static String URLContent;
	private String command;
	static {
		URLContent = new String();
	}

	public void setCommand(String c) {
		this.command = c;
	}

	public static void open(URL url, double timeout) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		URLContent = "";
		while ((line = reader.readLine()) != null) {
			URLContent += line;
		}
		reader.close();
	}

	public boolean checkLinkPresentByHref(String href) {
		String s = URLContent.toLowerCase();
		if (s.contains("href=\"" + href + "\"")) {
			return true;
		} else {
			return false;
		}
	}

	// адаптировать для вложенных тегов?
	public boolean checkLinkPresentByName(String linkName) {
		if (URLContent.matches(".*[>][^<]*" + linkName + "<.*")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPageTitle(String text) {
		if (URLContent.contains("<title>" + text + "</title>")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPageContains(String text) {
		if (URLContent.contains(text)) {
			return true;
		} else {
			return false;
		}
	}

	public void ifCommandNotFound(String command) {
		System.out.println("command >>> " + command + " not found");
	}

	public String getCommand() {
		return command;
	}
}
