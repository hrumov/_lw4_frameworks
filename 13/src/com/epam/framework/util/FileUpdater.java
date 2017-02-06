
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUpdater {

    public static String filePath = "resources/message.txt";

    public static void appendText(String text) {
        File file = new File(filePath);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}