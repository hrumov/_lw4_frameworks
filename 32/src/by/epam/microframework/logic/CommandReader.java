
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.epam.microframework.entity.Command;
import by.epam.microframework.exception.CommandReaderException;
import by.epam.microframework.exception.MalformedCommandFileException;

public class CommandReader {
	private BufferedReader reader;

	public CommandReader(String path) throws CommandReaderException {
		try {
			this.reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw new CommandReaderException(e);
		}
	}

	public List<Command> readCommands() throws CommandReaderException {
		ArrayList<Command> commands = new ArrayList<Command>();
		try {
			String string = "";
			while ((string = reader.readLine()) != null) {
				String[] split = string.split("\\s+\"");
				if (split.length < 1) {
					throw new MalformedCommandFileException("no command name");
				}
				Command command = new Command();
				command.setName(split[0]);
				for (int i = 1; i < split.length; i++) {
					command.getParams().add(split[i].replaceAll("\"",""));
				}
				commands.add(command);
			}
			reader.close();
			} catch (IOException e) {
			throw new CommandReaderException(e);
		}
		return commands;
	}

}
