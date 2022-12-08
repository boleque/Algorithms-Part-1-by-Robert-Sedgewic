/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int N = 0;

    private class RandomArrayIterator implements Iterator<Item> {
        // Each iterator must return the items in uniformly random order.
        // The order of two or more iterators to the same randomized queue must be mutually independent;
        // Each iterator must maintain its own random order.
        private int i;
        private int[] iPermutations;

        public RandomArrayIterator() {
            i = N;
            iPermutations = StdRandom.permutation(N);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            int nextI = iPermutations[--i];
            return arr[nextI];
        }

    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[0];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // repeated doubling
        if (N == arr.length) {
            int c = arr.length == 0? 1 : 0;
            resize(2 * (arr.length + c));
        }
        arr[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        Item ret = arr[i];
        arr[i] = arr[N-1];
        arr[N-1] = null;
        N--;
        if (N > 0 && N == arr.length / 4) {
            resize(arr.length / 2);
        }
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        return arr[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    // unit testing (required)
    //@SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    public static void main(String[] args) {
        RandomizedQueue<String> days = new RandomizedQueue<>();
        days.enqueue("Esmaspäev");
        days.enqueue("Teisipäev");
        days.enqueue("Kolmapäev");
        days.enqueue("Neljapäev");
        days.enqueue("Reede");
        StdOut.println("1 working days number: " + days.size());
        StdOut.println("2 Random day is: " + days.sample());
        days.dequeue();
        days.dequeue();
        days.dequeue();
        days.dequeue();
        days.dequeue();
        StdOut.println("3 Queue is empty: " + days.isEmpty());
    }
}
