
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticWriter {
	
	private static final Logger LOG = LogManager.getLogger(StatisticWriter.class);
	
	public void printStatistic() {
		String input = "logs\\output-log.log";		
		try (BufferedReader br = new BufferedReader(new FileReader(input));
				FileWriter fw = new FileWriter(input, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			String sCurrentLine;
			int numberOfTests = 0;
			int pass = 0;
			int fail = 0;			
			while ((sCurrentLine = br.readLine()) != null) {
				numberOfTests++;
				out.println("Total tests: " + numberOfTests);
				if(sCurrentLine.contains("+")) {
					pass++;
				}
				else if(sCurrentLine.contains("!")){
					fail++;
					out.println("Passed/Failed: " + pass + "/" + fail);
				}
			}
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

}
