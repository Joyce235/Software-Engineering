package boundedtravlist;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

public class LinkedTravList<E> extends AbstractTravList<E> {

    class Node {
    E contents;
    Node prev;
    Node next;

    public Node(E contents) {
        this.contents = contents;
    }

    public Node(E contents, Node prev, Node next) {
        this.contents = contents;
        this.prev = prev;
        this.next = next;
    }
}

    Node guard;
    Node cursor;

    public LinkedTravList(int max) {
        super(max);
        guard = new Node(null);
        guard.next = guard;
        guard.prev = guard;
        cursor = new Node(null, guard, guard);
    }

    @Override
    public void insert(E elem) {
        // Step 1: create a new node and make link the same as the cursor
        Node newElement = new Node(elem, null, null);
        newElement.prev = this.cursor.prev;
        newElement.next = this.cursor.next;

        // Step 2: Make adjacent links point to the new node.
        if(this.cursor.prev == this.guard) this.guard.next = newElement;
        if (this.guard.prev == this.guard) this.guard.prev = newElement.next;
        // for old node
        this.cursor.next.prev = newElement;

        // Modify the cursor links
        this.cursor.next = newElement;
    }

    @Override
    public E delete() {
        if (rightLength() == 0) {
            throw new IllegalStateException();
        }

        // Step 2: Redirect links of nodes on either side of result-node
        Node elem = this.cursor.next;
        elem.prev.next = elem.next;
        elem.next.prev = elem.prev;

        // Step 3: Modify the cursor links appropriately
        this.cursor.next = elem.next;

        // Step 4: Return the element inside the result-node
        return elem.contents;
    }

    @Override
    public void advance() {
        if (rightLength() == 0) {
            throw new IllegalStateException();
        }
        cursor.prev = cursor.prev.next;
        cursor.next = cursor.next.next;
    }

    @Override
    public void retreat() {
        if (leftLength() == 0) {
            throw new IllegalStateException();
        }
        cursor.prev = cursor.prev.prev;
        cursor.next = cursor.next.prev;

    }

    @Override
    public void reset() {
        cursor.prev = guard;
        cursor.next = guard.next;
    }

    @Override
    public void advanceToEnd() {
        cursor.prev = guard.prev;
        cursor.next = guard;
    }

    @Override
    public E getNext() {
        return rightLength() == 0 ? null : cursor.next.contents;
    }

    /**
     * "Primary Method"
     * <p>
     * Gets a handle to the element to the left of the cursor.
     * The traversable list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the left of the cursor.
     */
    @Override
    public E getPrevious() {
        return leftLength() == 0 ? null : cursor.prev.contents;
    }

    @Override
    public int leftLength() {
        int result = 0;
        Node temp = cursor;
        while (temp.prev != guard) {
            result++;
            temp = temp.prev;
        }
        return result;
    }

    @Override
    public int rightLength() {
        int result = 0;
        Node temp = cursor;
        while (temp.next != guard) {
            result++;
            temp = temp.next;
        }
        return result;
    }

    @Override
    public TravList<E> newInstance() {
        return new LinkedTravList<>(this.capacity());
    }

    @Override
    public Iterator<E> iterator() { return new RightIterator<>(this); }

    @Override
    public ListIterator<E> listIterator() { return new TravListIterator<>(this); }
}
