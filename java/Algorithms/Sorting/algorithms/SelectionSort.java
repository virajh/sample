package algorithms;

public class SelectionSort extends Sort{

	private final int[] numbers;


	public SelectionSort(int size)
	{
		this.numbers = getArray(size);
	}


	public int[] sort()
	{
		long start = System.currentTimeMillis();

		selectionSort();

		long stop = System.currentTimeMillis();

		System.out.println("Selection sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}


	private void selectionSort()
	{
		int size = numbers.length;

		for(int i = 0; i < size; i++)
		{
			int min = i;

			for(int j = i+1; j<size; j++)
			{
				if(numbers[j] < numbers[min])
					min = j;
			}

			swap(numbers, i, min);
		}
	}
}
