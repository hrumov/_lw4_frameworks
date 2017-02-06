
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CheckPageTitle {
	public static boolean checkPageTitle(String title) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(
				"D://workspace//FrameWork//src//epam//File.txt"));
		String line;
		Date dateStart = new Date();
		while ((line = reader.readLine()) != null) {
			if (line.contains(title)) {
				Date dateEnd = new Date();
				long timeStart = dateStart.getTime();
				long timeEnd = dateEnd.getTime();
				long duration = timeEnd - timeStart;
				double time = duration * 1.0 / 1000;
				reader.close();
				String aTime = "+ [checkPageTitle \"" + title + "\"] " + time;
				try (FileWriter writer = new FileWriter(
						"D://workspace//FrameWork//src//epam//LogFile.txt",
						true)) {
					writer.write(aTime);
					writer.append('\n');
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
				return true;
			}
		}
		Date dateEnd = new Date();
		long timeStart = dateStart.getTime();
		long timeEnd = dateEnd.getTime();
		long duration = timeEnd - timeStart;
		double time = duration * 1.0 / 1000;
		String aTime = "! [checkPageTitle \"" + title + "\"] " + time;
		try (FileWriter writer = new FileWriter(
				"D://workspace//FrameWork//src//epam//LogFile.txt", true)) {
			writer.write(aTime);
			writer.append('\n');
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
}