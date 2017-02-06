

import com.epam.framework.util.StatisticsCalculator;
import com.epam.framework.util.TextReader;

public class Runner {
    public static void main(String[] args) {
        TextReader.readFile(); //read instruction.txt
        StatisticsCalculator.createStatistics();
    }
}
