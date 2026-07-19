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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that there is only one statement per line.
 * </div>
 *
 * <p>
 * Rationale: It's very difficult to read multiple statements on one line.
 * </p>
 *
 * <p>
 * In the Java programming language, statements are the fundamental unit of
 * execution. All statements except blocks are terminated by a semicolon.
 * Blocks are denoted by open and close curly braces.
 * </p>
 *
 * <p>
 * OneStatementPerLineCheck checks the following types of statements:
 * variable declaration statements, empty statements, import statements,
 * assignment statements, expression statements, increment statements,
 * object creation statements, 'for loop' statements, 'break' statements,
 * 'continue' statements, 'return' statements, resources statements (optional).
 * </p>
 *
 * @since 5.3
 */
@FileStatefulCheck
public final class OneStatementPerLineCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.statements.line";

    /** Set of valid semi parent. */
    private static final Set<Integer> VALID_SEMI_PARENT = Set.of(
        TokenTypes.VARIABLE_DEF,
        TokenTypes.IMPORT,
        TokenTypes.STATIC_IMPORT,
        TokenTypes.MODULE_IMPORT,
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.LITERAL_CONTINUE
    );

    /**
     * Stack of statement line-number for nested lambdas and anonymous classes.
     * Used to isolate validation to the current nesting depth.
     */
    private final Deque<Integer> nestingScope = new ArrayDeque<>();

    /**
     * Hold the line-number where the last statement ended.
     */
    private int lastStatementEnd;

    /**
     * Hold the line-number where the last 'for-loop' statement ended.
     */
    private int forStatementEnd;

    /**
     * The for-header usually has 3 statements on one line, but THIS IS OK.
     */
    private boolean inForHeader;

    /**
     * Hold the line-number where the last resource variable statement ended.
     */
    private int lastVariableResourceStatementEnd;

    /**
     * Enable resources processing.
     */
    private boolean treatTryResourcesAsStatement;

    /**
     * Hold the line number of the last statement in the current nesting scope.
     */
    private int lastStatementInCurrentScope;

    /**
     * The first statement of lambda or anonymous class shouldn't be violation.
     */
    private boolean isFirstStatementOfLambdaOrAnonymous;

    /**
     * Creates a new {@code OneStatementPerLineCheck} instance.
     */
    public OneStatementPerLineCheck() {
        // no code by default
    }

    /**
     * Setter to enable resources processing.
     *
     * @param treatTryResourcesAsStatement user's value of treatTryResourcesAsStatement.
     * @since 8.23
     */
    public void setTreatTryResourcesAsStatement(boolean treatTryResourcesAsStatement) {
        this.treatTryResourcesAsStatement = treatTryResourcesAsStatement;
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
        return new int[] {
            TokenTypes.SEMI,
            TokenTypes.FOR_INIT,
            TokenTypes.FOR_ITERATOR,
            TokenTypes.LAMBDA,
            TokenTypes.EMPTY_STAT,
            TokenTypes.OBJBLOCK,
            TokenTypes.METHOD_CALL,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lastStatementEnd = 0;
        lastVariableResourceStatementEnd = 0;
        lastStatementInCurrentScope = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI, TokenTypes.EMPTY_STAT ->
                checkIfSemicolonIsInDifferentLineThanPrevious(ast);
            case TokenTypes.FOR_ITERATOR -> forStatementEnd = ast.getLineNo();
            case TokenTypes.LAMBDA, TokenTypes.OBJBLOCK -> {
                if (ast.getType() == TokenTypes.LAMBDA
                        || ast.getParent().getType() == TokenTypes.LITERAL_NEW) {
                    nestingScope.push(lastStatementEnd);
                    isFirstStatementOfLambdaOrAnonymous = true;
                }
            }
            case TokenTypes.METHOD_CALL -> nestingScope.push(lastStatementEnd);
            case TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
                 TokenTypes.RECORD_DEF, TokenTypes.ANNOTATION_DEF -> {
                DetailAST previousNode = ast.getPreviousSibling();
                if (previousNode != null) {
                    while (previousNode.hasChildren()) {
                        previousNode = previousNode.getLastChild();
                    }
                    if (TokenUtil.areOnSameLine(ast, previousNode)) {
                        logViolation(ast);
                    }
                }
            }
            default -> inForHeader = true;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI, TokenTypes.EMPTY_STAT -> {
                lastStatementEnd = ast.getLineNo();
                forStatementEnd = 0;
                isFirstStatementOfLambdaOrAnonymous = false;
                lastStatementInCurrentScope = lastStatementEnd;
            }
            case TokenTypes.FOR_ITERATOR -> inForHeader = false;
            case TokenTypes.LAMBDA, TokenTypes.OBJBLOCK -> {
                if (ast.getType() == TokenTypes.LAMBDA
                        || ast.getParent().getType() == TokenTypes.LITERAL_NEW) {
                    lastStatementInCurrentScope = nestingScope.pop();
                    isFirstStatementOfLambdaOrAnonymous = false;
                }
            }
            case TokenTypes.METHOD_CALL -> lastStatementInCurrentScope = nestingScope.pop();
            default -> {
                // do nothing
            }
        }
    }

    /**
     * Checks if statement of given semicolon is in different line
     * than previous statement.
     *
     * @param ast semicolon to check
     */
    private void checkIfSemicolonIsInDifferentLineThanPrevious(DetailAST ast) {
        boolean validStatement = true;
        DetailAST statementStart = getStatementStart(ast);
        if (isResource(ast.getParent())) {
            validStatement = checkResourceVariable(ast);
            statementStart = NullUtil.notNull(ast.getNextSibling());
        }
        else if (!inForHeader && !isFirstStatementOfLambdaOrAnonymous) {
            validStatement = isValidStatement(statementStart);
        }
        if (!validStatement) {
            logViolation(statementStart);
        }
    }

    /**
     * Logs a violation at the given AST node.
     *
     * @param violationNode token at which violation occurred.
     */
    private void logViolation(DetailAST violationNode) {
        log(violationNode, MSG_KEY);
    }

    /**
     * Checks whether the current statement is placed on a separate line
     * from the previous statement.
     *
     * @param statementStart token representing the start of the current statement
     * @return {@code true} if the current statement starts on a different line
     *         than the previous statement; {@code false} otherwise
     */
    private boolean isValidStatement(DetailAST statementStart) {
        boolean blockStatementBefore = false;
        DetailAST previousNode = statementStart.getPreviousSibling();
        final boolean isBlockStatement = TokenUtil.isOfType(previousNode,
                TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.ANNOTATION_DEF);
        if (isBlockStatement) {
            while (previousNode.hasChildren()) {
                previousNode = previousNode.getLastChild();
            }
            blockStatementBefore = TokenUtil.areOnSameLine(statementStart, previousNode);
        }
        return !blockStatementBefore && !isStatementStartOnPreviousLine(statementStart);
    }

    /**
     * Returns the starting node of the statement, or the previous statement's
     * start if the current one is an empty statement.
     *
     * @param ast the SEMI token.
     * @return the start of the associated statement or the previous sibling.
     */
    private DetailAST getStatementStart(DetailAST ast) {
        DetailAST statementStart = ast;
        final DetailAST parent = ast.getParent();
        final DetailAST previousSibling = ast.getPreviousSibling();
        final boolean validPreviousSibling = previousSibling != null
                && (previousSibling.getType() == TokenTypes.VARIABLE_DEF
                || previousSibling.getType() == TokenTypes.EXPR)
                && previousSibling.findFirstToken(TokenTypes.SEMI) == null;
        if (VALID_SEMI_PARENT.contains(parent.getType())) {
            statementStart = ast.getParent();
        }
        else if (validPreviousSibling) {
            if (previousSibling.getType() == TokenTypes.EXPR) {
                statementStart = getStartNodeInExpression(previousSibling);
            }
            else {
                statementStart = previousSibling;
            }
        }
        return statementStart;
    }

    /**
     * Checks whether the current statement represents a valid resource
     * declaration in a try-with-resources statement.
     *
     * @param currentStatement the statement to check
     * @return {@code true} if the statement is a valid resource declaration;
     *         {@code false} otherwise.
     */
    private boolean checkResourceVariable(DetailAST currentStatement) {
        boolean result = true;
        if (treatTryResourcesAsStatement) {
            final DetailAST nextNode = currentStatement.getNextSibling();
            if (currentStatement.getPreviousSibling().findFirstToken(TokenTypes.ASSIGN) != null) {
                lastVariableResourceStatementEnd = currentStatement.getLineNo();
            }
            result = nextNode.findFirstToken(TokenTypes.ASSIGN) == null
                    || nextNode.getLineNo() != lastVariableResourceStatementEnd;
        }
        return result;
    }

    /**
     * Finds the leftmost leaf node in the given expression's subtree.
     *
     * @param expression EXPR node.
     * @return the leftmost leaf {@code DetailAST} node
     */
    private DetailAST getStartNodeInExpression(DetailAST expression) {
        DetailAST child = expression;
        while (child.hasChildren()) {
            child = child.getFirstChild();
        }
        return child;
    }

    /**
     * Checks that given node is a resource.
     *
     * @param ast semicolon to check
     * @return true if node is a resource
     */
    private static boolean isResource(DetailAST ast) {
        return ast.getType() == TokenTypes.RESOURCES
                || ast.getType() == TokenTypes.RESOURCE_SPECIFICATION;
    }

    /**
     * Checks whether ast is on the line with another statement.
     *
     * @param ast token for the current statement.
     * @return true if two statements are on the same line.
     */
    private boolean isStatementStartOnPreviousLine(DetailAST ast) {
        return lastStatementInCurrentScope == ast.getLineNo() && forStatementEnd != ast.getLineNo();
    }

}
