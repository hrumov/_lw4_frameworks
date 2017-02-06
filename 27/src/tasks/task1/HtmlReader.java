
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HtmlReader {
	private String content;
	private URL url;
	int timeout;
	Long time;
	
	public HtmlReader(String url, int timeout){
	try {
		this.url=new URL(url);
		this.timeout = timeout;
		getContent();
	} catch (Exception e) {
		System.out.println("Wrong addredss: "+e.getMessage());
		this.time=0L;
	}	
	}
	
	private void getContent() throws IOException{
		Long begin = System.currentTimeMillis();
		URLConnection con = url.openConnection();
		con.setConnectTimeout(timeout);
		try (BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));){ //Try with resources
			String tmpStr="";
			while((tmpStr = in.readLine())!=null){
				content = content+tmpStr;
			}
		} catch (Exception e) {
			System.out.println("Wrong addredss/timeout: "+e.getMessage());
		}
		Long end = System.currentTimeMillis();
		this.time = end - begin;

	}
	
	public String open(){
		return content;
	}
	
	public Long getTime(){
		return time;
	}

}
