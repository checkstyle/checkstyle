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
    /** Package to control access to. */
    private final String mClassName;

    /**
     * Indicates if must be an exact match. Only valid if guard using a
     * package.
     */
    private final boolean mExactMatch;
    /** Indicates if the guard only applies to this package. */
    private final boolean mLocalOnly;
    /**
     * Indicates if the package and the class names are to be interpreted
     * as regular expressions.
     */
    private final boolean mRegExp;

    /**
     * Constructs an instance.
     * @param aAllow whether to allow access.
     * @param aLocalOnly whether guard is to be applied locally only
     * @param aPkgName the package to apply guard on.
     * @param aExactMatch whether the package must match exactly.
     * @param aRegExp whether the package is to be interpreted as regular
     *        expression.
     */
    Guard(final boolean aAllow, final boolean aLocalOnly,
        final String aPkgName, final boolean aExactMatch, final boolean aRegExp)
    {
        mAllowed = aAllow;
        mLocalOnly = aLocalOnly;
        mPkgName = aPkgName;
        mRegExp = aRegExp;
        mClassName = null;
        mExactMatch = aExactMatch;
    }

    /**
     * Constructs an instance.
     * @param aAllow whether to allow access.
     * @param aLocalOnly whether guard is to be applied locally only
     * @param aClassName the class to apply guard on.
     * @param aRegExp whether the class is to be interpreted as regular
     *        expression.
     */
    Guard(final boolean aAllow, final boolean aLocalOnly,
        final String aClassName, final boolean aRegExp)
    {
        mAllowed = aAllow;
        mLocalOnly = aLocalOnly;
        mRegExp = aRegExp;
        mPkgName = null;
        mClassName = aClassName;
        mExactMatch = true; // not used.
    }

    /**
     * Verifies whether a package name be used.
     * @param aForImport the package to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    AccessResult verifyImport(final String aForImport)
    {
        assert aForImport != null;
        if (mClassName != null) {
            final boolean classMatch = mRegExp
                ? aForImport.matches(mClassName)
                : aForImport.equals(mClassName);
            return calculateResult(classMatch);
        }

        // Must be checking a package. First check that we actually match
        // the package. Then check if matched and we must be an exact match.
        // In this case, the text after the first "." must not contain
        // another "." as this indicates that it is not an exact match.
        assert mPkgName != null;
        boolean pkgMatch;
        if (mRegExp) {
            pkgMatch = aForImport.matches(mPkgName + "\\..*");
            if (pkgMatch && mExactMatch) {
                pkgMatch = !aForImport.matches(mPkgName + "\\..*\\..*");
            }
        }
        else {
            pkgMatch = aForImport.startsWith(mPkgName + ".");
            if (pkgMatch && mExactMatch) {
                pkgMatch = (aForImport.indexOf('.',
                    (mPkgName.length() + 1)) == -1);
            }
        }
        return calculateResult(pkgMatch);
    }

    /**
     * @return returns whether the guard is to only be applied locally.
     */
    boolean isLocalOnly()
    {
        return mLocalOnly;
    }

    /**
     * Returns the appropriate {@link AccessResult} based on whether there
     * was a match and if the guard is to allow access.
     * @param aMatched indicates whether there was a match.
     * @return An appropriate {@link AccessResult}.
     */
    private AccessResult calculateResult(final boolean aMatched)
    {
        if (aMatched) {
            return mAllowed ? AccessResult.ALLOWED : AccessResult.DISALLOWED;
        }
        return AccessResult.UNKNOWN;
    }
}
