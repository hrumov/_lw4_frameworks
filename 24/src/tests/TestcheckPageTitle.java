
import static org.junit.Assert.*;

import java.io.IOException;

import logic.Frame;

import org.junit.Before;
import org.junit.Test;

public class TestcheckPageTitle {

	private Frame fr = new Frame();
	private String expected;
	@Before
	public void setUp() throws IOException{
		fr.open(System.getProperty("openlink"));
		expected = System.getProperty("title");
	}
	@Test
	public void testCheckPageTitle() {
		boolean flag = false;
		String actual = "<title>" + expected + "</title>";
		for (String str : fr.textURL) {
			if (str.contains(actual)) {
				flag = true;
			}
		}
		assertTrue(flag);
	}

}
