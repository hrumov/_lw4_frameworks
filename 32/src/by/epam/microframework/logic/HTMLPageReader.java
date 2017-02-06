
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import by.epam.microframework.exception.HTMLReaderException;

public class HTMLPageReader {
	private InputStream is;

	public HTMLPageReader(InputStream is) {
		super();
		this.is = is;
	}

	public String read() throws HTMLReaderException {
		StringBuilder result = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));) {
			String string = "";
			while ((string = reader.readLine()) != null) {
				result.append(string);
			}
		} catch (IOException e) {
			throw new HTMLReaderException(e);
		}
		return result.toString();

	}

}
