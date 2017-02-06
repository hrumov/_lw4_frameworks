
import java.io.*; 
import java.net.*; 



public class CheckHTMLPages { 
                  
    private String html = ""; 
    private String log = ""; 
    private int failedTests = 0; 
    private int totalTests = 0; 
    private double totalTime = 0; 
                  
    public static void main(String[] args) throws IOException  { 
                                    
        String inputFile = "D:/Eclipse_Projects/HT1/src/1.txt"; 
        String logFile = "D:/Eclipse_Projects/HT1/src/log.txt"; 
                                    
        CheckHTMLPages check = new CheckHTMLPages(); 
		BufferedReader input =  new BufferedReader(new FileReader(inputFile)); 
		String line; 
		String commands[]; 
		while (( line = input.readLine()) != null) 
		{ 
			commands = line.split("\"| \""); 
            try { 

                switch(commands[0]) { 

                case "open": 
                    try{ 
                        double timeout = Double.parseDouble(commands[3]); 
                        check.open(commands[1], timeout); 
                    } catch(ArrayIndexOutOfBoundsException | NumberFormatException e) 
                    { 
                        check.open(commands[1], Double.MAX_VALUE); 
                        check.setLog("You don't set timeout, so it doesn't check. Write number in seconds.\n"); 
                    } 
                        break; 
  
                case "checkLinkPresentByHref": 
                   check.checkLinkPresentByHref(commands[1]); 
                   break; 
 
                case "checkLinkPresentByName": 
                   check.checkLinkPresentByName(commands[1]); 
                   break; 

                case "checkPageTitle": 
                   check.checkPageTitle(commands[1]); 
                   break; 
                                                                              
                case "checkPageContains": 
                   check.checkPageContains(commands[1]); 
                   break; 
                                                                              
                default: 
                   check.setLog("Unknown command.\n"); 
                                                            } 
			} catch (ArrayIndexOutOfBoundsException e) 
			{ 
				check.setLog("Invalid command, there are no parameters after command.\n"); 
			} 
  
		} 
		input.close(); 
		check.printLog(logFile); 

	} 

	public void setHtml (String html) 
	{ 
		this.html = html; 
    } 
 
    public String getHtml () 
    { 
        return html; 
    } 
  
    public void setLog (String log) 
    { 
        this.log += log; 
    } 

    public void open (String url, double timeout) 
    { 
        long startTime = System.currentTimeMillis(); 
        totalTests++; 
        try 
        { 
            URL ur = new URL(url); 
            HttpURLConnection connect = (HttpURLConnection) ur.openConnection(); 
            connect.connect(); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream())); 
            String s; 
            while((s = reader.readLine()) != null) { 
                html += s; 
            } 
            reader.close(); 
        } 
        catch (Exception e) 
        { 
            log += "! [open \"" + url + "\" \"" + timeout+"\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
            failedTests++; 
            return; 
        } 
                                    
        if(System.currentTimeMillis() - startTime <= timeout*1000) 
        { 
            log += "+ [open \"" + url + "\" \"" + timeout+"\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
        } else 
        { 
            log += "! [open \"" + url + "\" \"" + timeout+"\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
            failedTests++; 
		} 
                                  
        totalTime += (double)(System.currentTimeMillis() - startTime)/1000; 
    } 
                  
    public void checkLinkPresentByHref (String href) 
    { 
        long startTime = System.currentTimeMillis(); 
        totalTests++; 
		if(html.contains("href=\"" +href)) 
		{ 
			log += "+ [checkLinkPresentByHref \"" + href + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
		} else 
		{ 
			log += "! [checkLinkPresentByHref \"" + href + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
			failedTests++; 
		} 
		totalTime += (double)(System.currentTimeMillis() - startTime)/1000; 
    } 
                  
	public void checkLinkPresentByName (String linkname) 
	{ 
		long startTime = System.currentTimeMillis(); 
		totalTests++; 
		if(html.contains(linkname + "</a>")) 
		{ 
			log += "+ [checkLinkPresentByName \"" + linkname + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
		} else 
		{ 
			log += "! [checkLinkPresentByName \"" + linkname + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
			failedTests++; 
		} 
		totalTime += (double)(System.currentTimeMillis() - startTime)/1000; 
	} 
                  
	public void checkPageTitle (String text) 
	{ 
		long startTime = System.currentTimeMillis(); 
		totalTests++; 
		if(html.contains("<title>" + text + "</title>")) 
		{ 
			log += "+ [checkPageTitle \"" + text + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
		} else 
		{ 
			log += "! [checkPageTitle \"" + text + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
			failedTests++; 
		} 
		totalTime += (double)(System.currentTimeMillis() - startTime)/1000; 
	} 
                  
	public void checkPageContains (String text) 
	{ 
		long startTime = System.currentTimeMillis(); 
		totalTests++; 
		if(html.contains(text)) 
		{ 
			log += "+ [checkPageContains \"" + text + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
		} else 
		{ 
			log += "! [checkPageContains \"" + text + "\"] " + (double)(System.currentTimeMillis() - startTime)/1000 + "\n"; 
			failedTests++; 
		} 
		totalTime += (double)(System.currentTimeMillis() - startTime)/1000; 
	} 
                  
	public void printLog (String logFileName) throws IOException 
	{ 

		log += "Total tests: " + totalTests + "\n"; 
		log += "Passed/Failed: " + (totalTests - failedTests) + "/" + failedTests + "\n"; 
		log += "Total time: " + totalTime + "\n"; 
		if(totalTests != 0) 
		{ 
			log += "Average time: " + (Math.round(totalTime/totalTests*1000)/1000.0) + "\n"; 
		} else 
		{ 
			log += "Average time: 0 \n"; 
			log += "You can write commands like:\nopen \"http://google.com\" \"1000\"\ncheckLinkPresentByName \"Language tools\"\n"; 
			log += "checkPageContains \"Search\" \ncheckPageTitle \"Google\" \ncheckLinkPresentByHref \"https://photos.google.com\""; 
		} 

		Writer writer = new BufferedWriter(new FileWriter(new File(logFileName))); 
		writer.write(log); 
		writer.close(); 
	} 
}
 