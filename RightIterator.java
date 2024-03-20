package boundedtravlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO (consider) Why not have an iterator that iterates over *all* the elements in the list?
// TODO (consider) Why is it important to restore the cursor position?
/*
This iterator starts at the cursor position
and iterates until the end of the scroll.
Before returning the last element, it restores
the scroll's cursor to its original position.
So it effectively iterates over the right portion
of the scroll. To iterate from the beginning
of the scroll, the user of this iterator will have
to reset the scroll before calling it.
*/
class RightIterator<E> implements Iterator<E> {

    private final TravList<E> travList;
    private final int pos;
    private final int length;
    private int count;

    public RightIterator(TravList<E> travList) {
        this.travList = travList;
        pos = travList.leftLength();
        length = travList.rightLength();
        count = 0;
    }

    @Override
    public boolean hasNext() {
        return count < length;
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        E result = travList.getNext();
        travList.advance();
        count++;
        // If result is the last element in the scroll,
        // then restore the cursor position.
        if (count == length) {
            travList.reset();
            for (int i = 0; i < pos; i++) {
                travList.advance();
            }
        }
        return result;
    }
}
