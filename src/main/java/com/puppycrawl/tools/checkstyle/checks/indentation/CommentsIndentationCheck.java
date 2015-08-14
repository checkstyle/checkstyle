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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This Check controls the indentation between comments and surrounding code.
 * Comments are indented at the same level as the surrounding code.
 * Detailed info about such convention can be found
 * <a href=
 * "http://checkstyle.sourceforge.net/reports/google-java-style.html#s4.8.6.1-block-comment-style">
 * here</a>
 * <p>
 * Examples:
 * </p>
 * <p>
 * To configure the Check:
 * </p>
 *
 * <pre>
 * {@code
 * &lt;module name=&quot;CommentsIndentation&quot;/module&gt;
 * }
 * {@code
 * /*
 *  * comment
 *  * some comment
 *  *&#47;
 * boolean bool = true; - such comment indentation is ok
 *    /*
 *    * comment
 *    * some comment
 *     *&#47;
 * double d = 3.14; - Block Comment has incorrect indentation level 7, expected 4.
 * // some comment - comment is ok
 * String str = "";
 *     // some comment Comment has incorrect indentation level 8, expected 4.
 * String str1 = "";
 * }
 * </pre>
 *
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 * @author <a href="mailto:andreyselkin@gmail.com">Andrei Selkin</a>
 *
 */
public class CommentsIndentationCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SINGLE = "comments.indentation.single";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK = "comments.indentation.block";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST commentAst) {
        switch (commentAst.getType()) {
            case TokenTypes.SINGLE_LINE_COMMENT:
                visitSingleLineComment(commentAst);
                break;
            case TokenTypes.BLOCK_COMMENT_BEGIN:
                visitBlockComment(commentAst);
                break;
            default:
                final String exceptionMsg = "Unexpected token type: " + commentAst.getText();
                throw new IllegalArgumentException(exceptionMsg);
        }
    }

    /**
     * Checks single line comment indentations over surrounding code, e.g.:
     * <p>
     * {@code
     * // some comment - this is ok
     * double d = 3.14;
     *     // some comment - this is <b>not</b> ok.
     * double d1 = 5.0;
     * }
     * </p>
     * @param singleLineComment {@link TokenTypes#SINGLE_LINE_COMMENT single line comment}.
     */
    private void visitSingleLineComment(DetailAST singleLineComment) {
        final DetailAST nextStatement = singleLineComment.getNextSibling();
        final DetailAST prevStatement = getPreviousStmt(singleLineComment);

        if (nextStatement != null
            && nextStatement.getType() != TokenTypes.RCURLY
            && !isTrailingSingleLineComment(singleLineComment)
            && !areSameLevelIndented(singleLineComment, prevStatement, nextStatement)) {

            log(singleLineComment.getLineNo(), MSG_KEY_SINGLE, nextStatement.getLineNo(),
                singleLineComment.getColumnNo(), nextStatement.getColumnNo());
        }
    }

    /**
     * Gets previous case block from switch block.
     * @param comment {@link TokenTypes#SINGLE_LINE_COMMENT single-line comment}.
     * @return previous case block from switch.
     */
    private static DetailAST getPreviousStmt(DetailAST comment) {
        final DetailAST parentStatement = comment.getParent();
        DetailAST prevStmt = null;
        if (parentStatement != null) {
            DetailAST prevBlock;
            if (parentStatement.getType() == TokenTypes.CASE_GROUP) {
                prevBlock = parentStatement.getPreviousSibling();
                if (prevBlock.getLastChild() != null) {
                    DetailAST blockBody = prevBlock.getLastChild().getLastChild();
                    if (blockBody.getPreviousSibling() != null) {
                        blockBody = blockBody.getPreviousSibling();
                    }
                    if (blockBody.getType() == TokenTypes.EXPR) {
                        prevStmt = blockBody.getFirstChild().getFirstChild();
                    }
                    else {
                        prevStmt = blockBody;

                    }
                }
            }
            else {
                final DetailAST parentBlock = parentStatement.getParent();

                if (parentBlock != null && parentBlock.getParent() != null
                    && parentBlock.getParent().getPreviousSibling() != null
                    && parentBlock.getParent().getPreviousSibling()
                        .getType() == TokenTypes.LITERAL_CASE) {

                    prevBlock = parentBlock.getParent().getPreviousSibling();
                    prevStmt = prevBlock;
                }
            }
        }
        return prevStmt;
    }

    /**
     * Checks if comment and next code statement
     * (or previous code stmt like <b>case</b> in switch block) are indented at the same level,
     * e.g.:
     * <p>
     * <pre>
     * {@code
     * // some comment - same indentation level
     * int x = 10;
     *     // some comment - different indentation level
     * int x1 = 5;
     * /*
     *  *
     *  *&#47;
     *  boolean bool = true; - same indentation level
     * }
     * </pre>
     * </p>
     * @param singleLineComment {@link TokenTypes#SINGLE_LINE_COMMENT single line comment}.
     * @param prevStmt previous code statement.
     * @param nextStmt next code statement.
     * @return true if comment and next code statement are indented at the same level.
     */
    private static boolean areSameLevelIndented(DetailAST singleLineComment,
                                                DetailAST prevStmt, DetailAST nextStmt) {
        boolean result;
        if (prevStmt == null) {
            result = singleLineComment.getColumnNo() == nextStmt.getColumnNo();
        }
        else {
            result = singleLineComment.getColumnNo() == nextStmt.getColumnNo()
                | singleLineComment.getColumnNo() == prevStmt.getColumnNo();
        }
        return result;
    }

    /**
     * Checks if current single line comment is trailing comment, e.g.:
     * <p>
     * {@code
     * double d = 3.14; // some comment
     * }
     * </p>
     * @param singleLineComment {@link TokenTypes#SINGLE_LINE_COMMENT single line comment}.
     * @return true if current single line comment is trailing comment.
     */
    private boolean isTrailingSingleLineComment(DetailAST singleLineComment) {
        final String targetSourceLine = getLine(singleLineComment.getLineNo() - 1);
        final int commentColumnNo = singleLineComment.getColumnNo();
        return !Utils.whitespaceBefore(commentColumnNo, targetSourceLine);
    }

    /**
     * Checks comment block indentations over surrounding code, e.g.:
     * <p>
     * {@code
     * /* some comment *&#47; - this is ok
     * double d = 3.14;
     *     /* some comment *&#47; - this is <b>not</b> ok.
     * double d1 = 5.0;
     * }
     * </p>
     * @param blockComment {@link TokenTypes#BLOCK_COMMENT_BEGIN block comment begin}.
     */
    private void visitBlockComment(DetailAST blockComment) {
        final DetailAST nextStatement = blockComment.getNextSibling();
        final DetailAST prevStatement = getPreviousStmt(blockComment);

        if (nextStatement != null
            && nextStatement.getType() != TokenTypes.RCURLY
            && !isTrailingBlockComment(blockComment)
            && !areSameLevelIndented(blockComment, prevStatement, nextStatement)) {

            log(blockComment.getLineNo(), MSG_KEY_BLOCK, nextStatement.getLineNo(),
                blockComment.getColumnNo(), nextStatement.getColumnNo());
        }
    }

    /**
     * Checks if current comment block is trailing comment, e.g.:
     * <p>
     * {@code
     * double d = 3.14; /* some comment *&#47;
     * /* some comment *&#47; double d = 18.5;
     * }
     * </p>
     * @param blockComment {@link TokenTypes#BLOCK_COMMENT_BEGIN block comment begin}.
     * @return true if current comment block is trailing comment.
     */
    private boolean isTrailingBlockComment(DetailAST blockComment) {
        final String commentLine = getLine(blockComment.getLineNo() - 1);
        final int commentColumnNo = blockComment.getColumnNo();
        return !Utils.whitespaceBefore(commentColumnNo, commentLine)
            || blockComment.getNextSibling().getLineNo() == blockComment.getLineNo();
    }
}
