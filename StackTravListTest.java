package boundedtravlist;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;

public class StackTravListTest {

    TravList<String> empty;
    TravList<String> abc_de;

    @Before
    public void setUp() throws Exception {

        empty = new StackTravList<>(5);
        abc_de = new StackTravList<>(5);
        abc_de.insert("E");
        abc_de.insert("D");
        abc_de.insert("C");
        abc_de.insert("B");
        abc_de.insert("A");
        abc_de.advance();
        abc_de.advance();
        abc_de.advance();
    }

    // ==========================================================
    // Setup Methods
    // ==========================================================

    @Test
    public void initEmptySetup() {
        assertEquals(0, empty.leftLength());
        assertEquals(0, empty.rightLength());
        assertNull(empty.getPrevious());
        assertNull(empty.getNext());
    }

    @Test
    public void initDefaultSetup() {
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("C", abc_de.getPrevious());
        assertEquals("D", abc_de.getNext());
    }

    // ==========================================================
    // Primary Methods
    // ==========================================================

    @Test
    public void deleteAndInsert() {
        abc_de.delete();
        abc_de.insert("X");
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("C", abc_de.getPrevious());
        assertEquals("X", abc_de.getNext());
    }

    @Test
    public void advance() {
        abc_de.advance();
        assertEquals(4, abc_de.leftLength());
        assertEquals(1, abc_de.rightLength());
        assertEquals("D", abc_de.getPrevious());
        assertEquals("E", abc_de.getNext());
    }

    @Test
    public void retreat() {
        abc_de.retreat();
        assertEquals(2, abc_de.leftLength());
        assertEquals(3, abc_de.rightLength());
        assertEquals("B", abc_de.getPrevious());
        assertEquals("C", abc_de.getNext());
    }

    @Test
    public void reset() {
        abc_de.reset();
        assertEquals(0, abc_de.leftLength());
        assertEquals(5, abc_de.rightLength());
        assertNull(abc_de.getPrevious());
        assertEquals("A", abc_de.getNext());
    }

    @Test
    public void advanceToEnd() {
        abc_de.advanceToEnd();
        assertEquals(5, abc_de.leftLength());
        assertEquals(0, abc_de.rightLength());
        assertEquals("E", abc_de.getPrevious());
        assertNull(abc_de.getNext());
    }

    @Test
    public void swapRights() {
        TravList<String> x_yz = new StackTravList<>(3);
        x_yz.insert("Z");
        x_yz.insert("Y");
        x_yz.insert("X");
        x_yz.reset();
        x_yz.advance();
        abc_de.swapRights(x_yz);
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("Z", abc_de.getNext());
        assertEquals("C", abc_de.getPrevious());
        assertEquals(1, x_yz.leftLength());
        assertEquals(2, x_yz.rightLength());
        assertEquals("X", x_yz.getPrevious());
        assertEquals("D", x_yz.getNext());
    }

    @Test
    public void swapRightsInheritForParent() {
        TravList<String> x_yz = new SimpleTravList<>(3);
        x_yz.insert("Z");
        x_yz.insert("Y");
        x_yz.insert("X");
        x_yz.reset();
        x_yz.advance();
        abc_de.swapRights(x_yz);
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("Y", abc_de.getNext());
        assertEquals("C", abc_de.getPrevious());
        assertEquals(1, x_yz.leftLength());
        assertEquals(2, x_yz.rightLength());
        assertEquals("X", x_yz.getPrevious());
        assertEquals("D", x_yz.getNext());
    }

    @Test
    public void reverse() {
        abc_de.reverse();
        assertNull(abc_de.getNext());
        assertEquals("A", abc_de.getPrevious());
        assertEquals(5, abc_de.leftLength());
    }

    @Test
    public void replace() {
        abc_de.replace("F");
        assertEquals("F", abc_de.getNext());
    }

    @Test
    public void splice() {
        TravList<String> ab_c = new StackTravList<>(6);
        TravList<String> xy = new StackTravList<>(2);
        ab_c.insert("C");
        ab_c.insert("B");
        ab_c.insert("A");
        ab_c.advance();
        xy.insert("Y");
        xy.insert("X");
        ab_c.splice(xy);
        assertEquals(3, ab_c.leftLength());
        assertEquals(2, ab_c.rightLength());
        assertEquals("C", ab_c.getNext());
        assertEquals("X", ab_c.getPrevious());
        assertNull(xy.getNext());
    }

    // ==========================================================
    // Simple Exceptions
    // ==========================================================

    @Test(expected = IllegalArgumentException.class)
    public void insertInFullListAndElementIsNull() {
        abc_de.insert("F");
        abc_de.insert(null);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void spliceLeftLengthIsNotNull() {
        TravList<String> ab_c = new SimpleTravList<>(6);
        TravList<String> xy = new SimpleTravList<>(2);
        ab_c.insert("C");
        ab_c.insert("B");
        ab_c.insert("A");
        ab_c.advance();
        xy.insert("Y");
        xy.insert("X");
        xy.advance();
        ab_c.splice(xy);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceNullElement() {
        abc_de.replace(null);
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void stackScrollAdvanceEmpty() {
        empty.advance();
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void stackScrollAdvanceRightLengthIsEmpty() {
        abc_de.advance();
        abc_de.advance();
        abc_de.advance();
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void stackScrollRetreatEmpty() {
        empty.retreat();
        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void stackScrollRemoveEmpty() {
        empty.delete();
        fail();
    }

    // ==========================================================
    // Iterator Methods
    // ==========================================================

    @Test
    public void iterator() {
        Iterator<String> iterator = abc_de.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertEquals(4, abc_de.leftLength());
        assertEquals(1, abc_de.rightLength());
        assertEquals("D", abc_de.getPrevious());
        assertEquals("E", abc_de.getNext());
    }

    @Test
    public void listIterator() {
        ListIterator<String> iterator = abc_de.listIterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertEquals(4, abc_de.leftLength());
        assertEquals(1, abc_de.rightLength());
        assertEquals("D", abc_de.getPrevious());
        assertEquals("E", abc_de.getNext());
        assertTrue(iterator.hasPrevious());
        iterator.previous();
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("C", abc_de.getPrevious());
        assertEquals("D", abc_de.getNext());
        assertEquals(2, iterator.previousIndex());
        assertEquals(3, iterator.nextIndex());
        iterator.remove();
        assertEquals(3, abc_de.leftLength());
        assertEquals(1, abc_de.rightLength());
        assertEquals("C", abc_de.getPrevious());
        assertEquals("E", abc_de.getNext());
        iterator.set("X");
        assertEquals("X", abc_de.getNext());
        iterator.add("Y");
        assertEquals(3, abc_de.leftLength());
        assertEquals(2, abc_de.rightLength());
        assertEquals("C", abc_de.getPrevious());
        assertEquals("Y", abc_de.getNext());
    }

    // ==========================================================
    // Inherit method
    // ==========================================================

    @Test
    public void testToString() {
        assertEquals("[A, B, C][E, D]:5", abc_de.toString());
    }
}