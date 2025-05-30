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

import java.util.Deque;
import java.util.LinkedList;

/**
 * Represents a tree of import rules for controlling whether packages or
 * classes are allowed to be used. Each instance must have a single parent or
 * be the root node.
 */
abstract class AbstractImportControl {

    /** List of {@link AbstractImportRule} objects to check. */
    private final Deque<AbstractImportRule> rules = new LinkedList<>();
    /** The parent. Null indicates we are the root node. */
    private final AbstractImportControl parent;
    /** Strategy in a case if matching allow/disallow rule was not found. */
    private final MismatchStrategy strategyOnMismatch;

    /**
     * Construct a child node.
     *
     * @param parent the parent node.
     * @param strategyOnMismatch strategy in a case if matching allow/disallow rule was not found.
     */
    protected AbstractImportControl(AbstractImportControl parent,
            MismatchStrategy strategyOnMismatch) {
        this.parent = parent;
        this.strategyOnMismatch = strategyOnMismatch;
    }

    /**
     * Search down the tree to locate the finest match for a supplied package.
     *
     * @param forPkg the package to search for.
     * @param forFileName the file name to search for.
     * @return the finest match, or null if no match at all.
     */
    public abstract AbstractImportControl locateFinest(String forPkg, String forFileName);

    /**
     * Check for equality of this with pkg.
     *
     * @param pkg the package to compare with.
     * @param fileName the file name to compare with.
     * @return if it matches.
     */
    protected abstract boolean matchesExactly(String pkg, String fileName);

    /**
     * Adds an {@link AbstractImportRule} to the node.
     *
     * @param rule the rule to be added.
     */
    protected void addImportRule(AbstractImportRule rule) {
        rules.addLast(rule);
    }

    /**
     * Returns whether a package or class is allowed to be imported.
     * The algorithm checks with the current node for a result, and if none is
     * found then calls its parent looking for a match. This will recurse
     * looking for match. If there is no clear result then
     * {@link AccessResult#UNKNOWN} is returned.
     *
     * @param inPkg the package doing the import.
     * @param inFileName the file name doing the import.
     * @param forImport the import to check on.
     * @return an {@link AccessResult}.
     */
    public AccessResult checkAccess(String inPkg, String inFileName, String forImport) {
        final AccessResult result;
        final AccessResult returnValue = localCheckAccess(inPkg, inFileName, forImport);
        if (returnValue != AccessResult.UNKNOWN) {
            result = returnValue;
        }
        else if (parent == null) {
            if (strategyOnMismatch == MismatchStrategy.ALLOWED) {
                result = AccessResult.ALLOWED;
            }
            else {
                result = AccessResult.FORBIDDEN;
            }
        }
        else {
            if (strategyOnMismatch == MismatchStrategy.ALLOWED) {
                result = AccessResult.ALLOWED;
            }
            else if (strategyOnMismatch == MismatchStrategy.FORBIDDEN) {
                result = AccessResult.FORBIDDEN;
            }
            else {
                result = parent.checkAccess(inPkg, inFileName, forImport);
            }
        }
        return result;
    }

    /**
     * Checks whether any of the rules for this node control access to
     * a specified package or file.
     *
     * @param inPkg the package doing the import.
     * @param inFileName the file name doing the import.
     * @param forImport the import to check on.
     * @return an {@link AccessResult}.
     */
    private AccessResult localCheckAccess(String inPkg, String inFileName, String forImport) {
        AccessResult localCheckAccessResult = AccessResult.UNKNOWN;
        for (AbstractImportRule importRule : rules) {
            // Check if an import rule is only meant to be applied locally.
            if (!importRule.isLocalOnly() || matchesExactly(inPkg, inFileName)) {
                final AccessResult result = importRule.verifyImport(forImport);
                if (result != AccessResult.UNKNOWN) {
                    localCheckAccessResult = result;
                    break;
                }
            }
        }
        return localCheckAccessResult;
    }

}
