
public class Runner {

	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("Error: No arguments given");
			System.out.println("Usage:  input.txt");
		} else {
			String fileName = args[0];
			ParserEngine engine = new ParserEngine(fileName);
			engine.runEngine();
			System.out.println("Done! Check log.txt");
			}
		
	}

}
