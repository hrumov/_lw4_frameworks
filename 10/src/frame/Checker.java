
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Checker {

	private URL url;
	private double timeout;
	private String href;
	private String linkName;
	private String titleText;
	private String containsText;

	private double startTime;
	private double finishTime;

	private String successFlag = "+";
	private int testsPassed;
	private int testsSum;
	private double totalTime;

	public void checkAndSplit() throws IOException {
		ArrayList<String> commandLines = new ArrayList<>();

		FileAction file = new FileAction();
		commandLines = file.readLines();
		Command c = new Command();
		
		file.createLog("logFile.txt");
		
		for (String commandLine : commandLines) {
			String[] buf = commandLine.split(" \"");
			String secondParameter;
			c.setCommand(buf[0]);

			if ((c.getCommand().equals("open") && buf.length != 3)
					|| (!c.getCommand().equals("open") && buf.length != 2)) {
				System.out.println("ERROR: Wrong number of parameters in " + c.getCommand()
						+ " command. Number of parameters: 2 in open command, 1 in other command types.");
			} else {
				secondParameter = buf[1].substring(0, buf[1].length() - 1);
				//System.out.println(" --- " + c.getCommand() + " --- [" + secondParameter +"]");
				switch (c.getCommand()) {
				case ("open"):
					String thirdParam = buf[2].substring(0, buf[2].length() - 1);
					try {
						double thirdParameter = Double.parseDouble(thirdParam);
						open(secondParameter, thirdParameter, commandLine, c, file);
					} catch (UnknownHostException UnknownHostException) {
						successFlag = "!";
						testsSum++;
						file.writeLog(successFlag, commandLine, 0);
						System.out.println("ERROR: UnknownHostException. Check Internet connection.");
					} catch (Exception NumberFormatException) {
						System.out.println("ERROR: NumberFormatException. Check the 2d parameter in open command.");
					}
					break;
				case ("checkLinkPresentByHref"):
					checkLinkPresentByHref(secondParameter, commandLine, c, file);
					break;
				case ("checkLinkPresentByName"):
					checkLinkPresentByName(secondParameter, commandLine, c, file);
					break;
				case ("checkPageTitle"):
					checkPageTitle(secondParameter, commandLine, c, file);
					break;
				case ("checkPageContains"):
					checkPageContains(secondParameter, commandLine, c, file);
					break;
				default:
					c.ifCommandNotFound(c.getCommand());
					break;
				}
			}
		}
		file.writeStatistics(testsSum, testsPassed, totalTime);
	}

	private void open(String secondParameter, double thirdParameter, String commandLine, Command c, FileAction file)
			throws IOException {
		url = new URL(secondParameter);
		timeout = thirdParameter;
		startTime = System.currentTimeMillis();
		Command.open(url, timeout); // c.open(url, timeout);
		finishTime = System.currentTimeMillis();
		if ((finishTime - startTime) / 1000 > timeout) {
			successFlag = "!";
		} else {
			successFlag = "+";
			testsPassed++;
		}
		testsSum++;
		totalTime += finishTime - startTime;

		file.writeLog(successFlag, commandLine, (finishTime - startTime) / 1000);
	}

	private void checkLinkPresentByHref(String secondParameter, String commandLine, Command c, FileAction file) {
		href = secondParameter;
		startTime = System.currentTimeMillis();
		c.checkLinkPresentByHref(href);
		finishTime = System.currentTimeMillis();
		if (c.checkLinkPresentByHref(href)) {
			successFlag = "+";
			testsPassed++;
			//System.out.println("yes");
		} else {
			successFlag = "!";
			//System.out.println("no");
		}
		testsSum++;
		totalTime += finishTime - startTime;
		file.writeLog(successFlag, commandLine, (finishTime - startTime) / 1000);

	}

	private void checkLinkPresentByName(String secondParameter, String commandLine, Command c, FileAction file) {
		linkName = secondParameter;
		startTime = System.currentTimeMillis();
		c.checkLinkPresentByName(linkName);
		finishTime = System.currentTimeMillis();
		if (c.checkLinkPresentByName(linkName)) {
			successFlag = "+";
			testsPassed++;
			//System.out.println("yes");
		} else {
			successFlag = "!";
			//System.out.println("no");
		}
		testsSum++;
		totalTime += finishTime - startTime;
		file.writeLog(successFlag, commandLine, (finishTime - startTime) / 1000);
	}

	private void checkPageTitle(String secondParameter, String commandLine, Command c, FileAction file) {
		titleText = secondParameter;
		startTime = System.currentTimeMillis();
		c.checkPageTitle(titleText);
		finishTime = System.currentTimeMillis();
		if (c.checkPageTitle(titleText)) {
			successFlag = "+";
			testsPassed++;
			//System.out.println("yes");
		} else {
			successFlag = "!";
			//System.out.println("no");
		}
		testsSum++;
		totalTime += finishTime - startTime;

		file.writeLog(successFlag, commandLine, (finishTime - startTime) / 1000);
	}

	private void checkPageContains(String secondParameter, String commandLine, Command c, FileAction file) {
		containsText = secondParameter;
		startTime = System.currentTimeMillis();
		c.checkPageContains(containsText);
		finishTime = System.currentTimeMillis();
		if (c.checkPageContains(containsText)) {
			successFlag = "+";
			testsPassed++;
			//System.out.println("yes");
		} else {
			successFlag = "!";
			//System.out.println("no");
		}
		testsSum++;
		totalTime += finishTime - startTime;
		file.writeLog(successFlag, commandLine, (finishTime - startTime) / 1000);
	}
}
