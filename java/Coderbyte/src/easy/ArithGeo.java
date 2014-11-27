package easy;

/*
 *	Using the Java language, have the function ArithGeo(arr) take the array of numbers stored in arr
 *	and return the string "Arithmetic" if the sequence follows an arithmetic pattern
 *	or return "Geometric" if it follows a geometric pattern.
 *
 *	If the sequence doesn't follow either pattern return -1.
 *	An arithmetic sequence is one where the difference between each of the numbers is consistent,
 *	where as in a geometric sequence, each term after the first is multiplied by some constant or common ratio.
 *
 *	Arithmetic example: [2, 4, 6, 8] and Geometric example: [2, 6, 18, 54].
 *
 *	Negative numbers may be entered as parameters, 0 will not be entered, and no array will contain all the same elements.
 *
 *	Sample:-
 * 			Input = 5,10,15		Output = "Arithmetic"
 * 			Input = 2,4,16,24	Output = -1
 */

public class ArithGeo {

	String checkMath(int[] arr)
	{
		boolean arithmetic = true;

		int length = arr.length;

		int difference = arr[1] - arr[0];

		for(int i = 1; i < length - 1; i++)
		{
			if(arr[i + 1] - arr[i] != difference)
			{
				arithmetic = false;
				break;
			}
		}

		if(arithmetic)
			return "Arithmetic";

		boolean geometric = true;

		int quotient = arr[1] / arr[0];

		for(int i = 1; i < length - 1; i++)
		{
			if(arr[i + 1] / arr[i] != quotient)
			{
				geometric = false;
				break;
			}
		}

		if(geometric)
			return "Geometric";
		else
			return "-1";
	}

	public static void main (String[] args)
	{
		ArithGeo object = new ArithGeo();

		System.out.println(object.checkMath(new int[] {1, 2, 3, 4}));

		System.out.println(object.checkMath(new int[] {2, 6, 18, 54}));

		System.out.println(object.checkMath(new int[] {2, 4, 16, 24}));

		System.out.println(object.checkMath(new int[] {2, -4, 8, -16}));
	}
}
