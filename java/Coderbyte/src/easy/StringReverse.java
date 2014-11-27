package easy;

/*
 * Have the function FirstReverse(str) take the str parameter being passed and return the string in reversed order.
 */

public class StringReverse {

	String FirstReverse(String str)
	{
		char[] array = str.toCharArray();

		int length = array.length;

		if(length < 2)
			return str;

		StringBuffer buffer = new StringBuffer(array.length);

		for(int i = length - 1; i >= 0; i--)
		{
			buffer.append(array[i]);
		}

		str = buffer.toString();

		return str;
	}

	public static void main (String[] args)
	{
		StringReverse stringReverse = new StringReverse();
		System.out.print(stringReverse.FirstReverse("abracadabra"));
	}
}
