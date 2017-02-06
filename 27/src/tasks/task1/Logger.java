
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class Logger {
	private ArrayList<String> logger; 
	private int testCount;
	private int testPassed;
	private int testFailed;
	private long totalTime;
	
	
	public Logger(){
		this.logger = new ArrayList<String>();
	}
	
	public void newTest(Boolean result, String command, Long time){
		String newRecord;
		DecimalFormat f = new DecimalFormat("#.####");
		f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH)); 
		if(result){
			newRecord="+";
			this.testPassed++;
		} else {
			newRecord="!";
			this.testFailed++;
		}
		newRecord=newRecord+" ["+command+"] "+ String.valueOf(f.format((double)time/1000));
		this.logger.add(newRecord);
		this.totalTime=this.totalTime+time;
		this.testCount++;
	}
	
	private double avgTime(){
		if(this.testCount>0){
		return this.totalTime/this.testCount;
		}
		return 0;
	}
	
	public void logToFile(){
		DecimalFormat f = new DecimalFormat("##.###");
		f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));   // Setting "." by default. It's java...
		try (PrintWriter out = new PrintWriter("log.txt", "UTF-8");){
			for(String log : logger){
				out.println(log);
			}
			out.println("-----------------------------------------");
			out.println("Total tests: "+this.testCount);
			out.println("Passed/Failed: "+this.testPassed+"/"+this.testFailed);
			out.println("Total time: "+f.format((double)this.totalTime/1000));
			out.println("Agerage time: "+f.format(avgTime()/1000));
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
