package boundedtravlist;

import java.util.*;

/**
 * <p>Java List-based traversable list.</p>
 *
 * @author Joyce
 * @param <E> the type of elements in this stack.
 */
public class SimpleTravList<E> extends AbstractTravList<E> {
    // representation

    /**
     * List of elements.
     */
    private List<E> list;

    /**
     * Position of cursor.
     */
    private int pos;

    // list = [], pos = 0 and capacity = 3 ==> TravList = [][]:3
    // list = [A, B, C, D, E, F], pos = 3 and capacity = 10 ==> TravList = [A, B, C][D, E, F]:3

    public SimpleTravList(int max) {
        super(max);
        this.list = new ArrayList<>();
        this.pos = 0;
    }

    /**
     * "Primary Method"
     *
     * Inserts an element to the right of the cursor.
     *
     * @param elem element to be added to this traversable list
     * @throws IllegalArgumentException if the specified element is null
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException {
        // throw exception if the list is full
        if (this.isFull() || elem == null) throw new IllegalArgumentException();

        // add element in the list
        // before, look for the cursor position to determine where to insert the element
        this.list.add(this.pos, elem);
    }

    private boolean isFull() {
        return this.list.size() >= super.capacity();
    }

    /**
     * "Primary Method"
     *
     * Deletes and returns the element to the right of the cursor.
     *
     * @return the element to the right of the cursor.
     */
    @Override
    public E delete() {
        // throw exception if the list is empty
        if (this.isEmpty()) throw new IllegalStateException();

        return this.list.remove(this.pos);
    }

    private boolean isEmpty() { return this.list.isEmpty(); }

    /**
     * "Primary Method"
     *
     * Advances the cursor one element to the right.
     */
    @Override
    public void advance() throws IllegalStateException {
        // if there is no more element to the right of the cursor then an IllegalStateException is raised
        if (this.rightLength() == 0) throw new IllegalStateException();

        // else increment the cursor
        this.pos = this.pos + 1;
    }

    /**
     * "Primary Method"
     *
     * Retreats the cursor one element to the left.
     */
    @Override
    public void retreat() throws IllegalStateException {
        // if there is no more element to the left of the cursor then an IllegalStateException is raised
        if (leftLength() == 0) throw new IllegalStateException();

        // else decrement the cursor
        this.pos = this.pos - 1;
    }

    /**
     * "Primary Method"
     *
     * Resets the cursor to the beginning of the list.
     * The stack-based implementation may use a loop, the other implementations may not.
     */
    @Override
    public void reset() {
        while(this.pos > 0) this.retreat();
    }

    /**
     * "Primary Method"
     *
     * Advances the cursor to the end of the list.
     * The stack-based implementation may use a loop, the other implementations may not.
     */
    @Override
    public void advanceToEnd() {
        while(this.getNext() != null) {
            this.advance();
        }
    }

    /**
     * "Primary Method"
     *
     * The number of elements to the left of the cursor.
     * The linked-based implementation may use loop.
     * Also, you can feel free to use extra (private) fields to keep track
     * of the left length in the linked-based implementation.
     * The other implementations must run in constant time (no loops).
     *
     * @return the number of elements to the left of the cursor.
     */
    @Override
    public int leftLength() {
        // we initialize an element counter to 0
        int numberOfElement = 0;

        for(int p = this.pos; p > 0; p--) {
            numberOfElement++;
        }

        // then we return this number
        return numberOfElement;
    }

    /**
     * "Primary Method"
     *
     * The number of elements to the right of the cursor.
     * The linked-based implementation may use loop (or private fields to keep track of right length).
     * The other implementations must run in constant time.
     *
     * @return the number of elements to the right of the cursor.
     */
    @Override
    public int rightLength() {
        // we initialize an element counter to 0
        int numberOfElement = 0;

        for(int p = this.pos; p < this.list.size(); p++) {
            numberOfElement++;
        }

        // then we return this number
        return numberOfElement;
    }

    /**
     * "Primary Method"
     *
     * Creates a new instance of a traversable list.
     * The new traversable list has the same concrete type that *this* list does,
     * and it also has the same capacity.
     * However, the new traversable list is *empty* – it does not contain any elements.
     *
     * @return the traversable list.
     */
    @Override
    public TravList<E> newInstance() {
        return new SimpleTravList<E>(this.capacity());
    }

    /**
     * "Primary Method"
     *
     * Gets a handle to (does not remove) the element to the right of the cursor.
     * The list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the right of the cursor.
     */
    @Override
    public E getNext() {
        E result = null;
        // return null if is empty
        if (this.isEmpty()) return null;

        try {
            result = this.delete();
            this.insert(result);
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    /**
     * "Primary Method"
     *
     * Gets a handle to the element to the left of the cursor.
     * The traversable list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the left of the cursor.
     */
    @Override
    public E getPrevious() {
        // return null if is empty
        if (this.isEmpty() || this.pos == 0) return null;

        this.retreat();
        E result = this.getNext();
        this.advance();

        return result;
    }
}
