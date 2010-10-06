////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
