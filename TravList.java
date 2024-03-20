package boundedtravlist;

import java.util.ListIterator;
// TODO Add exceptions to signatures

/**
 * <p>Data structure who keeps track of a distinguished position
 * in the sequence that we will call the "cursor position".</p>
 *
 * <p><ul>A traversable list can be depicted
 * using two side-by-side sequences
 * <li>a left sequence and a right sequence.</li></ul>
 * The position between the left sequence
 * and the right sequence is the cursor position.</p>
 *
 * <p>In this stack-based implementation, the leftmost element
 * represents the element at the bottom of the stack
 * , and the rightmost element represents
 * the element at the top of the stack.</p>
 *
 * <p>A typical representation of TravList is
 * <code>[l1, l2, l3, ...][r1, r2, r3, ...]:c</code>
 * where all <code>l</code> elements are in left of cursor
 * and all <code>r</code> elements are in right of cursor.
 * <code>c</code> is the capacity</p>
 *
 * <p>Implementation of this interface should
 * have a one-argument constructor
 * that takes the desired capacity and create an empty TravList.
 * The capacity must be strictly greater than zero.</p>
 *
 * @author Joyce
 * @param <E> the type of elements in this stack.
 */
public interface TravList<E> extends Iterable<E> {
    /**
     * "Primary Method"
     *
     * Inserts an element to the right of the cursor.
     *
     * @param elem element to be added to this traversable list
     * @throws IllegalArgumentException if the specified element is null
     */
    public void insert(E elem) throws IllegalArgumentException;

    /**
     * "Primary Method"
     *
     * Deletes and returns the element to the right of the cursor.
     *
     * @return the element to the right of the cursor.
     */
    public E delete();

    /**
     * "Primary Method"
     *
     * Advances the cursor one element to the right.
     */
    public void advance();

    /**
     * "Primary Method"
     *
     * Retreats the cursor one element to the left.
     */
    public void retreat();

    /**
     * "Primary Method"
     *
     * Resets the cursor to the beginning of the list.
     * The stack-based implementation may use a loop,
     * the other implementations may not.
     */
    public void reset();

    /**
     * "Primary Method"
     *
     * Advances the cursor to the end of the list.
     * The stack-based implementation may use a loop,
     * the other implementations may not.
     */
    public void advanceToEnd();

    /**
     * "Primary Method"
     *
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
    public void swapRights(TravList<E> that);

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
    public int leftLength();

    /**
     * "Primary Method"
     *
     * The number of elements to the right of the cursor.
     * The linked-based implementation may use
     * loop (or private fields to keep track of right length).
     * The other implementations must run in constant time.
     *
     * @return the number of elements to the right of the cursor.
     */
    public int rightLength();

    /**
     * "Primary Method"
     *
     * Creates a new instance of a traversable list.
     * The new traversable list has the same concrete
     * type that {@code this} list does,
     * and it also has the same capacity.
     * However, the new traversable list is {@code empty}
     * – it does not contain any elements.
     *
     * @return the traversable list.
     */
    public TravList<E> newInstance();

    /**
     * "Primary Method"
     *
     * Return the capacity of the list.
     *
     * @return the capacity of the list.
     */
    public int capacity();

    /**
     * "Iterator methods (also secondary)"
     *
     * A list iterator that starts at the cursor position.
     * Note that list iterators can move forward and backwards.
     * Also note that movement of the cursor in the list iterator {@code whill}
     * affect the cursor position of the traversable list.
     * This method should return a TravListIterator.
     *
     * @return a TravListIterator.
     */
    public ListIterator<E> listIterator();

    /**
     * "Primary Method"
     *
     * Gets a handle to (does not remove) the element to the right of the cursor.
     * The list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the right of the cursor.
     */
    public E getNext();

    /**
     * "Primary Method"
     *
     * Gets a handle to the element to the left of the cursor.
     * The traversable list does not change; the cursor does not change.
     * This should be implemented in constant time.
     *
     * @return a handle to the element to the left of the cursor.
     */
    public E getPrevious();

    /**
     * "Secondary Method"
     *
     * Replaces the element to the right of the cursor and returns the original.
     * This should be implemented in constant time.
     *
     * @param element the element to the right of the cursor.
     * @return the original element that was replaced.
     */
    public E replace(E element);

    /**
     * "Secondary Method"
     *
     * Adds all the elements from *that* traversable list to the left of the cursor
     * in {@code this} traversable list in the same order.
     * Note that this results in the specified list ({@code that})
     * being empty when the call completes.
     * The cursor of the specified list ({@code that}) must be
     * at the beginning of the list.
     * For example:
     * <code>PRE: this = [A, B, C][D, E, F] and that = [][X, Y, Z]
     * STMT: this.splice(that);
     * POST: this [A, B, C, X, Y, Z][D, E, F] and that = [][]</code>
     * This method can and should be implemented using only swapRights,
     * reset, and advanceToEnd.
     * What is the benefit (if any) in implementing this with swapRights
     * instead of using a loop to transfer elements?
     *
     * @param that list passed in parameter.
     */
    public void splice(TravList<E> that);

    /**
     * "Secondary Method"
     *
     * Moves all the elements from the right of the cursor
     * to the left of the cursor in reverse order.
     * The cursor must be at the beginning of the list when the method is called.
     * The cursor must be at the end of the list when the call is complete.
     * This method should be implemented using recursion.
     */
    public void reverse();

    /**
     * "Object methods (also secondary)"
     *
     * Checks for equality between the traversable list and another object.
     *
     * @param o the object to be compared.
     * @return equal or not.
     */
    public boolean equals(Object o);

    /**
     * "Object methods (also secondary)"
     *
     * Returns the hash code of the traversable list.
     *
     * @return the hash code of the traversable list.
     */
    public int hashCode();

    /**
     * "Object methods (also secondary)"
     *
     * Returns a string representation of the traversable list.
     *
     * @return a string representation of the traversable list.
     */
    public String toString();
}