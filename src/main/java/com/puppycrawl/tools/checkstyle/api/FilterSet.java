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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * A filter set applies filters to AuditEvents.
 * If a filter in the set rejects an AuditEvent, then the
 * AuditEvent is rejected. Otherwise, the AuditEvent is accepted.
 * @author Rick Giles
 */
public class FilterSet
    implements Filter {
    /** filter set */
    private final Set<Filter> filters = Sets.newHashSet();

    /**
     * Adds a Filter to the set.
     * @param filter the Filter to add.
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    /**
     * Removes filter.
     * @param filter filter to remove.
     */
    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    /**
     * Returns the Filters of the filter set.
     * @return the Filters of the filter set.
     */
    public Set<Filter> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return filters.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FilterSet filterSet = (FilterSet) o;
        return Objects.equals(filters, filterSet.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters);
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(AuditEvent event) {
        for (Filter filter : filters) {
            if (!filter.accept(event)) {
                return false;
            }
        }
        return true;
    }

    /** Clears the FilterSet. */
    public void clear() {
        filters.clear();
    }
}
