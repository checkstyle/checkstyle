////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A filter chain applies filters to Objects in chain order.
 * If the decision of a filter in the chain is ACCEPT, then
 * the chain accepts the Object immediately, without consulting
 * the remaining filters.
 * If the decision of a filter in the chain is DENY, then
 * the chain rejects the Object immediately, without consulting
 * the remaining filters.
 * If the decision of a filter in the chain is NEUTRAL, then
 * the mext filter in the chain, if any, is consulted.
 * @author Rick Giles
 */
public class FilterChain
    implements Filter
{
    /** filter chain */
    private List mFilterChain = new ArrayList();

    /**
     * Adds a Filter as the last filter in the chain.
     * @param aFilter the Filter to add.
     */
    public void addFilter(Filter aFilter)
    {
        mFilterChain.add(aFilter);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Filter#decide */
    public int decide(Object aObject)
    {
        final Iterator it = mFilterChain.iterator();
        while (it.hasNext()) {
            final Filter filter = (Filter) it.next();
            final int decision = filter.decide(aObject);
            if (decision != Filter.NEUTRAL) {
                return decision;
            }
        }
        return AuditEventFilter.NEUTRAL;
    }

    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return mFilterChain.toString();
    }

    /** @see java.lang.Object#hashCode() */
    public int hashCode()
    {
        return mFilterChain.hashCode();
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    public boolean equals (Object aObject)
    {
        if (aObject instanceof FilterChain) {
            final FilterChain other = (FilterChain) aObject;
            return this.mFilterChain.equals(other.mFilterChain);
        }
        else {
            return false;
        }
    }
}
