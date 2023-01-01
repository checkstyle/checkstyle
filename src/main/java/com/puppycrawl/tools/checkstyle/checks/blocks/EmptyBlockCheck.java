///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Arrays;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks for empty blocks. This check does not validate sequential blocks.
 * </p>
 * <p>
 * Sequential blocks won't be checked. Also, no violations for fallthrough:
 * </p>
 * <pre>
 * switch (a) {
 *   case 1:                          // no violation
 *   case 2:                          // no violation
 *   case 3: someMethod(); { }        // no violation
 *   default: break;
 * }
 * </pre>
 * <p>
 * NOTE: This check processes LITERAL_CASE and LITERAL_DEFAULT separately.
 * Verification empty block is done for single nearest {@code case} or {@code default}.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - specify the policy on block contents.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.blocks.BlockOption}.
 * Default value is {@code statement}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">
 * LITERAL_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">
 * LITERAL_TRY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">
 * LITERAL_FINALLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">
 * LITERAL_DO</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">
 * LITERAL_ELSE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">
 * LITERAL_FOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">
 * LITERAL_SWITCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">
 * LITERAL_SYNCHRONIZED</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="EmptyBlock"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *   private void emptyLoop() {
 *     for (int i = 0; i &lt; 10; i++) { // violation
 *     }
 *
 *     try { // violation
 *
 *     } catch (Exception e) {
 *       // ignored
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check for the {@code text} policy and only {@code try} blocks:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyBlock&quot;&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;text&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_TRY&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> Example: </p>
 * <pre>
 * public class Test {
 *   private void emptyLoop() {
 *     for (int i = 0; i &lt; 10; i++) {
 *       // ignored
 *     }
 *
 *     // violation on next line
 *     try {
 *
 *     } catch (Exception e) {
 *       // ignored
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check for default in switch block:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyBlock&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_DEFAULT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> Example: </p>
 * <pre>
 * public class Test {
 *   private void test(int a) {
 *     switch (a) {
 *       case 1: someMethod();
 *       default: // OK, as there is no block
 *     }
 *     switch (a) {
 *       case 1: someMethod();
 *       default: {} // violation
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code block.empty}
 * </li>
 * <li>
 * {@code block.noStatement}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
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

    /** Specify the policy on block contents. */
    private BlockOption option = BlockOption.STATEMENT;

    /**
     * Setter to specify the policy on block contents.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        option = BlockOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
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
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST leftCurly = findLeftCurly(ast);
        if (leftCurly != null) {
            if (option == BlockOption.STATEMENT) {
                final boolean emptyBlock;
                if (leftCurly.getType() == TokenTypes.LCURLY) {
                    final DetailAST nextSibling = leftCurly.getNextSibling();
                    emptyBlock = nextSibling.getType() != TokenTypes.CASE_GROUP
                            && nextSibling.getType() != TokenTypes.SWITCH_RULE;
                }
                else {
                    emptyBlock = leftCurly.getChildCount() <= 1;
                }
                if (emptyBlock) {
                    log(leftCurly,
                        MSG_KEY_BLOCK_NO_STATEMENT,
                        ast.getText());
                }
            }
            else if (!hasText(leftCurly)) {
                log(leftCurly,
                    MSG_KEY_BLOCK_EMPTY,
                    ast.getText());
            }
        }
    }

    /**
     * Checks if SLIST token contains any text.
     *
     * @param slistAST a {@code DetailAST} value
     * @return whether the SLIST token contains any text.
     */
    private boolean hasText(final DetailAST slistAST) {
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
        boolean returnValue = false;
        if (slistLineNo == rcurlyLineNo) {
            // Handle braces on the same line
            final int[] txt = Arrays.copyOfRange(getLineCodePoints(slistLineNo - 1),
                    slistColNo + 1, rcurlyColNo);

            if (!CodePointUtil.isBlank(txt)) {
                returnValue = true;
            }
        }
        else {
            final int[] codePointsFirstLine = getLineCodePoints(slistLineNo - 1);
            final int[] firstLine = Arrays.copyOfRange(codePointsFirstLine,
                    slistColNo + 1, codePointsFirstLine.length);
            final int[] codePointsLastLine = getLineCodePoints(rcurlyLineNo - 1);
            final int[] lastLine = Arrays.copyOfRange(codePointsLastLine, 0, rcurlyColNo);
            // check if all lines are also only whitespace
            returnValue = !(CodePointUtil.isBlank(firstLine) && CodePointUtil.isBlank(lastLine))
                    || !checkIsAllLinesAreWhitespace(slistLineNo, rcurlyLineNo);
        }
        return returnValue;
    }

    /**
     * Checks is all lines from 'lineFrom' to 'lineTo' (exclusive)
     * contain whitespaces only.
     *
     * @param lineFrom
     *            check from this line number
     * @param lineTo
     *            check to this line numbers
     * @return true if lines contain only whitespaces
     */
    private boolean checkIsAllLinesAreWhitespace(int lineFrom, int lineTo) {
        boolean result = true;
        for (int i = lineFrom; i < lineTo - 1; i++) {
            if (!CodePointUtil.isBlank(getLineCodePoints(i))) {
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
                && ast.getNextSibling().getFirstChild() != null
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
