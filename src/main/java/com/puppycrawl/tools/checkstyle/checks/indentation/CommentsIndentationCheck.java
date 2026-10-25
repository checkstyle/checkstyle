///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Controls the indentation between comments and surrounding code.
 * Comments are indented at the same level as the surrounding code.
 * Detailed info about such convention can be found
 * <a href="https://checkstyle.org/styleguides/google-java-style-20260409/javaguide.html#s4.8.6.1-block-comment-style">
 * here</a>
 * </div>
 *
 * @since 6.10
 */
@FileStatefulCheck
public class CommentsIndentationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_SINGLE = "comments.indentation.single";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_BLOCK = "comments.indentation.block";

    /** Root of the AST of the current file. */
    private DetailAST rootAst;

    /** Index of the AST of the current file, it is built lazily. */
    private PreviousStatementIndex previousStatementIndex;

    /** Numbers of empty lines before each line of the current file, built lazily. */
    private int[] emptyLinesBefore;

    /**
     * Creates a new {@code CommentsIndentationCheck} instance.
     */
    public CommentsIndentationCheck() {
        // no code by default
    }

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
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        rootAst = rootAST;
        previousStatementIndex = null;
        emptyLinesBefore = null;
    }

    @Override
    public void visitToken(DetailAST commentAst) {
        switch (commentAst.getType()) {
            case TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN ->
                visitComment(commentAst);

            default -> {
                final String exceptionMsg = "Unexpected token type: " + commentAst.getText();
                throw new IllegalArgumentException(exceptionMsg);
            }
        }
    }

    /**
     * Checks comment indentations over surrounding code, e.g.:
     *
     * <p>
     * {@code
     * // some comment - this is ok
     * double d = 3.14;
     *     // some comment - this is <b>not</b> ok.
     * double d1 = 5.0;
     * }
     * </p>
     *
     * @param comment comment to check.
     */
    private void visitComment(DetailAST comment) {
        if (!isTrailingComment(comment)) {
            final DetailAST prevStmt = getPreviousStatement(comment);
            final DetailAST nextStmt = getNextStmt(comment);

            if (isInEmptyCaseBlock(prevStmt, nextStmt)) {
                handleCommentInEmptyCaseBlock(prevStmt, comment, nextStmt);
            }
            else if (isFallThroughComment(prevStmt, nextStmt)) {
                handleFallThroughComment(prevStmt, comment, nextStmt);
            }
            else if (isInEmptyCodeBlock(prevStmt, nextStmt)) {
                handleCommentInEmptyCodeBlock(comment, nextStmt);
            }
            else if (isCommentAtTheEndOfTheCodeBlock(nextStmt)) {
                handleCommentAtTheEndOfTheCodeBlock(prevStmt, comment, nextStmt);
            }
            else if (nextStmt != null && !areSameLevelIndented(comment, nextStmt, nextStmt)
                    && !areInSameMethodCallWithSameIndent(comment)) {
                log(comment, getMessageKey(comment), nextStmt.getLineNo(),
                    comment.getColumnNo(), nextStmt.getColumnNo());
            }
        }
    }

    /**
     * Returns the next statement of a comment.
     *
     * @param comment comment.
     * @return the next statement of a comment.
     */
    private static DetailAST getNextStmt(DetailAST comment) {
        DetailAST nextStmt = comment.getNextSibling();
        while (nextStmt != null
                && isComment(nextStmt)
                && comment.getColumnNo() != nextStmt.getColumnNo()) {
            nextStmt = nextStmt.getNextSibling();
        }
        return nextStmt;
    }

    /**
     * Returns the previous statement of a comment.
     *
     * @param comment comment.
     * @return the previous statement of a comment.
     */
    private DetailAST getPreviousStatement(DetailAST comment) {
        final DetailAST prevStatement;
        if (isDistributedPreviousStatement(comment)) {
            prevStatement = getDistributedPreviousStatement(comment);
        }
        else {
            prevStatement = getOneLinePreviousStatement(comment);
        }
        return prevStatement;
    }

    /**
     * Checks whether the previous statement of a comment is distributed over two or more lines.
     *
     * @param comment comment to check.
     * @return true if the previous statement of a comment is distributed over two or more lines.
     */
    private boolean isDistributedPreviousStatement(DetailAST comment) {
        final DetailAST previousSibling = comment.getPreviousSibling();
        return isDistributedExpression(comment)
            || isDistributedReturnStatement(previousSibling)
            || isDistributedThrowStatement(previousSibling);
    }

    /**
     * Checks whether the previous statement of a comment is a method call chain or
     * string concatenation statement distributed over two or more lines.
     *
     * @param comment comment to check.
     * @return true if the previous statement is a distributed expression.
     */
    private boolean isDistributedExpression(DetailAST comment) {
        DetailAST previousSibling = comment.getPreviousSibling();
        while (previousSibling != null && isComment(previousSibling)) {
            previousSibling = previousSibling.getPreviousSibling();
        }
        boolean isDistributed = false;
        if (previousSibling != null) {
            if (previousSibling.getType() == TokenTypes.SEMI
                    && isOnPreviousLineIgnoringComments(comment, previousSibling)) {
                DetailAST currentToken = previousSibling.getPreviousSibling();
                while (currentToken.getFirstChild() != null) {
                    currentToken = currentToken.getFirstChild();
                }
                if (!TokenUtil.areOnSameLine(previousSibling, currentToken)) {
                    isDistributed = true;
                }
            }
            else {
                isDistributed = isStatementWithPossibleCurlies(previousSibling);
            }
        }
        return isDistributed;
    }

    /**
     * Whether the statement can have or always have curly brackets.
     *
     * @param previousSibling the statement to check.
     * @return true if the statement can have or always have curly brackets.
     */
    private static boolean isStatementWithPossibleCurlies(DetailAST previousSibling) {
        return previousSibling.getType() == TokenTypes.LITERAL_IF
            || previousSibling.getType() == TokenTypes.LITERAL_TRY
            || previousSibling.getType() == TokenTypes.LITERAL_FOR
            || previousSibling.getType() == TokenTypes.LITERAL_DO
            || previousSibling.getType() == TokenTypes.LITERAL_WHILE
            || previousSibling.getType() == TokenTypes.LITERAL_SWITCH
            || isDefinition(previousSibling);
    }

    /**
     * Whether the statement is a kind of definition (method, class etc.).
     *
     * @param previousSibling the statement to check.
     * @return true if the statement is a kind of definition.
     */
    private static boolean isDefinition(DetailAST previousSibling) {
        return TokenUtil.isTypeDeclaration(previousSibling.getType())
            || previousSibling.getType() == TokenTypes.METHOD_DEF;
    }

    /**
     * Checks whether the previous statement of a comment is a distributed return statement.
     *
     * @param commentPreviousSibling previous sibling of the comment.
     * @return true if the previous statement of a comment is a distributed return statement.
     */
    private static boolean isDistributedReturnStatement(DetailAST commentPreviousSibling) {
        boolean isDistributed = false;
        if (commentPreviousSibling != null
                && commentPreviousSibling.getType() == TokenTypes.LITERAL_RETURN) {
            final DetailAST firstChild = commentPreviousSibling.getFirstChild();
            final DetailAST nextSibling = firstChild.getNextSibling();
            if (nextSibling != null) {
                isDistributed = true;
            }
        }
        return isDistributed;
    }

    /**
     * Checks whether the previous statement of a comment is a distributed throw statement.
     *
     * @param commentPreviousSibling previous sibling of the comment.
     * @return true if the previous statement of a comment is a distributed throw statement.
     */
    private static boolean isDistributedThrowStatement(DetailAST commentPreviousSibling) {
        boolean isDistributed = false;
        if (commentPreviousSibling != null
                && commentPreviousSibling.getType() == TokenTypes.LITERAL_THROW) {
            final DetailAST firstChild = commentPreviousSibling.getFirstChild();
            final DetailAST nextSibling = firstChild.getNextSibling();
            if (!TokenUtil.areOnSameLine(nextSibling, commentPreviousSibling)) {
                isDistributed = true;
            }
        }
        return isDistributed;
    }

    /**
     * Returns the first token of the distributed previous statement of comment.
     *
     * @param comment comment to check.
     * @return the first token of the distributed previous statement of comment.
     */
    private static DetailAST getDistributedPreviousStatement(DetailAST comment) {
        DetailAST currentToken = comment.getPreviousSibling();
        while (isComment(currentToken)) {
            currentToken = currentToken.getPreviousSibling();
        }
        final DetailAST previousStatement;
        if (currentToken.getType() == TokenTypes.SEMI) {
            currentToken = currentToken.getPreviousSibling();
            while (currentToken.getFirstChild() != null) {
                if (isComment(currentToken)) {
                    currentToken = currentToken.getNextSibling();
                }
                else {
                    currentToken = currentToken.getFirstChild();
                }
            }
            previousStatement = currentToken;
        }
        else {
            previousStatement = currentToken;
        }
        return previousStatement;
    }

    /**
     * Checks whether case block is empty.
     *
     * @param prevStmt next statement.
     * @param nextStmt previous statement.
     * @return true if case block is empty.
     */
    private static boolean isInEmptyCaseBlock(DetailAST prevStmt, DetailAST nextStmt) {
        return prevStmt != null
            && nextStmt != null
            && (prevStmt.getType() == TokenTypes.LITERAL_CASE
                || prevStmt.getType() == TokenTypes.CASE_GROUP)
            && (nextStmt.getType() == TokenTypes.LITERAL_CASE
                || nextStmt.getType() == TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks whether comment is a 'fall through' comment.
     * For example:
     *
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     *        int someVariable = 1;
     *        // fall through
     *    case OPTION_TWO:
     *        int a = 5;
     *        break;
     *    ...
     * }
     * </p>
     *
     * @param prevStmt previous statement.
     * @param nextStmt next statement.
     * @return true if a comment is a 'fall through' comment.
     */
    private static boolean isFallThroughComment(DetailAST prevStmt, DetailAST nextStmt) {
        return prevStmt != null
            && nextStmt != null
            && prevStmt.getType() != TokenTypes.LITERAL_CASE
            && (nextStmt.getType() == TokenTypes.LITERAL_CASE
                || nextStmt.getType() == TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks whether a comment is placed at the end of the code block.
     *
     * @param nextStmt next statement.
     * @return true if a comment is placed at the end of the block.
     */
    private static boolean isCommentAtTheEndOfTheCodeBlock(DetailAST nextStmt) {
        return nextStmt != null
            && nextStmt.getType() == TokenTypes.RCURLY;
    }

    /**
     * Checks whether comment is placed in the empty code block.
     * For example:
     *
     * <p>
     * ...
     * {@code
     *  // empty code block
     * }
     * ...
     * </p>
     * Note, the method does not treat empty case blocks.
     *
     * @param prevStmt previous statement.
     * @param nextStmt next statement.
     * @return true if comment is placed in the empty code block.
     */
    private static boolean isInEmptyCodeBlock(DetailAST prevStmt, DetailAST nextStmt) {
        return prevStmt != null
            && nextStmt != null
            && (prevStmt.getType() == TokenTypes.SLIST
                || prevStmt.getType() == TokenTypes.LCURLY
                || prevStmt.getType() == TokenTypes.ARRAY_INIT
                || prevStmt.getType() == TokenTypes.OBJBLOCK)
            && nextStmt.getType() == TokenTypes.RCURLY;
    }

    /**
     * Handles a comment which is placed within empty case block.
     * Note, if comment is placed at the end of the empty case block, we have Checkstyle's
     * limitations to clearly detect user intention of explanation target - above or below. The
     * only case we can assume as a violation is when a single-line comment within the empty case
     * block has indentation level that is lower than the indentation level of the next case
     * token. For example:
     *
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     * // violation
     *    case OPTION_TWO:
     *    ...
     * }
     * </p>
     *
     * @param prevStmt previous statement.
     * @param comment single-line comment.
     * @param nextStmt next statement.
     */
    private void handleCommentInEmptyCaseBlock(DetailAST prevStmt, DetailAST comment,
                                               DetailAST nextStmt) {
        if (comment.getColumnNo() < prevStmt.getColumnNo()
                || comment.getColumnNo() < nextStmt.getColumnNo()) {
            logMultilineIndentation(prevStmt, comment, nextStmt);
        }
    }

    /**
     * Handles 'fall through' single-line comment.
     * Note, 'fall through' and similar comments can have indentation level as next or previous
     * statement.
     * For example:
     *
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     *        int someVariable = 1;
     *        // fall through - OK
     *    case OPTION_TWO:
     *        int a = 5;
     *        break;
     *    ...
     * }
     * </p>
     *
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     *        int someVariable = 1;
     *    // then init variable a - OK
     *    case OPTION_TWO:
     *        int a = 5;
     *        break;
     *    ...
     * }
     * </p>
     *
     * @param prevStmt previous statement.
     * @param comment single-line comment.
     * @param nextStmt next statement.
     */
    private void handleFallThroughComment(DetailAST prevStmt, DetailAST comment,
                                          DetailAST nextStmt) {
        if (!areSameLevelIndented(comment, prevStmt, nextStmt)) {
            logMultilineIndentation(prevStmt, comment, nextStmt);
        }
    }

    /**
     * Handles a comment which is placed at the end of non-empty code block.
     * Note, if single-line comment is placed at the end of non-empty block the comment should have
     * the same indentation level as the previous statement. For example:
     *
     * <p>
     * {@code
     *    if (a == true) {
     *        int b = 1;
     *        // comment
     *    }
     * }
     * </p>
     *
     * @param prevStmt previous statement.
     * @param comment comment to check.
     * @param nextStmt next statement.
     */
    private void handleCommentAtTheEndOfTheCodeBlock(DetailAST prevStmt, DetailAST comment,
                                                     DetailAST nextStmt) {
        if (prevStmt != null) {
            if (prevStmt.getType() == TokenTypes.LITERAL_CASE
                    || prevStmt.getType() == TokenTypes.CASE_GROUP
                    || prevStmt.getType() == TokenTypes.LITERAL_DEFAULT) {
                if (comment.getColumnNo() < nextStmt.getColumnNo()) {
                    log(comment, getMessageKey(comment), nextStmt.getLineNo(),
                        comment.getColumnNo(), nextStmt.getColumnNo());
                }
            }
            else if (isCommentForMultiblock(nextStmt)) {
                if (!areSameLevelIndented(comment, prevStmt, nextStmt)) {
                    logMultilineIndentation(prevStmt, comment, nextStmt);
                }
            }
            else if (!areSameLevelIndented(comment, prevStmt, prevStmt)) {
                final int prevStmtLineNo = prevStmt.getLineNo();
                log(comment, getMessageKey(comment), prevStmtLineNo,
                        comment.getColumnNo(), getLineStart(prevStmtLineNo));
            }
        }
    }

    /**
     * Whether the comment might have been used for the next block in a multi-block structure.
     *
     * @param endBlockStmt the end of the current block.
     * @return true, if the comment might have been used for the next
     *     block in a multi-block structure.
     */
    private static boolean isCommentForMultiblock(DetailAST endBlockStmt) {
        final DetailAST nextBlock = endBlockStmt.getParent().getNextSibling();
        final int endBlockLineNo = endBlockStmt.getLineNo();
        final DetailAST catchAst = endBlockStmt.getParent().getParent();
        final DetailAST finallyAst = catchAst.getNextSibling();
        return nextBlock != null && nextBlock.getLineNo() == endBlockLineNo
                || finallyAst != null
                    && catchAst.getType() == TokenTypes.LITERAL_CATCH
                    && finallyAst.getLineNo() == endBlockLineNo;
    }

    /**
     * Handles a comment which is placed within the empty code block.
     * Note, if comment is placed at the end of the empty code block, we have Checkstyle's
     * limitations to clearly detect user intention of explanation target - above or below. The
     * only case we can assume as a violation is when a single-line comment within the empty
     * code block has indentation level that is lower than the indentation level of the closing
     * right curly brace. For example:
     *
     * <p>
     * {@code
     *    if (a == true) {
     * // violation
     *    }
     * }
     * </p>
     *
     * @param comment comment to check.
     * @param nextStmt next statement.
     */
    private void handleCommentInEmptyCodeBlock(DetailAST comment, DetailAST nextStmt) {
        if (comment.getColumnNo() < nextStmt.getColumnNo()) {
            log(comment, getMessageKey(comment), nextStmt.getLineNo(),
                comment.getColumnNo(), nextStmt.getColumnNo());
        }
    }

    /**
     * Finds the previous statement of the comment. The result is the first node, in pre-order
     * traverse of the enclosing block (and the siblings following it), for which
     * {@code findPreviousStatement} returns non-null value.
     * Instead of evaluating every node of the block, only the nodes for which that method
     * can return non-null value are evaluated, see {@link PreviousStatementIndex}.
     *
     * @param comment current statement.
     * @return previous statement of the comment or null if the comment does not have previous
     *         statement.
     */
    private DetailAST getOneLinePreviousStatement(DetailAST comment) {
        DetailAST root = comment.getParent();
        while (root != null && !isBlockStart(root)) {
            root = root.getParent();
        }

        DetailAST previousStatement = null;
        if (root != null) {
            if (previousStatementIndex == null) {
                previousStatementIndex = new PreviousStatementIndex(rootAst);
            }
            final int start = previousStatementIndex.getIndex(root);
            final int end = previousStatementIndex.getTraverseEnd(root);
            final int commentLineNo = comment.getLineNo();
            final int lineCandidate =
                previousStatementIndex.getFirstLineCandidate(start, commentLineNo - 1);
            int nodeNotAbove = previousStatementIndex.getFirstNodeNotAbove(start, commentLineNo);
            int candidate = Math.min(Math.min(lineCandidate, nodeNotAbove),
                previousStatementIndex.getNextCommentCandidate(start));
            while (previousStatement == null && candidate < end) {
                previousStatement =
                    findPreviousStatement(comment, previousStatementIndex.getNode(candidate));
                if (candidate == nodeNotAbove) {
                    // previous statement from switch block is absent, so other nodes
                    // which are not above the comment can not be previous statement by it
                    nodeNotAbove = end;
                }
                candidate = Math.min(Math.min(lineCandidate, nodeNotAbove),
                    previousStatementIndex.getNextCommentCandidate(candidate + 1));
            }
        }
        return previousStatement;
    }

    /**
     * Whether the ast is a comment.
     *
     * @param ast the ast to check.
     * @return true if the ast is a comment.
     */
    private static boolean isComment(DetailAST ast) {
        final int astType = ast.getType();
        return astType == TokenTypes.SINGLE_LINE_COMMENT
            || astType == TokenTypes.BLOCK_COMMENT_BEGIN
            || astType == TokenTypes.COMMENT_CONTENT
            || astType == TokenTypes.BLOCK_COMMENT_END;
    }

    /**
     * Whether the AST node starts a block.
     *
     * @param root the AST node to check.
     * @return true if the AST node starts a block.
     */
    private static boolean isBlockStart(DetailAST root) {
        return root.getType() == TokenTypes.SLIST
                || root.getType() == TokenTypes.OBJBLOCK
                || root.getType() == TokenTypes.ARRAY_INIT
                || root.getType() == TokenTypes.CASE_GROUP;
    }

    /**
     * Finds a previous statement of the comment.
     * Uses root token of the line while searching.
     *
     * @param comment comment.
     * @param root root token of the line.
     * @return previous statement of the comment or null if previous statement was not found.
     */
    private DetailAST findPreviousStatement(DetailAST comment, DetailAST root) {
        DetailAST previousStatement = null;
        if (Math.max(root.getLineNo(), comment.getLineNo()) == root.getLineNo()) {
            // ATTENTION: parent of the comment is below the comment in case block
            // See https://github.com/checkstyle/checkstyle/issues/851
            previousStatement = getPrevStatementFromSwitchBlock(comment);
        }
        final DetailAST tokenWhichBeginsTheLine =
            PreviousStatementIndex.getTokenWhichBeginsTheLine(root);
        if (tokenWhichBeginsTheLine != null
                && !isComment(tokenWhichBeginsTheLine)
                && isOnPreviousLineIgnoringComments(comment, tokenWhichBeginsTheLine)) {
            previousStatement = tokenWhichBeginsTheLine;
        }
        return previousStatement;
    }

    /**
     * Finds the start token of method call chain.
     *
     * @param root root token of the line.
     * @return the start token of method call chain.
     */
    private static DetailAST findStartTokenOfMethodCallChain(DetailAST root) {
        DetailAST startOfMethodCallChain = root;
        while (startOfMethodCallChain.getFirstChild() != null
                && TokenUtil.areOnSameLine(startOfMethodCallChain.getFirstChild(), root)) {
            startOfMethodCallChain = startOfMethodCallChain.getFirstChild();
        }
        if (startOfMethodCallChain.getFirstChild() != null) {
            startOfMethodCallChain = startOfMethodCallChain.getFirstChild().getNextSibling();
        }
        return startOfMethodCallChain;
    }

    /**
     * Checks whether the checked statement is on the previous line ignoring empty lines
     * and lines which contain only comments.
     *
     * @param currentStatement current statement.
     * @param checkedStatement checked statement.
     * @return true if checked statement is on the line which is previous to current statement
     *     ignoring empty lines and lines which contain only comments.
     */
    private boolean isOnPreviousLineIgnoringComments(DetailAST currentStatement,
                                                     DetailAST checkedStatement) {
        DetailAST nextToken = getNextToken(checkedStatement);
        int distanceAim = 1;
        if (nextToken != null && isComment(nextToken)) {
            distanceAim += countEmptyLines(checkedStatement, currentStatement);
        }

        while (nextToken != null && nextToken != currentStatement && isComment(nextToken)) {
            if (nextToken.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                distanceAim += nextToken.getLastChild().getLineNo() - nextToken.getLineNo();
            }
            distanceAim++;
            nextToken = nextToken.getNextSibling();
        }
        return currentStatement.getLineNo() - checkedStatement.getLineNo() == distanceAim;
    }

    /**
     * Get the token to start counting the number of lines to add to the distance aim from.
     *
     * @param checkedStatement the checked statement.
     * @return the token to start counting the number of lines to add to the distance aim from.
     */
    private DetailAST getNextToken(DetailAST checkedStatement) {
        DetailAST nextToken = PreviousStatementIndex.getFollowingToken(checkedStatement);
        if (nextToken != null && isComment(nextToken) && isTrailingComment(nextToken)) {
            nextToken = nextToken.getNextSibling();
        }
        return nextToken;
    }

    /**
     * Count the number of empty lines between statements.
     *
     * @param startStatement start statement.
     * @param endStatement end statement.
     * @return the number of empty lines between statements.
     */
    private int countEmptyLines(DetailAST startStatement, DetailAST endStatement) {
        if (emptyLinesBefore == null) {
            final String[] lines = getLines();
            emptyLinesBefore = new int[lines.length + 1];
            int emptyLinesNumber = 0;
            for (int lineNo = 0; lineNo < lines.length; lineNo++) {
                if (CommonUtil.isBlank(lines[lineNo])) {
                    emptyLinesNumber++;
                }
                emptyLinesBefore[lineNo + 1] = emptyLinesNumber;
            }
        }
        // no lines are counted if the end statement is above the start statement
        return Math.max(0, emptyLinesBefore[endStatement.getLineNo()]
            - emptyLinesBefore[startStatement.getLineNo()]);
    }

    /**
     * Logs comment which can have the same indentation level as next or previous statement.
     *
     * @param prevStmt previous statement.
     * @param comment comment.
     * @param nextStmt next statement.
     */
    private void logMultilineIndentation(DetailAST prevStmt, DetailAST comment,
                                         DetailAST nextStmt) {
        final String multilineNoTemplate = "%d, %d";
        log(comment, getMessageKey(comment),
            String.format(Locale.getDefault(), multilineNoTemplate, prevStmt.getLineNo(),
                nextStmt.getLineNo()), comment.getColumnNo(),
            String.format(Locale.getDefault(), multilineNoTemplate,
                    getLineStart(prevStmt.getLineNo()), getLineStart(nextStmt.getLineNo())));
    }

    /**
     * Get a message key depending on a comment type.
     *
     * @param comment the comment to process.
     * @return a message key.
     */
    private static String getMessageKey(DetailAST comment) {
        final String msgKey;
        if (comment.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            msgKey = MSG_KEY_SINGLE;
        }
        else {
            msgKey = MSG_KEY_BLOCK;
        }
        return msgKey;
    }

    /**
     * Gets comment's previous statement from switch block.
     *
     * @param comment {@link TokenTypes#SINGLE_LINE_COMMENT single-line comment}.
     * @return comment's previous statement or null if previous statement is absent.
     */
    private static DetailAST getPrevStatementFromSwitchBlock(DetailAST comment) {
        final DetailAST prevStmt;
        final DetailAST parentStatement = comment.getParent();
        if (parentStatement.getType() == TokenTypes.CASE_GROUP) {
            prevStmt = getPrevStatementWhenCommentIsUnderCase(parentStatement);
        }
        else {
            prevStmt = getPrevCaseToken(parentStatement);
        }
        return prevStmt;
    }

    /**
     * Gets previous statement for comment which is placed immediately under case.
     *
     * @param parentStatement comment's parent statement.
     * @return comment's previous statement or null if previous statement is absent.
     */
    private static DetailAST getPrevStatementWhenCommentIsUnderCase(DetailAST parentStatement) {
        DetailAST prevStmt = null;
        final DetailAST prevBlock = parentStatement.getPreviousSibling();
        if (prevBlock.getLastChild() != null) {
            DetailAST blockBody = prevBlock.getLastChild().getLastChild();
            if (blockBody.getType() == TokenTypes.SEMI) {
                blockBody = blockBody.getPreviousSibling();
            }
            if (blockBody.getType() == TokenTypes.EXPR) {
                prevStmt = findStartTokenOfMethodCallChain(blockBody);
            }
            else {
                if (blockBody.getType() == TokenTypes.SLIST) {
                    prevStmt = blockBody.getParent().getParent();
                }
                else {
                    prevStmt = blockBody;
                }
            }
            if (isComment(prevStmt)) {
                prevStmt = prevStmt.getNextSibling();
            }
        }
        return prevStmt;
    }

    /**
     * Gets previous case-token for comment.
     *
     * @param parentStatement comment's parent statement.
     * @return previous case-token or null if previous case-token is absent.
     */
    private static DetailAST getPrevCaseToken(DetailAST parentStatement) {
        final DetailAST prevCaseToken;
        final DetailAST parentBlock = parentStatement.getParent();
        if (parentBlock.getParent().getPreviousSibling() != null
                && parentBlock.getParent().getPreviousSibling().getType()
                    == TokenTypes.LITERAL_CASE) {
            prevCaseToken = parentBlock.getParent().getPreviousSibling();
        }
        else {
            prevCaseToken = null;
        }
        return prevCaseToken;
    }

    /**
     * Checks if comment and next code statement
     * (or previous code stmt like <b>case</b> in switch block) are indented at the same level,
     * e.g.:
     * <pre>
     * {@code
     * // some comment - same indentation level
     * int x = 10;
     *     // some comment - different indentation level
     * int x1 = 5;
     * /*
     *  *
     *  * /
     *  boolean bool = true; - same indentation level
     * }
     * </pre>
     *
     * @param comment {@link TokenTypes#SINGLE_LINE_COMMENT single-line comment}.
     * @param prevStmt previous code statement.
     * @param nextStmt next code statement.
     * @return true if comment and next code statement are indented at the same level.
     */
    private boolean areSameLevelIndented(DetailAST comment, DetailAST prevStmt,
                                                DetailAST nextStmt) {
        return comment.getColumnNo() == getLineStart(nextStmt.getLineNo())
            || comment.getColumnNo() == getLineStart(prevStmt.getLineNo());
    }

    /**
     * Get a column number where a code starts.
     *
     * @param lineNo the line number to get column number in.
     * @return the column number where a code starts.
     */
    private int getLineStart(int lineNo) {
        final char[] line = getLines()[lineNo - 1].toCharArray();
        int lineStart = 0;
        while (Character.isWhitespace(line[lineStart])) {
            lineStart++;
        }
        return lineStart;
    }

    /**
     * Checks if current comment is a trailing comment.
     *
     * @param comment comment to check.
     * @return true if current comment is a trailing comment.
     */
    private boolean isTrailingComment(DetailAST comment) {
        final boolean isTrailingComment;
        if (comment.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            isTrailingComment = isTrailingSingleLineComment(comment);
        }
        else {
            isTrailingComment = isTrailingBlockComment(comment);
        }
        return isTrailingComment;
    }

    /**
     * Checks if current single-line comment is trailing comment, e.g.:
     *
     * <p>
     * {@code
     * double d = 3.14; // some comment
     * }
     * </p>
     *
     * @param singleLineComment {@link TokenTypes#SINGLE_LINE_COMMENT single-line comment}.
     * @return true if current single-line comment is trailing comment.
     */
    private boolean isTrailingSingleLineComment(DetailAST singleLineComment) {
        final String targetSourceLine = getLine(singleLineComment.getLineNo() - 1);
        final int commentColumnNo = singleLineComment.getColumnNo();
        return !CommonUtil.hasWhitespaceBefore(commentColumnNo, targetSourceLine);
    }

    /**
     * Checks if current comment block is trailing comment, e.g.:
     *
     * <p>
     * {@code
     * double d = 3.14; /* some comment * /
     * /* some comment * / double d = 18.5;
     * }
     * </p>
     *
     * @param blockComment {@link TokenTypes#BLOCK_COMMENT_BEGIN block comment begin}.
     * @return true if current comment block is trailing comment.
     */
    private boolean isTrailingBlockComment(DetailAST blockComment) {
        final String commentLine = getLine(blockComment.getLineNo() - 1);
        final int commentColumnNo = blockComment.getColumnNo();
        final DetailAST nextSibling = blockComment.getNextSibling();
        return !CommonUtil.hasWhitespaceBefore(commentColumnNo, commentLine)
            || nextSibling != null && TokenUtil.areOnSameLine(nextSibling, blockComment);
    }

    /**
     * Checks if the comment is inside a method call with same indentation of first expression. e.g:
     *
     * <p>
     * {@code
     * private final boolean myList = someMethod(
     *     // Some comment here
     *     s1,
     *     s2,
     *     s3
     *     // ok
     * );
     * }
     * </p>
     *
     * @param comment comment to check.
     * @return true, if comment is inside a method call with same indentation.
     */
    private static boolean areInSameMethodCallWithSameIndent(DetailAST comment) {
        return comment.getParent().getType() == TokenTypes.METHOD_CALL
                && comment.getColumnNo()
                     == getFirstExpressionNodeFromMethodCall(comment.getParent()).getColumnNo();
    }

    /**
     * Returns the first EXPR DetailAST child from parent of comment.
     *
     * @param methodCall methodCall DetailAst from which node to be extracted.
     * @return first EXPR DetailAST child from parent of comment.
     */
    private static DetailAST getFirstExpressionNodeFromMethodCall(DetailAST methodCall) {
        // Method call always has ELIST
        return methodCall.findFirstToken(TokenTypes.ELIST);
    }

    /**
     * Index of the AST of a file which allows to find the previous statement of a comment
     * without evaluating every node of the enclosing block.
     *
     * <p>
     * All nodes are numbered in pre-order, so the nodes traversed when searching the previous
     * statement of a comment form a contiguous range. In that range,
     * {@code findPreviousStatement} can return non-null value only for:
     * </p>
     * <ul>
     * <li>the first node which is not above the comment, as the previous statement from
     * switch block does not depend on the node;</li>
     * <li>a node whose token which begins the line is on the line just above the comment,
     * when the token is not followed by a comment;</li>
     * <li>a node whose token which begins the line is followed by a comment.</li>
     * </ul>
     *
     * <p>
     * Candidates are kept in sorted arrays as odd numbers {@code 2 * index + 1}, so a binary
     * search for an even number never finds an exact match and always returns the insertion
     * point. Each array ends with a sentinel which is greater than any candidate.
     * </p>
     */
    private static final class PreviousStatementIndex {

        /** Bits of a line candidate which are taken by the encoded index of the node. */
        private static final int INDEX_BITS = Integer.SIZE;

        /** Mask of the bits of a line candidate which are taken by the encoded index. */
        private static final long INDEX_MASK = (1L << INDEX_BITS) - 1;

        /** Encoded index which is greater than encoded index of any node. */
        private static final long SENTINEL_INDEX = encode(Integer.MAX_VALUE);

        /** Nodes in pre-order. */
        private final PreOrderNode[] nodes;

        /** Indexes of the nodes which start a block. */
        private final Map<DetailAST, Integer> blockIndexes = new HashMap<>();

        /**
         * Nodes whose token which begins the line is not followed by a comment, as line number
         * of the token in the high bits and encoded index of the node in the low bits, sorted.
         */
        private final long[] lineCandidates;

        /**
         * Encoded indexes of the nodes whose token which begins the line is followed by
         * a comment, sorted.
         */
        private final long[] commentCandidates;

        /**
         * Creates the index.
         *
         * @param rootAst root of the AST of a file.
         */
        private PreviousStatementIndex(DetailAST rootAst) {
            final List<DetailAST> preOrder = getPreOrder(rootAst);
            nodes = new PreOrderNode[preOrder.size()];
            final long[] lineCandidatesBuffer = new long[nodes.length];
            int lineCandidatesCount = 0;
            final long[] commentCandidatesBuffer = new long[nodes.length];
            int commentCandidatesStart = nodes.length;
            final ListIterator<DetailAST> iterator = preOrder.listIterator(nodes.length);
            while (iterator.hasPrevious()) {
                final int index = iterator.previousIndex();
                final DetailAST node = iterator.previous();
                nodes[index] = createPreOrderNode(nodes, blockIndexes, node, index);
                final DetailAST tokenWhichBeginsTheLine = getTokenWhichBeginsTheLine(node);
                if (tokenWhichBeginsTheLine != null && !isComment(tokenWhichBeginsTheLine)) {
                    final DetailAST followingToken = getFollowingToken(tokenWhichBeginsTheLine);
                    if (followingToken != null && isComment(followingToken)) {
                        commentCandidatesStart--;
                        commentCandidatesBuffer[commentCandidatesStart] = encode(index);
                    }
                    else {
                        lineCandidatesBuffer[lineCandidatesCount] = encodeLineCandidate(
                            tokenWhichBeginsTheLine.getLineNo(), encode(index));
                        lineCandidatesCount++;
                    }
                }
            }
            lineCandidates = Arrays.copyOf(lineCandidatesBuffer, lineCandidatesCount + 1);
            lineCandidates[lineCandidatesCount] =
                encodeLineCandidate(Integer.MAX_VALUE, SENTINEL_INDEX);
            Arrays.sort(lineCandidates);
            commentCandidates = Arrays.copyOfRange(commentCandidatesBuffer,
                commentCandidatesStart, nodes.length + 1);
            commentCandidates[nodes.length - commentCandidatesStart] = SENTINEL_INDEX;
        }

        /**
         * Gets the token which begins the line of the root token, as it is considered by
         * {@code findPreviousStatement}.
         *
         * @param root root token of the line.
         * @return the token which begins the line, or null if it is absent.
         */
        private static DetailAST getTokenWhichBeginsTheLine(DetailAST root) {
            final DetailAST tokenWhichBeginsTheLine;
            if (root.getType() == TokenTypes.EXPR) {
                tokenWhichBeginsTheLine = findStartTokenOfMethodCallChain(root);
            }
            else if (root.getType() == TokenTypes.PLUS) {
                tokenWhichBeginsTheLine = root.getFirstChild();
            }
            else {
                tokenWhichBeginsTheLine = root;
            }
            return tokenWhichBeginsTheLine;
        }

        /**
         * Gets the token which follows the checked statement, without skipping trailing comments,
         * as it is considered by {@code getNextToken}.
         *
         * @param checkedStatement the checked statement.
         * @return the token which follows the checked statement.
         */
        private static DetailAST getFollowingToken(DetailAST checkedStatement) {
            final DetailAST followingToken;
            if (checkedStatement.getType() == TokenTypes.SLIST
                    || checkedStatement.getType() == TokenTypes.ARRAY_INIT
                    || checkedStatement.getType() == TokenTypes.CASE_GROUP) {
                followingToken = checkedStatement.getFirstChild();
            }
            else {
                followingToken = checkedStatement.getNextSibling();
            }
            return followingToken;
        }

        /**
         * Collects the nodes of the AST in pre-order.
         *
         * @param rootAst root of the AST.
         * @return the nodes in pre-order.
         */
        private static List<DetailAST> getPreOrder(DetailAST rootAst) {
            final List<DetailAST> preOrder = new ArrayList<>();
            DetailAST node = rootAst;
            while (node != null) {
                preOrder.add(node);
                DetailAST next = node.getFirstChild();
                while (node != null && next == null) {
                    next = node.getNextSibling();
                    node = node.getParent();
                }
                node = next;
            }
            return preOrder;
        }

        /**
         * Encodes index of a node as odd number.
         *
         * @param index index of a node.
         * @return encoded index.
         */
        private static long encode(int index) {
            return 2L * index + 1;
        }

        /**
         * Decodes index of a node.
         *
         * @param encodedIndex encoded index of a node.
         * @return index of a node.
         */
        private static int decode(long encodedIndex) {
            return Math.toIntExact(encodedIndex / 2);
        }

        /**
         * Encodes a line candidate.
         *
         * @param lineNo line number of the token which begins the line.
         * @param encodedIndex encoded index of the node.
         * @return encoded line candidate.
         */
        private static long encodeLineCandidate(int lineNo, long encodedIndex) {
            return (long) lineNo << INDEX_BITS | encodedIndex;
        }

        /**
         * Creates the node of pre-order, computing the end and maximal line number of
         * its subtree. Nodes which follow the node must be created already.
         *
         * @param nodes nodes in pre-order.
         * @param blockIndexes indexes of the nodes which start a block.
         * @param node the node.
         * @param index index of the node.
         * @return the node of pre-order.
         */
        private static PreOrderNode createPreOrderNode(PreOrderNode[] nodes,
                Map<DetailAST, Integer> blockIndexes, DetailAST node, int index) {
            if (isBlockStart(node)) {
                blockIndexes.put(node, index);
            }
            final int lineNo = node.getLineNo();
            int maxLineNo = lineNo;
            int child = index + 1;
            DetailAST childNode = node.getFirstChild();
            while (childNode != null) {
                maxLineNo = Math.max(maxLineNo, nodes[child].subtreeMaxLineNo());
                child = nodes[child].subtreeEnd();
                childNode = childNode.getNextSibling();
            }
            return new PreOrderNode(node, lineNo, child, maxLineNo);
        }

        /**
         * Gets the node by index.
         *
         * @param index index of the node.
         * @return the node.
         */
        private DetailAST getNode(int index) {
            return nodes[index].node();
        }

        /**
         * Gets index of the node which starts a block.
         *
         * @param block the node which starts a block.
         * @return index of the node.
         */
        private int getIndex(DetailAST block) {
            return blockIndexes.get(block);
        }

        /**
         * Gets the end of pre-order traverse which starts from the node which starts a block
         * and visits the following siblings of the node.
         *
         * @param block the node to start traverse from.
         * @return index of the node which follows the traverse.
         */
        private int getTraverseEnd(DetailAST block) {
            int end = getIndex(block);
            DetailAST node = block;
            while (node != null) {
                end = nodes[end].subtreeEnd();
                node = node.getNextSibling();
            }
            return end;
        }

        /**
         * Finds the first node, starting from the index, which is on the line or below.
         * Such node always exists in the traverse which contains the comment on the line.
         *
         * @param start index to start search from.
         * @param lineNo line number.
         * @return index of the found node.
         */
        private int getFirstNodeNotAbove(int start, int lineNo) {
            int index = start;
            while (nodes[index].lineNo() < lineNo) {
                if (nodes[index].subtreeMaxLineNo() < lineNo) {
                    index = nodes[index].subtreeEnd();
                }
                else {
                    index++;
                }
            }
            return index;
        }

        /**
         * Finds the first node, starting from the index, whose token which begins the line
         * is on the line and is not followed by a comment.
         *
         * @param from index to start search from.
         * @param lineNo line number of the token.
         * @return index of the found node or index greater than index of any node.
         */
        private int getFirstLineCandidate(int from, int lineNo) {
            final int position = -Arrays.binarySearch(lineCandidates,
                encodeLineCandidate(lineNo, 2L * from)) - 1;
            int result = Integer.MAX_VALUE;
            if (lineCandidates[position] >> INDEX_BITS == lineNo) {
                result = decode(lineCandidates[position] & INDEX_MASK);
            }
            return result;
        }

        /**
         * Finds the first node, starting from the index, whose token which begins the line
         * is followed by a comment.
         *
         * @param from index to start search from.
         * @return index of the found node or index greater than index of any node.
         */
        private int getNextCommentCandidate(int from) {
            final int position = -Arrays.binarySearch(commentCandidates, 2L * from) - 1;
            return decode(commentCandidates[position]);
        }

    }

    /**
     * Node of pre-order with information about its subtree.
     *
     * @param node the node.
     * @param lineNo line number of the node.
     * @param subtreeEnd index of the node which follows the subtree of the node.
     * @param subtreeMaxLineNo maximal line number of the nodes in the subtree of the node.
     */
    private record PreOrderNode(DetailAST node, int lineNo, int subtreeEnd,
                                int subtreeMaxLineNo) {
    }

}
