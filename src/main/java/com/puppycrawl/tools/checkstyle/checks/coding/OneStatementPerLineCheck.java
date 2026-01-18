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
     * Hold the line-number where the last statement ended.
     */
    private int lastStatementEnd;

    /**
     * Hold the column where the last statement ended.
     */
    private int lastStatementColumn;

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
     * Hold the line-number where the last lambda expression ended.
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
            TokenTypes.EMPTY_STAT,
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
            case TokenTypes.SEMI, TokenTypes.EMPTY_STAT ->
                checkIfSemicolonIsInDifferentLineThanPrevious(ast);
            case TokenTypes.FOR_ITERATOR -> forStatementEnd = ast.getLineNo();
            case TokenTypes.LAMBDA -> isInLambda = true;
            default -> inForHeader = true;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI, TokenTypes.EMPTY_STAT -> {
                lastStatementEnd = ast.getLineNo();
                lastStatementColumn = ast.getColumnNo();
                forStatementEnd = 0;
                lambdaStatementEnd = 0;
            }
            case TokenTypes.FOR_ITERATOR -> inForHeader = false;
            case TokenTypes.LAMBDA -> {
                isInLambda = false;
                DetailAST currentAst = ast;
                while (currentAst != null) {
                    lambdaStatementEnd = currentAst.getLineNo();
                    currentAst = currentAst.getLastChild();
                }
            }
            default -> {
                // do nothing
            }
        }
    }

    /**
     * Checks if given semicolon is in different line than previous.
     *
     * @param ast semicolon to check
     */
    private void checkIfSemicolonIsInDifferentLineThanPrevious(DetailAST ast) {
        final DetailAST previousSibling = ast.getPreviousSibling();
        boolean validStatement = true;
        if (isResource(ast.getParent())) {
            validStatement = checkResourceVariable(ast);
        }
        else if (isInLambda) {
            validStatement = checkLambda(ast, previousSibling);
        }
        else if (!inForHeader) {
            validStatement = isValidStatement(ast, previousSibling);
        }
        if (!validStatement) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks whether the current statement is placed on a separate line
     * from the previous statement.
     *
     * @param ast semicolon token representing the end of the current statement
     * @param previousSibling previous sibling of the semicolon in the AST
     * @return {@code true} if the current statement starts on a different line
     *         than the previous statement; {@code false} otherwise
     */
    private boolean isValidStatement(DetailAST ast, DetailAST previousSibling) {
        final boolean validStatement;
        final boolean onPreviousLine = onPreviousLine(ast);
        if (onPreviousLine) {
            validStatement = false;
        }
        else if (previousSibling == null) {
            validStatement = true;
        }
        else {
            validStatement = !onPreviousLine(ast.getPreviousSibling());
        }
        return validStatement;
    }

    /**
     * Checks whether the current statement inside a lambda expression is placed
     * on a separate line from the previous statement within the same lambda body.
     *
     * @param ast semicolon token representing the end of the current statement
     *            inside the lambda
     * @param previousSibling previous sibling of the semicolon in the lambda AST
     * @return {@code true} if the current statement starts on a different line
     *         than the previous statement inside the lambda body;
     *         {@code false} otherwise
     */
    private boolean checkLambda(DetailAST ast, DetailAST previousSibling) {
        return inForHeader
                || isValidStatement(ast, previousSibling);
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
     * Checks whether the ast is on line with another statement.
     *
     * @param ast token for the current statement.
     * @return true if two statements are on the same line.
     */
    private boolean onPreviousLine(DetailAST ast) {
        return lastStatementEnd == ast.getLineNo() && forStatementEnd != ast.getLineNo()
                && lambdaStatementEnd != ast.getLineNo() && lastStatementColumn < ast.getColumnNo();
    }

}
