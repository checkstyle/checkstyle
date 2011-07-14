////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
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
