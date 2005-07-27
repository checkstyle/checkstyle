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
package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Represents whether a package is allowed to be used or not.
 * @author Oliver Burn
 */
class Guard
{
    /** Indicates if allow access or not. */
    private final boolean mAllowed;
    /** Package to control access to. */
    private final String mPkgName;

    /**
     * Constructs an instance.
     * @param aAllowed whether the package is allowed.
     * @param aPkgName the package name.
     */
    Guard(final boolean aAllowed, final String aPkgName)
    {
        mAllowed = aAllowed;
        mPkgName = aPkgName;
    }

    /**
     * Verifies whether a package name be used.
     * @param aName the package to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    AccessResult verify(final String aName)
    {
        assert aName != null;
        if (!aName.startsWith(mPkgName + ".")) {
            return AccessResult.UNKNOWN;
        }
        return mAllowed ? AccessResult.ALLOWED : AccessResult.DISALLOWED;
    }
}
