
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

public class Open {

	public static boolean open(String targetURL){
		Date dateStart = new Date();
		try{
		URL url = new URL(targetURL);
		InputStream out = url.openConnection().getInputStream();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(out));
		FileOutputStream in = new FileOutputStream(
				"D://workspace//FrameWork//src//epam//File.txt");		
		byte[] buffer = new byte[out.available()];
		out.read(buffer, 0, buffer.length); // считываем буфер
		in.write(buffer, 0, buffer.length); // записываем из буфера в файл
		Date dateEnd = new Date();
		long timeStart = dateStart.getTime();
		long timeEnd = dateEnd.getTime();
		long duration = timeEnd - timeStart;
		double time = duration * 1.0 / 1000;
		bufferedReader.close();
		
		String target = "+ [open \"" + targetURL + "\"] " + time;
		 try(FileWriter writer = new FileWriter("D://workspace//FrameWork//src//epam//LogFile.txt", false))
	        {	           
	         writer.write(target);	            
             writer.append('\n');	            
	        }
	        catch(IOException ex){
	             
	            System.out.println(ex.getMessage());
	        } 			
		return true;
		
		} catch(IOException e) {
			Date dateEnd = new Date();
			long timeStart = dateStart.getTime();
			long timeEnd = dateEnd.getTime();
			long duration = timeEnd - timeStart;
			double time = duration * 1.0 / 1000;
			System.out.println("! [open \"" + targetURL + "\"] " + time);
			return false;
		}
	}
}
