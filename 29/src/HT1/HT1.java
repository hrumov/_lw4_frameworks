
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 */
class TestExecutionInfo
{
    public boolean passed;
    public String commandName;
    public String arguments;
    public double executionTime;
}


public class HT1
{
    static PageChecker pageChecker;

    public static void main(String[] args)
    {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
        writeLog("####################################### " + timeStamp);
        pageChecker = new PageChecker();

        File file = new File("Instructions.txt");
        try
        {
            Scanner scanner = new Scanner(file);
            int totalTests =0;
            double totalTime = 0;
            int passed = 0;
            int failed = 0;
            while (scanner.hasNextLine())
            {
                TestExecutionInfo info =  executeInstruction(scanner.nextLine());
                totalTime += info.executionTime;

                //generate log entry
                String logEntry;
                if(info.passed)
                {
                    passed ++;
                    logEntry = "+";
                }
                else
                {
                    failed ++;
                    logEntry = "!";
                }
                logEntry += " ["+ info.commandName + " " + info.arguments +"] "+ String.format("%.3f", info.executionTime);
                writeLog(logEntry);
                totalTests++;
            }
            double averageTime = totalTime/totalTests;
            writeLog("Total tests: " + totalTests);
            writeLog("Passed/Failed: "+ passed+"/"+failed);
            writeLog("Total time: " + String.format("%.3f", totalTime));
            writeLog("Average time: " + String.format("%.3f", averageTime));
        } catch (IOException e)
        {
            System.out.println("Error opening file");
            return;
        }
    }

    private static void writeLog(String logEntry)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(new FileWriter("Log.txt", true));
            printWriter.println(logEntry);
            printWriter.close();
        }
        catch (Exception err)
        {
            System.out.println("error writing to log file" );
        }
    }

    private static TestExecutionInfo executeInstruction(String instruction)
    {
        TestExecutionInfo info = new TestExecutionInfo();
        //parse instruction
        int splitIndex = instruction.indexOf("(");
        info.commandName = instruction.substring(0,splitIndex -1);
        String argumentsStr = instruction.substring(splitIndex).replaceAll("\\(", "").replaceAll("\\)", ""); //remove all brackets and quotes
        info.arguments = argumentsStr;
        String[] arguments = argumentsStr.replaceAll("\"", "").split("[\" \"]");
        double startTime = System.currentTimeMillis();
        boolean result = executeCommand(info.commandName, arguments);
        info.passed = result;
        double endTime = System.currentTimeMillis();
        double duration = (endTime - startTime)/1000;  //divide by 1000 to get seconds
        info.executionTime = duration;
        return info;
    }

    private static boolean executeCommand(String commandName, String[] arguments)
    {
        boolean result = false;
        if (commandName.equals("open"))
        {
            result = pageChecker.open(arguments[0], arguments[1]);
        } else if (commandName.equals("checkLinkPresentByHref"))
        {
            result = pageChecker.checkLinkPresentByHref(arguments[0]);
        } else if (commandName.equals("checkLinkPresentByName"))
        {
            result = pageChecker.checkLinkPresentByName(arguments[0]);
        } else if (commandName.equals("checkPageTitle"))
        {
            result = pageChecker.checkPageTitle(arguments[0]);
        } else if (commandName.equals("checkPageContains"))
        {
            result = pageChecker.checkPageContains(arguments[0]);
        }
        return result;
    }

}

