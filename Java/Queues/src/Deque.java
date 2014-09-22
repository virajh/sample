
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

	private int count;
	private Node root;

	public Deque()
	{
		count = 0;
		root = null;
		assert validate();
	}


	public boolean isEmpty()
	{
		return count == 0;
	}


	public int size()
	{
		return count;
	}


	public void addFirst(Item item)
	{
		if(item == null)
			throw new NullPointerException("addFirst(): null argument");

		Node oldRoot = root;
		root = new Node(item, oldRoot);
		count++;
		assert validate();
	}


	public void addLast(Item item)
	{
		if(item == null)
			throw new NullPointerException("addLast(): null argument");

		Node current = root;

		while(current.next != null)
			current = current.next;

		Node newNode = new Node(item, null);

		current.next = newNode;

		count++;

		assert validate();
	}


	public Item removeFirst()
	{
		if(isEmpty())
			throw new NoSuchElementException("removeFirst(): deque underflow");

		Node oldRoot = root;

		root = root.next;

		count--;

		assert validate();

		return oldRoot.item;
	}


	public Item removeLast()
	{
		if(isEmpty())
			throw new NoSuchElementException("removeLast(): deque underflow");

		Node current = root;

		while(current.next.next != null)
			current = current.next;

		Item item = current.next.item;

		current.next = null;

		count --;

		assert validate();

		return item;
	}


	public static void main(String[] args)
	{

	}


	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}


	private class DequeIterator implements Iterator<Item>
	{

		private Node current = root;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove(): unsupported operation");
		}

		@Override
		public Item next()
		{
			if(!hasNext())
				throw new NoSuchElementException("next(): no more items");

			Item item = current.item;

			current = current.next;

			return item;
		}

	}

	private class Node
	{
		private Item item;
		private Node next;

		private Node(Item item, Node next)
		{
			this.item = item;
			this.next = next;
		}
	}


	private boolean validate()
	{
		if(count == 0 && root != null)
			return false;

		else if(count != 0 && root == null)
			return false;

		else if(count == 1 && root == null)
			return false;

		else if(count == 1 && root.next != null)
			return false;

		else if (count > 1 && root.next == null)
			return false;

		int nodes = 0;
		for(Node x = root; x != null; x = x.next)
			nodes++;

		return nodes == count;
	}
}
