package easy;

/*
 *	Using the Java language, have the function DivisionStringified(num1,num2) take both parameters being passed,
 *	divide num1 by num2, and return the result as a string with properly formatted commas.
 *
 *	If an answer is only 3 digits long, return the number with no commas (ie. 2 / 3 should output "1").
 *
 *	For example: if num1 is 123456789 and num2 is 10000 the output should be "12,345".
 */

public class DivisionStringified {

	String stringDivide(int num1, int num2)
	{
		String value = String.valueOf(num1/num2);

		char[] charArray = value.toCharArray();

		StringBuffer buffer = new StringBuffer();

		int length = charArray.length;

		int commaCounter = 3;

		for(int i = length - 1; i >= 0; i--)
		{
			buffer.insert(0, charArray[i]);
			commaCounter--;

			if(commaCounter == 0 && i != 0)
			{
				buffer.insert(0, ",");
				commaCounter = 3;
			}
		}

		return buffer.toString();
	}

	public static void main (String[] args)
	{
		DivisionStringified object = new DivisionStringified();

		System.out.println(object.stringDivide(123456789, 10000));

		System.out.println(object.stringDivide(123456789, 1000));

		System.out.println(object.stringDivide(123456789, 100));

		System.out.println(object.stringDivide(503394930, 43));

		System.out.println(object.stringDivide(45, 50));

		System.out.println(object.stringDivide(5, 54));

		System.out.println(object.stringDivide(63, 14));
	}
}
