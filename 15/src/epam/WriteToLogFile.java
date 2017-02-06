
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class WriteToLogFile {
	final String LOGFILENAME = "./log.txt";
	File file = new File(LOGFILENAME);
	


	public void arrListToFile(ArrayList<String> output, char status, long time) {
		

		try {
			// проверяем, что если файл не существует то создаем его заново
			
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));

			Iterator<String> itr1 = output.iterator();
			StringBuffer sb = new StringBuffer(status + " [");
			while (itr1.hasNext()) {

				sb.append(itr1.next() + " ");

			}

			out.printf(sb + " ] ");
			out.printf(" %.3f%n", (float) time / 1000);

			out.close();

		} catch (IOException e) {
			System.out.println("IN/OUT Exception writing to log!!!");
		}
	}
	public void infoToLogFile(int counter, int passedCounter, long totalTime){
		try {
			PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
			
				out1.println("Total tests: "+counter);
				out1.println("Passed/Failed: "+passedCounter+"/"+(counter-passedCounter));
				out1.println("Total time: "+(float)totalTime/1000);
				out1.printf("Average time:%.3f%n ",(float)totalTime/(counter*1000));
				out1.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
