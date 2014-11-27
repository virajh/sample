package easy;

import java.util.Arrays;

/*
 *	Using the Java language, have the function AlphabetSoup(str) take the str string parameter being passed
 *	and return the string with the letters in alphabetical order (ie. hello becomes ehllo).
 *
 *	Assume numbers and punctuation symbols will not be included in the string.
 */

public class AlphabetSoup {

	String makeSoup(String str)
	{
		char[] charArray = str.toCharArray();

		Arrays.sort(charArray);

		StringBuffer buffer = new StringBuffer();

		for(char ch: charArray)
			buffer.append(ch);

		return buffer.toString();
	}


	public static void main(String[] args)
	{
		AlphabetSoup soup = new AlphabetSoup();

		System.out.println(soup.makeSoup("hello"));

		System.out.println(soup.makeSoup("coderbyte"));

		System.out.println(soup.makeSoup("hooplah"));
	}
}
