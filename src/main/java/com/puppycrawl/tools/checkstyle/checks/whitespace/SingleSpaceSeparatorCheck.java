////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that non-whitespace characters are separated by no more than one
 * whitespace. Separating characters by tabs or multiple spaces will be
 * reported. Currently the check doesn't permit horizontal alignment. To inspect
 * whitespaces before and after comments, set the property
 * {@link #validateComments} to true.
 * </p>
 *
 * <p>
 * Setting {@link #validateComments} to false will ignore cases like:
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
 * public long toNanos(long d)  { return d;             }  &#47;&#47; 2 violations
 * public long toMicros(long d) { return d / (C1 / C0); }
 * </pre>
 *
 * <p>
 * Check have following options:
 * </p>
 *
 * <ul>
 * <li>validateComments - Boolean when set to {@code true}, whitespaces
 * surrounding comments will be ignored. Default value is {@code false}.</li>
 * </ul>
 *
 * <p>
 * To configure the check:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;SingleSpaceSeparator&quot;/&gt;
 * </pre>
 *
 * <p>
 * To configure the check so that it validates comments:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;SingleSpaceSeparator&quot;&gt;
 * &lt;property name=&quot;validateComments&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Robert Whitebit
 * @author Richard Veach
 */
public class SingleSpaceSeparatorCheck extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "single.space.separator";

    /** Indicates if whitespaces surrounding comments will be ignored. */
    private boolean validateComments;

    /**
     * Sets whether or not to validate surrounding whitespaces at comments.
     *
     * @param validateComments {@code true} to validate surrounding whitespaces at comments.
     */
    public void setValidateComments(boolean validateComments) {
        this.validateComments = validateComments;
    }

    @Override
    public int[] getDefaultTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return validateComments;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        visitEachToken(rootAST);
    }

    /**
     * Examines every sibling and child of {@code node} for violations.
     *
     * @param node The node to start examining.
     */
    private void visitEachToken(DetailAST node) {
        DetailAST sibling = node;

        while (sibling != null) {
            final int columnNo = sibling.getColumnNo() - 1;

            if (columnNo >= 0
                    && !isTextSeparatedCorrectlyFromPrevious(getLine(sibling.getLineNo() - 1),
                            columnNo)) {
                log(sibling.getLineNo(), columnNo, MSG_KEY);
            }
            if (sibling.getChildCount() > 0) {
                visitEachToken(sibling.getFirstChild());
            }

            sibling = sibling.getNextSibling();
        }
    }

    /**
     * Checks if characters in {@code line} at and around {@code columnNo} has
     * the correct number of spaces. to return {@code true} the following
     * conditions must be met:<br />
     * - the character at {@code columnNo} is the first in the line.<br />
     * - the character at {@code columnNo} is not separated by whitespaces from
     * the previous non-whitespace character. <br />
     * - the character at {@code columnNo} is separated by only one whitespace
     * from the previous non-whitespace character.<br />
     * - {@link #validateComments} is disabled and the previous text is the
     * end of a block comment.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the text at {@code columnNo} is separated
     *         correctly from the previous token.
     */
    private boolean isTextSeparatedCorrectlyFromPrevious(String line, int columnNo) {
        return isSingleSpace(line, columnNo)
                || !isWhitespace(line, columnNo)
                || isFirstInLine(line, columnNo)
                || !validateComments && isBlockCommentEnd(line, columnNo);
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is a single space, and not
     * preceded by another space.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the character at {@code columnNo} is a space, and
     *         not preceded by another space.
     */
    private static boolean isSingleSpace(String line, int columnNo) {
        return !isPrecededByMultipleWhitespaces(line, columnNo)
                && isSpace(line, columnNo);
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is a space.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the character at {@code columnNo} is a space.
     */
    private static boolean isSpace(String line, int columnNo) {
        return line.charAt(columnNo) == ' ';
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is preceded by at least 2
     * whitespaces.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if there are at least 2 whitespace characters before
     *         {@code columnNo}.
     */
    private static boolean isPrecededByMultipleWhitespaces(String line, int columnNo) {
        return columnNo >= 1
                && Character.isWhitespace(line.charAt(columnNo))
                && Character.isWhitespace(line.charAt(columnNo - 1));
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is a whitespace character.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the character at {@code columnNo} is a
     *         whitespace.
     */
    private static boolean isWhitespace(String line, int columnNo) {
        return Character.isWhitespace(line.charAt(columnNo));
    }

    /**
     * Checks if the {@code line} up to and including {@code columnNo} is all
     * non-whitespace text encountered.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the column position is the first non-whitespace
     *         text on the {@code line}.
     */
    private static boolean isFirstInLine(String line, int columnNo) {
        return line.substring(0, columnNo + 1).trim().isEmpty();
    }

    /**
     * Checks if the {@code line} at {@code columnNo} is the end of a comment,
     * '*&#47;'.
     *
     * @param line The line in the file to examine.
     * @param columnNo The column position in the {@code line} to examine.
     * @return {@code true} if the previous text is a end comment block.
     */
    private static boolean isBlockCommentEnd(String line, int columnNo) {
        return line.substring(0, columnNo).trim().endsWith("*/");
    }
}
