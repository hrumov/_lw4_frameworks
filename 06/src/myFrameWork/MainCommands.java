
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainCommands{
	boolean regEXPcheck = true;
	
	
	public boolean openURL(String ur) throws IOException	{
		
	
		File file = new File("instruction//url.txt");
	    if(!file.exists()||file.isDirectory())
		{
	    	file.createNewFile();
	    }
		
				ResultGenerator openRes = new ResultGenerator();
				
		
				try{
					URL url = new URL(ur);
					HttpURLConnection connect = (HttpURLConnection)url.openConnection();
					BufferedReader readFromUrl = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
					
					Writer writerToFile = new BufferedWriter(new FileWriter(file));
					String str;
					while((str = readFromUrl.readLine()) != null)	{
						writerToFile.write(str); 
						writerToFile.flush();
					}
				
				    readFromUrl.close();
					writerToFile.close();
			
					return true;
					
				}catch (Exception e){
					e.printStackTrace();
				}
				
				
				
				return false;
				
			
	}

	
	
	public boolean checkExt(String patt)throws IOException{
	
		
		String fullname = patt;
		
		
	
	
		fullname = correctStr(fullname);
	
		
	
		final File file = new File("instruction//url.txt");
	    if(!file.exists()||file.isDirectory())
		{
	    	
	    	System.out.println("File" + file.getAbsolutePath() + " not found");
	    	
		}
	    Pattern byname = Pattern
	    		
				.compile(".*" +fullname + ".*");
	 
		String str;
		BufferedReader readFromFile = new BufferedReader(new FileReader(file));
		
		long start = System.currentTimeMillis();
		
		while((str = readFromFile.readLine()) != null){
		
			Matcher check = byname.matcher(str);
			regEXPcheck = check.matches();
			if(regEXPcheck){
				return true;
			}else{
				continue;
			}
			
		
				
			
		}
		readFromFile.close();
		return false;
		

		
	}
	
	
public String correctStr(String someStr){
	StringBuilder sb = new StringBuilder();
	sb.append(someStr);
	for(int i = 0; i < sb.length(); i++){
		switch(sb.charAt(i)){
		case '.':
		case '^':
		case '$':
		case '*':
		case '+':
		case '?':
		case '{':
		case '[':
		case ']':
		case '|':
		case '(':
		case ')':
		
			sb.replace(i, i+1, "\\"+sb.charAt(i));
			i = i+2;
			break;
		case '\\':
			sb.replace(i, i+1, "\\\\"); 
			i = i + 4;
			break;
		default: continue;
		}
	
	}
	return sb.toString();
}

}
