package easy;

/*
 *	Using the Java language, have the function AdditivePersistence(num) take the parameter being passed which will always be a positive integer
 *	and return its additive persistence which is the number of times you must add the digits in num until you reach a single digit.
 *
 *	For example: if num is 2718 then your program should return 2 because 2 + 7 + 1 + 8 = 18 and 1 + 8 = 9 and you stop at 9.
 *
 *	Sample:-
 *		Input = 4	Output = 0
 *		Input = 19	Output = 2
 */

public class AdditivePersistence {

	int getAdditivePersistence(int num)
	{
		int additivePersistence = 1;

		if(num < 10)
			return 0;

		char[] number = Integer.toString(num).toCharArray();

		int length = number.length;

		int sum = 0;

		for(int i = 0; i < length; i++)
		{
			sum += Integer.parseInt(Character.toString(number[i]));
		}

		if(sum > 9)
		{
			additivePersistence += getAdditivePersistence(sum);
		}

		return additivePersistence;
	}

	public static void main (String[] args)
	{
		AdditivePersistence underTest = new AdditivePersistence();

		System.out.println(underTest.getAdditivePersistence(2718));

		System.out.println(underTest.getAdditivePersistence(4));

		System.out.println(underTest.getAdditivePersistence(19));
	}
}
