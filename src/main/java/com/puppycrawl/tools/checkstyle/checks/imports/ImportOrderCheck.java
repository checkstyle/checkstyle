///
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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * <div>
 * Checks the ordering/grouping of imports. Features are:
 * </div>
 * <ul>
 * <li>
 * groups type/static imports: ensures that groups of imports come in a specific order
 * (e.g., java. comes first, javax. comes second, then everything else)
 * </li>
 * <li>
 * adds a separation between type import groups : ensures that a blank line sit between each group
 * </li>
 * <li>
 * type/static import groups aren't separated internally: ensures that each group aren't separated
 * internally by blank line or comment
 * </li>
 * <li>
 * sorts type/static imports inside each group: ensures that imports within each group are in
 * lexicographic order
 * </li>
 * <li>
 * sorts according to case: ensures that the comparison between imports is case-sensitive, in
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>
 * </li>
 * <li>
 * arrange static imports: ensures the relative order between type imports and static imports
 * (see
 * <a href="https://checkstyle.org/property_types.html#ImportOrderOption">ImportOrderOption</a>)
 * </li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code caseSensitive} - Control whether string comparison should be
 * case-sensitive or not. Case-sensitive sorting is in
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
 * It affects both type imports and static imports.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code groups} - Specify list of <b>type import</b> groups. Every group identified
 * either by a common prefix string, or by a regular expression enclosed in forward slashes
 * (e.g. {@code /regexp/}). If an import matches two or more groups,
 * the best match is selected (closest to the start, and the longest match).
 * All type imports, which does not match any group, falls into an
 * additional group, located at the end.
 * Thus, the empty list of type groups (the default value) means one group for all type imports.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code option} - Specify policy on the relative order between type imports and static
 * imports.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderOption}.
 * Default value is {@code under}.
 * </li>
 * <li>
 * Property {@code ordered} - Control whether type imports within each group should be
 * sorted.
 * It doesn't affect sorting for static imports.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code separated} - Control whether type import groups should be separated
 * by, at least, one blank line or comment and aren't separated internally.
 * It doesn't affect separations for static imports.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code separatedStaticGroups} - Control whether static import groups should
 * be separated by, at least, one blank line or comment and aren't separated internally.
 * This property has effect only when the property {@code option} is set to {@code top}
 * or {@code bottom} and when property {@code staticGroups} is enabled.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code sortStaticImportsAlphabetically} - Control whether
 * <b>static imports</b> located at <b>top</b> or <b>bottom</b> are sorted within the group.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code staticGroups} - Specify list of <b>static</b> import groups. Every group
 * identified either by a common prefix string, or by a regular expression enclosed in forward
 * slashes (e.g. {@code /regexp/}). If an import matches two or more groups,
 * the best match is selected (closest to the start, and the longest match).
 * All static imports, which does not match any group, fall into
 * an additional group, located at the end. Thus, the empty list of static groups (the default
 * value) means one group for all static imports. This property has effect only when the property
 * {@code option} is set to {@code top} or {@code bottom}.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code useContainerOrderingForStatic} - Control whether to use container
 * ordering (Eclipse IDE term) for static imports or not.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code import.groups.separated.internally}
 * </li>
 * <li>
 * {@code import.ordering}
 * </li>
 * <li>
 * {@code import.separation}
 * </li>
 * </ul>
 *
 * @since 3.2
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

    /** The Forward slash. */
    private static final String FORWARD_SLASH = "/";

    /** Empty array of pattern type needed to initialize check. */
    private static final Pattern[] EMPTY_PATTERN_ARRAY = new Pattern[0];

    /**
     * Specify list of <b>type import</b> groups. Every group identified either by a common prefix
     * string, or by a regular expression enclosed in forward slashes (e.g. {@code /regexp/}).
     * If an import matches two or more groups,
     * the best match is selected (closest to the start, and the longest match).
     * All type imports, which does not match any group, falls into an additional group,
     * located at the end. Thus, the empty list of type groups (the default value) means one group
     * for all type imports.
     */
    private String[] groups = CommonUtil.EMPTY_STRING_ARRAY;

    /**
     * Specify list of <b>static</b> import groups. Every group identified either by a common prefix
     * string, or by a regular expression enclosed in forward slashes (e.g. {@code /regexp/}).
     * If an import matches two or more groups,
     * the best match is selected (closest to the start, and the longest match).
     * All static imports, which does not match any group, fall into an additional group, located
     * at the end. Thus, the empty list of static groups (the default value) means one group for all
     * static imports. This property has effect only when the property {@code option} is set to
     * {@code top} or {@code bottom}.
     */
    private String[] staticGroups = CommonUtil.EMPTY_STRING_ARRAY;

    /**
     * Control whether type import groups should be separated by, at least, one blank
     * line or comment and aren't separated internally. It doesn't affect separations for static
     * imports.
     */
    private boolean separated;

    /**
     * Control whether static import groups should be separated by, at least, one blank
     * line or comment and aren't separated internally. This property has effect only when the
     * property {@code option} is set to {@code top} or {@code bottom} and when property
     * {@code staticGroups} is enabled.
     */
    private boolean separatedStaticGroups;

    /**
     * Control whether type imports within each group should be sorted.
     * It doesn't affect sorting for static imports.
     */
    private boolean ordered = true;

    /**
     * Control whether string comparison should be case-sensitive or not. Case-sensitive
     * sorting is in <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
     * It affects both type imports and static imports.
     */
    private boolean caseSensitive = true;

    /** Last imported group. */
    private int lastGroup;
    /** Line number of last import. */
    private int lastImportLine;
    /** Name of last import. */
    private String lastImport;
    /** If last import was static. */
    private boolean lastImportStatic;
    /** Whether there were any imports. */
    private boolean beforeFirstImport;
    /**
     * Whether static and type import groups should be split apart.
     * When the {@code option} property is set to {@code INFLOW}, {@code ABOVE} or {@code UNDER},
     * both the type and static imports use the properties {@code groups} and {@code separated}.
     * When the {@code option} property is set to {@code TOP} or {@code BOTTOM}, static imports
     * uses the properties {@code staticGroups} and {@code separatedStaticGroups}.
     **/
    private boolean staticImportsApart;

    /**
     * Control whether <b>static imports</b> located at <b>top</b> or <b>bottom</b> are
     * sorted within the group.
     */
    private boolean sortStaticImportsAlphabetically;

    /**
     * Control whether to use container ordering (Eclipse IDE term) for static imports
     * or not.
     */
    private boolean useContainerOrderingForStatic;

    /**
     * Specify policy on the relative order between type imports and static imports.
     */
    private ImportOrderOption option = ImportOrderOption.UNDER;

    /**
     * Complied array of patterns for property {@code groups}.
     */
    private Pattern[] groupsReg = EMPTY_PATTERN_ARRAY;

    /**
     * Complied array of patterns for property {@code staticGroups}.
     */
    private Pattern[] staticGroupsReg = EMPTY_PATTERN_ARRAY;

    /**
     * Setter to specify policy on the relative order between type imports and static imports.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 5.0
     */
    public void setOption(String optionStr) {
        option = ImportOrderOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Setter to specify list of <b>type import</b> groups. Every group identified either by a
     * common prefix string, or by a regular expression enclosed in forward slashes
     * (e.g. {@code /regexp/}). If an import matches two or more groups,
     * the best match is selected (closest to the start, and the longest match).
     * All type imports, which does not match any group, falls into an
     * additional group, located at the end. Thus, the empty list of type groups (the default value)
     * means one group for all type imports.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     * @since 3.2
     */
    public void setGroups(String... packageGroups) {
        groups = UnmodifiableCollectionUtil.copyOfArray(packageGroups, packageGroups.length);
        groupsReg = compilePatterns(packageGroups);
    }

    /**
     * Setter to specify list of <b>static</b> import groups. Every group identified either by a
     * common prefix string, or by a regular expression enclosed in forward slashes
     * (e.g. {@code /regexp/}). If an import matches two or more groups,
     * the best match is selected (closest to the start, and the longest match).
     * All static imports, which does not match any group, fall into an
     * additional group, located at the end. Thus, the empty list of static groups (the default
     * value) means one group for all static imports. This property has effect only when
     * the property {@code option} is set to {@code top} or {@code bottom}.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     * @since 8.12
     */
    public void setStaticGroups(String... packageGroups) {
        staticGroups = UnmodifiableCollectionUtil.copyOfArray(packageGroups, packageGroups.length);
        staticGroupsReg = compilePatterns(packageGroups);
    }

    /**
     * Setter to control whether type imports within each group should be sorted.
     * It doesn't affect sorting for static imports.
     *
     * @param ordered
     *            whether lexicographic ordering of imports within a group
     *            required or not.
     * @since 3.2
     */
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Setter to control whether type import groups should be separated by, at least,
     * one blank line or comment and aren't separated internally.
     * It doesn't affect separations for static imports.
     *
     * @param separated
     *            whether groups should be separated by one blank line or comment.
     * @since 3.2
     */
    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    /**
     * Setter to control whether static import groups should be separated by, at least,
     * one blank line or comment and aren't separated internally.
     * This property has effect only when the property
     * {@code option} is set to {@code top} or {@code bottom} and when property {@code staticGroups}
     * is enabled.
     *
     * @param separatedStaticGroups
     *            whether groups should be separated by one blank line or comment.
     * @since 8.12
     */
    public void setSeparatedStaticGroups(boolean separatedStaticGroups) {
        this.separatedStaticGroups = separatedStaticGroups;
    }

    /**
     * Setter to control whether string comparison should be case-sensitive or not.
     * Case-sensitive sorting is in
     * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
     * It affects both type imports and static imports.
     *
     * @param caseSensitive
     *            whether string comparison should be case-sensitive.
     * @since 3.3
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Setter to control whether <b>static imports</b> located at <b>top</b> or
     * <b>bottom</b> are sorted within the group.
     *
     * @param sortAlphabetically true or false.
     * @since 6.5
     */
    public void setSortStaticImportsAlphabetically(boolean sortAlphabetically) {
        sortStaticImportsAlphabetically = sortAlphabetically;
    }

    /**
     * Setter to control whether to use container ordering (Eclipse IDE term) for static
     * imports or not.
     *
     * @param useContainerOrdering whether to use container ordering for static imports or not.
     * @since 7.1
     */
    public void setUseContainerOrderingForStatic(boolean useContainerOrdering) {
        useContainerOrderingForStatic = useContainerOrdering;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lastGroup = Integer.MIN_VALUE;
        lastImportLine = Integer.MIN_VALUE;
        lastImportStatic = false;
        beforeFirstImport = true;
        staticImportsApart =
            option == ImportOrderOption.TOP || option == ImportOrderOption.BOTTOM;
    }

    // -@cs[CyclomaticComplexity] SWITCH was transformed into IF-ELSE.
    @Override
    public void visitToken(DetailAST ast) {
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
            doVisitToken(ident, isStatic, isStaticAndNotLastImport, ast);
        }
        else if (option == ImportOrderOption.BOTTOM || option == ImportOrderOption.UNDER) {
            final boolean isLastImportAndNonStatic = lastImportStatic && !isStatic;
            doVisitToken(ident, isStatic, isLastImportAndNonStatic, ast);
        }
        else if (option == ImportOrderOption.INFLOW) {
            // "previous" argument is useless here
            doVisitToken(ident, isStatic, true, ast);
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
     * @param ast node of the AST.
     */
    private void doVisitToken(FullIdent ident, boolean isStatic, boolean previous, DetailAST ast) {
        final String name = ident.getText();
        final int groupIdx = getGroupNumber(isStatic && staticImportsApart, name);

        if (groupIdx > lastGroup) {
            if (!beforeFirstImport
                && ast.getLineNo() - lastImportLine < 2
                && needSeparator(isStatic)) {
                log(ast, MSG_SEPARATION, name);
            }
        }
        else if (groupIdx == lastGroup) {
            doVisitTokenInSameGroup(isStatic, previous, name, ast);
        }
        else {
            log(ast, MSG_ORDERING, name);
        }
        if (isSeparatorInGroup(groupIdx, isStatic, ast.getLineNo())) {
            log(ast, MSG_SEPARATED_IN_GROUP, name);
        }

        lastGroup = groupIdx;
        lastImport = name;
    }

    /**
     * Checks whether import groups should be separated.
     *
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
            staticImportSeparator = separated;
        }
        final boolean separatorBetween = isStatic != lastImportStatic
            && (separated || separatedStaticGroups);

        return typeImportSeparator || staticImportSeparator || separatorBetween;
    }

    /**
     * Checks whether imports group separated internally.
     *
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
     *
     * @param line the line of the current import.
     * @return true if there is separator before current import which isn't the first import.
     */
    private boolean isSeparatorBeforeImport(int line) {
        return line - lastImportLine > 1;
    }

    /**
     * Shares processing...
     *
     * @param isStatic whether the token is static or not.
     * @param previous previous non-static but current is static (above), or
     *     previous static but current is non-static (under).
     * @param name the name of the current import.
     * @param ast node of the AST.
     */
    private void doVisitTokenInSameGroup(boolean isStatic,
            boolean previous, String name, DetailAST ast) {
        if (ordered) {
            if (option == ImportOrderOption.INFLOW) {
                if (isWrongOrder(name, isStatic)) {
                    log(ast, MSG_ORDERING, name);
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
                    log(ast, MSG_ORDERING, name);
                }
            }
        }
    }

    /**
     * Checks whether import name is in wrong order.
     *
     * @param name import name.
     * @param isStatic whether it is a static import name.
     * @return true if import name is in wrong order.
     */
    private boolean isWrongOrder(String name, boolean isStatic) {
        final boolean result;
        if (isStatic) {
            if (useContainerOrderingForStatic) {
                result = compareContainerOrder(lastImport, name, caseSensitive) > 0;
            }
            else if (staticImportsApart) {
                result = sortStaticImportsAlphabetically
                    && compare(lastImport, name, caseSensitive) > 0;
            }
            else {
                result = compare(lastImport, name, caseSensitive) > 0;
            }
        }
        else {
            // out of lexicographic order
            result = compare(lastImport, name, caseSensitive) > 0;
        }
        return result;
    }

    /**
     * Compares two import strings.
     * We first compare the container of the static import, container being the type enclosing
     * the static element being imported. When this returns 0, we compare the qualified
     * import name. For e.g. this is what is considered to be container names:
     * <pre>
     * import static HttpConstants.COLON     =&gt; HttpConstants
     * import static HttpHeaders.addHeader   =&gt; HttpHeaders
     * import static HttpHeaders.setHeader   =&gt; HttpHeaders
     * import static HttpHeaders.Names.DATE  =&gt; HttpHeaders.Names
     * </pre>
     *
     * <p>
     * According to this logic, HttpHeaders.Names would come after HttpHeaders.
     * For more details, see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=473629#c3">
     * static imports comparison method</a> in Eclipse.
     * </p>
     *
     * @param importName1 first import name
     * @param importName2 second import name
     * @param caseSensitive whether the comparison of fully qualified import names is
     *                      case-sensitive
     * @return the value {@code 0} if str1 is equal to str2; a value
     *         less than {@code 0} if str is less than the str2 (container order
     *         or lexicographical); and a value greater than {@code 0} if str1 is greater than str2
     *         (container order or lexicographically)
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
     * <pre>
     * import static HttpConstants.COLON     =&gt; HttpConstants
     * import static HttpHeaders.addHeader   =&gt; HttpHeaders
     * import static HttpHeaders.setHeader   =&gt; HttpHeaders
     * import static HttpHeaders.Names.DATE  =&gt; HttpHeaders.Names
     * </pre>
     *
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
            patterns = staticGroupsReg;
        }
        else {
            patterns = groupsReg;
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
     *            the first string
     * @param string2
     *            the second string
     * @param caseSensitive
     *            whether the comparison is case-sensitive
     * @return the value {@code 0} if string1 is equal to string2; a value
     *         less than {@code 0} if string1 is lexicographically less
     *         than the string2; and a value greater than {@code 0} if
     *         string1 is lexicographically greater than string2
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
     * @throws IllegalArgumentException if any of the package groups are not valid.
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
            else if (pkg.startsWith(FORWARD_SLASH)) {
                if (!pkg.endsWith(FORWARD_SLASH)) {
                    throw new IllegalArgumentException("Invalid group: " + pkg);
                }
                pkg = pkg.substring(1, pkg.length() - 1);
                grp = Pattern.compile(pkg);
            }
            else {
                final StringBuilder pkgBuilder = new StringBuilder(pkg);
                if (!pkg.endsWith(".")) {
                    pkgBuilder.append('.');
                }
                grp = Pattern.compile("^" + Pattern.quote(pkgBuilder.toString()));
            }

            patterns[i] = grp;
        }
        return patterns;
    }

}
