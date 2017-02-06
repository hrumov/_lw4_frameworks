
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckInputDataService {

	private static final Logger LOG = LogManager.getLogger(CheckInputDataService.class);

	public void checkInputData() {

		PageService page = new PageService();

		String input = "src\\main\\resources\\input-data.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("open")) {
					page.checkPageIsOpened();
				} else if (sCurrentLine.contains("checkLinkPresentByHref")) {
					page.checkLinkPresentByHref(sCurrentLine.substring(24, sCurrentLine.length() - 1));
				} else if (sCurrentLine.contains("checkLinkPresentByName")) {
					page.checkLinkPresentByName(sCurrentLine.substring(24, sCurrentLine.length() - 1));
				} else if (sCurrentLine.contains("checkPageTitle")) {
					page.checkPageTitle(sCurrentLine.substring(16, sCurrentLine.length() - 1));
				} else if (sCurrentLine.contains("checkPageContains")) {
					page.checkPageContains(sCurrentLine.substring(19, sCurrentLine.length() - 1));
				}
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}
}
