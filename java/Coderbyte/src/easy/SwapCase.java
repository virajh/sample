package easy;

/*
 *	Using the Java language, have the function SwapCase(str) take the str parameter and swap the case of each character.
 *
 *	For example: if str is "Hello World" the output should be hELLO wORLD. Let numbers and symbols stay the way they are.
 *
 *	Sample:-
 *		Input = "Hello-LOL"		Output = "hELLO-lol"
 *		Input = "Sup DUDE!!?"	Output = "sUP dude!!?"
 */

public class SwapCase {

	String swapCase(String str)
	{
		String[] words = str.split("\\s");

		StringBuffer buffer = new StringBuffer();

		for(String word: words)
		{
			char[] charArr = word.toCharArray();

			for(char ch: charArr)
			{
				buffer.append(switchCase(ch));
			}

			buffer.append(" ");
		}

		return buffer.toString().trim();
	}

	private char switchCase(char ch)
	{
		if(Character.isUpperCase(ch))
			return Character.toLowerCase(ch);
		else
			return Character.toUpperCase(ch);
	}

	public static void main (String[] args)
	{
		SwapCase underTest = new SwapCase();

		System.out.println(underTest.swapCase("Hello World"));

		System.out.println(underTest.swapCase("Hello-LOL"));

		System.out.println(underTest.swapCase("Sup DUDE!!?"));

		System.out.println(underTest.swapCase("Hello2 World"));

		System.out.println(underTest.swapCase("123gg))(("));
	}
}
