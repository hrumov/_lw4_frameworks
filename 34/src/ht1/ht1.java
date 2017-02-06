
import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import webTestingFramework.*;

public class ht1 
{

	public static void main(String[] args) throws IOException, URISyntaxException 
	{
		ArrayList<TestResult> testRes = new ArrayList<TestResult>();	
		
		BufferedReader br = new BufferedReader(new FileReader("test.txt"));
		try 
		{
			boolean res = false;
		    String line = "";
		    webTestingFramework wtf = new webTestingFramework();

		    while ((line = br.readLine()) != null) 
		    {
		    	String[] cmdArgs = line.split("\"");
		    	String arg2 = cmdArgs[1];
		        switch(cmdArgs[0].trim())
		        {
		        case "open":
		        	res = wtf.open(arg2, Integer.parseInt(cmdArgs[3]));
		        	break;
		        	
		        case "checkLinkPresentByHref":
		        	res = wtf.checkLinkPresentByHref(arg2);
		        	break;
		        	
		        case "checkLinkPresentByName":
		        	res = wtf.checkLinkPresentByName(arg2);
		        	break;
		        	
		        case "checkPageTitle":
		        	res = wtf.checkPageTitle(arg2);
		        	break;
		        	
		        case "checkPageContains":
		        	res = wtf.checkPageContains(arg2);
		        	break;
		        }
		        
		        testRes.add(new TestResult(res, line, wtf.getLastTestTime()));
		    }
		    
		} 
		finally 
		{
		    br.close();
		}
		
		
		try
		{

		    PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
		    
		    int tPassed = 0, tFailed = 0;
		    double totalTime = 0;
		    
		    for(TestResult tr: testRes)
		    {
		    	
		    	writer.println( (tr._result ? "+" : "!") + " " + "[" +  tr._args + "] " + (tr._time/1000));
		    	
		    	if(tr._result)
		    	{
		    		tPassed++;
		    	}
		    	else 
		    	{
		    		tFailed++;
				}
		    	
		    	totalTime += tr._time;
		    }
		    
		    writer.println("Total tests: " + testRes.size());
		    writer.println("Passed/Failed: " + tPassed + "/" + tFailed);
		    writer.println("Total time: " + totalTime/1000);
		    writer.println("Average time: " + (((double)(int)(totalTime/testRes.size()))/1000));
		    writer.close();
		} 
		catch (Exception e) 
		{
		   
		}
		
		
		

	}

	
	
}
