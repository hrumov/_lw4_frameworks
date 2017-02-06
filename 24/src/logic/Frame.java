
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import tests.TestcheckLinkPresentByHref;

public class Frame extends RunListener {
	public static List<String> textOut = new ArrayList<String>();
	public static List<String> textIn = new ArrayList<String>();
	public List<String> textURL = new ArrayList<String>();
	public ArrayList<String> command = new ArrayList<String>();
	public static int testsPassed = 0;
	public static int testsFailed = 0;
	public static double totalTime = 0;

	public void run() throws IOException {
		readFile("C:/Users/Staravoitau/workspace/FrameworkProject/src/logic/input.txt");
		for (int i = 0; i < textIn.size(); i++) {
			splitText(i);
			detect(i);
		}
	}

	public void readFile(String dir) throws IOException {
		File in = new File(dir);
		BufferedReader reader = new BufferedReader(new FileReader(in));
		String line;
		List<String> lines = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		textIn = lines;
		reader.close();
	}

	public void open(String link) throws IOException {
		URL url = new URL(link);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ((line = in.readLine()) != null) {
			lines.add(line);
		}
		textURL = lines;
		in.close();
		/*for (int i = 0; i < textURL.size(); i++)
			System.out.println(textURL.get(i));*/
	}

	public void splitText(int index) {
		String[] comparator = textIn.get(index).split("\\s*[\"]");
		command = new ArrayList<String>(Arrays.asList(comparator));
		command.remove("");
	}

	public void detect(int i) throws IOException {
		boolean passedTest = true;
		double time = 0;
		JUnitCore junit = new JUnitCore();
		Result result;
		switch (command.get(0)) {
		case "open":
			open(command.get(1));
			System.setProperty("openlink", command.get(1));
			result = junit.run(tests.TestOpen.class);
			time = result.getRunTime() / 1000.00;
			if (time < Double.parseDouble(command.get(2)))
				passedTest = result.wasSuccessful();
			else passedTest = false;
			totalCount(time, passedTest);
			break;
		case "checkLinkPresentByHref":
			System.setProperty("link", command.get(1));
			result = junit.run(tests.TestcheckLinkPresentByHref.class);
			passedTest = result.wasSuccessful();
			time = result.getRunTime() / 1000.00;
			totalCount(time, passedTest);
			break;
		case "checkLinkPresentByName":
			System.setProperty("name", command.get(1));
			result = junit.run(tests.TestcheckLinkPresentByName.class);
			passedTest = result.wasSuccessful();
			time = result.getRunTime() / 1000.00;
			totalCount(time, passedTest);
			break;
		case "checkPageTitle":
			System.setProperty("title", command.get(1));
			result = junit.run(tests.TestcheckPageTitle.class);
			passedTest = result.wasSuccessful();
			time = result.getRunTime() / 1000.00;
			totalCount(time, passedTest);
			break;
		case "checkPageContains":
			System.setProperty("word", command.get(1));
			result = junit.run(tests.TestcheckPageContains.class);
			passedTest = result.wasSuccessful();
			time = result.getRunTime() / 1000.00;
			totalCount(time, passedTest);
			break;
		default:
			System.out.println(command.get(0) + " is not command.");
		}
		String answer;
		if (passedTest)
			answer = "+";
		else
			answer = "!";
		textOut.add(i, answer + " [" + textIn.get(i) + "] " + time);

	}

	public void makeOutput() throws IOException {
		Writer output = new BufferedWriter(
				new FileWriter(
						"C:/Users/Staravoitau/workspace/Framework/src/logic/output.txt"));
		BufferedWriter writer = new BufferedWriter(output);
		for (int i = 0; i < textOut.size(); i++) {
			writer.write(textOut.get(i) + "\r\n");
		}
		writer.write("\r\nTotal tests: " + (testsPassed + testsFailed)
				+ "\r\nPassed/Failed: " + testsPassed + "/" + testsFailed
				+ "\r\nTotal Time: " + totalTime + "\r\nAverage time: "
				+ totalTime / (testsPassed + testsFailed));
		writer.close();
	}

	private static void totalCount(double time, boolean passedTest) {
		totalTime += time;
		if (passedTest)
			testsPassed++;
		else
			testsFailed++;
	}
}
