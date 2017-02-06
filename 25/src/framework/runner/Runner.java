
import framework.commands.CommandExecutor;
import framework.input_output.Logger;
import framework.input_output.Reader;

public class Runner {

    public static void main(String[] args) {
        //instruction file reading
        Reader reader = new Reader(args);

        //parsing and execution of instructions
        CommandExecutor comEx = new CommandExecutor();
        comEx.parse(reader.readInstructionFile());

        // log file creation
        Logger logger = new Logger(args,comEx.totalTests,comEx.passedTests,comEx.builder,comEx.totalTime);
        logger.writeLog();
    }
}
