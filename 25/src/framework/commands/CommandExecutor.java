import framework.commands.*;
import framework.input_output.Message;

import java.text.DecimalFormat;
import java.util.List;

public class CommandExecutor {

   public int totalTests = 0, passedTests = 0;
   public double totalTime = 0;
    public StringBuilder builder = new StringBuilder();

    public void parse(List<String> instructionsList) {
        DecimalFormat dFormat = new DecimalFormat("0.000");
        Open open = new Open();
        for (String instruction : instructionsList){
            String flag = "!";  // "!" - failed test; "+" - passed test;
            String command[] = instruction.split(" "); // parsing of string from the instruction and getting command
            if (command[0].equals("open")){ //  open command execution
                totalTests++;
                if(open.openCommand(instruction)) {
                    passedTests++;
                    flag = "+";
                }
                totalTime = totalTime + (open.time/1000.0);
                builder.append(flag + " [" + instruction + "] " + dFormat.format(open.time/1000.0) + "\r\n"); //creation string with result of the test
                continue;
            }
            if (command[0].equals("checkLinkPresentByHref")){ //  checkLinkPresentByHref command execution
                totalTests++;
                CheckLinkPresentByHref check = new CheckLinkPresentByHref();
                if(check.checkLinkHrefCommand(instruction, open.getDoc())) {
                    passedTests++;
                    flag = "+";
                }
                totalTime = totalTime + (check.time/1000.0);
                builder.append(flag + " [" + instruction + "] " + dFormat.format(check.time/1000.0)  + "\r\n");
                continue;
            }
            if (command[0].equals("checkLinkPresentByName")){ //  checkLinkPresentByName command execution
                totalTests++;
                CheckLinkPresentByName check = new CheckLinkPresentByName();
                if(check.checkLinkNameCommand(instruction, open.getDoc())) {
                    passedTests++;
                    flag = "+";
                }
                totalTime = totalTime + (check.time/1000.0);
                builder.append(flag + " [" + instruction + "] " + dFormat.format(check.time/1000.0)  + "\r\n");
                continue;
            }
            if (command[0].equals("checkPageTitle")){ //  checkPageTitle command execution
                totalTests++;
                CheckPageTitle check = new CheckPageTitle();
                if(check.checkPageTitleCommand(instruction, open.getDoc())) {
                    passedTests++;
                    flag = "+";
                }
                totalTime = totalTime + (check.time/1000.0);
                builder.append(flag + " [" + instruction + "] " + dFormat.format(check.time/1000.0)  + "\r\n");
                continue;
            }
            if (command[0].equals("checkPageContains")){ // checkPageContains command execution
                totalTests++;
                CheckPageContains check = new CheckPageContains();

                if(check.checkPageContainsCommand(instruction, open.getDoc())) {
                    passedTests++;
                    flag = "+";
                }
                totalTime = totalTime + (check.time/1000.0);
                builder.append(flag + " [" + instruction + "] " + dFormat.format(check.time/1000.0)  + "\r\n");
            }
            else { //if name of the command is wrong: result in the log file is "!", time is 0;
                totalTests++;
                new Message("WARNING: Incorrect name of command №" + totalTests + " in the instruction [" + instruction + "]").printMessage();
                builder.append(flag + " [" + instruction + "] 0,000" + "\r\n");
            }
        }
    }
}
