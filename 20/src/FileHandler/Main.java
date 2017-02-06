
import java.io.File;


public class Main extends RunFile{
    public static void main(String[] args) throws Exception {
        if(args.length==1)
             runFile(new File(args[0]), new File("out.txt"));
        if(args.length==2)
            runFile(new File(args[0]), new File(args[1]));
    }
}
