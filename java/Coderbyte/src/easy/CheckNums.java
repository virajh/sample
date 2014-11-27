package easy;

/*
 *	Using the Java language, have the function CheckNums(num1,num2) take both parameters being passed
 *	and return the string true if num2 is greater than num1, otherwise return the string false.
 *
 *	If the parameter values are equal to each other then return the string -1.
 */

public class CheckNums {


	String checkNumbers(int num1, int num2)
	{
		if(num1 == num2)
			return "-1";
		else if (num1 < num2)
			return "true";
		else
			return "false";
	}


	public static void main(String[] args)
	{
		CheckNums checkNums = new CheckNums();

		System.out.println(checkNums.checkNumbers(3, 122));

		System.out.println(checkNums.checkNumbers(10, 10));

		System.out.println(checkNums.checkNumbers(52, 12));
	}

}
