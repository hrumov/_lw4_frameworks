
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.Parser;

public class WorkWithFile {


	private String path="";
	private ArrayList<String> arrayOfRows = new ArrayList<String>();
	private ArrayList<String> operations = new ArrayList<String>();
	private long timeout;
	private File file;
	private BufferedWriter br;
	private String content;
	private int totalTests=0, passedTests=0;
	private double totalTime=0, averageTime=0;
			
	public WorkWithFile(String str) throws IOException{		
		path=str;
		operations.add("open");
		operations.add("checkLinkPresentByHref");
		operations.add("checkLinkPresentByName");
		operations.add("checkPageTitle");
		operations.add("checkPageContains");
		file = new File("report.txt");
		br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
	}
	
	public void readFile(){//последовательно читать файл и записывать каждую строку в элемент коллекции
		
		String S="";
		try
        {	
			FileInputStream fin=new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fin);
			Reader hn = new BufferedReader(isr);
            int i=-1;
            while((i=hn.read())!=-1){   
            	if (((char)i!='\r') && ((char)i!='\n'))
            	{
            		S+=((char)i);
            	}
            	else
            	{
            		i++;
            		arrayOfRows.add(S);
            		S="";     
            	}            	
            }   
        }
        catch(IOException ex){             
            System.out.println(ex.getMessage());
        } 
	}
	
	public void addInFile(String s) throws IOException{ //запись в файл	
		br.write(s);		
	}
	
	public void doOperations() throws IOException{
		for(String i:arrayOfRows){
			Pattern p=Pattern.compile("\"([^\"]+)");//запись значений из кавычек
			Matcher m=p.matcher(i);
			String text=new String();
			if (m.find()){
				text = i.substring(m.start()+1, m.end());
			}
			if (i.contains(operations.get(0))){
				operationOpen(text,i);	
				totalTests++;
			}
			else{
				if (i.contains(operations.get(1))){
					checkTextInHTML("href=\""+text+"\"",i);	
					totalTests++;
				}
				else{
					if (i.contains(operations.get(2))){
						checkTextInHTML(text+"</a>",i);
						totalTests++;
					}
					else{
						if (i.contains(operations.get(3))){
							checkTextInHTML("<title>"+text,i);
							totalTests++;
						}
						else{
							if (i.contains(operations.get(4))){
								checkTextInHTML(text,i);
								totalTests++;
							}
						}
					}
				}
			}
		}
		addInFile("Total tests: "+totalTests+"\r\n");
		addInFile("Passed/Failed: "+passedTests+"/"+(totalTests-passedTests)+"\r\n");
		addInFile("Total time: "+totalTime+"\r\n");
		averageTime=totalTime/totalTests;
		addInFile("Average time: "+averageTime+"\r\n");
		br.close();
	}
	
	private void readTextFromString(String s) {
		Pattern p=Pattern.compile("\"([^\"]+)");//получаем из строки ссылки или текст
		Matcher m=p.matcher(s);
		String text="";
		try{
			while (m.find()){
			text = s.substring(m.start()+1, m.end());
			}
			timeout=Long.parseLong(text);
		}
		catch(NumberFormatException e){
			timeout=1000;
		}
	}
	
	private void operationOpen(String url,String s) throws IOException{
		
		boolean flag=true;
		readTextFromString(s);
		long executeTime = System.nanoTime();//засекаем время выполнения
		
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));//открываем сайт
		executeTime=checkTime(executeTime);
		float totalT = (executeTime/1000000000.0f);
		if (totalT>(timeout)){  //выводим в файл результаты выполнения
			flag=false;
		}
		if (flag==true)
		{
			addInFile("+ [");
			passedTests++;
		}
		else{
			addInFile("! [");
		}
		
		addInFile(s+"] "+totalT);
		addInFile("\r\n");
		saveHTML(url);
	}
	
	private void checkTextInHTML(String text,String allText) throws IOException{
		long executeTime = System.nanoTime();//засекаем время выполнения
		
		if (content.contains(text))
		{
			addInFile("+ [");
			passedTests++;
		}
		else{
			addInFile("! [");
		}
		executeTime=checkTime(executeTime);
		float totalT = (executeTime/1000000000.0f);
	
		addInFile(allText+"] "+ totalT);
		addInFile("\r\n");
	}
	
	private long checkTime(long time)//рассчитываем время выполнения
	{
		time=System.nanoTime()-time;
		totalTime+=time;
		return time;
	}
	
	private void saveHTML(String path) throws IOException{//сохранение html-кода сайта в строку

		URL url = new URL(path); 
		try { 
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
		content = reader.readLine(); 
		
		} catch (IOException e) { 
		e.printStackTrace(); 
		}	       
		content=new String(content.getBytes(),"UTF-8");
		addInFile(content);
	}
}
