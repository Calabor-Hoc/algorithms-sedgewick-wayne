package chapter2.section4.study.common;

public class IntNode extends Node<Integer> {
	public IntNode(Integer data) {
		super(data);
	}

	public void setData(Integer data) {
		System.out.println("MyNode.setData");
		super.setData(data);
	}

	public static void main(String[] args) {
		IntNode mn = new IntNode(5);
		Node n = mn;            // A raw type - compiler throws an unchecked warning
		n.setData("Hello");
		Integer x = mn.data;    // Causes a ClassCastException to be thrown.
	}

}

