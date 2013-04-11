package virajH;
/*
 * by Viraj H.
 * 4/8/2013
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.vmware.vim25.AlarmSpec;
import com.vmware.vim25.AuthorizationPrivilege;
import com.vmware.vim25.CustomizationFault;
import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.GuestNicInfo;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.MigrationFault;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.SnapshotFault;
import com.vmware.vim25.StateAlarmExpression;
import com.vmware.vim25.StateAlarmOperator;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.Timedout;
import com.vmware.vim25.VimFault;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineMovePriority;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineQuickStats;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineSnapshotTree;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.AlarmManager;
import com.vmware.vim25.mo.AuthorizationManager;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class vMonitor {

	private static HostSystem[] hostList;
	protected static VirtualMachine[] vmList;
	private static VirtualMachine clonePrimary;
	private static ArrayList<ResourcePool> resPool;
	private static ClusterComputeResource[] clusterList;
	private static InventoryNavigator iNav;
	private static AlarmManager alarmMon;
	private static boolean flag = true;
	private static SnapshotThread thread;
	//------------------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException 
	{
		System.out.println("vMonitor has started...");
		System.out.println("------------------------");
		
		URL url = new URL("https://130.65.157.175/sdk");
		ServiceInstance si = new ServiceInstance(url, "administrator", "12!@qwQW", true);
		Folder rootFolder = si.getRootFolder();
		iNav = new InventoryNavigator(rootFolder);
		alarmMon = si.getAlarmManager();
		
		AuthorizationManager am2 = si.getAuthorizationManager();
		AuthorizationPrivilege[] ap=am2.getPrivilegeList();
		String[] privIds = new String[ap.length];		
		for(int i=0;i<ap.length;i++)
			privIds[i] = ap[i].getPrivId();
		am2.addAuthorizationRole("vAPI", privIds);
		
		scanAll(iNav);
			
		for(int i=0;i<vmList.length;i++)
		{
			getStats(vmList[i],i);
			setAlarm(alarmMon, vmList[i]);
			Task task = createSnapshot(vmList[i]);
			while(task.getTaskInfo().getState()==TaskInfoState.running)
				;
			System.out.println(vmList[i].getName()+"->Snapshot successful.");
			System.out.println("------------------------");
		}
		
		thread = new SnapshotThread(vmList);
			
		System.out.println("Beginning Monitoring...");
		System.out.println("------------------------");
		while(true)
		{ boolean alive;
		
			for(VirtualMachine vm : vmList)
				{alive=ping(vm);
				if(alive)
					;
				else 
					recovery(vm);
				}
			System.out.println("------------------------");
		}
	}
	
	//------------------------------------------------------------------------------------------------
	static void getStats(VirtualMachine vm, int k) throws InvalidProperty, RuntimeFault, RemoteException, NullPointerException {
		
		VirtualMachineConfigInfo vmc = vm.getConfig();
		VirtualMachineRuntimeInfo vmri = vm.getRuntime();
		ResourcePool rp = vm.getResourcePool();
		String rp1 = rp.getName();
		ComputeResource rp2 = rp.getOwner();
		HostSystem[] rp3 = rp2.getHosts();
		
		System.out.println("VM"+(k+1)+"->"+vm.getName());
		System.out.println("OS->"+vmc.getGuestFullName());
		System.out.println("Status->"+vmri.getPowerState());
		System.out.println("Host->"+rp3[0].getName());
		System.out.println("Resource Pool->"+rp1);
		System.out.println();
		
		VirtualMachineSummary vms = vm.getSummary();
		VirtualMachineQuickStats vmqs = vms.getQuickStats();
		VirtualHardware vmh =	vmc.getHardware();
		
		System.out.println("Number of CPU->"+vmh.getNumCPU());
		System.out.println("CPU Speed->"+vmri.getMaxCpuUsage()+" MHz");
		System.out.println("CPU Usage->"+vmqs.getOverallCpuUsage()+" MHz");
		System.out.println();
		
		
		System.out.println("Total RAM->"+vmh.getMemoryMB()+" MB");
		System.out.println("RAM Usage->"+vmqs.getHostMemoryUsage()+" MB");
		System.out.println();
		
		GuestInfo guestInfo = vm.getGuest();
		GuestNicInfo[] nic = guestInfo.getNet();
		
		System.out.println("IP->"+vm.getGuest().ipAddress);
		if(nic!=null) {
		if(nic.length>0 && nic[0]!=null)
		System.out.println("Network->"+nic[0].getNetwork());}
		System.out.println();
		
		Datastore[] vmn = vm.getDatastores();
		for(int i=0;i<vmn.length;i++) 
		{	
		System.out.println("Datastore->"+vmn[i].getName());
		
		DatastoreInfo a = vmn[i].getInfo();
		System.out.println("Location->"+a.getUrl());
		
		DatastoreSummary b = vmn[i].getSummary();
		System.out.println("Total Size->"+(b.getCapacity()/(1024*1024*1024))+" GB");
		System.out.println("Free space->"+(a.getFreeSpace()/(1024*1024*1024))+" GB");
		}
		System.out.println("------------------------");
	}

	//------------------------------------------------------------------------------------------------
	static void getVMList(HostSystem[] hs) throws InvalidProperty, RuntimeFault, RemoteException
		{
			int count=0;
			for(int i=0;i<hs.length;i++)
			{
				VirtualMachine[] v1 = hs[i].getVms();
				count += v1.length;
			}
				
			VirtualMachine[] y = new VirtualMachine[count];int k=0;
			for(int i=0;i<hs.length;i++)
			{
				VirtualMachine[] v1 =hs[i].getVms();	
				for(int j=0;j<v1.length;j++)
					y[k++]= v1[j];
			}
			
			for(int i=0;i<y.length;i++)
			{
				if(y[i].getName().contentEquals("CloneVM"))
				{
					clonePrimary = y[i];
				}
			}			
			
			vmList = new VirtualMachine[(count-1)]; int q=0;
			for(int i=0;i<y.length;i++)
			{
				if(y[i]!=clonePrimary)
					vmList[q++]=y[i];
			}
			
			System.out.println("VM Count->"+(count-1));
			for(int i=0;i<vmList.length;i++)
				System.out.println((i+1)+". "+vmList[i].getName());
			
			System.out.println("------------------------");
		}
		
	//------------------------------------------------------------------------------------------------
	static void getHostList(InventoryNavigator iNav) throws InvalidProperty, RuntimeFault, RemoteException
		{
			ManagedEntity[] x = iNav.searchManagedEntities("HostSystem");
			hostList = new HostSystem[x.length];
			for(int i=0;i<x.length;i++) {
				hostList[i]=(HostSystem) x[i];
			}
			
			System.out.println("---Hosts-found---");
			for (int i=0;i<hostList.length;i++)
			{
			System.out.println((i+1)+". "+hostList[i].getName());
			}
			System.out.println("------------------------");
		}
		
	//------------------------------------------------------------------------------------------------
	static void getResPool(InventoryNavigator iNav) throws InvalidProperty, RuntimeFault, RemoteException
		{
			ManagedEntity[] x = iNav.searchManagedEntities("ResourcePool");
					
			resPool = new ArrayList<ResourcePool>();
			for(ManagedEntity rp :x)
			{
				resPool.add((ResourcePool) rp);
			}
			int i=1;
			System.out.println("--Resource-Pools--");
			for(ResourcePool rp:resPool)
				{System.out.println(i+". "+rp.getName());
					i++;}
				
			System.out.println("------------------------");
		}

	//------------------------------------------------------------------------------------------------
	static void setAlarm(AlarmManager am, VirtualMachine vm) throws InvalidName, RuntimeFault, RemoteException
	{
		
		String s = vm.getName()+"-AlarmPowerOff";
		StateAlarmExpression sae = new StateAlarmExpression();
		sae.setType("VirtualMachine");
		sae.setStatePath("runtime.powerState");
	    sae.setOperator(StateAlarmOperator.isEqual);
	    sae.setRed("poweredOff");
		
	    AlarmSpec as = new AlarmSpec();
		as.setDescription("Alarm for powered Off VM");
		as.setName(s);
		as.setExpression(sae);
		as.setEnabled(true);
		
		try 
		{	am.createAlarm(vm,as);	}
		
		catch(com.vmware.vim25.DuplicateName e)
		{
			System.out.print("Error->");
			System.out.println(vm.getName()+" has an alarm already defined.");
		}
		
		System.out.println("Alarm set->"+vm.getName());
		System.out.println("------------------------");
	}

	//------------------------------------------------------------------------------------------------
	static void cloneVM(VirtualMachine vm, VirtualMachine clone) throws VmConfigFault, TaskInProgress, CustomizationFault, FileFault, InvalidState, InsufficientResourcesFault, MigrationFault, InvalidDatastore, RuntimeFault, RemoteException
	{
		VirtualMachineRelocateSpec rel=new VirtualMachineRelocateSpec();
		HostSystem hs = selectHost2(vm);
		rel.setHost(hs.getMOR());
		rel.setPool(selectResPool(hs));
		VirtualMachineCloneSpec spec = new VirtualMachineCloneSpec();
		spec.setPowerOn(true);
		spec.setTemplate(false);
		spec.setLocation(rel);
		String name = vm.getName()+"-Clone";
		
		System.out.println("Beginning recovery by cloning...");
		
		long t1 =System.currentTimeMillis();
		
		Task task = clone.cloneVM_Task((Folder) vm.getParent(), name, spec);
		
		while(task.getTaskInfo().getState()==TaskInfoState.running)
			;
		
		long t2 =System.currentTimeMillis();
		
		if(task.getTaskInfo().getState()==TaskInfoState.success)
			{System.out.println("VM failure recovery complete.");
			System.out.println("Recovery time->"+(t2-t1)/1000f+" Seconds.");
			removeVM(vm);
			}
		else
			if(task.getTaskInfo().getState()==TaskInfoState.error)
				System.out.println("VM recovery failed. Cloning error.");
			else
				System.out.println("Unknown Error.");
		
	}

	//------------------------------------------------------------------------------------------------
	static void getClusters(InventoryNavigator iNav) throws InvalidProperty, RuntimeFault, RemoteException
	{
		ManagedEntity[] x = iNav.searchManagedEntities("ClusterComputeResource");
		clusterList = new ClusterComputeResource[x.length];
		
		for(int i=0;i<x.length;i++) {
			clusterList[i]=(ClusterComputeResource) x[i];
		}
		
		System.out.println("---Clusters-found---");
		for (int i=0;i<clusterList.length;i++)
		{
		System.out.println((i+1)+"-"+clusterList[i].getName());
		}
		System.out.println("------------------------");
	}

	//------------------------------------------------------------------------------------------------
	static boolean ping(VirtualMachine vm) throws IOException
    {
		String x = vm.getGuest().getIpAddress();
		InetAddress inet = InetAddress.getByName(x);
		System.out.println("Pinging->"+vm.getName());
		if(inet.isReachable(3000))
			{System.out.println(vm.getName()+" is alive.");
			return true;}
		else {
			System.out.println(vm.getName()+" is dead.");
			return false;}
    }

	//------------------------------------------------------------------------------------------------
	static VirtualMachine getClone() throws InvalidProperty, RuntimeFault, RemoteException
	{
		if(clonePrimary!=null)
		{
			return clonePrimary;
		}
		else 
		{
		VirtualMachine clone = null;
		VirtualMachine[] x =null;
		
		for(ResourcePool rp : resPool)
		{	if(rp.getName().equalsIgnoreCase("ClonePool"))
				x = rp.getVMs();	}
		
		clone= x[0];
		return clone;
		}
	}

	//------------------------------------------------------------------------------------------------
	static void recoverVM(VirtualMachine vm) throws VmConfigFault, TaskInProgress, CustomizationFault, FileFault, InvalidState, InsufficientResourcesFault, MigrationFault, InvalidDatastore, RuntimeFault, InvalidProperty, RemoteException
	{
		System.out.println("Cold migration called for "+vm.getName());
		cloneVM(vm,getClone());
		scanAll(iNav);
		for(int i=0;i<vmList.length;i++)
			{getStats(vmList[i],i);
			setAlarm(alarmMon, vmList[i]);}
		thread.setVMList(vmList);
	}
	
	//------------------------------------------------------------------------------------------------
	static void scanAll(InventoryNavigator iNav) throws InvalidProperty, RuntimeFault, RemoteException
	{
		getClusters(iNav);
		getHostList(iNav);
		getVMList(hostList);
		getResPool(iNav);
	}

	//------------------------------------------------------------------------------------------------
	static void removeVM(VirtualMachine vm) throws VimFault, RuntimeFault, RemoteException
	{
		System.out.println("Removing failed VM->"+vm.getName());
		vm.powerOffVM_Task();
		System.out.println(vm.getName()+" powered off.");
		Task task = vm.destroy_Task();
		
		while(task.getTaskInfo().getState()==TaskInfoState.running)
			;
		
		if(task.getTaskInfo().getState()==TaskInfoState.success)
			System.out.println("Failed VM removal complete.");
		else
			if(task.getTaskInfo().getState()==TaskInfoState.error)
				System.out.println("VM removal failed.");
			else
				System.out.println("Unknown Error.");
	}

	//------------------------------------------------------------------------------------------------
	static void powerSwitch(VirtualMachine vm) throws VmConfigFault, TaskInProgress, FileFault, InvalidState, InsufficientResourcesFault, RuntimeFault, RemoteException {

		
		VirtualMachineRuntimeInfo vmri = vm.getRuntime();
		VirtualMachinePowerState vmps = vmri.getPowerState();
		System.out.println("Switching power state...");
		
		switch(vmps) {
									
		case poweredOff :System.out.println("Powering on...");
						vm.powerOnVM_Task(null); break;	
		
		case suspended :System.out.println("Powering on...");
						vm.powerOnVM_Task(null); break;
						
		case poweredOn :if(flag) {System.out.println("Powering off...");
								vm.powerOffVM_Task(); flag=false; break;}
						else {
								System.out.println("Suspending...");
								vm.suspendVM_Task(); flag=true; break;
								}
						}//End of Switch
		System.out.println("------------------------");
		}

	//------------------------------------------------------------------------------------------------
	static Task createSnapshot(VirtualMachine vm) throws InvalidName, VmConfigFault, SnapshotFault, TaskInProgress, FileFault, InvalidState, RuntimeFault, RemoteException
	{
		System.out.println(vm.getName()+"->Creating snapshot...");
		String name = vm.getName()+"-snapshot";
		String description = "Snapshot taken->"+new Date().toString();
		
		Task task = vm.createSnapshot_Task(name, description, false, false);
	
		return task;
	}
	
	//------------------------------------------------------------------------------------------------
	static void recovery(VirtualMachine vm) throws VmConfigFault, TaskInProgress, CustomizationFault, FileFault, InvalidState, InsufficientResourcesFault, MigrationFault, InvalidDatastore, RuntimeFault, InvalidProperty, RemoteException
	{
		System.out.println("Recovery called for failed VM->"+vm.getName());
		VirtualMachineSnapshotInfo x = vm.getSnapshot();
		if(x!=null) 
		{
		System.out.println("Recovering from snapshot...");
		
		VirtualMachineSnapshotTree[] y = x.getRootSnapshotList();
		
		System.out.println(y[0].getName());
		System.out.println(y[0].getDescription());
		
		String name = vm.getName()+"-Clone";
		
		VirtualMachineRelocateSpec rel=new VirtualMachineRelocateSpec();		
		HostSystem hs = selectHost2(vm);
		rel.setHost(hs.getMOR());
		rel.setPool(selectResPool(hs));
		
		VirtualMachineCloneSpec spec = new VirtualMachineCloneSpec();
		spec.setPowerOn(true);
		spec.setTemplate(false);
		spec.setSnapshot(vm.getCurrentSnapShot().getMOR());
		spec.setLocation(rel);		
		
		Task task = vm.cloneVM_Task((Folder) vm.getParent(), name, spec);
		
		while(task.getTaskInfo().getState()==TaskInfoState.running)
			;
		
		if(task.getTaskInfo().getState()==TaskInfoState.success)
		{ 
			System.out.println("Successfully cloned "+name+" from "+vm.getName()+" snapshot "+ y[0].getName() );
			removeVM(vm);
			scanAll(iNav);
			for(int i=0;i<vmList.length;i++)
			{getStats(vmList[i],i);
			setAlarm(alarmMon, vmList[i]);}
			thread.setVMList(vmList);
			System.out.println("------------------------");			
		}
		else 
			{
				System.out.println("Snapshot cloning failed. Switching to cold migration for recovery.");
				recoverVM(vm);
			}
		}
		else
			{
				System.out.println("No snapshots detected. Switching to cold migration for recovery.");
				recoverVM(vm);
			}
		}

	//------------------------------------------------------------------------------------------------
	static ManagedObjectReference selectHost(VirtualMachine vm)
	{
		HostSystem host = null;
		ManagedObjectReference current=vm.getRuntime().getHost();
		for(int i=0;i<hostList.length;i++)
		{
			if(hostList[i].getMOR()==current)
				;
			else
				host=hostList[i];
		}
		return host.getMOR();
	}

	//------------------------------------------------------------------------------------------------
	static ManagedObjectReference selectResPool(HostSystem hs) throws InvalidProperty, RuntimeFault, RemoteException
	{
		ComputeResource cr = (ComputeResource) hs.getParent();
		return cr.getResourcePool().getMOR();
	}
	
	//------------------------------------------------------------------------------------------------
	static HostSystem selectHost2(VirtualMachine vm)
	{
		ManagedObjectReference x = vm.getRuntime().getHost();
		for(HostSystem hs : hostList)
		{
			if(hs.getMOR().equals(x))
				;
			else
				return hs;
		}
		return null;
	}

	//------------------------------------------------------------------------------------------------
	static void moveVM(VirtualMachine vm) throws VmConfigFault, Timedout, FileFault, InvalidState, InsufficientResourcesFault, MigrationFault, RuntimeFault, RemoteException
	{
		System.out.println("Migrate called for->"+vm.getName());

		Task task = vm.migrateVM_Task(null, selectHost2(vm), VirtualMachineMovePriority.defaultPriority, VirtualMachinePowerState.poweredOff);
		
		while(task.getTaskInfo().getState()==TaskInfoState.running)
			;
		if(task.getTaskInfo().getState()==TaskInfoState.success)
			{
			System.out.println(vm.getName()+" migration complete.");
			vm.powerOnVM_Task(null);}
		else
			System.out.println("Migration Error.");
		
	}
}
