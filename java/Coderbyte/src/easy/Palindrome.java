package easy;

/*
 *	Using the Java language, have the function Palindrome(str) take the str parameter being passed
 *	and return the string true if the parameter is a palindrome,
 *	(the string is the same forward as it is backward) otherwise return the string false.
 *
 *	For example: "racecar" is also "racecar" backwards. Punctuation and numbers will not be part of the string.
 */

public class Palindrome {

	String checkPalindrome(String str)
	{
		StringBuffer buffer = new StringBuffer();

		for(char ch: str.toCharArray())
		{
			if(Character.isDigit(ch) || Character.isLetter(ch))
				buffer.append(ch);
		}

		int length = buffer.length();

		for(int i = 0; i < length / 2; i++)
		{
			if(buffer.charAt(i) != buffer.charAt(length - ( i + 1 )))
				return "false";
		}

		return "true";
	}

	public static void main (String[] args)
	{
		Palindrome object = new Palindrome();

		System.out.println(object.checkPalindrome("aba"));

		System.out.println(object.checkPalindrome("abbfa"));

		System.out.println(object.checkPalindrome("bab"));

		System.out.println(object.checkPalindrome("racecar"));

		System.out.println(object.checkPalindrome("ahqg"));
	}
}
