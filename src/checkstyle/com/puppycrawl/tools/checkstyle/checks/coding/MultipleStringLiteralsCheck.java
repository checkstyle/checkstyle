////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for multiple occurrences of the same string literal within a
 * single file.
 *
 * @author Daniel Grenner
 */
public class MultipleStringLiteralsCheck extends Check
{
    /**
     * The found strings and their positions.
     * <String, ArrayList>, with the ArrayList containing StringInfo objects.
     */
    private HashMap mStringMap = new HashMap();
    /**
     * The allowed number of string duplicates in a file before an error is
     * generated.
     */
    private int mAllowedDuplicates = 1;

    /**
     * Sets the maximum allowed duplicates of a string.
     * @param aAllowedDuplicates The maximum number of duplicates.
     */
    public void setAllowedDuplicates(int aAllowedDuplicates)
    {
        mAllowedDuplicates = aAllowedDuplicates;
    }

    /** {@inheritDoc} */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.STRING_LITERAL};
    }

    /** {@inheritDoc} */
    public void visitToken(DetailAST aAST)
    {
        final String currentString = aAST.getText();
        ArrayList hitList = (ArrayList) mStringMap.get(currentString);
        if (hitList == null) {
            hitList = new ArrayList();
            mStringMap.put(currentString, hitList);
        }
        final int line = aAST.getLineNo();
        final int col = aAST.getColumnNo();
        hitList.add(new StringInfo(line, col));
    }

    /** {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
        super.beginTree(aRootAST);
        mStringMap.clear();
    }

    /** {@inheritDoc} */
    public void finishTree(DetailAST aRootAST)
    {
        final Set keys = mStringMap.keySet();
        final Iterator keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            final String key = (String) keyIterator.next();
            final ArrayList hits = (ArrayList) mStringMap.get(key);
            if (hits.size() > mAllowedDuplicates) {
                final StringInfo firstFinding = (StringInfo) hits.get(0);
                final int line = firstFinding.getLine();
                final int col = firstFinding.getCol();
                final Object[] args =
                    new Object[]{key, new Integer(hits.size())};
                log(line, col, "multiple.string.literal", args);
            }
        }
    }

    /**
     * This class contains information about where a string was found.
     */
    private static final class StringInfo
    {
        /**
         * Line of finding
         */
        private final int mLine;
        /**
         * Column of finding
         */
        private final int mCol;
        /**
         * Creates information about a string position.
         * @param aLine int
         * @param aCol int
         */
        private StringInfo(int aLine, int aCol)
        {
            mLine = aLine;
            mCol = aCol;
        }

        /**
         * The line where a string was found.
         * @return int Line of the string.
         */
        private int getLine()
        {
            return mLine;
        }

        /**
         * The column where a string was found.
         * @return int Column of the string.
         */
        private int getCol()
        {
            return mCol;
        }
    }
}
