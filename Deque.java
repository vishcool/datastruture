
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {

        Item item;
        Node next;
        Node prev;
    }
    private int size = 0;
    private Node first, last;

    // construct an empty deque
    public Deque() // is the deque empty?
    {

    }

    public boolean isEmpty() // return the number of items on the deque
    {
        return first == null ? true : false;
    }

    public int size() // add the item to the front
    {
        return size;
    }

    public void addFirst(Item item) // add the item to the back
    {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        size++;
        if (item != null) {
            if (first == null) {
                first = new Node();
                first.item = item;
                last = first;

            } else {
                Node oldFirst = first;
                first = new Node();
                first.item = item;
                first.next = oldFirst;
                oldFirst.prev = first;
            }
        }
    }

    public void addLast(Item item) // remove and return the item from the front
    {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        size++;
        if (item != null) {
            if (last == null) {
                last = new Node();
                last.item = item;
                first = last;
            } else {
                Node oldLast = last;
                last = new Node();
                last.item = item;
                last.prev = oldLast;
                oldLast.next = last;
                last.next = null;

            }
        }

    }

    public Item removeFirst() // remove and return the item from the back
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        Item recentItem = first.item;
        first = first.next;
        return recentItem;
    }

    public Item removeLast() // return an iterator over items in order from front to back
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        Item recentItem = last.item;
        last = first.prev;
        return recentItem;
    }

    // Return an iterator over items in order from front to end
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

// Unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(4);

        deque.addFirst(0);
        deque.addLast(3);

        for (int item : deque) {
            System.out.println(item);
        }
        System.out.println("--------------------");
        deque.removeFirst();
        for (int item : deque) {
            System.out.println(item);
        }
    }
}
