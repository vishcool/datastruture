
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {

        private Item item;
        private Node<Item> next;
    }

    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Node<Item> current = first;
        Node<Item> previous = null;
        for (int i = 0; i < randomIndex; i++) {
            previous = current;
            current = current.next;
        }
        Item item = current.item;
        if (previous == null) {
            first = first.next;
        } else {
            previous.next = current.next;
        }
        if (current == last) {
            last = previous;
        }
        size--;
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Node<Item> current = first;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] shuffledQueue;
        private int current;

        public RandomizedQueueIterator() {
            shuffledQueue = (Item[]) new Object[size]; // Create a new array for shuff
            Node<Item> currentNode = first;
            for (int i = 0; i < size; i++) {
                shuffledQueue[i] = currentNode.item;
                currentNode = currentNode.next;
            }
            fisherYatesShuffle(shuffledQueue);
            current = 0;
        }

        private void fisherYatesShuffle(Item[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int j = StdRandom.uniformInt(i + 1);
                Item temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        public boolean hasNext() {
            return current < shuffledQueue.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledQueue[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeue: " + rq.dequeue());

        for (int item : rq) {
            System.out.println(item);
        }
    }
}
