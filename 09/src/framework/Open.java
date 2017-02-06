
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class Open extends TimerTask
{
	private final String path = System.getProperty("user.dir")+"\\Temp.html"; // path to save file from url
	
	//Override run method to open url
	@Override
	public void run()
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\temp2.txt"));
			long t1 = System.currentTimeMillis();
			boolean b = readUrl(reader.readLine());
			double work = (System.currentTimeMillis()-t1)/1000;
			reader.close();
			File f = new File(System.getProperty("user.dir")+"\\temp2.txt");
			f.delete();
			PrintWriter pr = new PrintWriter(new FileOutputStream(System.getProperty("user.dir")+"\\temp1.txt"));
			if (b)
			{
				pr.println(work);
				pr.println("0");
			}
			else
			{
				pr.println("1");
			}
			pr.close();
		} catch (Exception e) {
			
		}
	}
	
	//Method to open url and write it in file
	private boolean readUrl(String ur) throws Exception
	{
		URL url = new URL(ur);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
    	connect.connect();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "windows-1251"));
    	Writer writer = new BufferedWriter(new FileWriter(new File(path)));
    	String s;
    	while((s = reader.readLine()) != null)	{
        	writer.write(s+"\n");
    	}
    	reader.close();
    	writer.close();
    	return true;
	}
	
}
