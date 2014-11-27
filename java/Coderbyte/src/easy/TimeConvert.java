package easy;

/*
 *  Using the Java language, have the function TimeConvert(num) take the num parameter being passed
 *  and return the number of hours and minutes the parameter converts to (ie. if num = 63 then the output should be 1:3).
 *
 *  Separate the number of hours and minutes with a colon.
 */

public class TimeConvert
{

	String convertToTime(int num)
	{
		int hours = num / 60;
		int minutes = num % 60;

		return hours+":"+minutes;
	}


	public static void main (String[] args)
	{
		TimeConvert timeConvert = new TimeConvert();

		System.out.println(timeConvert.convertToTime(126));

		System.out.println(timeConvert.convertToTime(45));
	}
}
