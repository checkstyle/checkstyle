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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * Checks for empty blocks. The policy to verify is specified using the {@link
 * BlockOption} class and defaults to {@link BlockOption#STMT}.
 *
 * <p> By default the check will check the following blocks:
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH}.
 * </p>
 *
 * <p> An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EmptyBlock"/&gt;
 * </pre>
 *
 * <p> An example of how to configure the check for the {@link
 * BlockOption#TEXT} policy and only catch blocks is:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyBlock"&gt;
 *    &lt;property name="tokens" value="LITERAL_CATCH"/&gt;
 *    &lt;property name="option" value="text"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Lars K�hne
 */
public class EmptyBlockCheck
    extends AbstractOptionCheck<BlockOption>
{
    /**
     * Creates a new <code>EmptyBlockCheck</code> instance.
     */
    public EmptyBlockCheck()
    {
        super(BlockOption.STMT, BlockOption.class);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_SWITCH,
            //TODO: does this handle TokenTypes.LITERAL_SYNCHRONIZED?
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST slistToken = aAST.findFirstToken(TokenTypes.SLIST);
        final DetailAST leftCurly = slistToken != null
                ? slistToken : aAST.findFirstToken(TokenTypes.LCURLY);
        if (leftCurly != null) {
            if (getAbstractOption() == BlockOption.STMT) {
                boolean emptyBlock;
                if (leftCurly.getType() == TokenTypes.LCURLY) {
                    emptyBlock = leftCurly.getNextSibling().getType() != TokenTypes.CASE_GROUP;
                }
                else {
                    emptyBlock = leftCurly.getChildCount() <= 1;
                }
                if (emptyBlock) {
                    log(leftCurly.getLineNo(),
                        leftCurly.getColumnNo(),
                        "block.noStmt",
                        aAST.getText());
                }
            }
            else if (getAbstractOption() == BlockOption.TEXT
                    && !hasText(leftCurly))
            {
                log(leftCurly.getLineNo(),
                    leftCurly.getColumnNo(),
                    "block.empty",
                    aAST.getText());
            }
        }
    }

    /**
     * @param aSlistAST a <code>DetailAST</code> value
     * @return whether the SLIST token contains any text.
     */
    protected boolean hasText(final DetailAST aSlistAST)
    {
        boolean retVal = false;

        final DetailAST rightCurly = aSlistAST.findFirstToken(TokenTypes.RCURLY);
        final DetailAST rcurlyAST = rightCurly != null
                ? rightCurly : aSlistAST.getParent().findFirstToken(TokenTypes.RCURLY);
        if (rcurlyAST != null) {
            final int slistLineNo = aSlistAST.getLineNo();
            final int slistColNo = aSlistAST.getColumnNo();
            final int rcurlyLineNo = rcurlyAST.getLineNo();
            final int rcurlyColNo = rcurlyAST.getColumnNo();
            final String[] lines = getLines();
            if (slistLineNo == rcurlyLineNo) {
                // Handle braces on the same line
                final String txt = lines[slistLineNo - 1]
                    .substring(slistColNo + 1, rcurlyColNo);
                if (txt.trim().length() != 0) {
                    retVal = true;
                }
            }
            else {
                // check only whitespace of first & last lines
                if ((lines[slistLineNo - 1]
                     .substring(slistColNo + 1).trim().length() != 0)
                    || (lines[rcurlyLineNo - 1]
                        .substring(0, rcurlyColNo).trim().length() != 0))
                {
                    retVal = true;
                }
                else {
                    // check if all lines are also only whitespace
                    for (int i = slistLineNo; i < (rcurlyLineNo - 1); i++) {
                        if (lines[i].trim().length() > 0) {
                            retVal = true;
                            break;
                        }
                    }
                }
            }
        }
        return retVal;
    }
}
