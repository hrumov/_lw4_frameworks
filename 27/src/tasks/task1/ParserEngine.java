
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ParserEngine {
	private ArrayList<String> command;
	private Logger logger;
	private String content;
	private String href;
	private String link_name;
	private String title_text;
	private String contains_text;
	Long time;

	public ParserEngine(String filename) {
		this.logger = new Logger();
		this.command = new ArrayList<String>();
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String tmpStr = "";
			this.content = "";
			while ((tmpStr = in.readLine()) != null) {
				command.add(tmpStr);
			}
		} catch (Exception e) {
			System.out.println("Wrong filename!");
			e.printStackTrace();
		}
	}

	public void runEngine() {
		for (String com : command) {
			if (com.contains("open")) {
				open(com);
			}
			if (com.contains("checkLinkPresentByHref")) {
				checkLinkByHref(com);
			}
			if (com.contains("checkLinkPresentByName")) {
				checkLinkByName(com);
			}
			if (com.contains("checkPageTitle")) {
				checkPageTitle(com);
			}
			if (com.contains("checkPageContains")) {
				checkPageContains(com);
			}
		}
		logger.logToFile();
	}

	private void checkPageContains(String com) {
		Long start = System.currentTimeMillis();
		int pos1, pos2;
		pos1 = com.indexOf("\"");
		pos2 = com.indexOf("\"", pos1 + 1);
		this.contains_text = com.substring(pos1 + 1, pos2);
		Boolean passed = false;
		if (this.content != null) {
			if (this.content.contains(this.contains_text)) {
				passed = true;
			}
		}
		Long stop = System.currentTimeMillis() + 1;
		this.logger.newTest(passed, com, stop - start);
	}

	private void checkPageTitle(String com) {
		Long start = System.currentTimeMillis();
		int pos1, pos2;
		int posA = 0;
		int posB = 0;
		pos1 = com.indexOf("\"");
		pos2 = com.indexOf("\"", pos1 + 1);
		this.title_text = com.substring(pos1 + 1, pos2);
		Boolean passed = false;
		if (this.content != null) {
			posA = this.content.indexOf("<title>");
			posB = this.content.indexOf("</title>");
			if ((posA + 7 < this.content.length()) & (posB <= this.content.length())) {
				passed = this.title_text.equalsIgnoreCase(this.content.substring(posA + 7, posB));
			}
		}
		Long stop = System.currentTimeMillis() + 1;
		this.logger.newTest(passed, com, stop - start);
	}

	private void checkLinkByName(String com) { // Potential dangerous. Needs for
												// improvement
		Long start = System.currentTimeMillis();
		int pos1, pos2;
		int posA = 0;
		int posB = 0;
		pos1 = com.indexOf("\"");
		pos2 = com.indexOf("\"", pos1 + 1);
		this.link_name = com.substring(pos1 + 1, pos2);
		Boolean passed = false;
		if (this.content != null) {
			while (posA >= 0 & posA < this.content.length()) {
				posA = this.content.indexOf("<a ", posB);
				posB = this.content.indexOf("</a>", posA);
				if (subRoutine(posA, posB + 1)) {
					passed = true;
					break;
				}
			}
		}
		Long stop = System.currentTimeMillis() + 1;
		this.logger.newTest(passed, com, stop - start);

	}

	private Boolean subRoutine(int posA, int posB) { // Checks if link is
														// between <a href> ...
														// </a>
		if (posA < 0 || posB < 0) {
			return false;
		}
		String tmpStr = this.content.substring(posA, posB);
		int pos;
		if (tmpStr.indexOf("href=") > 0) {
			pos = tmpStr.indexOf(">");
			if (tmpStr.substring(pos, tmpStr.length()).indexOf(this.link_name) > 0) {
				return true;
			}
		}
		return false;
	}

	private void checkLinkByHref(String com) {
		Long start = System.currentTimeMillis();
		int pos1, pos2;
		int hrefPos = 0;
		pos1 = com.indexOf("\"");
		pos2 = com.indexOf("\"", pos1 + 1);
		this.href = com.substring(pos1 + 1, pos2);
		if (this.content != null) {
			hrefPos = this.content.indexOf("href=\"" + this.href + "\"");
		}

		Long stop = System.currentTimeMillis() + 1;
		Boolean passed = false;
		if (hrefPos > 0) {
			passed = checkIfTagCorrect(hrefPos);
		}
		this.logger.newTest(passed, com, stop - start);

	}

	private Boolean checkIfTagCorrect(int hrefPos) {  //Find first '<' symbol moving left from pos
		if (hrefPos < 0) {							  //and checks if it's "a" tag
			return false;
		}
		int pos = hrefPos;
		if (this.content != null) {
			while (pos >= 0) {
				pos--;
				if (this.content.charAt(pos) == '<') {
					return (this.content.charAt(pos + 1) == 'a');
				}
			}
		}
		return false;
	}

	private void open(String com) {
		int pos1, pos2, pos3, pos4, timeout;
		boolean passed = false;
		String url, t;
		timeout = 0;
		url = "";
		pos1 = com.indexOf("\"");
		pos2 = com.indexOf("\"", pos1 + 1);
		if (pos1 > 0 & pos2 > 0) {
			url = com.substring(pos1 + 1, pos2);
			pos3 = com.indexOf("\"", pos2 + 1);
			pos4 = com.indexOf("\"", pos3 + 1);
			t = com.substring(pos3 + 1, pos4);
			try {
				double tmpD = Double.parseDouble(t);
				tmpD = tmpD * 1000;
				timeout = (int) tmpD;
			} catch (Exception e) {
				System.out.println("Error! Timeout was given wrong!" + e.getMessage());
			}
		}
		HtmlReader reader = new HtmlReader(url, timeout);
		this.content = reader.open();
		if (this.content != null) {
			passed = true;
		}
		this.logger.newTest(passed, com, reader.getTime());
	}

}
