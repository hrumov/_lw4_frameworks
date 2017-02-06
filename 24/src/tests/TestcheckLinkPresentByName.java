
import static org.junit.Assert.*;

import java.io.IOException;

import logic.Frame;

import org.junit.Before;
import org.junit.Test;

public class TestcheckLinkPresentByName {

	private Frame fr = new Frame();
	private String expected;
	@Before
	public void setUp() throws IOException{
		fr.open(System.getProperty("openlink"));
		expected = System.getProperty("name");
	}
	@Test
	public void testcheckLinkPresentByName() {
		boolean flag = false;
		for (String str : fr.textURL) {
			if (str.contains("<a ") && str.contains("</a>")) {
				int start = str.indexOf("<a ");
				int end = str.indexOf("</a>");
				String sub = str.substring(start, end);
				if (sub.contains(expected)) {
					flag = true;
				}
			}
		}
		assertTrue(flag);
	}

}
