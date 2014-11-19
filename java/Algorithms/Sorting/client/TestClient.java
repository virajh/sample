package client;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import algorithms.BubbleSort;
import algorithms.InsertionSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.SelectionSort;
import algorithms.Shellsort;
import algorithms.Sort;

public class TestClient {

	private final static int SIZE = 100000;


	public static void main(String[] args)
	{

		List<Sort> sortAlgorithms = new LinkedList<Sort>();

		Collections.addAll(
				sortAlgorithms,
				new QuickSort(SIZE),
				new MergeSort(SIZE),
				new Shellsort(SIZE),
				new SelectionSort(SIZE),
				new InsertionSort(SIZE),
				new BubbleSort(SIZE)
				);

		for(Sort algorithm: sortAlgorithms)
			System.out.println("Client: "+isSorted(algorithm.sort())+"\n");
	}


	private static boolean isSorted(int[] numbers)
	{
		if (numbers == null)
			return true;

		int size = numbers.length;

		for(int i = 0; i < size - 1; i++)
		{
			if(numbers[i] > numbers[i+1])
				return false;
		}

		return true;
	}

}
