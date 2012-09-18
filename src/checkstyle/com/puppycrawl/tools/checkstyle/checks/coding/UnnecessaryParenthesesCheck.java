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

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks if unnecessary parentheses are used in a statement or expression.
 * The check will flag the following with warnings:
 * </p>
 * <pre>
 *     return (x);          // parens around identifier
 *     return (x + 1);      // parens around return value
 *     int x = (y / 2 + 1); // parens around assignment rhs
 *     for (int i = (0); i &lt; 10; i++) {  // parens around literal
 *     t -= (z + 1);        // parens around assignment rhs</pre>
 * <p>
 * The check is not "type aware", that is to say, it can't tell if parentheses
 * are unnecessary based on the types in an expression.  It also doesn't know
 * about operator precedence and associatvity; therefore it won't catch
 * something like
 * </p>
 * <pre>
 *     int x = (a + b) + c;</pre>
 * <p>
 * In the above case, given that <em>a</em>, <em>b</em>, and <em>c</em> are
 * all <code>int</code> variables, the parentheses around <code>a + b</code>
 * are not needed.
 * </p>
 *
 * @author Eric Roe
 */
public class UnnecessaryParenthesesCheck extends Check
{
    /** The minimum number of child nodes to consider for a match. */
    private static final int MIN_CHILDREN_FOR_MATCH = 3;
    /** The maximum string length before we chop the string. */
    private static final int MAX_QUOTED_LENGTH = 25;

    /** Token types for literals. */
    private static final int [] LITERALS = {
        TokenTypes.NUM_DOUBLE,
        TokenTypes.NUM_FLOAT,
        TokenTypes.NUM_INT,
        TokenTypes.NUM_LONG,
        TokenTypes.STRING_LITERAL,
        TokenTypes.LITERAL_NULL,
        TokenTypes.LITERAL_FALSE,
        TokenTypes.LITERAL_TRUE,
    };

    /** Token types for assignment operations. */
    private static final int [] ASSIGNMENTS = {
        TokenTypes.ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BOR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.BXOR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MINUS_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.SL_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.STAR_ASSIGN,
    };

    /**
     * Used to test if logging a warning in a parent node may be skipped
     * because a warning was already logged on an immediate child node.
     */
    private DetailAST mParentToSkip;
    /** Depth of nested assignments.  Normally this will be 0 or 1. */
    private int mAssignDepth;

    @Override
    public int[] getDefaultTokens()
    {
        return new int [] {
            TokenTypes.EXPR,
            TokenTypes.IDENT,
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_NULL,
            TokenTypes.LITERAL_FALSE,
            TokenTypes.LITERAL_TRUE,
            TokenTypes.ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR_ASSIGN,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final int type = aAST.getType();
        final boolean surrounded = isSurrounded(aAST);
        final DetailAST parent = aAST.getParent();

        if ((type == TokenTypes.ASSIGN)
            && (parent.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR))
        {
            // shouldn't process assign in annotation pairs
            return;
        }

        // An identifier surrounded by parentheses.
        if (surrounded && (type == TokenTypes.IDENT)) {
            mParentToSkip = aAST.getParent();
            log(aAST, "unnecessary.paren.ident", aAST.getText());
            return;
        }

        // A literal (numeric or string) surrounded by parentheses.
        if (surrounded && inTokenList(type, LITERALS)) {
            mParentToSkip = aAST.getParent();
            if (type == TokenTypes.STRING_LITERAL) {
                log(aAST, "unnecessary.paren.string",
                    chopString(aAST.getText()));
            }
            else {
                log(aAST, "unnecessary.paren.literal", aAST.getText());
            }
            return;
        }

        // The rhs of an assignment surrounded by parentheses.
        if (inTokenList(type, ASSIGNMENTS)) {
            mAssignDepth++;
            final DetailAST last = aAST.getLastChild();
            if (last.getType() == TokenTypes.RPAREN) {
                log(aAST, "unnecessary.paren.assign");
            }
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        final int type = aAST.getType();
        final DetailAST parent = aAST.getParent();

        if ((type == TokenTypes.ASSIGN)
            && (parent.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR))
        {
            // shouldn't process assign in annotation pairs
            return;
        }

        // An expression is surrounded by parentheses.
        if (type == TokenTypes.EXPR) {

            // If 'mParentToSkip' == 'aAST', then we've already logged a
            // warning about an immediate child node in visitToken, so we don't
            // need to log another one here.

            if ((mParentToSkip != aAST) && exprSurrounded(aAST)) {
                if (mAssignDepth >= 1) {
                    log(aAST, "unnecessary.paren.assign");
                }
                else if (aAST.getParent().getType()
                    == TokenTypes.LITERAL_RETURN)
                {
                    log(aAST, "unnecessary.paren.return");
                }
                else {
                    log(aAST, "unnecessary.paren.expr");
                }
            }

            mParentToSkip = null;
        }
        else if (inTokenList(type, ASSIGNMENTS)) {
            mAssignDepth--;
        }

        super.leaveToken(aAST);
    }

    /**
     * Tests if the given <code>DetailAST</code> is surrounded by parentheses.
     * In short, does <code>aAST</code> have a previous sibling whose type is
     * <code>TokenTypes.LPAREN</code> and a next sibling whose type is <code>
     * TokenTypes.RPAREN</code>.
     * @param aAST the <code>DetailAST</code> to check if it is surrounded by
     *        parentheses.
     * @return <code>true</code> if <code>aAST</code> is surrounded by
     *         parentheses.
     */
    private boolean isSurrounded(DetailAST aAST)
    {
        final DetailAST prev = aAST.getPreviousSibling();
        final DetailAST next = aAST.getNextSibling();

        return (prev != null) && (prev.getType() == TokenTypes.LPAREN)
            && (next != null) && (next.getType() == TokenTypes.RPAREN);
    }

    /**
     * Tests if the given expression node is surrounded by parentheses.
     * @param aAST a <code>DetailAST</code> whose type is
     *        <code>TokenTypes.EXPR</code>.
     * @return <code>true</code> if the expression is surrounded by
     *         parentheses.
     * @throws IllegalArgumentException if <code>aAST.getType()</code> is not
     *         equal to <code>TokenTypes.EXPR</code>.
     */
    private boolean exprSurrounded(DetailAST aAST)
    {
        if (aAST.getType() != TokenTypes.EXPR) {
            throw new IllegalArgumentException("Not an expression node.");
        }
        boolean surrounded = false;
        if (aAST.getChildCount() >= MIN_CHILDREN_FOR_MATCH) {
            final AST n1 = aAST.getFirstChild();
            final AST nn = aAST.getLastChild();

            surrounded = (n1.getType() == TokenTypes.LPAREN)
                && (nn.getType() == TokenTypes.RPAREN);
        }
        return surrounded;
    }

    /**
     * Check if the given token type can be found in an array of token types.
     * @param aType the token type.
     * @param aTokens an array of token types to search.
     * @return <code>true</code> if <code>aType</code> was found in <code>
     *         aTokens</code>.
     */
    private boolean inTokenList(int aType, int [] aTokens)
    {
        // NOTE: Given the small size of the two arrays searched, I'm not sure
        //       it's worth bothering with doing a binary search or using a
        //       HashMap to do the searches.

        boolean found = false;
        for (int i = 0; (i < aTokens.length) && !found; i++) {
            found = aTokens[i] == aType;
        }
        return found;
    }

    /**
     * Returns the specified string chopped to <code>MAX_QUOTED_LENGTH</code>
     * plus an ellipsis (...) if the length of the string exceeds <code>
     * MAX_QUOTED_LENGTH</code>.
     * @param aString the string to potentially chop.
     * @return the chopped string if <code>aString</code> is longer than
     *         <code>MAX_QUOTED_LENGTH</code>; otherwise <code>aString</code>.
     */
    private String chopString(String aString)
    {
        if (aString.length() > MAX_QUOTED_LENGTH) {
            return aString.substring(0, MAX_QUOTED_LENGTH) + "...\"";
        }
        return aString;
    }
}
