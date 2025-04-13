////
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A before execution file filter set applies filters to events.
 * If a before execution file filter in the set rejects an event, then the
 * event is rejected. Otherwise, the event is accepted.
 */
public final class BeforeExecutionFileFilterSet
    implements BeforeExecutionFileFilter {

    /** Filter set. */
    private final Set<BeforeExecutionFileFilter> beforeExecutionFileFilters = new HashSet<>();

    /**
     * Adds a Filter to the set.
     *
     * @param filter the Filter to add.
     */
    public void addBeforeExecutionFileFilter(BeforeExecutionFileFilter filter) {
        beforeExecutionFileFilters.add(filter);
    }

    /**
     * Removes filter.
     *
     * @param filter filter to remove.
     */
    public void removeBeforeExecutionFileFilter(BeforeExecutionFileFilter filter) {
        beforeExecutionFileFilters.remove(filter);
    }

    /**
     * Returns the Filters of the filter set.
     *
     * @return the Filters of the filter set.
     */
    public Set<BeforeExecutionFileFilter> getBeforeExecutionFileFilters() {
        return Collections.unmodifiableSet(beforeExecutionFileFilters);
    }

    @Override
    public String toString() {
        return beforeExecutionFileFilters.toString();
    }

    @Override
    public boolean accept(String uri) {
        boolean result = true;
        for (BeforeExecutionFileFilter filter : beforeExecutionFileFilters) {
            if (!filter.accept(uri)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /** Clears the BeforeExecutionFileFilterSet. */
    public void clear() {
        beforeExecutionFileFilters.clear();
    }

}
