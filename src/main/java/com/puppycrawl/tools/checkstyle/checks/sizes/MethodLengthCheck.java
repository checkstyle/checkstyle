////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks for long methods and constructors.
 * </p>
 * <p>
 * Rationale: If a method becomes very long it is hard to understand.
 * Therefore long methods should usually be refactored into several
 * individual methods that focus on a specific task.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of lines allowed.
 * Type is {@code int}.
 * Default value is {@code 150}.
 * </li>
 * <li>
 * Property {@code countEmpty} - Control whether to count empty lines and single
 * line comments of the form {@code //}.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"/&gt;
 * </pre>
 * <p>
 * To configure the check so that it accepts methods with at most 60 lines:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *   &lt;property name="max" value="60"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check so that it accepts methods with at most 60 lines,
 * not counting empty lines and single line comments:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *   &lt;property name="max" value="60"/&gt;
 *   &lt;property name="countEmpty" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code maxLen.method}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class MethodLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.method";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX_LINES = 150;

    /** Control whether to count empty lines and single line comments of the form {@code //}. */
    private boolean countEmpty = true;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX_LINES;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                openingBrace.findFirstToken(TokenTypes.RCURLY);
            final int length = getLengthOfBlock(openingBrace, closingBrace);
            if (isExceedingLimit(length)) {
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast, MSG_KEY, length, max, methodName);
            }
        }
    }

    /**
     * Returns length of code only without comments and blank lines.
     *
     * @param openingBrace block opening brace
     * @param closingBrace block closing brace
     * @return number of lines with code for current block
     */
    private int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        final int startLineNo = openingBrace.getLineNo();
        final int endLineNo = closingBrace.getLineNo();
        int length = endLineNo - startLineNo + 1;

        // Pitest does not like check for exceeding limit below,
        // but is a shortcut to avoid iterating over whole ast,
        // because its result cant be greater than "raw" block length
        if (!countEmpty
                && isExceedingLimit(length)) {
            length = countUsedLines(openingBrace.getFirstChild(), startLineNo, endLineNo);
        }
        return length;
    }

    /**
     * Checks that length is strictly greater than limit.
     *
     * @param length length
     * @return true if given length is strictly greater than limit
     */
    private boolean isExceedingLimit(int length) {
        return length > max;
    }

    /**
     * Collects numbers of lines where code present into given set.
     *
     * @param ast start ast
     * @param startLine start line number
     * @param endLine end line number
     * @return number of used lines of code
     */
    private static int countUsedLines(DetailAST ast, int startLine, int endLine) {
        final boolean[] usedLines = new boolean[endLine - startLine + 1];
        usedLines[0] = true;
        final Deque<DetailAST> nodes = new ArrayDeque<>();
        Stream.iterate(ast, Objects::nonNull, DetailAST::getNextSibling).forEach(nodes::addLast);
        while (!nodes.isEmpty()) {
            final DetailAST node = nodes.removeFirst();
            final int offset = node.getLineNo() - startLine;
            // text block content requires special treatment,
            // since it is the only non-comment token that can span more than one line
            if (node.getType() == TokenTypes.TEXT_BLOCK_CONTENT) {
                final int contentLinesCount = (int) node.getText().lines().count();
                // count only lines inside, exclude first and last lines
                IntStream.range(1, contentLinesCount)
                    .map(idx -> offset + idx)
                    .forEach(idx -> usedLines[idx] = true);
            }
            else {
                usedLines[offset] = true;
            }
            Stream.iterate(node.getLastChild(), Objects::nonNull, DetailAST::getPreviousSibling)
                .forEach(nodes::addFirst);
        }
        int result = 0;
        for (boolean usedLine : usedLines) {
            if (usedLine) {
                result++;
            }
        }
        return result;
    }

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of a method.
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * Setter to control whether to count empty lines and single line comments
     * of the form {@code //}.
     *
     * @param countEmpty whether to count empty and single line comments
     *     of the form //.
     */
    public void setCountEmpty(boolean countEmpty) {
        this.countEmpty = countEmpty;
    }

}
