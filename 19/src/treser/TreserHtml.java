import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;





public class TreserHtml extends OpenURL {

	public TreserHtml(String url, int timeout) {
		super(url, timeout);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		final String INPUT_FILE = "In.txt";
		final String OUTPUT_FILE = "Out.txt";
		BufferedReader input = new BufferedReader(new FileReader(INPUT_FILE));
		PrintStream printStream = new PrintStream(OUTPUT_FILE);
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();
		Double totalTime = 0.0;
		int passed = 0;
		int failed = 0;
		while((line = input.readLine())!= null)
			{
			
			String arrStr[]=line.split(" ");
			try{
				switch(arrStr[0])
				{
					case "open" :
						try{
						long timeout= System.currentTimeMillis();
						OpenURL ur = new OpenURL(arrStr[1],(Integer.parseInt(arrStr[2]))*1000);
						printStream.println(ur.open() + " " + "[" + line + "]" + " " + ((double)(System.currentTimeMillis() - timeout))/1000);
						totalTime+=(double)(System.currentTimeMillis() - timeout)/1000;
							if(ur.open().equals("+")){
								passed++;
							}else
							{
								failed++;
							}
						break;
						}
						catch(ArrayIndexOutOfBoundsException e){
							printStream.println("Check entered parameters");
						}
						
					case "checkPageTitle" :
						long timeout= System.currentTimeMillis();
						printStream.println(checkPageTitle(arrStr[1]) + " " + "[" + line + "]" + " " + ((double)(System.currentTimeMillis() - timeout))/1000);
						totalTime+=(double)(System.currentTimeMillis() - timeout)/1000;
							if(checkPageTitle(arrStr[1]).equals("+")){
							passed++;
							}else
								{
									failed++;
								}
						break;
						
						
					case "checkLinkPresentByHref" :
						long timeout1= System.currentTimeMillis();
						printStream.println(checkLinkPresentByHref(arrStr[1]) + " " + "[" + line + "]" + " " + ((double)(System.currentTimeMillis() - timeout1))/1000);
						totalTime+=(double)(System.currentTimeMillis() - timeout1)/1000;
							if(checkPageTitle(arrStr[1]).equals("+")){
								passed++;
							}else
								{
									failed++;
								}
						break;
						
						
					case "checkLinkPresentByName" :
						long timeout11= System.currentTimeMillis();
						printStream.println(checkLinkPresentByName(arrStr[1]) + " " + "[" + line + "]" + " " + ((double)(System.currentTimeMillis() - timeout11))/1000);
						totalTime+=(double)(System.currentTimeMillis() - timeout11)/1000;
							if(checkLinkPresentByName(arrStr[1]).equals("+")){
								passed++;
							}else
								{
									failed++;
								}
						break;
						
						
					case "checkPageContains" :
						long timeout111= System.currentTimeMillis();
						printStream.println(checkPageContains(arrStr[1]) + " " + "[" + line + "]" + " " + ((double)(System.currentTimeMillis() - timeout111))/1000);
						totalTime+=(double)(System.currentTimeMillis() - timeout111)/1000;
							if(checkPageContains(arrStr[1]).equals("+")){
								passed++;
							}else
								{
									failed++;
								}
						break;
				}
			}
			catch (IllegalArgumentException e){	
				printStream.println("Please, first use 'open' command ");
				break;
			}
			
			
		lines.add(line);
		
		
			}
		
		
		
		printStream.println("Total tests: " + (passed+failed));
		printStream.println("Passed/Failed: " + passed + " / " + failed);
		printStream.println("Total time: " + Math.rint(1000.0 * (totalTime)) / 1000.0);
		printStream.println("Average time: " + Math.rint(1000.0 * (totalTime/(passed+failed))) / 1000.0);
		
		System.out.println("See Out.txt for results");
		
	}
	
	
	
	 
}

