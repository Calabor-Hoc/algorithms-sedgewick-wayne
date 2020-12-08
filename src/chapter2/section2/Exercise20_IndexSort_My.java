package chapter2.section2;

import edu.princeton.cs.algs4.StdOut;
import util.ArrayGenerator;

/**
 * Created by Rene Argento on 18/02/17.
 */
//Based on http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
public class Exercise20_IndexSort_My {

	public static void main(String[] args) {

		Comparable[] array1 = ArrayGenerator.generateRandomArray(1000000);
		int[] indexSortedArray1 = Exercise20_IndexSort_My.indexSort(array1);

		int[] expectedIndexSortedArray1 = Exercise20_IndexSort.indexSort(array1);
		if (validate(expectedIndexSortedArray1, indexSortedArray1)) {
			StdOut.println("Index sorted OK");
		} else {
			StdOut.println("Index sorted NOT OK");
			throw new IllegalStateException("Index sorted NOT OK");
		}
		StdOut.println("Expected: Index sorted OK\n");

		Comparable[] array2 = generateArray2();
		int[] indexSortedArray2 = indexSort(array2);

		int[] expectedIndexSortedArray2 = {3, 2, 1, 0};
		if (validate(expectedIndexSortedArray2, indexSortedArray2)) {
			StdOut.println("Index sorted OK");
		} else {
			StdOut.println("Index sorted NOT OK");
		}
		StdOut.println("Expected: Index sorted OK");
	}

	private static Comparable[] generateArray1() {
		Comparable[] array = new Comparable[10];

		array[0] = 10; //Correct index: 9
		array[1] = 9; //Correct index: 7
		array[2] = 8; //Correct index: 5
		array[3] = 7; //Correct index: 3
		array[4] = 6; //Correct index: 1
		array[5] = 5; //Correct index: 0
		array[6] = 6; //Correct index: 2
		array[7] = 7; //Correct index: 4
		array[8] = 8; //Correct index: 6
		array[9] = 9; //Correct index: 8

		//Expected: [5, 4, 6, 3, 7, 2, 8, 1, 9, 0]

		return array;
	}

	private static Comparable[] generateArray2() {

		Comparable[] array = new Comparable[4];

		array[0] = 4; //Correct index: 3
		array[1] = 3; //Correct index: 2
		array[2] = 2; //Correct index: 1
		array[3] = 1; //Correct index: 0

		//Expected: [3, 2, 1, 0]

		return array;
	}

	public static int[] indexSort(Comparable[] array) {
		final int N = array.length;
		final int[] result = new int[N];
		final int[] auxArray = new int[N];
		recursiveIndexSort(array, 0, N-1, result, auxArray);
		return result;
	}

	private static void recursiveIndexSort(Comparable[] array, int lo, int hi, int[] result, int[] auxArray) {
		if (result.length - 1 < hi) {
			throw new IllegalArgumentException("The result array must big enough to hold the result");
		}
		if (lo == hi) {
			result[lo] = lo;
			return;
		}
		int mid = lo + (hi - lo) / 2;

		recursiveIndexSort(array, lo, mid, result, auxArray);
		recursiveIndexSort(array, mid + 1, hi, result, auxArray);
		mergeIndex(array, lo, mid, hi, result, auxArray);
	}

	private static void mergeIndex(Comparable[] array, int lo, int mid, int hi, int[] result, int[] auxArray) {
		System.arraycopy(result, lo, auxArray, lo, hi - lo + 1);
		int left = lo;
		int right = mid + 1;
		int idx = lo;

		while (idx <= hi) {
			if (left > mid) {
				result[idx++] = auxArray[right++];
			} else if (right > hi) {
				result[idx++] = auxArray[left++];
			} else if (array[auxArray[left]].compareTo(array[auxArray[right]]) <= 0) {
				result[idx++] = auxArray[left++];
			} else {
				result[idx++] = auxArray[right++];
			}
		}
	}


	private static boolean validate(int[] expectedIndexSortedArray, int[] indexSortedArray) {
		if (expectedIndexSortedArray.length != indexSortedArray.length) {
			return false;
		}

		for (int i = 0; i < expectedIndexSortedArray.length; i++) {
			if (expectedIndexSortedArray[i] != indexSortedArray[i]) {
				return false;
			}
		}

		return true;
	}

}
