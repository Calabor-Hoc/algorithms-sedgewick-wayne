package Chapter1.Section3.LinkedList;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Exercise31_DoublyLinkedList<Item> implements Iterable<Item>{
	
	private class DoubleNode {
		Item item;
		DoubleNode previous;
		DoubleNode next;
	}
	
	private int size;
	private DoubleNode first;
	private DoubleNode last;
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}

	public void insertAtTheBeginning(Item item) {
		DoubleNode oldFirst = first;
		
		first = new DoubleNode();
		first.item = item;
		first.next = oldFirst;
		
		if (oldFirst != null) {
			oldFirst.previous = first;
		}
		
		//If the list was empty before adding the new item:
		if (last == null) {
			last = first;
		}

		size++;
	}
	
	public void insertAtTheEnd(Item item) {
		DoubleNode oldLast = last;
		
		last = new DoubleNode();
		last.item = item;
		last.previous = oldLast;
		
		if (oldLast != null) {
			oldLast.next = last;
		}

		//If the list was empty before adding the new item:
		if (first == null) {
			first = last;
		}

		size++;
	}

	public void insertBeforeNode(Item beforeItem, Item item) {
		if (isEmpty()) {
			return;
		}

		DoubleNode currentNode;

		for(currentNode = first; currentNode != null; currentNode = currentNode.next) {
			if (currentNode.item.equals(beforeItem)) {
				break;
			}
		}

		if (currentNode != null) {
			DoubleNode newNode = new DoubleNode();
			newNode.item = item;

			DoubleNode previousNode = currentNode.previous;
			currentNode.previous = newNode;
			newNode.next = currentNode;
			newNode.previous = previousNode;

			if (newNode.previous == null) {
				//This is the first node
				first = newNode;
			} else {
				newNode.previous.next = newNode;
			}

			size++;
		}
	}

	public void insertAfterNode(Item afterNode, Item item) {
		if(isEmpty()) {
			return;
		}

		DoubleNode currentNode;

		for(currentNode = first; currentNode != null; currentNode = currentNode.next) {
			if (currentNode.item.equals(afterNode)) {
				break;
			}
		}

		if(currentNode != null) {
			DoubleNode newNode = new DoubleNode();
			newNode.item = item;

			DoubleNode nextNode = currentNode.next;
			currentNode.next = newNode;
			newNode.previous = currentNode;
			newNode.next = nextNode;

			if(newNode.next == null) {
				//This is the last node
				last = newNode;
			} else {
				newNode.next.previous = newNode;
			}

			size++;
		}
	}
	
	public Item removeFromTheBeginning() {
		if (isEmpty()){
			return null;
		}
		
		Item item = first.item;
		
		if(first.next != null) {
			first.next.previous = null;
		} else { // There is only 1 element in the list
			last = null;
		}
		
		first = first.next;

		size--;
		
		return item;
	}
	
	public Item removeFromTheEnd(){
		if (isEmpty()) {
			return null;
		}

		Item item = last.item;

		if(last.previous != null) {
			last.previous.next = null;
		} else { // There is only 1 element in the list
			first = null;
		}

		last = last.previous;

		size--;

		return item;
	}

	public Item removeNode(int nodeNumber) {
		if (isEmpty() || nodeNumber <= 0 || nodeNumber > size()){
			return null;
		}

		boolean startFromTheBeginning = nodeNumber <= size() / 2;

		int index = startFromTheBeginning ? 1 : size();

		DoubleNode currentNode = startFromTheBeginning? first : last;

		while (currentNode != null) {
			if(nodeNumber == index) {
				break;
			}

			if(startFromTheBeginning) {
				index++;
			} else{
				index--;
			}

			currentNode = startFromTheBeginning ? currentNode.next : currentNode.previous;
		}

		@SuppressWarnings("ConstantConditions") //if we got here, the node exists
		Item item = currentNode.item;

		DoubleNode previousNode = currentNode.previous;
		DoubleNode nextNode = currentNode.next;

		if (previousNode != null) {
			previousNode.next = nextNode;
		}
		if (nextNode != null) {
			nextNode.previous = previousNode;
		}

		if(currentNode == first) {
			first = nextNode;
		}
		if (currentNode == last) {
			last = previousNode;
		}

		size--;

		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new DoublyLinkedListIterator();
	}

	private class DoublyLinkedListIterator implements Iterator<Item> {

		int index = 0;
		DoubleNode currentNode = first;

		@Override
		public boolean hasNext() {
			return index < size();
		}

		@Override
		public Item next() {
			Item item = currentNode.item;
			currentNode = currentNode.next;

			index++;

			return item;
		}
	}

	public static void main(String[] args){
		Exercise31_DoublyLinkedList<Integer> doublyLinkedList = new Exercise31_DoublyLinkedList<>();
		doublyLinkedList.insertAtTheBeginning(10);
		doublyLinkedList.insertAtTheBeginning(30);
		doublyLinkedList.insertAtTheEnd(999);

		doublyLinkedList.insertBeforeNode(9999, 998);
		doublyLinkedList.insertBeforeNode(999, 997);

		doublyLinkedList.insertAfterNode(10, 11);

		doublyLinkedList.removeFromTheBeginning();
		doublyLinkedList.removeFromTheEnd();

		doublyLinkedList.removeNode(2);

		for (int number : doublyLinkedList) {
			StdOut.println(number);
		}
	}
	
}
