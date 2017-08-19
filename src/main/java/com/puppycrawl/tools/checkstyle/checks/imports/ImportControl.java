////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
 * Represents a tree of import rules for controlling whether packages or
 * classes are allowed to be used. Each instance must have a single parent or
 * be the root node. Each instance may have zero or more children.
 *
 * @author Oliver Burn
 */
class ImportControl {
    /** The package separator: "." */
    private static final String DOT = ".";
    /** A pattern matching the package separator: "." */
    private static final Pattern DOT_PATTERN = Pattern.compile(DOT, Pattern.LITERAL);
    /** The regex for the package separator: "\\.". */
    private static final String DOT_REGEX = "\\.";
    /** List of {@link AbstractImportRule} objects to check. */
    private final Deque<AbstractImportRule> rules = new LinkedList<>();
    /** List of children {@link ImportControl} objects. */
    private final List<ImportControl> children = new ArrayList<>();
    /** The parent. Null indicates we are the root node. */
    private final ImportControl parent;
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
    /** Strategy in a case if matching allow/disallow rule was not found. */
    private final MismatchStrategy strategyOnMismatch;

    /**
     * Construct a root node.
     * @param pkgName the name of the package.
     * @param regex flags interpretation of pkgName as regex pattern.
     * @param strategyOnMismatch strategy in a case if matching allow/disallow rule was not found.
     */
    ImportControl(String pkgName, boolean regex,
                  MismatchStrategy strategyOnMismatch) {
        parent = null;
        this.regex = regex;
        this.strategyOnMismatch = strategyOnMismatch;
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
     * Construct a root node.
     * @param pkgName the name of the package.
     * @param regex flags interpretation of pkgName as regex pattern.
     */
    ImportControl(String pkgName, boolean regex) {
        this(pkgName, regex, MismatchStrategy.DISALLOWED);
    }

    /**
     * Construct a child node. The concatenation of regular expressions needs special care:
     * see {@link #ensureSelfContainedRegex(String, boolean)} for more details.
     * @param parent the parent node.
     * @param subPkg the sub package name.
     * @param regex flags interpretation of subPkg as regex pattern.
     * @param strategyOnMismatch strategy in a case if matching allow/disallow rule was not found.
     */
    ImportControl(ImportControl parent, String subPkg, boolean regex,
                  MismatchStrategy strategyOnMismatch) {
        this.parent = parent;
        this.strategyOnMismatch = strategyOnMismatch;
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
    }

    /**
     * Construct a child node. The concatenation of regular expressions needs special care:
     * see {@link #ensureSelfContainedRegex(String, boolean)} for more details.
     * @param parent the parent node.
     * @param subPkg the sub package name.
     * @param regex flags interpretation of subPkg as regex pattern.
     */
    ImportControl(ImportControl parent, String subPkg, boolean regex) {
        this(parent, subPkg, regex, MismatchStrategy.DELEGATE_TO_PARENT);
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
    private static String ensureSelfContainedRegex(String input, boolean alreadyRegex) {
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
     * Adds an {@link AbstractImportRule} to the node.
     * @param rule the rule to be added.
     */
    protected void addImportRule(AbstractImportRule rule) {
        rules.addFirst(rule);
    }

    /**
     * Adds new child import control.
     * @param importControl child import control
     */
    public void addChild(ImportControl importControl) {
        children.add(importControl);
    }

    /**
     * Search down the tree to locate the finest match for a supplied package.
     * @param forPkg the package to search for.
     * @return the finest match, or null if no match at all.
     */
    public ImportControl locateFinest(String forPkg) {
        ImportControl finestMatch = null;
        // Check if we are a match.
        if (matchesAtFront(forPkg)) {
            // If there won't be match so I am the best there is.
            finestMatch = this;
            // Check if any of the children match.
            for (ImportControl child : children) {
                final ImportControl match = child.locateFinest(forPkg);
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
    private boolean matchesAtFront(String pkg) {
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
    private boolean matchesAtFrontNoRegex(String pkg) {
        return pkg.startsWith(fullPackage)
                && (pkg.length() == fullPackage.length()
                    || pkg.charAt(fullPackage.length()) == '.');
    }

    /**
     * Returns whether a package or class is allowed to be imported.
     * The algorithm checks with the current node for a result, and if none is
     * found then calls its parent looking for a match. This will recurse
     * looking for match. If there is no clear result then
     * {@link AccessResult#UNKNOWN} is returned.
     * @param forImport the import to check on.
     * @param inPkg the package doing the import.
     * @return an {@link AccessResult}.
     */
    public AccessResult checkAccess(String inPkg, String forImport) {
        final AccessResult result;
        final AccessResult returnValue = localCheckAccess(inPkg, forImport);
        if (returnValue != AccessResult.UNKNOWN) {
            result = returnValue;
        }
        else if (parent == null) {
            if (strategyOnMismatch == MismatchStrategy.ALLOWED) {
                result = AccessResult.ALLOWED;
            }
            else {
                result = AccessResult.DISALLOWED;
            }
        }
        else {
            if (strategyOnMismatch == MismatchStrategy.ALLOWED) {
                result = AccessResult.ALLOWED;
            }
            else if (strategyOnMismatch == MismatchStrategy.DISALLOWED) {
                result = AccessResult.DISALLOWED;
            }
            else {
                result = parent.checkAccess(inPkg, forImport);
            }
        }
        return result;
    }

    /**
     * Checks whether any of the rules for this node control access to
     * a specified package or class.
     * @param forImport the import to check.
     * @param inPkg the package doing the import.
     * @return an {@link AccessResult}.
     */
    private AccessResult localCheckAccess(String inPkg, String forImport) {
        AccessResult localCheckAccessResult = AccessResult.UNKNOWN;
        for (AbstractImportRule importRule : rules) {
            // Check if an import rule is only meant to be applied locally.
            if (!importRule.isLocalOnly() || matchesExactly(inPkg)) {
                final AccessResult result = importRule.verifyImport(forImport);
                if (result != AccessResult.UNKNOWN) {
                    localCheckAccessResult = result;
                    break;
                }
            }
        }
        return localCheckAccessResult;
    }

    /**
     * Check for equality of this with pkg.
     * @param pkg the package to compare with.
     * @return if it matches.
     */
    private boolean matchesExactly(String pkg) {
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
