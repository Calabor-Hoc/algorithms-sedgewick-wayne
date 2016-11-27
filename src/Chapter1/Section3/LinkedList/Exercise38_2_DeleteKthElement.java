package Chapter1.Section3.LinkedList;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by rene on 8/21/16.
 */
public class Exercise38_2_DeleteKthElement<Item> implements Iterable<Item>{

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first;
    private Node last;
    private int size;

    public Exercise38_2_DeleteKthElement() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void insert(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;

        if(last.previous != null) {
            last.previous.next = last;
        } else{
            first = last;
        }

        size++;
    }

    public Item delete(int k) {
        if(isEmpty()) {
            throw new RuntimeException("Queue underflow");
        }
        if (k <= 0 || k > size) {
            throw new RuntimeException("Invalid index");
        }

        int count;
        boolean startFromBeginning = k <= size / 2;

        Node current;

        if(startFromBeginning) {
            count = 1;
            for(current = first; count < k; current = current.next) {
                count++;
            }
        } else {
            count = size;
            for(current = last; count > k; current = current.previous){
                count--;
            }
        }

        Item item = current.item;
        if(current.previous != null) {
            current.previous.next = current.next;
        } else {
            first = current.next;
        }

        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            last = current.previous;
        }

        size--;

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new GeneralizedQueueIterator();
    }

    private class GeneralizedQueueIterator implements Iterator<Item> {

        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Exercise38_2_DeleteKthElement<Integer> generalizedQueue = new Exercise38_2_DeleteKthElement<>();
        generalizedQueue.insert(0);
        generalizedQueue.insert(1);
        generalizedQueue.insert(2);
        generalizedQueue.insert(3);
        generalizedQueue.insert(4);

        generalizedQueue.delete(1);
        generalizedQueue.delete(4);
        generalizedQueue.insert(99);

        for(int item : generalizedQueue) {
            StdOut.println(item);
        }
    }

}
