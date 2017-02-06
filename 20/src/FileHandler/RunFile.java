
import URLHandler.GetContent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 */
public class RunFile extends GetContent{
    public static int successful;
    public static int failed;
    public static boolean isOpen = false;
    public static BufferedReader br;
    public static PrintWriter out;
    public static void runFile(File input, File output) throws IOException {
        if(!input.exists())
        {
            System.out.println("Input file doesn't exist");
            return;
        }
        if(!output.exists())
            System.out.println("Warning: Output file doesn't exist. This file will be created");
        if(output.exists()&&output.length()!=0)
            System.out.println("Warning: Output file is not empty!");

             br = new BufferedReader(new FileReader(input));
            out = new PrintWriter(new FileOutputStream(output));

        String line;
        while((line=br.readLine())!=null){
            double currentTotalTime = totalTime;
            String message = "";
            String [] params = line.split(" ");
            if(params.length>0)
            switch (params[0]) {
                case "open":
                    if(params.length<3)
                        break;
                    if(isOpenParamsValid(params)){
                        URL url = new URL(params[1].substring(1,params[1].length()-1));
                        double timeout = Double.parseDouble(params[2].substring(1,params[2].length()-1));
                    if(open(url,timeout)) {
                        isOpen = true;
                        successful++;
                        message +="+ ";}
                    }
                    if(message.length() == 0)
                    {
                        message += "! ";
                        failed++;
                    }
                    message+="["+line+"] "+ String.format("%.3f", (totalTime - currentTotalTime));
                    out.println(message);

                    break;
                case "checkPageContains":
                {
                    if(params.length>=2) {
                        params = line.split(" ", 2);
                        if (isOpen)
                            if (checkPageContains(params[1].substring(1, params[1].length() - 1))) {
                                successful++;
                                message += "+ ";
                            }
                    }
                    if(message.length() == 0)
                    {
                        message += "! ";
                        failed++;
                    }
                    message+="["+line+"] "+ String.format("%.3f", (totalTime - currentTotalTime));
                    out.println(message);
                }break;
                case "checkPageTitle":
                    if(params.length>=2) {
                        params = line.split(" ",2);
                        if (isOpen)
                            if (checkPageTitle(params[1].substring(1, params[1].length() - 1))) {
                                successful++;
                                message += "+ ";
                            }
                    }
                    if(message.length() == 0)
                    {
                        message += "! ";
                        failed++;
                    }
                    message+="["+line+"] "+ String.format("%.3f", (totalTime - currentTotalTime));
                    out.println(message);

                break;
                case "checkLinkPresentByHref":
                    if(params.length>=2)
                    {
                    params = line.split(" ", 2);
                    String href = params[1].substring(1, params[1].length()-1).trim();
                    if(isOpen)
                        if (checkLinkPresentByHref(href))
                        {
                            successful++;
                            message+="+ ";
                        }
                    }
                    if(message.length() == 0)
                    {
                        message += "! ";
                        failed++;
                    }
                    message+="["+line+"] "+ String.format("%.3f", (totalTime - currentTotalTime));
                    out.println(message);

                    break;
                case "checkLinkPresentByName":
                    if(params.length>=2)
                    {
                    params = line.split(" ", 2);
                    String name = params[1].substring(1, params[1].length()-1).trim();
                        if(isOpen)
                            if (checkLinkPresentByName(name))
                             {
                            successful++;
                            message+="+ ";
                             }
                    }
                    if(message.length() == 0)
                    {
                        message += "! ";
                        failed++;
                    }
                    message+="["+line+"] "+ String.format("%.3f", (totalTime - currentTotalTime));
                    out.println(message);

                    break;

            }

        }
        out.println("Total tests: " + (failed + successful));
        out.println("Passed/Failed: "+successful+"/"+failed);
        out.println("Total time: "+String.format("%.3f", totalTime));
        out.println("Average time: "+String.format("%.3f",totalTime/(failed + successful)));
        br.close();
        out.close();
    }
    private static boolean isOpenParamsValid(String [] params){
        if(params.length<3)
            return false;
        else{

                try {
                     URL url = new URL(params[1].substring(1,params[1].length()-1));
                } catch (MalformedURLException e) {
                    System.out.println(e.getClass()+" "+e.getLocalizedMessage());
                    return false;
                }
                try{
                     double timeout = Double.parseDouble(params[2].substring(1,params[2].length()-1));
                } catch (NumberFormatException e){
                    System.out.println(e.getClass()+" "+e.getLocalizedMessage());
                    return false;
                }

        }
        return true;
    }

}
