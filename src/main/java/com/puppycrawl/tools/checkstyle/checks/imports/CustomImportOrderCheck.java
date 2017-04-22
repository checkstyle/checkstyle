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
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that the groups of import declarations appear in the order specified
 * by the user. If there is an import but its group is not specified in the
 * configuration such an import should be placed at the end of the import list.
 * </p>
 * The rule consists of:
 *
 * <p>
 * 1. STATIC group. This group sets the ordering of static imports.
 * </p>
 *
 * <p>
 * 2. SAME_PACKAGE(n) group. This group sets the ordering of the same package imports.
 * Imports are considered on SAME_PACKAGE group if <b>n</b> first domains in package name
 * and import name are identical.
 * </p>
 *
 * <pre>
 *{@code
 *package java.util.concurrent.locks;
 *
 *import java.io.File;
 *import java.util.*; //#1
 *import java.util.List; //#2
 *import java.util.StringTokenizer; //#3
 *import java.util.concurrent.*; //#4
 *import java.util.concurrent.AbstractExecutorService; //#5
 *import java.util.concurrent.locks.LockSupport; //#6
 *import java.util.regex.Pattern; //#7
 *import java.util.regex.Matcher; //#8
 *}
 * </pre>
 *
 * <p>
 * If we have SAME_PACKAGE(3) on configuration file,
 * imports #4-6 will be considered as a SAME_PACKAGE group (java.util.concurrent.*,
 * java.util.concurrent.AbstractExecutorService, java.util.concurrent.locks.LockSupport).
 * SAME_PACKAGE(2) will include #1-8. SAME_PACKAGE(4) will include only #6.
 * SAME_PACKAGE(5) will result in no imports assigned to SAME_PACKAGE group because
 * actual package java.util.concurrent.locks has only 4 domains.
 * </p>
 *
 * <p>
 * 3. THIRD_PARTY_PACKAGE group. This group sets ordering of third party imports.
 * Third party imports are all imports except STATIC,
 * SAME_PACKAGE(n), STANDARD_JAVA_PACKAGE and SPECIAL_IMPORTS.
 * </p>
 *
 * <p>
 * 4. STANDARD_JAVA_PACKAGE group. By default this group sets ordering of standard java/javax
 * imports.
 * </p>
 *
 * <p>
 * 5. SPECIAL_IMPORTS group. This group may contains some imports
 * that have particular meaning for the user.
 * </p>
 *
 * <p>
 * NOTE!
 * </p>
 * <p>
 * Use the separator '###' between rules.
 * </p>
 * <p>
 * To set RegExps for THIRD_PARTY_PACKAGE and STANDARD_JAVA_PACKAGE groups use
 * thirdPartyPackageRegExp and standardPackageRegExp options.
 * </p>
 * <p>
 * Pretty often one import can match more than one group. For example, static import from standard
 * package or regular expressions are configured to allow one import match multiple groups.
 * In this case, group will be assigned according to priorities:
 * </p>
 * <ol>
 * <li>
 *    STATIC has top priority
 * </li>
 * <li>
 *    SAME_PACKAGE has second priority
 * </li>
 * <li>
 *    STANDARD_JAVA_PACKAGE and SPECIAL_IMPORTS will compete using "best match" rule: longer
 *    matching substring wins; in case of the same length, lower position of matching substring
 *    wins; if position is the same, order of rules in configuration solves the puzzle.
 * </li>
 * <li>
 *    THIRD_PARTY has the least priority
 * </li>
 * </ol>
 * <p>
 *    Few examples to illustrate "best match":
 * </p>
 * <p>
 *    1. patterns STANDARD_JAVA_PACKAGE = "Check", SPECIAL_IMPORTS="ImportOrderCheck" and input
 *    file:
 * </p>
 * <pre>
 *{@code
 *import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
 *import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;}
 * </pre>
 * <p>
 *    Result: imports will be assigned to SPECIAL_IMPORTS, because matching substring length is 16.
 *    Matching substring for STANDARD_JAVA_PACKAGE is 5.
 * </p>
 * <p>
 *    2. patterns STANDARD_JAVA_PACKAGE = "Check", SPECIAL_IMPORTS="Avoid" and file:
 * </p>
 * <pre>
 *{@code
 *import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck;}
 * </pre>
 * <p>
 *   Result: import will be assigned to SPECIAL_IMPORTS. Matching substring length is 5 for both
 *   patterns. However, "Avoid" position is lower then "Check" position.
 * </p>
 *
 * <pre>
 *    Properties:
 * </pre>
 * <table summary="Properties" border="1">
 *     <tr><th>name</th><th>Description</th><th>type</th><th>default value</th></tr>
 *      <tr><td>customImportOrderRules</td><td>List of order declaration customizing by user.</td>
 *          <td>string</td><td>null</td></tr>
 *      <tr><td>standardPackageRegExp</td><td>RegExp for STANDARD_JAVA_PACKAGE group imports.</td>
 *          <td>regular expression</td><td>^(java|javax)\.</td></tr>
 *      <tr><td>thirdPartyPackageRegExp</td><td>RegExp for THIRD_PARTY_PACKAGE group imports.</td>
 *          <td>regular expression</td><td>.*</td></tr>
 *      <tr><td>specialImportsRegExp</td><td>RegExp for SPECIAL_IMPORTS group imports.</td>
 *          <td>regular expression</td><td>^$</td></tr>
 *      <tr><td>separateLineBetweenGroups</td><td>Force empty line separator between import groups.
 *          </td><td>boolean</td><td>true</td></tr>
 *      <tr><td>sortImportsInGroupAlphabetically</td><td>Force grouping alphabetically,
 *          in ASCII sort order.</td><td>boolean</td><td>false</td></tr>
 * </table>
 *
 * <p>
 * For example:
 * </p>
 *        <p>To configure the check so that it matches default Eclipse formatter configuration
 *        (tested on Kepler, Luna and Mars):</p>
 *        <ul>
 *          <li>group of static imports is on the top</li>
 *          <li>groups of non-static imports: &quot;java&quot; and &quot;javax&quot; packages
 *          first, then &quot;org&quot; and then all other imports</li>
 *          <li>imports will be sorted in the groups</li>
 *          <li>groups are separated by, at least, one blank line</li>
 *        </ul>
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *        value=&quot;STATIC###STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS&quot;/&gt;
 *    &lt;property name=&quot;specialImportsRegExp&quot; value=&quot;org&quot;/&gt;
 *    &lt;property name=&quot;sortImportsInGroupAlphabetically&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;separateLineBetweenGroups&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 *        <p>To configure the check so that it matches default IntelliJ IDEA formatter
 *        configuration (tested on v14):</p>
 *        <ul>
 *          <li>group of static imports is on the bottom</li>
 *          <li>groups of non-static imports: all imports except of &quot;javax&quot;
 *          and &quot;java&quot;, then &quot;javax&quot; and &quot;java&quot;</li>
 *          <li>imports will be sorted in the groups</li>
 *          <li>groups are separated by, at least, one blank line</li>
 *        </ul>
 *
 *        <p>
 *        Note: &quot;separated&quot; option is disabled because IDEA default has blank line
 *        between &quot;java&quot; and static imports, and no blank line between
 *        &quot;javax&quot; and &quot;java&quot;
 *        </p>
 *
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *        value=&quot;THIRD_PARTY_PACKAGE###SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE
 *        ###STATIC&quot;/&gt;
 *    &lt;property name=&quot;specialImportsRegExp&quot; value=&quot;^javax\.&quot;/&gt;
 *    &lt;property name=&quot;standardPackageRegExp&quot; value=&quot;^java\.&quot;/&gt;
 *    &lt;property name=&quot;sortImportsInGroupAlphabetically&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;separateLineBetweenGroups&quot; value=&quot;false&quot;/&gt;
 *&lt;/module&gt;
 * </pre>
 *
 * <p>To configure the check so that it matches default NetBeans formatter
 *    configuration (tested on v8):</p>
 * <ul>
 *     <li>groups of non-static imports are not defined, all imports will be sorted as a one
 *         group</li>
 *     <li>static imports are not separated, they will be sorted along with other imports</li>
 * </ul>
 *
 * <pre>
 *&lt;module name=&quot;CustomImportOrder&quot;/&gt;
 * </pre>
 * <p>To set RegExps for THIRD_PARTY_PACKAGE and STANDARD_JAVA_PACKAGE groups use
 *         thirdPartyPackageRegExp and standardPackageRegExp options.</p>
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *    value=&quot;STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE&quot;/&gt;
 *    &lt;property name=&quot;thirdPartyPackageRegExp&quot; value=&quot;com|org&quot;/&gt;
 *    &lt;property name=&quot;standardPackageRegExp&quot; value=&quot;^(java|javax)\.&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Also, this check can be configured to force empty line separator between
 * import groups. For example
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;separateLineBetweenGroups&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * It is possible to enforce
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>
 * of imports in groups using the following configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;sortImportsInGroupAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of ASCII order:
 * </p>
 * <pre>
 * {@code
 *import java.awt.Dialog;
 *import java.awt.Window;
 *import java.awt.color.ColorSpace;
 *import java.awt.Frame; // violation here - in ASCII order 'F' should go before 'c',
 *                       // as all uppercase come before lowercase letters}
 * </pre>
 * <p>
 * To force checking imports sequence such as:
 * </p>
 *
 * <pre>
 * {@code
 * package com.puppycrawl.tools.checkstyle.imports;
 *
 * import com.google.common.annotations.GwtCompatible;
 * import com.google.common.annotations.Beta;
 * import com.google.common.annotations.VisibleForTesting;
 *
 * import org.abego.treelayout.Configuration;
 *
 * import static sun.tools.util.ModifierFilter.ALL_ACCESS;
 *
 * import com.google.common.annotations.GwtCompatible; // violation here - should be in the
 *                                                     // THIRD_PARTY_PACKAGE group
 * import android.*;}
 * </pre>
 * configure as follows:
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *    value=&quot;SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS&quot;/&gt;
 *    &lt;property name=&quot;specialImportsRegExp&quot; value=&quot;android.*&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class CustomImportOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_SEPARATOR = "custom.import.order.line.separator";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LEX = "custom.import.order.lex";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_NONGROUP_IMPORT = "custom.import.order.nonGroup.import";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_NONGROUP_EXPECTED = "custom.import.order.nonGroup.expected";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ORDER = "custom.import.order";

    /** STATIC group name. */
    public static final String STATIC_RULE_GROUP = "STATIC";

    /** SAME_PACKAGE group name. */
    public static final String SAME_PACKAGE_RULE_GROUP = "SAME_PACKAGE";

    /** THIRD_PARTY_PACKAGE group name. */
    public static final String THIRD_PARTY_PACKAGE_RULE_GROUP = "THIRD_PARTY_PACKAGE";

    /** STANDARD_JAVA_PACKAGE group name. */
    public static final String STANDARD_JAVA_PACKAGE_RULE_GROUP = "STANDARD_JAVA_PACKAGE";

    /** SPECIAL_IMPORTS group name. */
    public static final String SPECIAL_IMPORTS_RULE_GROUP = "SPECIAL_IMPORTS";

    /** NON_GROUP group name. */
    private static final String NON_GROUP_RULE_GROUP = "NOT_ASSIGNED_TO_ANY_GROUP";

    /** Pattern used to separate groups of imports. */
    private static final Pattern GROUP_SEPARATOR_PATTERN = Pattern.compile("\\s*###\\s*");

    /** List of order declaration customizing by user. */
    private final List<String> customImportOrderRules = new ArrayList<>();

    /** Contains objects with import attributes. */
    private final List<ImportDetails> importToGroupList = new ArrayList<>();

    /** RegExp for SAME_PACKAGE group imports. */
    private String samePackageDomainsRegExp = "";

    /** RegExp for STANDARD_JAVA_PACKAGE group imports. */
    private Pattern standardPackageRegExp = Pattern.compile("^(java|javax)\\.");

    /** RegExp for THIRD_PARTY_PACKAGE group imports. */
    private Pattern thirdPartyPackageRegExp = Pattern.compile(".*");

    /** RegExp for SPECIAL_IMPORTS group imports. */
    private Pattern specialImportsRegExp = Pattern.compile("^$");

    /** Force empty line separator between import groups. */
    private boolean separateLineBetweenGroups = true;

    /** Force grouping alphabetically, in ASCII order. */
    private boolean sortImportsInGroupAlphabetically;

    /** Number of first domains for SAME_PACKAGE group. */
    private int samePackageMatchingDepth = 2;

    /**
     * Sets standardRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setStandardPackageRegExp(Pattern regexp) {
        standardPackageRegExp = regexp;
    }

    /**
     * Sets thirdPartyRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setThirdPartyPackageRegExp(Pattern regexp) {
        thirdPartyPackageRegExp = regexp;
    }

    /**
     * Sets specialImportsRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setSpecialImportsRegExp(Pattern regexp) {
        specialImportsRegExp = regexp;
    }

    /**
     * Sets separateLineBetweenGroups specified by user.
     * @param value
     *        user value.
     */
    public final void setSeparateLineBetweenGroups(boolean value) {
        separateLineBetweenGroups = value;
    }

    /**
     * Sets sortImportsInGroupAlphabetically specified by user.
     * @param value
     *        user value.
     */
    public final void setSortImportsInGroupAlphabetically(boolean value) {
        sortImportsInGroupAlphabetically = value;
    }

    /**
     * Sets a custom import order from the rules in the string format specified
     * by user.
     * @param inputCustomImportOrder
     *        user value.
     */
    public final void setCustomImportOrderRules(final String inputCustomImportOrder) {
        customImportOrderRules.clear();
        for (String currentState : GROUP_SEPARATOR_PATTERN.split(inputCustomImportOrder)) {
            addRulesToList(currentState);
        }
        customImportOrderRules.add(NON_GROUP_RULE_GROUP);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        importToGroupList.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            if (customImportOrderRules.contains(SAME_PACKAGE_RULE_GROUP)) {
                samePackageDomainsRegExp = createSamePackageRegexp(
                        samePackageMatchingDepth, ast);
            }
        }
        else {
            final String importFullPath = getFullImportIdent(ast);
            final int lineNo = ast.getLineNo();
            final boolean isStatic = ast.getType() == TokenTypes.STATIC_IMPORT;
            importToGroupList.add(new ImportDetails(importFullPath,
                    lineNo, getImportGroup(isStatic, importFullPath),
                    isStatic));
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {

        if (!importToGroupList.isEmpty()) {
            finishImportList();
        }
    }

    /** Examine the order of all the imports and log any violations. */
    private void finishImportList() {
        final ImportDetails firstImport = importToGroupList.get(0);
        String currentGroup = getImportGroup(firstImport.isStaticImport(),
                firstImport.getImportFullPath());
        int currentGroupNumber = customImportOrderRules.indexOf(currentGroup);
        String previousImportFromCurrentGroup = null;

        for (ImportDetails importObject : importToGroupList) {
            final String importGroup = importObject.getImportGroup();
            final String fullImportIdent = importObject.getImportFullPath();

            if (getCountOfEmptyLinesBefore(importObject.getLineNumber()) > 1) {
                log(importObject.getLineNumber(), MSG_LINE_SEPARATOR, fullImportIdent);
            }
            if (importGroup.equals(currentGroup)) {
                if (sortImportsInGroupAlphabetically
                        && previousImportFromCurrentGroup != null
                        && compareImports(fullImportIdent, previousImportFromCurrentGroup) < 0) {
                    log(importObject.getLineNumber(), MSG_LEX,
                            fullImportIdent, previousImportFromCurrentGroup);
                }
                else {
                    previousImportFromCurrentGroup = fullImportIdent;
                }
            }
            else {
                //not the last group, last one is always NON_GROUP
                if (customImportOrderRules.size() > currentGroupNumber + 1) {
                    final String nextGroup = getNextImportGroup(currentGroupNumber + 1);
                    if (importGroup.equals(nextGroup)) {
                        if (separateLineBetweenGroups
                                && getCountOfEmptyLinesBefore(importObject.getLineNumber()) == 0) {
                            log(importObject.getLineNumber(), MSG_LINE_SEPARATOR, fullImportIdent);
                        }
                        currentGroup = nextGroup;
                        currentGroupNumber = customImportOrderRules.indexOf(nextGroup);
                        previousImportFromCurrentGroup = fullImportIdent;
                    }
                    else {
                        logWrongImportGroupOrder(importObject.getLineNumber(),
                                importGroup, nextGroup, fullImportIdent);
                    }
                }
                else {
                    logWrongImportGroupOrder(importObject.getLineNumber(),
                            importGroup, currentGroup, fullImportIdent);
                }
            }
        }
    }

    /**
     * Log wrong import group order.
     * @param currentImportLine
     *        line number of current import current import.
     * @param importGroup
     *        import group.
     * @param currentGroupNumber
     *        current group number we are checking.
     * @param fullImportIdent
     *        full import name.
     */
    private void logWrongImportGroupOrder(int currentImportLine, String importGroup,
            String currentGroupNumber, String fullImportIdent) {
        if (NON_GROUP_RULE_GROUP.equals(importGroup)) {
            log(currentImportLine, MSG_NONGROUP_IMPORT, fullImportIdent);
        }
        else if (NON_GROUP_RULE_GROUP.equals(currentGroupNumber)) {
            log(currentImportLine, MSG_NONGROUP_EXPECTED, importGroup, fullImportIdent);
        }
        else {
            log(currentImportLine, MSG_ORDER, importGroup, currentGroupNumber, fullImportIdent);
        }
    }

    /**
     * Get next import group.
     * @param currentGroupNumber
     *        current group number.
     * @return
     *        next import group.
     */
    private String getNextImportGroup(int currentGroupNumber) {
        int nextGroupNumber = currentGroupNumber;

        while (customImportOrderRules.size() > nextGroupNumber + 1) {
            if (hasAnyImportInCurrentGroup(customImportOrderRules.get(nextGroupNumber))) {
                break;
            }
            nextGroupNumber++;
        }
        return customImportOrderRules.get(nextGroupNumber);
    }

    /**
     * Checks if current group contains any import.
     * @param currentGroup
     *        current group.
     * @return
     *        true, if current group contains at least one import.
     */
    private boolean hasAnyImportInCurrentGroup(String currentGroup) {
        boolean result = false;
        for (ImportDetails currentImport : importToGroupList) {
            if (currentGroup.equals(currentImport.getImportGroup())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Get import valid group.
     * @param isStatic
     *        is static import.
     * @param importPath
     *        full import path.
     * @return import valid group.
     */
    private String getImportGroup(boolean isStatic, String importPath) {
        RuleMatchForImport bestMatch = new RuleMatchForImport(NON_GROUP_RULE_GROUP, 0, 0);
        if (isStatic && customImportOrderRules.contains(STATIC_RULE_GROUP)) {
            bestMatch.group = STATIC_RULE_GROUP;
            bestMatch.matchLength = importPath.length();
        }
        else if (customImportOrderRules.contains(SAME_PACKAGE_RULE_GROUP)) {
            final String importPathTrimmedToSamePackageDepth =
                    getFirstDomainsFromIdent(samePackageMatchingDepth, importPath);
            if (samePackageDomainsRegExp.equals(importPathTrimmedToSamePackageDepth)) {
                bestMatch.group = SAME_PACKAGE_RULE_GROUP;
                bestMatch.matchLength = importPath.length();
            }
        }
        if (bestMatch.group.equals(NON_GROUP_RULE_GROUP)) {
            for (String group : customImportOrderRules) {
                if (STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(group)) {
                    bestMatch = findBetterPatternMatch(importPath,
                            STANDARD_JAVA_PACKAGE_RULE_GROUP, standardPackageRegExp, bestMatch);
                }
                if (SPECIAL_IMPORTS_RULE_GROUP.equals(group)) {
                    bestMatch = findBetterPatternMatch(importPath,
                            SPECIAL_IMPORTS_RULE_GROUP, specialImportsRegExp, bestMatch);
                }
            }
        }
        if (bestMatch.group.equals(NON_GROUP_RULE_GROUP)
                && customImportOrderRules.contains(THIRD_PARTY_PACKAGE_RULE_GROUP)
                && thirdPartyPackageRegExp.matcher(importPath).find()) {
            bestMatch.group = THIRD_PARTY_PACKAGE_RULE_GROUP;
        }
        return bestMatch.group;
    }

    /** Tries to find better matching regular expression:
     * longer matching substring wins; in case of the same length,
     * lower position of matching substring wins.
     * @param importPath
     *      Full import identifier
     * @param group
     *      Import group we are trying to assign the import
     * @param regExp
     *      Regular expression for import group
     * @param currentBestMatch
     *      object with currently best match
     * @return better match (if found) or the same (currentBestMatch)
     */
    private static RuleMatchForImport findBetterPatternMatch(String importPath, String group,
            Pattern regExp, RuleMatchForImport currentBestMatch) {
        RuleMatchForImport betterMatchCandidate = currentBestMatch;
        final Matcher matcher = regExp.matcher(importPath);
        while (matcher.find()) {
            final int length = matcher.end() - matcher.start();
            if (length > betterMatchCandidate.matchLength
                    || length == betterMatchCandidate.matchLength
                        && matcher.start() < betterMatchCandidate.matchPosition) {
                betterMatchCandidate = new RuleMatchForImport(group, length, matcher.start());
            }
        }
        return betterMatchCandidate;
    }

    /**
     * Checks compare two import paths.
     * @param import1
     *        current import.
     * @param import2
     *        previous import.
     * @return a negative integer, zero, or a positive integer as the
     *        specified String is greater than, equal to, or less
     *        than this String, ignoring case considerations.
     */
    private static int compareImports(String import1, String import2) {
        int result = 0;
        final String separator = "\\.";
        final String[] import1Tokens = import1.split(separator);
        final String[] import2Tokens = import2.split(separator);
        for (int i = 0; i < import1Tokens.length && i != import2Tokens.length; i++) {
            final String import1Token = import1Tokens[i];
            final String import2Token = import2Tokens[i];
            result = import1Token.compareTo(import2Token);
            if (result != 0) {
                break;
            }
        }
        return result;
    }

    /**
     * Counts empty lines before given.
     * @param lineNo
     *        Line number of current import.
     * @return count of empty lines before given.
     */
    private int getCountOfEmptyLinesBefore(int lineNo) {
        int result = 0;
        final String[] lines = getLines();
        //  [lineNo - 2] is the number of the previous line
        //  because the numbering starts from zero.
        int lineBeforeIndex = lineNo - 2;
        while (lineBeforeIndex >= 0 && lines[lineBeforeIndex].trim().isEmpty()) {
            lineBeforeIndex--;
            result++;
        }
        return result;
    }

    /**
     * Forms import full path.
     * @param token
     *        current token.
     * @return full path or null.
     */
    private static String getFullImportIdent(DetailAST token) {
        String ident = "";
        if (token != null) {
            ident = FullIdent.createFullIdent(token.findFirstToken(TokenTypes.DOT)).getText();
        }
        return ident;
    }

    /**
     * Parses ordering rule and adds it to the list with rules.
     * @param ruleStr
     *        String with rule.
     */
    private void addRulesToList(String ruleStr) {
        if (STATIC_RULE_GROUP.equals(ruleStr)
                || THIRD_PARTY_PACKAGE_RULE_GROUP.equals(ruleStr)
                || STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(ruleStr)
                || SPECIAL_IMPORTS_RULE_GROUP.equals(ruleStr)) {
            customImportOrderRules.add(ruleStr);

        }
        else if (ruleStr.startsWith(SAME_PACKAGE_RULE_GROUP)) {

            final String rule = ruleStr.substring(ruleStr.indexOf('(') + 1,
                    ruleStr.indexOf(')'));
            samePackageMatchingDepth = Integer.parseInt(rule);
            if (samePackageMatchingDepth <= 0) {
                throw new IllegalArgumentException(
                        "SAME_PACKAGE rule parameter should be positive integer: " + ruleStr);
            }
            customImportOrderRules.add(SAME_PACKAGE_RULE_GROUP);

        }
        else {
            throw new IllegalStateException("Unexpected rule: " + ruleStr);
        }
    }

    /**
     * Creates samePackageDomainsRegExp of the first package domains.
     * @param firstPackageDomainsCount
     *        number of first package domains.
     * @param packageNode
     *        package node.
     * @return same package regexp.
     */
    private static String createSamePackageRegexp(int firstPackageDomainsCount,
             DetailAST packageNode) {
        final String packageFullPath = getFullImportIdent(packageNode);
        return getFirstDomainsFromIdent(firstPackageDomainsCount, packageFullPath);
    }

    /**
     * Extracts defined amount of domains from the left side of package/import identifier
     * @param firstPackageDomainsCount
     *        number of first package domains.
     * @param packageFullPath
     *        full identifier containing path to package or imported object.
     * @return String with defined amount of domains or full identifier
     *        (if full identifier had less domain then specified)
     */
    private static String getFirstDomainsFromIdent(
            final int firstPackageDomainsCount, final String packageFullPath) {
        final StringBuilder builder = new StringBuilder();
        final StringTokenizer tokens = new StringTokenizer(packageFullPath, ".");
        int count = firstPackageDomainsCount;

        while (count > 0 && tokens.hasMoreTokens()) {
            builder.append(tokens.nextToken()).append('.');
            count--;
        }
        return builder.toString();
    }

    /**
     * Contains import attributes as line number, import full path, import
     * group.
     * @author max
     */
    private static class ImportDetails {
        /** Import full path. */
        private final String importFullPath;

        /** Import line number. */
        private final int lineNumber;

        /** Import group. */
        private final String importGroup;

        /** Is static import. */
        private final boolean staticImport;

        /**
         * @param importFullPath
         *        import full path.
         * @param lineNumber
         *        import line number.
         * @param importGroup
         *        import group.
         * @param staticImport
         *        if import is static.
         */
        ImportDetails(String importFullPath,
                int lineNumber, String importGroup, boolean staticImport) {
            this.importFullPath = importFullPath;
            this.lineNumber = lineNumber;
            this.importGroup = importGroup;
            this.staticImport = staticImport;
        }

        /**
         * Get import full path variable.
         * @return import full path variable.
         */
        public String getImportFullPath() {
            return importFullPath;
        }

        /**
         * Get import line number.
         * @return import line.
         */
        public int getLineNumber() {
            return lineNumber;
        }

        /**
         * Get import group.
         * @return import group.
         */
        public String getImportGroup() {
            return importGroup;
        }

        /**
         * Checks if import is static.
         * @return true, if import is static.
         */
        public boolean isStaticImport() {
            return staticImport;
        }
    }

    /**
     * Contains matching attributes assisting in definition of "best matching"
     * group for import.
     * @author ivanov-alex
     */
    private static class RuleMatchForImport {
        /** Position of matching string for current best match. */
        private final int matchPosition;
        /** Length of matching string for current best match. */
        private int matchLength;
        /** Import group for current best match. */
        private String group;

        /** Constructor to initialize the fields.
         * @param group
         *        Matched group.
         * @param length
         *        Matching length.
         * @param position
         *        Matching position.
         */
        RuleMatchForImport(String group, int length, int position) {
            this.group = group;
            matchLength = length;
            matchPosition = position;
        }
    }
}
