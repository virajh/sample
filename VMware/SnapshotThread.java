package virajH;
/*
 * by Viraj H.
 * 4/8/2013
 */
import java.rmi.RemoteException;
import java.util.Date;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.SnapshotFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class SnapshotThread implements Runnable 
{
	private VirtualMachine[] client;
	private Thread t;
	
	//------------------------------------------------------------------------------------------------
	public SnapshotThread(VirtualMachine[] h1)
	{
		System.out.println("SnapshotThread : Initiated.");
		client=h1;
		t = new Thread(this);
		t.start();
	}

	//------------------------------------------------------------------------------------------------
	@SuppressWarnings("static-access")
	public void run() 
	{
		while(true) {
			try 
			{
				t.sleep(5000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
		for(VirtualMachine vm : client)
		{
		try {
			Task task = createSnapshot(vm);
			while(task.getTaskInfo().getState()==TaskInfoState.running)
				;
			if(task.getTaskInfo().getState()==TaskInfoState.success)
				System.out.println("SnapshotThread : Snapshot Successful->"+vm.getName());
			else
				System.out.println("SnapshotThread : Snapshot failed->"+vm.getName());
			
			t.sleep(5000);
			} 
		catch (InvalidName e) 
		{
			e.printStackTrace();
		} 
		catch (VmConfigFault e) 
		{
			e.printStackTrace();
		} 
		catch (SnapshotFault e) 
		{
			e.printStackTrace();
		} 
		catch (TaskInProgress e) 
		{
			e.printStackTrace();
		} 
		catch (FileFault e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidState e) 
		{
			e.printStackTrace();
		} 
		catch (RuntimeFault e) 
		{
			e.printStackTrace();
		}
		catch (RemoteException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		}
	}
	}

	//------------------------------------------------------------------------------------------------
	static Task createSnapshot(VirtualMachine vm) throws InvalidName, VmConfigFault, SnapshotFault, TaskInProgress, FileFault, InvalidState, RuntimeFault, RemoteException
		{
			System.out.println("SnapshotThread : Creating snapshot->"+vm.getName());
			String name = vm.getName()+"-snapshot";
			String description = "Snapshot taken->"+new Date().toString();
			
			Task task = vm.createSnapshot_Task(name, description, false, false);
		
			return task;
		}

	//------------------------------------------------------------------------------------------------
	public void setVMList(VirtualMachine[] vm)
	{
		client=vm;
		System.out.println("SnapshotThread : VM list refreshed.");
	}
}
