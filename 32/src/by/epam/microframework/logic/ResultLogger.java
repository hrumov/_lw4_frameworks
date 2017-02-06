
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import by.epam.microframework.entity.Command;
import by.epam.microframework.exception.HTMLWriterException;

public class ResultLogger implements AutoCloseable  {

	private static final String FAIL_COMMAND = "!";
	private static final String OK_COMMAND = "+";
	private BufferedWriter writer;

	public ResultLogger(String path) throws HTMLWriterException {
		try {
			writer = new BufferedWriter(new FileWriter(new File(path)));
		} catch (IOException e) {
			throw new HTMLWriterException(e);
		}
	}

	public void command(boolean success, Command command, long time) throws HTMLWriterException {
		try {
			writer.write(successString(success) + command.toString() + time / 1000.0);
			writer.newLine();
		} catch (IOException e) {
			throw new HTMLWriterException(e);
		}
	}

	public void statistic(int totalTests, int passed, long totalTime) throws HTMLWriterException {
		try {
			writer.write("Total tests: " + totalTests + "\n" +
					"Passed/Failed: " + passed+'/'+(totalTests-passed)+"\n" +
					"Total time: " + totalTime/1000.0 + "\n" +
					"Average time: " + totalTime/totalTests/1000.0 + "\n");
		} catch (IOException e) {
			throw new HTMLWriterException(e);
		}	
	}

	private String successString(boolean success) {
		if (success) {
			return OK_COMMAND;
		} else {
			return FAIL_COMMAND;
		}
	}

	@Override
	public void close() throws Exception {
		writer.close();
		
	}
}
