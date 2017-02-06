
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class ParseFile {

	
	int positiveNumbers;
	int negativeNumbers;
	Double allOfTime = 0.0;		
	double testTime;
	String timeAmount;
	
		
	MainCommands obj = new MainCommands();
	ResultGenerator objRes = new ResultGenerator();
	long timer = 0;
	long start;
	long finish;
	boolean checkTest = true;
	String nameInstruction;
	String valueOfInstruction;
	String urlName;
	String urlLoadTime;	
	String openLogName;
	Double averageTime;

	File file = new File("res//log.txt");
	public void checkCommands() throws Exception{			
    final File instruction = new File("instruction//instructions.txt");
    if(!instruction.exists()||instruction.isDirectory())
	{
    	System.out.println("File " + instruction.getAbsolutePath() + " not found");
    	
	}
    
	BufferedReader readFromInstruction;
		 readFromInstruction = new BufferedReader(new FileReader(instruction));
	
		
	
    String str;
	while((str = readFromInstruction.readLine()) != null)	{
	
		if(str.matches(".*\\s\\\".*\\\"\\s?\\\"?.*?\\\"?") && (str.charAt(0) != '\"')){
		
			try{
				nameInstruction = str.substring(0,str.indexOf('"')-1);
					
			}catch (Exception e){
				System.out.println("Wrong instruction");
			}
	
		}
	
		switch(nameInstruction){
		    case "open":
		    	
				 valueOfInstruction = str.substring((str.indexOf('"')+1),str.lastIndexOf('"'));
				 urlLoadTime = valueOfInstruction.substring(valueOfInstruction.lastIndexOf('"')+1);
				 urlLoadTime = urlLoadTime.replace(',', '.');
				 urlName = valueOfInstruction.substring(0,valueOfInstruction.indexOf('"'));
				 start = System.currentTimeMillis();
				 checkTest = obj.openURL(urlName);
				 finish = System.currentTimeMillis();
				 openLogName = "[open \"" + urlName + "\" " + "\"" + urlLoadTime + "\"" +"] ";
				 objRes.logResult(urlLoadTime, urlName, checkTest, start, finish, openLogName);
			break;
			
		    case "checkLinkPresentByHref":
			
				valueOfInstruction = str.substring((str.indexOf('"')+1),str.lastIndexOf('"'));
				start = System.currentTimeMillis();
				checkTest = obj.checkExt("href=\"" + valueOfInstruction + "\"");
				finish = System.currentTimeMillis();
				openLogName = "[checkLinkPresentByHref \""+ valueOfInstruction + "\"" +"] ";
				objRes.logResultAllBesideOpenOperation(checkTest, start, finish, openLogName);
			break;
			
		    case "checkLinkPresentByName":
			
			     valueOfInstruction = str.substring((str.indexOf('"')+1),str.lastIndexOf('"'));
			     start = System.currentTimeMillis();
				 checkTest = obj.checkExt(">" + valueOfInstruction + "</a>");
				 finish = System.currentTimeMillis();
				 openLogName = "[checkLinkPresentByName \""+ valueOfInstruction + "\"" +"] ";
				 objRes.logResultAllBesideOpenOperation(checkTest, start, finish, openLogName);
				break;
		    case "checkPageTitle":
	    	
	    	valueOfInstruction = str.substring((str.indexOf('"')+1),str.lastIndexOf('"'));
	    	start = System.currentTimeMillis();
			checkTest = obj.checkExt("<title>" + valueOfInstruction + "</title>");
			finish = System.currentTimeMillis();
			openLogName = "[checkPageTitle  \""+ valueOfInstruction + "\"" +"] ";
			objRes.logResultAllBesideOpenOperation(checkTest, start, finish, openLogName);
	        	
			break;
		    case "checkPageContains":
	    	
	    	valueOfInstruction = str.substring((str.indexOf('"')+1),str.lastIndexOf('"'));
	    	start = System.currentTimeMillis();
			checkTest = obj.checkExt(valueOfInstruction);
			finish = System.currentTimeMillis();
			openLogName = "[checkPageContains \""+ valueOfInstruction + "\"" +"] ";
			objRes.logResultAllBesideOpenOperation(checkTest, start, finish, openLogName);
	    	
	    	break;
		    default:
			{
				objRes.undefinedInstruction(str);
			
			}
		
		}
			
	
		
	}
			readFromInstruction.close();
			
			for(int i = 0; i < objRes.commonTime.size(); i ++)
			{
				
				allOfTime =+ (objRes.commonTime.get(i)/1000);
				
			}
			String.format("%.3f",averageTime);
			String.format("%.3f",objRes.OpenUrlTime);
		    averageTime = ((allOfTime)/1000)+((objRes.OpenUrlTime)/1000);
				
	String res = "Total tests: " +  ((objRes.commonTime.size()+objRes.OpenUrlTestCount)) + "\r\n"
		 		 + "Passed/Failed: " + (objRes.intPositiveTests) + "/" + objRes.intNegativeTests + "\r\n"
		 		 + "Total time: " + String.format("%.3f",(((allOfTime)/1000)+((objRes.OpenUrlTime)/1000))) + " \r\n"
		 		 + "Average time: " + String.format("%.3f",averageTime / (((objRes.commonTime.size()+objRes.OpenUrlTestCount))));



    Files.write(Paths.get(file.getAbsolutePath()), res.getBytes(), StandardOpenOption.APPEND);
	}
		 
}
	

	
	
	

