package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.Filter;

import junit.framework.TestCase;

/** Tests IntMatchFilter */
public class CSVFilterTest extends TestCase
{
    public void testDecideSingle()
    {
        final Filter filter = new CSVFilter("0");
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(1)));
    }
    
    public void testDecidePair()
    {
        final Filter filter = new CSVFilter("0, 2");
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal 0", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(1)));
        assertEquals("equal 2", Filter.ACCEPT, filter.decide(new Integer(2)));
    }
    
    public void testDecideRange()
    {
        final Filter filter = new CSVFilter("0-2");
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal 0", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("equal 1", Filter.ACCEPT, filter.decide(new Integer(1)));
        assertEquals("equal 2", Filter.ACCEPT, filter.decide(new Integer(2)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(3)));
    }
    
    public void testDecideEmptyRange()
    {
        final Filter filter = new CSVFilter("2-0");
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal 0", Filter.NEUTRAL, filter.decide(new Integer(0)));
        assertEquals("equal 1", Filter.NEUTRAL, filter.decide(new Integer(1)));
        assertEquals("equal 2", Filter.NEUTRAL, filter.decide(new Integer(2)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(3)));
    }
    
    public void testDecideRangePlusValue()
    {
        final Filter filter = new CSVFilter("0-2, 10");
        assertEquals("less than", Filter.NEUTRAL, filter.decide(new Integer(-1)));
        assertEquals("equal 0", Filter.ACCEPT, filter.decide(new Integer(0)));
        assertEquals("equal 1", Filter.ACCEPT, filter.decide(new Integer(1)));
        assertEquals("equal 2", Filter.ACCEPT, filter.decide(new Integer(2)));
        assertEquals("greater than", Filter.NEUTRAL, filter.decide(new Integer(3)));
        assertEquals("equal 10", Filter.ACCEPT, filter.decide(new Integer(10)));
    }
}
