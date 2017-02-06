
public class Parser {

	public static String parseOperation(String command) {	//Parse operation
		return command.split("\\s\"+")[0];
	}

	public static String parseFirst(String command) {		//Parse first parameter
		if((command.split("\\s\"+").length) == 1){
			return new String();
		}
		return command.split("\\s\"+")[1].split("\"+")[0];
	}

	public static String parseSecond(String command) {		//Parse second parameter
		if(((command.split("\\s\"+").length) == 1)||((command.split("\\s\"+").length) == 2)){
			return new String();
		}
		return command.split("\\s\"+")[2].split("\"+")[0];
	}
}
