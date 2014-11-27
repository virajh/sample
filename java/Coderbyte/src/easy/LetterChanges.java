package easy;

/*
 * Using the Java language, have the function LetterChanges(str) take the str parameter being passed and modify it using the following algorithm.
 *
 * Replace every letter in the string with the letter following it in the alphabet (ie. c becomes d, z becomes a).
 *
 * Then capitalize every vowel in this new string (a, e, i, o, u) and finally return this modified string.
 *
 * Samples:-
 * 		Input = "hello*3"	Output = "Ifmmp*3"
 * 		Input = "fun times!"	Output = "gvO Ujnft!"
 */

public class LetterChanges {

	String changeLetters(String str)
	{
		char[] charArr = str.toCharArray();

		int length = charArr.length;

		StringBuffer stringBuffer = new StringBuffer(length);

		for(int i = 0; i < length; i++)
			stringBuffer.append(incrementChar(charArr[i]));

		return stringBuffer.toString();
	}

	private char incrementChar(char input)
	{
		int asciiValue = input - 0;

		if(asciiValue < 97 || asciiValue > 122)
			return input;

		if(asciiValue == 122)
			return 'A';
		else
			return capitalizeVowels((char) (asciiValue+1));
	}

	private char capitalizeVowels(char input)
	{
		if(input == 'e' || input == 'i' || input == 'o' || input == 'u')
			return Character.toUpperCase(input);
		else
			return input;
	}


	public static void main (String[] args)
	{
		LetterChanges letterChanges = new LetterChanges();

		System.out.println(letterChanges.changeLetters("abcdefghijklmn opqrs; $tuvwxyz"));
	}
}
