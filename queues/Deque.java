/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private Integer nodesNumber;

    private class Node {
        Item item;
        Node prev = null;
        Node next = null;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        nodesNumber = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == last && first == null && nodesNumber == 0;
    }

    // return the number of items on the deque
    public int size() {
        return nodesNumber;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        if (first == null) {
            first = new Node(item);
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node(item);
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        nodesNumber++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        if (last == null) {
            last = new Node(item);
            first = last;
        }
        else {
            Node oldLast = last;
            last = new Node(item);
            last.prev = oldLast;
            oldLast.next = last;
        }
        nodesNumber++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque is empty!");
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        else {
            first.prev = null;
        }
        nodesNumber--;
        if (nodesNumber == 0) {
            last = null; first = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("Deque is empty!");
        }
        Node oldLast = last;
        Item item = oldLast.item;
        last = oldLast.prev;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        nodesNumber--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> monthes = new Deque<>();
        monthes.addFirst("detsember");
        monthes.addFirst("november");
        monthes.addFirst("oktoober");
        StdOut.println("1 First month is oktoober: " + monthes.first.item.equals("oktoober"));
        StdOut.println("2 Last month is detsember: " + monthes.last.item.equals("detsember"));
        monthes.removeFirst();
        monthes.removeLast();
        StdOut.println("3 First month is november: " + monthes.first.item.equals("november"));
        StdOut.println("4 Last month is november too: " + monthes.last.item.equals("november"));
        monthes.removeLast();
        StdOut.println("5 Deque is empty: " + monthes.isEmpty());
        StdOut.println("6 Last node pointer is null: " + (monthes.last == null));
        StdOut.println("7 First node pointer is null: " + (monthes.first == null));
    }
}
