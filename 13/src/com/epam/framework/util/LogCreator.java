
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogCreator {
    public static String filePath = "resources/log.txt";

    public static void appendText(StringBuilder text) {
        File file = new File(filePath);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(String.valueOf(text));
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
