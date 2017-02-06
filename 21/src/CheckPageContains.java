
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CheckPageContains {
	public static boolean checkPageContains(String text) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(
				"D://workspace//FrameWork//src//epam//File.txt"));
		String line;
		Date dateStart = new Date();
		while ((line = reader.readLine()) != null) {
			if (line.contains(text)) {
				Date dateEnd = new Date();
				long timeStart = dateStart.getTime();
				long timeEnd = dateEnd.getTime();
				long duration = timeEnd - timeStart;
				double time = duration * 1.0 / 1000;
				reader.close();
				String con = "+ [checkPageContains  \"" + text + "\"] " + time;
				try (FileWriter writer = new FileWriter(
						"D://workspace//FrameWork//src//epam//LogFile.txt",
						true)) {
					writer.write(con);
					writer.append('\n');
					writer.flush();
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
		String con = "! [checkPageContains  \"" + text + "\"] " + time;
		try (FileWriter writer = new FileWriter(
				"D://workspace//FrameWork//src//epam//LogFile.txt", true)) {
			writer.write(con);
			writer.append('\n');
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
}