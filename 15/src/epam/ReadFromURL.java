
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadFromURL {
	final String ENCODING = "UTF-8";// site encoding ex. windows-1251,utf-8

	public String read(String path, String ur, int timeout) throws IOException {

		String content = "";
		try {
			URL url = new URL(ur);
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect.connect();
			try {
				connect.setConnectTimeout(timeout);// create connection with timeout													// timeout
			} catch (Exception e) {

				System.out.println("Timeout expired-->" + connect.getConnectTimeout());
			}

			BufferedReader reader;
			//Writer writer;
			reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));

		//	writer = new BufferedWriter(new FileWriter(new File(path)));
			String data = "";

			StringBuffer sb = new StringBuffer();
			while ((data = reader.readLine()) != null) {//read from URL
			//	writer.write(data);//write to file
				sb.append(data);//append to string
			}
			content = sb.toString();
			// System.out.println(sb);

			reader.close();
			//writer.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Incorrect ENCODING ,change to UTF-8 or windows-1251 or see the right on the site!!");

		}

		return content;
	}

}
