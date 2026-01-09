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
     * Tracks the current nesting depth of lambda expressions.
     */
    private int nestingDepthLambda;

    /**
     * Stack of statement coordinates for nested lambdas and anonymous classes.
     * Used to isolate validation to the current nesting depth.
     */
    private final Deque<Integer> nestingScope = new ArrayDeque<>();

    /**
     * Hold the line-number where the last anonymous class or lambda ended.
     */
    private int lastStatementInNesting;

    /**
     * Tracks the current nesting depth of anonymous class.
     */
    private int nestingDepthAnonymous;

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
            TokenTypes.OBJBLOCK,
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
            case TokenTypes.LAMBDA -> {
                nestingScope.push(lastStatementEnd);
                nestingDepthLambda++;
            }
            case TokenTypes.OBJBLOCK -> {
                final boolean isAnonymous = ast.getParent().getType() == TokenTypes.LITERAL_NEW;
                if (isAnonymous) {
                    nestingScope.push(lastStatementEnd);
                    nestingDepthAnonymous++;
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
                lastStatementColumn = ast.getColumnNo();
                forStatementEnd = 0;
                lastStatementInNesting = 0;
            }
            case TokenTypes.FOR_ITERATOR -> inForHeader = false;
            case TokenTypes.LAMBDA -> {
                DetailAST currentAst = ast;
                while (currentAst != null) {
                    lastStatementInNesting = currentAst.getLineNo();
                    currentAst = currentAst.getLastChild();
                }
                nestingDepthLambda--;
            }
            case TokenTypes.OBJBLOCK -> {

                if (nestingDepthAnonymous > 0) {
                    DetailAST currentAst = ast;
                    while (currentAst != null) {
                        lastStatementInNesting = currentAst.getLineNo();
                        currentAst = currentAst.getLastChild();
                    }
                    nestingDepthAnonymous--;
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
        boolean validStatement = true;
        if (isResource(ast.getParent())) {
            validStatement = checkResourceVariable(ast);
        }
        else if (!inForHeader) {
            validStatement = isValidStatement(ast);
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
     * @return {@code true} if the current statement starts on a different line
     *         than the previous statement; {@code false} otherwise
     */
    private boolean isValidStatement(DetailAST ast) {
        final DetailAST statementStart = getStatementStart(ast);
        final boolean validStatement;
        final boolean onPreviousLine = onPreviousLine(ast);
        if (onPreviousLine) {
            validStatement = false;
        }
        else if (statementStart == null) {
            validStatement = true;
        }
        else {
            validStatement = !isStatementStartOnPreviousLine(statementStart);
        }
        return validStatement;
    }

    /**
     * Returns the starting node of the statement, or the previous statement's
     * start if the current one is an empty statement.
     *
     * @param ast the SEMI token.
     * @return the start of the associated statement or the previous sibling.
     */
    private static DetailAST getStatementStart(DetailAST ast) {
        final DetailAST statementStart;
        final DetailAST parent = ast.getParent();
        final boolean statementBeginFromParent =
                parent.getType() == TokenTypes.VARIABLE_DEF
                        || parent.getType() == TokenTypes.IMPORT
                        || parent.getType() == TokenTypes.STATIC_IMPORT
                        || parent.getType() == TokenTypes.MODULE_IMPORT;
        if (statementBeginFromParent) {
            statementStart = ast.getParent();
        }
        else {
            statementStart = ast.getPreviousSibling();
        }
        return statementStart;
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
     * Checks whether the ast is on the line with another statement.
     *
     * @param ast token for the current statement.
     * @return true if two statements are on the same line.
     */
    private boolean onPreviousLine(DetailAST ast) {
        return lastStatementEnd == ast.getLineNo() && forStatementEnd != ast.getLineNo()
                && lastStatementInNesting != ast.getLineNo();

    }

    /**
     * Checks whether ast is on line with another statement.
     *
     * @param ast token for the current statement.
     * @return true if two statements are on the same line.
     */
    private boolean isStatementStartOnPreviousLine(DetailAST ast) {
        final int lastStatementInScope;
        final int lastStatementColumnInScope;
        if (nestingDepthLambda + nestingDepthAnonymous == nestingScope.size()) {
            lastStatementInScope = lastStatementEnd;
            lastStatementColumnInScope = lastStatementColumn;
        }
        else {
            lastStatementInScope = nestingScope.pop();
            lastStatementColumnInScope = 0;
        }
        return lastStatementInScope == ast.getLineNo() && forStatementEnd != ast.getLineNo()
                && lastStatementColumnInScope < ast.getColumnNo();
    }

}
