
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RunCommands {

	public static void run(String fileIn, String fileOut) throws IOException {
		PrintWriter writer = new PrintWriter(fileOut, "UTF-8");
		String[] commands = ReadAndSave.readCommands(fileIn).split("\r\n");
		String input = "";
		String log = "";
		String successOpen = "+";
		BigDecimal timeToOpen = new BigDecimal("0.0");
		BigDecimal total = new BigDecimal("0.0");
		int totalTests = 0;
		int failed = 0;

		String operation = Parser.parseOperation(commands[0]);
		if (operation.equals("open")) {
			String url = Parser.parseFirst(commands[0]);
			if (url.equals("")) {
				System.out.println("The first parameter is absent in command open.");
				Utils.exit(log, writer);
			}

			if (!Utils.validateUrl(url)) {
				System.out.println("Incorrect URL!");
				Utils.exit(log, writer);
			}
			if (Parser.parseSecond(commands[0]).equals("")) {
				System.out.println("The second parameter is absent in command open.");
				Utils.exit(log, writer);
			}

			BigDecimal timeOut = new BigDecimal("0.0");
			try {
				timeOut = new BigDecimal(Parser.parseSecond(commands[0]));
			} catch (NumberFormatException e) {
				System.out.println("Wrong format of second parameter! ");
				Utils.exit(log, writer);
			}

			BigDecimal startTimeOpen = new BigDecimal(System.currentTimeMillis());
			BufferedReader reader = Commands.open(url);
			BigDecimal endTimeOpen = new BigDecimal(System.currentTimeMillis());
			timeToOpen = endTimeOpen.subtract(startTimeOpen).divide(new BigDecimal("1000"));
			if ((timeToOpen.compareTo(timeOut)) == 1) {
				successOpen = "!";
				failed++;
			}

			totalTests++;
			log += successOpen + " [" + operation + " \"" + Parser.parseFirst(commands[0]) + "\" " + "\""
					+ Parser.parseSecond(commands[0]) + "\"" + "] " + timeToOpen + "\n";
			input = ReadAndSave.saveInput(reader);
		}

		else {
			System.out.println(
					"Please, input command \"open\" in 1 line! Then input other commands. Each command is entered on a separate line. ");
			System.out.println("Parameters of commands: ");
			System.out.println("open \"url\" \"timeout\" " + "\n" + "checkLinkPresentByHref \"href\"" + "\n"
					+ "checkLinkPresentByName \"name\"" + "\n" + "checkPageTitle \"text\"" + "\n"
					+ "checkPageContains \"text\"");
			Utils.exit(log, writer);
		}

		for (int i = 1; i < commands.length; ++i) {
			BigDecimal startTime = new BigDecimal(System.currentTimeMillis());
			String success = "+";
			boolean checkOperation = true;
			boolean checkParameter = true;

			switch (Parser.parseOperation(commands[i])) {

			case "checkLinkPresentByHref":
				String href = Parser.parseFirst(commands[i]);
				if (href.equals("")) {
					System.out.println("The first parameter is absent in command checkLinkPresentByHref.");
					checkParameter = false;
				} else if (!Utils.validateUrl(href)) {
					System.out.println("Incorrect href!");
					checkParameter = false;
				} else if ((Commands.checkLinkPresentByHref(href, input)) == false) {
					success = "!";
					failed++;
				}
				break;
			case "checkLinkPresentByName":
				String name = Parser.parseFirst(commands[i]);
				if (name.equals("")) {
					System.out.println("The first parameter is absent in command checkLinkPresentByName.");
					checkParameter = false;
				} else if ((Commands.checkLinkPresentByName(name, input)) == false) {
					System.out.println("sdfsa");

					success = "!";
					failed++;
				}
				break;
			case "checkPageTitle":
				String title = Parser.parseFirst(commands[i]);
				if (title.equals("")) {
					System.out.println("The first parameter is absent in command checkPageTitle.");
					checkParameter = false;
				} else if ((Commands.checkPageTitle(title, input)) == false) {
					success = "!";
					failed++;
				}
				break;
			case "checkPageContains":
				String text = Parser.parseFirst(commands[i]);
				if (text.equals("")) {
					System.out.println("The first parameter is absent in command checkPageContains.");
					checkParameter = false;
				} else if ((Commands.checkPageContains(text, input)) == false) {
					success = "!";
					failed++;
				}
				break;
			default:
				System.out.println("Error in " + (i + 1)
						+ " line in \"commands.txt\"! Select from checkLinkPresentByHref, checkLinkPresentByName, checkPageTitle, checkPageContains. ");
				checkOperation = false;
			}

			if ((checkOperation == true) && (checkParameter == true)) {
				totalTests++;
				BigDecimal endTime = new BigDecimal(System.currentTimeMillis());
				BigDecimal diff = endTime.subtract(startTime).divide(new BigDecimal("1000"));
				total = total.add(diff);
				log += success + " [" + commands[i] + "] " + diff + "\n";
			}
		}

		BigDecimal totalTime = new BigDecimal("0.0");
		totalTime = total.add(timeToOpen).setScale(3, RoundingMode.HALF_UP);
	
		BigDecimal average = totalTime.divide( new BigDecimal(totalTests), 3, RoundingMode.HALF_UP);

		log += "Total tests: " + totalTests + "\n";
		log += "Passed/Failed: " + (totalTests - failed) + "/" + failed + "\n";
		log += "Total time: " + totalTime + "\n";
		log += "Average time: " + String.valueOf(average);

		writer.write(log);
		writer.close();
	}

}