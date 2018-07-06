////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Checks the ordering/grouping of imports. Features are:
 * <ul>
 * <li>groups imports: ensures that groups of imports come in a specific order
 * (e.g., java. comes first, javax. comes second, then everything else)</li>
 * <li>adds a separation between groups : ensures that a blank line sit between
 * each group</li>
 * <li>import groups aren't separated internally: ensures that
 * each group aren't separated internally by blank line or comment</li>
 * <li>sorts imports inside each group: ensures that imports within each group
 * are in lexicographic order</li>
 * <li>sorts according to case: ensures that the comparison between import is
 * case sensitive</li>
 * <li>groups static imports: ensures that static imports are at the top (or the
 * bottom) of all the imports, or above (or under) each group, or are treated
 * like non static imports (@see {@link ImportOrderOption}</li>
 * </ul>.
 *
 * <pre>
 * Properties:
 * </pre>
 * <table summary="Properties" border="1">
 *   <tr><th>name</th><th>Description</th><th>type</th><th>default value</th></tr>
 *   <tr><td>option</td><td>policy on the relative order between regular imports and static
 *       imports</td><td>{@link ImportOrderOption}</td><td>under</td></tr>
 *   <tr><td>groups</td><td>list of type import groups (every group identified either by a
 *       common prefix string, or by a regular expression enclosed in forward slashes
 *       (e.g. /regexp/). All type imports, which does not match any group,
 *       falls into an additional group, located at the end. Thus, the empty list of type groups
 *       (the default value) means one group for all type imports</td>
 *       <td>list of strings</td><td>empty list</td></tr>
 *   <tr><td>ordered</td><td>whether type imports within group should be sorted</td>
 *       <td>Boolean</td><td>true</td></tr>
 *   <tr><td>separated</td><td>whether type imports groups should be separated by, at least,
 *       one blank line or comment and aren't separated internally
 *       </td><td>Boolean</td><td>false</td></tr>
 *   <tr><td>separatedStaticGroups</td><td>whether static imports should be separated by, at least,
 *       one blank line or comment and aren't separated internally
 *       </td><td>Boolean</td><td>false</td></tr>
 *   <tr><td>caseSensitive</td><td>whether string comparison should be case sensitive or not.
 *       Case sensitive sorting is in ASCII sort order</td><td>Boolean</td><td>true</td></tr>
 *   <tr><td>staticGroups</td><td>list of static import groups (every group identified either by a
 *       common prefix string, or by a regular expression enclosed in forward slashes
 *       (e.g. /regexp/). All static imports, which does not match any group,
 *       falls into an additional group, located at the end. Thus, the empty list of static groups
 *       (the default value) means one group for all static imports</td>
 *       <td>list of strings</td><td>empty list</td></tr>
 *   <tr><td>sortStaticImportsAlphabetically</td><td>whether static imports located at top or
 *       bottom are sorted within the group.</td><td>Boolean</td><td>false</td></tr>
 *   <tr><td>useContainerOrderingForStatic</td><td>whether to use container ordering
 *       (Eclipse IDE term) for static imports or not</td><td>Boolean</td><td>false</td></tr>
 * </table>
 *
 * <p>
 * Example:
 * </p>
 * <p>To configure the check so that it matches default Eclipse formatter configuration
 *    (tested on Kepler, Luna and Mars):</p>
 * <ul>
 *     <li>group of static imports is on the top</li>
 *     <li>groups of non-static imports: &quot;java&quot; then &quot;javax&quot;
 *         packages first, then &quot;org&quot; and then all other imports</li>
 *     <li>imports will be sorted in the groups</li>
 *     <li>groups are separated by, at least, one blank line and aren't separated internally</li>
 * </ul>
 *
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *    &lt;property name=&quot;groups&quot; value=&quot;/^javax?\./,org&quot;/&gt;
 *    &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;separated&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;option&quot; value=&quot;above&quot;/&gt;
 *    &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>To configure the check so that it matches default IntelliJ IDEA formatter configuration
 *    (tested on v14):</p>
 * <ul>
 *     <li>group of static imports is on the bottom</li>
 *     <li>groups of non-static imports: all imports except of &quot;javax&quot; and
 *         &quot;java&quot;, then &quot;javax&quot; and &quot;java&quot;</li>
 *     <li>imports will be sorted in the groups</li>
 *     <li>groups are separated by, at least, one blank line and aren't separated internally</li>
 * </ul>
 *
 *         <p>
 *         Note: &quot;separated&quot; option is disabled because IDEA default has blank line
 *         between &quot;java&quot; and static imports, and no blank line between
 *         &quot;javax&quot; and &quot;java&quot;
 *         </p>
 *
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *     &lt;property name=&quot;groups&quot; value=&quot;*,javax,java&quot;/&gt;
 *     &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *     &lt;property name=&quot;separated&quot; value=&quot;false&quot;/&gt;
 *     &lt;property name=&quot;option&quot; value=&quot;bottom&quot;/&gt;
 *     &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>To configure the check so that it matches default NetBeans formatter configuration
 *    (tested on v8):</p>
 * <ul>
 *     <li>groups of non-static imports are not defined, all imports will be sorted
 *         as a one group</li>
 *     <li>static imports are not separated, they will be sorted along with other imports</li>
 * </ul>
 *
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *     &lt;property name=&quot;option&quot; value=&quot;inflow&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * Group descriptions enclosed in slashes are interpreted as regular
 * expressions. If multiple groups match, the one matching a longer
 * substring of the imported name will take precedence, with ties
 * broken first in favor of earlier matches and finally in favor of
 * the first matching group.
 * </p>
 *
 * <p>
 * There is always a wildcard group to which everything not in a named group
 * belongs. If an import does not match a named group, the group belongs to
 * this wildcard group. The wildcard group position can be specified using the
 * {@code *} character.
 * </p>
 *
 * <p>Check also has on option making it more flexible:
 * <b>sortStaticImportsAlphabetically</b> - sets whether static imports grouped by
 * <b>top</b> or <b>bottom</b> option should be sorted alphabetically or
 * not, default value is <b>false</b>. It is applied to static imports grouped
 * with <b>top</b> or <b>bottom</b> options.<br>
 * This option is helping in reconciling of this Check and other tools like
 * Eclipse's Organize Imports feature.
 * </p>
 * <p>
 * To configure the Check allows static imports grouped to the <b>top</b>
 * being sorted alphabetically:
 * </p>
 *
 * <pre>
 * {@code
 * import static java.lang.Math.abs;
 * import static org.abego.treelayout.Configuration.AlignmentInLevel; // OK, alphabetical order
 *
 * import org.abego.*;
 *
 * import java.util.Set;
 *
 * public class SomeClass { ... }
 * }
 * </pre>
 *
 *
 */
@FileStatefulCheck
public class ImportOrderCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEPARATION = "import.separation";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ORDERING = "import.ordering";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEPARATED_IN_GROUP = "import.groups.separated.internally";

    /** The special wildcard that catches all remaining groups. */
    private static final String WILDCARD_GROUP_NAME = "*";

    /** Empty array of pattern type needed to initialize check. */
    private static final Pattern[] EMPTY_PATTERN_ARRAY = new Pattern[0];

    /** List of type import groups specified by the user. */
    private Pattern[] groups = EMPTY_PATTERN_ARRAY;
    /** List of static import groups specified by the user. */
    private Pattern[] staticGroups = EMPTY_PATTERN_ARRAY;
    /** Require imports in group be separated. */
    private boolean separated;
    /** Require static imports in group be separated. */
    private boolean separatedStaticGroups;
    /** Require imports in group. */
    private boolean ordered = true;
    /** Should comparison be case sensitive. */
    private boolean caseSensitive = true;

    /** Last imported group. */
    private int lastGroup;
    /** Line number of last import. */
    private int lastImportLine;
    /** Name of last import. */
    private String lastImport;
    /** If last import was static. */
    private boolean lastImportStatic;
    /** Whether there was any imports. */
    private boolean beforeFirstImport;
    /** Whether static and type import groups should be split apart.
     * When the {@code option} property is set to {@code INFLOW}, {@code BELOW} or {@code UNDER},
     * both the type and static imports use the properties {@code groups} and {@code separated}.
     * When the {@code option} property is set to {@code TOP} or {@code BOTTOM}, static imports
     * uses the properties {@code staticGroups} and {@code separatedStaticGroups}.
     **/
    private boolean staticImportsApart;
    /** Whether static imports should be sorted alphabetically or not. */
    private boolean sortStaticImportsAlphabetically;
    /** Whether to use container ordering (Eclipse IDE term) for static imports or not. */
    private boolean useContainerOrderingForStatic;

    /** The policy to enforce. */
    private ImportOrderOption option = ImportOrderOption.UNDER;

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = ImportOrderOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + optionStr, iae);
        }
    }

    /**
     * Sets the list of package groups for type imports and the order they should occur in the
     * file.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     */
    public void setGroups(String... packageGroups) {
        groups = compilePatterns(packageGroups);
    }

    /**
     * Sets the list of package groups for static imports and the order they should occur in the
     * file. This property has effect only when the property {@code option} is set to {@code top}
     * or {@code bottom}.)
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     */
    public void setStaticGroups(String... packageGroups) {
        staticGroups = compilePatterns(packageGroups);
    }

    /**
     * Sets whether or not imports should be ordered within any one group of
     * imports.
     *
     * @param ordered
     *            whether lexicographic ordering of imports within a group
     *            required or not.
     */
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Sets whether or not groups of type imports must be separated from one another
     * by at least one blank line or comment.
     *
     * @param separated
     *            whether groups should be separated by one blank line or comment.
     */
    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    /**
     * Sets whether or not groups of static imports must be separated from one another
     * by at least one blank line or comment. This property has effect only when the property
     * {@code option} is set to {@code top} or {@code bottom}.)
     *
     * @param separatedStaticGroups
     *            whether groups should be separated by one blank line or comment.
     */
    public void setSeparatedStaticGroups(boolean separatedStaticGroups) {
        this.separatedStaticGroups = separatedStaticGroups;
    }

    /**
     * Sets whether string comparison should be case sensitive or not.
     *
     * @param caseSensitive
     *            whether string comparison should be case sensitive.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Sets whether static imports (when grouped using 'top' and 'bottom' option)
     * are sorted alphabetically or according to the package groupings.
     * @param sortAlphabetically true or false.
     */
    public void setSortStaticImportsAlphabetically(boolean sortAlphabetically) {
        sortStaticImportsAlphabetically = sortAlphabetically;
    }

    /**
     * Sets whether to use container ordering (Eclipse IDE term) for static imports or not.
     * @param useContainerOrdering whether to use container ordering for static imports or not.
     */
    public void setUseContainerOrderingForStatic(boolean useContainerOrdering) {
        useContainerOrderingForStatic = useContainerOrdering;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.IMPORT};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lastGroup = Integer.MIN_VALUE;
        lastImportLine = Integer.MIN_VALUE;
        lastImport = "";
        lastImportStatic = false;
        beforeFirstImport = true;
        staticImportsApart =
            option == ImportOrderOption.TOP || option == ImportOrderOption.BOTTOM;
    }

    // -@cs[CyclomaticComplexity] SWITCH was transformed into IF-ELSE.
    @Override
    public void visitToken(DetailAST ast) {
        final int line = ast.getLineNo();
        final FullIdent ident;
        final boolean isStatic;

        if (ast.getType() == TokenTypes.IMPORT) {
            ident = FullIdent.createFullIdentBelow(ast);
            isStatic = false;
        }
        else {
            ident = FullIdent.createFullIdent(ast.getFirstChild()
                    .getNextSibling());
            isStatic = true;
        }

        // using set of IF instead of SWITCH to analyze Enum options to satisfy coverage.
        // https://github.com/checkstyle/checkstyle/issues/1387
        if (option == ImportOrderOption.TOP || option == ImportOrderOption.ABOVE) {
            final boolean isStaticAndNotLastImport = isStatic && !lastImportStatic;
            doVisitToken(ident, isStatic, isStaticAndNotLastImport, line);
        }
        else if (option == ImportOrderOption.BOTTOM || option == ImportOrderOption.UNDER) {
            final boolean isLastImportAndNonStatic = lastImportStatic && !isStatic;
            doVisitToken(ident, isStatic, isLastImportAndNonStatic, line);
        }
        else if (option == ImportOrderOption.INFLOW) {
            // "previous" argument is useless here
            doVisitToken(ident, isStatic, true, line);
        }
        else {
            throw new IllegalStateException(
                    "Unexpected option for static imports: " + option);
        }

        lastImportLine = ast.findFirstToken(TokenTypes.SEMI).getLineNo();
        lastImportStatic = isStatic;
        beforeFirstImport = false;
    }

    /**
     * Shares processing...
     *
     * @param ident the import to process.
     * @param isStatic whether the token is static or not.
     * @param previous previous non-static but current is static (above), or
     *                  previous static but current is non-static (under).
     * @param line the line of the current import.
     */
    private void doVisitToken(FullIdent ident, boolean isStatic, boolean previous, int line) {
        final String name = ident.getText();
        final int groupIdx = getGroupNumber(isStatic && staticImportsApart, name);

        if (groupIdx > lastGroup) {
            if (!beforeFirstImport && line - lastImportLine < 2 && needSeparator(isStatic)) {
                log(line, MSG_SEPARATION, name);
            }
        }
        else if (groupIdx == lastGroup) {
            doVisitTokenInSameGroup(isStatic, previous, name, line);
        }
        else {
            log(line, MSG_ORDERING, name);
        }
        if (isSeparatorInGroup(groupIdx, isStatic, line)) {
            log(line, MSG_SEPARATED_IN_GROUP, name);
        }

        lastGroup = groupIdx;
        lastImport = name;
    }

    /**
     * Checks whether import groups should be separated.
     * @param isStatic whether the token is static or not.
     * @return true if imports groups should be separated.
     */
    private boolean needSeparator(boolean isStatic) {
        final boolean typeImportSeparator = !isStatic && separated;
        final boolean staticImportSeparator;
        if (staticImportsApart) {
            staticImportSeparator = isStatic && separatedStaticGroups;
        }
        else {
            staticImportSeparator = isStatic && separated;
        }
        final boolean separatorBetween = isStatic != lastImportStatic
            && (separated || separatedStaticGroups) && staticImportsApart;

        return typeImportSeparator || staticImportSeparator || separatorBetween;
    }

    /**
     * Checks whether imports group separated internally.
     * @param groupIdx group number.
     * @param isStatic whether the token is static or not.
     * @param line the line of the current import.
     * @return true if imports group are separated internally.
     */
    private boolean isSeparatorInGroup(int groupIdx, boolean isStatic, int line) {
        final boolean inSameGroup = groupIdx == lastGroup;
        return (inSameGroup || !needSeparator(isStatic)) && isSeparatorBeforeImport(line);
    }

    /**
     * Checks whether there is any separator before current import.
     * @param line the line of the current import.
     * @return true if there is separator before current import which isn't the first import.
     */
    private boolean isSeparatorBeforeImport(int line) {
        return !beforeFirstImport && line - lastImportLine > 1;
    }

    /**
     * Shares processing...
     *
     * @param isStatic whether the token is static or not.
     * @param previous previous non-static but current is static (above), or
     *     previous static but current is non-static (under).
     * @param name the name of the current import.
     * @param line the line of the current import.
     */
    private void doVisitTokenInSameGroup(boolean isStatic,
            boolean previous, String name, int line) {
        if (ordered) {
            if (option == ImportOrderOption.INFLOW) {
                if (isWrongOrder(name, isStatic)) {
                    log(line, MSG_ORDERING, name);
                }
            }
            else {
                final boolean shouldFireError =
                    // previous non-static but current is static (above)
                    // or
                    // previous static but current is non-static (under)
                    previous
                        ||
                        // current and previous static or current and
                        // previous non-static
                        lastImportStatic == isStatic
                    && isWrongOrder(name, isStatic);

                if (shouldFireError) {
                    log(line, MSG_ORDERING, name);
                }
            }
        }
    }

    /**
     * Checks whether import name is in wrong order.
     * @param name import name.
     * @param isStatic whether it is a static import name.
     * @return true if import name is in wrong order.
     */
    private boolean isWrongOrder(String name, boolean isStatic) {
        final boolean result;
        if (isStatic) {
            if (useContainerOrderingForStatic) {
                result = compareContainerOrder(lastImport, name, caseSensitive) >= 0;
            }
            else if (staticImportsApart) {
                result = sortStaticImportsAlphabetically
                    && compare(lastImport, name, caseSensitive) >= 0;
            }
            else {
                result = compare(lastImport, name, caseSensitive) >= 0;
            }
        }
        else {
            // out of lexicographic order
            result = compare(lastImport, name, caseSensitive) >= 0;
        }
        return result;
    }

    /**
     * Compares two import strings.
     * We first compare the container of the static import, container being the type enclosing
     * the static element being imported. When this returns 0, we compare the qualified
     * import name. For e.g. this is what is considered to be container names:
     * <p>
     * import static HttpConstants.COLON     => HttpConstants
     * import static HttpHeaders.addHeader   => HttpHeaders
     * import static HttpHeaders.setHeader   => HttpHeaders
     * import static HttpHeaders.Names.DATE  => HttpHeaders.Names
     * </p>
     * <p>
     * According to this logic, HttpHeaders.Names would come after HttpHeaders.
     *
     * For more details, see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=473629#c3">
     * static imports comparison method</a> in Eclipse.
     * </p>
     *
     * @param importName1 first import name.
     * @param importName2 second import name.
     * @param caseSensitive whether the comparison of fully qualified import names is case
     *                      sensitive.
     * @return the value {@code 0} if str1 is equal to str2; a value
     *         less than {@code 0} if str is less than the str2 (container order
     *         or lexicographical); and a value greater than {@code 0} if str1 is greater than str2
     *         (container order or lexicographically).
     */
    private static int compareContainerOrder(String importName1, String importName2,
                                             boolean caseSensitive) {
        final String container1 = getImportContainer(importName1);
        final String container2 = getImportContainer(importName2);
        final int compareContainersOrderResult;
        if (caseSensitive) {
            compareContainersOrderResult = container1.compareTo(container2);
        }
        else {
            compareContainersOrderResult = container1.compareToIgnoreCase(container2);
        }
        final int result;
        if (compareContainersOrderResult == 0) {
            result = compare(importName1, importName2, caseSensitive);
        }
        else {
            result = compareContainersOrderResult;
        }
        return result;
    }

    /**
     * Extracts import container name from fully qualified import name.
     * An import container name is the type which encloses the static element being imported.
     * For example, HttpConstants, HttpHeaders, HttpHeaders.Names are import container names:
     * <p>
     * import static HttpConstants.COLON     => HttpConstants
     * import static HttpHeaders.addHeader   => HttpHeaders
     * import static HttpHeaders.setHeader   => HttpHeaders
     * import static HttpHeaders.Names.DATE  => HttpHeaders.Names
     * </p>
     * @param qualifiedImportName fully qualified import name.
     * @return import container name.
     */
    private static String getImportContainer(String qualifiedImportName) {
        final int lastDotIndex = qualifiedImportName.lastIndexOf('.');
        return qualifiedImportName.substring(0, lastDotIndex);
    }

    /**
     * Finds out what group the specified import belongs to.
     *
     * @param isStatic whether the token is static or not.
     * @param name the import name to find.
     * @return group number for given import name.
     */
    private int getGroupNumber(boolean isStatic, String name) {
        final Pattern[] patterns;
        if (isStatic) {
            patterns = staticGroups;
        }
        else {
            patterns = groups;
        }

        int number = getGroupNumber(patterns, name);

        if (isStatic && option == ImportOrderOption.BOTTOM) {
            number += groups.length + 1;
        }
        else if (!isStatic && option == ImportOrderOption.TOP) {
            number += staticGroups.length + 1;
        }
        return number;
    }

    /**
     * Finds out what group the specified import belongs to.
     *
     * @param patterns groups to check.
     * @param name the import name to find.
     * @return group number for given import name.
     */
    private static int getGroupNumber(Pattern[] patterns, String name) {
        int bestIndex = patterns.length;
        int bestEnd = -1;
        int bestPos = Integer.MAX_VALUE;

        // find out what group this belongs in
        // loop over patterns and get index
        for (int i = 0; i < patterns.length; i++) {
            final Matcher matcher = patterns[i].matcher(name);
            if (matcher.find()) {
                if (matcher.start() < bestPos) {
                    bestIndex = i;
                    bestEnd = matcher.end();
                    bestPos = matcher.start();
                }
                else if (matcher.start() == bestPos && matcher.end() > bestEnd) {
                    bestIndex = i;
                    bestEnd = matcher.end();
                }
            }
        }
        return bestIndex;
    }

    /**
     * Compares two strings.
     *
     * @param string1
     *            the first string.
     * @param string2
     *            the second string.
     * @param caseSensitive
     *            whether the comparison is case sensitive.
     * @return the value {@code 0} if string1 is equal to string2; a value
     *         less than {@code 0} if string1 is lexicographically less
     *         than the string2; and a value greater than {@code 0} if
     *         string1 is lexicographically greater than string2.
     */
    private static int compare(String string1, String string2,
            boolean caseSensitive) {
        final int result;
        if (caseSensitive) {
            result = string1.compareTo(string2);
        }
        else {
            result = string1.compareToIgnoreCase(string2);
        }

        return result;
    }

    /**
     * Compiles the list of package groups and the order they should occur in the file.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     * @return array of compiled patterns.
     */
    private static Pattern[] compilePatterns(String... packageGroups) {
        final Pattern[] patterns = new Pattern[packageGroups.length];

        for (int i = 0; i < packageGroups.length; i++) {
            String pkg = packageGroups[i];
            final Pattern grp;

            // if the pkg name is the wildcard, make it match zero chars
            // from any name, so it will always be used as last resort.
            if (WILDCARD_GROUP_NAME.equals(pkg)) {
                // matches any package
                grp = Pattern.compile("");
            }
            else if (CommonUtil.startsWithChar(pkg, '/')) {
                if (!CommonUtil.endsWithChar(pkg, '/')) {
                    throw new IllegalArgumentException("Invalid group");
                }
                pkg = pkg.substring(1, pkg.length() - 1);
                grp = Pattern.compile(pkg);
            }
            else {
                final StringBuilder pkgBuilder = new StringBuilder(pkg);
                if (!CommonUtil.endsWithChar(pkg, '.')) {
                    pkgBuilder.append('.');
                }
                grp = Pattern.compile("^" + Pattern.quote(pkgBuilder.toString()));
            }

            patterns[i] = grp;
        }
        return patterns;
    }

}
