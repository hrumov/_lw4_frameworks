
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {

		WorkWithFile work;
		try
		{
			work = new WorkWithFile(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("You didn't enter path!");
			return;
		}
		work.readFile();
		work.doOperations();
	}
}
