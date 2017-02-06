
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		String fileIn = "commands.txt";
		String fileOut = "log.txt";
		RunCommands.run(fileIn, fileOut);
	}
}
