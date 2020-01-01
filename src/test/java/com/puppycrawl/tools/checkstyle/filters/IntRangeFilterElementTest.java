////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class IntRangeFilterElementTest {

    @Test
    public void testDecide() {
        final IntFilterElement filter = new IntRangeFilterElement(0, 10);
        assertFalse(filter.accept(-1), "less than");
        assertTrue(filter.accept(0), "in range");
        assertTrue(filter.accept(5), "in range");
        assertTrue(filter.accept(10), "in range");
        assertFalse(filter.accept(11), "greater than");
    }

    @Test
    public void testDecideSingle() {
        final IntFilterElement filter = new IntRangeFilterElement(0, 0);
        assertFalse(filter.accept(-1), "less than");
        assertTrue(filter.accept(0), "in range");
        assertFalse(filter.accept(1), "greater than");
    }

    @Test
    public void testDecideEmpty() {
        final IntFilterElement filter = new IntRangeFilterElement(10, 0);
        assertFalse(filter.accept(-1), "out");
        assertFalse(filter.accept(0), "out");
        assertFalse(filter.accept(5), "out");
        assertFalse(filter.accept(10), "out");
        assertFalse(filter.accept(11), "out");
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(IntRangeFilterElement.class)
                .usingGetClass().report();
        assertEquals(EqualsVerifierReport.SUCCESS, ev, "Error: " + ev.getMessage());
    }

}
