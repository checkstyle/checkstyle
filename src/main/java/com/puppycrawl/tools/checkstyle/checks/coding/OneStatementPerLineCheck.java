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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
 * <ul>
 * <li>
 * Property {@code treatTryResourcesAsStatement} - Enable resources processing.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code multiple.statements.line}
 * </li>
 * </ul>
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

    /**
     * Counts number of semicolons in nested lambdas.
     */
    private final Deque<Integer> countOfSemiInLambda = new ArrayDeque<>();

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
     * Holds if current token is inside lambda.
     */
    private boolean isInLambda;

    /**
     * Hold the line-number where the last lambda statement ended.
     */
    private int lambdaStatementEnd;

    /**
     * Hold the line-number where the last resource variable statement ended.
     */
    private int lastVariableResourceStatementEnd;

    /**
     * Enable resources processing.
     */
    private boolean treatTryResourcesAsStatement;

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
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lastStatementEnd = 0;
        lastVariableResourceStatementEnd = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI:
                checkIfSemicolonIsInDifferentLineThanPrevious(ast);
                break;
            case TokenTypes.FOR_ITERATOR:
                forStatementEnd = ast.getLineNo();
                break;
            case TokenTypes.LAMBDA:
                isInLambda = true;
                countOfSemiInLambda.push(0);
                break;
            default:
                inForHeader = true;
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI:
                lastStatementEnd = ast.getLineNo();
                forStatementEnd = 0;
                lambdaStatementEnd = 0;
                break;
            case TokenTypes.FOR_ITERATOR:
                inForHeader = false;
                break;
            case TokenTypes.LAMBDA:
                countOfSemiInLambda.pop();
                if (countOfSemiInLambda.isEmpty()) {
                    isInLambda = false;
                }
                lambdaStatementEnd = ast.getLineNo();
                break;
            default:
                break;
        }
    }

    /**
     * Checks if given semicolon is in different line than previous.
     *
     * @param ast semicolon to check
     */
    private void checkIfSemicolonIsInDifferentLineThanPrevious(DetailAST ast) {
        DetailAST currentStatement = ast;
        final DetailAST previousSibling = ast.getPreviousSibling();
        final boolean isUnnecessarySemicolon = previousSibling == null
            || previousSibling.getType() == TokenTypes.RESOURCES
            || ast.getParent().getType() == TokenTypes.COMPILATION_UNIT;
        if (!isUnnecessarySemicolon) {
            currentStatement = ast.getPreviousSibling();
        }
        if (isInLambda) {
            checkLambda(ast, currentStatement);
        }
        else if (isResource(ast.getParent())) {
            checkResourceVariable(ast);
        }
        else if (!inForHeader && isOnTheSameLine(currentStatement, lastStatementEnd,
                forStatementEnd, lambdaStatementEnd)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks semicolon placement in lambda.
     *
     * @param ast semicolon to check
     * @param currentStatement current statement
     */
    private void checkLambda(DetailAST ast, DetailAST currentStatement) {
        int countOfSemiInCurrentLambda = countOfSemiInLambda.pop();
        countOfSemiInCurrentLambda++;
        countOfSemiInLambda.push(countOfSemiInCurrentLambda);
        if (!inForHeader && countOfSemiInCurrentLambda > 1
                && isOnTheSameLine(currentStatement,
                lastStatementEnd, forStatementEnd,
                lambdaStatementEnd)) {
            log(ast, MSG_KEY);
        }
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
     * Checks resource variable.
     *
     * @param currentStatement current statement
     */
    private void checkResourceVariable(DetailAST currentStatement) {
        if (treatTryResourcesAsStatement) {
            final DetailAST nextNode = currentStatement.getNextSibling();
            if (currentStatement.getPreviousSibling().findFirstToken(TokenTypes.ASSIGN) != null) {
                lastVariableResourceStatementEnd = currentStatement.getLineNo();
            }
            if (nextNode.findFirstToken(TokenTypes.ASSIGN) != null
                && nextNode.getLineNo() == lastVariableResourceStatementEnd) {
                log(currentStatement, MSG_KEY);
            }
        }
    }

    /**
     * Checks whether two statements are on the same line.
     *
     * @param ast token for the current statement.
     * @param lastStatementEnd the line-number where the last statement ended.
     * @param forStatementEnd the line-number where the last 'for-loop'
     *                        statement ended.
     * @param lambdaStatementEnd the line-number where the last lambda
     *                        statement ended.
     * @return true if two statements are on the same line.
     */
    private static boolean isOnTheSameLine(DetailAST ast, int lastStatementEnd,
                                           int forStatementEnd, int lambdaStatementEnd) {
        return lastStatementEnd == ast.getLineNo() && forStatementEnd != ast.getLineNo()
                && lambdaStatementEnd != ast.getLineNo();
    }

}
