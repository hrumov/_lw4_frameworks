import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WorkWithFile {

	private String readFilePath;
	private String writeFilePath;
	private ArrayList<String> fileLines = new ArrayList<String>();

	WorkWithFile() {
	}

	WorkWithFile(String readFilePath, String writeFilePath) {
		this.readFilePath = readFilePath;
		this.writeFilePath = writeFilePath;
	}

	public String getReadFilePath() {
		return readFilePath;
	}

	public String getWriteFilePath() {
		return writeFilePath;
	}

	public ArrayList<String> getFileText(String readFilePath) throws IOException { // read file with instructions by lines
		BufferedReader br = new BufferedReader(new FileReader(readFilePath));
		String line;
		while ((line = br.readLine()) != null) {
			fileLines.add(line);
		}
		br.close();
		return fileLines;
	}

	public void comands() throws UnknownHostException {
		int passedTests = 0, failedTests = 0, totalTests = 0;
		double totalTime = 0;
		WorkWithSite pageCode = new WorkWithSite();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");  
		for (int i = 0; i < fileLines.size(); i++) {
			if (fileLines.get(i).contains("open")) {
				totalTests ++;

				int firstSymbol = fileLines.get(i).indexOf("\"") + 1; // cut off all before first " and after last " (get url" "4)
				int lastSymbol = fileLines.get(i).lastIndexOf("\"");
				String urlPlusTime = fileLines.get(i).substring(firstSymbol, lastSymbol);
				int firstSymbolURLPlusTime = urlPlusTime.indexOf("\"");
				int lastSymbolURLPlusTime = (urlPlusTime.lastIndexOf("\"")) + 1;
				String url = urlPlusTime.substring(0, firstSymbolURLPlusTime); // получаю url
				String time = urlPlusTime.substring(lastSymbolURLPlusTime, urlPlusTime.length()); // получаю время из файла
				
				pageCode.setSiteURL(url);

				double startTime = System.nanoTime();
				try {
					pageCode.getPageCode();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double finishedTime = System.nanoTime();
				double doneTime = (finishedTime - startTime) / 1000000; // вычисляю затраченное на операцию время
				double instructionTime = (Double.parseDouble(time)) * 1000;

				if (doneTime > instructionTime) {
					failedTests ++;
					String command = "! [" + fileLines.get(i) + "] " + sdf.format(System.currentTimeMillis());
					try { // запись результатов в файл
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					passedTests ++;
					
					String command = "+ [" + fileLines.get(i) + "] " + doneTime ;
					try { // запись результатов в файл
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalTime = totalTime + doneTime;
				}

			}
			if (fileLines.get(i).contains("checkLinkPresentByHref")) {
				totalTests ++;
				double startTime = System.nanoTime();
				int firstSymbol = fileLines.get(i).indexOf("\"") + 1;
				int lastSymbol = fileLines.get(i).lastIndexOf("\"");
				String linkHref = "href=\"" + fileLines.get(i).substring(firstSymbol, lastSymbol) + "\"";
				if (pageCode.getHtmlPage().contains(linkHref)) {
					passedTests ++;
					double finishedTime = System.nanoTime();
					double doneTime = (finishedTime - startTime) / 1000000;
					
					String command = "+ [" + fileLines.get(i) + "] " + doneTime ;
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalTime = totalTime + doneTime;
				} else {
					failedTests ++;
					
					String command = "! [" + fileLines.get(i) + "] " + sdf.format(System.currentTimeMillis());
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (fileLines.get(i).contains("checkLinkPresentByName")) { // done
				totalTests ++;
				double startTime = System.nanoTime();
				int firstSymbol = fileLines.get(i).indexOf("\"") + 1;
				int lastSymbol = fileLines.get(i).lastIndexOf("\"");
				String linkByName = "<a href=\"" + fileLines.get(i).substring(firstSymbol, lastSymbol) + "</a>";
				pageCode.getHtmlPage();
				if (pageCode.getHtmlPage().contains(linkByName)) {
					passedTests ++;
					double finishedTime = System.nanoTime();
					double doneTime = (finishedTime - startTime) / 1000000;
					
					String command = "+ [" + fileLines.get(i) + "] " + doneTime ;
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalTime = totalTime + doneTime;
				} else {
					failedTests ++;
					
					String command = "! [" + fileLines.get(i) + "] " + sdf.format(System.currentTimeMillis());
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (fileLines.get(i).contains("checkPageTitle")) { // done
				totalTests ++;
				double startTime = System.nanoTime();
				int firstSymbol = fileLines.get(i).indexOf("\"") + 1;
				int lastSymbol = fileLines.get(i).lastIndexOf("\"");
				String pageTitle = "<title>" + fileLines.get(i).substring(firstSymbol, lastSymbol) + "</title>";
				pageCode.getHtmlPage();
				if (pageCode.getHtmlPage().contains(pageTitle)) {
					passedTests ++;
					double finishedTime = System.nanoTime();
					double doneTime = (finishedTime - startTime) / 1000000;
					
					String command = "+ [" + fileLines.get(i) + "] " + doneTime ;
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalTime = totalTime + doneTime;
				} else {
					failedTests ++;
					
					String command = "! [" + fileLines.get(i) + "] " + sdf.format(System.currentTimeMillis());
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (fileLines.get(i).contains("checkPageContains")) { // done
				totalTests ++;
				double startTime = System.nanoTime();
				int firstSymbol = fileLines.get(i).indexOf("\"") + 1;
				int lastSymbol = fileLines.get(i).lastIndexOf("\"");
				String pageContains = fileLines.get(i).substring(firstSymbol, lastSymbol);
				pageCode.getHtmlPage();
				if (pageCode.getHtmlPage().contains(pageContains)) {
					passedTests ++;
					double finishedTime = System.nanoTime();
					double doneTime = (finishedTime - startTime) / 1000000;
					
					String command = "+ [" + fileLines.get(i) + "] + " + doneTime ;
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalTime = totalTime + doneTime;
				} else {
					failedTests ++;
					
					String command = "! [" + fileLines.get(i) + "] " + sdf.format(System.currentTimeMillis());
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
						bw.write(command);
						bw.newLine();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		String resultsTotalTests = "Total tests: " + totalTests;
		String passedFailedTests = "Passed/Failed: " + passedTests + "/" + failedTests;
		String totalTimeTests = "Total time: " + totalTime;
		String averageTimeTests = "Average time: " + totalTime/totalTests;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeFilePath, true));
			bw.write(resultsTotalTests);
			bw.newLine();
			
			bw.write(passedFailedTests);
			bw.newLine();
			
			bw.write(totalTimeTests);
			bw.newLine();
			
			bw.write(averageTimeTests);
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
