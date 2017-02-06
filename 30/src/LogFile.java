import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 */
public class LogFile {
    public int totalTest;
    public int passed;
    public int failed;
    public double totalTime;

    public File writeLogFile (String text) {
        File file=new File("D://logFile.txt");
        try
        {
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(text);
            bw.write("\r\n");
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    public File writeResults (int totalTest, int passed, int failed, double totalTime){
        File file=new File("D://logFile.txt");
        try
        {
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write("Total tests: " + totalTest);
            bw.write("\r\n");
            bw.write("Passed/Failed: " + passed + "/" + failed);
            bw.write("\r\n");
            bw.write("Total time: " + String.format("%.3f", totalTime));
            bw.write("\r\n");
            bw.write("Average time: " + String.format("%.3f", totalTime / totalTest));
            bw.write("\r\n");
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }
}

