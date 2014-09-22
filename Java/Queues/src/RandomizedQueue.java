
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>{

	private int count;
	private Item[] queue;


	@SuppressWarnings("unchecked")
	public RandomizedQueue()
	{
		count = 0;
		queue = (Item[]) new Object[1];
	}


	public boolean isEmpty()
	{
		return count == 0;
	}


	public int size()
	{
		return count;
	}


	public void enqueue(Item item)
	{
		if(item == null)
			throw new NullPointerException("enqueue(): null argument");

		if(count == queue.length)
			resize(2*queue.length);

		queue[count++] = item;
	}


	@SuppressWarnings("unchecked")
	private void resize(int newSize)
	{
		assert newSize > count;

		Item[] newQ = (Item[]) new Object[newSize];

		for(int i = 0; i < count; i++)
			newQ[i] = queue[i];

		queue = newQ;
	}


	public Item sample()
	{
		if(count == 0)
			throw new NoSuchElementException("sample(): queue underflow");

		return queue[StdRandom.uniform(count)];
	}


	public Item dequeue()
	{
		if(count == 0)
			throw new NoSuchElementException("dequeue(): queue underflow");

		int random = StdRandom.uniform(count);

		Item item = queue[random];

		queue[random] = queue[--count];

		if(count <= queue.length/4)
			resize(queue.length/2);

		return item;
	}


	@Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}


	private class RandomizedQueueIterator implements Iterator<Item>
	{
		private int counter = 0;
		private int[] shuffledIndices = new int[count];

		@Override
		public boolean hasNext()
		{
			if(counter == count)
				return false;

			return true;
		}

		@Override
		public Item next()
		{
			if(!hasNext())
				throw new NoSuchElementException("next(): no more items");

			if(counter == 0)
			{
				for(int i = 0; i < count; i++)
					shuffledIndices[i] = i;

				StdRandom.shuffle(shuffledIndices);
			}

			Item item = queue[shuffledIndices[counter++]];

			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove(): unsupported operation");
		}

	}


	public static void main(String[] args)
	{

	}
}
