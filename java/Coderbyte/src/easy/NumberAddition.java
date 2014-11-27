package easy;


/*
 *	Using the Java language, have the function NumberSearch(str) take the str parameter, search for all the numbers in the string,
 *	add them together, then return that final number.
 *
 *	For example: if str is "88Hello 3World!" the output should be 91.
 *
 *	You will have to differentiate between single digit numbers and multiple digit numbers like in the example above.
 *
 *	So "55Hello" and "5Hello 5" should return two different answers. Each string will contain at least one letter or symbol.
 *
 *	Sample:-
 *		Input = "75Number9"				Output = 84
 *		Input = "10 2One Number*1*"		Output = 13
 */

public class NumberAddition {

	int addNumbers(String str)
	{
		String[] words = str.split("\\s");

		int sum = 0;

		for(String word: words)
		{
			char[] charArr = word.toCharArray();

			StringBuffer buffer = new StringBuffer();

			int length = charArr.length;

			for(int i = 0; i < length; i++)
			{
				if(Character.isDigit(charArr[i]))
					buffer.append(charArr[i]);

				else if(buffer.length() > 0)
				{
					sum += Integer.parseInt(buffer.toString());
					buffer = new StringBuffer();
				}
			}

			if(buffer.length() > 0)
				sum += Integer.parseInt(buffer.toString());
		}

		return sum;
	}

	public static void main (String[] args)
	{
		NumberAddition underTest = new NumberAddition();

		System.out.println(underTest.addNumbers("88Hello 3World!"));

		System.out.println(underTest.addNumbers("55Hello"));

		System.out.println(underTest.addNumbers("5Hello 5"));

		System.out.println(underTest.addNumbers("75Number9"));

		System.out.println(underTest.addNumbers("10 20ne Number*1*"));
	}
}
