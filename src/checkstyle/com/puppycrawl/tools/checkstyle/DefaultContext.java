////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Context;

/**
 * A default implementation of the Context interface.
 * @author lkuehne
 */
public final class DefaultContext implements Context
{
    /** stores the context entries */
    private final Map mEntries = new HashMap();

    /** {@inheritDoc} */
    public Object get(String aKey)
    {
        return mEntries.get(aKey);
    }

    /** {@inheritDoc} */
    public String[] getAttributeNames()
    {
        final Set keySet = mEntries.keySet();
        return (String[]) keySet.toArray(new String[keySet.size()]);
    }

    /**
     * Adds a context entry.
     * @param aKey the context key
     * @param aValue the value for aKey
     */
    public void add(String aKey, Object aValue)
    {
        mEntries.put(aKey, aValue);
    }
}
