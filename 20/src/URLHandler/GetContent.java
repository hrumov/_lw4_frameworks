import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


/**
 */
public class GetContent {
    public static double totalTime;
    public static StringBuffer stringBuffer;



    public static boolean open(URL url, double time){
        stringBuffer = new StringBuffer(0);
        long begin = System.currentTimeMillis();
        double end;
        boolean isSuccessful = true;
        try {
            java.net.URLConnection conn = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String s;
            while((s=bufferedReader.readLine())!=null){
                stringBuffer.append(s);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Cannot reach "+e.getMessage());
            isSuccessful = false;
        }
        end = (double)(System.currentTimeMillis() - begin)/1000;
        if(end>time){
            isSuccessful = false;
            stringBuffer.delete(0,stringBuffer.capacity());
        }
        totalTime += end;
        if(isSuccessful)
            return true;
        else return false;

    }
    public static boolean checkPageContains(String s) {
        long begin = System.currentTimeMillis();
        if (stringBuffer!=null && s!=null)
            if (stringBuffer.indexOf(s) != -1)
            { totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
                return true;}

        totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
        return false;
    }
    public static boolean checkPageTitle(String s){
        long begin = System.currentTimeMillis();
        if ((s!=null)&&(stringBuffer.indexOf("<title>")!=-1))
            if (stringBuffer.substring(stringBuffer.indexOf("<title>")+7,stringBuffer.indexOf("</title>")).equals(s))
            { totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
                return true;}

        totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
        return false;
    }
    public static boolean checkLinkPresentByHref(String href){
        long begin = System.currentTimeMillis();
        if(href!=null)
            if(stringBuffer.indexOf("href=\""+href+"\"")!=-1){
                totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
                return true;
            }

        totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
        return false;
    }
    public static boolean checkLinkPresentByName(String name){
        long begin = System.currentTimeMillis();
        if(name!=null){
            int begin_index=0;
            while(stringBuffer.indexOf("<a",begin_index) != -1){
                int begin_href = stringBuffer.indexOf("<a",begin_index);
                int begin_name = stringBuffer.indexOf(">",begin_href)+1;
                int end_name = stringBuffer.indexOf("<", begin_name);
                //System.out.println(stringBuffer.substring(begin_name, end_name));
                if(stringBuffer.substring(begin_name, end_name).equals(name))
                {
                    totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
                    return true;
                }
                begin_index = end_name;
            }
        }
        totalTime+= (double)(System.currentTimeMillis() - begin)/1000;
        return false;
    }
    }

