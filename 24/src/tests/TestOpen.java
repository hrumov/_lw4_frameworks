
import java.io.IOException;

import logic.Frame;

import org.junit.Before;
import org.junit.Test;

public class TestOpen {
	private Frame fr = new Frame();
	private String str;
	@Before
	public void setUp(){
		str = System.getProperty("openlink");
	}
	@Test (timeout = 3*1000)
	public void testOpen() throws IOException {
		fr.open(str);
	}

}
