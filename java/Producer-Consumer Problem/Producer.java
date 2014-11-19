/*
 * By Viraj H <viraj.hardikar@gmail.com>
 * 
 * for the 8x8 Inc. coding challenge on 5/15/2014.
 * 
 * All rights reserverd.
 */

import java.util.Random;

public class Producer implements Runnable {

	private BlockingQueue queue;
	
	Producer(BlockingQueue que){
		this.queue = que;
	}
	
	public void run() {
		System.out.println("Starting producer...");
		
		Random gen = new Random();
		while(true){
			int k = gen.nextInt(50);
			System.out.println("Producer: "+k);
			try {
				queue.put(k);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
