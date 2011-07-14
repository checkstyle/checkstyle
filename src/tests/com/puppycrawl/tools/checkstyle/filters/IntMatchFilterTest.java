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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/** Tests IntMatchFilter */
public class IntMatchFilterTest
{
    @Test
    public void testDecide()
    {
        final IntFilter filter = new IntMatchFilter(0);
        assertFalse("less than", filter.accept(Integer.valueOf(-1)));
        assertTrue("equal", filter.accept(Integer.valueOf(0)));
        assertFalse("greater than", filter.accept(Integer.valueOf(1)));
    }

    @Test
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
