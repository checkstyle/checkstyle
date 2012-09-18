////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <ul>
 * <li>groups imports: ensures that groups of imports come in a specific order
 * (e.g., java. comes first, javax. comes second, then everything else)</li>
 * <li>adds a separation between groups : ensures that a blank line sit between
 * each group</li>
 * <li>sorts imports inside each group: ensures that imports within each group
 * are in lexicographic order</li>
 * <li>sorts according to case: ensures that the comparison between import is
 * case sensitive</li>
 * <li>groups static imports: ensures that static imports are at the top (or the
 * bottom) of all the imports, or above (or under) each group, or are treated
 * like non static imports (@see {@link ImportOrderOption}</li>
 * </ul>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 *  &lt;module name=&quot;ImportOrder&quot;&gt;
 *    &lt;property name=&quot;groups&quot; value=&quot;java,javax&quot;/&gt;
 *    &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;caseSensitive&quot; value=&quot;false&quot;/&gt;
 *    &lt;property name=&quot;option&quot; value=&quot;above&quot;/&gt;
 *  &lt;/module&gt;
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
 * <p>
 * Defaults:
 * </p>
 * <ul>
 * <li>import groups: none</li>
 * <li>separation: false</li>
 * <li>ordered: true</li>
 * <li>case sensitive: true</li>
 * <li>static import: under</li>
 * </ul>
 *
 * <p>
 * Compatible with Java 1.5 source.
 * </p>
 *
 * @author Bill Schneider
 * @author o_sukhodolsky
 * @author David DIDIER
 * @author Steve McKay
 */
public class ImportOrderCheck
    extends AbstractOptionCheck<ImportOrderOption>
{

    /** the special wildcard that catches all remaining groups. */
    private static final String WILDCARD_GROUP_NAME = "*";

    /** List of import groups specified by the user. */
    private Pattern[] mGroups = new Pattern[0];
    /** Require imports in group be separated. */
    private boolean mSeparated;
    /** Require imports in group. */
    private boolean mOrdered = true;
    /** Should comparison be case sensitive. */
    private boolean mCaseSensitive = true;

    /** Last imported group. */
    private int mLastGroup;
    /** Line number of last import. */
    private int mLastImportLine;
    /** Name of last import. */
    private String mLastImport;
    /** If last import was static. */
    private boolean mLastImportStatic;
    /** Whether there was any imports. */
    private boolean mBeforeFirstImport;

    /**
     * Groups static imports under each group.
     */
    public ImportOrderCheck()
    {
        super(ImportOrderOption.UNDER, ImportOrderOption.class);
    }

    /**
     * Sets the list of package groups and the order they should occur in the
     * file.
     *
     * @param aGroups a comma-separated list of package names/prefixes.
     */
    public void setGroups(String[] aGroups)
    {
        mGroups = new Pattern[aGroups.length];

        for (int i = 0; i < aGroups.length; i++) {
            String pkg = aGroups[i];
            Pattern grp;

            // if the pkg name is the wildcard, make it match zero chars
            // from any name, so it will always be used as last resort.
            if (WILDCARD_GROUP_NAME.equals(pkg)) {
                grp = Pattern.compile(""); // matches any package
            }
            else if (pkg.startsWith("/")) {
                if (!pkg.endsWith("/")) {
                    throw new IllegalArgumentException("Invalid group");
                }
                pkg = pkg.substring(1, pkg.length() - 1);
                grp = Pattern.compile(pkg);
            }
            else {
                if (!pkg.endsWith(".")) {
                    pkg = pkg + ".";
                }
                grp = Pattern.compile("^" + Pattern.quote(pkg));
            }

            mGroups[i] = grp;
        }
    }

    /**
     * Sets whether or not imports should be ordered within any one group of
     * imports.
     *
     * @param aOrdered
     *            whether lexicographic ordering of imports within a group
     *            required or not.
     */
    public void setOrdered(boolean aOrdered)
    {
        mOrdered = aOrdered;
    }

    /**
     * Sets whether or not groups of imports must be separated from one another
     * by at least one blank line.
     *
     * @param aSeparated
     *            whether groups should be separated by oen blank line.
     */
    public void setSeparated(boolean aSeparated)
    {
        mSeparated = aSeparated;
    }

    /**
     * Sets whether string comparison should be case sensitive or not.
     *
     * @param aCaseSensitive
     *            whether string comparison should be case sensitive.
     */
    public void setCaseSensitive(boolean aCaseSensitive)
    {
        mCaseSensitive = aCaseSensitive;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mLastGroup = Integer.MIN_VALUE;
        mLastImportLine = Integer.MIN_VALUE;
        mLastImport = "";
        mLastImportStatic = false;
        mBeforeFirstImport = true;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final FullIdent ident;
        final boolean isStatic;

        if (aAST.getType() == TokenTypes.IMPORT) {
            ident = FullIdent.createFullIdentBelow(aAST);
            isStatic = false;
        }
        else {
            ident = FullIdent.createFullIdent(aAST.getFirstChild()
                    .getNextSibling());
            isStatic = true;
        }

        switch (getAbstractOption()) {
        case TOP:
            if (!isStatic && mLastImportStatic) {
                mLastGroup = Integer.MIN_VALUE;
                mLastImport = "";
            }
            // no break;

        case ABOVE:
            // previous non-static but current is static
            doVisitToken(ident, isStatic, (!mLastImportStatic && isStatic));
            break;

        case INFLOW:
            // previous argument is useless here
            doVisitToken(ident, isStatic, true);
            break;

        case BOTTOM:
            if (isStatic && !mLastImportStatic) {
                mLastGroup = Integer.MIN_VALUE;
                mLastImport = "";
            }
            // no break;

        case UNDER:
            // previous static but current is non-static
            doVisitToken(ident, isStatic, (mLastImportStatic && !isStatic));
            break;

        default:
            break;
        }

        mLastImportLine = aAST.findFirstToken(TokenTypes.SEMI).getLineNo();
        mLastImportStatic = isStatic;
        mBeforeFirstImport = false;
    }

    /**
     * Shares processing...
     *
     * @param aIdent the import to process.
     * @param aIsStatic whether the token is static or not.
     * @param aPrevious previous non-static but current is static (above), or
     *                  previous static but current is non-static (under).
     */
    private void doVisitToken(FullIdent aIdent, boolean aIsStatic,
            boolean aPrevious)
    {
        if (aIdent != null) {
            final String name = aIdent.getText();
            final int groupIdx = getGroupNumber(name);
            final int line = aIdent.getLineNo();

            if (groupIdx > mLastGroup) {
                if (!mBeforeFirstImport && mSeparated) {
                    // This check should be made more robust to handle
                    // comments and imports that span more than one line.
                    if ((line - mLastImportLine) < 2) {
                        log(line, "import.separation", name);
                    }
                }
            }
            else if (groupIdx == mLastGroup) {
                doVisitTokenInSameGroup(aIdent, aIsStatic, aPrevious, name,
                        line);
            }
            else {
                log(line, "import.ordering", name);
            }

            mLastGroup = groupIdx;
            mLastImport = name;
        }
    }

    /**
     * Shares processing...
     *
     * @param aIdent the import to process.
     * @param aIsStatic whether the token is static or not.
     * @param aPrevious previous non-static but current is static (above), or
     *    previous static but current is non-static (under).
     * @param aName the name of the current import.
     * @param aLine the line of the current import.
     */
    private void doVisitTokenInSameGroup(FullIdent aIdent, boolean aIsStatic,
            boolean aPrevious, String aName, int aLine)
    {
        if (!mOrdered) {
            return;
        }

        if (getAbstractOption().equals(ImportOrderOption.INFLOW)) {
            // out of lexicographic order
            if (compare(mLastImport, aName, mCaseSensitive) > 0) {
                log(aLine, "import.ordering", aName);
            }
        }
        else {
            final boolean shouldFireError =
                // current and previous static or current and
                // previous non-static
                (!(mLastImportStatic ^ aIsStatic)
                &&
                // and out of lexicographic order
                (compare(mLastImport, aName, mCaseSensitive) > 0))
                ||
                // previous non-static but current is static (above)
                // or
                // previous static but current is non-static (under)
                aPrevious;

            if (shouldFireError) {
                log(aLine, "import.ordering", aName);
            }
        }
    }

    /**
     * Finds out what group the specified import belongs to.
     *
     * @param aName the import name to find.
     * @return group number for given import name.
     */
    private int getGroupNumber(String aName)
    {
        int bestIndex = mGroups.length;
        int bestLength = -1;
        int bestPos = 0;

        // find out what group this belongs in
        // loop over mGroups and get index
        for (int i = 0; i < mGroups.length; i++) {
            final Matcher matcher = mGroups[i].matcher(aName);
            while (matcher.find()) {
                final int length = matcher.end() - matcher.start();
                if ((length > bestLength)
                    || ((length == bestLength) && (matcher.start() < bestPos)))
                {
                    bestIndex = i;
                    bestLength = length;
                    bestPos = matcher.start();
                }
            }
        }

        return bestIndex;
    }

    /**
     * Compares two strings.
     *
     * @param aString1
     *            the first string.
     * @param aString2
     *            the second string.
     * @param aCaseSensitive
     *            whether the comparison is case sensitive.
     * @return the value <code>0</code> if string1 is equal to string2; a value
     *         less than <code>0</code> if string1 is lexicographically less
     *         than the string2; and a value greater than <code>0</code> if
     *         string1 is lexicographically greater than string2.
     */
    private int compare(String aString1, String aString2,
            boolean aCaseSensitive)
    {
        if (aCaseSensitive) {
            return aString1.compareTo(aString2);
        }

        return aString1.compareToIgnoreCase(aString2);
    }
}
