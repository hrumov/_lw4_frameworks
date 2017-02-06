
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HomeTask1 {
	
	public static void main(String[] args) throws IOException {

	
	File file = new File("fileexample.txt");
	StringBuilder sb = new StringBuilder();
	sb = readFile(file,sb, getingContentOfHTTPPage(null) );
}

	
	
	//txt of file to StringBuilder
	public static StringBuilder readFile(File file, StringBuilder sb,String contentOfHTTPPage){
		String line;	    
		BufferedReader fileIn = null;
		try{
			fileIn = new BufferedReader (new FileReader(file));
			while ((line = fileIn.readLine()) != null) {
			sb.append(line);
	        sb.append("\n");
	        }
			System.out.println(sb);
			fileIn.close();
			}catch (IOException e){}
		catch (NullPointerException e) {}
		total(sb, contentOfHTTPPage, file);
		return sb;
	}
	
		public static String getingContentOfHTTPPage(String pageAddress){ 
	    StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try{
        	URL pageURL = new URL(pageAddress);
        	URLConnection uc = pageURL.openConnection();
        	br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        	String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            } 
            br.close();
        }
        catch (MalformedURLException e){}
        catch (IOException e){}
        String contentOfHttpPage = sb.toString();
        return contentOfHttpPage;
        
    }

		
		public static String findOpenInFile(StringBuilder sb){
	     Pattern patHTTP = Pattern.compile("open .+");
	     Matcher matHTTP = patHTTP.matcher(sb);
	     String commandOpen = "";
	     while (matHTTP.find()) {
	    	 commandOpen = matHTTP.group();
	    	 }
	     Pattern patUrl = Pattern.compile("(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");
		 Matcher matUrl = patUrl.matcher(commandOpen);
		 String pageAddress = "";
		     while (matUrl.find()) {
		    	 pageAddress = matUrl.group();
		    	//System.out.println("We found url: " + pageAddress + "\n");
		    	 }getingContentOfHTTPPage(pageAddress);
		     return pageAddress ;
		     
		}
				
		 //поиск href в документе
		public static String findHrefInFile(StringBuilder sb, String contentOfHTTPPage, File file){
	     Pattern patHref = Pattern.compile("checkLinkPresentByHref .+");
	     Matcher matHref = patHref.matcher(sb);
	     String commandHref = "";
	     String pageHref = "";
	     while (matHref.find()){
	    	 commandHref = matHref.group();
	    	 System.out.println("We found checkLinkPresentByHref: " + commandHref);  
	    	 pageHref = commandHref.substring(24, (commandHref.length()-1) );
	         System.out.println("We found Href: " + pageHref + "\n" );
	    	 }hrefOnPage (contentOfHTTPPage, pageHref, commandHref, file);
		return pageHref;
		}
		
		  //поиск chekLinkPresentByName в документе
		public static String findNameInFile(StringBuilder sb,String contentOfHTTPPage, File file){
	     Pattern patName = Pattern.compile("chekLinkPresentByName .+");
	     Matcher matName = patName.matcher(sb);
	     String commandName = "";
	     String pageName = "";
	     while (matName.find()) {
	    	 commandName = matName.group();
	    	 //System.out.println("We found checkLinkPresentByName: " + commandName + "\n"); 
	    	 pageName = commandName.substring(24, (commandName.length()-1) );
	         //System.out.println("We found Name: " + pageName + "\n" );
	     }nameOnPage(contentOfHTTPPage,pageName, commandName, file);
		return pageName;
		}
		
		   //поиск checkPageTitle
		public static String findTitleInFile(StringBuilder sb,String contentOfHTTPPage, File file){
	     Pattern patTitle = Pattern.compile("checkPageTitle .+");
	     Matcher matTitle = patTitle.matcher(sb);
	     String commandTitle = "";
	     String pageTitle = "";
	     while (matTitle.find()){
	    	 commandTitle = matTitle.group();
	    	 //System.out.println("We found checkPageTitle: " + commandTitle );
	    	 pageTitle = commandTitle.substring(16, (commandTitle.length()-1) );
	    	 //System.out.println("We found Title: " + pageTitle + "\n" );
	    	 }
	     titleOnPage(contentOfHTTPPage, pageTitle, commandTitle, file);
		return pageTitle;
		}
	    
		
		public static String findContainsInFile(StringBuilder sb, String contentOfHTTPPage, File file){
	     Pattern patContains = Pattern.compile("checkPageContains .+");
	     Matcher matContains = patContains.matcher(sb);
	     String commandContains = "";
	     String pageContains = "";
	     while (matContains.find()){
	    	 commandContains = matContains.group();
	    	 //System.out.println("We found checkPageContains: " + commandContains);
	    	 pageContains = commandContains.substring(18, (commandContains.length()-1) );
	    	 //System.out.println("We found Contains: " + pageContains + "\n" );
	    	 } containsOnPage(contentOfHTTPPage, pageContains, commandContains, file);
		return pageContains;
	     }
		
		
		
		public static String hrefOnPage (String contentOfHTTPPage, String pageHref, String commandHref, File file){
			String whatToWrite = new String();
		 long startTime = System.currentTimeMillis();
		 long estimatedTime = 0; 
			 Pattern pat = Pattern.compile("href=\" + pageHref + \"" );
		     Matcher mat = pat.matcher(contentOfHTTPPage);
		     String hrefOnPage = "";
		     while(mat.find()){      
		    	 hrefOnPage = mat.group(); 
		    	 System.out.println(hrefOnPage);
		    	 estimatedTime = System.currentTimeMillis() - startTime;		     
		    	 }
		     if (mat.find()){
		    	whatToWrite ="+ [" + commandHref + "]";
		    	writeInFile (file, whatToWrite);
		     }else {
		    	 whatToWrite ="! [" + commandHref + "]" +Long.toString(estimatedTime); 
		     writeInFile (file, whatToWrite);}
		return whatToWrite;
		}
	
		public static String nameOnPage(String contentOfHTTPPage, String pageName, String commandName, File file){
			String whatToWrite = new String();
			long startTime = System.currentTimeMillis();			 
			long estimatedTime = 0;  
			Pattern pat = Pattern.compile(">" + pageName +"</a>");
		     Matcher mat = pat.matcher(contentOfHTTPPage);
		     String nameOnPage = "";
		     while (mat.find()){ 
			     nameOnPage = mat.group();  
			     System.out.println(nameOnPage );
			     estimatedTime = System.currentTimeMillis() - startTime;
			     }
		     if (mat.find()){	
		    	 whatToWrite ="+ [" + commandName + "]";
		    	 writeInFile (file, whatToWrite);
			     }else {
			    	 whatToWrite ="! [" + commandName + "]" +Long.toString(estimatedTime); 
		     writeInFile (file, whatToWrite);}
			return whatToWrite;
			}		
		
  public static String titleOnPage(String contentOfHTTPPage, String pageTitle, String commandTitle, File file){
			String whatToWrite = new String();
			long startTime = System.currentTimeMillis();			 
			long estimatedTime = 0;
			Pattern pat = Pattern.compile("<title>" + pageTitle + "</title>");
		    Matcher mat = pat.matcher(contentOfHTTPPage);
		    String titleOnPage = "";
		    while (mat.find()){ 
			     titleOnPage = mat.group();  
			     System.out.println(titleOnPage );
			     estimatedTime =  System.currentTimeMillis() - startTime;
			     }
		     if (mat.find()){	
			    	 whatToWrite ="+ [" + commandTitle + "]";
			    	 writeInFile (file, whatToWrite);
				     }else {
				    	 whatToWrite ="! [" + commandTitle + "]" +Long.toString(estimatedTime); 
		     writeInFile (file, whatToWrite);}
				return whatToWrite ;
				}
 
	
		public static String containsOnPage(String contentOfHTTPPage, String pageContains, String commandContains, File file){
			String whatToWrite = new String();
			long startTime =  System.currentTimeMillis();			 
			long estimatedTime = 0;
			 Pattern pat = Pattern.compile(pageContains);
		     Matcher mat = pat.matcher(contentOfHTTPPage);
		     String containsOnPage = "";
		     while (mat.find()){ 
		    	 containsOnPage = mat.group();  
			     System.out.println(containsOnPage );
			     estimatedTime = System.currentTimeMillis() - startTime;
			     }		     
		     if (mat.find()){	
			    	 whatToWrite ="+ [" + commandContains + "]";
			    	 writeInFile (file, whatToWrite);
				     }else {
				    	 whatToWrite ="! [" + commandContains + "]" +Long.toString(estimatedTime); 
		     writeInFile (file, whatToWrite);}
				return whatToWrite ;
				 
}
		
		
public static void writeInFile(File file, String whatToWrite) {
			 
		 try{
			BufferedWriter  writer = new BufferedWriter (new FileWriter(file));
			 writer.append(whatToWrite);
			 writer.flush();
			 writer.close();

				System.out.println("Done");
				if ( writer != null) writer.close( );
			} catch ( IOException e){}
		
		 
				}
		 
		
		
		public static void total(StringBuilder sb,String contentOfHTTPPage, File file){
			 findOpenInFile(sb);
			 findHrefInFile(sb, contentOfHTTPPage, file);
			 findNameInFile(sb, contentOfHTTPPage, file);
			 findTitleInFile(sb, contentOfHTTPPage, file);
			 findContainsInFile(sb, contentOfHTTPPage, file);
			 
		}
		public static void total2(String contentOfHTTPPage,String pageHref,String commandHref,
				String pageName,String commandName,String pageTitle,String commandTitle,
				String pageContains,String commandContains, File file)
		{
			hrefOnPage(contentOfHTTPPage,pageHref,commandHref, file);
			nameOnPage(contentOfHTTPPage,pageName, commandName, file);
			titleOnPage(contentOfHTTPPage,pageTitle, commandTitle, file);
			containsOnPage(contentOfHTTPPage,pageContains,commandContains, file);
}
}

