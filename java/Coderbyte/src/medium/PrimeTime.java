package medium;

/*
 * 	Using the Java language, have the function PrimeTime(num) take the num parameter being passed
 * 	and return the string true if the parameter is a prime number, otherwise return the string false.
 *
 * 	The range will be between 1 and 2^16.
 */

public class PrimeTime {

	boolean checkPrime(int num)
	{
		if(num % 2 == 0 || num == 1)
			return false;

		for(int i = 3; i * i <= num; i += 2)
			if(num % i == 0)
				return false;

		return true;
	}

	public static void main(String[] args)
	{
		PrimeTime primeTime = new PrimeTime();

		System.out.println(primeTime.checkPrime(13));

		System.out.println(primeTime.checkPrime(12));

		System.out.println(primeTime.checkPrime(7));

		System.out.println(primeTime.checkPrime(1));
	}
}
