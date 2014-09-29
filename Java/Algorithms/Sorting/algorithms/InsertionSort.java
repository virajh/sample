package algorithms;

public class InsertionSort extends Sort {

	private final int[] numbers;

	public InsertionSort(int size)
	{
		this.numbers = getArray(size);
	}


	public int[] sort()
	{
		long start = System.currentTimeMillis();

		insertionSort();

		long stop = System.currentTimeMillis();

		System.out.println("Insertion sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}


	private void insertionSort()
	{
		int size = numbers.length;

		for(int i = 0; i < size; i++)
		{
			for(int j = i; j > 0; j--)
			{
				if(numbers[j] < numbers[j-1])
					swap(numbers, j, j-1);

				else
					break;
			}
		}
	}
}
