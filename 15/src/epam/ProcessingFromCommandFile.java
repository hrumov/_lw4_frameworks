
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessingFromCommandFile {
	static int counter;
	static int passedCounter;
	static long totalTime;
	static long time;
	static int timeout;
	static List<String> allLines = new ArrayList<String>();
	static WriteToLogFile writeLog = new WriteToLogFile();
	static boolean key;

	public static void main(String[] args) {

		final String FILEFORURL = "./in.txt";
		final String COMMANDFILENAME = "./from.txt";

		String nextLine;
		String data = "";
		System.out.println("Command file name- " + COMMANDFILENAME);

		File file = new File(COMMANDFILENAME);

		// провер€ем, что если файл не существует, то создаем его

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			// провер€ем файл или первую строку на пустоту
			if (file.length() == 0 || (br.readLine() == null)) {
				System.out.println("Empty string or command file:" + COMMANDFILENAME);
				printWarning();
				br.close();
			}
			allLines = readUsingFiles(COMMANDFILENAME);
			System.out.println(allLines);
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e2) {

			e2.printStackTrace();
		}

		for (String item : allLines) {

			nextLine = item;

			ArrayList<String> commands = new ArrayList<String>();

			counter++;

			if (nextLine.startsWith("open")) {

				nextLine = nextLine.replaceAll("\"", "");
				String[] word = nextLine.split(" ");

				Collections.addAll(commands, checkAndTrimCommand(word));
				System.out.println(commands);
				String ur = commands.get(1);

				timeout = 1000 * Integer.parseInt(commands.get(2));

				time = System.currentTimeMillis();
				ReadFromURL rd = new ReadFromURL();
				try {// читаем с URL
					data = rd.read(FILEFORURL, ur, timeout);

				} catch (IOException e) {
					System.out.println("In/out error reading from URL");
				}
				if (data.length() != 0) {
					key = true;
				} else {
					key = false;
				}
				prosessingMethod(key, commands);

				if (time >= timeout) {
					System.err.println("Exeeding of timeout(" + timeout / 1000 + " sec) running " + commands);
					// System.err.println("Writing to log is not performed!!");
					continue;
				}
			} else {

				String[] word = nextLine.split("\"");
				Collections.addAll(commands, checkAndTrimCommand(word));

				String com = commands.get(0);
				String str = commands.get(1);

				time = System.currentTimeMillis();
				switch (com) {
				case "checkLinkPresentByHref":
					CheckLinkPresentByHref chkHref = new CheckLinkPresentByHref();
					key = chkHref.findElements(data, str);
					prosessingMethod(key, commands);
					break;

				case "checkLinkPresentByName":
					CheckLinkPresentByName chkName = new CheckLinkPresentByName();
					key = chkName.findElements(data, str);
					prosessingMethod(key, commands);
					break;

				case "checkPageContains":
					CheckPageContains chkCont = new CheckPageContains();
					key = chkCont.findElements(data, str);
					prosessingMethod(key, commands);
					break;

				case "checkPageTitle":
					CheckPageTitle chkTitle = new CheckPageTitle();
					key = chkTitle.findElements(data, str);
					prosessingMethod(key, commands);
					break;
				default:
					System.out.println("No such command !!! Check the syntax!!");

				}

			}
		}
		if (allLines.size() == counter) {

			writeLog.infoToLogFile(counter, passedCounter, totalTime);
		}

	}

	public static void prosessingMethod(boolean key, ArrayList<String> commands) {

		if (key == true) {

			char status = '+';

			time = (System.currentTimeMillis() - time);
			totalTime += time;
			passedCounter++;
			writeLog.arrListToFile(commands, status, time);

		} else {
			char status = '!';

			time = (System.currentTimeMillis() - time);
			totalTime += time;

			writeLog.arrListToFile(commands, status, time);
		}
	}

	public static List<String> readUsingFiles(String fileName) throws IOException {
		Path path = Paths.get(fileName);

		// считываем содержимое файла в список строк

		return Files.readAllLines(path, StandardCharsets.UTF_8);
	}

	public static String[] checkAndTrimCommand(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
			if (arr[i] == null) {
				System.out.println("Incorrect command syntax!!");
				printWarning();
			}
		}
		return arr;

	}

	public static void printWarning() {
		System.out.println("Please, fill the command file with commands:");
		System.out.println("open \"http://www.somehost\"  \"timeout\"");
		System.out.println("checkLinkPresentByHref \"Some link href\"");
		System.out.println("checkLinkPresentByName \"Some link name\"");
		System.out.println("checkPageContains \"Some text\"");
		System.out.println("checkPageTitle \"Some text\"");
	}
}