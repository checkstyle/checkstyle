////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>
 * Checks for restricted tokens beneath other tokens.
 * </p>
 * <p>
 * Examples of how to configure the check:
 * </p>
 * <pre>
 * &lt;!-- String literal equality check --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="EQUAL,NOT_EQUAL"/&gt;
 *     &lt;property name="limitedTokens" value="STRING_LITERAL"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="maximumDepth" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Switch with no default --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_DEFAULT"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Assert statement may have side effects --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_ASSERT"/&gt;
 *     &lt;property name="limitedTokens" value="ASSIGN,DEC,INC,POST_DEC,
 *     POST_INC,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,
 *     BSR_ASSIGN,SR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,
 *     METHOD_CALL"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Initialiser in for performs no setup - use while instead? --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="FOR_INIT"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Condition in for performs no check --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="FOR_CONDITION"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="minimumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Switch within switch --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="minimumDepth" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Return from within a catch or finally block --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_FINALLY,LITERAL_CATCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_RETURN"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Try within catch or finally block --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_CATCH,LITERAL_FINALLY"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_TRY"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many cases within a switch --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_SWITCH"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_CASE"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many local variables within a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many returns from within a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="LITERAL_RETURN"/&gt;
 *     &lt;property name="maximumNumber" value="3"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many fields within an interface --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="INTERFACE_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Limit the number of exceptions a method can throw --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="LITERAL_THROWS"/&gt;
 *     &lt;property name="limitedTokens" value="IDENT"/&gt;
 *     &lt;property name="maximumNumber" value="1"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Limit the number of expressions in a method --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="EXPR"/&gt;
 *     &lt;property name="maximumNumber" value="200"/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Disallow empty statements --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="EMPTY_STAT"/&gt;
 *     &lt;property name="limitedTokens" value="EMPTY_STAT"/&gt;
 *     &lt;property name="maximumNumber" value="0"/&gt;
 *     &lt;property name="maximumDepth" value="0"/&gt;
 *     &lt;property name="maximumMessage"
 *         value="Empty statement is not allowed."/&gt;
 * &lt;/module&gt;
 *
 * &lt;!-- Too many fields within a class --&gt;
 * &lt;module name="DescendantToken"&gt;
 *     &lt;property name="tokens" value="CLASS_DEF"/&gt;
 *     &lt;property name="limitedTokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="maximumDepth" value="2"/&gt;
 *     &lt;property name="maximumNumber" value="10"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Tim Tyler &lt;tim@tt1.org&gt;
 * @author Rick Giles
 */
public class DescendantTokenCheck extends Check
{
     /** minimum  depth */
    private int mMinimumDepth;
    /** maximum depth */
    private int mMaximumDepth = Integer.MAX_VALUE;
    /** minimum number */
    private int mMinimumNumber;
    /** maximum number */
    private int mMaximumNumber = Integer.MAX_VALUE;
    /** Whether to sum the number of tokens found. */
    private boolean mSumTokenCounts;
    /** limited tokens */
    private int[] mLimitedTokens = new int[0];
    /** error message when minimum count not reached */
    private String mMinimumMessage;
    /** error message when maximum count exceeded */
    private String mMaximumMessage;

    /**
     * Counts of descendant tokens.
     * Indexed by (token ID - 1) for performance.
     */
    private int[] mCounts = new int[0];

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        //reset counts
        Arrays.fill(mCounts, 0);
        countTokens(aAST, 0);

        // name of this token
        final String name = TokenTypes.getTokenName(aAST.getType());

        if (mSumTokenCounts) {
            int total = 0;
            for (int element : mLimitedTokens) {
                total += mCounts[element - 1];
            }
            if (total < mMinimumNumber) {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                        (null == mMinimumMessage) ? "descendant.token.sum.min"
                                : mMinimumMessage,
                        String.valueOf(total),
                        String.valueOf(mMinimumNumber), name);
            }
            if (total > mMaximumNumber) {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                        (null == mMaximumMessage) ? "descendant.token.sum.max"
                                : mMaximumMessage,
                        String.valueOf(total),
                        String.valueOf(mMaximumNumber),
                        name);
            }
        }
        else {
            for (int element : mLimitedTokens) {
                final int tokenCount = mCounts[element - 1];
                if (tokenCount < mMinimumNumber) {
                    final String descendantName = TokenTypes
                            .getTokenName(element);
                    log(aAST.getLineNo(), aAST.getColumnNo(),
                            (null == mMinimumMessage) ? "descendant.token.min"
                                    : mMinimumMessage,
                            String.valueOf(tokenCount),
                            String.valueOf(mMinimumNumber),
                            name,
                            descendantName);
                }
                if (tokenCount > mMaximumNumber) {
                    final String descendantName = TokenTypes
                            .getTokenName(element);
                    log(aAST.getLineNo(), aAST.getColumnNo(),
                            (null == mMaximumMessage) ? "descendant.token.max"
                                    : mMaximumMessage,
                            String.valueOf(tokenCount),
                            String.valueOf(mMaximumNumber),
                            name,
                            descendantName);
                }
            }
        }
    }

    /**
     * Counts the number of occurrences of descendant tokens.
     * @param aAST the root token for descendants.
     * @param aDepth the maximum depth of the counted descendants.
     */
    private void countTokens(AST aAST, int aDepth)
    {
        if (aDepth <= mMaximumDepth) {
            //update count
            if (aDepth >= mMinimumDepth) {
                final int type = aAST.getType();
                if (type <= mCounts.length) {
                    mCounts[type - 1]++;
                }
            }
            AST child = aAST.getFirstChild();
            final int nextDepth = aDepth + 1;
            while (child != null) {
                countTokens(child, nextDepth);
                child = child.getNextSibling();
            }
        }
    }

    @Override
    public int[] getAcceptableTokens()
    {
        // Any tokens set by property 'tokens' are acceptable
        final Set<String> tokenNames = getTokenNames();
        final int[] result = new int[tokenNames.size()];
        int i = 0;
        for (String name : tokenNames) {
            result[i++] = TokenTypes.getTokenId(name);
        }
        return result;
    }

    /**
     * Sets the tokens which occurance as descendant is limited.
     * @param aLimitedTokens - list of tokens to ignore.
     */
    public void setLimitedTokens(String[] aLimitedTokens)
    {
        mLimitedTokens = new int[aLimitedTokens.length];

        int maxToken = 0;
        for (int i = 0; i < aLimitedTokens.length; i++) {
            mLimitedTokens[i] = TokenTypes.getTokenId(aLimitedTokens[i]);
            if (mLimitedTokens[i] > maxToken) {
                maxToken = mLimitedTokens[i];
            }
        }
        mCounts = new int[maxToken];
    }

    /**
     * Sets the mimimum depth for descendant counts.
     * @param aMinimumDepth the mimimum depth for descendant counts.
     */
    public void setMinimumDepth(int aMinimumDepth)
    {
        mMinimumDepth = aMinimumDepth;
    }

    /**
     * Sets the maximum depth for descendant counts.
     * @param aMaximumDepth the maximum depth for descendant counts.
     */
    public void setMaximumDepth(int aMaximumDepth)
    {
        mMaximumDepth = aMaximumDepth;
    }

   /**
    * Sets a minimum count for descendants.
    * @param aMinimumNumber the minimum count for descendants.
    */
    public void setMinimumNumber(int aMinimumNumber)
    {
        mMinimumNumber = aMinimumNumber;
    }

    /**
      * Sets a maximum count for descendants.
      * @param aMaximumNumber the maximum count for descendants.
      */
    public void setMaximumNumber(int aMaximumNumber)
    {
        mMaximumNumber = aMaximumNumber;
    }

    /**
     * Sets the error message for minimum count not reached.
     * @param aMessage the error message for minimum count not reached.
     * Used as a <code>MessageFormat</code> pattern with arguments
     * <ul>
     * <li>{0} - token count</li>
     * <li>{1} - minimum number</li>
     * <li>{2} - name of token</li>
     * <li>{3} - name of limited token</li>
     * </ul>
     */
    public void setMinimumMessage(String aMessage)
    {
        mMinimumMessage = aMessage;
    }

    /**
     * Sets the error message for maximum count exceeded.
     * @param aMessage the error message for maximum count exceeded.
     * Used as a <code>MessageFormat</code> pattern with arguments
     * <ul>
     * <li>{0} - token count</li>
     * <li>{1} - maximum number</li>
     * <li>{2} - name of token</li>
     * <li>{3} - name of limited token</li>
     * </ul>
     */

    public void setMaximumMessage(String aMessage)
    {
        mMaximumMessage = aMessage;
    }

    /**
     * Sets whether to use the sum of the tokens found, rather than the
     * individual counts.
     * @param aSum whether to use the sum.
     */
    public void setSumTokenCounts(boolean aSum)
    {
        mSumTokenCounts = aSum;
    }
}
