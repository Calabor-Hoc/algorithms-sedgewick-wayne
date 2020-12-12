package chapter2.section2;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by Rene Argento on 18/02/17.
 */
//Good explanation here: http://www.geeksforgeeks.org/counting-inversions/
//Related to the Kendall tau distance: https://en.wikipedia.org/wiki/Kendall_tau_distance
public class Exercise19_Inversions_My {

	public static void main(String[] args) {

		Comparable[] array1 = generateArray1();

		int numberOfInversions1 = countInversions(array1);

		StdOut.println("Number of inversions 1: " + numberOfInversions1 + "\nExpected: 25");

		Comparable[] array2 = generateArray2();

		int numberOfInversions2 = countInversions(array2);

		StdOut.println("Number of inversions 2: " + numberOfInversions2 + "\nExpected: 6");
	}

	private static Comparable[] generateArray1() {
		Comparable[] array = new Comparable[10];

		array[0] = 10; //9 inversions
		array[1] = 9; //7 inversions
		array[2] = 8; //5 inversions
		array[3] = 7; //3 inversions
		array[4] = 6; //1 inversion
		array[5] = 5; //0 inversions
		array[6] = 6; //0 inversions
		array[7] = 7; //0 inversions
		array[8] = 8; //0 inversions
		array[9] = 9; //0 inversions

		//Total: 25 inversions

		return array;
	}

	private static Comparable[] generateArray2() {

		Comparable[] array = new Comparable[4];

		array[0] = 4; //3 inversions
		array[1] = 3; //2 inversions
		array[2] = 2; //1 inversions
		array[3] = 1; //0 inversions

		//Total: 6 inversions

		return array;
	}

	private static int countInversions(Comparable[] array) {

		//Used to not modify the original array
		Comparable[] copy = Arrays.copyOf(array, array.length);
		Comparable[] aux = new Comparable[copy.length];
		return countInv(copy, aux, 0, array.length - 1);

	}

	private static int countInv(Comparable[] array, Comparable[] aux, int lo, int hi) {
		if (lo == hi) {
			return 0;
		}
		int mid = lo + (hi - lo) / 2;
		int leftCount = countInv(array, aux, lo, mid);
		int rightCount = countInv(array, aux, mid + 1, hi);
		return leftCount + rightCount + mergeAndReturnCount(array, aux, lo,mid, hi);
	}

	private static int mergeAndReturnCount(Comparable[] array, Comparable[] aux, int lo,int mid, int hi) {
		System.arraycopy(array, lo, aux, lo, hi - lo + 1);
		int invDuringMerge =0;
		int lHalf = lo;
		int rHalf = mid + 1;
		int idx = lo;
		while (idx <= hi) {
			if (lHalf > mid) {
				array[idx++] = aux[rHalf++];
			} else if (rHalf > hi) {
				array[idx++] = aux[lHalf++];
			}else if (aux[lHalf].compareTo(aux[rHalf]) <= 0) {
				array[idx++] = aux[lHalf++];
			}else{
				array[idx++] = aux[rHalf++];
				invDuringMerge+=mid-lHalf+1;
			}
		}
		return invDuringMerge;
	}

}
