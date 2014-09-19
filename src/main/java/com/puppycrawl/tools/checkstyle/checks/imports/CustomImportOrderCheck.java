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
 * </pre>
 *
 * <pre>
 * THIRD_PARTY_PACKAGE group. This group sets ordering of third party imports.
 * Third party imports are all imports except STATIC,
 * SAME_PACKAGE(n) and STANDARD_JAVA_PACKAGE.
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
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;customImportOrderRules&quot;
 *    value=&quot;STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE&quot;/&gt;
 *    &lt;property name=&quot;thirdPartyPackageRegExp&quot; value=&quot;com|org&quot;/&gt;
 *    &lt;property name=&quot;standardPackageRegExp&quot; value=&quot;java|javax&quot;/&gt;
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
 * By the option it is possible to force alphabetically sorting.
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;CustomImportOrder&quot;&gt;
 *    &lt;property name=&quot;sortImportsInGroupAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author maxvetrenko
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
    private String mSamePackageDomainsRegExp = "";

    /** RegExp for STANDARD_JAVA_PACKAGE group imports */
    private Pattern mStandardPackageRegExp = Utils.getPattern("java|javax");

    /** RegExp for THIRDPARTY_PACKAGE group imports */
    private Pattern mThirdPartyPackageRegExp = Utils.getPattern("^$");

    /** RegExp for SPECIAL_IMPORTS group imports */
    private Pattern mSpecialImportsRegExp = Utils.getPattern("^$");

    /** Force empty line separator between import groups */
    private boolean mSeparateLineBetweenGroups = true;

    /** Force grouping alphabetically */
    private boolean mSortImportsInGroupAlphabetically;

    /** List of order declaration customizing by user */
    private final List<String> mCustomImportOrderRules =
            new ArrayList<String>();

    /** Number of first domains for SAME_PACKAGE group. */
    private int mSamePackageMatchingDepth = 2;

    /** Contains objects with import attributes */
    private List<ImportDetails> mImportToGroupList =
            new ArrayList<CustomImportOrderCheck.ImportDetails>();

    /**
     * Sets mStandardRegExp specified by user.
     * @param aRegexp
     *        user value.
     */
    public final void setStandardPackageRegExp(String aRegexp)
    {
        mStandardPackageRegExp = Utils.getPattern(aRegexp);
    }

    /**
     * Sets mThirdPartyRegExp specified by user.
     * @param aRegexp
     *        user value.
     */
    public final void setThirdPartyPackageRegExp(String aRegexp)
    {
        mThirdPartyPackageRegExp = Utils.getPattern(aRegexp);
    }

    /**
     * Sets mSpecialImportsRegExp specified by user.
     * @param aRegexp
     *        user value.
     */
    public final void setSpecialImportsRegExp(String aRegexp)
    {
        mSpecialImportsRegExp = Utils.getPattern(aRegexp);
    }

    /**
     * Sets mSeparateLineBetweenGroups specified by user.
     * @param aValue
     *        user value.
     */
    public final void setSeparateLineBetweenGroups(boolean aValue)
    {
        mSeparateLineBetweenGroups = aValue;
    }

    /**
     * Sets mSortImportsInGroupAlphabetically specified by user.
     * @param aValue
     *        user value.
     */
    public final void setSortImportsInGroupAlphabetically(boolean aValue)
    {
        mSortImportsInGroupAlphabetically = aValue;
    }

    /**
     * Sets a custom import order from the rules in the string format specified
     * by user.
     * @param aInputCustomImportOrder
     *        user value.
     */
    public final void setCustomImportOrderRules(final String aInputCustomImportOrder)
    {
        mCustomImportOrderRules.clear();
        try {
            for (String currentState : aInputCustomImportOrder
                    .split("\\s*###\\s*"))
            {
                addRulesToList(currentState);
            }
            mCustomImportOrderRules.add(NON_GROUP_RULE_GROUP);
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
    public void beginTree(DetailAST aRootAST)
    {
        mImportToGroupList.clear();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            if (mCustomImportOrderRules.contains(SAME_PACKAGE_RULE_GROUP)
                    && mSamePackageMatchingDepth != -1)
            {
                mSamePackageDomainsRegExp = createSamePackageRegexp(
                        mSamePackageMatchingDepth, aAST);
            }
        }
        else {
            final String importFullPath = getFullImportIdent(aAST);
            final int lineNo = aAST.getLineNo();
            final boolean isStatic = aAST.getType() == TokenTypes.STATIC_IMPORT;
            mImportToGroupList.add(new ImportDetails(importFullPath,
                    lineNo, getImportGroup(isStatic, importFullPath),
                    isStatic));
        }
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {

        if (mImportToGroupList.isEmpty()) {
            return;
        }

        final ImportDetails firstImport = mImportToGroupList.get(0);
        String currentGroup = getImportGroup(firstImport.isStatic(),
                firstImport.getImportFullPath());
        int groupNumber = mCustomImportOrderRules.indexOf(currentGroup);
        String previousImport = null;

        for (ImportDetails importObject : mImportToGroupList) {
            final String importGroup = importObject.getImportGroup();
            final String fullImportIdent = importObject.mImportFullPath;

            if (!importGroup.equals(currentGroup)) {
                if (mCustomImportOrderRules.size() > groupNumber + 1) {
                    final String nextGroup = getNextImportGroup(groupNumber + 1);
                    if (importGroup.equals(nextGroup)) {
                        if (mSeparateLineBetweenGroups && previousImport != null
                                && !hasEmptyLineBefore(importObject.getLineNumber()))
                        {
                            log(importObject.getLineNumber(), "custom.import.order.line.separator",
                                    fullImportIdent);
                        }
                        currentGroup = nextGroup;
                        groupNumber = mCustomImportOrderRules.indexOf(nextGroup);
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
            else if (mSortImportsInGroupAlphabetically
                    && previousImport != null
                    && matchesImportGroup(importObject.isStatic(),
                            fullImportIdent, currentGroup)
                    && !(compare(fullImportIdent, previousImport) >= 0))
            {
                log(importObject.getLineNumber(), "custom.import.order.lex", fullImportIdent);
            }
            previousImport = fullImportIdent;
        }
    }

    /**
     * Log wrong import group order.
     * @param aCurrentImportLine
     *        line number of current import current import.
     * @param aImportGroup
     *        import group.
     */
    private void logWrongImportGroupOrder(int aCurrentImportLine, String aImportGroup)
    {
        if (NON_GROUP_RULE_GROUP.equals(aImportGroup)) {
            log(aCurrentImportLine, "custom.import.order.nongroup.import");
        }
        else {
            log(aCurrentImportLine, "custom.import.order", aImportGroup);
        }
    }

    /**
     * Get next import group.
     * @param aCurrentGroupNumber
     *        current group number.
     * @return
     *        next import group.
     */
    private String getNextImportGroup(int aCurrentGroupNumber)
    {
        int nextGroupNumber = aCurrentGroupNumber;
        for (; mCustomImportOrderRules.size() > nextGroupNumber; nextGroupNumber++)
        {
            if (hasAnyImportInCurrentGroup(mCustomImportOrderRules.get(nextGroupNumber)))
            {
                break;
            }
        }
        return mCustomImportOrderRules.get(nextGroupNumber);
    }

    /**
     * Checks if current group contains any import.
     * @param aCurrentGroup
     *        current group.
     * @return
     *        true, if current group contains at least one import.
     */
    private boolean hasAnyImportInCurrentGroup(String aCurrentGroup)
    {
        for (ImportDetails currentImport : mImportToGroupList) {
            if (aCurrentGroup.equals(currentImport.getImportGroup())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get import valid group.
     * @param aStatic
     *        is static import.
     * @param aImportPath
     *        full import path.
     * @return import valid group.
     */
    private String getImportGroup(boolean aStatic, String aImportPath)
    {
        for (String group : mCustomImportOrderRules) {
            if (matchesImportGroup(aStatic, aImportPath, group)) {
                return group;
            }
        }
        return NON_GROUP_RULE_GROUP;
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param aStatic
     *        if import is static.
     * @param aImportPath
     *        import full path.
     * @param aCurrentGroup
     *        current group.
     * @return true, if import placed in the correct group.
     */
    private boolean matchesImportGroup(boolean aStatic, String aImportPath, String aCurrentGroup)
    {
        return matchesStaticImportGroup(aStatic, aCurrentGroup)
                || matchesSamePackageImportGroup(aStatic, aImportPath, aCurrentGroup)
                || matchesSpecialImportsGroup(aStatic, aImportPath, aCurrentGroup)
                || matchesStandartImportGroup(aStatic, aImportPath, aCurrentGroup)
                || matchesThirdPartyImportGroup(aStatic, aImportPath, aCurrentGroup);
    }

    /**
     * Checks if the import is placed in the STATIC group.
     * @param aStatic
     *        is static import.
     * @param aCurrentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesStaticImportGroup(boolean aStatic, String aCurrentGroup)
    {
        return aStatic && STATIC_RULE_GROUP.equals(aCurrentGroup);
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param aStatic
     *        if import is static.
     * @param aImportPath
     *        import full path.
     * @param aCurrentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesSamePackageImportGroup(boolean aStatic,
        String aImportPath, String aCurrentGroup)
    {
        final String importPath = aImportPath.substring(0, aImportPath.lastIndexOf("."));
        return !aStatic && SAME_PACKAGE_RULE_GROUP.equals(aCurrentGroup)
                && mSamePackageDomainsRegExp.contains(importPath);
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param aStatic
     *        if import is static.
     * @param aCurrentImport
     *        import full path.
     * @param aCurrentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesStandartImportGroup(boolean aStatic,
        String aCurrentImport, String aCurrentGroup)
    {
        return !aStatic && STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(aCurrentGroup)
                && mStandardPackageRegExp.matcher(aCurrentImport).find();
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param aStatic
     *        if import is static.
     * @param aCurrentImport
     *        import full path.
     * @param aCurrentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesSpecialImportsGroup(boolean aStatic,
        String aCurrentImport, String aCurrentGroup)
    {
        return !aStatic && SPECIAL_IMPORTS_RULE_GROUP.equals(aCurrentGroup)
                && mSpecialImportsRegExp.matcher(aCurrentImport).find();
    }

    /**
     * Checks if the import is placed in the correct group.
     * @param aStatic
     *        if import is static.
     * @param aCurrentImport
     *        import full path.
     * @param aCurrentGroup
     *        current group.
     * @return true, if the import is placed in the static group.
     */
    private boolean matchesThirdPartyImportGroup(boolean aStatic,
        String aCurrentImport, String aCurrentGroup)
    {
        return !aStatic && THIRD_PARTY_PACKAGE_RULE_GROUP.equals(aCurrentGroup)
                && mThirdPartyPackageRegExp.matcher(aCurrentImport).find()
                && !mStandardPackageRegExp.matcher(aCurrentImport).find();
    }

    /**
     * Checks compare two import paths.
     * @param aCurrentImport
     *        current import.
     * @param aPreviousImport
     *        previous import.
     * @return a negative integer, zero, or a positive integer as the
     *        specified String is greater than, equal to, or less
     *        than this String, ignoring case considerations.
     */
    private int compare(String aCurrentImport, String aPreviousImport)
    {
        int indexOfPreviousDotCurrent = 0;
        int indexOfNextDotCurrent = 0;
        String tokenCurrent = "";
        int indexOfPreviousDotPrevious = 0;
        int indexOfNextDotPrevious = 0;
        String tokenPrevious = "";
        final int currentImportDomainCount = countDomains(aCurrentImport);
        final int previousImportDomainCount = countDomains(aPreviousImport);
        int result = 0;

        while (aCurrentImport.lastIndexOf(".") != indexOfPreviousDotCurrent - 1
                && aPreviousImport.lastIndexOf(".") != indexOfPreviousDotPrevious - 1)
        {
            indexOfNextDotCurrent = aCurrentImport.indexOf(".", indexOfPreviousDotCurrent + 1);
            indexOfNextDotPrevious = aPreviousImport.indexOf(".", indexOfPreviousDotPrevious + 1);
            tokenCurrent = aCurrentImport.substring(indexOfPreviousDotCurrent,
                    indexOfNextDotCurrent);
            tokenPrevious = aPreviousImport.substring(indexOfPreviousDotPrevious,
                    indexOfNextDotPrevious);
            result = tokenCurrent.compareToIgnoreCase(tokenPrevious);
            if (result != 0) {
                return result;
            }
            indexOfPreviousDotCurrent = indexOfNextDotCurrent + 1;
            indexOfPreviousDotPrevious = indexOfNextDotPrevious + 1;
        }

        if (result == 0 && (aCurrentImport.lastIndexOf(".") == indexOfPreviousDotCurrent - 1
                || aPreviousImport.lastIndexOf(".") == indexOfPreviousDotPrevious - 1))
        {
            if (currentImportDomainCount != previousImportDomainCount) {
                getClassName(indexOfNextDotPrevious, aPreviousImport);
                return currentImportDomainCount - previousImportDomainCount;
            }
            else {
                getClassName(indexOfNextDotPrevious, aPreviousImport);
                return getClassName(indexOfNextDotCurrent,
                        aCurrentImport).compareToIgnoreCase(getClassName(indexOfNextDotPrevious,
                                aPreviousImport));
            }
        }
        return 0;
    }

    /**
     * Return class name from import full path.
     * @param aStartFrom number of start.
     * @param aImport import full path.
     * @return class name.
     */
    private String getClassName(int aStartFrom, String aImport)
    {
        String className = aImport;
        className = className.substring(aStartFrom, className.length());
        final StringTokenizer token = new StringTokenizer(className, ".\r");
        return token.nextToken();
    }

    /**
     * Count number of domains.
     * @param aImportPath current import.
     * @return number of domains.
     */
    private static int countDomains(String aImportPath)
    {
        final StringTokenizer tokens = new StringTokenizer(aImportPath, ".");
        int count = 0;

        while (tokens.hasMoreTokens()) {
            if (!Character.isUpperCase(tokens.nextToken().toString().charAt(0))) {
                count++;
            }
            else {
                break;
            }
        }
        return count - 1;
    }

    /**
     * Checks if a token has a empty line before.
     * @param aLineNo
     *        Line number of current import.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(int aLineNo)
    {
        //  [lineNo - 2] is the number of the previous line
        //  because the numbering starts from zero.
        final String lineBefore = getLines()[aLineNo - 2];
        return lineBefore.trim().isEmpty();
    }

    /**
     * Forms import full path.
     * @param aToken
     *        current token.
     * @return full path or null.
     */
    private static String getFullImportIdent(DetailAST aToken)
    {
        return aToken != null ? FullIdent.createFullIdent(aToken.
                findFirstToken(TokenTypes.DOT)).getText() : "";
    }

    /**
     * Parses ordering rule and adds it to the list with rules.
     * @param aRule
     *        String with rule.
     */
    private void addRulesToList(String aRule)
    {
        if (STATIC_RULE_GROUP.equals(aRule)
                || THIRD_PARTY_PACKAGE_RULE_GROUP.equals(aRule)
                || STANDARD_JAVA_PACKAGE_RULE_GROUP.equals(aRule)
                || SPECIAL_IMPORTS_RULE_GROUP.equals(aRule))
        {
            mCustomImportOrderRules.add(aRule);

        }
        else if (aRule.startsWith(SAME_PACKAGE_RULE_GROUP)) {

            final String rule = aRule.substring(aRule.indexOf("(") + 1,
                    aRule.indexOf(")"));
            try {
                mSamePackageMatchingDepth = Integer.parseInt(rule);
            }
            catch (Exception e) {
                mSamePackageDomainsRegExp = rule;
            }
            mCustomImportOrderRules.add(SAME_PACKAGE_RULE_GROUP);

        }
        else {
            throw new RuntimeException("Unexpected rule: " + aRule);
        }
    }

    /**
     * Creates mSamePackageDomainsRegExp of the first package domains.
     * @param aCount
     *        number of first package domains.
     * @param aPackageNode
     *        package node.
     * @return same package regexp.
     */
    private static String createSamePackageRegexp(int aCount, DetailAST aPackageNode)
    {
        final StringBuilder builder = new StringBuilder();
        final String packageFullPath = getFullImportIdent(aPackageNode);
        final StringTokenizer tokens = new StringTokenizer(packageFullPath, ".");
        int count = aCount;

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
        private String mImportFullPath;

        /** Import line number */
        private int mLineNumber;

        /** Import group */
        private String mImportGroup;

        /** Is static import */
        private boolean mStatic;

        /**
         * @param aImportFullPath
         *        import full path.
         * @param aLineNumber
         *        import line number.
         * @param aImportGroup
         *        import group.
         * @param aStatic
         *        if import is static.
         */
        public ImportDetails(String aImportFullPath,
                int aLineNumber, String aImportGroup, boolean aStatic)
        {
            setImportFullPath(aImportFullPath);
            setLineNumber(aLineNumber);
            setImportGroup(aImportGroup);
            setStatic(aStatic);
        }

        /**
         * Get import full path variable.
         * @return import full path variable.
         */
        public String getImportFullPath()
        {
            return mImportFullPath;
        }

        /**
         * Set import full path variable.
         * @param aImportFullPath
         *        import full path variable.
         */
        public void setImportFullPath(String aImportFullPath)
        {
            this.mImportFullPath = aImportFullPath;
        }

        /**
         * Get import line number.
         * @return import line.
         */
        public int getLineNumber()
        {
            return mLineNumber;
        }

        /**
         * Set import line number.
         * @param aLineNumber
         *        import line number.
         */
        public void setLineNumber(int aLineNumber)
        {
            this.mLineNumber = aLineNumber;
        }

        /**
         * Get import group.
         * @return import group.
         */
        public String getImportGroup()
        {
            return mImportGroup;
        }

        /**
         * Set import group.
         * @param aImportGroup
         *        import group.
         */
        public void setImportGroup(String aImportGroup)
        {
            this.mImportGroup = aImportGroup;
        }

        /**
         * Checks if import is static.
         * @return true, if import is static.
         */
        public boolean isStatic()
        {
            return mStatic;
        }

        /**
         * Set true, if import is static
         * @param aStatic
         *        if import is static.
         */
        public void setStatic(boolean aStatic)
        {
            this.mStatic = aStatic;
        }
    }
}
