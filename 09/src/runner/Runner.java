
import java.io.File;

import framework.Framework;

public class Runner 
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			System.out.println("Please, enter 1 parametr - path to the TXT file with test script!");
			System.out.println("Input /h as parametr to get help about script file format");
		}
		else if(args[0].equals("/h"))
		{
			System.out.println("-------------------------------Test script file format--------------------------------");
			System.out.println("open \"http://www.google.com\" \"3\"");
			System.out.println("checkPageTitle \"Google Search Page\"");
			System.out.println("checkPageContains \"The best search engine\"\n");
			System.out.println("--------------------Avaliable commands to use int test script file--------------------");
			System.out.println("open(url, timeout)");
			System.out.println("checkLinkPresentByHref(href)");
			System.out.println("checkLinkPresentByName(link name)");
			System.out.println("checkPageTitle(text)");
			System.out.println("checkPageContains(text)\n");
			System.out.println("--------------------------------------------------------------------------------------");
		}
		else
		{
			File f = new File(args[0]);
			if (!f.exists())
			{
				System.out.println("Your path is not exist!!!");
			}
			else if(!f.isFile())
			{
				System.out.println("Your path is not file!!!");
			}
			else if(!getFileExtension(f).equals("txt"))
			{
				System.out.println("Test script file must to have TXT extension!!!");
			}
			else
			{
				try
				{
					Framework fr = new Framework(f);
					System.out.println("Ends!");
				}
				catch (Exception ex)
				{
					System.out.println("ERROR!!!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	//Method to get file extension
	private static String getFileExtension(File file)
	{
        	String fileName = file.getName();
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        {
	        	String s = fileName.substring(fileName.lastIndexOf(".")+1);
	        	s.toLowerCase();
	        	return s;
	        }
	        else return "";
    	}
}
