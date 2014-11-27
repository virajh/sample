package easy;

/*
 *	Using the Java language, have the function DashInsert(num) insert dashes ('-') between each two odd numbers in num.
 *
 *	For example: if num is 454793 the output should be 4547-9-3. Don't count zero as an odd number.
 *
 *	Sample:-
 *		Input = 99946	Output = "9-9-946"
 *		Input = 56730	Output = "567-30"
 */

public class DashInsert {

	String insertDash(int num)
	{
		char[] charArr = Integer.toString(num).toCharArray();

		StringBuffer buffer = new StringBuffer();

		int length = charArr.length;

		for(int i = 0; i <= length - 1; i++)
		{
			buffer.append(charArr[i]);

			if(i == length - 1)
				break;

			if( isOdd(charArr[i]) && isOdd(charArr[i + 1]))
				buffer.append('-');
		}

		return buffer.toString();
	}

	private boolean isOdd(char ch)
	{
		return Integer.parseInt(Character.toString(ch)) % 2 != 0;
	}

	public static void main (String[] args)
	{
		DashInsert dashInsert = new DashInsert();

		System.out.println(dashInsert.insertDash(454793));
	}
}
