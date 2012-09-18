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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks the padding of parentheses; that is whether a space is required
 * after a left parenthesis and before a right parenthesis, or such spaces are
 * forbidden, with the exception that it does
 * not check for padding of the right parenthesis  at an empty for iterator.
 * Use Check {@link EmptyForIteratorPadCheck EmptyForIteratorPad} to validate
 * empty for iterators.
 * <p>
 * </p>
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p> By default the check will check parentheses that occur with the following
 * tokens:
 *  {@link TokenTypes#CTOR_CALL CTOR_CALL},
 *  {@link TokenTypes#LPAREN LPAREN},
 *  {@link TokenTypes#METHOD_CALL METHOD_CALL},
 *  {@link TokenTypes#RPAREN RPAREN},
 *  {@link TokenTypes#SUPER_CTOR_CALL SUPER_CTOR_CALL},
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to require spaces for the
 * parentheses of constructor, method, and super constructor invocations is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"&gt;
 *     &lt;property name="tokens"
 *               value="CTOR_CALL, METHOD_CALL, SUPER_CTOR_CALL"/&gt;
 *     &lt;property name="option" value="space"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @version 1.0
 */
public class ParenPadCheck extends AbstractParenPadCheck
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.RPAREN,
                          TokenTypes.LPAREN,
                          TokenTypes.CTOR_CALL,
                          TokenTypes.SUPER_CTOR_CALL,
                          TokenTypes.METHOD_CALL,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        DetailAST theAst = aAST;
        // Strange logic in this method to guard against checking RPAREN tokens
        // that are associated with a TYPECAST token.
        if (theAst.getType() != TokenTypes.RPAREN) {
            if ((theAst.getType() == TokenTypes.CTOR_CALL)
                || (theAst.getType() == TokenTypes.SUPER_CTOR_CALL))
            {
                theAst = theAst.getFirstChild();
            }
            if (!isPreceedsEmptyForInit(theAst)) {
                processLeft(theAst);
            }
        }
        else if ((theAst.getParent() == null)
                 || (theAst.getParent().getType() != TokenTypes.TYPECAST)
                 || (theAst.getParent().findFirstToken(TokenTypes.RPAREN)
                     != theAst))
        {
            if (!isFollowsEmptyForIterator(theAst)) {
                processRight(theAst);
            }
        }
    }

    /**
     * @param aAST the token to check
     * @return whether a token follows an empty for iterator
     */
    private boolean isFollowsEmptyForIterator(DetailAST aAST)
    {
        boolean followsEmptyForIterator = false;
        final DetailAST parent = aAST.getParent();
        //Only traditional for statements are examined, not for-each statements
        if ((parent != null)
            && (parent.getType() == TokenTypes.LITERAL_FOR)
            && (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null))
        {
            final DetailAST forIterator =
                parent.findFirstToken(TokenTypes.FOR_ITERATOR);
            followsEmptyForIterator = (forIterator.getChildCount() == 0)
                && (aAST == forIterator.getNextSibling());
        }
        return followsEmptyForIterator;
    }

    /**
     * @param aAST the token to check
     * @return whether a token preceeds an empty for initializer
     */
    private boolean isPreceedsEmptyForInit(DetailAST aAST)
    {
        boolean preceedsEmptyForInintializer = false;
        final DetailAST parent = aAST.getParent();
        //Only traditional for statements are examined, not for-each statements
        if ((parent != null)
            && (parent.getType() == TokenTypes.LITERAL_FOR)
            && (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null))
        {
            final DetailAST forIterator =
                    parent.findFirstToken(TokenTypes.FOR_INIT);
            preceedsEmptyForInintializer = (forIterator.getChildCount() == 0)
                    && (aAST == forIterator.getPreviousSibling());
        }
        return preceedsEmptyForInintializer;
    }
}
