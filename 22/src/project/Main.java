
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	private static String fileName = "a.txt";
	public static void main(String[] args) throws Exception {

 AllMethods obj = new AllMethods();
 String pageSource = obj.openUrlOnPage(1000);
 try(FileWriter writer = new FileWriter(fileName, true))
 {
 if (pageSource!="")
 {
	 writer.write("+ [open \"http://www.tut.by\" \"1\"]\n");

 }
 else 
 {
	 writer.write("! [open \"http://www.tut.by\" \"1\"]\n");
  
 }
 
 if (obj.checkLinkPresentByHref(pageSource) == true){
	 writer.write("+ [checkLinkPresentByHref \"http://afisha.tut.by/online-cinema/\"]\n");

 }
 else {
	 writer.write("! [checkLinkPresentByHref \"http://afisha.tut.by/online-cinema/\"]\n");

 }
 
 if (obj.checkLinkPresentByName(pageSource) == true){
	 writer.write("+ [checkLinkPresentByName \"http://afisha.tut.by/online-cinema/\"]\n");

 }
 else{
	 writer.write("! [checkLinkPresentByName \"http://afisha.tut.by/online-cinema/\"]\n");

 }
 
 if (obj.checkPageTitle(pageSource) == true){
	 writer.write("+ [checkPageTitle \"Белорусский портал TUT.BY\"]\n");

 }
 else{
	 writer.write("! [checkPageTitle \"Белорусский портал TUT.BY\"]\n");

 }
 
 if (obj.checkPageContains(pageSource) == true){
	 writer.write("+ [checkPageContains \"Разделы\"]");
 }
 else{
	 writer.write("! [checkPageContains \"Разделы\"]");
 }
writer.flush();

}
 catch(IOException ex){
     
     System.out.println(ex.getMessage());
 } 
	}
}
