package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.Filter;

import junit.framework.TestCase;

/** Tests IntMatchFilter */
public class IntRangeFilterTest extends TestCase
{
    public void testDecide()
    {
        final Filter filter = new IntRangeFilter(0, 10);
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("in range", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("in range", Filter.ACCEPT, filter.decide(new Integer(5)));
        assertEquals("in range", Filter.ACCEPT, filter.decide(new Integer(10)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(11)));
    }
    
    public void testDecideSingle()
    {
        final Filter filter = new IntRangeFilter(0, 0);
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("in range", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(1)));
    }

    public void testDecideEmpty()
    {
        final Filter filter = new IntRangeFilter(10, 0);
        assertEquals("out", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("out", Filter.NEUTRAL, filter.decide(new Integer(0)));
        assertEquals("out", Filter.NEUTRAL, filter.decide(new Integer(5)));
        assertEquals("out", Filter.NEUTRAL, filter.decide(new Integer(10)));
        assertEquals("out", Filter.NEUTRAL, filter.decide(new Integer(11)));
    }
    
    public void testEquals()
    {
        final Filter filter = new IntRangeFilter(0, 2);
        final Filter filter2 = new IntRangeFilter(0, 2);
        final Filter filter3 = new IntRangeFilter(0, 1);
        final Filter filter4 = new IntRangeFilter(1, 2);
        assertEquals("[0,2] == [0,2]", filter, filter2);
        assertFalse("[0,2] != [0,1]", filter.equals(filter3));
        assertFalse("[0,2] != [1,2]", filter.equals(filter4));
        assertFalse("[0,2] != this", filter.equals(this));
        assertFalse("[0,2] != null", filter.equals(null)); 
    }
}
