/*
 * By Viraj H.
 * 4/19/2013
 * San Jose State University
 * 
 * Credits to Ai-Phuong Le for the Ping functionality.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import com.vmware.vim25.AboutInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostListSummaryQuickStats;
import com.vmware.vim25.InvalidLogin;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.ServiceContent;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class Monitor 
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Welcome to the vMonitor.");
		while(true)
		{	
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			System.out.println("Enter username to scan by...");
			
			String user = s.nextLine();
			if(user.equalsIgnoreCase("exit"))
				System.exit(0);
			
			System.out.println("Enter password to scan by...");
			
			String pass = s.nextLine();
			if(pass.equalsIgnoreCase("exit"))
				System.exit(0);
			
			@SuppressWarnings("unused")
			String str=run(user,pass);
		}
	}
	
	//------------------------------------------------------------------------------------------------
	static boolean ping(String s) throws IOException
		{
		ArrayList<String> command = new ArrayList<String>();
		boolean flag = true;
		command.add("ping");
		command.add(s);		
		
		ProcessBuilder pb = new ProcessBuilder(command);
		Process process;
		
		process=pb.start();
		BufferedReader br_in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String str;
		
		while((str=br_in.readLine())!=null)
		{
			if(str.endsWith("unreachable.") || str.endsWith("Request timed out."))
			{
				flag=false;
				break;
			}
		}
		return flag;
		}

	//------------------------------------------------------------------------------------------------
	private static boolean getHostStats(String ip, String user, String pass, int count) throws MalformedURLException, InvalidProperty, RuntimeFault
		{
			if(count>=2)
				return false;
			URL url = new URL("https://"+ip+"/sdk");
			try {
			ServiceInstance si = new ServiceInstance(url, user, pass, true);			
			Folder rootFolder = si.getRootFolder();
			InventoryNavigator iNav= new InventoryNavigator(rootFolder);
			
			ManagedEntity[] mes = iNav.searchManagedEntities("HostSystem");	
			HostSystem[] hs = new HostSystem[mes.length];
					
			for(int i=0;i<mes.length;i++)
			hs[i]=(HostSystem) mes[i];
					
			System.out.println("--Host List--");
			for(int i=0;i<hs.length;i++)
			{
				System.out.print("Host: "+hs[i].getName()+"\t");
				HostListSummary x= hs[i].getSummary();
				HostListSummaryQuickStats y =x.getQuickStats();
				System.out.print("\tCPU Usage: "+y.getOverallCpuUsage()+" MHz");
				System.out.println("\tRAM Usage: "+y.getOverallMemoryUsage()+" MB");			
			}
			System.out.println("-----------");		
			ManagedEntity[] mes2 = iNav.searchManagedEntities("VirtualMachine");
			VirtualMachine[] vm = new VirtualMachine[mes2.length];
			for(int i=0;i<mes2.length;i++)
				vm[i]=(VirtualMachine) mes2[i];
					
			System.out.println("--VM List--");
			for(int i=0;i<vm.length;i++)
			{	
				System.out.print(vm[i].getName()+"\t");
				VirtualMachineConfigInfo x = vm[i].getConfig();
				String z = null;
				try
				{
					z = x.getGuestFullName();
				}
				catch(NullPointerException e)
				{
					z="No Guest OS / No VMware Tools Installed.";
				}
				System.out.println("\t"+z);
			}
					
			System.out.println("-----------");
			return true;
			}
			catch(ConnectException e)
			{
				return false;
			} 
			catch(InvalidLogin e)
			{
				count++;
				boolean newFlag = getHostStats(ip,"root",pass,count);
				return newFlag;
			}
			catch (RemoteException e)
			{
				return false;
			}

		}

	//------------------------------------------------------------------------------------------------
	private static boolean compare (int[] a1, int []a2)
	{
		if(a1[3]==a2[3] && a1[2]==a2[2] && a1[1]==a2[1] && a1[0]==a2[0])
			return true;
		else
			return false;
	}

	//------------------------------------------------------------------------------------------------
	private static void shutdown(LogWriter log) throws IOException
	{
		log.close();
		System.out.println("Exiting...Bye.");
		System.exit(0);
	}

	//------------------------------------------------------------------------------------------------
	private static String getHostName(String ip, String user, String pass) throws MalformedURLException, RemoteException
	{
		URL url = new URL("https://"+ip+"/sdk");
		ServiceInstance si;
		try{
			si= new ServiceInstance(url, user, pass, true);			
		}
		catch(InvalidLogin e)
		{
			si = new ServiceInstance(url,"root",pass,true);
		}

		Folder folder = si.getRootFolder();
		InventoryNavigator iNav = new InventoryNavigator(folder);
		ServiceContent sc=si.getServiceContent();
		AboutInfo about = sc.getAbout();
		
		if(about.getName().equalsIgnoreCase("VMware ESXi"))
		{
			ManagedEntity[] mes=iNav.searchManagedEntities("HostSystem");
			return mes[0].getName();
		}
		
		else
			return "vCenter Server.";
	}

	//------------------------------------------------------------------------------------------------
	private static String run(String user, String pass) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner scan1 = new Scanner(System.in);
		LogWriter logWriter= new LogWriter();
	try {
		
		System.out.println("Enter first IP address...");
		String ip1 = scan1.nextLine();
		if(ip1.equalsIgnoreCase("exit"))
			shutdown(logWriter);
			
		String[] Arr1 = ip1.split("\\.", 4);
		
		int i1= Integer.parseInt(Arr1[0]);
		int i2= Integer.parseInt(Arr1[1]);
		int i3= Integer.parseInt(Arr1[2]);
		int i4= Integer.parseInt(Arr1[3]);
		IPOb startIP = new IPOb(i1, i2, i3, i4);
			
		System.out.println("Enter last IP address...");
		String ip2 = scan1.nextLine();
		if(ip2.equalsIgnoreCase("exit"))
			shutdown(logWriter);
		
		String[] Arr2 = ip2.split("\\.", 4); 
			
		int[] last = new int[4];
		last[0] = Integer.parseInt(Arr2[0]);
		last[1] = Integer.parseInt(Arr2[1]);
		last[2] = Integer.parseInt(Arr2[2]);
		last[3] = Integer.parseInt(Arr2[3]);
	
		if(compare(startIP.getFirst(),last) || compare2(startIP.getFirst(),last))
		{
			System.out.println("Invalid IP range specified.");
			run(user,pass);
		}
		
		System.out.println("Processing...");
		long time1 = System.currentTimeMillis();
		
		while(true)
		{
			boolean flag1,flag2;
				
			if(compare2(startIP.getFirst(), last))
				break;
			else 
			{
				System.out.println("..."+startIP.getIP());
				flag1 = ping(startIP.getIP());
			
				if(flag1)
				{
					flag2=getHostStats(startIP.getIP(),user,pass,0);
					
					if(flag2)
					{
						logWriter.addToLog(startIP.getIP()+"\t"+getHostName(startIP.getIP(),user,pass));//VMware device
					}
					else
						logWriter.addToLog(startIP.getIP()+"\t"+"Non VMware device."); //used ip
				}
				else
				{
					logWriter.addToLog(startIP.getIP()+"\t"+"Unassigned IP."); //unused ip
				}
			}
			startIP.increment();
		}
		long time2 = System.currentTimeMillis();
		long time3 = time2-time1;
		System.out.println("Total Time->"+ (time3/1000)/60f +" minutes.");
		logWriter.close();
		return "Complete";
	}
	catch(NumberFormatException e)
	{
		System.out.println("Please enter valid IP addresses.");
		run(user,pass);
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		System.out.println("Please enter valid IP addresses.");
		run(user,pass);
	}
	return "";
	
	}

	//------------------------------------------------------------------------------------------------
	private static boolean compare2(int[] first, int[] last) 
	{
		if(first[3]>last[3]||first[2]>last[2]||first[1]>last[1]||first[0]>last[0])
		return true;
		else return false;
	}
}
