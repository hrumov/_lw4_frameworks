
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Runner {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Enter two paths: java Framework input.txt log.txt");
			return;
		}

		String inputPath = args[0];
		String logPath = args[1];
		ArrayList<String> temp = new ArrayList<String>();
		WorkWithFiles workWithFiles = new WorkWithFiles(temp);
		Commands commands = new Commands();
		String logText = "";
		double totalTime = 0;
		int passedTests = 0, failedTests = 0, size = 0;
		ParseStrings ps = new ParseStrings();
		if (workWithFiles.readFile(inputPath)) {
			workWithFiles.getListCommands();
			if (ps.parseStr(temp)) {
				for (String line : temp) {
					double time = 0;
					switch (line.substring(0, line.indexOf("\"") - 1).trim()) {
					case "open":
						String URLTIME = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim();
						String URL = URLTIME.substring(0, URLTIME.indexOf("\"")).trim();
						int timeOut = Integer.valueOf(URLTIME.substring(URLTIME.lastIndexOf("\"") + 1).trim());
						double timeBegin1 = System.currentTimeMillis();
						if (commands.open(URL, timeOut)) {
							logText += "+ ";
							logText += "[" + line + "] ";
							passedTests++;
						} else {
							logText += "! ";
							logText += "[" + line + "] ";
							failedTests++;
						}
						time = (System.currentTimeMillis() - timeBegin1);
						logText += String.format("%.3f", (time / 1000)) + "\r\n";
						totalTime += time;
						size++;
						break;
					case "checkLinkPresentByHref":
						if (commands.flag) {
							String linkName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim();
							double timeBegin2 = System.currentTimeMillis();
							if (commands.checkLinkPresentByHref(linkName)) {
								logText += "+ ";
								logText += "[" + line + "] ";
								passedTests++;
							} else {
								logText += "! ";
								logText += "[" + line + "] ";
								failedTests++;
							}
							time = (System.currentTimeMillis() - timeBegin2);
							logText += String.format("%.3f", (time / 1000)) + "\r\n";
							totalTime += time;
							size++;
						}
						break;
					case "checkLinkPresentByName":
						if (commands.flag) {
							String titleName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim();
							double timeBegin3 = System.currentTimeMillis();
							if (commands.checkLinkPresentByName(titleName)) {
								logText += "+ ";
								logText += "[" + line + "] ";
								passedTests++;
							} else {
								logText += "! ";
								logText += "[" + line + "] ";
								failedTests++;
							}
							time = (System.currentTimeMillis() - timeBegin3);
							logText += String.format("%.3f", (time / 1000)) + "\r\n";
							totalTime += time;
							size++;
						}
						break;
					case "checkPageTitle":
						if (commands.flag) {
							String text = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim();
							double timeBegin4 = System.currentTimeMillis();
							if (commands.checkPageTitle(text)) {
								logText += "+ ";
								logText += "[" + line + "] ";
								passedTests++;
							} else {
								logText += "! ";
								logText += "[" + line + "] ";
								failedTests++;
							}
							time = (System.currentTimeMillis() - timeBegin4);
							logText += String.format("%.3f", (time / 1000)) + "\r\n";
							totalTime += time;
							size++;
						}
						break;
					case "checkPageContains":
						if (commands.flag) {
							String page = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")).trim();
							double timeBegin5 = System.currentTimeMillis();
							if (commands.checkPageContains(page)) {
								logText += "+ ";
								logText += "[" + line + "] ";
								passedTests++;
							} else {
								logText += "! ";
								logText += "[" + line + "] ";
								failedTests++;
							}
							time = (System.currentTimeMillis() - timeBegin5);
							logText += String.format("%.3f", (time / 1000)) + "\r\n";
							totalTime += time;
							size++;
						}
						break;
					default:
						System.out.println("Command incorrect: " + line);
						break;
					}
				}
				logText += "Total tests: " + size + "\r\n";
				logText += "Passed/Failed: " + passedTests + "/" + failedTests + "\r\n";
				logText += "Total time: " + String.format("%.3f", (totalTime / 1000)) + "\r\n";
				logText += "Average time: " + String.format("%.3f", (totalTime / 1000 / size));
				workWithFiles.writeFile(logPath, logText);
			} else
				System.out.println("Incorrect input.txt Example: " + "\r\n" + "open " + "\"URL\" " + "\"timeout\""
						+ "\r\n" + "checkLinkPresentByHref " + "\"href\"" + "\r\n" + "checkLinkPresentByName "
						+ "\"linkName\"" + "\r\n" + "checkPageTitle " + "\"text\"" + "\r\n" + "checkPageContains "
						+ "\"text\"");
		}
	}

}
