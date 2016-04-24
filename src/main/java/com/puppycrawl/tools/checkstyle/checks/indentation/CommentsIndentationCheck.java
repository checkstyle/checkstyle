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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

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
 * &lt;module name=&quot;CommentsIndentation&quot;/&gt;
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
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 * @author <a href="mailto:andreyselkin@gmail.com">Andrei Selkin</a>
 */
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
        return CommonUtils.EMPTY_INT_ARRAY;
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
        final DetailAST prevStmt = getPreviousStatementOfSingleLineComment(singleLineComment);
        final DetailAST nextStmt = singleLineComment.getNextSibling();

        if (!isTrailingSingleLineComment(singleLineComment)) {
            if (isInEmptyCaseBlock(prevStmt, nextStmt)) {
                handleSingleLineCommentInEmptyCaseBlock(prevStmt, singleLineComment,
                    nextStmt);
            }
            else if (isFallThroughSingleLineComment(prevStmt, nextStmt)) {
                handleFallThroughtSingleLineComment(prevStmt, singleLineComment,
                    nextStmt);
            }
            else if (isInEmptyCodeBlock(prevStmt, nextStmt)) {
                handleSingleLineCommentInEmptyCodeBlock(singleLineComment, nextStmt);
            }
            else if (isSingleLineCommentAtTheEndOfTheCodeBlock(nextStmt)) {
                handleSingleLineCommentAtTheEndOfTheCodeBlock(prevStmt, singleLineComment,
                    nextStmt);
            }
            else if (nextStmt != null
                        && !areSameLevelIndented(singleLineComment, nextStmt, nextStmt)) {
                log(singleLineComment.getLineNo(), MSG_KEY_SINGLE, nextStmt.getLineNo(),
                    singleLineComment.getColumnNo(), nextStmt.getColumnNo());
            }
        }
    }

    /**
     * Returns the previous statement of a single line comment.
     * @param comment single line comment.
     * @return the previous statement of a single line comment.
     */
    private static DetailAST getPreviousStatementOfSingleLineComment(DetailAST comment) {
        final DetailAST prevStatement;
        if (isDistributedPreviousStatement(comment)) {
            prevStatement = getDistributedPreviousStatementOfSingleLineComment(comment);
        }
        else {
            prevStatement = getOneLinePreviousStatementOfSingleLineComment(comment);
        }
        return prevStatement;
    }

    /**
     * Checks whether the previous statement of a single line comment is distributed over two or
     * more lines.
     * @param singleLineComment single line comment.
     * @return true if the previous statement of a single line comment is distributed over two or
     *         more lines.
     */
    private static boolean isDistributedPreviousStatement(DetailAST singleLineComment) {
        final DetailAST previousSibling = singleLineComment.getPreviousSibling();
        return isDistributedMethodChainOrConcatenationStatement(singleLineComment, previousSibling)
            || isDistributedReturnStatement(previousSibling)
            || isDistributedThrowStatement(previousSibling);
    }

    /**
     * Checks whether the previous statement of a single line comment is a method call chain or
     * string concatenation statemen distributed over two ore more lines.
     * @param comment single line comment.
     * @param commentPreviousSibling previous sibling of the sinle line comment.
     * @return if the previous statement of a single line comment is a method call chain or
     *         string concatenation statemen distributed over two ore more lines.
     */
    private static boolean isDistributedMethodChainOrConcatenationStatement(
        DetailAST comment, DetailAST commentPreviousSibling) {
        boolean isDistributed = false;
        if (commentPreviousSibling != null
                && commentPreviousSibling.getType() == TokenTypes.SEMI
                && comment.getLineNo() - commentPreviousSibling.getLineNo() == 1) {
            DetailAST currentToken = commentPreviousSibling.getPreviousSibling();
            while (currentToken.getFirstChild() != null) {
                currentToken = currentToken.getFirstChild();
            }
            if (currentToken.getType() == TokenTypes.COMMENT_CONTENT) {
                currentToken = currentToken.getParent();
                while (currentToken.getType() == TokenTypes.SINGLE_LINE_COMMENT
                        || currentToken.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                    currentToken = currentToken.getNextSibling();
                }
            }
            if (commentPreviousSibling.getLineNo() != currentToken.getLineNo()) {
                isDistributed = true;
            }
        }
        return isDistributed;
    }

    /**
     * Checks whether the previous statement of a single line comment is a destributed return
     * statement.
     * @param commentPreviousSibling previous sibling of the single line comment.
     * @return true if the previous statement of a single line comment is a destributed return
     *         statement.
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
     * Checks whether the previous statement of a single line comment is a destributed throw
     * statement.
     * @param commentPreviousSibling previous sibling of the single line comment.
     * @return true if the previous statement of a single line comment is a destributed throw
     *         statement.
     */
    private static boolean isDistributedThrowStatement(DetailAST commentPreviousSibling) {
        boolean isDistributed = false;
        if (commentPreviousSibling != null
                && commentPreviousSibling.getType() == TokenTypes.LITERAL_THROW) {
            final DetailAST firstChild = commentPreviousSibling.getFirstChild();
            final DetailAST nextSibling = firstChild.getNextSibling();
            if (nextSibling.getLineNo() != commentPreviousSibling.getLineNo()) {
                isDistributed = true;
            }
        }
        return isDistributed;
    }

    /**
     * Returns the first token of the destributed previous statement of single line comment.
     * @param comment single line comment.
     * @return the first token of the destributed previous statement of single line comment.
     */
    private static DetailAST getDistributedPreviousStatementOfSingleLineComment(DetailAST comment) {
        final DetailAST previousStatement;
        DetailAST currentToken = comment.getPreviousSibling();
        if (currentToken.getType() == TokenTypes.LITERAL_RETURN
                || currentToken.getType() == TokenTypes.LITERAL_THROW) {
            previousStatement = currentToken;
        }
        else {
            currentToken = currentToken.getPreviousSibling();
            while (currentToken.getFirstChild() != null) {
                currentToken = currentToken.getFirstChild();
            }
            previousStatement = currentToken;
        }
        return previousStatement;
    }

    /**
     * Checks whether case block is empty.
     * @param nextStmt previous statement.
     * @param prevStmt next statement.
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
     * Checks whether single line comment is a 'fall through' comment.
     * For example:
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
     * @param prevStmt previous statement.
     * @param nextStmt next statement.
     * @return true if a single line comment is a 'fall through' comment.
     */
    private static boolean isFallThroughSingleLineComment(DetailAST prevStmt, DetailAST nextStmt) {
        return prevStmt != null
            && nextStmt != null
            && prevStmt.getType() != TokenTypes.LITERAL_CASE
            && (nextStmt.getType() == TokenTypes.LITERAL_CASE
                || nextStmt.getType() == TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks whether a single line comment is placed at the end of the code block.
     * @param nextStmt next statement.
     * @return true if a single line comment is placed at the end of the block.
     */
    private static boolean isSingleLineCommentAtTheEndOfTheCodeBlock(DetailAST nextStmt) {
        return nextStmt != null
            && nextStmt.getType() == TokenTypes.RCURLY;
    }

    /**
     * Checks whether comment is placed in the empty code block.
     * For example:
     * <p>
     * ...
     * {@code
     *  // empty code block
     * }
     * ...
     * </p>
     * Note, the method does not treat empty case blocks.
     * @param prevStmt previous statement.
     * @param nextStmt next statement.
     * @return true if comment is placed in the empty code block.
     */
    private static boolean isInEmptyCodeBlock(DetailAST prevStmt, DetailAST nextStmt) {
        return prevStmt != null
            && nextStmt != null
            && (prevStmt.getType() == TokenTypes.SLIST
                || prevStmt.getType() == TokenTypes.OBJBLOCK)
            && nextStmt.getType() == TokenTypes.RCURLY;
    }

    /**
     * Handles a single line comment which is plased within empty case block.
     * Note, if comment is placed at the end of the empty case block, we have Checkstyle's
     * limitations to clearly detect user intention of explanation target - above or below. The
     * only case we can assume as a violation is when a single line comment within the empty case
     * block has indentation level that is lower than the indentation level of the next case
     * token. For example:
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     * // violation
     *    case OPTION_TWO:
     *    ...
     * }
     * </p>
     * @param prevStmt previous statement.
     * @param comment single line comment.
     * @param nextStmt next statement.
     */
    private void handleSingleLineCommentInEmptyCaseBlock(DetailAST prevStmt, DetailAST comment,
                                                         DetailAST nextStmt) {

        if (comment.getColumnNo() < prevStmt.getColumnNo()
                || comment.getColumnNo() < nextStmt.getColumnNo()) {
            logMultilineIndentation(prevStmt, comment, nextStmt);
        }
    }

    /**
     * Handles 'fall through' single line comment.
     * Note, 'fall through' and similar comments can have indentation level as next or previous
     * statement.
     * For example:
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
     * <p>
     * {@code
     *    ...
     *    case OPTION_ONE:
     *        int someVariable = 1;
     *    // than init variable a - OK
     *    case OPTION_TWO:
     *        int a = 5;
     *        break;
     *    ...
     * }
     * </p>
     * @param prevStmt previous statement.
     * @param comment single line comment.
     * @param nextStmt next statement.
     */
    private void handleFallThroughtSingleLineComment(DetailAST prevStmt, DetailAST comment,
                                                     DetailAST nextStmt) {

        if (!areSameLevelIndented(comment, prevStmt, nextStmt)) {
            logMultilineIndentation(prevStmt, comment, nextStmt);
        }

    }

    /**
     * Hendles a single line comment which is placed at the end of non empty code block.
     * Note, if single line comment is plcaed at the end of non empty block the comment should have
     * the same indentation level as the previous statement. For example:
     * <p>
     * {@code
     *    if (a == true) {
     *        int b = 1;
     *        // comment
     *    }
     * }
     * </p>
     * @param prevStmt previous statement.
     * @param comment single line statement.
     * @param nextStmt next statement.
     */
    private void handleSingleLineCommentAtTheEndOfTheCodeBlock(DetailAST prevStmt,
                                                               DetailAST comment,
                                                               DetailAST nextStmt) {
        if (prevStmt != null) {
            if (prevStmt.getType() == TokenTypes.LITERAL_CASE
                    || prevStmt.getType() == TokenTypes.CASE_GROUP
                    || prevStmt.getType() == TokenTypes.LITERAL_DEFAULT
                    || prevStmt.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
                if (comment.getColumnNo() < nextStmt.getColumnNo()) {
                    log(comment.getLineNo(), MSG_KEY_SINGLE, nextStmt.getLineNo(),
                        comment.getColumnNo(), nextStmt.getColumnNo());
                }
            }
            else if (!areSameLevelIndented(comment, prevStmt, prevStmt)) {
                final int prevStmtLineNo = prevStmt.getLineNo();
                log(comment.getLineNo(), MSG_KEY_SINGLE, prevStmtLineNo,
                    comment.getColumnNo(), getLineStart(prevStmtLineNo));
            }
        }

    }

    /**
     * Handles a single line comment which is placed within the empty code block.
     * Note, if comment is placed at the end of the empty code block, we have Checkstyle's
     * limitations to clearly detect user intention of explanation target - above or below. The
     * only case we can assume as a violation is when a single line comment within the empty
     * code block has indentation level that is lower than the indentation level of the closing
     * right curly brace. For example:
     * <p>
     * {@code
     *    if (a == true) {
     * // violation
     *    }
     * }
     * </p>
     *
     * @param comment single line comment.
     * @param nextStmt next statement.
     */
    private void handleSingleLineCommentInEmptyCodeBlock(DetailAST comment, DetailAST nextStmt) {
        if (comment.getColumnNo() < nextStmt.getColumnNo()) {
            log(comment.getLineNo(), MSG_KEY_SINGLE, nextStmt.getLineNo(),
                comment.getColumnNo(), nextStmt.getColumnNo());
        }
    }

    /**
     * Does pre-order traverse of abstract syntax tree to find the previous statement of the
     * single line comment. If previous statement of the comment is found, then the traverse will
     * be finished.
     * @param comment current statement.
     * @return previous statement of the comment or null if the comment does not have previous
     *         statement.
     */
    private static DetailAST getOneLinePreviousStatementOfSingleLineComment(DetailAST comment) {
        DetailAST previousStatement = null;
        final Deque<DetailAST> stack = new ArrayDeque<>();
        DetailAST root = comment.getParent();

        while (root != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                root = stack.pop();
            }
            while (root != null) {
                previousStatement = findPreviousStatementOfSingleLineComment(comment, root);
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
     * Finds a previous statement of the single line comment.
     * Uses root token of the line while searching.
     * @param comment single line comment.
     * @param root root token of the line.
     * @return previous statement of the single line comment or null if previous statement was not
     *         found.
     */
    private static DetailAST findPreviousStatementOfSingleLineComment(DetailAST comment,
                                                                      DetailAST root) {
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
                && isOnPreviousLine(comment, tokenWhichBeginsTheLine)
            ) {
            previousStatement = tokenWhichBeginsTheLine;
        }
        return previousStatement;
    }

    /**
     * Finds a token which begins the line.
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
     * @param root root token of the line.
     * @return true if there is a use of an object reference to invoke an object's method on line.
     */
    private static boolean isUsingOfObjectReferenceToInvokeMethod(DetailAST root) {
        return root.getFirstChild().getFirstChild().getFirstChild() != null
            && root.getFirstChild().getFirstChild().getFirstChild().getNextSibling() != null;
    }

    /**
     * Finds the start token of method call chain.
     * @param root root token of the line.
     * @return the start token of method call chain.
     */
    private static DetailAST findStartTokenOfMethodCallChain(DetailAST root) {
        DetailAST startOfMethodCallChain = root;
        while (startOfMethodCallChain.getFirstChild() != null
                && startOfMethodCallChain.getFirstChild().getLineNo() == root.getLineNo()) {
            startOfMethodCallChain = startOfMethodCallChain.getFirstChild();
        }
        if (startOfMethodCallChain.getFirstChild() != null) {
            startOfMethodCallChain = startOfMethodCallChain.getFirstChild().getNextSibling();
        }
        return startOfMethodCallChain;
    }

    /**
     * Checks whether the checked statement is on previous line.
     * @param currentStatement current statement.
     * @param checkedStatement checked statement.
     * @return true if checked statement is on the line which is previous to current statement.
     */
    private static boolean isOnPreviousLine(DetailAST currentStatement,
                                            DetailAST checkedStatement) {
        return currentStatement.getLineNo() - checkedStatement.getLineNo() == 1;
    }

    /**
     * Logs comment which can have the same indentation level as next or previous statement.
     * @param comment comment.
     * @param nextStmt next statement.
     * @param prevStmt previous statement.
     */
    private void logMultilineIndentation(DetailAST prevStmt, DetailAST comment,
                                         DetailAST nextStmt) {
        final String multilineNoTemplate = "%d, %d";
        log(comment.getLineNo(), MSG_KEY_SINGLE,
            String.format(Locale.getDefault(), multilineNoTemplate, prevStmt.getLineNo(),
                nextStmt.getLineNo()), comment.getColumnNo(),
            String.format(Locale.getDefault(), multilineNoTemplate, prevStmt.getColumnNo(),
                nextStmt.getColumnNo()));
    }

    /**
     * Gets comment's previous statement from switch block.
     * @param comment {@link TokenTypes#SINGLE_LINE_COMMENT single-line comment}.
     * @return comment's previous statement or null if previous statement is absent.
     */
    private static DetailAST getPrevStatementFromSwitchBlock(DetailAST comment) {
        DetailAST prevStmt = null;
        final DetailAST parentStatement = comment.getParent();
        if (parentStatement != null) {
            if (parentStatement.getType() == TokenTypes.CASE_GROUP) {
                prevStmt = getPrevStatementWhenCommentIsUnderCase(parentStatement);
            }
            else {
                prevStmt = getPrevCaseToken(parentStatement);
            }
        }
        return prevStmt;
    }

    /**
     * Gets previous statement for comment which is placed immediately under case.
     * @param parentStatement comment's parent statement.
     * @return comment's previous statement or null if previous statement is absent.
     */
    private static DetailAST getPrevStatementWhenCommentIsUnderCase(DetailAST parentStatement) {
        DetailAST prevStmt = null;
        final DetailAST prevBlock = parentStatement.getPreviousSibling();
        if (prevBlock.getLastChild() != null) {
            DetailAST blockBody = prevBlock.getLastChild().getLastChild();
            if (blockBody.getPreviousSibling() != null) {
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
        }
        return prevStmt;
    }

    /**
     * Gets previous case-token for comment.
     * @param parentStatement comment's parent statement.
     * @return previous case-token or null if previous case-token is absent.
     */
    private static DetailAST getPrevCaseToken(DetailAST parentStatement) {
        final DetailAST prevCaseToken;
        final DetailAST parentBlock = parentStatement.getParent();
        if (parentBlock != null && parentBlock.getParent() != null
                && parentBlock.getParent().getPreviousSibling() != null
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
     * @param comment {@link TokenTypes#SINGLE_LINE_COMMENT single line comment}.
     * @param prevStmt previous code statement.
     * @param nextStmt next code statement.
     * @return true if comment and next code statement are indented at the same level.
     */
    private boolean areSameLevelIndented(DetailAST comment, DetailAST prevStmt,
                                                DetailAST nextStmt) {
        final boolean result;
        if (prevStmt == null) {
            result = comment.getColumnNo() == getLineStart(nextStmt.getLineNo());
        }
        else {
            result = comment.getColumnNo() == getLineStart(nextStmt.getLineNo())
                    || comment.getColumnNo() == getLineStart(prevStmt.getLineNo());
        }
        return result;
    }

    /**
     * Get a column number where a code starts.
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
        return !CommonUtils.hasWhitespaceBefore(commentColumnNo, targetSourceLine);
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
        final DetailAST prevStatement = getPrevStatementFromSwitchBlock(blockComment);

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
        return !CommonUtils.hasWhitespaceBefore(commentColumnNo, commentLine)
            || blockComment.getNextSibling().getLineNo() == blockComment.getLineNo();
    }
}
