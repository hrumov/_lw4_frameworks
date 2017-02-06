
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import logger.Logger;

public class Framework 
{
	private File checkFile; //File with url content to check
	private final String path = System.getProperty("user.dir")+"\\Temp.html"; // path to save file from url
	ArrayList<String> commands; //List with readed commands
	private long timeout;
	private boolean isUrlRead; //Flag to check is url was read 
	private Logger logger; //object of class Logger to write log file
	private int passedTests = 0;
	private int failedTests = 0;
	private double totalTime = 0; //Variable that contains total time that was needed to work all tests
	private double workOpen;
	
	public Framework()
	{
	}
	
	public Framework(File scriptFile) throws Exception
	{
		commands = scriptFileRead(scriptFile);
		logger = new Logger(scriptFile);
		scriptParseandStart();
	}
	
	//Method to reading test script file
	private ArrayList<String> scriptFileRead(File scriptFile) throws Exception
	{
		ArrayList<String> output = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
		String line;
		while((line = reader.readLine()) != null)
		{
			output.add(line);
		}
		reader.close();
		return output;
	}
	
	//Method to parse commands and tests start
	private void scriptParseandStart() throws Exception
	{
		for (int i=0;i<commands.size();i++)
		{
			String s = commands.get(i);
			String[] parsed = s.split(" ");
			String command = parsed[0];
			String check = s.substring(s.indexOf("\"")+1, s.lastIndexOf("\""));
			String b;
			boolean fl;
			long t1, work;
			double w;
			switch (command)
			{
				case "open":
					b = parsed[1].substring(1, parsed[1].length()-1);
					String c = parsed[2].substring(1, parsed[2].length()-1);
					isUrlRead = open(b, Integer.valueOf(c));//, Integer.valueOf(c));
					totalTime+=workOpen;
					if (isUrlRead)
					{
						logger.writeLogMessage("+ [open \""+b+"\" \""+c+"\"] "+String.valueOf(workOpen));
						passedTests++;
					}
					else
					{
						logger.writeLogMessage("! [open \""+b+"\" \""+c+"\"] ");
						failedTests++;
					}
					break;
				case "checkLinkPresentByHref":
					if (isUrlRead)
					{
						t1 = System.currentTimeMillis(); 
						fl = checkLinkPresentByHref(check);
						work = System.currentTimeMillis()-t1;
						w = (double)work/1000;
						totalTime+=w;
						if (fl)
						{
							logger.writeLogMessage("+ [checkLinkPresentByHref \""+check+"\"] "+String.valueOf(w));
							passedTests++;
						}
						else
						{
							logger.writeLogMessage("! [checkLinkPresentByHref \""+check+"\"] "+String.valueOf(w));
							failedTests++;
						}
					}
					else
					{
						logger.writeLogMessage("! [checkLinkPresentByHref \""+check+"\"] 0");
						failedTests++;
					}
					break;
				case "checkLinkPresentByName":
					if (isUrlRead)
					{
						t1 = System.currentTimeMillis(); 
						fl = checkLinkPresentByName(check);
						work = System.currentTimeMillis()-t1;
						w = (double)work/1000;
						totalTime+=w;
						if (fl)
						{
							logger.writeLogMessage("+ [checkLinkPresentByName \""+check+"\"] "+String.valueOf(w));
							passedTests++;
						}
						else
						{
							logger.writeLogMessage("! [checkLinkPresentByName \""+check+"\"] "+String.valueOf(w));
							failedTests++;
						}
					}
					else
					{
						logger.writeLogMessage("! [checkLinkPresentByName \""+check+"\"] 0");
						failedTests++;
					}
					break;
				case "checkPageTitle":
					if (isUrlRead)
					{
						t1 = System.currentTimeMillis(); 
						fl = checkPageTitle(check);
						work = System.currentTimeMillis()-t1;
						w = (double)work/1000;
						totalTime+=w;
						if (fl)
						{
							logger.writeLogMessage("+ [checkPageTitle \""+check+"\"] "+String.valueOf(w));
							passedTests++;
						}
						else
						{
							logger.writeLogMessage("! [checkPageTitle \""+check+"\"] "+String.valueOf(w));
							failedTests++;
						}
					}
					else
					{
						logger.writeLogMessage("! [checkPageTitle \""+check+"\"] 0");
						failedTests++;
					}
					break;
				case "checkPageContains":
					if (isUrlRead)
					{
						t1 = System.currentTimeMillis(); 
						fl = checkPageContains(check);
						work = System.currentTimeMillis()-t1;
						w = (double)work/1000;
						totalTime+=w;
						if (fl)
						{
							logger.writeLogMessage("+ [checkPageContains \""+check+"\"] "+String.valueOf(w));
							passedTests++;
						}
						else
						{
							logger.writeLogMessage("! [checkPageContains \""+check+"\"] "+String.valueOf(w));
							failedTests++;
						}
					}
					else
					{
						logger.writeLogMessage("! [checkPageContains \""+check+"\"] 0");
						failedTests++;
					}	
					break;
			}
		}
		scriptEnd();
	}
	
	//Method to command 'open' into script file
	private boolean open(String urlin, int timeoutin) throws Exception
	{
		this.timeout = timeoutin;
		
		PrintWriter pr = new PrintWriter(new FileOutputStream(System.getProperty("user.dir")+"\\temp2.txt"));
		pr.println(urlin);
		pr.close();
		
		TimerTask timerTask = new Open();
		Timer timer = new Timer(true);
		
		timer.schedule(timerTask, 0);
		Thread.sleep(this.timeout*1000+100);
		timer.cancel();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\temp1.txt"));
			workOpen = Double.valueOf(reader.readLine());
			if (reader.readLine().equals("0"))
			{
				checkFile = new File(path);
				reader.close();
				File f = new File(System.getProperty("user.dir")+"\\temp1.txt");
				f.delete();
				return true;
			}
			else 
			{
				reader.close();
				return false;
			}
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	
	//Method to check link to present by href attribute
	private boolean checkLinkPresentByHref(String href) throws Exception
	{
		InputStreamReader br = new InputStreamReader(new FileInputStream(checkFile), "utf-8");
		BufferedReader reader = new BufferedReader(br);
		String line;
		while((line = reader.readLine()) != null)
		{
			line.toLowerCase();
			int k = line.indexOf("href=\""+href.toLowerCase()+"\"");
			if ( k>-1)
			{
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	//Method to check link to present by text of link
	private boolean checkLinkPresentByName(String name) throws Exception
	{
		InputStreamReader br = new InputStreamReader(new FileInputStream(checkFile), "utf-8");
		BufferedReader reader = new BufferedReader(br);
		String line;
		while((line = reader.readLine()) != null)
		{
			int k = line.indexOf(name+"</a>");
			if ( k>-1)
			{
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	//Method to check the title of the page
	private boolean checkPageTitle(String title) throws Exception
	{
		InputStreamReader br = new InputStreamReader(new FileInputStream(checkFile), "utf-8");
		BufferedReader reader = new BufferedReader(br);
		String line;
		while((line = reader.readLine()) != null)
		{
			int k = line.indexOf(title+"</title>");
			if ( k>-1)
			{
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	//Method to check that page contains some text
	private boolean checkPageContains(String text) throws Exception
	{
		InputStreamReader br = new InputStreamReader(new FileInputStream(checkFile), "utf-8");
		BufferedReader reader = new BufferedReader(br);
		String line;
		while((line = reader.readLine()) != null)
		{
			int k = line.indexOf(text);
			if ( k>-1)
			{
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	
	private void scriptEnd() throws Exception
	{
		logger.writeLogMessage("Total test: "+commands.size());
		logger.writeLogMessage("Passed/Failed: "+passedTests+"/"+failedTests);
		logger.writeLogMessage("Total time: "+String.format("%(.3f",totalTime).replace(",", "."));
		logger.writeLogMessage("Average time: "+String.format("%(.3f",totalTime/commands.size()).replace(",","."));
		logger.Close();
		if (checkFile!=null)
		{
			checkFile.delete();
		}
	}
}
