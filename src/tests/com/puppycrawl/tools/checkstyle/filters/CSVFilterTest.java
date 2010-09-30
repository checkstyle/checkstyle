package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/** Tests CSVFilter */
public class CSVFilterTest
{
    @Test
    public void testDecideSingle()
    {
        final IntFilter filter = new CSVFilter("0");
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertTrue("equal", filter.accept(Integer.valueOf(0)));
        assertFalse("greater than", filter.accept(Integer.valueOf(1)));
    }

    @Test
    public void testDecidePair()
    {
        final IntFilter filter = new CSVFilter("0, 2");
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertTrue("equal 0", filter.accept(Integer.valueOf(0)));
        assertFalse("greater than", filter.accept(Integer.valueOf(1)));
        assertTrue("equal 2", filter.accept(Integer.valueOf(2)));
    }

    @Test
    public void testDecideRange()
    {
        final IntFilter filter = new CSVFilter("0-2");
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertTrue("equal 0", filter.accept(Integer.valueOf(0)));
        assertTrue("equal 1", filter.accept(Integer.valueOf(1)));
        assertTrue("equal 2", filter.accept(Integer.valueOf(2)));
        assertFalse("greater than", filter.accept(Integer.valueOf(3)));
    }

    @Test
    public void testDecideEmptyRange()
    {
        final IntFilter filter = new CSVFilter("2-0");
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertFalse("equal 0", filter.accept(Integer.valueOf(0)));
        assertFalse("equal 1", filter.accept(Integer.valueOf(1)));
        assertFalse("equal 2", filter.accept(Integer.valueOf(2)));
        assertFalse("greater than", filter.accept(Integer.valueOf(3)));
    }

    @Test
    public void testDecideRangePlusValue()
    {
        final IntFilter filter = new CSVFilter("0-2, 10");
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertTrue("equal 0", filter.accept(Integer.valueOf(0)));
        assertTrue("equal 1", filter.accept(Integer.valueOf(1)));
        assertTrue("equal 2", filter.accept(Integer.valueOf(2)));
        assertFalse("greater than", filter.accept(Integer.valueOf(3)));
        assertTrue("equal 10", filter.accept(Integer.valueOf(10)));
    }
}
