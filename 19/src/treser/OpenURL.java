
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OpenURL {
	private static String url;
	private int timeoutUrl;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTimeout() {
		return timeoutUrl;
	}
	public void setTimeout(int timeout) {
		this.timeoutUrl = timeout;
	}
	public OpenURL(String url, int timeout){
		this.url = url;
		this.timeoutUrl = timeout;
	}
	
	public String open() throws IOException{
		try{
		Document doc= Jsoup.connect(url).timeout(timeoutUrl).get();
		}catch(UnknownHostException e){
			return "!";
			}
		catch(SocketTimeoutException e){
			return "!";
		}	
		
		
		return "+";
		
	}
	
	public static  Document getHTML() throws IOException {
		
		Document doc= Jsoup.connect(url).get();
		return doc;
		
	}
	
	public static String checkPageTitle(String text) throws IOException{
			
			String result = null;
		try{
			Document doc = getHTML();
		
			String title = doc.title();
			if (text.equals(title)){
				result = "+";
			} 
			else {
				result = "!";
			}
		}
		catch(UnknownHostException e){
			return "!";
			
		}
			return result;
		}



	public static String checkLinkPresentByHref(String href) throws IOException{
		String result = null;
		try{
		Document doc = Jsoup.connect(url).get();
	    Elements links = doc.getElementsByTag("a");
	    for (Element link : links){
	    
	           String urlhref = link.attr("href"); 
	           
	          if (urlhref.contains(href)){
	        	  
	        	    result = "+"; 
	        	break;
	          } 
	          else{
	        	  result="!"; 
	          }
	    }
		}
	    catch(UnknownHostException e){
	    	return "!";
	    	
	    }
	    
		return result;
	    
		
    }
	
	public static String checkLinkPresentByName(String name) throws IOException{
		String result = null;
		Document doc = Jsoup.connect(url).get();
	    Elements links = doc.select("a[href]");
	   
	    for (Element link : links){
		    String text = link.text();
			//System.out.println(text); output visible names of links
			if (text.contains(name)){
	        	  
        	    result = "+"; 
        	break;
          } 
          else{
        	  result="!"; 
          }
		}
		
		return result;
		
		
	}
	
	
public static String checkPageContains(String text) throws IOException{
		
		Document doc = Jsoup.connect(url).get();
	    String html = doc.toString();
	    //System.out.println(html);      output html for parsing
	    String result;
		if(html.contains(text)){
	    	result = "+";
	    }
	    else{
	    	result = "!";
	    }
		return result;
		
	    }
		
		
		
		
	
	
	
}























