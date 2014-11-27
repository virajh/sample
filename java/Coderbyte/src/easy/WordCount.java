package easy;

/*
 * 	Using the Java language, have the function WordCount(str) take the str string parameter being passed
 * 	and return the number of words the string contains (ie. "Never eat shredded wheat" would return 4).
 *
 * 	Words will be separated by single spaces.
 */

public class WordCount {

	int countWords(String str)
	{
		return str.split("\\s").length;
	}


	public static void main(String[] args)
	{
		WordCount wordCount = new WordCount();

		System.out.println(wordCount.countWords("Hello world"));

		System.out.println(wordCount.countWords("one 222 three three three"));
	}
}
