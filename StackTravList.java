package boundedtravlist;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Stack;

public class StackTravList<E> extends AbstractTravList<E> {
    private Stack<E> left, right;

    public StackTravList(int max) {
        super(max);
        this.left = new Stack<>();
        this.right = new Stack<>();
    }

    /**
     * "Primary Method"
     * <p>
     * Inserts an element to the right of the cursor.
     *
     * @param elem element to be added to this traversable list
     * @throws IllegalArgumentException if the specified element is null
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException {
        if(elem == null || this.isFull()) throw new IllegalArgumentException();

        this.right.push(elem);
    }

    private boolean isFull() {
        return this.right.size() == this.capacity();
    }

    /**
     * "Primary Method"
     * <p>
     * Deletes and returns the element to the right of the cursor.
     *
     * @return the element to the right of the cursor.
     */
    @Override
    public E delete() {
        if(this.right.isEmpty()) throw new IllegalStateException();
        return this.right.pop();
    }

    /**
     * "Primary Method"
     * <p>
     * Advances the cursor one element to the right.
     */
    @Override
    public void advance() throws IllegalStateException {
        if(this.right.isEmpty() || this.rightLength() == 0) throw new IllegalStateException();

        this.left.push(right.pop());
    }

    /**
     * "Primary Method"
     * <p>
     * Retreats the cursor one element to the left.
     */
    @Override
    public void retreat() {
        if(this.left.isEmpty()) throw new IllegalStateException();

        this.right.push(left.pop());
    }

    /**
     * "Primary Method"
     * <p>
     * Resets the cursor to the beginning of the list.
     * The stack-based implementation may use a loop,
     * the other implementations may not.
     */
    @Override
    public void reset() {
        while(!this.left.isEmpty()) this.retreat();
    }

    /**
     * "Primary Method"
     * <p>
     * Advances the cursor to the end of the list.
     * The stack-based implementation may use a loop,
     * the other implementations may not.
     */
    @Override
    public void advanceToEnd() {
        while(!this.right.isEmpty()) this.advance();
    }

    /**
     * "Primary Method"
     * <p>
     * Swaps the right part of {@code this} list with
     * the right part of {@code that} list.
     * The stack-based implementation should execute
     * in constant time when both lists are stack-based.
     * The simple implementation and the pointer-based
     * implementation do not have to implement this;
     * they can simply use the default implementation
     * from the abstract traversable list.
     *
     * @param that right part of the list passed in parameter.
     */
    @Override
    public void swapRights(TravList<E> that) {
        if (!(that instanceof StackTravList)) super.swapRights(that);
        else {
            StackTravList<E> thatStackTravList = (StackTravList<E>) that;
            StackTravList<E> revThis = (StackTravList<E>) this.newInstance();

            while (!this.right.isEmpty()) {
                revThis.right.push(this.right.pop());
            }
            while (!thatStackTravList.right.isEmpty()) {
                this.right.push(thatStackTravList.right.pop());
            }
            while (!revThis.right.isEmpty()) {
                thatStackTravList.right.push(revThis.right.pop());
            }
        }
    }

    /**
     * "Primary Method"
     * <p>
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
        return left.size();
    }

    /**
     * "Primary Method"
     * <p>
     * The number of elements to the right of the cursor.
     * The linked-based implementation may use
     * loop (or private fields to keep track of right length).
     * The other implementations must run in constant time.
     *
     * @return the number of elements to the right of the cursor.
     */
    @Override
    public int rightLength() {
        return right.size();
    }

    /**
     * "Primary Method"
     * <p>
     * Creates a new instance of a traversable list.
     * The new traversable list has the same concrete
     * type that {@code this} list does,
     * and it also has the same capacity.
     * However, the new traversable list is {@code empty}
     * – it does not contain any elements.
     *
     * @return the traversable list.
     */
    @Override
    public TravList<E> newInstance() {
        return new StackTravList<>(this.capacity());
    }

    /**
     * "Primary Method"
     * <p>
     * Gets a handle to (does not remove) the element to the right of the cursor.
     * The list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the right of the cursor.
     */
    @Override
    public E getNext() {
        return rightLength() == 0 ? null : this.right.peek();
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
        return leftLength() == 0 ? null : this.left.peek();
    }

    @Override
    public Iterator<E> iterator() { return new RightIterator<>(this); }

    @Override
    public ListIterator<E> listIterator() { return new TravListIterator<>(this); }
}
