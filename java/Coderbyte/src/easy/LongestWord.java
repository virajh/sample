package easy;

/*
 *  Using the Java language, have the function LongestWord(sen) take the sen parameter being passed
 *  and return the largest word in the string.
 *
 *  If there are two or more words that are the same length,
 *  return the first word from the string with that length.
 *
 *  Ignore punctuation and assume sen will not be empty.
 */

public class LongestWord {


	String getLongestWord(String sentence)
	{
		String[] wordArray = sentence.split("\\W");

		int length = wordArray.length;

		for(int i = 0; i < length - 1; i++)
		{
			if(wordArray[i].length() >= wordArray[i+1].length())
			{
				wordArray[i+1] = wordArray[i];
			}
		}

		return wordArray[length - 1];
	}


	public static void main(String[] args)
	{
		LongestWord longestWord = new LongestWord();

		System.out.println(longestWord.getLongestWord("This sentence different1 , has ?many -a word &of different2 sizes."));

		System.out.println(longestWord.getLongestWord("sentence3 sentence2 this sentence1"));

		System.out.println(longestWord.getLongestWord("fun&!! time"));

		System.out.println(longestWord.getLongestWord("i love dogs"));
	}

}
