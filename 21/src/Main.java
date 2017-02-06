
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Main {

	public static void main(String[] args) throws Exception {

		int totalTest = 0;
		int falseTest = 0;
		int trueTest = 0;
		Date dateStart = new Date();
		String file = "D://workspace//FrameWork//src//epam//Commands.txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String commands;
		while ((commands = reader.readLine()) != null) {
			String[] part = commands.split("\\|");
			switch (part[0]) {
			case "open":
				if (Open.open(part[1])) {
					trueTest++;
				} else {
					falseTest++;
				}
				totalTest++;
				break;
			case "checkLinkPresentByHref":
				if (CheckLinkPresentByHref.checkLinkPresentByHref(part[1])) {
					trueTest++;
				} else {
					falseTest++;
				}
				totalTest++;
				break;
			case "checkLinkPresentByName":
				if (CheckLinkPresentByName.checkLinkPresentByName(part[1])) {
					trueTest++;
				} else {
					falseTest++;
				}
				totalTest++;
				break;
			case "checkPageTitle":
				if (CheckPageTitle.checkPageTitle(part[1])) {
					trueTest++;
				} else {
					falseTest++;
				}
				totalTest++;
				break;
			case "checkPageContains":
				if (CheckPageContains.checkPageContains(part[1])) {
					trueTest++;
				} else {
					falseTest++;
				}
				totalTest++;
				break;
			}
		}
		Date dateEnd = new Date();
		long timeStart = dateStart.getTime();
		long timeEnd = dateEnd.getTime();
		long duration = timeEnd - timeStart;
		double time = duration * 1.0 / 1000;
		double average = time / totalTest;
		String total = "Total tests: " + totalTest;
		String tests = "Passed/Failed: " + trueTest + "/" + falseTest;
		String tTimes = "Total time: " + time;
		String aTime = "Average time: " + Math.rint(100.0 * average) / 1000.0;
		try (FileWriter writer = new FileWriter(
				"D://workspace//FrameWork//src//epam//LogFile.txt", true)) {
			writer.write(total);
			writer.append('\n');
			writer.write(tests);
			writer.append('\n');
			writer.write(tTimes);
			writer.append('\n');
			writer.write(aTime);
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}