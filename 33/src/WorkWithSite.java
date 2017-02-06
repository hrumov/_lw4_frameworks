import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorkWithSite {
	private String siteURL;
	private String htmlPage;

	WorkWithSite() {

	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public String getPageCode() throws IOException { // читаю html страницы в
														// строку
		URL url = new URL(siteURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		while (true) {
			htmlPage = br.readLine();
			if (br.readLine() == null) {
				break;
			}
		}
		br.close();
		return htmlPage;
	}

	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}
}
