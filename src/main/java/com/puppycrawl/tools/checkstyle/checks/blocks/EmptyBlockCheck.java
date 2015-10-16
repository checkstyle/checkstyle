////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH}.
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED}.
 * </p>
 *
 * <p> An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EmptyBlock"/&gt;
 * </pre>
 *
 * <p> An example of how to configure the check for the {@link
 * BlockOption#TEXT} policy and only try blocks is:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyBlock"&gt;
 *    &lt;property name="tokens" value="LITERAL_TRY"/&gt;
 *    &lt;property name="option" value="text"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Lars Kühne
 */
public class EmptyBlockCheck
    extends AbstractOptionCheck<BlockOption> {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK_NO_STMT = "block.noStmt";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK_EMPTY = "block.empty";

    /**
     * Creates a new {@code EmptyBlockCheck} instance.
     */
    public EmptyBlockCheck() {
        super(BlockOption.STMT, BlockOption.class);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
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
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.ARRAY_INIT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST slistToken = ast.findFirstToken(TokenTypes.SLIST);
        final DetailAST leftCurly;

        if (slistToken == null) {
            leftCurly = ast.findFirstToken(TokenTypes.LCURLY);
        }
        else {
            leftCurly = slistToken;
        }

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
                        MSG_KEY_BLOCK_NO_STMT,
                        ast.getText());
                }
            }
            else if (!hasText(leftCurly)) {
                log(leftCurly.getLineNo(),
                    leftCurly.getColumnNo(),
                    MSG_KEY_BLOCK_EMPTY,
                    ast.getText());
            }
        }
    }

    /**
     * @param slistAST a {@code DetailAST} value
     * @return whether the SLIST token contains any text.
     */
    protected boolean hasText(final DetailAST slistAST) {
        final DetailAST rightCurly = slistAST.findFirstToken(TokenTypes.RCURLY);
        final DetailAST rcurlyAST;

        if (rightCurly == null) {
            rcurlyAST = slistAST.getParent().findFirstToken(TokenTypes.RCURLY);
        }
        else {
            rcurlyAST = rightCurly;
        }
        final int slistLineNo = slistAST.getLineNo();
        final int slistColNo = slistAST.getColumnNo();
        final int rcurlyLineNo = rcurlyAST.getLineNo();
        final int rcurlyColNo = rcurlyAST.getColumnNo();
        final String[] lines = getLines();
        boolean returnValue = false;
        if (slistLineNo == rcurlyLineNo) {
            // Handle braces on the same line
            final String txt = lines[slistLineNo - 1]
                    .substring(slistColNo + 1, rcurlyColNo);
            if (StringUtils.isNotBlank(txt)) {
                returnValue = true;
            }
        }
        else {
            // check only whitespace of first & last lines
            if (lines[slistLineNo - 1].substring(slistColNo + 1).trim().isEmpty()
                    && lines[rcurlyLineNo - 1].substring(0, rcurlyColNo).trim().isEmpty()) {
                // check if all lines are also only whitespace
                returnValue = !checkIsAllLinesAreWhitespace(lines, slistLineNo, rcurlyLineNo);
            }
            else {
                returnValue = true;
            }
        }
        return returnValue;
    }

    /**
     * Checks is all lines in array contain whitespaces only.
     *
     * @param lines
     *            array of lines
     * @param lineFrom
     *            check from this line number
     * @param lineTo
     *            check to this line numbers
     * @return true if lines contain only whitespaces
     */
    private static boolean checkIsAllLinesAreWhitespace(String[] lines, int lineFrom, int lineTo) {
        boolean result = true;
        for (int i = lineFrom; i < lineTo - 1; i++) {
            if (!lines[i].trim().isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }
}
