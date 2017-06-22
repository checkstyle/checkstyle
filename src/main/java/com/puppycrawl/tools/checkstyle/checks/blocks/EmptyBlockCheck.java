////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Checks for empty blocks. This check does not validate sequential blocks.
 * The policy to verify is specified using the {@link
 * BlockOption} class and defaults to {@link BlockOption#STATEMENT}.
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
    extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK_NO_STATEMENT = "block.noStatement";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK_EMPTY = "block.empty";

    /** The policy to enforce. */
    private BlockOption option = BlockOption.STATEMENT;

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = BlockOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + optionStr, iae);
        }
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST leftCurly = findLeftCurly(ast);
        if (leftCurly != null) {
            if (option == BlockOption.STATEMENT) {
                final boolean emptyBlock;
                if (leftCurly.getType() == TokenTypes.LCURLY) {
                    emptyBlock = leftCurly.getNextSibling().getType() != TokenTypes.CASE_GROUP;
                }
                else {
                    emptyBlock = leftCurly.getChildCount() <= 1;
                }
                if (emptyBlock) {
                    log(leftCurly.getLineNo(),
                        leftCurly.getColumnNo(),
                        MSG_KEY_BLOCK_NO_STATEMENT,
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
     * Checks if SLIST token contains any text.
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
            if (!CommonUtils.isBlank(txt)) {
                returnValue = true;
            }
        }
        else {
            final String firstLine = lines[slistLineNo - 1].substring(slistColNo + 1);
            final String lastLine = lines[rcurlyLineNo - 1].substring(0, rcurlyColNo);
            if (CommonUtils.isBlank(firstLine)
                    && CommonUtils.isBlank(lastLine)) {
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
            if (!CommonUtils.isBlank(lines[i])) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Calculates the left curly corresponding to the block to be checked.
     *
     * @param ast a {@code DetailAST} value
     * @return the left curly corresponding to the block to be checked
     */
    private static DetailAST findLeftCurly(DetailAST ast) {
        final DetailAST leftCurly;
        final DetailAST slistAST = ast.findFirstToken(TokenTypes.SLIST);
        if ((ast.getType() == TokenTypes.LITERAL_CASE
                || ast.getType() == TokenTypes.LITERAL_DEFAULT)
                && ast.getNextSibling() != null
                && ast.getNextSibling().getFirstChild().getType() == TokenTypes.SLIST) {
            leftCurly = ast.getNextSibling().getFirstChild();
        }
        else if (slistAST == null) {
            leftCurly = ast.findFirstToken(TokenTypes.LCURLY);
        }
        else {
            leftCurly = slistAST;
        }
        return leftCurly;
    }
}
