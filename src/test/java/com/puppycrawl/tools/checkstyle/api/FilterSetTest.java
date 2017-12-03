////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.filters.SeverityMatchFilter;
import nl.jqno.equalsverifier.EqualsVerifier;

public class FilterSetTest {
    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(FilterSet.class).usingGetClass().verify();
    }

    @Test
    public void testGetFilters() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertEquals("size is the same", 1, filterSet.getFilters().size());
    }

    @Test
    public void testToString() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertNotNull("size is the same", filterSet.toString());
    }

    @Test
    public void testClear() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());

        assertEquals("Invalid filter set size", 1, filterSet.getFilters().size());

        filterSet.clear();

        assertEquals("Invalid filter set size", 0, filterSet.getFilters().size());
    }
}
