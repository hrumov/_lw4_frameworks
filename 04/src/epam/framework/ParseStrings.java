
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseStrings {

	private String parseURL = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";

	public boolean parseStr(ArrayList<String> ar) {
		boolean flag = false;
		for (String line : ar) {
			int count = 0;
			flag = false;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '"') {
					count++;
				}
			}
			if (count == 4 || count == 2) {
				String[] lines = line.trim().split("\"");
				if (lines.length == 4) {
					if ((lines[0].contains(" ")) && (lines[0].trim().toLowerCase().equals("open"))) {
						Pattern pattern = Pattern.compile(parseURL);
						Matcher matcher = pattern.matcher(lines[1].trim());
						while (matcher.find()) {
							flag = true;
						}
						if (!lines[2].contains(" ")) {
							flag = false;
						}
						if (flag == true) {
							flag = false;
							try {
								int time = Integer.parseInt(lines[3].trim());
								flag = true;
							} catch (NumberFormatException e) {
								System.out.println("timeout only integer");
								return flag = false;
							}
						}
					}
				} else if (lines.length == 2) {
					if (lines[0].contains(" ")) {
						flag = true;
					} else
						flag = false;
				}
			}
			if (flag == false) {
				break;
			}
		}

		return flag;
	}

}
