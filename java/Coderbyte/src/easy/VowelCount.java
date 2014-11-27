package easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 	Using the Java language, have the function VowelCount(str) take the str string parameter being passed
 * 	and return the number of vowels the string contains (ie. "All cows eat grass" would return 5).
 *
 * 	Do not count y as a vowel for this challenge.
 */

public class VowelCount {

	int countVowels(String str)
	{
		Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

		int vowelCount = 0;

		int len = str.length();

		for (int i = 0; i < len; i++)
		{
			if(vowels.contains(Character.toLowerCase(str.charAt(i))))
				vowelCount++;
		}

		return vowelCount;
	}

	public static void main(String[] args)
	{
		VowelCount vowelCount = new VowelCount();

		System.out.println(vowelCount.countVowels("comma"));

		System.out.println(vowelCount.countVowels("yoop"));

		System.out.println(vowelCount.countVowels("All cows eat grass"));

	}

}
