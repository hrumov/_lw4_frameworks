
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Framework {
	
	private File instructions_file;
	private File log;
	HTMLParser parser;
	boolean new_parser;
	
	enum Commands {open, checkLinkPresentByHref, checkLinkPresentByName,
	checkPageTitle, checkPageContains};
	private List <String> instructions = new ArrayList <String> ();
	private String final_message = "";
	private URLConnection connection;
	

	private boolean test_failed;
	private String success = "+";
	private String fail = "!";
	private int all_tests;
	private int passed_tests;
	private int failed_tests;
	double total_time;
	
	
	
	//framework with preassigned log file
	public Framework (File instructions_file, File log) {
		this.instructions_file = instructions_file;
		this.log = log;
	}
	
	//framework creates a log file
	public Framework (File instructions_file) {
		this.instructions_file = instructions_file;
		String path = instructions_file.getAbsolutePath();
		String log_path = path + "log.txt";
		log = new File (log_path);
	}
	
	// read the file with instructions
	public void readCommands () {
		boolean file_exists = checkFile (instructions_file);
		if (file_exists) {
			readFromFile();
			performInstructions ();
		} 
	}

	//checking the instruction file existence 
	private boolean checkFile(File file) {
		if (file.exists()) {
			return true;
		} else {
			final_message += file.toString() + "doesn't exist !\n";
			return false;
		}
	}
			
	/*
	 * getting strings from a file and
	 * putting it to the list of instructions	
	 */
	private void readFromFile () {
		BufferedReader f = null;
		String line = null;
		try {
			f = new BufferedReader (new FileReader (instructions_file));
			while ((line = f.readLine()) != null) {
				instructions.add(line);	
			}
		} catch (IOException e) {
			final_message += "Error while reading from the instruction file.\n";
		}
	}
	
	// perfoming instructions from the list
	private void performInstructions() {
		if (instructions.isEmpty()) {
			final_message += " There are no commands to perform!\n";
			return;
		}
		for (String instruction : instructions) {
			long start = System.currentTimeMillis();
			test_failed = false;
			parseInstructions (instruction);
			long finish = System.currentTimeMillis();
			long time = finish - start;
			total_time += time;
			testResult (instruction, time);
		}
		globalResults ();
	}

	//divide a line to a command and parameters
	private void parseInstructions(String line) {
		String parameter = "";
		Commands enum_command = null;
		
		String array [] = line.split(" ");
		enum_command = checkCommand (array);
		if (test_failed) {
			return;
		}
		for (int i = 1; i < array.length; i++) {
			parameter +=(array[i] + " ");
		}
		checkForOpen (enum_command, parameter);		
	}
	
	/*checking that a command has parameter 
	 * and the command is permitted
	 */
	private Commands checkCommand(String [] array) {
		Commands enum_command = null;
		
		if (array.length == 1) {
			test_failed = true;
		}
		String command = array[0];
		try {
			enum_command = Commands.valueOf(command);
		} catch (IllegalArgumentException e) {
			test_failed = true;
		}
		return enum_command;
	}

	/* checking the connection with the page
	 * under tests
	 */
	private void checkForOpen(Commands enum_command, String parameter) {
		if (enum_command.equals(Commands.open)) {
			int timeout = findTimeout (parameter);
			String url = findURL (parameter);
			open (url, timeout);
			new_parser = true;
		} else {
			new_parser = false;
			performCommand (enum_command, parameter);	
		}		
	}
	
	private String findURL(String parameter) {
		String url = null;
		String [] array = parameter.split(" ");
		url = array[0];
		return url;
	}

	// parsing the parameter for timeout
	private int findTimeout(String parameter) {
		int timeout = 0;
		String [] array = parameter.split(" ");
		if ((array.length < 2)) {
			test_failed = true;
			return timeout;
		}
		try {
			timeout = (Integer.parseInt(array[1]))*1000;
		} catch (NumberFormatException e) {
			test_failed = true;
			return timeout;
		}
		return timeout;
	}

	// opening connection with the preassigned URL
	private void open(String url, int timeout) {
		connection = null;
		URL page = null;
			
		try {
			long start = System.currentTimeMillis();
			page = new URL (url);
			connection = page.openConnection();
			long finish = System.currentTimeMillis();
			long time = (int) (finish - start);
			if (time >= timeout) {
				test_failed = true;
			}
		} catch (MalformedURLException e) {
			final_message +=" URL is incorrect.\n";
		} catch (IOException e) {
			final_message += " Error while getting the URL.\n";
		}
	}
	
	// perfoming a command with parameters
	private void performCommand(Commands enum_command, String parameter) {
		String command = enum_command.toString();
		
		if (connection != null) {
			try {
				parser = HTMLParser.parseWebPage (connection, command, parameter, parser, new_parser);
				test_failed = parser.isTest_failed();
			} catch (IOException e) {
				final_message += e.getMessage() + "Error while reading the page content.\n";
				test_failed = true;
			}
		} else {
			test_failed = false;
		}
	}
	
	// defining a test as failed or successful
	private void testResult(String instruction, long time) {
		if (!test_failed) {
			writeTestResult (success, instruction, time);
			passed_tests++;
		} else {
			writeTestResult (fail, instruction, time);
			failed_tests++;
		}
		all_tests++;
	}
	
	
	//writing a result of a test to the log
	private void writeTestResult (String result, String instruction, long time) {
		double time_in_sec = milliSecToSec (time);
		String test_result = result + " " + instruction + " " +time_in_sec;
		writeToFile(test_result);
		
	}
	
	// writing tests statistics to the log
		private void globalResults() {
			String result = "";
			double avg_time = 0.0;
			
			if (all_tests != 0) {
				avg_time = (double) total_time/all_tests;
			}
			avg_time = milliSecToSec ( (long) avg_time);
			total_time = milliSecToSec ( (long) total_time);
			result = "Total tests: " + all_tests;
			writeToFile (result);
			result = "Passed/Failed: " + passed_tests + "/" + failed_tests;
			writeToFile (result);
			result = "Total time: " + total_time;
			writeToFile (result);
			result = "Average time: " + avg_time; 
			writeToFile (result);
		
		}
	
	// write a string to the file
	private void writeToFile (String text) {
		BufferedWriter wr = null;
		try {
			wr = new BufferedWriter (new FileWriter(log, true), 1024);
			wr.write(text);
			wr.write("\n");
		} catch (IOException e) {
			System.out.println("IO Exception "+e.getMessage());
		} finally {
			try {
				wr.close();
			} catch (IOException e) {
				System.out.println("IO Exception "+e.getMessage());
			}
		}	
	}

	// milliseconds to seconds
	private double milliSecToSec ( long millsec) {
		double seconds = 0.0;
		double mills = (double) millsec;
		seconds = mills/1000;
		return seconds;
	}
	
	public String getFinal_message() {
		return final_message;
	}

}
