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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * <p>
 * Checks the placement of right curly braces.
 * The policy to verify is specified using the {@link RightCurlyOption} class
 * and defaults to {@link RightCurlyOption#SAME}.
 * </p>
 * <p> By default the check will check the following tokens:
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY}.
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY}.
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check with policy
 * {@link RightCurlyOption#ALONE} for <code>else</code> tokens is:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"&gt;
 *     &lt;property name="tokens" value="LITERAL_ELSE"/&gt;
 *     &lt;property name="option" value="alone"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Oliver Burn
 * @author lkuehne
 * @author o_sukhodolsky
 * @version 2.0
 */
public class RightCurlyCheck extends AbstractOptionCheck<RightCurlyOption>
{
    /** Do we need to check if rculry starts line. */
    private boolean mShouldStartLine = true;

    /**
     * Sets the right curly option to same.
     */
    public RightCurlyCheck()
    {
        super(RightCurlyOption.SAME, RightCurlyOption.class);
    }

    /**
     * Does the check need to check if rcurly starts line.
     * @param aFlag new value of this property.
     */
    public void setShouldStartLine(boolean aFlag)
    {
        mShouldStartLine = aFlag;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        // Attempt to locate the tokens to do the check
        DetailAST rcurly;
        DetailAST lcurly;
        DetailAST nextToken;
        boolean shouldCheckLastRcurly = false;

        switch (aAST.getType()) {
        case TokenTypes.LITERAL_TRY:
            lcurly = aAST.getFirstChild();
            nextToken = lcurly.getNextSibling();
            rcurly = lcurly.getLastChild();
            break;
        case TokenTypes.LITERAL_CATCH:
            nextToken = aAST.getNextSibling();
            lcurly = aAST.getLastChild();
            rcurly = lcurly.getLastChild();
            if (nextToken == null) {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(aAST);
            }
            break;
        case TokenTypes.LITERAL_IF:
            nextToken = aAST.findFirstToken(TokenTypes.LITERAL_ELSE);
            if (nextToken != null) {
                lcurly = nextToken.getPreviousSibling();
                rcurly = lcurly.getLastChild();
            }
            else {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(aAST);
                lcurly = aAST.getLastChild();
                rcurly = lcurly.getLastChild();
            }
            break;
        case TokenTypes.LITERAL_ELSE:
            shouldCheckLastRcurly = true;
            nextToken = getNextToken(aAST);
            lcurly = aAST.getFirstChild();
            rcurly = lcurly.getLastChild();
            break;
        case TokenTypes.LITERAL_FINALLY:
            shouldCheckLastRcurly = true;
            nextToken = getNextToken(aAST);
            lcurly = aAST.getFirstChild();
            rcurly = lcurly.getLastChild();
            break;
        default:
            throw new RuntimeException("Unexpected token type ("
                    + TokenTypes.getTokenName(aAST.getType()) + ")");
        }

        if ((rcurly == null) || (rcurly.getType() != TokenTypes.RCURLY)) {
            // we need to have both tokens to perform the check
            return;
        }

        if (shouldCheckLastRcurly) {
            if (rcurly.getLineNo() == nextToken.getLineNo()) {
                log(rcurly, "line.alone", "}");
            }
        }
        else if ((getAbstractOption() == RightCurlyOption.SAME)
            && (rcurly.getLineNo() != nextToken.getLineNo()))
        {
            log(rcurly, "line.same", "}");
        }
        else if ((getAbstractOption() == RightCurlyOption.ALONE)
                 && (rcurly.getLineNo() == nextToken.getLineNo()))
        {
            log(rcurly, "line.alone", "}");
        }

        if (!mShouldStartLine) {
            return;
        }
        final boolean startsLine =
            Utils.whitespaceBefore(rcurly.getColumnNo(),
                                   getLines()[rcurly.getLineNo() - 1]);

        if (!startsLine && (lcurly.getLineNo() != rcurly.getLineNo())) {
            log(rcurly, "line.new", "}");
        }
    }

    /**
     * Finds next token after the given one.
     * @param aAST the given node.
     * @return the token which represents next lexical item.
     */
    private DetailAST getNextToken(DetailAST aAST)
    {
        DetailAST next = null;
        DetailAST parent = aAST;
        while ((parent != null) && (next == null)) {
            next = parent.getNextSibling();
            parent = parent.getParent();
        }
        return CheckUtils.getFirstNode(next);
    }
}
