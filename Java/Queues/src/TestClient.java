
import java.util.Iterator;
import java.util.NoSuchElementException;


public class TestClient {

	public static void main(String[] args)
	{
		TestClient self = new TestClient();

		self.testRandomizedQueue();

		self.testDeque();
	}


	private void testDeque()
	{
		Deque<String> deque = new Deque<String>();

		// This is a regular English sentence.

		deque.addFirst(" regular");
		deque.addLast(" English");
		deque.addFirst(" a");
		deque.addFirst(" is");
		deque.addLast(" sentence.");
		deque.addFirst("This");

		while(!deque.isEmpty())
			System.out.println(deque.removeFirst());

		deque.addFirst(" regular");
		deque.addLast(" English");
		deque.addFirst(" a");
		deque.addFirst(" is");
		deque.addLast(" sentence.");
		deque.addFirst("This");

		Iterator<String> iterator1 = deque.iterator();
		Iterator<String> iterator2 = deque.iterator();

		while(iterator1.hasNext() || iterator2.hasNext())
			System.out.println(iterator1.next()+" | "+iterator2.next());

		String expected = "This is a regular English sentence.";
		String result = "";

		Iterator<String> iterator = deque.iterator();

		while(iterator.hasNext())
			result = result.concat((String)iterator.next());

		System.out.println(result);
		System.out.println(result.equals(expected));

		try {
			iterator.next();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		try {
			iterator.remove();
		} catch (UnsupportedOperationException e) {
			System.out.println(e.getMessage());
		}

		try {
			deque.removeFirst();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		try {
			deque.removeLast();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		try {
			deque.addFirst(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		try {
			deque.addLast(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}


	private void testRandomizedQueue()
	{
		RandomizedQueue<String> que = new RandomizedQueue<String>();

		try {
			que.dequeue();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		// This is a regular English sentence.

		que.enqueue("This ");
		que.enqueue("is ");
		que.enqueue("a ");
		que.enqueue("regular ");
		que.enqueue("English ");
		que.enqueue("sentence.");

		Iterator<String> iterator1 = que.iterator();
		Iterator<String> iterator2 = que.iterator();

		while(iterator1.hasNext() && iterator2.hasNext())
			System.out.println(iterator1.next()+" | "+iterator2.next());

		for(int i = 0; i < 5; i++)
			System.out.println(que.sample());

		System.out.println("------");

		for(int i = 0; i <= 5; i++)
			System.out.println(que.dequeue());

		System.out.println("------");

		que.enqueue("This ");
		que.enqueue("is ");
		que.enqueue("a ");
		que.enqueue("regular ");
		que.enqueue("English ");
		que.enqueue("sentence.");

		Iterator<String> iterator = que.iterator();

		while(iterator.hasNext())
			System.out.println(iterator.next());

		System.out.println("------");

		try {
			iterator.remove();
		} catch (UnsupportedOperationException e) {
			System.out.println(e.getMessage());
		}
	}
}
