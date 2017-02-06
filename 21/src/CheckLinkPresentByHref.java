
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CheckLinkPresentByHref {
	public static boolean checkLinkPresentByHref(String href) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(
				"D://workspace//FrameWork//src//epam//File.txt"));
		String line;
		Date dateStart = new Date();
		double time;
		while ((line = reader.readLine()) != null) {
			if (line.contains(href)) {
				Date dateEnd = new Date();
				long timeStart = dateStart.getTime();
				long timeEnd = dateEnd.getTime();
				long duration = timeEnd - timeStart;
				time = duration * 1.0 / 1000;
				reader.close();
				String hr = "+ [checkLinkPresentByHref \"" + href + "\" ] "
						+ time;
				try (FileWriter writer = new FileWriter(
						"D://workspace//FrameWork//src//epam//LogFile.txt",
						true)) {
					writer.write(hr);
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
		time = duration * 1.0 / 1000;
		System.out.println("! [checkLinkPresentByHref \"" + href + "\"] "
				+ time);
		return false;
	}
}
