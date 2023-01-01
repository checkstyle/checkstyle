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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that non-whitespace characters are separated by no more than one
 * whitespace. Separating characters by tabs or multiple spaces will be
 * reported. Currently, the check doesn't permit horizontal alignment. To inspect
 * whitespaces before and after comments, set the property
 * {@code validateComments} to true.
 * </p>
 *
 * <p>
 * Setting {@code validateComments} to false will ignore cases like:
 * </p>
 *
 * <pre>
 * int i;  &#47;&#47; Multiple whitespaces before comment tokens will be ignored.
 * private void foo(int  &#47;* whitespaces before and after block-comments will be
 * ignored *&#47;  i) {
 * </pre>
 *
 * <p>
 * Sometimes, users like to space similar items on different lines to the same
 * column position for easier reading. This feature isn't supported by this
 * check, so both braces in the following case will be reported as violations.
 * </p>
 *
 * <pre>
 * public long toNanos(long d)  { return d;             } &#47;&#47; 2 violations
 * public long toMicros(long d) { return d / (C1 / C0); }
 * </pre>
 * <ul>
 * <li>
 * Property {@code validateComments} - Control whether to validate whitespaces
 * surrounding comments.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;SingleSpaceSeparator&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * int foo()   { // violation, 3 whitespaces
 *   return  1; // violation, 2 whitespaces
 * }
 * int fun1() { // OK, 1 whitespace
 *   return 3; // OK, 1 whitespace
 * }
 * void  fun2() {} // violation, 2 whitespaces
 * </pre>
 *
 * <p>
 * To configure the check so that it validates comments:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;SingleSpaceSeparator&quot;&gt;
 *   &lt;property name=&quot;validateComments&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * void fun1() {}  // violation, 2 whitespaces before the comment starts
 * void fun2() { return; }  /* violation here, 2 whitespaces before the comment starts *&#47;
 *
 * /* violation, 2 whitespaces after the comment ends *&#47;  int a;
 *
 * String s; /* OK, 1 whitespace *&#47;
 *
 * /**
 * * This is a Javadoc comment
 * *&#47;  int b; // violation, 2 whitespaces after the javadoc comment ends
 *
 * float f1; // OK, 1 whitespace
 *
 * /**
 * * OK, 1 white space after the doc comment ends
 * *&#47; float f2;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code single.space.separator}
 * </li>
 * </ul>
 *
 * @since 6.19
 */
@StatelessCheck
public class SingleSpaceSeparatorCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "single.space.separator";

    /** Control whether to validate whitespaces surrounding comments. */
    private boolean validateComments;

    /**
     * Setter to control whether to validate whitespaces surrounding comments.
     *
     * @param validateComments {@code true} to validate surrounding whitespaces at comments.
     */
    public void setValidateComments(boolean validateComments) {
        this.validateComments = validateComments;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return validateComments;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        if (rootAST != null) {
            visitEachToken(rootAST);
        }
    }

    /**
     * Examines every sibling and child of {@code node} for violations.
     *
     * @param node The node to start examining.
     */
    private void visitEachToken(DetailAST node) {
        DetailAST currentNode = node;
        final DetailAST parent = node.getParent();

        do {
            final int columnNo = currentNode.getColumnNo() - 1;

            // in such expression: "j  =123", placed at the start of the string index of the second
            // space character will be: 2 = 0(j) + 1(whitespace) + 1(whitespace). It is a minimal
            // possible index for the second whitespace between non-whitespace characters.
            final int minSecondWhitespaceColumnNo = 2;

            if (columnNo >= minSecondWhitespaceColumnNo
                    && !isTextSeparatedCorrectlyFromPrevious(
                            getLineCodePoints(currentNode.getLineNo() - 1),
                            columnNo)) {
                log(currentNode, MSG_KEY);
            }
            if (currentNode.hasChildren()) {
                currentNode = currentNode.getFirstChild();
            }
            else {
                while (currentNode.getNextSibling() == null && currentNode.getParent() != parent) {
                    currentNode = currentNode.getParent();
                }
                currentNode = currentNode.getNextSibling();
            }
        } while (currentNode != null);
    }

    /**
     * Checks if characters in {@code line} at and around {@code columnNo} has
     * the correct number of spaces. to return {@code true} the following
     * conditions must be met:
     * <ul>
     * <li> the character at {@code columnNo} is the first in the line. </li>
     * <li> the character at {@code columnNo} is not separated by whitespaces from
     * the previous non-whitespace character. </li>
     * <li> the character at {@code columnNo} is separated by only one whitespace
     * from the previous non-whitespace character. </li>
     * <li> {@link #validateComments} is disabled and the previous text is the
     * end of a block comment. </li>
     * </ul>
     *
     * @param line Unicode code point array of line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the text at {@code columnNo} is separated
     *         correctly from the previous token.
     */
    private boolean isTextSeparatedCorrectlyFromPrevious(int[] line, int columnNo) {
        return isSingleSpace(line, columnNo)
                || !CommonUtil.isCodePointWhitespace(line, columnNo)
                || isFirstInLine(line, columnNo)
                || !validateComments && isBlockCommentEnd(line, columnNo);
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is a single space, and not
     * preceded by another space.
     *
     * @param line Unicode code point array of line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the character at {@code columnNo} is a space, and
     *         not preceded by another space.
     */
    private static boolean isSingleSpace(int[] line, int columnNo) {
        return isSpace(line, columnNo) && !CommonUtil.isCodePointWhitespace(line, columnNo - 1);
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is a space.
     *
     * @param line Unicode code point array of line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the character at {@code columnNo} is a space.
     */
    private static boolean isSpace(int[] line, int columnNo) {
        return line[columnNo] == ' ';
    }

    /**
     * Checks if the {@code line} up to and including {@code columnNo} is all
     * non-whitespace text encountered.
     *
     * @param line Unicode code point array of line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the column position is the first non-whitespace
     *         text on the {@code line}.
     */
    private static boolean isFirstInLine(int[] line, int columnNo) {
        return CodePointUtil.isBlank(Arrays.copyOfRange(line, 0, columnNo));
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is the end of a comment,
     * '*&#47;'.
     *
     * @param line Unicode code point array of line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the previous text is an end comment block.
     */
    private static boolean isBlockCommentEnd(int[] line, int columnNo) {
        final int[] strippedLine = CodePointUtil
                .stripTrailing(Arrays.copyOfRange(line, 0, columnNo));
        return CodePointUtil.endsWith(strippedLine, "*/");
    }

}
