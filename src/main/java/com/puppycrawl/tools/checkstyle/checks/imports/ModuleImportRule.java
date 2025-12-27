///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Represents whether a module is allowed to be imported or not.
 */
class ModuleImportRule extends AbstractImportRule {

    /** Module to control access to. */
    private final String moduleName;

    /**
     * Constructs an instance.
     *
     * @param allow whether to allow access.
     * @param localOnly whether the rule is to be applied locally only
     * @param moduleName the module to apply the rule on.
     * @param regExp whether the module name is to be interpreted as a regular expression.
     */
    /* package */ ModuleImportRule(final boolean allow, final boolean localOnly,
            final String moduleName, final boolean regExp) {
        super(allow, localOnly, regExp);
        this.moduleName = moduleName;
    }

    /**
     * Verifies whether a module name is used.
     *
     * @param forImport the import to check.
     * @return a result {@link AccessResult} indicating whether it can be used.
     */
    @Override
    public AccessResult verifyImport(final String forImport) {
        final boolean moduleMatch;
        if (isRegExp()) {
            moduleMatch = forImport.matches(moduleName);
        }
        else {
            moduleMatch = forImport.equals(moduleName);
        }
        return calculateResult(moduleMatch);
    }
}
