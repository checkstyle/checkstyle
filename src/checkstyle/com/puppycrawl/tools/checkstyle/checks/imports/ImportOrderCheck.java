////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Class to check the ordering/grouping of imports.  Ensures that
 * groups of imports come in a specific order (e.g., java. comes
 * first, javax. comes second, then everything else) and imports
 * within each group are in lexicographic order. Static imports must
 * be at the end of a group and in lexicographic order amongst themselves.
 *
 * <p>
 * Example:
 * <pre>
 *  &lt;module name=&quot;ImportOrder&quot;>
 *    &lt;property name=&quot;groups&quot; value=&quot;java,javax&quot;/>
 *    &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/>
 *    &lt;property name=&quot;caseSensitive&quot; value=&quot;false&quot;/>
 *  &lt;/module>
 * </pre>
 *
 * There is always an additional, implied &quot;everything else&quot; package
 * group.  If no &quot;groups&quot; property is supplied, all imports belong in
 * this &quot;everything else&quot; group.  </p>
 *
 * <p>
 * ordered defaults to true.
 * </p>
 *
 * <p>
 * separated defaults to false.
 * </p>
 *
 * Compatible with Java 1.5 source.
 *
 * @author Bill Schneider
 * @author o_sukhodolsky
 */
public class ImportOrderCheck extends Check
{
    /** List of import groups specified by the user. */
    private String[] mGroups = new String[0];

    /** Require imports in group. */
    private boolean mOrdered = true;

    /** Require imports in group be separated. */
    private boolean mSeparated;
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
     * Default constructor.
     */
    public ImportOrderCheck()
    {
    }

    /**
     * sets the list of package groups and the order they should
     * occur in the  file.
     *
     * @param aGroups a comma-separated list of package names/prefixes
     */
    public void setGroups(String[] aGroups)
    {
        mGroups = new String[ aGroups.length ];

        for (int i = 0; i < aGroups.length; i++) {
            String pkg = aGroups[i];

            if (!pkg.endsWith(".")) {
                pkg = pkg + ".";
            }

            mGroups[i] = pkg;
        }
    }

    /**
     * Sets whether or not imports should be ordered within any one
     * group of imports.
     *
     * @param aOrdered whether lexicographic ordering of imports within
     *                 a group required or not.
     */
    public void setOrdered(boolean aOrdered)
    {
        mOrdered = aOrdered;
    }

    /**
     * Sets whether or not groups of imports must be separated from
     * one another by at least one blank line.
     *
     * @param aSeparated whehter groups should be separated by blank line.
     */
    public void setSeparated(boolean aSeparated)
    {
        mSeparated = aSeparated;
    }

    /**
     * Sets whether string comparision should be case sensitive
     * or not.
     * @param aCaseSensitive whether string comparision should be
     *                       case sensitive.
     */
    public void setCaseSensitive(boolean aCaseSensitive)
    {
        mCaseSensitive = aCaseSensitive;
    }

    /** {@inheritDoc} */
    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    /** {@inheritDoc} */
    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /**
     * @param aName import name to check.
     * @return group number for given import name.
     */
    private int getGroupNumber(String aName)
    {
        int i = 0;

        // find out what group this belongs in
        // loop over mGroups and get index
        for (; i < mGroups.length; i++) {
            if (aName.startsWith(mGroups[i])) {
                break;
            }
        }

        return i;
    }

    /** {@inheritDoc} */
    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mLastGroup = Integer.MIN_VALUE;
        mLastImportLine = Integer.MIN_VALUE;
        mLastImport = "";
        mLastImportStatic = false;
        mBeforeFirstImport = true;
    }

    /** {@inheritDoc} */
    @Override
    public void visitToken(DetailAST aAST)
    {
        final FullIdent ident;
        boolean isStatic;
        if (aAST.getType() == TokenTypes.IMPORT) {
            ident = FullIdent.createFullIdentBelow(aAST);
            isStatic = false;
        }
        else {
            ident = FullIdent.createFullIdent(
                (DetailAST) aAST.getFirstChild().getNextSibling());
            isStatic = true;
        }

        if (ident != null) {
            final String name = ident.getText();
            final int groupIdx = getGroupNumber(name);
            final int line = ident.getLineNo();

            if (groupIdx > mLastGroup) {
                if (!mBeforeFirstImport && mSeparated) {
                    // This check should be made more robust to handle
                    // comments and imports that span more than one line.
                    if (line - mLastImportLine < 2) {
                        log(line, "import.separation", name);
                    }
                }
            }
            else if (groupIdx == mLastGroup) {
                if (mOrdered) {
                    boolean shouldFireError = false;
                    if (mCaseSensitive) {
                        shouldFireError =
                            //current and previous static or current and
                            //previous non-static
                            (!(mLastImportStatic ^ isStatic)
                            &&
                            //and out of lexicographic order
                            (mLastImport.compareTo(name) >= 0))
                            ||
                            //previous static but current is non-static
                            (mLastImportStatic && !isStatic);
                    }
                    else {
                        shouldFireError =
                                //current and previous static or current and
                                //previous non-static
                                (!(mLastImportStatic ^ isStatic)
                                &&
                                //and out of lexicographic order
                                (mLastImport.compareToIgnoreCase(name) >= 0))
                                ||
                                //previous static but current is non-static
                                (mLastImportStatic && !isStatic);
                    }
                    if (shouldFireError) {
                        log(line, "import.ordering", name);
                    }
                }
            }
            else {
                log(line, "import.ordering", name);
            }

            mLastGroup = groupIdx;
            mLastImport = name;
            mLastImportLine = aAST.findFirstToken(TokenTypes.SEMI).getLineNo();
            mLastImportStatic = isStatic;
            mBeforeFirstImport = false;
        }
    }
}
