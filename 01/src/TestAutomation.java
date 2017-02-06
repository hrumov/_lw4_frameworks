/**
 */
public class TestAutomation {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("This framework allow to test web-page. You need to transfer 2 arguments:"
                    + System.lineSeparator() + "1. Path to instructions file."
                    + System.lineSeparator() + "2. Path to log file.");
            return;
        }
        instructionsRunner(args[0], args[1]);
    }

    private static void instructionsRunner(String instructionsPath, String logPath) {
        InstructionsParser.parseInstructionsFromFile(instructionsPath);
        Logger logger = new Logger(logPath);

        int passedTests = 0;
        long totalTime = 0;

        Boolean executionResult;

        for (String instruction: InstructionsParser.getInstructionsList()) {

            long startTime = System.currentTimeMillis();
            executionResult = instructionExecutor(instruction); //выполнение инструкции
            long finishTime = System.currentTimeMillis();
            long executionTime = finishTime - startTime;

            String loggerMessage;

            if (executionResult != null) { //проверка на то, что инструкция была успешно распознана
                loggerMessage = (executionResult ? "+" : "!") + " [" + instruction + "] " + executionTime / 1000.0;
            } else {
                loggerMessage = "! No such instruction" + " [" + instruction + "] ";
            }

            logger.writeLog(loggerMessage + System.lineSeparator());

            totalTime += executionTime;
            if (executionResult != null && executionResult) {
                ++passedTests;
            }
        }

        logger.writeLog(summary(passedTests, InstructionsParser.getInstructionsList().size(), totalTime));
    }

    private static Boolean instructionExecutor(String instruction) {
        Boolean executionResult = null;
        if (!instruction.contains(" ")) { //возвращаем null если в инструкции не указан параметр для запуска
            return executionResult;
        }

        String test = instruction.substring(0, instruction.indexOf(' '));
        String arg = instruction.substring(instruction.indexOf(' ') + 2, instruction.length() - 1);

        switch (test) {
            case "open":
                String url = instruction.substring(instruction.indexOf(' ') + 2, instruction.lastIndexOf(' ') - 1);
                String timeout = instruction.substring(instruction.lastIndexOf(' ') + 2, instruction.length() - 1);
                executionResult = Tests.open(url, Integer.parseInt(timeout));
                break;
            case "checkLinkPresentByHref":
                executionResult = Tests.checkLinkPresentByHref(arg);
                break;
            case "checkLinkPresentByName":
                executionResult = Tests.checkLinkPresentByName(arg);
                break;
            case "checkPageTitle":
                executionResult = Tests.checkPageTitle(arg);
                break;
            case "checkPageContains":
                executionResult = Tests.checkPageContains(arg);
                break;
            default:
                break;
        }

        return executionResult;
    }

    private static String summary(int passedTests, int totalTests, long totalTimeInMs) {
        double averageTimeInMs = (totalTimeInMs / totalTests) / 1000.0;

        String summary;
        summary = "Total tests: " + InstructionsParser.getInstructionsList().size() + System.lineSeparator();
        summary += "Passed/Failed: " + passedTests + "/" + (totalTests - passedTests) + System.lineSeparator();
        summary += "Total time: " + (totalTimeInMs / 1000.0) + System.lineSeparator();
        summary += "Average time: " + averageTimeInMs + System.lineSeparator() + System.lineSeparator();

        return summary;
    }

}