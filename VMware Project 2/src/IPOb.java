/*
 * By Viraj H.
 * 4/19/2013
 * San Jose State University
 */

public class IPOb {
	
	private int[] Arr;
	
	public IPOb(int int1, int int2, int int3, int int4)
	{
		Arr = new int[4];
		Arr[0]=int1;
		Arr[1]=int2;
		Arr[2]=int3;
		Arr[3]=int4;
	}
	
	//---------------------------------------------------------------
	public String getIP()
	{
		return Arr[0]+"."+Arr[1]+"."+Arr[2]+"."+Arr[3];
	}
	
	//---------------------------------------------------------------
	public int[] getNext()
	{
		if(Arr[3]<=254)
		{
			Arr[3]++;
			return Arr;
		}
		else
		{
			if(Arr[2]<=254)
			{
			Arr[2]++;
			Arr[3]=0;
			return Arr;
			}
			else
			{	if(Arr[1]<=254)
				{
					Arr[1]++;
					Arr[2]=0;
					Arr[3]=0;
					return Arr;
				}
				else {
						if(Arr[0]<=254)
						{
							Arr[0]++;
							Arr[1]=0;
							Arr[2]=0;
							Arr[3]=0;
							return Arr;
						}
						else
						{
							System.out.println("Error->End of IP range.\nExiting...");
							System.exit(0);
						}
					}
			}	
		}
		return null;
	}
	
	//---------------------------------------------------------------
	public int[] getFirst()
	{
		return Arr;
	}
	
	//---------------------------------------------------------------
	public void increment()
	{
		if(Arr[3]<=254)
			Arr[3]++;
		else
		{
			if(Arr[2]<=254)
			{
				Arr[2]++;
				Arr[3]=0;
			}
			else
			{
				if(Arr[1]<=254)
				{
					Arr[1]++;
					Arr[2]=0;
					Arr[3]=0;
				}
				else {
					if(Arr[0]<=254)
					{
						Arr[0]++;
						Arr[1]=0;
						Arr[2]=0;
						Arr[3]=0;
					}
					else
					{
						System.out.println("Error->End of IP range.\nExiting...");
						System.exit(0);
					}
				}
			}
		}
	}
}
