package algorithms;

import java.util.Random;

public abstract class Sort {


	protected void swap (int[] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}


	protected void shuffle(int[] arr)
	{
		Random random = new Random();
		int len = arr.length;

		for(int i = 0; i < len; i++)
		{
			int k = random.nextInt(i+1);
			swap(arr, i, k);
		}
	}


	protected int[] getArray(int size)
	{
		int[] numbers = new int[size];

		Random random = new Random();

		for(int i=0; i < size; i++)
			numbers[i] = random.nextInt(size) + 1;

		return numbers;
	}


	public abstract int[] sort();
}
