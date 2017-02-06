
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchEngine {

    public static boolean flag;

    public static void readURLFile(StringBuilder checkInput){
        String urlLine;
        try (BufferedReader br = new BufferedReader(new FileReader(FileUpdater.filePath)))
        {
            while ((urlLine = br.readLine()) != null) {
                if (urlLine.contains(checkInput)) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkPageTitle(String text) {
        String open = "<title>";
        String close = "</title>";
        StringBuilder sb = new StringBuilder();
        sb.append(open).append(text).append(close);
        readURLFile(sb);
    }

    public static void checkPageContains(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        readURLFile(sb);
    }

    public static void checkLinkPresentByHref(String text) {
        String open = "href=\"";
        StringBuilder sb = new StringBuilder();
        sb.append(open).append(text);
        readURLFile(sb);
    }

    public static void checkLinkPresentByName(String text) {
        String close = "</a>";
        StringBuilder sb = new StringBuilder();
        sb.append(text).append(close);
        readURLFile(sb);
    }
}
