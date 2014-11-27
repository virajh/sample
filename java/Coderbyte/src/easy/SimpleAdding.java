package easy;

/*
 * Using the Java language, have the function SimpleAdding(num) add up all the numbers from 1 to num.
 *
 * For the test cases, the parameter num will be any number from 1 to 1000.
 *
 * Sample:-
 * 		Input = 12		Output = 78
 * 		Input = 140		Output = 9870
 */

public class SimpleAdding {

	int SimpleAdd(int num)
	{
		int sum = 0;

		for(int i = 0; i <= num; i++)
			sum = sum + i;

		return sum;

	}

	public static void main (String[] args)
	{
		// keep this function call here
		//		Scanner  s = new Scanner(System.in);
		//		Function c = new Function();
		//		System.out.print(c.SimpleAdding(s.nextLine()));

		SimpleAdding simpleAdding = new SimpleAdding();

		System.out.println(simpleAdding.SimpleAdd(1000));
	}
}
