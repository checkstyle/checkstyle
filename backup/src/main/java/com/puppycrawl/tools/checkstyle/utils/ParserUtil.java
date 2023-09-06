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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.AbstractMap;
import java.util.Map;

import org.antlr.v4.runtime.CommonToken;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for parser to use while creating ast.
 */
public final class ParserUtil {

    /** Symbols with which javadoc starts. */
    private static final String JAVADOC_START = "/**";
    /** Symbols with which multiple comment starts. */
    private static final String BLOCK_MULTIPLE_COMMENT_BEGIN = "/*";
    /** Symbols with which multiple comment ends. */
    private static final String BLOCK_MULTIPLE_COMMENT_END = "*/";

    /** Stop instances being created. **/
    private ParserUtil() {
    }

    /**
     * Create block comment from string content.
     *
     * @param content comment content.
     * @return DetailAST block comment
     */
    public static DetailAST createBlockCommentNode(String content) {
        final DetailAstImpl blockCommentBegin = new DetailAstImpl();
        blockCommentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        blockCommentBegin.setText(BLOCK_MULTIPLE_COMMENT_BEGIN);
        blockCommentBegin.setLineNo(0);
        blockCommentBegin.setColumnNo(-JAVADOC_START.length());

        final DetailAstImpl commentContent = new DetailAstImpl();
        commentContent.setType(TokenTypes.COMMENT_CONTENT);
        commentContent.setText("*" + content);
        commentContent.setLineNo(0);
        // javadoc should starts at 0 column, so COMMENT_CONTENT node
        // that contains javadoc identifier has -1 column
        commentContent.setColumnNo(-1);

        final DetailAstImpl blockCommentEnd = new DetailAstImpl();
        blockCommentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        blockCommentEnd.setText(BLOCK_MULTIPLE_COMMENT_END);

        blockCommentBegin.setFirstChild(commentContent);
        commentContent.setNextSibling(blockCommentEnd);
        return blockCommentBegin;
    }

    /**
     * Create block comment from token.
     *
     * @param token Token object.
     * @return DetailAST with BLOCK_COMMENT type.
     */
    public static DetailAST createBlockCommentNode(CommonToken token) {
        final DetailAstImpl blockComment = new DetailAstImpl();
        blockComment.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, BLOCK_MULTIPLE_COMMENT_BEGIN);

        final int tokenCharPositionInLine = token.getCharPositionInLine();
        final int tokenLine = token.getLine();
        final String tokenText = token.getText();

        blockComment.setColumnNo(tokenCharPositionInLine);
        blockComment.setLineNo(tokenLine);

        final DetailAstImpl blockCommentContent = new DetailAstImpl();
        blockCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // Add length of '/*'
        blockCommentContent.setColumnNo(tokenCharPositionInLine + 2);
        blockCommentContent.setLineNo(tokenLine);
        blockCommentContent.setText(tokenText);

        final DetailAstImpl blockCommentClose = new DetailAstImpl();
        blockCommentClose.initialize(TokenTypes.BLOCK_COMMENT_END, BLOCK_MULTIPLE_COMMENT_END);

        final Map.Entry<Integer, Integer> linesColumns = countLinesColumns(
                tokenText, tokenLine, tokenCharPositionInLine + 1);
        blockCommentClose.setLineNo(linesColumns.getKey());
        blockCommentClose.setColumnNo(linesColumns.getValue());

        blockComment.addChild(blockCommentContent);
        blockComment.addChild(blockCommentClose);
        return blockComment;
    }

    /**
     * Count lines and columns (in last line) in text.
     *
     * @param text              String.
     * @param initialLinesCnt   initial value of lines counter.
     * @param initialColumnsCnt initial value of columns counter.
     * @return entry(pair), key is line counter, value is column counter.
     */
    private static Map.Entry<Integer, Integer> countLinesColumns(
        String text, int initialLinesCnt, int initialColumnsCnt) {
        int lines = initialLinesCnt;
        int columns = initialColumnsCnt;
        boolean foundCr = false;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                foundCr = false;
                lines++;
                columns = 0;
            }
            else {
                if (foundCr) {
                    foundCr = false;
                    lines++;
                    columns = 0;
                }
                if (c == '\r') {
                    foundCr = true;
                }
                columns++;
            }
        }
        if (foundCr) {
            lines++;
            columns = 0;
        }
        return new AbstractMap.SimpleEntry<>(lines, columns);
    }
}
