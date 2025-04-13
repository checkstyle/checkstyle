///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
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
 * <a href="https://checkstyle.org/styleguides/google-java-style-20220203/javaguide.html#s4.8.6.1-block-comment-style">
 * here</a>
 * </div>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SINGLE_LINE_COMMENT">
 * SINGLE_LINE_COMMENT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BLOCK_COMMENT_BEGIN">
 * BLOCK_COMMENT_BEGIN</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code comments.indentation.block}
 * </li>
 * <li>
 * {@code comments.indentation.single}
 * </li>
 * </ul>
 *
 * @since 6.10
 */
@StatelessCheck
public class CommentsIndentationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_SINGLE = "comments.indentation.single";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
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
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST commentAst) {
        switch (commentAst.getType()) {
            case TokenTypes.SINGLE_LINE_COMMENT:
            case TokenTypes.BLOCK_COMMENT_BEGIN:
                visitComment(commentAst);
                break;
            default:
                final String exceptionMsg = "Unexpected token type: " + commentAst.getText();
                throw new IllegalArgumentException(exceptionMsg);
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
     * Does pre-order traverse of abstract syntax tree to find the previous statement of the
     * comment. If previous statement of the comment is found, then the traverse will
     * be finished.
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

        final Deque<DetailAST> stack = new ArrayDeque<>();
        DetailAST previousStatement = null;
        while (root != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                root = stack.pop();
            }
            while (root != null) {
                previousStatement = findPreviousStatement(comment, root);
                if (previousStatement != null) {
                    root = null;
                    stack.clear();
                    break;
                }
                if (root.getNextSibling() != null) {
                    stack.push(root.getNextSibling());
                }
                root = root.getFirstChild();
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
        if (root.getLineNo() >= comment.getLineNo()) {
            // ATTENTION: parent of the comment is below the comment in case block
            // See https://github.com/checkstyle/checkstyle/issues/851
            previousStatement = getPrevStatementFromSwitchBlock(comment);
        }
        final DetailAST tokenWhichBeginsTheLine;
        if (root.getType() == TokenTypes.EXPR
                && root.getFirstChild().getFirstChild() != null) {
            if (root.getFirstChild().getType() == TokenTypes.LITERAL_NEW) {
                tokenWhichBeginsTheLine = root.getFirstChild();
            }
            else {
                tokenWhichBeginsTheLine = findTokenWhichBeginsTheLine(root);
            }
        }
        else if (root.getType() == TokenTypes.PLUS) {
            tokenWhichBeginsTheLine = root.getFirstChild();
        }
        else {
            tokenWhichBeginsTheLine = root;
        }
        if (tokenWhichBeginsTheLine != null
                && !isComment(tokenWhichBeginsTheLine)
                && isOnPreviousLineIgnoringComments(comment, tokenWhichBeginsTheLine)) {
            previousStatement = tokenWhichBeginsTheLine;
        }
        return previousStatement;
    }

    /**
     * Finds a token which begins the line.
     *
     * @param root root token of the line.
     * @return token which begins the line.
     */
    private static DetailAST findTokenWhichBeginsTheLine(DetailAST root) {
        final DetailAST tokenWhichBeginsTheLine;
        if (isUsingOfObjectReferenceToInvokeMethod(root)) {
            tokenWhichBeginsTheLine = findStartTokenOfMethodCallChain(root);
        }
        else {
            tokenWhichBeginsTheLine = root.getFirstChild().findFirstToken(TokenTypes.IDENT);
        }
        return tokenWhichBeginsTheLine;
    }

    /**
     * Checks whether there is a use of an object reference to invoke an object's method on line.
     *
     * @param root root token of the line.
     * @return true if there is a use of an object reference to invoke an object's method on line.
     */
    private static boolean isUsingOfObjectReferenceToInvokeMethod(DetailAST root) {
        return root.getFirstChild().getFirstChild().getFirstChild() != null
            && root.getFirstChild().getFirstChild().getFirstChild().getNextSibling() != null;
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
        DetailAST nextToken;
        if (checkedStatement.getType() == TokenTypes.SLIST
                || checkedStatement.getType() == TokenTypes.ARRAY_INIT
                || checkedStatement.getType() == TokenTypes.CASE_GROUP) {
            nextToken = checkedStatement.getFirstChild();
        }
        else {
            nextToken = checkedStatement.getNextSibling();
        }
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
        int emptyLinesNumber = 0;
        final String[] lines = getLines();
        final int endLineNo = endStatement.getLineNo();
        for (int lineNo = startStatement.getLineNo(); lineNo < endLineNo; lineNo++) {
            if (CommonUtil.isBlank(lines[lineNo])) {
                emptyLinesNumber++;
            }
        }
        return emptyLinesNumber;
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
                if (isUsingOfObjectReferenceToInvokeMethod(blockBody)) {
                    prevStmt = findStartTokenOfMethodCallChain(blockBody);
                }
                else {
                    prevStmt = blockBody.getFirstChild().getFirstChild();
                }
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
     *  *&#47;
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
     * double d = 3.14; /* some comment *&#47;
     * /* some comment *&#47; double d = 18.5;
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
     * Checks if the comment is inside a method call with same indentation of
     * first expression. e.g:
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

}
