import java.io.*;
import java.util.Arrays;

/**
 */
public class TextCommandsFramework {
    public static void main(String[] args) {
        String line = "";
        File inputFile = null;
        File logFile = null;
        commandsImplementationModule analyzer = new commandsImplementationModule();

        if (args.length != 2) {
            if ((args.length==1)&&(args[0].equalsIgnoreCase("help"))){
                analyzer.doCommand("help",null);
                System.out.print(analyzer.getStatusMessage());
                return;
            }else {
                System.out.println("Usage: 2 params: Input File Output File or 1 param help");
                return;
            }
        } else {
            inputFile = new File(args[0]);
            logFile = new File(args[1]);
        }
        if(!inputFile.exists()){
            System.out.println("Input file does not exists");
        }if(!logFile.canWrite()){
            System.out.println("Cannot write log!");
        }if(!inputFile.canRead()){
            System.out.println("Cannot read input file");
        }//Handling CMD parameters

        BufferedReader commandReader = null;
        BufferedWriter logWriter;

        Report report = new Report();
        commandReader = getBufferedReader(inputFile, commandReader);
        Command command;
        logWriter = getBufferedWriter(logFile);

        while (true) {

            try {
                line = (commandReader.readLine());
            } catch (IOException e) {
                System.out.println("IO Exception when trying to read command");;
            }
            if (line == null) break;
            command = new Command(line);
            analyzer.doCommand(command.getCommand(), command.getArguments());
            write(createLogMessage(command, analyzer, report), logWriter);
        }//Processing Commands and writing Log

        write(report.createReport(), logWriter);
        flush(logWriter);//Writing report
    }

    private static String createLogMessage(Command command, commandsImplementationModule analyzer, Report report) {
        String result="";
        report.addTime(analyzer.getTime());
        if ((analyzer.getStatus()&analyzer.SUCCESSFUL_EXECUTION)!=0) {
            if ((analyzer.getStatus()&analyzer.RESULT_IS_TRUE)!=0) {
                result += "+ ";
                report.setPassed(report.getPassed() + 1);
                report.setTotal(report.getTotal() + 1);
            } else {
                result += "! ";
                report.setTotal(report.getTotal() + 1);
            }

            result += "[" + command.getCommand() + " " +
                    Arrays.toString(command.getArguments()) + "]," + report.milsToSec(analyzer.getTime()) + " ";
            if ((analyzer.getStatus()&(analyzer.ERROR))!=0){
                result+="Error: "+analyzer.getStatusMessage();
            }else {
                result += analyzer.getStatusMessage();
            }
        }else result=" Command was not executed:";
        if ((analyzer.getStatus()&analyzer.UNKNOWN_COMMAND)!=0){
            result += command.getCommand()+"-Unknown command";
        }if ((analyzer.ILLEGAL_ACCESS_EXCEPTION&analyzer.getStatus())!=0){
            result += "Illegal access exception when trying execute command";
        }if ((analyzer.getStatus()&analyzer.ILLEGAL_ARGUMENT_EXCEPTION)!=0){
            result += analyzer.getStatusMessage();
        }
        return result+" \n";
    }

    private static void flush(BufferedWriter logWriter) {
        try {
            logWriter.flush();
        } catch (IOException e) {
            System.out.println("IO Exception when trying to write to log file");
        }
    }

    private static void write(String message,BufferedWriter writer) {
        try {
            writer.write(message);
        } catch (IOException e) {
            System.out.println("IO Exception when trying to write to log buffer");
        }
    }

    private static BufferedWriter getBufferedWriter(File logFile) {
        BufferedWriter logWriter=null;
        try {
            logWriter=new BufferedWriter(new OutputStreamWriter(new PrintStream(logFile)));
        } catch (IOException e) {
            System.out.println("IO Exception when trying to create log writer");;
        }
        return logWriter;
    }

    private static BufferedReader getBufferedReader(File inputFile, BufferedReader commandReader) {
        try {
            commandReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commandReader;
    }

}
