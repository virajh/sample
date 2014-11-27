package easy;

/*
 * Using the Java language, have the function LetterCapitalize(str) take the str parameter being passed
 * and capitalize the first letter of each word.
 *
 * Words will be separated by only one space.
 *
 * Sample:-
 * 		Input = "hello world"	Output = "Hello World"
 * 		Input = "i ran there"	Output = "I Ran There"
 */

public class LetterCapitalize {

	String capitalizeLetters(String str)
	{
		String[] words = str.split("\\s");

		StringBuffer result = new StringBuffer();

		for(String string: words)
		{
			result.append(Character.toUpperCase(string.charAt(0)));

			result.append(string.substring(1));

			result.append(" ");
		}

		str = result.toString().trim();

		return str;
	}

	public static void main (String[] args)
	{
		// keep this function call here
		//		Scanner  s = new Scanner(System.in);
		//		Function c = new Function();
		//		System.out.print(c.LetterCapitalize(s.nextLine()));

		LetterCapitalize letter = new LetterCapitalize();

		String input1 = "i ran there";
		String input2 = "hello world";
		String input3 = "I am some Caps Already";

		String output1 = letter.capitalizeLetters(input1);
		String output2 = letter.capitalizeLetters(input2);
		String output3 = letter.capitalizeLetters(input3);

		System.out.println(output1.equals("I Ran There"));
		System.out.println(output2.equals("Hello World"));
		System.out.println(output3.equals("I Am Some Caps Already"));

	}
}
