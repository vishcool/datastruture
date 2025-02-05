
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;
    private final Random random;

    private static class Node<Item> {

        private Item item;
        private Node<Item> next;
    }

    // Construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
        random = new Random();
    }

    // Is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // Add the item
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

    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = random.nextInt(size);
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

    // Return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = random.nextInt(size);
        Node<Item> current = first;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.item;
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] shuffledQueue;
        private int current;

        public RandomizedQueueIterator() {
            shuffledQueue = (Item[]) new Object[size];
            Node<Item> currentNode = first;
            for (int i = 0; i < size; i++) {
                shuffledQueue[i] = currentNode.item;
                currentNode = currentNode.next;
            }
            shuffle(shuffledQueue);
            current = 0;
        }

        private void shuffle(Item[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                Item temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }

        public boolean hasNext() {
            return current < size;
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

    // Unit testing (required)
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
