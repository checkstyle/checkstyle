////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * Checks that the groups of import declarations appear in the order specified
 * by the user. If there is an import but its group is not specified in the
 * configuration such an import should be placed at the end of the import list.
 * </p>
 * The rule consists of:
 *
 * <pre>
 * STATIC group. This group sets the ordering of static imports.
 * </pre>
 *
 * <pre>
 * SAME_PACKAGE(n) group. This group sets the ordering of the same package imports.
 * 'n' - a number of the first package domains. For example:
 * </pre>
 *
 * <pre>
 * <code>
 * package java.util.concurrent;
 *
 * import java.util.regex.Pattern;
 * import java.util.List;
 * import java.util.StringTokenizer;
 * import java.util.regex.Pattern;
 * import java.util.*;
 * import java.util.concurrent.AbstractExecutorService;
 * import java.util.concurrent.*;
 *
 * And we have such configuration: SAME_PACKAGE (3).
 * Same package imports are java.util.*, java.util.concurrent.*,
 * java.util.concurrent.AbstractExecutorService,
 * java.util.List and java.util.StringTokenizer
 * </code>
 * </pre>
 *
 * <pre>
 * THIRD_PARTY_PACKAGE group. This group sets ordering of third party imports.
 * Third party imports are all imports except STATIC,
 * SAME_PACKAGE(n), STANDARD_JAVA_PACKAGE and SPECIAL_IMPORTS.
 * </pre>
 *
 * <pre>
 * STANDARD_JAVA_PACKAGE group. This group sets ordering of standard java (java|javax) imports.
 * </pre>
 *
 * <pre>
 * SPECIAL_IMPORTS group. This group may contains some imports
 * that have particular meaning for the user.
 * </pre>
 *
 * <p>
 * NOTICE!
 * </p>
 * <p>
 * Use the separator '###' between rules.
 * </p>
 * <p>
 * To set RegExps for THIRD_PARTY_PACKAGE and STANDARD_JAVA_PACKAGE groups use
 * thirdPartyPackageRegExp and standardPackageRegExp options.
 * </p>
 *
 * <pre>
 * For example:
 * </pre>
 *
 * <pre>
 * <code>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *    value=&quot;STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE&quot;/&gt;
 *    &lt;property name=&quot;thirdPartyPackageRegExp&quot; value=&quot;com|org&quot;/&gt;
 *    &lt;property name=&quot;standardPackageRegExp&quot; value=&quot;java|javax&quot;/&gt;
 * &lt;/module&gt;
 * </code>
 * </pre>
 * <p>
 * Also, this check can be configured to force empty line separator between
 * import groups. For example
 * </p>
 *
 * <pre>
 * <code>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;separateLineBetweenGroups&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </code>
 * </pre>
 * <p>
 * By the option it is possible to force alphabetically sorting.
 * </p>
 *
 * <pre>
 * <code>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;sortImportsInGroupAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </code>
 * </pre>
 *
 * <p>
 * To force checking imports sequence such as:
 * </p>
 *
 * <pre>
 * <code>
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
 * import android.*;</code>
 * </pre>
 * configure as follows:
 * <pre>
 * <code>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *    value=&quot;SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS&quot;/&gt;
 *    &lt;property name=&quot;specialImportsRegExp&quot; value=&quot;android.*&quot;/&gt;
 * &lt;/module&gt;</code>
 * </pre>
 *
 * @author maxvetrenko
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class CustomImportOrderCheck extends Check
{

    /** STATIC group name */
    private static final String STATIC_RULE_GROUP = "STATIC";

    /** SAME_PACKAGE group name */
    private static final String SAME_PACKAGE_RULE_GROUP = "SAME_PACKAGE";

    /** THIRD_PARTY_PACKAGE group name */
    private static final String THIRD_PARTY_PACKAGE_RULE_GROUP = "THIRD_PARTY_PACKAGE";

    /** STANDARD_JAVA_PACKAGE group name */
    private static final String STANDARD_JAVA_PACKAGE_RULE_GROUP = "STANDARD_JAVA_PACKAGE";

    /** NON_GROUP group name */
    private static final String SPECIAL_IMPORTS_RULE_GROUP = "SPECIAL_IMPORTS";

    /** NON_GROUP group name */
    private static final String NON_GROUP_RULE_GROUP = "NON_GROUP";

    /** RegExp for SAME_PACKAGE group imports */
    private String samePackageDomainsRegExp = "";

    /** RegExp for STANDARD_JAVA_PACKAGE group imports */
    private Pattern standardPackageRegExp = Utils.getPattern("java|javax");

    /** RegExp for THIRDPARTY_PACKAGE group imports */
    private Pattern thirdPartyPackageRegExp = Utils.getPattern(".*");

    /** RegExp for SPECIAL_IMPORTS group imports */
    private Pattern specialImportsRegExp = Utils.getPattern("^$");

    /** Force empty line separator between import groups */
    private boolean separateLineBetweenGroups = true;

    /** Force grouping alphabetically */
    private boolean sortImportsInGroupAlphabetically;

    /** List of order declaration customizing by user */
    private final List<String> customImportOrderRules =
            new ArrayList<String>();

    /** Number of first domains for SAME_PACKAGE group. */
    private int samePackageMatchingDepth = 2;

    /** Contains objects with import attributes */
    private List<ImportDetails> importToGroupList =
            new ArrayList<CustomImportOrderCheck.ImportDetails>();

    /**
     * Sets standardRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setStandardPackageRegExp(String regexp)
    {
        standardPackageRegExp = Utils.getPattern(regexp);
    }

    /**
     * Sets thirdPartyRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setThirdPartyPackageRegExp(String regexp)
    {
        thirdPartyPackageRegExp = Utils.getPattern(regexp);
    }

    /**
     * Sets specialImportsRegExp specified by user.
     * @param regexp
     *        user value.
     */
    public final void setSpecialImportsRegExp(String regexp)
    {
        specialImportsRegExp = Utils.getPattern(regexp);
    }

    /**
     * Sets separateLineBetweenGroups specified by user.
     * @param value
     *        user value.
     */
    public final void setSeparateLineBetweenGroups(boolean value)
    {
        separateLineBetweenGroups = value;
    }

    /**
     * Sets sortImportsInGroupAlphabetically specified by user.
     * @param value
     *        user value.
     */
    public final void setSortImportsInGroupAlphabetically(boolean value)
    {
        sortImportsInGroupAlphabetically = value;
    }

    /**
     * Sets a custom import order from the rules in the string format specified
     * by user.
     * @param inputCustoimportOrder
     *        user value.
     */
    public final void setCustomImportOrderRules(final String inputCustoimportOrder)
    {
        customImportOrderRules.clear();
        try {
            for (final String currentState : inputCustoimportOrder
                    .split("\\s*###\\s*"))
            {
                addRuleastoList(currentState);
            }
            customImportOrderRules.add(NON_GROUP_RULE_GROUP);
        }
        catch (StringIndexOutOfBoundsException exp) {
            //if the structure of the input rule isn't correct
            throw new RuntimeException("Unable to parse input rule: " + exp);
        }
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
        importToGroupList.clear();
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            if (customImportOrderRules.contains(SAME_PACKAGE_RULE_GROUP)
                    && samePackageMatchingDepth != -1)
            {
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
    public void finishTree(DetailAST rootAST)
    {

        if (importToGroupList.isEmpty()) {
            return;
        }

        final ImportDetails firstImport = importToGroupList.get(0);
        String currentGroup = getImportGroup(firstImport.isStatic(),
                firstImport.getImportFullPath());
        int groupNumber = customImportOrderRules.indexOf(currentGroup);
        String previousImport = null;

        for (final ImportDetails importObject : importToGroupList) {
            final String importGroup = importObject.getImportGroup();
            final String fullImportIdent = importObject.importFullPath;

            if (!importGroup.equals(currentGroup)) {
                if (customImportOrderRules.size() > groupNumber + 1) {
                    final String nextGroup = getNextImportGroup(groupNumber + 1);
                    if (importGroup.equals(nextGroup)) {
                        if (separateLineBetweenGroups && previousImport != null
                                && !hasEmptyLineBefore(importObject.getLineNumber()))
                        {
                            log(importObject.getLineNumber(), "custom.import.order.line.separator",
                                    fullImportIdent);
                        }
                        currentGroup = nextGroup;
                        groupNumber = customImportOrderRules.indexOf(nextGroup);
                    }
                    else {
                        logWrongImportGroupOrder(importObject.getLineNumber(),
                                importGroup);
                    }
                }
                else {
                    logWrongImportGroupOrder(importObject.getLineNumber(),
                            importGroup);
                }
            }
            else if (sortImportsInGroupAlphabetically
                    && previousImport != null
                    && matchesImportGroup(importObject.isStatic(),
                            fullImportIdent, currentGroup)
                    && !(compareImports(fullImportIdent, previousImport) >= 0))
            {
                log(importObject.getLineNumber(), "custom.import.order.lex", fullImportIdent);
            }
            previousImport = fullImportIdent;
        }
    }

    /**
     * Log wrong import group order.
     * @param currentImportLine
     *        line number of current import current import.
     * @param importGroup
     *        import group.
     */
    private void logWrongImportGroupOrder(int currentImportLine, String importGroup)
    {
        if (NON_GROUP_RULE_GROUP.equals(importGroup)) {
            log(currentImportLine, "custom.import.order.nongroup.import");
        }
        else {
            log(currentImportLine, "custom.import.order", importGroup);
        }
    }

    /**
     * Get next import group.
     * @param currentGroupNumber
     *        current group number.
     * @return
     *        next import group.
     */
    private String getNextImportGroup(int currentGroupNumber)
    {
        int nextGroupNumber = currentGroupNumber;

        while (customImportOrderRules.size() > nextGroupNumber + 1) {
            if (hasAnyImportInCurrentGroup(customImportOrderRules.get(nextGroupNumber)))
            {
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
    private boolean hasAnyImportInCurrentGroup(String currentGroup)
    {
        for (final ImportDetails currentImport : importToGroupList) {
            if (currentGroup.equals(currentImport.getImportGroup())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get import valid group.
     * @param isStatic
     *        is static import.
     * @param importPath
     *        full import path.
     * @return import valid group.
     */
    private String getImportGroup(boolean isStatic, String importPath)
    {
        for (final String group : customImportOrderRules) {
            if (matchesImportGroup(isStatic, importPath, group)) {
                return group;
            }
        }
        return NON_GROUP_RULE_GROUP;
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param isStatic
     *        if import is static.
     * @param importPath
     *        import full path.
     * @param currentGroup
     *        current group.
     * @return true, if import placed in the correct group.
     */
    private boolean matchesImportGroup(boolean isStatic, String importPath, String currentGroup)
    {
        return matchesStaticImportGroup(isStatic, currentGroup)
                || matchesSamePackageImportGroup(isStatic, importPath, currentGroup)
                || matchesSpecialImportsGroup(isStatic, importPath, currentGroup)
                || matchesStandartImportGroup(isStatic, importPath, currentGroup)
                || matchesThirdPartyImportGroup(isStatic, importPath, currentGroup);
    }

    /**
     * Checks if the import is placed in the STATIC group.
     * @param isStatic
     *        is static import.
     * @param currentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesStaticImportGroup(boolean isStatic, String currentGroup)
    {
        return isStatic && STATIC_RULE_GROUP.equals(currentGroup);
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param isStatic
     *        if import is static.
     * @param importFullPath
     *        import full path.
     * @param currentGroup
     *        current group.
     * @return true, if the import is placed in the same package group.
     */
    private boolean matchesSamePackageImportGroup(boolean isStatic,
        String importFullPath, String currentGroup)
    {
        final String importPath = importFullPath.substring(0, importFullPath.lastIndexOf("."));
        return !isStatic && SAME_PACKAGE_RULE_GROUP.equals(currentGroup)
                && samePackageDomainsRegExp.contains(importPath);
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param isStatic
     *        if import is static.
     * @param currentImport
     *        import full path.
     * @param currentGroup
     *        current group.
     * @return true, if the import is placed in the standard group.
     */
    private boolean matchesStandartImportGroup(boolean isStatic,
        String currentImport, String currentGroup)
    {
        return !isStatic && STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(currentGroup)
                && standardPackageRegExp.matcher(currentImport).find();
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param isStatic
     *        if import is static.
     * @param currentImport
     *        import full path.
     * @param currentGroup
     *        current group.
     * @return true, if the import is placed in the special group.
     */
    private boolean matchesSpecialImportsGroup(boolean isStatic,
        String currentImport, String currentGroup)
    {
        return !isStatic && SPECIAL_IMPORTS_RULE_GROUP.equals(currentGroup)
                && specialImportsRegExp.matcher(currentImport).find();
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param isStatic
     *        if import is static.
     * @param currentImport
     *        import full path.
     * @param currentGroup
     *        current group.
     * @return true, if the import is placed in the third party group.
     */
    private boolean matchesThirdPartyImportGroup(boolean isStatic,
        String currentImport, String currentGroup)
    {
        return !isStatic && THIRD_PARTY_PACKAGE_RULE_GROUP.equals(currentGroup)
                && thirdPartyPackageRegExp.matcher(currentImport).find()
                && !standardPackageRegExp.matcher(currentImport).find()
                && !specialImportsRegExp.matcher(currentImport).find();
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
    private static int compareImports(String import1, String import2)
    {
        int result = 0;
        final String[] import1Tokens = import1.split("\\.");
        final String[] import2Tokens = import2.split("\\.");
        for (int i = 0; i < import1Tokens.length; i++) {
            if (i == import2Tokens.length) {
                break;
            }
            final String import1Token = import1Tokens[i];
            final String import2Token = import2Tokens[i];
            result = import1Token.compareToIgnoreCase(import2Token);
            if (result != 0) {
                break;
            }
        }
        return result;
    }

    /**
     * Return class name from import full path.
     * @param startFrom number of start.
     * @param importPath import full path.
     * @return class name.
     */
    private String getClassName(int startFrom, String importPath)
    {
        String className = importPath;
        className = className.substring(startFrom, className.length());
        final StringTokenizer token = new StringTokenizer(className, ".\r");
        return token.nextToken();
    }

    /**
     * Checks if a token has a empty line before.
     * @param lineNo
     *        Line number of current import.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(int lineNo)
    {
        //  [lineNo - 2] is the number of the previous line
        //  because the numbering starts from zero.
        final String lineBefore = getLine(lineNo - 2);
        return lineBefore.trim().isEmpty();
    }

    /**
     * Forms import full path.
     * @param token
     *        current token.
     * @return full path or null.
     */
    private static String getFullImportIdent(DetailAST token)
    {
        return token != null ? FullIdent.createFullIdent(token.
                findFirstToken(TokenTypes.DOT)).getText() : "";
    }

    /**
     * Parses ordering rule and adds it to the list with rules.
     * @param ruleStr
     *        String with rule.
     */
    private void addRuleastoList(String ruleStr)
    {
        if (STATIC_RULE_GROUP.equals(ruleStr)
                || THIRD_PARTY_PACKAGE_RULE_GROUP.equals(ruleStr)
                || STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(ruleStr)
                || SPECIAL_IMPORTS_RULE_GROUP.equals(ruleStr))
        {
            customImportOrderRules.add(ruleStr);

        }
        else if (ruleStr.startsWith(SAME_PACKAGE_RULE_GROUP)) {

            final String rule = ruleStr.substring(ruleStr.indexOf("(") + 1,
                    ruleStr.indexOf(")"));
            try {
                samePackageMatchingDepth = Integer.parseInt(rule);
            }
            catch (Exception e) {
                samePackageDomainsRegExp = rule;
            }
            customImportOrderRules.add(SAME_PACKAGE_RULE_GROUP);

        }
        else {
            throw new RuntimeException("Unexpected rule: " + ruleStr);
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
             DetailAST packageNode)
    {
        final StringBuilder builder = new StringBuilder();
        final String packageFullPath = getFullImportIdent(packageNode);
        final StringTokenizer tokens = new StringTokenizer(packageFullPath, ".");
        int count = firstPackageDomainsCount;

        while (tokens.hasMoreTokens() && count > 0) {
            builder.append(tokens.nextToken()).append(".");
            count--;
        }
        return builder.append("*").toString();
    }

    /**
     * Contains import attributes as line number, import full path, import
     * group.
     * @author max
     */
    class ImportDetails
    {
        /** Import full path */
        private String importFullPath;

        /** Import line number */
        private int lineNumber;

        /** Import group */
        private String importGroup;

        /** Is static import */
        private boolean isStatic;

        /**
         * @param importFullPath
         *        import full path.
         * @param lineNumber
         *        import line number.
         * @param importGroup
         *        import group.
         * @param isStatic
         *        if import is static.
         */
        public ImportDetails(String importFullPath,
                int lineNumber, String importGroup, boolean isStatic)
        {
            setImportFullPath(importFullPath);
            setLineNumber(lineNumber);
            setImportGroup(importGroup);
            setStatic(isStatic);
        }

        /**
         * Get import full path variable.
         * @return import full path variable.
         */
        public String getImportFullPath()
        {
            return importFullPath;
        }

        /**
         * Set import full path variable.
         * @param importFullPath
         *        import full path variable.
         */
        public void setImportFullPath(String importFullPath)
        {
            this.importFullPath = importFullPath;
        }

        /**
         * Get import line number.
         * @return import line.
         */
        public int getLineNumber()
        {
            return lineNumber;
        }

        /**
         * Set import line number.
         * @param lineNumber
         *        import line number.
         */
        public void setLineNumber(int lineNumber)
        {
            this.lineNumber = lineNumber;
        }

        /**
         * Get import group.
         * @return import group.
         */
        public String getImportGroup()
        {
            return importGroup;
        }

        /**
         * Set import group.
         * @param importGroup
         *        import group.
         */
        public void setImportGroup(String importGroup)
        {
            this.importGroup = importGroup;
        }

        /**
         * Checks if import is static.
         * @return true, if import is static.
         */
        public boolean isStatic()
        {
            return isStatic;
        }

        /**
         * Set true, if import is static
         * @param isStatic
         *        if import is static.
         */
        public void setStatic(boolean isStatic)
        {
            this.isStatic = isStatic;
        }
    }
}
