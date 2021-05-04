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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks the alignment of leading asterisks in block comments and javadocs.
 * If asterisks is not aligned properly or
 * if there are consecutive asterisks then it will produce violation.
 * If no leading asterisk is used then that line will be ignored.
 * </p>
 * <p>By default the leading asterisk should be aligned under the forward slash ( &#47; ).</p>
 * <p>
 * For {@code Block comments} the check will work like this:
 * </p>
 * <p>
 * If {@code option} property is set to default ( RIGHT ) then the leading asterisk
 * should be aligned under the forward slash ( &#47; ) and if it is set to {@code left} then
 * the leading asterisk should be aligned under the asterisk ( &#42; )
 * </p>
 * <p>
 * For {@code Javadoc comments} the check will work like this:
 * </p>
 * <p>
 * If {@code option} property is set to default ( RIGHT ) then the leading asterisk
 * should be aligned under the forward slash ( &#47; ) and if it is set to {@code left} then
 * the leading asterisk should be aligned under the first asterisk ( &#42; ) but if the leading
 * is aligned under the second asterisk then the check will throw an error.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify the alignment direction of the asterisk.
 * Type is {@code java.lang.String}.
 * Default value is {@code "RIGHT"}.
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
 * {@code leading.asterisk.duplicate}
 * </li>
 * <li>
 * {@code leading.asterisk.indentation}
 * </li>
 * </ul>
 *
 * @since 10.15
 */
@FileStatefulCheck
public class LeadingAsteriskAlignCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRONG_ALIGNMENT = "leading.asterisk.indentation";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DUPLICATE_ASTERISK = "leading.asterisk.duplicate";

    /** RegExp to match against the line containing block comment end token. */
    private static final String BLOCK_COMMENT_END_REGEXP = "^(?=.*\\*/).*$";

    /** Column number of block comment begin token. */
    private int commentBeginColumn;

    /**
     * Prevents check from producing incorrect indentation for leading
     * asterisk violation if duplicate leading asterisks are present.
     */
    private boolean isDuplicateAsteriskPresent;

    /** List of block comment text lines. */
    private List<String> commentTextLineList;

    /** Specify the alignment direction of the asterisk. */
    private String option = LeadingAsteriskAlignOption.RIGHT.toString();

    /**
     * Setter to specify the alignment direction of the asterisk.
     *
     * @param alignment whether asterisk alignment should be left or right
     * @since 10.15
     */
    public void setOption(String alignment) {
        option = LeadingAsteriskAlignOption.valueOf(alignment.trim()
                                            .toUpperCase(Locale.ENGLISH)).toString();
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        commentBeginColumn = ast.getColumnNo();
        createCommentLineList(ast);

        if (commentTextLineList.size() > 1) {
            for (int lineNumber = 1; lineNumber < commentTextLineList.size(); lineNumber++) {

                final String commentEndLine = getLine(lineNumber + ast.getLineNo() - 1);

                if (commentEndLine.matches(BLOCK_COMMENT_END_REGEXP)) {
                    checkAsteriskAlignment(getColumnForCommentEnd(commentEndLine),
                            lineNumber + ast.getLineNo());
                }
                else {
                    final String commentTextLine = commentTextLineList.get(lineNumber);
                    checkAsteriskDuplicate(commentTextLine, lineNumber + ast.getLineNo());
                    checkAsteriskAlignment(getColumnForCommentText(commentTextLine),
                            lineNumber + ast.getLineNo());
                }
            }
            if (isCommentEndColumnZero(ast)) {
                checkAsteriskAlignment(0, ast.getFirstChild().getNextSibling().getLineNo());
            }
        }
    }

    /**
     * Create an ArrayList of lines of block comment text to be processed.
     *
     * @param ast abstract syntax tree of block comment begin token
     */
    private void createCommentLineList(DetailAST ast) {
        final String commentText = ast.getFirstChild().getText();
        commentTextLineList = Arrays.asList(commentText.split("\n"));
    }

    /**
     * Returns whether block comment end token is at column zero.
     *
     * @param ast abstract syntax tree of block comment end token
     * @return whether end comment token is at column zero
     */
    private static boolean isCommentEndColumnZero(DetailAST ast) {
        return ast.getFirstChild().getNextSibling().getColumnNo() == 0;
    }

    /**
     * Checks whether duplicate leading asterisk exists in line and logs violation.
     *
     * @param line string line to be processed
     * @param lineNumber line number of the line to be processed
     */
    private void checkAsteriskDuplicate(String line, int lineNumber) {
        final Pattern pattern = Pattern.compile("^\\*{2,}");
        final Matcher matcher = pattern.matcher(line.trim());
        final boolean isDoubleAsterisk = matcher.find();

        if (isDoubleAsterisk) {
            log(lineNumber, MSG_DUPLICATE_ASTERISK, lineNumber);
            isDuplicateAsteriskPresent = true;
        }
    }

    /**
     * Processes and returns the column number of asterisks from block comment text.
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
     * Processes and returns the column number of block comment end token.
     *
     * @param line line containing block comment end token
     * @return column number of comment end token
     */
    private static int getColumnForCommentEnd(String line) {
        int columnNumber = -1;
        int spaces = 0;
        final char[] charLine = line.toCharArray();

        int index = 0;
        while (index < charLine.length) {
            if (charLine[index] == ' ') {
                spaces++;
            }
            else if (charLine[index] == '*') {
                if (charLine[index + 1] == '/') {
                    columnNumber = spaces;
                    break;
                }
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
     * @param columnNumber column number of asterisk/comment end token
     * @param lineNumber line number of asterisk/comment end token
     */
    private void checkAsteriskAlignment(int columnNumber, int lineNumber) {
        if (isDuplicateAsteriskPresent) {
            isDuplicateAsteriskPresent = false;
        }
        else if (columnNumber != -1) {
            final int columnDifference = commentBeginColumn - columnNumber;

            if ("LEFT".equals(option)) {
                if (columnDifference != 0) {
                    log(lineNumber, MSG_WRONG_ALIGNMENT, commentBeginColumn);
                }
            }
            else {
                if (columnDifference != -1) {
                    log(lineNumber, MSG_WRONG_ALIGNMENT, commentBeginColumn + 1);
                }
            }
        }
    }
}
