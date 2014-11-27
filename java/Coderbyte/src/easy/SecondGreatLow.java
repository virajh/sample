package easy;

import java.util.Arrays;

/*
 *	Using the Java language, have the function SecondGreatLow(arr) take the array of numbers stored in arr
 *	and return the second lowest and second greatest numbers, respectively, separated by a space.
 *
 *	For example: if arr contains [7, 7, 12, 98, 106] the output should be 12 98.
 *
 *	The array will not be empty and will contain at least 2 numbers. It can get tricky if there's just two numbers!
 *
 *	Sample:-
 * 		Input = 1, 42, 42, 180	Output = "42 42"
 * 		Input = 4, 90			Output = "90 4"
 */

public class SecondGreatLow {

	String getNumbers(int[] arr)
	{
		Arrays.sort(arr);

		int length = arr.length;

		if(length == 2)
		{
			return arr[1]+" "+arr[0];
		}

		int greatest = arr[length - 1];

		int lowest = arr[0];

		StringBuffer buffer = new StringBuffer();

		for(int i = 1; i < length; i++)
		{
			if(arr[i] > lowest)
			{
				buffer.append(arr[i]);
				break;
			}
		}

		buffer.append(" ");

		for(int i = length - 2; i >= 0; i--)
		{
			if(arr[i] < greatest)
			{
				buffer.append(arr[i]);
				break;
			}
		}

		return buffer.toString();
	}

	public static void main (String[] args)
	{
		SecondGreatLow object = new SecondGreatLow();

		System.out.println(object.getNumbers(new int[] {7, 7, 12, 98, 106}));

		System.out.println(object.getNumbers(new int[] {1, 42, 42, 180}));

		System.out.println(object.getNumbers(new int[] {4, 90}));

		System.out.println(object.getNumbers(new int[] {80, 80}));
	}
}
