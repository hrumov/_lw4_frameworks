
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Commands {

	private Document doc;
	public boolean flag = false;

	public boolean open(String URL, int time) throws MalformedURLException, IOException {
		try {
			doc = Jsoup.connect(URL).timeout(time * 1000).get();
		} catch (UnknownHostException e) {
			return false;
		}
		flag = true;
		return true;
	}

	public boolean checkLinkPresentByHref(String line) {
		Elements links = doc.select("a[href]");
		for (Element el : links) {
			String linkHref = el.attr("href");
			if (linkHref.equals(line)) {
				return true;
			}
		}

		return false;

	}

	public boolean checkLinkPresentByName(String line) {
		Elements links = doc.select("a");
		for (Element el : links) {
			if (el.html().equals(line)) {
				return true;
			}
		}

		return false;

	}

	public boolean checkPageTitle(String line) {
		String pageTitle = doc.title();
		if (pageTitle.equals(line)) {
			return true;
		}
		return false;

	}

	public boolean checkPageContains(String line) {
		String pageText = doc.text();
		if (pageText.contains(line)) {
			return true;
		}

		return false;

	}
}
