
import by.epam.microframework.entity.Command;
import by.epam.microframework.exception.CommandReaderException;
import by.epam.microframework.exception.HTMLAnalyzerException;
import by.epam.microframework.exception.HTMLWriterException;

import java.net.MalformedURLException;
import java.util.List;

public class FrameworkRunner {

	private static final String OPEN = "open";
	private static final String CHECK_LINK_PRESENT_BY_HREF = "checkLinkPresentByHref";
	private static final String CHECK_LINK_PRESENT_BY_NAME = "checkLinkPresentByName";
	private static final String CHECK_PAGE_TITLE = "checkPageTitle";
	private static final String CHECK_PAGE_CONTAINS = "checkPageContains";
	private HTMLAnalyzer htmlAnalyzer = new HTMLAnalyzer();
	private ResultLogger logger;
	private CommandReader commandReader;
	private int passed;
	private long totalTime;

	public FrameworkRunner(String in, String out) {
		try {
			this.commandReader = new CommandReader(in);
			this.logger = new ResultLogger(out);
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		} catch (CommandReaderException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			List<Command> commands = commandReader.readCommands();
			for (Command command : commands) {
				switch (command.getName()) {
				case OPEN:
					open(command);
					break;
				case CHECK_LINK_PRESENT_BY_HREF:
					checkLinkPresentByHref(command);
					break;
				case CHECK_LINK_PRESENT_BY_NAME:
					checkLinkPresentByName(command);
					break;
				case CHECK_PAGE_TITLE:
					checkPageTitle(command);
					break;
				case CHECK_PAGE_CONTAINS:
					checkPageContains(command);
					break;
				}
			}
			logger.statistic(commands.size(), passed, totalTime);
			logger.close();
		} catch (CommandReaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void checkPageContains(Command command) {
		try {
			long totalTime = -System.currentTimeMillis();
			boolean result = htmlAnalyzer.checkPageContains(command.getParams().get(0));
			totalTime += System.currentTimeMillis();
			passed += result ? 1 : 0;
			this.totalTime += totalTime;
			logger.command(result, command, totalTime);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		}		
	}

	private void open(Command command) {
		try {
			long totalTime = -System.currentTimeMillis();
			boolean result = htmlAnalyzer.open(command.getParams().get(0),
					(long) (Double.parseDouble(command.getParams().get(1)) * 1000));
			totalTime += System.currentTimeMillis();
			passed += result ? 1 : 0;
			this.totalTime += totalTime;
			logger.command(result, command, totalTime);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (HTMLAnalyzerException e) {
			e.printStackTrace();
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		}
	}

	private void checkPageTitle(Command command) {
		try {
			long totalTime = -System.currentTimeMillis();
			boolean result = htmlAnalyzer.checkPageTitle(command.getParams().get(0));
			totalTime += System.currentTimeMillis();
			passed += result ? 1 : 0;
			this.totalTime += totalTime;
			logger.command(result, command, totalTime);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		}
	}

	private void checkLinkPresentByName(Command command) {
		try {
			long totalTime = -System.currentTimeMillis();
			boolean result = htmlAnalyzer.checkLinkPresentByName(command.getParams().get(0));
			totalTime += System.currentTimeMillis();
			passed += result ? 1 : 0;
			this.totalTime += totalTime;
			logger.command(result, command, totalTime);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		}
	}

	private void checkLinkPresentByHref(Command command) {
		try {
			long totalTime = -System.currentTimeMillis();
			boolean result = htmlAnalyzer.checkLinkPresentByHref(command.getParams().get(0));
			totalTime += System.currentTimeMillis();
			passed += result ? 1 : 0;
			this.totalTime += totalTime;
			logger.command(result, command, totalTime);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HTMLWriterException e) {
			e.printStackTrace();
		}
	}

}
