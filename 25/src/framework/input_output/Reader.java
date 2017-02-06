
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    File instructionFile;

    public Reader(String[] args){
        // check the required number of arguments when the application starts. in the case of incorrect number of arguments the application will be stopped
        if (args.length !=2){
            String USAGE = "USAGE: To start the application you should specify correct path to the file with instructions as the first argument" +
                    " and path to the log file as the second argument.\nIf a log file doesn't exist it will be created in the specified path";
            String EXAMPLE = "EXAMPLE: D:\\framework\\instructions.txt D:\\framework\\log.txt";
            new Message(USAGE + "\n" + EXAMPLE).printMessage();
            System.exit(1);
        }

        this.instructionFile = new File(args[0]); //path to to the file with instructions
        if (!instructionFile.exists()){
            new Message("The file with instructions doesn't exist").printMessage();
            System.exit(1);
        } else if (instructionFile.length() == 0) {
            new Message("There are no commands to execute. File is empty").printMessage();
            System.exit(1);
        }
    }

    public List<String> readInstructionFile() { //reading file with instructions
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(instructionFile)));
            String string;
            while ((string = reader.readLine()) != null) {
                list.add(string);
            }
            reader.close();
        } catch (IOException e) {
            new Message(e.getMessage()).printMessage();
            System.exit(1);
        }
        return list;
    }
}
