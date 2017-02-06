import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
class InstructionsParser {
    private static List<String> instructionsList;

    static List<String> getInstructionsList() {
        return instructionsList;
    }

    static void parseInstructionsFromFile(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        instructionsList = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if (!line.trim().isEmpty()) {
                    instructionsList.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
