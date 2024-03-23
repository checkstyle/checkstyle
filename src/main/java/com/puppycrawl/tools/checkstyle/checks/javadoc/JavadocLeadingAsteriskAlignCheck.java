///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that the
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisks</a> are aligned vertically under the second asterisk ( &#42; )
 * of opening Javadoc tag. The alignment of closing Javadoc tag is also checked.
 * It also checks for consecutive leading asterisks in the Javadoc comment.
 * If the line does not contain leading asterisk then that line will be ignored.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.asterisk.duplicate}
 * </li>
 * <li>
 * {@code javadoc.asterisk.indentation}
 * </li>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 10.15
 */
@FileStatefulCheck
public class JavadocLeadingAsteriskAlignCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRONG_ALIGNMENT = "javadoc.asterisk.indentation";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DUPLICATE_ASTERISK = "javadoc.asterisk.duplicate";

    /** Specifies if the javadoc is of one line. */
    private boolean isOneLineJavadoc;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
                JavadocTokenTypes.JAVADOC,
                JavadocTokenTypes.LEADING_ASTERISK,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.JAVADOC) {
            final DetailAST javadocEndToken = getBlockCommentAst().getLastChild();
            final String endTokenText = getLastLine(javadocEndToken);

            if (!isOneLineJavadoc) {
                final int commentIndex = getColumnForCommentText(endTokenText);

                checkDuplicateAsterisk(endTokenText, javadocEndToken.getLineNo());
                checkAsteriskAlignment(
                        javadocEndToken.getLineNo(),
                        ast.getColumnNumber() - 2,
                        commentIndex);
            }
        }
        else {
            DetailNode parent = ast.getParent();
            while (parent.getType() != JavadocTokenTypes.JAVADOC) {
                parent = parent.getParent();
            }

            final int javadocStartTokenColumn = parent.getColumnNumber() - 2;
            final int commentIndex = getColumnForCommentText(ast.getText());
            final String commentText = "*".concat(JavadocUtil.getNextSibling(ast).getText());

            checkDuplicateAsterisk(commentText, ast.getLineNumber());
            checkAsteriskAlignment(
                    ast.getLineNumber(),
                    javadocStartTokenColumn,
                    commentIndex);
        }
        isOneLineJavadoc = false;
    }

    /**
     * Create an ArrayList of lines of block comment text to be processed.
     *
     * @param ast javadoc end token
     * @return last line of javadoc comment
     */
    private String getLastLine(DetailAST ast) {
        String lastLine;

        if (ast.getColumnNo() == 0) {
            lastLine = ast.getText();
        }
        else {
            final String blockComment = JavadocUtil.getBlockCommentContent(getBlockCommentAst());
            final List<String> commentTextLineList = Arrays.asList(blockComment.split("\n"));

            if (commentTextLineList.size() <= 1) {
                isOneLineJavadoc = true;
            }
            lastLine = commentTextLineList.get(commentTextLineList.size() - 1).concat("*/");;
        }

        return lastLine;
    }

    /**
     * Checks whether duplicate leading asterisk exists in line and logs violation.
     *
     * @param line string line to be processed
     * @param lineNumber line number of the line to be processed
     */
    private void checkDuplicateAsterisk(String line, int lineNumber) {
        final Pattern doubleAsterisks = Pattern.compile("^\\*{2,}");
        final Matcher matcher = doubleAsterisks.matcher(line.trim());

        if (matcher.find()) {
            log(lineNumber, MSG_DUPLICATE_ASTERISK, lineNumber);
        }
    }

    /**
     * Processes and returns the column number of leading asterisk from javadoc comment text.
     *
     * @param line single line from block comment text
     * @return column number of asterisk
     */
    private static int getColumnForCommentText(String line) {
        int columnNumber = -1;
        int spaces = 0;
        final char[] charLine = line.toCharArray();

        int index = 0;
        while (index < charLine.length) {
            final char currChar = charLine[index];
            if (currChar == ' ') {
                spaces++;
            }
            else if (currChar == '*') {
                columnNumber = spaces;
                break;
            }
            else {
                index = charLine.length;
            }
            index++;
        }
        return columnNumber;
    }

    /**
     * Checks alignment of asterisks and logs violations.
     *
     * @param lineNumber line number of current comment line
     * @param commentBeginColumn column number of javadoc starting token
     * @param asteriskColumn column number of current line's leading asterisk
     */
    private void checkAsteriskAlignment(int lineNumber,
                                        int commentBeginColumn,
                                        int asteriskColumn) {

        final int columnDifference = commentBeginColumn - asteriskColumn;

        if (asteriskColumn != -1) {
            if (columnDifference != 0) {
                log(lineNumber, MSG_WRONG_ALIGNMENT, commentBeginColumn);
            }
        }
    }
}
