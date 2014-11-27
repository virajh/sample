package easy;

import java.util.Stack;

/*
 * Using the Java language, have the function SimpleSymbols(str) take the str parameter being passed
 * 	and determine if it is an acceptable sequence by either returning the string true or false.
 *
 * 	The str parameter will be composed of + and = symbols with several letters between them (ie. ++d+===+c++==a)
 * 	and for the string to be true each letter must be surrounded by a + symbol. So the string to the left would be false.
 *
 * 	The string will not be empty and will have at least one letter.
 *
 *	Sample:-
 *	 	Input = "+d+=3=+s+"		Output = "true"
 *		Input = "f++d+"			Output = "false"
 */

public class SimpleSymbol {

	String checkString(String string)
	{
		String yes = "true";
		String no = "false";

		Stack<Character> stack = new Stack<Character>();

		char[] charArray = string.toCharArray();

		for(char ch: charArray)
		{

			if(Character.isLetter(ch))
			{
				if(!stack.isEmpty() && stack.peek().equals('+'))
					stack.push(ch);
				else
					return no;
			}

			if(ch == '+')
			{
				if(stack.isEmpty() || !Character.isLetter(stack.peek()))
					stack.push(ch);

				else if(Character.isLetter(stack.peek()))
				{
					stack.push(ch);
				}

				else
					return no;
			}

			if(ch == '=')
			{
				if(!stack.isEmpty() && Character.isLetter(stack.peek()))
					return no;
			}
		}

		return yes;
	}

	public static void main(String[] args)
	{
		SimpleSymbol simpleSymbol = new SimpleSymbol();

		System.out.println(simpleSymbol.checkString("+d+=3=+s+"));

		System.out.println(simpleSymbol.checkString("f++d+"));

		System.out.println(simpleSymbol.checkString("++d+===+c++==a"));

		System.out.println(simpleSymbol.checkString("+z+z+z+"));

		System.out.println(simpleSymbol.checkString("+a="));

		System.out.println(simpleSymbol.checkString("2+a+a+"));

		System.out.println(simpleSymbol.checkString("+z+z+==+a+"));
	}

}
