///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.filters.SeverityMatchFilter;

public class FilterSetTest {

    @Test
    public void testGetFilters() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertWithMessage("Invalid filter set size")
                .that(filterSet.getFilters())
                .hasSize(1);
    }

    @Test
    public void testRemoveFilters() {
        final FilterSet filterSet = new FilterSet();
        final Filter filter = new SeverityMatchFilter();
        filterSet.addFilter(filter);
        filterSet.removeFilter(filter);
        assertWithMessage("Invalid filter set size")
                .that(filterSet.getFilters())
                .hasSize(0);
    }

    @Test
    public void testToString() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());
        assertWithMessage("Invalid filter set size")
                .that(filterSet.toString())
                .isNotNull();
    }

    @Test
    public void testClear() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new SeverityMatchFilter());

        assertWithMessage("Invalid filter set size")
                .that(filterSet.getFilters())
                .hasSize(1);

        filterSet.clear();

        assertWithMessage("Invalid filter set size")
                .that(filterSet.getFilters())
                .hasSize(0);
    }

    @Test
    public void testAccept() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(true));
        assertWithMessage("invalid accept response")
                .that(filterSet.accept(null))
                .isTrue();
    }

    @Test
    public void testNotAccept() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(false));
        assertWithMessage("invalid accept response")
                .that(filterSet.accept(null))
                .isFalse();
    }

    @Test
    public void testNotAcceptEvenIfOneAccepts() {
        final FilterSet filterSet = new FilterSet();
        filterSet.addFilter(new DummyFilter(true));
        filterSet.addFilter(new DummyFilter(false));
        assertWithMessage("invalid accept response")
                .that(filterSet.accept(null))
                .isFalse();
    }

    /*
      Due to low level configuration setup of FilterSet, conventional
      input validation cannot be done here hence, pure JUnit testing has been
      done for the time being
    */
    @Test
    public void testUnmodifiableSet() {
        final FilterSet filterSet = new FilterSet();
        final Filter filter = new FilterSet();
        filterSet.addFilter(filter);
        final Set<Filter> subFilterSet = filterSet.getFilters();
        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
            () -> subFilterSet.add(filter));
        assertWithMessage("Exception message not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
    }

    /*
      Input based test does not call toString, but this method might
      be useful for third party integrations
    */
    @Test
    public void testEmptyToString() {
        final FilterSet filterSet = new FilterSet();
        assertWithMessage("toString() result shouldn't be an empty string")
                .that(filterSet.toString())
                .isNotEmpty();
    }

    private static final class DummyFilter implements Filter {

        private final boolean acceptValue;

        private DummyFilter(boolean accept) {
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
