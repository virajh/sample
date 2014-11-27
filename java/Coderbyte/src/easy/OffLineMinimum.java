package easy;

import java.util.PriorityQueue;
import java.util.Queue;

/*
 *	Using the Java language, have the function OffLineMinimum(strArr) take the strArr parameter being passed
 *	which will be an array of integers ranging from 1...n and the letter "E" and return the correct subset based on the following rules.
 *
 *	The input will be in the following format: ["I","I","E","I",...,"E",...,"I"] where the I's stand for integers
 *	and the E means take out the smallest integer currently in the whole set.
 *
 *	When finished, your program should return that new set with integers separated by commas.
 *
 *	For example: if strArr is ["5","4","6","E","1","7","E","E","3","2"] then your program should return 4,1,5.
 *
 *	Sample:-
 *		Input = "1","2","E","E","3"				Output = "1,2"
 *		Input = "4","E","1","E","2","E","3","E"	Output = "4,1,2,3"
 */

public class OffLineMinimum
{
	String getOffLineMinimum(String[] strArr)
	{
		StringBuffer stringBuffer = new StringBuffer();

		Queue<Integer> queue = new PriorityQueue<Integer>();

		for(String input: strArr)
		{
			if(Character.isDigit(input.charAt(0)))
				queue.add(Integer.parseInt(input));

			else if(input.equals("E"))
			{
				stringBuffer.append(queue.remove());
				stringBuffer.append(",");
			}
		}

		if(stringBuffer.charAt(stringBuffer.length() - 1) == ',')
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		return stringBuffer.toString().trim();
	}

	public static void main (String[] args)
	{
		OffLineMinimum underTest = new OffLineMinimum();

		System.out.println(underTest.getOffLineMinimum(new String[] {"1","2","E","E","3"}));

		System.out.println(underTest.getOffLineMinimum(new String[] {"4","E","1","E","2","E","3","E"}));
	}
}
