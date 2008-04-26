package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/** Tests SuppressElementFilter */
// TODO: this test should be removed/rewritten
public class FilterSetTest
{
    private CSVFilter filter;

    @Before
    public void setUp()
    {
        filter = new CSVFilter("");
    }

    @Test
    public void testEmptyChain()
    {
        assertFalse("0", filter.accept(Integer.valueOf(0)));
    }

    @Test
    public void testOneFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        assertTrue("0", filter.accept(Integer.valueOf(0)));
        assertFalse("1", filter.accept(Integer.valueOf(1)));
    }

    @Test
    public void testMultipleFilter()
    {
        filter.addFilter(new IntMatchFilter(0));
        filter.addFilter(new IntRangeFilter(0, 2));
        assertTrue("0", filter.accept(Integer.valueOf(0)));
        assertTrue("1", filter.accept(Integer.valueOf(1)));
        filter.addFilter(new IntRangeFilter(3, 4));
        assertTrue("0 is in [3,4]", filter.accept(Integer.valueOf(0)));
    }
}
