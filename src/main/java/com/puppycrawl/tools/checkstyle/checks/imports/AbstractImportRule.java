///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Base class for import rules.
 */
abstract class AbstractImportRule {

    /** Indicates whether to allow access or not. */
    private final boolean allowed;

    /** Indicates if the rule only applies to this package. */
    private final boolean localOnly;

    /**
     * Indicates if the name is to be interpreted
     * as a regular expression.
     */
    private final boolean regExp;

    /**
     * Constructs an instance.
     *
     * @param allow whether to allow access.
     * @param localOnly whether the rule is to be applied locally only.
     * @param regExp whether the name is to be interpreted as a regular
     *        expression.
     */
    protected AbstractImportRule(final boolean allow, final boolean localOnly,
        final boolean regExp) {
        allowed = allow;
        this.localOnly = localOnly;
        this.regExp = regExp;
    }

    /**
     * Verifies whether a package name is used.
     *
     * @param forImport the import to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    public abstract AccessResult verifyImport(String forImport);

    /**
     * Return true if the guard is to only be applied locally or false.
     *
     * @return whether the guard is to only be applied locally.
     */
    public boolean isLocalOnly() {
        return localOnly;
    }

    /**
     * Return true if the name is to be interpreted as a regular expression or false.
     *
     * @return whether the name is to be interpreted as a regular expression.
     */
    protected boolean isRegExp() {
        return regExp;
    }

    /**
     * Returns the appropriate {@link AccessResult} based on whether there
     * was a match and if the rule is to allow access.
     *
     * @param matched indicates whether there was a match.
     * @return An appropriate {@link AccessResult}.
     */
    protected AccessResult calculateResult(final boolean matched) {
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
