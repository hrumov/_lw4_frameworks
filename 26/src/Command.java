import java.util.ArrayList;

/**
 */
public class Command {
    private ArrayList<String> arguments =new ArrayList<>();
    String command="";
    String str;
    private String commandSeparators=" ";
    private String argumentsSeparators="\"";
    public Command(String input){
        str=input;
        setCommand(str);
        setArguments(str);
    }

    private void setCommand(String input) {
        boolean endFlag=false;
        for (int i = 0; i < input.length(); i++) {
            if (commandSeparators.indexOf(input.charAt(i)) == -1) {
                endFlag = true;
                command = command + (String.valueOf(input.charAt(i)));
                continue;
            }
            if (endFlag) {
                return;
            }else {
                command = null;
            }
        }
    }

    private void setArguments(String input) {
        boolean startFlag=false;
        boolean insideFlag=false;
        String result="";
        for (int i = 0; i < input.length(); i++) {
            if (argumentsSeparators.indexOf(input.charAt(i))!=-1) {
                startFlag=!startFlag;
                if (insideFlag) {
                    arguments.add(result);
                    insideFlag=false;
                    result="";
                }
            }
            if (startFlag){
                if (insideFlag) {result=result+(String.valueOf(input.charAt(i)));}
                insideFlag=true;
            }

        }
    }

    public String getCommand(){
        return command;
    }

    public String[] getArguments(){
        String[] result= new String[arguments.size()];
        for (int i = 0; i < arguments.size(); i++) {
            result[i]= arguments.get(i);
        }
        return result;
    }

}
