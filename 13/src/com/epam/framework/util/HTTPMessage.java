
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HTTPMessage {

    private static URL url;

    public static void responseMessage(String siteURL) {
        try {
            url = new URL(siteURL);
            openURL();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public static void openURL() {
        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream(), "windows-1251"));
            String httpMess = reader.readLine();
            while (httpMess != null) {
                FileUpdater.appendText(httpMess); //add readLine to message.txt
                httpMess = reader.readLine();
            }
            reader.close();
        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}