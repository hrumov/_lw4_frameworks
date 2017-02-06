
public class StatisticsCalculator {
    public static double totalTime;
    public static int totalTests;
    public static int numPassedTests;
    public static int numFailedTests;
    public static double averageTime;

    public static void createStatistics() {
        StringBuilder sb = new StringBuilder();
        averageTime = totalTime / totalTests;
        sb.append("Total tests:").append(totalTests).append("\n").append("Passed/Failed: ").append(numPassedTests)
                .append("/").append(numFailedTests).append("\n").append("Total time: ").append(totalTime)
                .append("\n").append("Average time: ").append(averageTime).append("\n\n");
        LogCreator.appendText(sb);
    }
}

