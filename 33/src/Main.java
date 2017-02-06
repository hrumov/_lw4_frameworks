import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		WorkWithFile rFile = new WorkWithFile(args[0], args[1]);

		try {
			rFile.getFileText(rFile.getReadFilePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rFile.comands();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
