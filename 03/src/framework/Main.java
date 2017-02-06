
import java.io.File;

public class Main {

	public static void main(String[] args) {
		Framework framework = null;
		
		if (args.length > 1) {
			File instructions_file = new File (args [0]);
			File log_file = new File (args [1]);
			framework = new Framework (instructions_file, log_file);
		} else if (args.length == 1) {
			File instructions_file = new File (args [0]);
			framework = new Framework (instructions_file);
		} else {
			System.out.println("There are no commands for the framework.");
		}
		if (!framework.equals(null)) {
			framework.readCommands();
		}
		if (!framework.equals(null)){
			System.out.println(framework.getFinal_message());
		}
		

	}

}
