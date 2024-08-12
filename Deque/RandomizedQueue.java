/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[8];
        size = 0;
    }

    // Resize arrays
    private Item[] resizeUp(Item[] oldArray) {
        Item[] newArray = (Item[]) new Object[oldArray.length * 2];
        for (int i = 0; i < oldArray.length; ++i) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }

    private Item[] resizeDown(Item[] oldArray) {
        Item[] newArray = (Item[]) new Object[oldArray.length / 2];
        int index = 0;
        for (int i = 0; i < oldArray.length; ++i) {
            if (oldArray[i] != null) {
                newArray[index] = oldArray[i];
                index++;
            }
        }
        return newArray;
    }

    // Iterator
    private class ArrayIterator implements Iterator<Item> {
        private int[] indices;
        private int currentIndex;

        ArrayIterator(Item[] array) {
            currentIndex = 0;
            indices = new int[array.length];
            for (int i = 0; i < indices.length; i++)
                indices[i] = i;
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            while (currentIndex < array.length && array[indices[currentIndex]] == null)
                currentIndex++;
            return currentIndex < array.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = array[indices[currentIndex]];
            currentIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == array.length) array = resizeUp(array);
        array[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        int randIndex = StdRandom.uniformInt(array.length);
        while (array[randIndex] == null)
            randIndex = StdRandom.uniformInt(array.length);
        Item item = array[randIndex];
        array[randIndex] = null;
        size--;
        // Resize down array if only a quarter full
        if (size <= array.length / 4)
            array = resizeDown(array);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        int randIndex = StdRandom.uniformInt(array.length);
        while (array[randIndex] == null)
            randIndex = StdRandom.uniformInt(array.length);
        return array[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator(array);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 9; i++) {
            randQueue.enqueue(i);
        }
        StdOut.println("Size: " + randQueue.size);
        for (int i : randQueue) {
            StdOut.print(i);
        }
        StdOut.println();

        for (int i = 0; i < 5; i++)
            StdOut.println("Remove: " + randQueue.dequeue());
        StdOut.println("Size after dequeue: " + randQueue.size);
        for (int i : randQueue)
            StdOut.print(i);
        StdOut.println();
    }

}
