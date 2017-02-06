

import java.io.PrintWriter;
import java.net.*;

public class Instruction {
	private String name;
	private String text;
	private double timeout;
	static Page page=new Page();
	
	public Instruction(String i){
		String [] splI = i.split(" ",2);
		name = splI[0];
		text = splI[1];
		if (name.equals("open")){
			String [] buf = text.split(" ",2);
			text = buf[0];
			timeout = Double.parseDouble(buf[1]);
		}
	}
	
	public boolean execute(){
		switch (this.name){
		case "open":
			return this.open(this.text, this.timeout);
		case "checkLinkPresentByHref":
			return this.checkLinkPresentByHref(this.text);
		case "checkLinkPresentByName":
			return this.checkLinkPresentByName(this.text);
		case "checkPageTitle":
			return this.checkPageTitle(this.text);
		case "checkPageContains":
			return this.checkPageContains(this.text);
		default:
			return false;
			}	
	}
	
	public boolean open(String text, double timeout){
		try{
			URL url = new URL(text);
			URLConnection urlCon = url.openConnection();
			double time=System.nanoTime();
			urlCon.connect();
			time = (System.nanoTime()-time)/1e9;
			page=new Page(urlCon);
			if(time < timeout)
				return true;
			else 
				return false;
		}
		catch (Exception e){
			return false;
		}
	}
	
	public boolean checkLinkPresentByHref(String href){
		if (page.getPage().isEmpty()){
			return false;
		}
		else{
			for(String line: Instruction.page.getPage())	{
				boolean isContains = line.contains("href="+'"' + text + '"');
				if (isContains)
					return true;
				else 
					continue;
			}
			return false;
		}	
	}
	
	public boolean checkLinkPresentByName(String linkName){
		if (page.getPage().isEmpty()){
			return false;
		}
		else{
			for(String line: Instruction.page.getPage())	{
				boolean isContains = line.contains("title="+'"' + text + '"');
				if (isContains)
					return true;
				else
					continue;
			}
			return false;
		}
	}
	
	public boolean checkPageTitle(String text){
		if (page.getPage().isEmpty()){
			return false;
		}
		else{
			for(String line: Instruction.page.getPage())	{
				boolean isContains = line.contains("<title>" + text + "</title>");
				if (isContains)
					return true;
				else 
					continue;
			}
			return false;
		}
	}
	
	public boolean checkPageContains(String text){
		if (page.getPage().isEmpty()){
			return false;
		}
		else{
			for(String line: Instruction.page.getPage())	{
				boolean isContains = line.contains(text);
				if (isContains)
					return true;
				else
					continue;
			}
			return false;
		}
	}
	
	public void writeToLog(PrintWriter out, boolean result, String time){	 
		if (result)
			if (this.name.equals("open"))
				out.println("+ [" + this.name + " " + this.text + " " + this.timeout + "] " + time);
			else
				out.println("+ [" + this.name + " " + this.text + "] " + time); 
		else
			if (this.name.equals("open"))
				out.println("! [" + this.name + " " + this.text + " " + this.timeout + "] " + time);
			else
				out.println("! [" + this.name + " " + this.text + "] " + time);
	}
}
