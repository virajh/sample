/*
 * By Viraj H <viraj.hardikar@gmail.com>
 * 
 * for the 8x8 Inc. coding challenge on 5/15/2014.
 * 
 * All rights reserverd.
 */

public class Consumer implements Runnable {

	private BlockingQueue queue;
	
	Consumer(BlockingQueue que){
		this.queue = que;
	}
	
	public void run() {
		
		System.out.println("Starting consumer...");
		
		while(true){
			try {
				System.out.println("Consumer: "+ queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
