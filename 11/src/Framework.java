import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Framework 
{

	private String inputFile;
	private String outputFile;

	public void setFiles(String inputFile, String outputFile)
	{
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public static void main(String[] args) throws IOException
	{
		Framework framework = new Framework();
		framework.setFiles(args[0], args[1]);
		framework.test();
	}

	public void test() throws IOException 
	{
		Scanner in = new Scanner(new File(inputFile));
		ArrayList<String> commands = new ArrayList<String>();
		while(in.hasNextLine())
			commands.add(in.nextLine());
		in.close();

		FileWriter out = new FileWriter(new File(outputFile));
		String textOut = "";
		double totalTime = 0;
		int numOfPassed = 0, numOfFailed = 0;

		for (int i=0; i<commands.size(); i++)
		{
			String commandWithParameters = commands.get(i);
			StringTokenizer tokens = new StringTokenizer(commandWithParameters, " ");
			String command = tokens.nextToken();
			String parameters = commandWithParameters.substring(command.length() + 1);
			String parameter = parameters.substring(1, parameters.length() - 1);

			boolean checkResult = true;
			boolean skipped = false;
			long start, finish, duration = 0;

			switch (command)
			{
			case "open":
				StringTokenizer params = new StringTokenizer(parameters, "\" ");
				String url = params.nextToken();
				String timeout = params.nextToken();
				Scanner sc = new Scanner(timeout);
				if (!sc.hasNextInt())
					textOut += "ERROR: second parameter must be a number ";
				start = System.currentTimeMillis();
				checkResult = Commands.open(url, timeout);
				finish = System.currentTimeMillis();
				duration  = finish - start;
				break;
			case "checkLinkPresentByHref":
				start = System.currentTimeMillis();
				checkResult = Commands.checkLinkPresentByHref(parameter);
				finish = System.currentTimeMillis();
				duration  = finish - start;
				break;
			case "checkLinkPresentByName":
				start = System.currentTimeMillis();
				checkResult = Commands.checkLinkPresentByName(parameter);
				finish = System.currentTimeMillis();
				duration  = finish - start;
				break;
			case "checkPageTitle":
				start = System.currentTimeMillis();
				checkResult = Commands.checkPageTitle(parameter);
				finish = System.currentTimeMillis();
				duration  = finish - start;
				break;
			case "checkPageContains":
				start = System.currentTimeMillis();
				checkResult = Commands.checkPageContains(parameter);
				finish = System.currentTimeMillis();
				duration  = finish - start;
				break;
			default:
				skipped = true;
			}
			
			double timeSec = duration / 1000.0;
			totalTime += timeSec ;
			if (!skipped)
			{
				if (checkResult)
				{
					numOfPassed++;
					textOut += "+ [" + commandWithParameters + "] " + timeSec + "\r\n";
				}
				else
				{
					numOfFailed++;
					textOut += "! [" + commandWithParameters + "] " + timeSec + "\r\n";
				}
			}
			else
				textOut += "skipped [" + commandWithParameters + "]" + "\r\n";
		}
		textOut += "Total tests: " + Integer.toString(commands.size()) + "\r\n";
		textOut += "Passed/Failed: " + Integer.toString(numOfPassed) + "/" + Integer.toString(numOfFailed) + "\r\n";
		textOut += "Total time: " + totalTime + "\r\n";
		BigDecimal round = new BigDecimal(totalTime / (numOfPassed + numOfFailed));
		round = round.setScale(3, BigDecimal.ROUND_HALF_UP);
		textOut += "Average time: " + round + "\r\n";

		out.write(textOut);
		out.flush();
		out.close();
		System.out.println("output.txt generated");

	}

}
