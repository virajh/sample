package easy;

import java.util.Arrays;

/*
 *	Using the Java language, have the function ArrayAdditionI(arr) take the array of numbers stored in arr
 *	and return the string true if any combination of numbers in the array can be added up to equal the largest number in the array,
 *	otherwise return the string false.
 *
 *	For example: if arr contains [4, 6, 23, 10, 1, 3] the output should return true because 4 + 6 + 10 + 3 = 23.
 *
 *	The array will not be empty, will not contain all the same elements, and may contain negative numbers.
 */

public class ArrayAdditionOne
{

	String ArrayAdditionI(int[] arr)
	{
		Arrays.sort(arr);

		int length = arr.length;

		int sum = 0;

		for(int i = 0; i < length - 1; i++)
			sum += arr[i];

		if(sum == arr[length-1])
			return "true";
		else
			return "false";
	}

	public static void main (String[] args)
	{
		ArrayAdditionOne object = new ArrayAdditionOne();

		System.out.println(object.ArrayAdditionI(new int[]{1,2,3,4}));

		System.out.println(object.ArrayAdditionI(new int[]{54,49,1,0,7,4}));

		System.out.println(object.ArrayAdditionI(new int[]{3,4,5,7}));
	}
}
