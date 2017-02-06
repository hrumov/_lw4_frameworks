
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Solve {

    long allTime = 0;
    int fails = 0;
    
    public Solve() throws IOException {
        solve();
    }

    double roundResult(double d) {
        int precise = 1000;
        d *= precise;
        int i = (int)Math.round(d);
        return (double)i / precise;
    }

    int openIndex(ArrayList<String> commands, int index) {
        if (index + 1 < commands.size())
            for (int i = index + 1; i < commands.size(); i++) {
                StringTokenizer st = new StringTokenizer(commands.get(i));
                if (st.nextToken().equalsIgnoreCase("open"))
                    return i;
            }
        return -1;
    }
    
    void printCheckPageTitle(FileWriter writer, String oneCommand, String htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        if (st.countTokens() >= 2) {
            String command = st.nextToken();
            String pageTitle = st.nextToken();
            while (st.hasMoreTokens())
                pageTitle += " " + st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            contains = htmlCode.contains("<title>" + pageTitle + "</title>");
            long finishTime = System.currentTimeMillis() - startTime;
            if (contains) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckPageContains(FileWriter writer, String oneCommand, String htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String text = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            contains = htmlCode.contains(text);
            long finishTime = System.currentTimeMillis() - startTime;
            if (contains) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckLinkPresentByHref(FileWriter writer, String oneCommand, String htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String href = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            contains = htmlCode.contains("href=\"" + href + "\"") || htmlCode.contains("href = \"" + href + "\"");
            long finishTime = System.currentTimeMillis() - startTime;
            if (contains) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckLinkPresentByName(FileWriter writer, String oneCommand, String htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String name = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            contains = htmlCode.contains(">" +  name + "</a>") || htmlCode.contains("> " + name + " </a>");
            long finishTime = System.currentTimeMillis() - startTime;
            if (contains) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckPageContains(FileWriter writer, String oneCommand, ArrayList<String> htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        boolean flag = false;
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String text = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            for (String commandString: htmlCode) {
                contains = commandString.contains(text);
                if (contains) {
                    flag = true;
                    break;
                }
            }
            long finishTime = System.currentTimeMillis() - startTime;
            if (flag) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }            
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckLinkPresentByHref(FileWriter writer, String oneCommand, ArrayList<String> htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        boolean flag = false;
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String href = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            for (String commandString: htmlCode) {
                contains = htmlCode.contains("href=\"" + href + "\"") || htmlCode.contains("href = \"" + href + "\"");
                if (contains) {
                    flag = true;
                    break;
                }
            }
            long finishTime = System.currentTimeMillis() - startTime;
            if (flag) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }

    void printCheckLinkPresentByName(FileWriter writer, String oneCommand, ArrayList<String> htmlCode) throws IOException {
        StringTokenizer st = new StringTokenizer(oneCommand, " \"\n\t\r");
        boolean flag = false;
        if (st.countTokens() == 2) {
            String command = st.nextToken();
            String name = st.nextToken();
            boolean contains;
            long startTime = System.currentTimeMillis();
            for (String commandString: htmlCode) {
                contains = commandString.contains(">" +  name + "</a>") || commandString.contains("> " +  name + " </a>");
                if (contains) {
                    flag = true;
                    break;
                }
            }
            long finishTime = System.currentTimeMillis() - startTime;
            if (flag) {
                writer.write("+ [" + oneCommand + "] " + (double) finishTime / 1000 + "\n");
                allTime += finishTime;
            }
            else {
                writer.write("! [" + oneCommand + "] 0.000\n");
                fails++;
            }
        }
        else {
            writer.write("! [" + oneCommand + "] 0.000\n");
            fails++;
        }
    }
    
    public void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        ArrayList<String> commands = new ArrayList<String>();
        String line;        
        while ((line = reader.readLine()) != null) {
            if (!line.equalsIgnoreCase(""))
                commands.add(line.trim());
        }
        reader.close();
        FileWriter writer = new FileWriter("log.txt");
        for (int i = 0; i < openIndex(commands, -1); i++) {
            writer.write("! [" + commands.get(i) + "] 0.000\n");
            fails++;
        }
        ArrayList<OpenCommands> allCommands = new ArrayList<OpenCommands>();
        for (int i = 0; i < commands.size(); i++) {
            StringTokenizer st = new StringTokenizer(commands.get(i), " \n\t\r\"");
            String commandName = st.nextToken();
            ArrayList<String> list = new ArrayList<String>();
            if (commandName.equalsIgnoreCase("open")) {
                if (commands.size() != 1) {
                    int bufInd = openIndex(commands, i);
                    if (bufInd != -1) {
                        for (int j = i; j < bufInd; j++)
                            list.add(commands.get(j));
                    } else {
                        for (int j = i; j < commands.size(); j++)
                            list.add(commands.get(j));
                    }
                } else
                    list.add(commands.get(0));
                allCommands.add(new OpenCommands(list));
            }
        }
        int index;
        for (OpenCommands openCommands: allCommands) {
            index = 0;
            StringTokenizer st = new StringTokenizer(openCommands.getList().get(index), " \n\t\r\"");
            String command = st.nextToken();
            String urlStr = st.nextToken();
            double timeout;
            try {
                timeout = Double.parseDouble(st.nextToken());
            }
            catch (NoSuchElementException e) {
                writer.write("! [" + openCommands.getList().get(index) + "] 0.000\n");
                fails++;
                continue;
            }
            String badElem = "";
            if (st.hasMoreTokens())
                badElem = st.nextToken();
            Open open = new Open(urlStr, timeout);
            URL url;
//            ArrayList<String> htmlStrings = new ArrayList<String>();
            String htmlCode;
            try {
                htmlCode = "";
                url = new URL(open.getUrl());
                long startTime = System.currentTimeMillis();
                URLConnection urlC = url.openConnection();
                long finishTime = System.currentTimeMillis() - startTime;
                if (finishTime <= timeout * 1000 && badElem.equalsIgnoreCase("")) {
                    allTime += finishTime;
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
                    String str;
                    while ((str = in.readLine()) != null) {
//                        htmlStrings.add(str);
                        htmlCode += str;
                    }
                    in.close();
                    writer.write("+ [" + openCommands.getList().get(index) + "] " + (double)finishTime / 1000 + "\n");
                    for (int k = index + 1; k < openCommands.getList().size(); k++) {
                        String oneCommand = openCommands.getList().get(k);
                        if (oneCommand.startsWith("checkPageTitle"))
                            printCheckPageTitle(writer, oneCommand, htmlCode);
                        else if (oneCommand.startsWith("checkPageContains"))
                            printCheckPageContains(writer, oneCommand, htmlCode);//htmlStrings);
                        else if (oneCommand.startsWith("checkLinkPresentByHref"))
                            printCheckLinkPresentByHref(writer, oneCommand, htmlCode);//htmlStrings);
                        else if (oneCommand.startsWith("checkLinkPresentByName"))
                            printCheckLinkPresentByName(writer, oneCommand, htmlCode);//htmlStrings);
                        else {
                            writer.write("! [" + oneCommand + "] 0.000\n");
                            fails++;
                        }
                    }
                }
                else if (!badElem.equalsIgnoreCase("")) {
                    writer.write("! [" + openCommands.getList().get(index) + "] 0.000\n");
                    fails++;
                    for (int k = index + 1; k < openCommands.getList().size(); k++) {
                        writer.write("! [" + openCommands.getList().get(k) + "] 0.000\n");
                        fails++;
                    }
                }
                else {
                    writer.write("! [" + openCommands.getList().get(index).trim() + "] 0.000\n");
                    fails++;
                    for (int k = index + 1; k < openCommands.getList().size(); k++) {
                        writer.write("! [" + openCommands.getList().get(k).trim() + "] 0.000\n");
                        fails++;
                    }
                }
            } catch (MalformedURLException e) {
                writer.write("! [" + openCommands.getList().get(0).trim() + "] 0.000\n");
                fails++;
            }
        }
        writer.write("Total tests: " + commands.size() + "\n");
        writer.write("Passed/Failed: " + (commands.size() - fails) + "/" + fails + "\n");
        writer.write("Total time: " + (double)allTime / 1000 + "\n");
        double time = roundResult((double) allTime / 1000 / commands.size());
        if (time == 0.0)
            writer.write("Average time: 0.000");
        else
            writer.write("Average time: " + time);
        writer.flush();
        writer.close();
    }
}