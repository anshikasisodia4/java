
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
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

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        newNode.next = first;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            first.prev = newNode;
            first = newNode;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = last;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            last.next = newNode;
            last = newNode;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        first = first.next;

        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        last = last.prev;

        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        System.out.println("Empty: " + deque.isEmpty());
        System.out.println("Size: " + deque.size());

        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);

        System.out.println("Size after adds: " + deque.size());

        System.out.print("Deque contents: ");
        for (int x : deque) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("removeLast(): " + deque.removeLast());

        System.out.print("Remaining: ");
        for (int x : deque) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Size: " + deque.size());
        System.out.println("Empty: " + deque.isEmpty());
    }
}