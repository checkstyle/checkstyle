////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableCollection;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.Context;
import java.util.Map;

/**
 * A default implementation of the Context interface.
 * @author lkuehne
 */
public final class DefaultContext implements Context
{
    /** stores the context entries */
    private final Map<String, Object> entries = Maps.newHashMap();

    /** {@inheritDoc} */
    @Override
    public Object get(String key)
    {
        return entries.get(key);
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableCollection<String> getAttributeNames()
    {
        return ImmutableList.copyOf(entries.keySet());
    }

    /**
     * Adds a context entry.
     * @param key the context key
     * @param value the value for key
     */
    public void add(String key, Object value)
    {
        entries.put(key, value);
    }
}
