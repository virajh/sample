package algorithms;

public class Shellsort extends Sort {

	private final int[] numbers;


	public Shellsort(int size)
	{
		this.numbers = getArray(size);
	}


	public int[] sort()
	{
		long start = System.currentTimeMillis();

		shellSort();

		long stop = System.currentTimeMillis();

		System.out.println("Shell sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}


	private void shellSort()
	{
		int size = numbers.length;

		int h = 1;

		while(h < size / 3)
			h = 3 * h + 1;

		while(h >= 1)
		{
			for(int i = h; i < size; i++)
			{
				for(int j = i; j >= h && numbers[j] < numbers[j-h]; j -= h)
					swap(numbers, j, j-h);
			}

			h = h / 3;
		}
	}
}
