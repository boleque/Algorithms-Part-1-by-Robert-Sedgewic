/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (true) {
            try {
                String item = StdIn.readString();
                queue.enqueue(item);
            }
            catch (NoSuchElementException err) {
                break;
            }
        }
        Iterator<String> it = queue.iterator();
        while(it.hasNext()) {
            if (k == 0) break;
            Object element = it.next();
            StdOut.println(element);
            k--;
        }
    }
}
