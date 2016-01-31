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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Restricts the number of statements per line to one.
 * <p>
 *     Rationale: It's very difficult to read multiple statements on one line.
 * </p>
 * <p>
 *     In the Java programming language, statements are the fundamental unit of
 *     execution. All statements except blocks are terminated by a semicolon.
 *     Blocks are denoted by open and close curly braces.
 * </p>
 * <p>
 *     OneStatementPerLineCheck checks the following types of statements:
 *     variable declaration statements, empty statements, assignment statements,
 *     expression statements, increment statements, object creation statements,
 *     'for loop' statements, 'break' statements, 'continue' statements,
 *     'return' statements, import statements.
 * </p>
 * <p>
 *     The following examples will be flagged as a violation:
 * </p>
 * <pre>
 *     //Each line causes violation:
 *     int var1; int var2;
 *     var1 = 1; var2 = 2;
 *     int var1 = 1; int var2 = 2;
 *     var1++; var2++;
 *     Object obj1 = new Object(); Object obj2 = new Object();
 *     import java.io.EOFException; import java.io.BufferedReader;
 *     ;; //two empty statements on the same line.
 *
 *     //Multi-line statements:
 *     int var1 = 1
 *     ; var2 = 2; //violation here
 *     int o = 1, p = 2,
 *     r = 5; int t; //violation here
 * </pre>
 *
 * @author Alexander Jesse
 * @author Oliver Burn
 * @author Andrei Selkin
 */
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
    private int lastStatementEnd = -1;

    /**
     * Hold the line-number where the last 'for-loop' statement ended.
     */
    private int forStatementEnd = -1;

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
    private int lambdaStatementEnd = -1;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.SEMI,
            TokenTypes.FOR_INIT,
            TokenTypes.FOR_ITERATOR,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        inForHeader = false;
        lastStatementEnd = -1;
        forStatementEnd = -1;
        isInLambda = false;
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
                forStatementEnd = -1;
                lambdaStatementEnd = -1;
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
     * @param ast semicolon to check
     */
    private void checkIfSemicolonIsInDifferentLineThanPrevious(DetailAST ast) {
        DetailAST currentStatement = ast;
        final boolean hasResourcesPrevSibling =
                currentStatement.getPreviousSibling() != null
                        && currentStatement.getPreviousSibling().getType() == TokenTypes.RESOURCES;
        if (!hasResourcesPrevSibling && isMultilineStatement(currentStatement)) {
            currentStatement = ast.getPreviousSibling();
        }
        if (isInLambda) {
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
        else if (!inForHeader && isOnTheSameLine(currentStatement, lastStatementEnd,
                forStatementEnd, lambdaStatementEnd)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks whether two statements are on the same line.
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

    /**
     * Checks whether statement is multiline.
     * @param ast token for the current statement.
     * @return true if one statement is distributed over two or more lines.
     */
    private static boolean isMultilineStatement(DetailAST ast) {
        final boolean multiline;
        if (ast.getPreviousSibling() == null) {
            multiline = false;
        }
        else {
            final DetailAST prevSibling = ast.getPreviousSibling();
            multiline = prevSibling.getLineNo() != ast.getLineNo()
                    && ast.getParent() != null;
        }
        return multiline;
    }
}
