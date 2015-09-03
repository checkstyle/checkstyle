////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import nl.jqno.equalsverifier.EqualsVerifier;

/** Tests IntMatchFilter. */
public class IntMatchFilterTest {
    @Test
    public void testDecide() {
        final IntFilter filter = new IntMatchFilter(0);
        assertFalse("less than", filter.accept(-1));
        assertTrue("equal", filter.accept(0));
        assertFalse("greater than", filter.accept(1));
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(IntMatchFilter.class).verify();
    }

    @Test
    public void testToString() {
        final IntFilter filter = new IntMatchFilter(6);
        assertEquals("IntMatchFilter[6]", filter.toString());
    }
}
