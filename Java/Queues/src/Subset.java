
public class Subset {

	public static void main(String[] args)
	{
		RandomizedQueue<String> que = new RandomizedQueue<String>();

		String item = StdIn.readString();
		int number = Integer.parseInt(args[0]);

		while (!StdIn.isEmpty())
		{
			item = StdIn.readString();
			que.enqueue(item);
		}

		while(number != 0)
		{
			System.out.println(que.dequeue());
			number--;
		}
	}
}
