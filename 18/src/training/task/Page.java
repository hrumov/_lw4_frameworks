
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;

public class Page {
	private ArrayList <String> page = new ArrayList<String>();
	
	public ArrayList <String> getPage(){
		return page;
	}
	
	public Page(){
	}
	public Page(URLConnection urlCon){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(
	                urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				page.add(inputLine);
			}
			in.close();		
		}
		catch(Exception e){}
	}
}
