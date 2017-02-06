
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class FileAction {
	private BufferedReader read;
	private ArrayList<String> lines = new ArrayList<>();
	private File logFile;
	
	public ArrayList<String> readLines() throws IOException {
		read = new BufferedReader(new FileReader("test2.txt"));
		String line;
		String firstLine;
		if((firstLine=read.readLine()) == null){
			System.out.println("ERROR: File is empty.");
		}else{
			lines.add(firstLine);
		}
		while ((line = read.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}
	
	
	public void createLog(String path) {
		logFile = new File(path);
		if (logFile.exists()) {
			logFile.delete();
		}
	}
	
	public void writeLog(String flag, String commandLine, double time) {
		FileWriter writer;
		try {
			writer = new FileWriter(logFile, true);
			writer.write(flag + " [" + commandLine + "] " + time + "\r\n");
			writer.flush();
		} catch (IOException e) {
			System.out.println("File writing error");
		}

	}

	// формат вывода последней строки - 3 знака после запятой
	public void writeStatistics(int testsSum, int testsPassed, double totalTime) {
		FileWriter writer;
		try {
			writer = new FileWriter(logFile, true);
			writer.write("Total tests: " + testsSum + "\r\n");
			writer.write("Passed/Failed:" + testsPassed + "/" + (testsSum - testsPassed) + "\r\n");
			writer.write("Total time: " + (totalTime / 1000) + "\r\n");
			DecimalFormatSymbols dotSymbols = new DecimalFormatSymbols();
			dotSymbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("##.###", dotSymbols);
			String averageTime = df.format(totalTime / 1000 / testsSum);
			///Double averageTime =  (double) (Math.round(totalTime/testsSum/1000  * 100) / 100);
			writer.write("Average time: " + averageTime + "\r\n");
			writer.flush();
		} catch (IOException e) {
			System.out.println("File writing error");
		}
	}
}