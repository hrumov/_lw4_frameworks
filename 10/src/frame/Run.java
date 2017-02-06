
import java.io.IOException;

public class Run {
	public static void main(String[] args) {
		Checker c = new Checker();
		try {
			c.checkAndSplit();
		} catch (IOException e) {
			System.out.println("ERROR: Check your file!");
		}
	}
}
