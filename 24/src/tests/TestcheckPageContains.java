
import static org.junit.Assert.*;

import java.io.IOException;

import logic.Frame;

import org.junit.Before;
import org.junit.Test;

public class TestcheckPageContains {
	private Frame fr = new Frame();
	private String expected;
	@Before
	public void setUp() throws IOException{
		fr.open(System.getProperty("openlink"));
		expected = System.getProperty("word");
	}
	@Test
	public void testCheckPageContains() {
		boolean flag = false;
		for (String str : fr.textURL) {
			if (str.contains(expected)) {
				flag = true;
			}
		}
		assertTrue(flag);
	}

}
