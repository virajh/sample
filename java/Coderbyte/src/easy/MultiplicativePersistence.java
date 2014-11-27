package easy;

/*
 *	Using the Java language, have the function MultiplicativePersistence(num) take the parameter being passed which will always be a positive integer
 *	and return its multiplicative persistence which is the number of times you must multiply the digits in num until you reach a single digit.
 *
 *	For example: if num is 39 then your program should return 3 because 3 * 9 = 27 then 2 * 7 = 14 and finally 1 * 4 = 4 and you stop at 4.
 *
 *	Sample:-
 *		Input = 4	Output = 0
 *		Input = 25	Output = 2
 */

public class MultiplicativePersistence {

	int getMultiplicativePersistence(int num)
	{
		int multiplicativePersistence = 1;

		if(num < 10)
			return 0;

		char[] number = Integer.toString(num).toCharArray();

		int length = number.length;

		int product = 1;

		for(int i = 0; i < length; i++)
		{
			product *= Integer.parseInt(Character.toString(number[i]));
		}

		if(product > 9)
		{
			multiplicativePersistence += getMultiplicativePersistence(product);
		}

		return multiplicativePersistence;
	}

	public static void main (String[] args)
	{
		MultiplicativePersistence underTest =  new MultiplicativePersistence();

		System.out.println(underTest.getMultiplicativePersistence(39));

		System.out.println(underTest.getMultiplicativePersistence(4));

		System.out.println(underTest.getMultiplicativePersistence(25));
	}
}
