package easy;

/*
 *	Using the Java language, have the function ExOh(str) take the str parameter being passed
 *	and return the string true if there is an equal number of x's and o's, otherwise return the string false.
 *
 *	Only these two letters will be entered in the string, no punctuation or numbers.
 *
 *	For example: if str is "xooxxxxooxo" then the output should return false because there are 6 x's and 5 o's.
 */

public class ExOh {

	String process(String str)
	{
		int length = str.length();

		int count = 0;

		int i = 0;

		while(i < length)
		{
			char ch = str.charAt(i);

			if(ch == 'x')
				count++;
			else
				count--;

			i++;
		}

		if(count == 0)
			return "true";
		else
			return "false";
	}

	public static void main (String[] args) {

		ExOh object = new ExOh();

		System.out.println(object.process("xoxoxoxo"));

		System.out.println(object.process("oooxxx"));

		System.out.println(object.process("xo"));

		System.out.println(object.process("xoo"));

		System.out.println(object.process("xx"));

		System.out.println(object.process("o"));

	}
}
