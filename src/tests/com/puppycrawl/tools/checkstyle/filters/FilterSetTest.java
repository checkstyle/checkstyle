package com.puppycrawl.tools.checkstyle.filters;

import junit.framework.TestCase;

/** Tests SuppressElementFilter */
// TODO: this test should be removed/rewritten
public class FilterSetTest extends TestCase
{
    private CSVFilter filter;
    
    public void setUp()
    {
        filter = new CSVFilter("");
    }
    
    public void testEmptyChain()
    {
        assertFalse("0", filter.accept(new Integer(0)));
    }
    
    public void testOneFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        assertTrue("0", filter.accept(new Integer(0)));
        assertFalse("1", filter.accept(new Integer(1)));
    }
    
    public void testMultipleFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        filter.addFilter(new IntRangeFilter(0, 2));
        assertTrue("0", filter.accept(new Integer(0)));
        assertTrue("1", filter.accept(new Integer(1)));
        filter.addFilter(new IntRangeFilter(3, 4));
        assertTrue("0 is in [3,4]", filter.accept(new Integer(0)));
    }
}
