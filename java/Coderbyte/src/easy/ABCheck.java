package easy;

/*
 * 	Using the Java language, have the function ABCheck(str) take the str parameter being passed
 * 	and return the string true if the characters a and b are separated by exactly 3 places anywhere
 * 	in the string at least once (ie. "lane borrowed" would result in true because there is exactly
 * 	three characters between a and b).
 *
 * 	Otherwise return the string false.
 */

public class ABCheck {

	String check(String str)
	{
		int len = str.length();

		int i = 0;

		while(i < len)
		{
			try {
				if(str.charAt(i) == 'a' && str.charAt(i+4) == 'b' || str.charAt(i) == 'b' && str.charAt(i+4) == 'a')
					return "true";
			} catch (StringIndexOutOfBoundsException e)
			{
				return "false";
			}
			i++;
		}

		return "false";
	}

	public static void main(String[] args)
	{
		ABCheck underTest = new ABCheck();

		System.out.println(underTest.check("lane borrowed"));

		System.out.println(underTest.check("after badly"));

		System.out.println(underTest.check("bzzza"));

	}
}
