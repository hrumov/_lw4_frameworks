import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 */
public class commandsImplementationModule extends CommandProcessor  {
    public final String USAGE="open(String URL,int timeout)\ncheckLinkPresentByHref(String href)\ncheckLinkPresentByName(String name)\n" +
            "checkPageTitle(String title)\ncheckPageContains(String str)";
    public String page;
    public final int TIME_IS_UP=USER_BIT1;
    public final int IO_EXCEPTION=USER_BIT2;
    public final int WRONG_ARGUMENTS=USER_BIT3;
    public final int RESULT_IS_TRUE=USER_BIT4;
    public final String WARNING_PAGE_IS_NOT_OPENED="          Warning: result may be not accurate due to open command problem";
    private boolean isOpened=false;
    private BufferedReader pageReader;

    public boolean open(String[] args) {
        long time;
        long startTime= 0;
        String str="";
        page=null;
        URL url;

        startTime=System.currentTimeMillis();
        if(!isArgsNumberOk(args,2)) return false;
        try {
            time = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            this.setStatus(this.getStatus()|(ERROR));
            this.setStatusMessage("Time should be integer");
            return isOpened=false;
        }//Retrieving timeout
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            this.setStatus(this.getStatus() | (ERROR));
            this.setStatusMessage("MalformedURLException");
            return isOpened = false;
        }
        try {
            pageReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        }catch (UnknownHostException e){
            this.setStatus(this.getStatus()|(ERROR));
            this.setStatusMessage("Unknown Host Exception");
            return isOpened=false;
        }
         catch (IOException e) {
            this.setStatus(this.getStatus()|(ERROR));
            this.setStatusMessage("IO Exception");
            return isOpened=false;
        }

        while((System.currentTimeMillis()-startTime)<=time) {
            try {
                str = pageReader.readLine();
            } catch (IOException e) {
                this.setStatus(this.getStatus()|ERROR);
                this.setStatusMessage("IO exception while reading URL");
                return isOpened=false;
            }
            if (str==null) {
                break;
            }
            page+=str;
        }
        if (!(isOpened=(System.currentTimeMillis()-startTime)<time)) {
            this.setStatus(this.getStatus()|ERROR);
            this.setStatusMessage("Reading was interrupted by timeout, so page may be not full");
        }
        try {
            pageReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isOpened) {{this.setStatus(this.getStatus()|RESULT_IS_TRUE);}}
        return isOpened;
    }

    public boolean checkLinkPresentByHref(String[] args) {
        String result;
        if(!isArgsNumberOk(args,1)) {return false;}
        if (page==null) {
            error("Nothing to process");
            return false;
        }
        if (page.length()==0){
            error("Nothing to process");
            return false;
        }
        if (!isOpened) {
            this.setStatusMessage(WARNING_PAGE_IS_NOT_OPENED);
        }
        Finder aTag=new Finder(page,"<a",">");
        while(aTag.hasNext()){
            Finder aTagTrimmed= new Finder(aTag.findSubStringBetween(),"href",">");
            if (aTagTrimmed.hasNext()) {
                Finder href = new Finder(aTagTrimmed.findSubStringBetween(),"\"","\"");
                if (href.hasNext()) {
                    if (href.findSubStringBetweenWithoutTags().equals(args[0])) {
                        this.setStatus(this.getStatus()|RESULT_IS_TRUE);return true;
                    }
                }

            }

        }
        return false;
    }

    public boolean checkLinkPresentByName(String[] args) {
        String name;
        String CLOSING_TAG="</a>";
        if (!isArgsNumberOk(args,1)) return false;
        if (page==null) {
            error("Nothing to process");
            return false;
        }
        if (page.length()==0){
            error("Nothing to process");
            return false;
        }
        if (!isOpened) {
            this.setStatusMessage(WARNING_PAGE_IS_NOT_OPENED);
        }
        Finder f=new Finder(page,"<a",CLOSING_TAG);
        while(f.hasNext()){
            Finder nameFinder=new Finder(f.findSubStringBetween(),">",CLOSING_TAG);
            if (nameFinder.hasNext()) {
                if (nameFinder.findSubStringBetweenWithoutTags().equals(args[0])) {
                    this.setStatus(this.getStatus()|RESULT_IS_TRUE);
                    return true;
                }
            }
            }
        return false;
    }


    public boolean checkPageTitle(String[] args) {
        String str="";
        if (!isArgsNumberOk(args,1)) return false;
        if (page==null) {
            error("Nothing to process");
            return false;
        }
        if (page.length()==0){
            error("Nothing to process");
            return false;
        }
        if (!isOpened) {
            this.setStatusMessage(WARNING_PAGE_IS_NOT_OPENED);
        }
        Finder title= new Finder(page,"<title>","</title>");
        if (title.hasNext()){
            str=title.findSubStringBetweenWithoutTags();
        }
        if (str.equals(args[0])) {
            this.setStatus(this.getStatus()|RESULT_IS_TRUE);
            return true;
        }else {return false;}
    }

    public boolean checkPageContains(String[] args) {
        boolean result;
        if (!isArgsNumberOk(args,1)) return false;
        if (page==null) {
            error("Nothing to process");
            return false;
        }
        if (page.length()==0){
            error("Nothing to process");
            return false;
        }
        if (!isOpened) {
            this.setStatusMessage(WARNING_PAGE_IS_NOT_OPENED);
        }
        result = page.contains(args[0]);
        if (result) {this.setStatus(this.getStatus()|RESULT_IS_TRUE);return true;}
        return result;
    }

    private boolean isArgsNumberOk(String[] args, int count) {
        if (args.length>count){
            this.setStatus(this.getStatus()|this.ERROR);
            this.setStatusMessage("Too many params!");
            return false;
        }
        if (args.length<count){
            this.setStatus(this.getStatus()|this.ERROR);
            this.setStatusMessage("Too few params!");
            return false;
        }
        return true;
    }

    private void error(String message) {
        this.setStatus(this.getStatus()|ERROR);
        this.setStatusMessage(message);
    }

    @Override
    public String help(){
        this.setStatusMessage("\n--------Syntax:\n"+USAGE+"\n---------------------End of help.");
        this.setStatus(this.getStatus()|RESULT_IS_TRUE);
        return "\n"+USAGE;
    }

}