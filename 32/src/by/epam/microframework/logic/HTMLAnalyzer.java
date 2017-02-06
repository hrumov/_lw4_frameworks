
import by.epam.microframework.exception.HTMLAnalyzerException;
import by.epam.microframework.exception.HTMLReaderException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLAnalyzer {

	private static final Pattern URL_REF_PATTERN = Pattern.compile("(?<=<a)(.*)(?=>)");
	private static final Pattern NAME_REF_PATTERN = Pattern.compile("<a([^</]|\\n)*</");
	private static final Pattern TITTLE_PATTERN = Pattern.compile("(?<=<title>)(.*)(?=</title>)");
	private String title;
	private String pageContent;
	private List<String> refList = new ArrayList<String>();
	private List<String> nameRefList = new ArrayList<String>();

	public boolean open(String url, long timeout) throws MalformedURLException, HTMLAnalyzerException {
		try {
			long totalTime = -System.currentTimeMillis();
			URLConnection connection = new URL(url).openConnection();
			readHtml(connection);
			totalTime += System.currentTimeMillis();
			return totalTime < timeout;
		} catch (IOException e) {
			throw new HTMLAnalyzerException(e);
		}
	}

	private void readHtml(URLConnection connection) throws HTMLAnalyzerException {
		try {
			pageContent = new HTMLPageReader(connection.getInputStream()).read();
			initParsedPageContent(pageContent);

		} catch (HTMLReaderException | IOException e) {
			throw new HTMLAnalyzerException(e);
		}
	}

	private void initParsedPageContent(String pageContent) {
		Matcher matcher = URL_REF_PATTERN.matcher(pageContent);
		while (matcher.find()) {
			refList.add(matcher.group());
		}
		 matcher = NAME_REF_PATTERN.matcher(pageContent);
		 if (matcher.find()) {
		 nameRefList.add(matcher.group());
		 }
		matcher = TITTLE_PATTERN.matcher(pageContent);
		while (matcher.find()) {
			this.title = matcher.group();
		}
	}

	public boolean checkLinkPresentByHref(String href) {
		if (refList == null) {
			throw new IllegalStateException("no page content");
		}
		for (String ref : refList) {
			if (ref.contains(href)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkLinkPresentByName(String linkName) {
		if (nameRefList == null) {
			throw new IllegalStateException("no page content");
		}
		for (String name : nameRefList) {
			if (name.contains(linkName)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkPageTitle(String title) {
		if (this.title == null) {
			throw new IllegalStateException("no page content");
		}
		return this.title.equals(title);
	}

	public boolean checkPageContains(String text) {
		return pageContent.contains(text);
	}

}
