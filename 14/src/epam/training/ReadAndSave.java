
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadAndSave {
	public static String readCommands(String fileIn) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileIn);
			byte[] str = new byte[inputStream.available()];
			inputStream.read(str);
			String everything = new String(str);
			return everything;
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
			return e.toString();
		}
		finally{
			inputStream.close();
		}
	}
	
	public static String saveInput(BufferedReader reader) throws IOException {
		String inputLine = "";
		String result = "";

		while ((inputLine = reader.readLine()) != null) {
			result += inputLine;
		}
		reader.close();
		return result;
	}
}
