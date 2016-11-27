package Chapter1.Section3.Stack;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Exercise5 {
	
	private static void decimalToBinary(int n) {
		Stack<Integer> stack = new Stack<>();
		
		while (n > 0) {
			stack.push(n % 2);
			n = n / 2;
		}
		
		for (int d : stack) {
			StdOut.print(d);
		}
		StdOut.println();
	}

	public static void main(String[] args) {
		decimalToBinary(50);
	}

}
