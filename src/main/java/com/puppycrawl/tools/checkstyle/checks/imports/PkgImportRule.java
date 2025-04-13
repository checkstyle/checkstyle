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

package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Represents whether a package is allowed to be imported or not.
 */
class PkgImportRule extends AbstractImportRule {

    /** Package to control access to. */
    private final String pkgName;

    /** Indicates if the package name must be an exact match. */
    private final boolean exactMatch;

    /**
     * Constructs an instance.
     *
     * @param allow whether to allow access.
     * @param localOnly whether the rule is to be applied locally only
     * @param pkgName the package to apply the rule on.
     * @param exactMatch whether the package name must match exactly.
     * @param regExp whether the package name is to be interpreted as a regular
     *        expression.
     */
    /* package */ PkgImportRule(final boolean allow, final boolean localOnly,
                                final String pkgName, final boolean exactMatch, final boolean regExp) {
        super(allow, localOnly, regExp);
        this.pkgName = pkgName;
        this.exactMatch = exactMatch;
    }

    /**
     * Verifies whether a package name is used.
     *
     * @param forImport the import to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    @Override
    public AccessResult verifyImport(final String forImport) {
        // First check that we actually match the package.
        // Then check if matched and f we must be an exact match.
        // In this case, the text after the first "." must not contain
        // another "." as this indicates that it is not an exact match.

        boolean pkgMatch;

        if (isRegExp()) {
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

}
