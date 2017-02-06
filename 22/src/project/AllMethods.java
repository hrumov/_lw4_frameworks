

import java.net.*;
import java.io.*;
public class AllMethods {

	public String openUrlOnPage(int timeOut) throws Exception {
		long startTime = System.currentTimeMillis();
		String inputLine;
		String something = "";
				for (;;) {
		URL oracle = new URL("http://tut.by");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        
        while ((inputLine = in.readLine()) != null)
        	something += inputLine;       
        in.close();
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed >= timeOut) {
            break;
        }
        System.out.println(elapsed);
				}
        return something;
	}
	
	public boolean checkLinkPresentByHref(String inputLine){
		String href = "http://afisha.tut.by/online-cinema/";
		return inputLine.contains(href);
		
	}
	
	public boolean checkLinkPresentByName(String inputLine){
		String name = "http://afisha.tut.by/online-cinema/";
		return inputLine.contains(name);
		
	}
	public boolean checkPageTitle(String inputLine){
		String name = "Белорусский портал TUT.BY";
		return inputLine.contains(name);
		
	}
	
	public boolean checkPageContains(String inputLine){
		String name = "Разделы";
		return inputLine.contains(name);
		
	}
	
}
