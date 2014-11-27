package easy;

import java.util.HashMap;

/*
 *	Using the Java language, have the function MeanMode(arr) take the array of numbers stored in arr
 *	and return 1 if the mode equals the mean,
 *	 0 if they don't equal each other (ie. [5, 3, 3, 3, 1] should return 1 because the mode (3) equals the mean (3)).
 *
 *	The array will not be empty, will only contain positive integers, and will not contain more than one mode.
 */

public class MeanMode {

	int calculate(int[] arr)
	{
		int mean = mean(arr);

		int mode = mode(arr);

		if(mean == mode)
			return 1;
		else
			return 0;
	}

	private int mean(int[] arr)
	{
		int mean = 0;

		for(int i: arr)
			mean +=i;

		mean = mean/arr.length;

		return mean;
	}

	private int mode(int[] arr)
	{
		int count = 1;

		int mode = 0;

		HashMap<Integer, Integer> intMap = new HashMap<Integer, Integer>();

		for(int i: arr)
		{
			if(intMap.containsKey(i))
			{
				int value = intMap.get(i);

				value++;

				intMap.replace(i, value);

				if(value > count)
				{
					count = value;
					mode = i;
				}
			}
			else
				intMap.put(i, 1);
		}

		return mode;
	}

	public static void main (String[] args)
	{
		MeanMode meanMode = new MeanMode();

		System.out.println(meanMode.calculate(new int[] {5, 3, 3, 3, 1}));

		System.out.println(meanMode.calculate(new int[] {1, 2, 3}));

		System.out.println(meanMode.calculate(new int[] {4, 4, 4, 6, 2}));
	}
}
