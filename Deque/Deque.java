/* *****************************************************************************
 *  Name: Yi
 *  Date: 2024/08/05
 *  Description: Implementation of Deque and Randomized Queues
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // Private variables
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        Node(Node<Item> prev, Item item, Node<Item> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != sentinel;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node<Item> sentinel;

    private int size;

    // construct an empty deque
    public Deque() {
        this.sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the prev
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> newNode = new Node<>(sentinel, item, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    // add the item to the next
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> newNode = new Node<>(sentinel.prev, item, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    // remove and return the item from the prev
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Item item = sentinel.next.item;
        Node<Item> oldFirst = sentinel.next;
        sentinel.next = oldFirst.next;
        oldFirst.next.prev = sentinel;
        size--;
        return item;
    }

    // remove and return the item from the next
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Item item = sentinel.prev.item;
        Node<Item> oldLast = sentinel.prev;
        sentinel.prev = oldLast.prev;
        oldLast.prev.next = sentinel;
        size--;
        return item;
    }

    // return an iterator over items in order from prev to next
    public Iterator<Item> iterator() {
        return new LinkedIterator(sentinel.next);
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Constructor
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.size()); // 0
        System.out.println(deque.isEmpty()); // true

        deque.addFirst(1);
        deque.addLast(2);
        System.out.println(deque.size()); // 2
        deque.addLast(3);
        deque.addFirst(4);
        for (int i : deque) {
            System.out.print(i);
        }
        System.out.println();
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        for (int i : deque) {
            System.out.print(i);
        }
    }
}
