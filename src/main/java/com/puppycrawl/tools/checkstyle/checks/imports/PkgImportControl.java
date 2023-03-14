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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a tree of import rules for a specific package.
 * Each instance may have zero or more children. A child may
 * be a sub-package, a class, or an allow/disallow rule.
 */
class PkgImportControl extends AbstractImportControl {
    /** The package separator: "." */
    private static final String DOT = ".";

    /** The regex for the package separator: "\\.". */
    private static final String DOT_REGEX = "\\.";

    /** A pattern matching the package separator: "\." */
    private static final Pattern DOT_REGEX_PATTERN = Pattern.compile(DOT_REGEX);

    /** The regex for the escaped package separator: "\\\\.". */
    private static final String DOT_ESCAPED_REGEX = "\\\\.";

    /** List of children {@link AbstractImportControl} objects. */
    private final List<AbstractImportControl> children = new ArrayList<>();

    /** The full name for the package. */
    private final String fullPackageName;
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
     * Construct a root, package node.
     *
     * @param packageName the name of the package.
     * @param regex flags interpretation of name as regex pattern.
     * @param strategyOnMismatch strategy in a case if matching allow/disallow rule was not found.
     */
    /* package */ PkgImportControl(String packageName, boolean regex,
            MismatchStrategy strategyOnMismatch) {
        super(null, strategyOnMismatch);

        this.regex = regex;
        if (regex) {
            // ensure that fullName is a self-contained regular expression
            fullPackageName = encloseInGroup(packageName);
            patternForPartialMatch = createPatternForPartialMatch(fullPackageName);
            patternForExactMatch = createPatternForExactMatch(fullPackageName);
        }
        else {
            fullPackageName = packageName;
            patternForPartialMatch = null;
            patternForExactMatch = null;
        }
    }

    /**
     * Construct a sub-package node. The concatenation of regular expressions needs special care:
     * see {@link #ensureSelfContainedRegex(String, boolean)} for more details.
     *
     * @param parent the parent package.
     * @param subPackageName the name of the current sub-package.
     * @param regex flags interpretation of name as regex pattern.
     * @param strategyOnMismatch strategy in a case if matching allow/disallow rule was not found.
     */
    /* package */ PkgImportControl(PkgImportControl parent, String subPackageName, boolean regex,
            MismatchStrategy strategyOnMismatch) {
        super(parent, strategyOnMismatch);
        if (regex || parent.regex) {
            // regex gets inherited
            final String parentRegex = ensureSelfContainedRegex(parent.fullPackageName,
                    parent.regex);
            final String thisRegex = ensureSelfContainedRegex(subPackageName, regex);
            fullPackageName = parentRegex + DOT_REGEX + thisRegex;
            patternForPartialMatch = createPatternForPartialMatch(fullPackageName);
            patternForExactMatch = createPatternForExactMatch(fullPackageName);
            this.regex = true;
        }
        else {
            fullPackageName = parent.fullPackageName + DOT + subPackageName;
            patternForPartialMatch = null;
            patternForExactMatch = null;
            this.regex = false;
        }
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
     *
     * @param expression the input regular expression
     * @return a grouped pattern.
     */
    private static String encloseInGroup(String expression) {
        return "(?:" + expression + ")";
    }

    /**
     * Converts a normal package name into a regex pattern by escaping all
     * special characters that may occur in a java package name.
     *
     * @param input the input string.
     * @return a regex string.
     */
    private static String toRegex(String input) {
        return DOT_REGEX_PATTERN.matcher(input).replaceAll(DOT_ESCAPED_REGEX);
    }

    /**
     * Creates a Pattern from {@code expression} that matches exactly and child packages.
     *
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
     *
     * @param expression a self-contained regular expression matching the full package exactly.
     * @return a Pattern.
     */
    private static Pattern createPatternForExactMatch(String expression) {
        return Pattern.compile(expression);
    }

    @Override
    public AbstractImportControl locateFinest(String forPkg, String forFileName) {
        AbstractImportControl finestMatch = null;
        // Check if we are a match.
        if (matchesAtFront(forPkg)) {
            // If there won't be match, so I am the best there is.
            finestMatch = this;
            // Check if any of the children match.
            for (AbstractImportControl child : children) {
                final AbstractImportControl match = child.locateFinest(forPkg, forFileName);
                if (match != null) {
                    finestMatch = match;
                    break;
                }
            }
        }
        return finestMatch;
    }

    /**
     * Adds new child import control.
     *
     * @param importControl child import control
     */
    public void addChild(AbstractImportControl importControl) {
        children.add(importControl);
    }

    /**
     * Matches other package name exactly or partially at front.
     *
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
     *
     * @param pkg the package to compare with.
     * @return if it matches.
     */
    private boolean matchesAtFrontNoRegex(String pkg) {
        final int length = fullPackageName.length();
        return pkg.startsWith(fullPackageName)
                && (pkg.length() == length || pkg.charAt(length) == '.');
    }

    @Override
    protected boolean matchesExactly(String pkg, String fileName) {
        final boolean result;
        if (regex) {
            result = patternForExactMatch.matcher(pkg).matches();
        }
        else {
            result = fullPackageName.equals(pkg);
        }
        return result;
    }
}
