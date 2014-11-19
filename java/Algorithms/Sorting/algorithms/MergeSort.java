package algorithms;

public class MergeSort extends Sort {

	private final int[] numbers;


	public MergeSort(int size) {
		this.numbers = getArray(size);
	}


	public int[] sort() {
		long start = System.currentTimeMillis();

		mergeSort();

		long stop = System.currentTimeMillis();

		System.out.println("Merge sort: "+(double)(stop-start)/1000.0+" seconds.");

		return numbers;
	}


	private void mergeSort()
	{
		mergeSort(numbers, new int[numbers.length], 0, numbers.length-1);
	}


	private void mergeSort(int[] array, int[] aux, int low, int high)
	{
		if (high <= low)
			return;

		int mid = low + (high - low) / 2;

		mergeSort(array, aux, low, mid);
		mergeSort(array, aux, mid + 1, high);
		merge(array, aux, low, mid, high);
	}


	private void merge(int[] array, int[] aux, int low, int mid, int high)
	{
		for(int k = low; k <= high; k++)
			aux[k] = array[k];

		int i = low;
		int j = mid+1;

		for(int k = low; k <= high;k++)
		{
			if (i > mid)
				array[k] = aux[j++];

			else if (j > high)
				array[k] = aux[i++];

			else if (aux[i] > aux[j])
				array[k] = aux[j++];

			else
				array[k] = aux[i++];
		}
	}

}
