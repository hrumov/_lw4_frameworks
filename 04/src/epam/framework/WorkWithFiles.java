
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class WorkWithFiles {
	
	private ArrayList<String> list;
	
	public WorkWithFiles(ArrayList<String> list) {
		this.list=list;
	}

	public boolean readFile(String path) throws IOException {
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(path.trim()));
			String line = null;
			while ((line = bf.readLine()) != null) {
				if (!line.trim().equals("")) {
					list.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println("File not found");
			return false;
		} catch (NullPointerException e) {
			System.out.println("File not found");
		}
		return true;
	}
	
	public ArrayList<String> getListCommands(){
		return list;
	}

	public void writeFile(String path, String logText) throws IOException {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path.trim()));
			writer.write(logText);

		} catch (IOException e) {
			System.out.println("File not found");
		} finally {
			writer.close();
		}
	}

}
