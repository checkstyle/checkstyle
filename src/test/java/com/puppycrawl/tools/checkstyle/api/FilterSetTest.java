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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.filters.SeverityMatchFilter;

public class FilterSetTest {

    @Test
    public void testGetFilters() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertEquals(1, filterSet.getFilters().size(), "size is the same");
    }

    @Test
    public void testRemoveFilters() {
        final FilterSet filterSet = new FilterSet();
        final Filter filter = new SeverityMatchFilter();
        filterSet.addFilter(filter);
        filterSet.removeFilter(filter);
        assertEquals(0, filterSet.getFilters().size(), "size is the same");
    }

    @Test
    public void testToString() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertNotNull(filterSet.toString(), "size is the same");
    }

    @Test
    public void testClear() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());

        assertEquals(1, filterSet.getFilters().size(), "Invalid filter set size");

        filterSet.clear();

        assertEquals(0, filterSet.getFilters().size(), "Invalid filter set size");
    }

    @Test
    public void testAccept() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(true));
        assertTrue(filterSet.accept(null), "invalid accept response");
    }

    @Test
    public void testNotAccept() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(false));
        assertFalse(filterSet.accept(null), "invalid accept response");
    }

    @Test
    public void testNotAcceptEvenIfOneAccepts() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(true));
        filterSet.addFilter(new DummyFilter(false));
        assertFalse(filterSet.accept(null), "invalid accept response");
    }

    private static class DummyFilter implements Filter {

        private final boolean acceptValue;

        /* package */ DummyFilter(boolean accept) {
            acceptValue = accept;
        }

        @Override
        public boolean accept(AuditEvent event) {
            return acceptValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(!acceptValue);
        }

        @Override
        public boolean equals(Object object) {
            if (getClass() != object.getClass()) {
                return false;
            }
            final DummyFilter other = (DummyFilter) object;
            return Boolean.compare(acceptValue, other.acceptValue) == 0;
        }

    }

}
