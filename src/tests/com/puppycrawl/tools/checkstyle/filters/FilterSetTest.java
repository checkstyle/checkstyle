package com.puppycrawl.tools.checkstyle.filters;

import junit.framework.TestCase;

import com.puppycrawl.tools.checkstyle.api.FilterSet;

/** Tests SuppressElementFilter */
public class FilterSetTest extends TestCase
{
    private FilterSet filter;
    
    public void setUp()
    {
        filter = new FilterSet();
    }
    
    public void testEmptyChain()
    {
        assertTrue("0", filter.accept(new Integer(0)));
    }
    
    public void testOneFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        assertTrue("0", filter.accept(new Integer(0)));
        assertFalse("1", filter.accept(new Integer(1)));
        assertFalse("\"0\"", filter.accept("0"));
    }
    
    public void testMultipleFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        filter.addFilter(new IntRangeFilter(0, 2));
        assertTrue("0", filter.accept(new Integer(0)));
        assertFalse("1", filter.accept(new Integer(1)));
        assertFalse("\"0\"", filter.accept("0"));
        filter.addFilter(new IntRangeFilter(3, 4));
        assertFalse("0 not in [3,4]", filter.accept(new Integer(0)));
    }
}
