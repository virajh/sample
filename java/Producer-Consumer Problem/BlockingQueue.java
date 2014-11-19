/*
 * By Viraj H. <viraj.hardikar@gmail.com>
 * 
 * for the 8x8 Inc. coding challenge on 5/15/2014.
 * 
 * All rights reserved.
 */

public class BlockingQueue {

	private int size;
	private int count;
	private myList list;
	
	BlockingQueue(int size){
		this.size = size;
		list = null;
		count = 0;
	}
	
	void put(int obj) throws InterruptedException{
		synchronized (this) {
			if(count == size){
				System.out.println("Queue is full. Waiting...");
				list.print();
				this.wait();
			}
		}
		
		synchronized (this) {
			if(list ==null){
				list = new myList(obj);
				this.notifyAll();
			}
			else{
				list.put(obj);
				count++;
				this.notifyAll();
			}
		}
	}
	
	int take() throws InterruptedException{
		
		synchronized (this) {
			if(count==0){
				System.out.println("Queue is empty waiting...");
				this.wait();
			}
		}
		
		synchronized (this) {
			count--;
			int k = list.take();
			this.notifyAll();
			return k;
		}
	}
	
	private class myList{
		//doubly ended linked-list
		private Node root;
		private Node tail;
		
		myList(int obj){
			root = new Node(obj);
			tail = root;
		}
		
		void put(int obj)
		{
			//insert objects at tail
			tail.setNext(new Node(obj));
			tail = tail.getNext();
		}
		
		@SuppressWarnings("null")
		int take(){
			//remove objects from root
			if (root == null)
				return (Integer) null;
			
			int obj = root.getValue();
			root = root.getNext();
			return obj;
		}
		
		void print(){
			Node node = root;
			while(node.getNext()!=null){
				System.out.print(node.getValue()+" - ");
				node = node.getNext();
			}
		}
	}

	
	private class Node{
		private int object;
		private Node node;
		
		Node(int obj){
			this.object=obj;
			this.node=null;
		}
		
		int getValue(){
			return this.object;
		}
		
		void setNext(Node nextNode){
			this.node=nextNode;
		}
		
		Node getNext(){
			return node;
		}
	}
}
