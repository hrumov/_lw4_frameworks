
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.FilerException;

public class Program {
	private static String URL;
	//we use this field to work with user
	private static String answer;
	private static double start;
	private static double finish;
	private static double timeConsumedMillis;
	private static double totalTime;
	private static String wayToFileToRead;
	private static String wayToFileToWrite;
	public static void main(String [] args) throws IOException {
		setWayToFileToRead();
		userInteraction();
		start(wayToFileToRead,wayToFileToWrite);
	}		
	public static String start(String wayToFileToRead, String wayToFileToWrite) throws  IOException{
		//Always clean the file which will be written
		clearFile(wayToFileToWrite);
		int totalTests = 0, passed = 0, failed = 0;
		double averageTime = 0;
		String log = "";
		
		for(String l1: listLinesFiles(wayToFileToRead)){
			if(parsingCommandsOpen(l1).get(0).equals("open")){
				totalTests++;
				setURL(parsingCommandsOpen(l1).get(1));
				if(open(parsingCommandsOpen(l1).get(1), Integer.parseInt(parsingCommandsOpen(l1).get(2)))){
					log = "+ [" + parsingCommandsOpen(l1).get(0) + " "
							+ "\"" + parsingCommandsOpen(l1).get(1) + "\"" + " "
							+ "\"" + parsingCommandsOpen(l1).get(2) + "\"" +" ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					passed++;
				} else{
						log = "! [" + parsingCommandsOpen(l1).get(0) + " "
								+ "\"" + parsingCommandsOpen(l1).get(1) + "\""  + " "
								+ "\"" + parsingCommandsOpen(l1).get(2) + "\"" + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					failed++;	
				}
			} else if(parsingOtherCommands(l1).get(0).equals("checkLinkPresentByHref")){
				totalTests++;
				if(checkLinkPresentByHref(parsingOtherCommands(l1).get(1))){
						log = "+ [" + parsingOtherCommands(l1).get(0) + " "
								+ parsingOtherCommands(l1).get(1) + " ] "  + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					passed++;	
				} else{
						log = "! [" + parsingOtherCommands(l1).get(0) + " "
								+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					failed++;
				}
			} else if(parsingOtherCommands(l1).get(0).equals("checkLinkPresentByName")){
				totalTests++;
				if(checkLinkPresentByHref(parsingOtherCommands(l1).get(1))){
						log = "+ [" + parsingOtherCommands(l1).get(0) + " "
							+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					passed++;
				} else{
						log = "! [" + parsingOtherCommands(l1).get(0) + " "
								+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					failed++;
				}
			} else if(parsingOtherCommands(l1).get(0).equals("checkPageTitle")){
				totalTests++;
				if(checkLinkPresentByHref(parsingOtherCommands(l1).get(1))){
						log = "+ [" + parsingOtherCommands(l1).get(0) + " "
							+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					passed++;
				} else{
						log = "! [" + parsingOtherCommands(l1).get(0) + " "
								+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					failed++;
				}
			} else if(parsingOtherCommands(l1).get(0).equals("checkPageContains")){
				totalTests++;
				if(checkLinkPresentByHref(parsingOtherCommands(l1).get(1))){
						log = "+ [" + parsingOtherCommands(l1).get(0) + " "
							+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					passed++;
				} else{
						log = "! [" + parsingOtherCommands(l1).get(0) + " "
								+ parsingOtherCommands(l1).get(1) + " ] " + getTimeConsumedMillis() / 1000;
					fileWrite(wayToFileToWrite, log, 1);
					failed++;
				}
			}
		}
		averageTime = (totalTime /(passed + failed)); 
		addTimeToLog(totalTests, passed, failed, averageTime);
		return log;
	}
	//method that add results in file
	public static void addTimeToLog(int totalTests, int passed, int failed,double averageTime) {
		String linkOfTotalTests = "Total tests: " + totalTests;
		String linkOfPassedFailed = "Passed/Failed: " + passed + "/" + failed;
		String linkOfTotalTime = "Total time: " + totalTime / 1000 ;
		String linkOfAverageTime = "Average time: " + averageTime / 1000;
		
		fileWrite(wayToFileToWrite, linkOfTotalTests, 1);
		fileWrite(wayToFileToWrite, linkOfPassedFailed, 1);
		fileWrite(wayToFileToWrite, linkOfTotalTime, 1);
		fileWrite(wayToFileToWrite, linkOfAverageTime, 1);
	}
	
	public static boolean open(String url,int timeout) throws IOException  {
		setStart(System.currentTimeMillis());
		URLConnection connection = new URL(url).openConnection();
		java.net.HttpURLConnection con =(java.net.HttpURLConnection)connection;
		connection.addRequestProperty("User-Agent", 
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		//con.setConnectTimeout(timeout);
		try {
			con.getResponseCode();
		} catch(Exception e){
			System.out.println("Sorry but this url isn't correct \n"
					+ "mayby you have some problems with yor internet connection");
			printHelpCommands();
			System.exit(0);
		}
		boolean result = false;
		//We look at the response from the server if it is in the range of 200 to 299 returns true
		//
		if(con.getResponseCode() >= 200 && con.getResponseCode() < 300 && timeout * 1000 > getTimeConsumedMillis()) {
			result = true;
		}
		setFinish(System.currentTimeMillis());
		setTimeConsumedMillis(getFinish() - getStart());
		totalTime += getTimeConsumedMillis();
		return result;
	}
	public static String getContent() throws IOException {
		if(URL.isEmpty()) {
			System.out.println("URL is empty");
			printHelpCommands();
			System.exit(0);
		}
		String content = "";
		URLConnection connection = new URL(URL).openConnection();
		connection.addRequestProperty("User-Agent", 
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		java.net.HttpURLConnection con =(java.net.HttpURLConnection)connection;
		Scanner scanner = new Scanner(con.getInputStream());
		scanner.useDelimiter("\\Z");
		content = scanner.next();
		return content;
	}
	public static boolean checkLinkPresentByHref(String href) throws IOException {
		setStart(System.currentTimeMillis());
		String html = getContent();
		Pattern p = Pattern.compile("(?<=(?i)href\\s{0,1}=\\s{0,1}\").*?(?=\")");
        Matcher m = p.matcher(html);
        String hrefs = "";
        boolean result;
        while (m.find()) {
            hrefs += m.group();
        }
        if(checForWord(hrefs, href)) {
        	result = true;
        } else{result = false;}
        
        setFinish(System.currentTimeMillis());
		setTimeConsumedMillis(getFinish() - getStart());
		totalTime += getTimeConsumedMillis();
		return result;
	}
	public static boolean checkLinkPresentByName(String linkName) throws IOException {
		setStart(System.currentTimeMillis());
		String html = getContent();
		Pattern p=Pattern.compile("(?<=)(?i)(<a.*?>)(.+?)()>");
	    Matcher m=p.matcher(html);
	    String names = "";
        boolean result;
        while (m.find()) {
            names += m.group();
        }
        if(checForWord(names, linkName)) {
        	result = true;
        } else{result = false;}
        setFinish(System.currentTimeMillis());
		setTimeConsumedMillis(getFinish() - getStart());
		totalTime += getTimeConsumedMillis();
		return result;
	}
	public static boolean checkPageTitle(String title) throws IOException {
		setStart(System.currentTimeMillis());
		String html = getContent();
		Pattern p=Pattern.compile("(?<=)(?i)(<title.*?>)(.+?)()>");
	    Matcher m=p.matcher(html);
	    String names = "";
        boolean result;
        while (m.find()) {
            names += m.group();
        }
        if(checForWord(names, title)) {
        	result = true;
        } else{result = false;}
        setFinish(System.currentTimeMillis());
		setTimeConsumedMillis(getFinish() - getStart());
		totalTime += getTimeConsumedMillis();
		return result;
	}
	public static boolean checkPageContains(String title) throws IOException {
		setStart(System.currentTimeMillis());
		boolean result = false;
		String content = getContent();
		if(checForWord(content, title)) {
        	result = true;
        } else{result = false;}
        setFinish(System.currentTimeMillis());
		setTimeConsumedMillis(getFinish() - getStart());
		totalTime += getTimeConsumedMillis();
		return result;
	}
	//check whether there is such a line in our line ntml
	public static boolean checForWord(String line, String word) {
        return line.contains(word);
	}
	public static String readWayToFile() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String way = reader.readLine();
		return way;
	}
		public static void createFile(String wayToFile) {
			File newFile = new File(wayToFile);
		}
		public static void fileWrite(String way,String text,int noOfLines) {
	        FileWriter fr = null;
	        BufferedWriter br = null;
	        String dataWithNewLine = text + System.getProperty("line.separator");
	        try {
	            fr = new FileWriter(way,true);
	            br = new BufferedWriter(fr);
	            for(int i = noOfLines; i>0; i--){
	                br.write(dataWithNewLine);
	            }
	        } catch (IOException e) {
	            System.out.println("The way to writable file isn't correct");
	            printHelpFileWay();
	            System.exit(0);
	        }finally {
	            try {
	                br.close();
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		} 
		public static void clearFile(String wayToFile) {
			try {
		        FileWriter fstream1 = new FileWriter(wayToFile);// конструктор с одним параметром - для перезаписи
		        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
		        out1.write(""); // очищаем, перезаписав поверх пустую строку
		         out1.close(); // закрываем
		         } catch (Exception e) 
		            {System.err.println("Error in file cleaning: " + e.getMessage());}
		}
	//perform the normal procedure for parsing commands from a text file	
	public static ArrayList<String> parsingCommandsOpen(String line){
		ArrayList<String> list = new ArrayList<>();
		String[] str = line.split("\\ ");
		for(int i = 0; i<str.length; i++) {
			str[i] =  str[i].replaceAll("\\\"","");
		}
		for(String s:str){
			list.add(s);
		}
		return list;
	}
	//parse commands in quotes, that the Pope would not superfluous commands, 
	//then perform the normal procedure for parsing commands from a text file
	public static ArrayList<String> parsingOtherCommands(String line){
		ArrayList<String> list = new ArrayList<>();
		String[] str1 = line.split("\\\" ");
		
		for(int i = 0; i<str1.length; i++){
			str1[i] =  str1[i].replaceAll("\\\"","");
		}
		
		String[] str2 = str1[0].split(" ");
		for(int i = 1; i<str2.length - 1; i++){
			str2[1] +=" " + str2[i + 1];
		}
		
		list.add(str2[0]);
		list.add(str2[1]);
		
		return list;
	}
	//read commands and save lines in array list
	public static ArrayList<String> listLinesFiles(String fileway) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(fileway));
	    String line;
	    ArrayList<String> lines = new ArrayList<String>();
	    
	    while ((line = reader.readLine()) != null) {
	    	 lines.add(line);    
	    }
	    return lines;
	}
	public static void userInteraction() throws IOException {
		System.out.println("Put \"e\" if you wont to use exist file to save result \n"
				+ "Put \"N\" or \"n\" if you wont to create a new file and save result therein ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = reader.readLine();
		if(str.equals("E") || str.equals("e")) {
			 setAnswer(str);
		} else if(str.equals("N") || str.equals("n")) {
			 setAnswer(str);
		} else{
			System.out.println("Please put only \"e\" or \"n\"");
			userInteraction();
		}
		
		if(answer.equals("E") || answer.equals("e")) {
			System.out.println("Write way to your file and name of file \n"
					+ "like : .../home/anton/nameOfSomeExistFile.txt");
			setWayToFileToWrite();
		} else if(answer.equals("N") || answer.equals("n")) {
			System.out.println("Write way to your file and name of new file \n"
					+ "like : .../home/anton/nameOfSomeNewFile.txt");
			setWayToFileToWrite();
			createFile(wayToFileToWrite);
		}
	}
	public static void printHelpCommands() {
		System.out.println("Please keep correct commands order and check that you put correct url " + "\n"
				+ "first of all command \"open\"" + "\n"
				+ "example :" + "\n"
				+ "open \"http://www.google.com\" \"3\"" + "\n"
				+ "checkPageTitle \"Google Search Page\" " + "\n"
				+ "checkPageContains \"The best search engine\"");
	}
	public static void printHelpFileWay() {
		System.out.println("Please keep correct file way, check that the file exist " + "\n"
				+ "example :" + "\n"
				+ "/home/anton/Text.txt");
	}
	//getters
	public static String getURL() {return URL;}
	public static double getStart() {return start;}
	public static double getTimeConsumedMillis() {return timeConsumedMillis;}
	public static double getFinish() {return finish;}
	public static String getAnswer() {return answer;}
	public static String getWayToFileToRead() {return wayToFileToRead;}
	public static String getWayToFileToWrite() {	return wayToFileToWrite;	}
	
	//setters
	public static void setStart(long start) {Program.start = start;}
	public static void setURL(String uRL) {URL = uRL;}
	public static void setFinish(long finish) {Program.finish = finish;}
	public static void setTimeConsumedMillis(double timeConsumedMillis) {Program.timeConsumedMillis = timeConsumedMillis;}
	public static void setAnswer(String answer) {Program.answer = answer;}
	//look, that would be the path to the file from which we will read the data was correct
	public static void setWayToFileToRead() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Program.wayToFileToRead = reader.readLine();
			BufferedReader readerFile = new BufferedReader(new FileReader(Program.wayToFileToRead));
		} catch(FileNotFoundException e){
			System.out.println("Way to readable file isn't correct");
			printHelpFileWay();
			System.exit(0);
		}
	}
	//look, that would be the path to the file from we will write the data was correct
	public static void setWayToFileToWrite() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Program.wayToFileToWrite = reader.readLine();
			BufferedReader readerFile = new BufferedReader(new FileReader(Program.wayToFileToWrite));
		} catch(FileNotFoundException e){
			System.out.println("Way to readable file isn't correct");
			printHelpFileWay();
			System.exit(0);
		}
	}
}
