
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ResultGenerator {
	
	double timer;
	


	int intPositiveTests = 0;
	int intNegativeTests = 0;

	
	public double timeConsumedMillis;
	public double OpenUrlTime;
	String stringFlag;
	ArrayList<Double> commonTime = new ArrayList<>();
	int OpenUrlTestCount = 0;
	File file = new File("res//log.txt");
	 
	
	public void logResult(String setTime, String Url, Boolean trueTest, long StartTestTime, long FinishTestTime, String logName) throws IOException{
	
		timeConsumedMillis = (FinishTestTime - StartTestTime);
		
		
		if(setTime.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
			
			try{
			
				timer = Double.parseDouble(setTime);
			
			
			}catch (NumberFormatException e){
				System.out.println("Wrong size of variable");
			}
		}else{
			timer = 0;
		}
		
	
	
	if(file.exists()&&!file.isDirectory())
	{
			OpenUrlTime += (Double)timeConsumedMillis;
			OpenUrlTestCount++;
			timeConsumedMillis = (Double)timeConsumedMillis/1000;
			
			
				
			if(trueTest){
				
				if(timeConsumedMillis <= (timer*1000)){
					this.intPositiveTests++;
					stringFlag = "+ ";
					
				}else{
					this.intNegativeTests++;
					stringFlag = "! ";
					
					}
			}else{
				this.intNegativeTests++;
				stringFlag = "! ";
				
				}
			
			     
		     String replace = String.format("%.3f",timeConsumedMillis).replace(',', '.');
			 try {
				 	String res = stringFlag + logName +  replace + "\r\n";
		            Files.write(Paths.get(file.getAbsolutePath()), res.getBytes(), StandardOpenOption.APPEND);
		        }
		        catch (IOException e) {
		        	System.out.println("File" + file.getAbsolutePath() + " cant be writed\founded");
		        }
				
	}else {
		try{
			file.createNewFile();
			}catch (Exception e){
				System.out.println("File can't be created");
			}
		logResult(setTime, Url, trueTest, StartTestTime, FinishTestTime, logName);
	}
	}
	
		
	
	public void logResultAllBesideOpenOperation(Boolean trueTest, long StartTestTime, long FinishTestTime, String logName) throws IOException{
		
			 
	if(file.exists()&&!file.isDirectory())
	{
		timeConsumedMillis = FinishTestTime - StartTestTime;
		
		commonTime.add(timeConsumedMillis);
		timeConsumedMillis = (Double)timeConsumedMillis/1000;
		
		
		
	if(trueTest){
		
			this.intPositiveTests++;
			stringFlag = "+ ";
			
		}else{
			
			this.intNegativeTests++;
			stringFlag = "! ";
			}

	
	     
	     String replace = String.format("%.3f",timeConsumedMillis).replace(',', '.');
		 try {
			 	String res = stringFlag + logName +  replace + "\r\n";
	            Files.write(Paths.get(file.getAbsolutePath()), res.getBytes(), StandardOpenOption.APPEND);
	        }
	        catch (IOException e) {
	        	System.out.println("File" + file.getAbsolutePath() + " cant be writed\founded");
	        }
			
	}else {
		try{
			file.createNewFile();
			}catch (Exception e){
				System.out.println("File can't be created");
			}
		logResultAllBesideOpenOperation(trueTest,StartTestTime,FinishTestTime,logName);
	}
	
	
	}
	
	
	
	
	
	
	public void undefinedInstruction(String name) throws IOException{
		if(file.exists()&&!file.isDirectory())
		{
			
		 try {
			 
	            Files.write(Paths.get(file.getAbsolutePath()), ("Unknown command  --> " + name + "\r\n").getBytes(), StandardOpenOption.APPEND);
	        }
	        catch (IOException e) {
	        	System.out.println("File" + file.getAbsolutePath() + " cant be writed\founded");
	        }
	}else {
		try{
		file.createNewFile();
		}catch (Exception e){
			System.out.println("File can't be created");
		}
		undefinedInstruction(name);
	}
	}
	
	
}