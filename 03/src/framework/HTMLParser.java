
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HTMLParser {
	
	private URLConnection connection;
	private String page_content = "";
	private String piece_of_page = "";
	private boolean test_failed;

	public HTMLParser (URLConnection connection) throws IOException {
		this.connection = connection;
		readPage ();
	}
	
	
	private void readPage() throws IOException {
		InputStream input = connection.getInputStream();
		int c;
		while (( c = input.read())!= -1) {
			System.out.print(c);
			page_content += (char) c;
		}
		input.close();
		
	}

	/*
	 * the method creates a parser with a parameter.
	 * Return true, if necessary text is not found.
	 */
	public static HTMLParser parseWebPage (URLConnection connection, String command, String parameter, 
			HTMLParser parser, boolean new_parser) 
			throws IOException {
		if ((parser == null)||new_parser){
			parser = new HTMLParser (connection);
		}
		switch (command) {
		case "checkLinkPresentByHref" :
			parser.checkLinkPresentByHref (parameter);
			break;
		case "checkLinkPresentByName" :
			parser.checkLinkPresentByName (parameter);
			break;
		case "checkPageTitle" :
			parser.checkPageTitle (parameter);
			break;
		case "checkPageContains" :
			parser.checkPageContains (parameter);
			break;
		}
		return parser;
	}
		
	//"checkLinkPresentByHref" command implementation
	private void checkLinkPresentByHref (String href) {
		test_failed = false;
		piece_of_page = page_content;
		String open_link = "<a";
		String bracket  = ">";
		String link_href = "href=";
		int index = parsingPage (open_link, link_href, bracket, href);
		
		if (index == -1) {
			return;
		} else {
			test_failed = false;
		}
	}	
	
	//"checkLinkPresentByName" command implementation
	private void checkLinkPresentByName (String link) {
		test_failed = false;
		piece_of_page = page_content;
		String open_link = "<a";
		String close_link = "</a>";
		String bracket  = ">";
		int index = parsingPage (open_link, bracket, close_link, link);
		
		if (index == -1) {
			return;
		} else {
			test_failed = false;
		}
	}
	
	/*
	 * The method separates read pieces of the page
	 * until it find necessary text or come to the
	 * end of page
	 */
	private int parsingPage (String first, String middle, String last, String text) {
		int last_index = page_content.length() -1;
		int read_symbols = 0;
		int index = -1;
		
		while (read_symbols < last_index) {
			piece_of_page = piece_of_page.substring(read_symbols);
			String content = findContent(first, last, piece_of_page);
			content = findContent(middle, last, content); 
			index = findSubstring(content, text);
		}
		return index;
	}
	
	//"checkPageTitle" command implementation
	private void checkPageTitle (String title) {
		String open_title = "<title";
		String close_title = "</title>";
		
		String content = findContent (open_title, close_title, page_content);
		int index = findSubstring (content, title);
		if (index == -1) {
			return;
		} else {
			test_failed = false;
		}
	}
	
	// this method searches for the text between preassigned symbols
	private String findContent (String open, String close, String search_field) {
		int start;
		int finish;
		String content;
		
		start = findSubstring (open, search_field);
		finish = findSubstring (close, search_field);
		if ((start == -1) || (finish == -1)) {
			content = "";
		} else {
			start += open.length();  
			content = getString (search_field, start, finish);
		}
		return content;
	}

	private String getString(String page_content, int start, int finish) {
		String string;
		string = page_content.substring(start, finish);
		return string;
	}

	//"checkPageContains" command implementation
	private void checkPageContains (String content) {
		test_failed = false;
		findSubstring (content, page_content);	
	}
	
	
	private int findSubstring (String substring, String string) {
		int index = -1;
		index = findIndex (substring, string);
		if (index ==-1) {
			test_failed = true;
		} 
		return index;
	}
	
	private int findIndex (String substring, String string) {
		int index = -1;
		index = string.indexOf(substring); 
		return index;
	}
	
	public boolean isTest_failed() {
		return test_failed;
	}


}
