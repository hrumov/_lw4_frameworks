
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logger 
{
	private File loggerFile;
	private String logFileName;
	private PrintWriter output;
	
	public Logger(File scriptFile) throws Exception
	{
		logFileName = System.getProperty("user.dir")+"\\";
		logFileName += fileNameWithoutExtension(scriptFile.getName());
		logFileName += "_"+new java.util.Date().toString().replaceAll(":", "_").replaceAll(" ","_");
		logFileName += "_log.txt";
		loggerFile = new File(logFileName);
		try 
		{
			if (!loggerFile.exists())
			{
				loggerFile.createNewFile();
			}
			output = new PrintWriter(new FileOutputStream(loggerFile));
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	public void writeLogMessage(String message) throws Exception
	{
		try
		{
			output.println(message);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	public void Close()
	{
		output.close();
	}
	
	private String fileNameWithoutExtension(String FileName)
	{
		FileName = FileName.toLowerCase();
		String s = FileName.substring(0, FileName.indexOf(".txt"));
		return s;
	}
}
