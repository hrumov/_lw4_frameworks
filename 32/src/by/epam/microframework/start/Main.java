
import by.epam.microframework.logic.FrameworkRunner;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please enter correct parameters: 1 - input file, 2 - output file");
            return;
        }
        try {
            Path path = Paths.get(args[0]);
            Paths.get(args[1]);
            if (!(Files.exists(path) && !Files.isDirectory(path))) {
                System.out.println("Input file does not exist");
                return;
            }
        } catch (InvalidPathException e) {
            System.out.println("Please enter file's path");
            return;
        }
        new FrameworkRunner(args[0], args[1]).run();
    }

}
