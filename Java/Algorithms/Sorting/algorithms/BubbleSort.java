package algorithms;

public class BubbleSort extends Sort {

	private final int[] numbers;


	public BubbleSort(int size)
	{
		this.numbers = getArray(size);
	}


	public int[] sort()
	{
		long start = System.currentTimeMillis();

		bubbleSort();

		long stop = System.currentTimeMillis();

		System.out.println("Bubble sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}


	private void bubbleSort()
	{
		int size = numbers.length;

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size-1; j++)
			{
				if(numbers[j] > numbers[j+1])
					swap(numbers, j, j+1);
			}
		}
	}

}
