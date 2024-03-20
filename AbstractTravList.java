package boundedtravlist;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

/**
 * <p>Abstract TravList.</p>
 *
 * @author Joyce
 * @param <E> the type of elements in this stack.
 */
public abstract class AbstractTravList<E> implements TravList<E> {
    /**
     * Capacity of the list.
     */
    private int capacity;

    public AbstractTravList(int max) {
        this.capacity = max;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new TravListIterator<>(this);
    }

    @Override
    public E replace(E element) {
        if (element == null) throw new IllegalArgumentException();
        E elem = this.delete();
        this.insert(element);

        return elem;
    }

    @Override
    public void reverse() {
        if(this.rightLength() == 0) return;

        this.reset();
        E element = this.delete();
        this.reverse();
        this.advanceToEnd();
        this.insert(element);
        this.advanceToEnd();
    }

    @Override
    public void splice(TravList<E> that) {
        if (that.leftLength() != 0) {
            throw new IllegalArgumentException();
        }

        TravList<E> temp = this.newInstance();

        this.swapRights(temp);
        this.swapRights(that);
        this.advanceToEnd();
        this.swapRights(temp);
    }

    @Override
    public void swapRights(TravList<E> that) {
        TravList<E> revThis = this.newInstance();
        TravList<E> revThat = that.newInstance();

        while (this.rightLength() > 0) {
            revThis.insert(this.delete());
        }
        while (that.rightLength() > 0) {
            revThat.insert(that.delete());
        }
        while (revThat.rightLength() > 0) {
            this.insert(revThat.delete());
        }
        while (revThis.rightLength() > 0) {
            that.insert(revThis.delete());
        }
    }

    // ----------------------------------------------------------
    // ToString method
    // ----------------------------------------------------------

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        TravList<E> leftElement = this.newInstance();
        TravList<E> rightElement = this.newInstance();
        ListIterator<E> iterator = this.listIterator();

        /*
         * Step 1: Deconstruct this TravList ( [left-elems][right-elems] )
         * into the following 2 travlists: [][left-elems] and [][right-elems]
         */
        while (iterator.hasNext()) rightElement.insert(iterator.next());
        while (iterator.hasPrevious()) leftElement.insert(iterator.previous());

        /*
         * Step 2: Use rightToString to add both left and right portions of the TravList
         * to the string builder.
         */
        sb.append(this.rightToString(leftElement));
        sb.append(this.rightToString(rightElement));
        String showCapacity = ":" + this.capacity();
        sb.append(showCapacity);

        /*
         * Step 3: Restore this TravList to its original state.
         */

        return sb.toString();
    }

    /*
     *  Returns a string representation of the right portion of the TravList.
     *  The elements are separated by commas and enclosed in brackets.
     */
    private String rightToString(TravList<E> TravList) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        RightIterator<E> iter = new RightIterator<>(TravList);
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public Iterator<E> iterator() {
        return new RightIterator<>(this);
    }
}
