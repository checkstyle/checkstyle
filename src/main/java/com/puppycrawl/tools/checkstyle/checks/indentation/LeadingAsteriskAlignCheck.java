////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
    private static final String BLOCK_COMMENT_END_REGEXP = "\\s.*(\\*/).*";

    /** Column number of block comment begin token. */
    private int commentBeginColumn;

    /** List of block comment text lines */
    private List<String> commentTextLineList;

    /** Specify alignment option for leading asterisk. */
    private LeadingAsteriskAlignOption option = LeadingAsteriskAlignOption.RIGHT;

    /** Specify tab indentation size for file to be checked. */
    private int tabSize = 4;

    /**
     * Setter to specify alignment option for leading asterisk.
     *
     * @param alignment whether asterisk alignment should be left or right
     */
    public void setOption(String alignment) {
        option = LeadingAsteriskAlignOption.valueOf(alignment.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Setter to specify tab indentation size for file to be checked.
     *
     * @param size tab character indentation size
     */
    public void setTabSize(String size) {
        if (Integer.parseInt(size) > 0) {
            tabSize = Integer.parseInt(size);
        }
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
        String commentBeginLine = getLine(ast.getLineNo() - 1);
        commentBeginColumn = getColumnForCommentBegin(commentBeginLine);
        createCommentLineList(ast);

        if (commentTextLineList.size() > 1) {
            for (int lineNumber = 1; lineNumber < commentTextLineList.size(); lineNumber++) {

                String commentTextLine = commentTextLineList.get(lineNumber);
                String commentEndLine = getLine(lineNumber + ast.getLineNo() - 1);

                if (commentEndLine.matches(BLOCK_COMMENT_END_REGEXP)) {
                    checkAsteriskAlignment(getColumnForCommentEnd(commentEndLine),
                            lineNumber + ast.getLineNo());
                }
                else {
                    checkAsteriskAlignment(getColumnForCommentText(commentTextLine),
                            lineNumber + ast.getLineNo());
                    checkAsteriskDuplicate(commentTextLine, lineNumber + ast.getLineNo());
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
        String commentText = ast.getFirstChild().getText();

        if (commentText.isEmpty()) {
            commentTextLineList = null;
        }
        else {
            commentTextLineList = Arrays.asList(commentText.split("\n"));
        }
    }

    /**
     * Returns whether block comment end token is at column zero.
     *
     * @param ast abstract syntax tree of block comment end token
     * @return whether end comment token is at column zero
     */
    private boolean isCommentEndColumnZero(DetailAST ast) {
        return ast.getFirstChild().getNextSibling().getColumnNo() == 0;
    }

    /**
     * Checks whether duplicate leading asterisk exists in line and logs violation.
     *
     * @param line string line to be processed
     * @param lineNumber line number of the line to be processed
     */
    private void checkAsteriskDuplicate(String line, int lineNumber) {
        char[] charLine = line.toCharArray();

        if (charLine.length > commentBeginColumn + 1) {
            boolean leftAsteriskPresent = charLine[commentBeginColumn] == '*';
            boolean rightAsteriskPresent = charLine[commentBeginColumn + 1] == '*';

            if (leftAsteriskPresent && rightAsteriskPresent) {
                log(lineNumber, MSG_DUPLICATE_ASTERISK);
            }
        }
    }

    /**
     * Processes and returns the column number of block comment begin token.
     *
     * @param line line containing block comment begin token
     * @return column number of comment begin token
     */
    private int getColumnForCommentBegin(String line) {
        int columnNumber = -1;
        int spaces = 0;
        char[] charLine = line.toCharArray();

        for (int i = 0; i < charLine.length; i++) {
            switch (charLine[i]) {
                case ' ':
                    spaces++;
                    break;

                case '\t':
                    spaces += tabSize;
                    break;

                case '/':
                    if (charLine[i + 1] == '*') {
                        columnNumber = spaces;
                        return columnNumber;
                    }

                default:
                    spaces++;
            }
        }
        return columnNumber;
    }

    /**
     * Processes and returns the column number of asterisks from block comment text.
     *
     * @param line single line from block comment text
     * @return column number of asterisk
     */
    private int getColumnForCommentText(String line) {
        int columnNumber = -1;
        int spaces = 0;
        char[] charLine = line.toCharArray();

        for (char character : charLine) {
            switch (character) {
                case ' ':
                    spaces++;
                    break;

                case '\t':
                    spaces += tabSize;
                    break;

                case '*':
                    columnNumber = spaces;
                    return columnNumber;

                default:
                    return columnNumber;
            }
        }
        return columnNumber;
    }

    /**
     * Processes and returns the column number of block comment end token.
     *
     * @param line line containing block comment end token
     * @return column number of comment end token
     */
    private int getColumnForCommentEnd(String line) {
        int columnNumber = -1;
        int spaces = 0;
        char[] charLine = line.toCharArray();

        for (int i = 0; i < charLine.length; i++) {
            switch (charLine[i]) {
                case ' ':
                    spaces++;
                    break;

                case '\t':
                    spaces += tabSize;
                    break;

                case '*':
                    if (charLine[i + 1] == '/') {
                        columnNumber = spaces;
                        return columnNumber;
                    }

                default:
                    return columnNumber;
            }
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
        if (columnNumber == -1) {
            return;
        }
        int columnDifference = commentBeginColumn - columnNumber;

        switch (option) {
            case LEFT:
                if (columnDifference != 0) {
                    log(lineNumber, MSG_WRONG_ALIGNMENT, commentBeginColumn);
                }
                break;
            case RIGHT:
                if (columnDifference != -1) {
                    log(lineNumber, MSG_WRONG_ALIGNMENT, commentBeginColumn + 1);
                }
                break;
        }
    }
}
