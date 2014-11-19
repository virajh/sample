package algorithms;

public class QuickSort extends Sort{

	private final int[] numbers;

	public QuickSort(int size)
	{
		this.numbers = getArray(size);
	}

	public int[] sort() {
		long start = System.currentTimeMillis();

		quickSort();

		long stop = System.currentTimeMillis();

		System.out.println("Quick sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}

	private void quickSort()
	{
		shuffle(numbers);
		quickSort(numbers, 0, numbers.length - 1);
	}

	private void quickSort(int[] array, int low, int high)
	{
		if(high <= low)
			return;

		int pivot = partition(array, low, high);

		quickSort(array, low, pivot - 1);
		quickSort(array, pivot + 1, high);
	}

	private int partition(int[] array, int low, int high)
	{
		int i = low; int j = high + 1;

		while(true)
		{
			while(array[++i] < array[low])
				if(i == high)
					break;

			while(array[--j] > array[low])
				if(j == low)
					break;

			if(i >= j)
				break;

			swap(array, i, j);
		}

		swap(array, low, j);

		return j;
	}
}
