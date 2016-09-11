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

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents the a tree of guards for controlling whether packages are allowed
 * to be used. Each instance must have a single parent or be the root node.
 * Each instance may have zero or more children.
 *
 * @author Oliver Burn
 */
class PkgControl {
    /** The package separator: "." */
    private static final String DOT = ".";
    /** A pattern matching the package separator: "." */
    private static final Pattern DOT_PATTERN = Pattern.compile(DOT, Pattern.LITERAL);
    /** The regex for the package separator: "\\.". */
    private static final String DOT_REGEX = "\\.";
    /** List of {@link Guard} objects to check. */
    private final Deque<Guard> guards = new LinkedList<>();
    /** List of children {@link PkgControl} objects. */
    private final List<PkgControl> children = new ArrayList<>();
    /** The parent. Null indicates we are the root node. */
    private final PkgControl parent;
    /** The full package name for the node. */
    private final String fullPackage;
    /**
     * The regex pattern for partial match (exact and for subpackages) - only not
     * null if regex is true.
     */
    private final Pattern patternForPartialMatch;
    /** The regex pattern for exact matches - only not null if regex is true. */
    private final Pattern patternForExactMatch;
    /** If this package represents a regular expression. */
    private final boolean regex;

    /**
     * Construct a root node.
     * @param pkgName the name of the package.
     * @param regex flags interpretation of pkgName as regex pattern.
     */
    PkgControl(final String pkgName, final boolean regex) {
        parent = null;
        this.regex = regex;
        if (regex) {
            // ensure that fullPackage is a self-contained regular expression
            fullPackage = encloseInGroup(pkgName);
            patternForPartialMatch = createPatternForPartialMatch(fullPackage);
            patternForExactMatch = createPatternForExactMatch(fullPackage);
        }
        else {
            fullPackage = pkgName;
            patternForPartialMatch = null;
            patternForExactMatch = null;
        }
    }

    /**
     * Construct a child node. The concatenation of regular expressions needs special care:
     * see {@link #ensureSelfContainedRegex(String, boolean)} for more details.
     * @param parent the parent node.
     * @param subPkg the sub package name.
     * @param regex flags interpretation of subPkg as regex pattern.
     */
    PkgControl(final PkgControl parent, final String subPkg, final boolean regex) {
        this.parent = parent;
        if (regex || parent.regex) {
            // regex gets inherited
            final String parentRegex = ensureSelfContainedRegex(parent.fullPackage, parent.regex);
            final String thisRegex = ensureSelfContainedRegex(subPkg, regex);
            fullPackage = parentRegex + DOT_REGEX + thisRegex;
            patternForPartialMatch = createPatternForPartialMatch(fullPackage);
            patternForExactMatch = createPatternForExactMatch(fullPackage);
            this.regex = true;
        }
        else {
            fullPackage = parent.fullPackage + DOT + subPkg;
            patternForPartialMatch = null;
            patternForExactMatch = null;
            this.regex = false;
        }
        parent.children.add(this);
    }

    /**
     * Returns a regex that is suitable for concatenation by 1) either converting a plain string
     * into a regular expression (handling special characters) or 2) by enclosing {@code input} in
     * a (non-capturing) group if {@code input} already is a regular expression.
     *
     * <p>1) When concatenating a non-regex package component (like "org.google") with a regex
     * component (like "[^.]+") the other component has to be converted into a regex too, see
     * {@link #toRegex(String)}.
     *
     * <p>2) The grouping is strictly necessary if a) {@code input} is a regular expression that b)
     * contains the alteration character ('|') and if c) the pattern is not already enclosed in a
     * group - as you see in this example: {@code parent="com|org", child="common|uncommon"} will
     * result in the pattern {@code "(?:org|com)\.(?common|uncommon)"} what will match
     * {@code "com.common"}, {@code "com.uncommon"}, {@code "org.common"}, and {@code
     * "org.uncommon"}. Without the grouping it would be {@code "com|org.common|uncommon"} which
     * would match {@code "com"}, {@code "org.common"}, and {@code "uncommon"}, which clearly is
     * undesirable. Adding the group fixes this.
     *
     * <p>For simplicity the grouping is added to regular expressions unconditionally.
     *
     * @param input the input string.
     * @param alreadyRegex signals if input already is a regular expression.
     * @return a regex string.
     */
    private static String ensureSelfContainedRegex(final String input, final boolean alreadyRegex) {
        final String result;
        if (alreadyRegex) {
            result = encloseInGroup(input);
        }
        else {
            result = toRegex(input);
        }
        return result;
    }

    /**
     * Enclose {@code expression} in a (non-capturing) group.
     * @param expression the input regular expression
     * @return a grouped pattern.
     */
    private static String encloseInGroup(String expression) {
        return "(?:" + expression + ")";
    }

    /**
     * Converts a normal package name into a regex pattern by escaping all
     * special characters that may occur in a java package name.
     * @param input the input string.
     * @return a regex string.
     */
    private static String toRegex(String input) {
        return DOT_PATTERN.matcher(input).replaceAll(DOT_REGEX);
    }

    /**
     * Creates a Pattern from {@code expression} that matches exactly and child packages.
     * @param expression a self-contained regular expression matching the full package exactly.
     * @return a Pattern.
     */
    private static Pattern createPatternForPartialMatch(String expression) {
        // javadoc of encloseInGroup() explains how to concatenate regular expressions
        // no grouping needs to be added to fullPackage since this already have been done.
        return Pattern.compile(expression + "(?:\\..*)?");
    }

    /**
     * Creates a Pattern from {@code expression}.
     * @param expression a self-contained regular expression matching the full package exactly.
     * @return a Pattern.
     */
    private static Pattern createPatternForExactMatch(String expression) {
        return Pattern.compile(expression);
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
        if (matchesAtFront(forPkg)) {
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
     * Matches other package name exactly or partially at front.
     * @param pkg the package to compare with.
     * @return if it matches.
     */
    private boolean matchesAtFront(final String pkg) {
        final boolean result;
        if (regex) {
            result = patternForPartialMatch.matcher(pkg).matches();
        }
        else {
            result = matchesAtFrontNoRegex(pkg);
        }
        return result;
    }

    /**
     * Non-regex case. Ensure a trailing dot for subpackages, i.e. "com.puppy"
     * will match "com.puppy.crawl" but not "com.puppycrawl.tools".
     * @param pkg the package to compare with.
     * @return if it matches.
     */
    private boolean matchesAtFrontNoRegex(final String pkg) {
        return pkg.startsWith(fullPackage)
                && (pkg.length() == fullPackage.length()
                    || pkg.charAt(fullPackage.length()) == '.');
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
            if (g.isLocalOnly() && !matchesExactly(inPkg)) {
                continue;
            }
            final AccessResult result = g.verifyImport(forImport);
            if (result != AccessResult.UNKNOWN) {
                return result;
            }
        }
        return AccessResult.UNKNOWN;
    }

    /**
     * Check for equality of this with pkg
     * @param pkg the package to compare with.
     * @return if it matches.
     */
    private boolean matchesExactly(final String pkg) {
        final boolean result;
        if (regex) {
            result = patternForExactMatch.matcher(pkg).matches();
        }
        else {
            result = fullPackage.equals(pkg);
        }
        return result;
    }
}
