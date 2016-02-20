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

import java.util.Deque;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Represents the a tree of guards for controlling whether packages are allowed
 * to be used. Each instance must have a single parent or be the root node.
 * Each instance may have zero or more children.
 *
 * @author Oliver Burn
 */
class PkgControl {
    /** List of {@link Guard} objects to check. */
    private final Deque<Guard> guards = Lists.newLinkedList();
    /** List of children {@link PkgControl} objects. */
    private final List<PkgControl> children = Lists.newArrayList();
    /** The parent. Null indicates we are the root node. */
    private final PkgControl parent;
    /** The full package name for the node. */
    private final String fullPackage;

    /**
     * Construct a root node.
     * @param pkgName the name of the package.
     */
    PkgControl(final String pkgName) {
        parent = null;
        fullPackage = pkgName;
    }

    /**
     * Construct a child node.
     * @param parent the parent node.
     * @param subPkg the sub package name.
     */
    PkgControl(final PkgControl parent, final String subPkg) {
        this.parent = parent;
        fullPackage = parent.fullPackage + "." + subPkg;
        parent.children.add(this);
    }

    /**
     * Adds a guard to the node.
     * @param thug the guard to be added.
     */
    protected void addGuard(final Guard thug) {
        guards.addFirst(thug);
    }

    /**
     * Search down the tree to locate the finest match for a supplied package.
     * @param forPkg the package to search for.
     * @return the finest match, or null if no match at all.
     */
    public PkgControl locateFinest(final String forPkg) {
        PkgControl finestMatch = null;
        // Check if we are a match.
        // This algorithm should be improved to check for a trailing "."
        // or nothing following.
        if (forPkg.startsWith(fullPackage)) {
            // If there won't be match so I am the best there is.
            finestMatch = this;
            // Check if any of the children match.
            for (PkgControl child : children) {
                final PkgControl match = child.locateFinest(forPkg);
                if (match != null) {
                    finestMatch = match;
                    break;
                }
            }
        }
        return finestMatch;
    }

    /**
     * Returns whether a package is allowed to be used. The algorithm checks
     * with the current node for a result, and if none is found then calls
     * its parent looking for a match. This will recurse looking for match.
     * If there is no clear result then {@link AccessResult#UNKNOWN} is
     * returned.
     * @param forImport the package to check on.
     * @param inPkg the package doing the import.
     * @return an {@link AccessResult}.
     */
    public AccessResult checkAccess(final String forImport, final String inPkg) {
        final AccessResult result;
        final AccessResult returnValue = localCheckAccess(forImport, inPkg);
        if (returnValue != AccessResult.UNKNOWN) {
            result = returnValue;
        }
        else if (parent == null) {
            // we are the top, so default to not allowed.
            result = AccessResult.DISALLOWED;
        }
        else {
            result = parent.checkAccess(forImport, inPkg);
        }
        return result;
    }

    /**
     * Checks whether any of the guards for this node control access to
     * a specified package.
     * @param forImport the package to check.
     * @param inPkg the package doing the import.
     * @return an {@link AccessResult}.
     */
    private AccessResult localCheckAccess(final String forImport,
        final String inPkg) {
        for (Guard g : guards) {
            // Check if a Guard is only meant to be applied locally.
            if (g.isLocalOnly() && !fullPackage.equals(inPkg)) {
                continue;
            }
            final AccessResult result = g.verifyImport(forImport);
            if (result != AccessResult.UNKNOWN) {
                return result;
            }
        }
        return AccessResult.UNKNOWN;
    }
}
