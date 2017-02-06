
import java.io.IOException;
import epam.training.services.CheckInputDataService;
import epam.training.statistic.StatisticWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		
		CheckInputDataService check = new CheckInputDataService();
		check.checkInputData();
		StatisticWriter st = new StatisticWriter();
		st.printStatistic();
	}
}
