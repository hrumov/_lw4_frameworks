
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import epam.training.models.Page;

public class PageService {	
	
	private static final Logger LOG = LogManager.getLogger(PageService.class);

	public String returnUrl() {
		String input = "src\\main\\resources\\input-data.txt";
		String url;
		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("open")) {
					url = sCurrentLine.substring(6, sCurrentLine.length() - 5);
					return url;
				}
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
		throw new RuntimeException();
	}
	
	public int returnTimeout() {
		String input = "src\\main\\resources\\input-data.txt";
		String timeout;
		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("open")) {
					timeout = sCurrentLine.substring(sCurrentLine.length() - 2, sCurrentLine.length() - 1);
					return Integer.parseInt(timeout);
				}
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
		throw new RuntimeException();
	}

	public Page open() {
		Page page = new Page();
		try {
			String url = returnUrl();
			page.setUrl(url);			
			Document doc = Jsoup.connect(page.getUrl()).timeout(returnTimeout()*1000).get();
			String html = doc.html();
			page.setHtml(html);
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
		return page;
	}

	public void checkPageIsOpened() {
		double st;
		st = System.currentTimeMillis();
		Page page = open();
		double timeout = (System.currentTimeMillis() - st) / 1000;
		if (page.getHtml() != null) {
			LOG.info("\n+ [open \"" + page.getUrl() + "\" " + "\"" + returnTimeout() + "\"]" + " " + timeout);
		} else {
			LOG.info("\n! [open \"" + page.getUrl() + "\" " + "\"" + returnTimeout() + "\"]" + " " + timeout);
		}
	}

	public void checkLinkPresentByHref(String href) {
		try {
			double st;
			st = System.currentTimeMillis();
			Page page = open();
			Document doc = Jsoup.connect(page.getUrl()).get();
			Set<String> linksSet = new HashSet<String>();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				linksSet.add(link.attr("abs:href").toString());
			}
			List<String> linksArrayList = new ArrayList<String>();
			linksArrayList.addAll(linksSet);
			page.setLinks(linksArrayList);
			double timeout = (System.currentTimeMillis() - st) / 1000;
			if (page.getLinks().contains(href)) {
				LOG.info("+ [checkLinkPresentByHref \"" + href + "\"]" + " " + timeout);
			} else {
				LOG.info("! [checkLinkPresentByHref \"" + href + "\"]" + " " + timeout);
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

	public void checkLinkPresentByName(String name) {
		try {
			double st = System.currentTimeMillis();
			Page page = open();
			Document doc = Jsoup.connect(page.getUrl()).get();
			Set<String> linksSet = new HashSet<String>();
			Elements links = doc.select("a");
			for (Element link : links) {
				linksSet.add(link.text());
			}
			List<String> linksArrayList = new ArrayList<String>();
			linksArrayList.addAll(linksSet);
			page.setLinkNames(linksArrayList);
			double timeout = (System.currentTimeMillis() - st) / 1000;
			if (page.getLinkNames().contains(name)) {
				LOG.info("+ [checkLinkPresentByName \"" + name + "\"]" + " " + timeout);
			} else {
				LOG.info("! [checkLinkPresentByName \"" + name + "\"]" + " " + timeout);
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

	public void checkPageTitle(String title) {
		try {
			double st = System.currentTimeMillis();
			Page page = open();
			Document doc = Jsoup.connect(page.getUrl()).get();
			String titl = doc.title();
			page.setTitle(titl);
			double timeout = (System.currentTimeMillis() - st) / 1000;
			if (page.getTitle().equals(title)) {
				LOG.info("+ [checkPageTitle \"" + title + "\"]" + " " + timeout);
			} else {
				LOG.info("! [checkPageTitle \"" + title + "\"]" + " " + timeout);
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

	public void checkPageContains(String text) {
		double st = System.currentTimeMillis();
		Page page = open();
		double timeout = (System.currentTimeMillis() - st) / 1000;
		if (page.getHtml().contains(text)) {
			LOG.info("+ [checkPageContains \"" + text + "\"]" + " " + timeout);
		} else {
			LOG.info("! [checkPageContains \"" + text + "\"]" + " " + timeout);
		}
	}

}
