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
package com.puppycrawl.tools.checkstyle.api;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * A filter set applies filters to AuditEvents.
 * If a filter in the set rejects an AuditEvent, then the
 * AuditEvent is rejected. Otherwise, the AuditEvent is accepted.
 * @author Rick Giles
 */
public class FilterSet
    implements Filter
{
    /** filter set */
    private final Set<Filter> mFilters = Sets.newHashSet();

    /**
     * Adds a Filter to the set.
     * @param aFilter the Filter to add.
     */
    public void addFilter(Filter aFilter)
    {
        mFilters.add(aFilter);
    }

    /**
     * Removes filter.
     * @param aFilter filter to remove.
     */
    public void removeFilter(Filter aFilter)
    {
        mFilters.remove(aFilter);
    }

    /**
     * Returns the Filters of the filter set.
     * @return the Filters of the filter set.
     */
    protected Set<Filter> getFilters()
    {
        return mFilters;
    }

    @Override
    public String toString()
    {
        return mFilters.toString();
    }

    @Override
    public int hashCode()
    {
        return mFilters.hashCode();
    }

    @Override
    public boolean equals(Object aObject)
    {
        if (aObject instanceof FilterSet) {
            final FilterSet other = (FilterSet) aObject;
            return this.mFilters.equals(other.mFilters);
        }
        return false;
    }

    /** {@inheritDoc} */
    public boolean accept(AuditEvent aEvent)
    {
        for (Filter filter : mFilters) {
            if (!filter.accept(aEvent)) {
                return false;
            }
        }
        return true;
    }

    /** Clears the FilterSet. */
    public void clear()
    {
        mFilters.clear();
    }
}
