package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.Filter;

import junit.framework.TestCase;

/** Tests IntMatchFilter */
public class IntMatchFilterTest extends TestCase
{
    public void testDecide()
    {
        final Filter filter = new IntMatchFilter(0);
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(1)));
    }
    
    public void testEquals()
    {
        final Filter filter = new IntMatchFilter(0);
        final Filter filter2 = new IntMatchFilter(0);
        final Filter filter3 = new IntMatchFilter(1);
        assertEquals("0", filter, filter2);
        assertFalse("0 != 1", filter.equals(filter3));
        assertFalse("0 != this", filter.equals(this));
        assertFalse("0 != null", filter.equals(null));
    }
}
