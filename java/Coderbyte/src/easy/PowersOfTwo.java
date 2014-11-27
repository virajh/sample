package easy;

/*
 *	Using the Java language, have the function PowersofTwo(num) take the num parameter being passed which will be an integer
 *	and return the string true if it's a power of two.
 *
 *	If it's not return the string false.
 *
 *	For example if the input is 16 then your program should return the string true
 *	but if the input is 22 then the output should be the string false.
 */

public class PowersOfTwo {

	String check(int num)
	{
		if( (num & (num - 1)) == 0)
			return "true";

		else
			return "false";
	}

	public static void main (String[] args)
	{
		PowersOfTwo underTest = new PowersOfTwo();

		System.out.println(underTest.check(16));

		System.out.println(underTest.check(22));
	}
}
