/*
 * By Viraj H.
 * 4/19/2013
 * San Jose State University
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class LogWriter {
	
	private String genTime;
	private PrintWriter pw1;
	
	public LogWriter() throws IOException
	{
		if(new File("vLog.txt").exists())
		{
			pw1 = new PrintWriter(new BufferedWriter(new FileWriter("vLog.txt",true)));
			pw1.println("------------------------------------------------------------------------");
			genTime= new String(new Date().toString());
			pw1.println("Log file generated at "+genTime.toUpperCase());		
		}
		else
		{
			File file = new File("vLog.txt");
			pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			pw1.println("By Viraj H.\nApril 2013, SJSU.\n------------------------------------------------------------------------");
			genTime= new String(new Date().toString());
			pw1.println("Log file generated at "+genTime.toUpperCase());
		}
	}
	
	public void addToLog(String s) throws IOException
	{
		pw1.println(s);
	}
	
	public void close() throws IOException
	{
		pw1.println("End of log file generated at "+genTime.toUpperCase());
		pw1.println("------------------------------------------------------------------------");
		pw1.close();
	}
}
