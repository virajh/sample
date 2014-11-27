package easy;

import java.util.Arrays;
import java.util.HashMap;

/*
 *	Using the Java language, have the function LetterCountI(str) take the str parameter being passed
 *	and return the first word with the greatest number of repeated letters.
 *
 *	For example: "Today, is the greatest day ever!" should return greatest because it has 2 e's (and 2 t's)
 *	and it comes before ever which also has 2 e's.
 *
 *	If there are no words with repeating letters return -1. Words will be separated by spaces.
 */

public class LetterCountOne {

	String countLetters(String str)
	{
		String[] words = str.split("\\s");

		String targetWord = "-1";

		int maxRepititions = 1;

		for(String word : words)
		{
			HashMap<Character, Integer> lengthSet = new HashMap<Character, Integer>();

			char[] charArray = word.toLowerCase().toCharArray();

			Arrays.sort(charArray);

			for(char ch: charArray)
			{
				if(lengthSet.containsKey(ch))
				{
					int value = lengthSet.get(ch);
					value = value + 1;
					lengthSet.replace(ch, value);

					if(value > maxRepititions)
					{
						maxRepititions = value;
						targetWord = word;
					}
				}
				else
					lengthSet.put(ch, 1);
			}
		}

		return targetWord;
	}

	public static void main (String[] args)
	{
		LetterCountOne lcOne = new LetterCountOne();

		System.out.println(lcOne.countLetters("Today, is the greatest day ever!"));

		System.out.println(lcOne.countLetters("Hello apple pie"));

		System.out.println(lcOne.countLetters("No words"));

		System.out.println(lcOne.countLetters("hello world"));

		System.out.println(lcOne.countLetters("no words here"));

		System.out.println(lcOne.countLetters("I lied before"));

		System.out.println(lcOne.countLetters("coderbyte"));

		System.out.println(lcOne.countLetters("the dog and cat"));

		System.out.println(lcOne.countLetters("yellow and red"));

		System.out.println(lcOne.countLetters("red none yellow"));

		System.out.println(lcOne.countLetters("letters galore"));

		System.out.println(lcOne.countLetters("a b c d ee"));

		System.out.println(lcOne.countLetters("a bat cat rat"));
	}
}
