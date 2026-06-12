import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == items.length) {
            resize(2 * items.length);
        }

        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniformInt(n);
        Item item = items[randomIndex];

        items[randomIndex] = items[n - 1];
        items[n - 1] = null;
        n--;

        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniformInt(n);
        return items[randomIndex];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }

        items = copy;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] shuffled;
        private int current;

        public RandomizedQueueIterator() {
            shuffled = (Item[]) new Object[n];

            for (int i = 0; i < n; i++) {
                shuffled[i] = items[i];
            }

            StdRandom.shuffle(shuffled);
            current = 0;
        }

        public boolean hasNext() {
            return current < shuffled.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return shuffled[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(10);
        rq.enqueue(20);
        rq.enqueue(30);
        rq.enqueue(40);

        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeued: " + rq.dequeue());

        System.out.println("Items in random order:");
        for (int x : rq) {
            System.out.println(x);
        }
    }
}