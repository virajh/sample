package easy;

/*
 * Have the function FirstFactorial(num) take the num parameter being passed
 * and return the factorial of it (ie. if num = 4, return (4 * 3 * 2 * 1)).
 *
 * For the test cases, the range will be between 1 and 18.
 */

public class FirstFactorial {

	long getFactorial(int num)
	{
		if(num == 0)
			return 0;

		if(num == 1)
			return 1;

		return num*getFactorial(num-1);
	}

	public static void main (String[] args) {
		// keep this function call here
		//		Scanner  s = new Scanner(System.in);
		FirstFactorial firstFactorial = new FirstFactorial();
		System.out.print(firstFactorial.getFactorial(18));
	}
}
