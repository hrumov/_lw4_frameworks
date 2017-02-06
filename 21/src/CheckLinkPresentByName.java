
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CheckLinkPresentByName {
	public static boolean checkLinkPresentByName(String name) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(
				"D://workspace//FrameWork//src//epam//File.txt"));
		Date dateStart = new Date();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(name)) {
				Date dateEnd = new Date();
				long timeStart = dateStart.getTime();
				long timeEnd = dateEnd.getTime();
				long duration = timeEnd - timeStart;
				double time = duration * 1.0 / 1000;
				reader.close();
				String nm = "+ [checkLinkPresentByName \"" + name + "\"] "
						+ time;

				try (FileWriter writer = new FileWriter(
						"D://workspace//FrameWork//src//epam//LogFile.txt",
						true)) {
					writer.write(nm);
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
		String nm = "! [checkLinkPresentByName \"" + name + "\"] " + time;

		try (FileWriter writer = new FileWriter(
				"D://workspace//FrameWork//src//epam//LogFile.txt", true)) {
			writer.write(nm);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
}