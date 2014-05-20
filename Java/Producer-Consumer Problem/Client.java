/*
 * By Viraj H <viraj.hardikar@gmail.com>
 * 
 * for the 8x8 Inc. coding challenge on 5/15/2014.
 * 
 * All rights reserverd.
 */

public class Client{

	public static void main(String[] args) throws InterruptedException {

		int size = 5;
		System.out.println("Queue size is set to "+ size);
		
		BlockingQueue que = new BlockingQueue(size);
		Producer prod = new Producer(que);
		Consumer con = new Consumer(que);
		
		Thread t1 = new Thread(con);
		Thread t2 = new Thread(prod);
		
		t2.start();
		
		Thread.sleep(1000);
		
		t1.start();
	}

}
