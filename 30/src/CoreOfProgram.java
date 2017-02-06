
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class CoreOfProgram {
    public static void main(String[] args) {
        File myfile = new File("d:\\tempFile.txt");
        CoreOfProgram coreOfProgram = new CoreOfProgram();
        DOCExtractor docExtractor = new DOCExtractor();
        HTMLExtractor htmlExtractor = new HTMLExtractor();
        LogFile logFile = new LogFile();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(myfile));
            String inputLine;
            String resultFromDoc;
            String resultFromHTML;
            String HTMLText = null;
            inputLine = reader.readLine();
            if (inputLine.indexOf("open") != -1) {
                    resultFromDoc = docExtractor.extractLinkOpen(inputLine);
                    double time;
                    long timeStratOpen = System.nanoTime();
                    if (coreOfProgram.openUrl(resultFromDoc) != null ){  // Здесь имеется баг  -  при негативном тесте, если указать неверный url, выскачит исключение
                        long timeFinishOpen = System.nanoTime();            //java.net.UnknownHostException, НО в лог файле команда отразиться как успешная!
                        double timeResult =(double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        HTMLText = coreOfProgram.readFromUrl(resultFromDoc);
                        time = docExtractor.extractTime(inputLine);
                        if (timeResult<time){
                            logFile.writeLogFile("+ [open \"" + resultFromDoc + "\" " + "\"" + time + "\"] " +
                                    String.format("%.3f", timeResult));
                            logFile.totalTest++;
                            logFile.passed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        } else {
                            logFile.writeLogFile("! [open \"" + resultFromDoc + "\" " + "\"" + time + "\"] " +
                                    String.format("%.3f", timeResult));
                            logFile.totalTest++;
                            logFile.failed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        }
                    } else {
                        System.out.println("Введен не верный url");
                    }

                } else {
                System.out.println("Нет команды open");
                System.exit(0);
            }
            while ((inputLine = reader.readLine()) != null) {
                 if (inputLine.indexOf("checkLinkPresentByHref ") != -1) {
                    resultFromDoc = docExtractor.extractLink(inputLine);
                    resultFromHTML = htmlExtractor.extractLinkPresentByHref(HTMLText, resultFromDoc);
                    long timeStratOpen = System.nanoTime();
                    if (resultFromHTML != null && resultFromHTML.indexOf(resultFromDoc) !=-1) {
                        long timeFinishOpen = System.nanoTime();
                        logFile.writeLogFile("+ [checkLinkPresentByHref \"" + resultFromDoc + "\"] " +
                                String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                        logFile.totalTest++;
                        logFile.passed++;
                        logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                    } else {
                        long timeFinishOpen = System.nanoTime();
                        logFile.writeLogFile("! [checkLinkPresentByHref \"" + resultFromDoc + "\"] " +
                                String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                        logFile.totalTest++;
                        logFile.failed++;
                        logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                    }
                }
                else if (inputLine.indexOf("checkLinkPresentByName ") != -1) {
                        resultFromDoc = docExtractor.extractLink(inputLine);
                        resultFromHTML = htmlExtractor.extractLinkPresentByName(HTMLText, resultFromDoc);
                        long timeStratOpen = System.nanoTime();
                        if (resultFromHTML != null && resultFromHTML.indexOf(resultFromDoc) !=-1) {
                            long timeFinishOpen = System.nanoTime();
                            logFile.writeLogFile("+ [checkLinkPresentByName \"" + resultFromDoc + "\"] " +
                                    String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                            logFile.totalTest++;
                            logFile.passed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        } else {
                            long timeFinishOpen = System.nanoTime();
                            logFile.writeLogFile("! [checkLinkPresentByName \"" + resultFromDoc + "\"] " +
                                    String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                            logFile.totalTest++;
                            logFile.failed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        }
                    }
                 else if (inputLine.indexOf("checkPageTitle ") != -1) {
                        resultFromDoc = docExtractor.extractLink(inputLine);
                        resultFromHTML = htmlExtractor.extractPageTitle(HTMLText, resultFromDoc);
                        long timeStratOpen = System.nanoTime();
                        if (resultFromHTML != null && resultFromHTML.indexOf(resultFromDoc) !=-1) {
                            long timeFinishOpen = System.nanoTime();
                            logFile.writeLogFile("+ [checkPageTitle \"" + resultFromDoc + "\"] " +
                                    String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                            logFile.totalTest++;
                            logFile.passed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        } else {
                            long timeFinishOpen = System.nanoTime();
                            logFile.writeLogFile("! [checkPageTitle \"" + resultFromDoc + "\"] " +
                                    String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                            logFile.totalTest++;
                            logFile.failed++;
                            logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                        }
                    }
                 else if (inputLine.indexOf("checkPageContains ") != -1) {
                            resultFromDoc = docExtractor.extractLink(inputLine);
                            long timeStratOpen = System.nanoTime();
                            if (HTMLText.indexOf(resultFromDoc) != -1) {
                                long timeFinishOpen = System.nanoTime();
                                logFile.writeLogFile("+ [checkPageContains  \"" + resultFromDoc + "\"] " +
                                        String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                                logFile.totalTest++;
                                logFile.passed++;
                                logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                            } else {
                                long timeFinishOpen = System.nanoTime();
                                logFile.writeLogFile("! [checkPageContains  \"" + resultFromDoc + "\"] " +
                                        String.format("%.3f", (double) (timeFinishOpen - timeStratOpen) / 1000000000));
                                logFile.totalTest++;
                                logFile.failed++;
                                logFile.totalTime = logFile.totalTime + (double) (timeFinishOpen - timeStratOpen) / 1000000000;
                            }
                        }
                    }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logFile.writeResults(logFile.totalTest, logFile.passed, logFile.failed, logFile.totalTime);
        System.out.println("Лог-файл сформирован! По умолчанию D:/logFile.txt");

    }
    public String readFromUrl (String url) {
        StringBuilder sbHTML = new StringBuilder();
        try { 
            BufferedReader br = new BufferedReader(new InputStreamReader(openUrl(url).getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sbHTML.append(inputLine);
                sbHTML.append("\n");
            }
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String HTMLText = new String(sbHTML);
        return HTMLText;
    }

    public URLConnection openUrl(String urlText) {

        URLConnection conn = null;
        try {
            URL url = new URL(urlText);
            conn = url.openConnection();
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
            return conn;
    }
}
