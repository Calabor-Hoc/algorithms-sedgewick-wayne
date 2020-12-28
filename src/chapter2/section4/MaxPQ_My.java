package chapter2.section4;

import edu.princeton.cs.algs4.StdOut;
import util.ArrayGenerator;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ_My<Key extends Comparable<Key>> implements Iterable<Key> {
	private Key[] pq;                    // store items at indices 1 to n
	private int n;                       // number of items on priority queue
	private Comparator<Key> comparator;  // optional comparator
	private final Class<?> eleType;  // optional comparator

	/**
	 * Initializes an empty priority queue with the given initial capacity.
	 *
	 * @param initCapacity the initial capacity of this priority queue
	 */
	@SuppressWarnings("unchecked")
	public MaxPQ_My(Class<Key> c, int initCapacity) {
		this.eleType = c;
		pq = (Key[]) Array.newInstance(c, initCapacity + 1);
		n = 0;
	}

	/**
	 * Initializes an empty priority queue.
	 */
	public MaxPQ_My(Class<Key> c) {
		this(c, 1);
	}

	/**
	 * Initializes an empty priority queue with the given initial capacity,
	 * using the given comparator.
	 *
	 * @param initCapacity the initial capacity of this priority queue
	 * @param comparator   the order in which to compare the keys
	 */
	public MaxPQ_My(Class<Key> c, int initCapacity, Comparator<Key> comparator) {
		this.eleType = c;
		this.comparator = comparator;
		pq = (Key[]) Array.newInstance(c, initCapacity + 1);
		n = 0;
	}

	/**
	 * Initializes an empty priority queue using the given comparator.
	 *
	 * @param comparator the order in which to compare the keys
	 */
	public MaxPQ_My(Class<Key> c, Comparator<Key> comparator) {
		this(c, 1, comparator);
	}

	/**
	 * Initializes a priority queue from the array of keys.
	 * Takes time proportional to the number of keys, using sink-based heap construction.
	 *
	 * @param keys the array of keys
	 */
	public MaxPQ_My(Class<Key> c, Key[] keys) {
		this.eleType = c;
		n = keys.length;
		pq = (Key[]) Array.newInstance(c, keys.length + 1);
		System.arraycopy(keys, 0, pq, 1, n);
		for (int k = n / 2; k >= 1; k--)
			sink(k);
		assert isMaxHeap();
	}


	/**
	 * Returns true if this priority queue is empty.
	 *
	 * @return {@code true} if this priority queue is empty;
	 * {@code false} otherwise
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns a largest key on this priority queue.
	 *
	 * @return a largest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public Key max() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}

	// helper function to double the size of the heap array
	private void resize(int capacity) {
		assert capacity > n;
		Key[] temp = (Key[]) Array.newInstance(eleType, capacity);
		if (n >= 0)
			System.arraycopy(pq, 1, temp, 1, n);
		pq = temp;
	}


	/**
	 * Adds a new key to this priority queue.
	 *
	 * @param x the new key to add to this priority queue
	 */
	public void insert(Key x) {

		// double size of array if necessary
		if (n == pq.length - 1)
			resize(2 * pq.length);

		// add x, and percolate it up to maintain heap invariant
		pq[++n] = (Key) eleType.cast(x);
		swim(n);
		assert isMaxHeap();
	}

	/**
	 * Removes and returns a largest key on this priority queue.
	 *
	 * @return a largest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public Key delMax() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		Key max = pq[1];
		exch(1, n--);
		sink(1);
		pq[n + 1] = null;     // to avoid loitering and help with garbage collection
		if ((n > 0) && (n == (pq.length - 1) / 4))
			resize(pq.length / 2);
		assert isMaxHeap();
		return max;
	}


	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k / 2, k);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (k <= n / 2) {
			int child;
			final int leftChild = 2 * k;
			final int rightChild = leftChild + 1;
			if (rightChild > n) {
				child = leftChild;
			} else {
				child = less(leftChild, rightChild) ? rightChild : leftChild;
			}
			if (less(k, child)) {
				exch(k, child);
				k = child;
			} else {
				break;
			}
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		if (comparator == null) {
			return pq[i].compareTo(pq[j]) < 0;
		} else {
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	// is pq[1..n] a max heap?
	private boolean isMaxHeap() {
		for (int i = 1; i <= n; i++) {
			if (pq[i] == null)
				return false;
		}
		for (int i = n + 1; i < pq.length; i++) {
			if (pq[i] != null)
				return false;
		}
		if (pq[0] != null)
			return false;
		return isMaxHeapOrdered(1);
	}

	// is subtree of pq[1..n] rooted at k a max heap?
	private boolean isMaxHeapOrdered(int k) {
		if (k > n)
			return true;
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= n && less(k, left))
			return false;
		if (right <= n && less(k, right))
			return false;
		return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
	}


	/***************************************************************************
	 * Iterator.
	 ***************************************************************************/

	/**
	 * Returns an iterator that iterates over the keys on this priority queue
	 * in descending order.
	 * The iterator doesn't implement {@code remove()} since it's optional.
	 *
	 * @return an iterator that iterates over the keys in descending order
	 */
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Key> {

		// create a new pq
		private MaxPQ_My<Key> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator == null)
				copy = new MaxPQ_My(eleType, size());
			else
				copy = new MaxPQ_My(eleType, size(), comparator);
			for (int i = 1; i <= n; i++)
				copy.insert(pq[i]);
		}

		public boolean hasNext() {
			return !copy.isEmpty();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Key next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.delMax();
		}

	}

	/**
	 * Unit tests the {@code MaxPQ} data type.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		final Double[] doubleArr1 = (Double[]) ArrayGenerator.generateRandomArray(50);
		MaxPQ_My<Double> pq1 = new MaxPQ_My<>(Double.class);
		for (Comparable<Double> d : doubleArr1) {
			pq1.insert((Double) d);
		}
		StdOut.println("(" + pq1.size() + " left on pq)");
		for (Double aDouble : pq1) {
			StdOut.println("dddd:" + aDouble);
		}
	}

}