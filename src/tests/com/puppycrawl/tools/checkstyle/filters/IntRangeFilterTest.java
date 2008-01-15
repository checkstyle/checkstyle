package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Tests IntRangeFilter */
public class IntRangeFilterTest
{
    @Test public void testDecide()
    {
        final IntFilter filter = new IntRangeFilter(0, 10);
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("in range", filter.accept(new Integer(0)));
        assertTrue("in range", filter.accept(new Integer(5)));
        assertTrue("in range", filter.accept(new Integer(10)));
        assertFalse("greater than", filter.accept(new Integer(11)));
    }

    @Test public void testDecideSingle()
    {
        final IntFilter filter = new IntRangeFilter(0, 0);
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("in range", filter.accept(new Integer(0)));
        assertFalse("greater than", filter.accept(new Integer(1)));
    }

    @Test public void testDecideEmpty()
    {
        final IntFilter filter = new IntRangeFilter(10, 0);
        assertFalse("out", filter.accept(new Integer(-1)));
        assertFalse("out", filter.accept(new Integer(0)));
        assertFalse("out", filter.accept(new Integer(5)));
        assertFalse("out", filter.accept(new Integer(10)));
        assertFalse("out", filter.accept(new Integer(11)));
    }

    @Test public void testEquals()
    {
        final IntFilter filter = new IntRangeFilter(0, 2);
        final IntFilter filter2 = new IntRangeFilter(0, 2);
        final IntFilter filter3 = new IntRangeFilter(0, 1);
        final IntFilter filter4 = new IntRangeFilter(1, 2);
        assertEquals("[0,2] == [0,2]", filter, filter2);
        assertFalse("[0,2] != [0,1]", filter.equals(filter3));
        assertFalse("[0,2] != [1,2]", filter.equals(filter4));
        assertFalse("[0,2] != this", filter.equals(this));
        assertFalse("[0,2] != null", filter.equals(null));
    }
}
