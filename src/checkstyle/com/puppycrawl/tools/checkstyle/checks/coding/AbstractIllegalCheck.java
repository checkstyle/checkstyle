////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import java.util.Set;

/**
 * Support for checks that look for usage of illegal types.
 * @author Oliver Burn
 */
public abstract class AbstractIllegalCheck extends Check
{
    /** Illegal class names */
    private final Set<String> mIllegalClassNames = Sets.newHashSet();

    /**
     * Constructs an object.
     * @param aInitialNames the initial class names to treat as illegal
     */
    protected AbstractIllegalCheck(final String[] aInitialNames)
    {
        assert aInitialNames != null;
        setIllegalClassNames(aInitialNames);
    }

    /**
     * Checks if given class is illegal.
     *
     * @param aIdent
     *            ident to check.
     * @return true if given ident is illegal.
     */
    protected final boolean isIllegalClassName(final String aIdent)
    {
        return mIllegalClassNames.contains(aIdent);
    }

    /**
     * Set the list of illegal classes.
     *
     * @param aClassNames
     *            array of illegal exception classes
     */
    public final void setIllegalClassNames(final String[] aClassNames)
    {
        assert aClassNames != null;
        mIllegalClassNames.clear();
        for (final String name : aClassNames) {
            mIllegalClassNames.add(name);
            final int lastDot = name.lastIndexOf(".");
            if ((lastDot > 0) && (lastDot < (name.length() - 1))) {
                final String shortName = name
                        .substring(name.lastIndexOf(".") + 1);
                mIllegalClassNames.add(shortName);
            }
        }
    }
}
