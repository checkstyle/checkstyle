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
 * Represents whether a class is allowed to be imported or not.
 */
class ClassImportRule extends AbstractImportRule {

    /** Package to control access to. */
    private final String className;

    /**
     * Constructs an instance.
     *
     * @param allow whether to allow access.
     * @param localOnly whether the rule is to be applied locally only
     * @param className the class to apply the rule on.
     * @param regExp whether the class name is to be interpreted as a regular
     *        expression.
     */
    /* package */  ClassImportRule(final boolean allow, final boolean localOnly,
                                   final String className, final boolean regExp) {
        super(allow, localOnly, regExp);
        this.className = className;
    }

    /**
     * Verifies whether a class name is used.
     *
     * @param forImport the import to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    @Override
    public AccessResult verifyImport(final String forImport) {
        final boolean classMatch;

        if (isRegExp()) {
            classMatch = forImport.matches(className);
        }
        else {
            classMatch = forImport.equals(className);
        }

        return calculateResult(classMatch);
    }

}
