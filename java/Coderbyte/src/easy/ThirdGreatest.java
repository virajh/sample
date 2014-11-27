package easy;

import java.util.LinkedList;
import java.util.List;

/*
 *	Using the Java language, have the function ThirdGreatest(strArr) take the array of strings stored in strArr
 *	and return the third largest word within in.
 *
 *	So for example: if strArr is ["hello", "world", "before", "all"] your output should be world because "before" is 6 letters long,
 *	and "hello" and "world" are both 5, but the output should be world because it appeared as the last 5 letter word in the array.
 *
 *	If strArr was ["hello", "world", "after", "all"] the output should be after because the first three words are all 5 letters long,
 *	so return the last one.
 *
 *	The array will have at least three strings and each string will only contain letters.
 *
 *	Sample:-
 * 		Input = "coder","byte","code"	Output = "code"
 * 		Input = "abc","defg","z","hijk"	Output = "abc"
 */

public class ThirdGreatest {

	String findThirdGreatest(String[] strArr)
	{
		int length = strArr.length;

		List<String> stringList = new LinkedList<String>();

		int count = 3;

		while(count != 0)
		{
			int maxLength = 0;

			int longestWord = -1;

			count--;

			for (int i = 0; i < length; i++)
			{
				if(strArr[i].length() > maxLength)
				{
					maxLength = strArr[i].length();
					longestWord = i;
				}
			}

			stringList.add(strArr[longestWord]);
			strArr[longestWord] = "";
		}

		return stringList.get(stringList.size() - 1);
	}

	public static void main (String[] args)
	{
		ThirdGreatest underTest = new ThirdGreatest();

		System.out.println(underTest.findThirdGreatest(new String[] {"coder","byte","code"}));

		System.out.println(underTest.findThirdGreatest(new String[] {"abc","defg","z","hijk"}));

		System.out.println(underTest.findThirdGreatest(new String[] {"hello", "world", "before", "all"}));

		System.out.println(underTest.findThirdGreatest(new String[] {"hello", "world", "after", "all"}));
	}
}
