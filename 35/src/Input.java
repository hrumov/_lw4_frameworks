
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Input {
    private int totaltests = 0;
    private static final Logger logger = Logger.getLogger(Input.class);

    public static void main(String[] args) {
        Input input = new Input();
        input.input();
    }

    /*В данном методе построчно читается скрипт и строка передается на обработку в метод executeLine.
    Кроме этого, здесь происходит логирование общей информации о ходе выполнения программы.*/
    private void input() {
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader("script.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                executeLine(line);
                logger.info("[" + line + "]");
                totaltests++;
            }
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }
        long timeSpent = (System.currentTimeMillis() - startTime);
        long avgTime = timeSpent / totaltests;
        logger.info("Total tests: " + totaltests);
        logger.info("Passed/Failed: " + Command.countPassed + "/" + Command.countFailed);
        logger.info("Total time: " + timeSpent + " ms");
        logger.info("Average time: " + avgTime + " ms");

    }

    /*Строка разбивается на команду и параметры. "Командная" часть строки передается в метод createCommand*/
    public void executeLine(String line) throws IOException {
        String commandLine = getCommand(line);
        String[] params = getParams(line);
        createCommand(commandLine, params);

    }

    /*Извлекает команду из строки с помощью регулярного выражения и возвращает ее*/
    private String getCommand(String line) {
        String regex = "[\\^\\s*]*[^\"][a-zA-Z]+[^\"]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /*Извлекает параметры из строки с помощью регулярного выражения и возвращает их*/
    private String[] getParams(String line) {
        String regex = "[\\s*]*[\"].+?[\"]";
        ArrayList<String> params = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String tmp = matcher.group();
            tmp = tmp.subSequence(tmp.indexOf("\"") + 1, tmp.lastIndexOf("\"")).
                    toString().trim();
            params.add(tmp);
        }
        String[] str = params.toArray(new String[params.size()]);
        return str;
    }

    /*Сравнивает  значение "командной" части строки с названиями комманд,при совпадении передает в одноименный метод параметры*/
    private void createCommand(String commandString, String[] params) {
        commandString = commandString.toLowerCase().trim();
        switch (commandString) {
            case "open":
                Command.open(params);
                break;
            case "checklinkpresentbyhref":
                Command.checkLinkPresentByHref(params);
                break;
            case "checklinkpresentbyname":
                Command.checkLinkPresentByName(params);
                break;
            case "checkpagetitle":
                Command.checkPageTitle(params);
                break;
            case "checkpagecontains":
                Command.checkPageContains(params);
                break;
        }
    }
}
