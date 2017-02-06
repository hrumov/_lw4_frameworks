
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FWTask {
	static final String FORMAT="#0.000";
	
		public static void main(String[] args) {
		if (args.length>0){
			String path = args[0];
			ArrayList<String> setOfInstructions = new ArrayList<String>();
			try(BufferedReader reader = new BufferedReader(new FileReader(path));){
				String instruction;
				while ((instruction = reader.readLine()) != null) {
					setOfInstructions.add(instruction);
				}
	        }
	        catch(IOException ex){
	            System.out.println(ex.getMessage());
	        }	
			File file = new File("log.txt");
			double totalTime = 0;
			int totalTests = 0;
			int passedTests = 0;
			int failedTests = 0;
			try{
				PrintWriter out = new PrintWriter(file.getAbsoluteFile());
				for (String i:setOfInstructions){
					Instruction instruction = new Instruction(i);
					double exTime=System.currentTimeMillis();
					boolean isPassed = instruction.execute();
					exTime = (System.currentTimeMillis()-exTime)/1e3;
					String formExTime = new DecimalFormat(FORMAT).format(exTime);
					totalTests++;
					totalTime+=exTime;
					if (isPassed)
						passedTests++;
					else
						failedTests++;
					instruction.writeToLog(out, isPassed, formExTime);
				}
				String formTotalTime = new DecimalFormat(FORMAT).format(totalTime);
				String formAvTime = new DecimalFormat(FORMAT).format(totalTime/totalTests);
				out.println("Total tests: " + totalTests);
				out.println("Passed/Failed: " + passedTests + "/" + failedTests);
				out.println("Total time: " + formTotalTime);
				out.println("Average time: " + formAvTime);
				out.close();
			}
			catch(Exception e){}
		}
		else 
		{
			System.out.println("Input path to file with instrustions for url you would like to check. "
					+ "Example of instructions set: ");
			System.out.println("open http://google.by 0.5");
			System.out.println("checkPageTitle Google");
		}
	}
}
