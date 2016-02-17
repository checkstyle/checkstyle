////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
class Guard {
    /** Indicates if allow access or not. */
    private final boolean allowed;
    /** Package to control access to. */
    private final String pkgName;
    /** Package to control access to. */
    private final String className;

    /**
     * Indicates if must be an exact match. Only valid if guard using a
     * package.
     */
    private final boolean exactMatch;
    /** Indicates if the guard only applies to this package. */
    private final boolean localOnly;
    /**
     * Indicates if the package and the class names are to be interpreted
     * as regular expressions.
     */
    private final boolean regExp;

    /**
     * Constructs an instance.
     * @param allow whether to allow access.
     * @param localOnly whether guard is to be applied locally only
     * @param pkgName the package to apply guard on.
     * @param exactMatch whether the package must match exactly.
     * @param regExp whether the package is to be interpreted as regular
     *        expression.
     */
    Guard(final boolean allow, final boolean localOnly,
        final String pkgName, final boolean exactMatch, final boolean regExp) {
        allowed = allow;
        this.localOnly = localOnly;
        this.pkgName = pkgName;
        this.regExp = regExp;
        className = null;
        this.exactMatch = exactMatch;
    }

    /**
     * Constructs an instance.
     * @param allow whether to allow access.
     * @param localOnly whether guard is to be applied locally only
     * @param className the class to apply guard on.
     * @param regExp whether the class is to be interpreted as regular
     *        expression.
     */
    Guard(final boolean allow, final boolean localOnly,
        final String className, final boolean regExp) {
        allowed = allow;
        this.localOnly = localOnly;
        this.regExp = regExp;
        pkgName = null;
        this.className = className;

        // not used
        exactMatch = true;
    }

    /**
     * Verifies whether a package name be used.
     * @param forImport the package to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    public AccessResult verifyImport(final String forImport) {
        if (className != null) {
            final boolean classMatch;

            if (regExp) {
                classMatch = forImport.matches(className);
            }
            else {
                classMatch = forImport.equals(className);
            }
            return calculateResult(classMatch);
        }

        // Must be checking a package. First check that we actually match
        // the package. Then check if matched and we must be an exact match.
        // In this case, the text after the first "." must not contain
        // another "." as this indicates that it is not an exact match.
        boolean pkgMatch;
        if (regExp) {
            pkgMatch = forImport.matches(pkgName + "\\..*");
            if (pkgMatch && exactMatch) {
                pkgMatch = !forImport.matches(pkgName + "\\..*\\..*");
            }
        }
        else {
            pkgMatch = forImport.startsWith(pkgName + ".");
            if (pkgMatch && exactMatch) {
                pkgMatch = forImport.indexOf('.',
                        pkgName.length() + 1) == -1;
            }
        }
        return calculateResult(pkgMatch);
    }

    /**
     * @return returns whether the guard is to only be applied locally.
     */
    public boolean isLocalOnly() {
        return localOnly;
    }

    /**
     * Returns the appropriate {@link AccessResult} based on whether there
     * was a match and if the guard is to allow access.
     * @param matched indicates whether there was a match.
     * @return An appropriate {@link AccessResult}.
     */
    private AccessResult calculateResult(final boolean matched) {
        AccessResult result = AccessResult.UNKNOWN;

        if (matched) {
            if (allowed) {
                result = AccessResult.ALLOWED;
            }
            else {
                result = AccessResult.DISALLOWED;
            }
        }
        return result;
    }
}
