/**
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public abstract class CommandProcessor {
    private String statusMessage;
    private Object result;
    private long time;

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    public final int UNKNOWN_COMMAND=1;
    public final int ILLEGAL_ARGUMENT_EXCEPTION=2;
    public final int INVOCATION_TARGET_EXCEPTION=4;
    public final int ILLEGAL_ACCESS_EXCEPTION=8;
    public final int SUCCESSFUL_EXECUTION =16;
    public final int ERROR=32;
    public final int USER_BIT1=0x00000040;
    public final int USER_BIT2=0x00000080;
    public final int USER_BIT3=0x00000100;
    public final int USER_BIT4=0x00000200;
    public final int USER_BIT5=0x00000400;
    public final int USER_BIT6=0x00000800;
    public final int USER_BIT7=0x00001000;
    public final int USER_BIT8=0x00002000;
    public final int USER_BIT9=0x00004000;
    public final int USER_BIT10=0x00008000;
    public final int USER_BIT11=0x00010000;
    public final int USER_BIT12=0x00020000;
    public final int USER_BIT13=0x00040000;
    public final int USER_BIT14=0x00080000;
    public final int USER_BIT15=0x00100000;
    public final int USER_BIT16=0x00200000;//User bits

    public Object doCommand(String command,String[] args) {
        this.status=0;
        time=0;
        this.setStatusMessage("");
        Class c = this.getClass();
        Method[] allMethods = c.getDeclaredMethods();
        for (Method m : allMethods) {
            if (command.equals(m.getName())&&(!command.equals("doCommand"))){

                try {
                    m.setAccessible(true);
                    m.isAccessible();
                    long start = System.currentTimeMillis();
                    if ((args!=null)&&(args.length!=0)) {
                        if (m.getReturnType().getName().equals("void")) {
                            m.invoke(this, new Object[]{args});
                        }else {
                            this.setResult(m.invoke(this, new Object[]{args}));
                        }
                    }else{
                        if (m.getReturnType().getName().equals("void")) {
                            m.invoke(this);
                        }else {
                            this.setResult(m.invoke(this));
                        }
                    }
                    this.status=this.status| SUCCESSFUL_EXECUTION;
                    long end = System.currentTimeMillis();
                    this.setTime(end-start);
                    return result;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return result;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return result;
                }
                catch (IllegalArgumentException e) {
                    this.setStatus(this.getStatus()|this.ILLEGAL_ARGUMENT_EXCEPTION);
                    this.setStatusMessage("Wrong implementation of method in CommandProcessor's child." +
                            "Can only execute methods with String[] argument.Sorry");
                    return result;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return result;
                }
            }

        }
        this.status=this.status|this.UNKNOWN_COMMAND;
        return result;
    }

    private void setTime(long time) {
        this.time=time;
    }

    public int getStatus() {
        return status;
    }

    public long getTime() {
        return time;
    }

    public String help(){
        String message="";
        Parameter[] params = new Parameter[0];
        Class c = this.getClass();
        Method[] allMethods = c.getDeclaredMethods();
        for (Method m : allMethods) {
                params=m.getParameters();
                message+=m.getName()+"(";
            message+= Arrays.toString(m.getParameters())+")\n";
        }

        return message;
    }
    //---------------setters and getters
    public Object getResult() {
        return result;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
