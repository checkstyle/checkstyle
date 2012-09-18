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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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
    private final Map<String, List<StringInfo>> mStringMap = Maps.newHashMap();

    /**
     * Marks the TokenTypes where duplicate strings should be ignored.
     */
    private final BitSet mIgnoreOccurrenceContext = new BitSet();

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

    /**
     * Pattern for matching ignored strings.
     */
    private Pattern mPattern;

    /**
     * Construct an instance with default values.
     */
    public MultipleStringLiteralsCheck()
    {
        setIgnoreStringsRegexp("^\"\"$");
        mIgnoreOccurrenceContext.set(TokenTypes.ANNOTATION);
    }

    /**
     * Sets regexp pattern for ignored strings.
     * @param aIgnoreStringsRegexp regexp pattern for ignored strings
     */
    public void setIgnoreStringsRegexp(String aIgnoreStringsRegexp)
    {
        if ((aIgnoreStringsRegexp != null)
            && (aIgnoreStringsRegexp.length() > 0))
        {
            mPattern = Utils.getPattern(aIgnoreStringsRegexp);
        }
        else {
            mPattern = null;
        }
    }

    /**
     * Adds a set of tokens the check is interested in.
     * @param aStrRep the string representation of the tokens interested in
     */
    public final void setIgnoreOccurrenceContext(String[] aStrRep)
    {
        mIgnoreOccurrenceContext.clear();
        for (final String s : aStrRep) {
            final int type = TokenTypes.getTokenId(s);
            mIgnoreOccurrenceContext.set(type);
        }
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.STRING_LITERAL};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (isInIgnoreOccurrenceContext(aAST)) {
            return;
        }
        final String currentString = aAST.getText();
        if ((mPattern == null) || !mPattern.matcher(currentString).find()) {
            List<StringInfo> hitList = mStringMap.get(currentString);
            if (hitList == null) {
                hitList = Lists.newArrayList();
                mStringMap.put(currentString, hitList);
            }
            final int line = aAST.getLineNo();
            final int col = aAST.getColumnNo();
            hitList.add(new StringInfo(line, col));
        }
    }

    /**
     * Analyses the path from the AST root to a given AST for occurrences
     * of the token types in {@link #mIgnoreOccurrenceContext}.
     *
     * @param aAST the node from where to start searching towards the root node
     * @return whether the path from the root node to aAST contains one of the
     * token type in {@link #mIgnoreOccurrenceContext}.
     */
    private boolean isInIgnoreOccurrenceContext(DetailAST aAST)
    {
        for (DetailAST token = aAST;
             token.getParent() != null;
             token = token.getParent())
        {
            final int type = token.getType();
            if (mIgnoreOccurrenceContext.get(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        super.beginTree(aRootAST);
        mStringMap.clear();
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {
        final Set<String> keys = mStringMap.keySet();
        for (String key : keys) {
            final List<StringInfo> hits = mStringMap.get(key);
            if (hits.size() > mAllowedDuplicates) {
                final StringInfo firstFinding = hits.get(0);
                final int line = firstFinding.getLine();
                final int col = firstFinding.getCol();
                log(line, col, "multiple.string.literal", key, hits.size());
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
