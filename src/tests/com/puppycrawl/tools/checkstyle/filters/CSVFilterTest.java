package com.puppycrawl.tools.checkstyle.filters;

import junit.framework.TestCase;

/** Tests CSVFilter */
public class CSVFilterTest extends TestCase
{
    public void testDecideSingle()
    {
        final IntFilter filter = new CSVFilter("0");
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("equal", filter.accept(new Integer(0)));
        assertFalse("greater than", filter.accept(new Integer(1)));
    }
    
    public void testDecidePair()
    {
        final IntFilter filter = new CSVFilter("0, 2");
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("equal 0", filter.accept(new Integer(0)));
        assertFalse("greater than", filter.accept(new Integer(1)));
        assertTrue("equal 2", filter.accept(new Integer(2)));
    }
    
    public void testDecideRange()
    {
        final IntFilter filter = new CSVFilter("0-2");
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("equal 0", filter.accept(new Integer(0)));
        assertTrue("equal 1", filter.accept(new Integer(1)));
        assertTrue("equal 2", filter.accept(new Integer(2)));
        assertFalse("greater than", filter.accept(new Integer(3)));
    }
    
    public void testDecideEmptyRange()
    {
        final IntFilter filter = new CSVFilter("2-0");
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertFalse("equal 0", filter.accept(new Integer(0)));
        assertFalse("equal 1", filter.accept(new Integer(1)));
        assertFalse("equal 2", filter.accept(new Integer(2)));
        assertFalse("greater than", filter.accept(new Integer(3)));
    }
    
    public void testDecideRangePlusValue()
    {
        final IntFilter filter = new CSVFilter("0-2, 10");
        assertFalse("less than", filter.accept(new Integer(-1)));
        assertTrue("equal 0", filter.accept(new Integer(0)));
        assertTrue("equal 1", filter.accept(new Integer(1)));
        assertTrue("equal 2", filter.accept(new Integer(2)));
        assertFalse("greater than", filter.accept(new Integer(3)));
        assertTrue("equal 10", filter.accept(new Integer(10)));
    }
}
