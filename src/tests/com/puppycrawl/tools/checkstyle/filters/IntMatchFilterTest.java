package com.puppycrawl.tools.checkstyle.filters;

import junit.framework.TestCase;

/** Tests IntMatchFilter */
public class IntMatchFilterTest extends TestCase
{
    public void testDecide()
    {
        final IntFilter filter = new IntMatchFilter(0);
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("equal", filter.accept(new Integer(0)));
        assertFalse("greater than", filter.accept(new Integer(1)));
    }
    
    public void testEquals()
    {
        final IntFilter filter = new IntMatchFilter(0);
        final IntFilter filter2 = new IntMatchFilter(0);
        final IntFilter filter3 = new IntMatchFilter(1);
        assertEquals("0", filter, filter2);
        assertFalse("0 != 1", filter.equals(filter3));
        assertFalse("0 != this", filter.equals(this));
        assertFalse("0 != null", filter.equals(null));
    }
}
