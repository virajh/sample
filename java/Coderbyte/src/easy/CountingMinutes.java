package easy;

/*
 *	Using the Java language, have the function CountingMinutesI(str) take the str parameter being passed
 *	which will be two times (each properly formatted with a colon and am or pm) separated by a hyphen
 *	and return the total number of minutes between the two times.
 *
 *	The time will be in a 12 hour clock format.
 *
 * 	For example: if str is 9:00am-10:00am then the output should be 60. If str is 1:00pm-11:00am the output should be 1320.
 *
 * 	Sample:-
 *  		Input = "12:30pm-12:00am"	Output = 690
 *  		Input = "1:23am-1:08am"		Output = 1425
 */

public class CountingMinutes {

	int countMinutes(String str)
	{
		String[] times = str.split("-");

		int minutes = 0;

		String dayNight1 = times[0].substring(times[0].length() - 2);

		String dayNight2 = times[1].substring(times[1].length() - 2);

		if(!dayNight1.equals(dayNight2))
			minutes = 720;

		String hour1 = times[0].substring(0, times[0].indexOf(":"));

		String hour2 = times[1].substring(0, times[1].indexOf(":"));

		String minute1 = times[0].substring(times[0].indexOf(":") + 1, times[0].length() - 2);

		String minute2 = times[1].substring(times[1].indexOf(":") + 1, times[1].length() - 2);

		int time1 = Integer.parseInt(hour1) * 60 + Integer.parseInt(minute1);

		int time2 = Integer.parseInt(hour2) * 60 + Integer.parseInt(minute2);

		minutes += time2 - time1;

		if(Integer.signum(minutes) == -1)
			return 1440+minutes;

		return minutes;
	}

	public static void main(String[] args)
	{
		CountingMinutes underTest = new CountingMinutes();

		System.out.println(underTest.countMinutes("9:30am-10:31am"));

		System.out.println(underTest.countMinutes("1:00pm-11:00am"));

		System.out.println(underTest.countMinutes("12:30pm-12:00am"));

		System.out.println(underTest.countMinutes("1:23am-1:08am"));
	}
}
