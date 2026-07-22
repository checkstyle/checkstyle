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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that expression lambdas are used instead of single-line block lambdas
 * where possible.
 * </div>
 *
 * <p>
 * Rationale: According to the OpenJDK Java Style Guidelines (and general
 * modern Java conventions), expression lambdas are preferred over single-line
 * block lambdas for readability and conciseness.
 * </p>
 *
 * <p>
 * A single-line block lambda is a lambda whose body is a block ({@code {...}})
 * that fits on a single line and contains only one statement that could be
 * written as an expression lambda.
 * </p>
 *
 * <p>
 * Example of a violation:
 * </p>
 * <pre>
 * Runnable r = () -&gt; { System.out.println("Hello"); };
 * </pre>
 *
 * <p>
 * Preferred form:
 * </p>
 * <pre>
 * Runnable r = () -&gt; System.out.println("Hello");
 * </pre>
 *
 * @since 8.46
 */
@StatelessCheck
public class ExpressionOverBlockLambdaCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "expression.over.block.lambda";

    /**
     * Creates a new {@code ExpressionOverBlockLambdaCheck} instance.
     */
    public ExpressionOverBlockLambdaCheck() {
        // no code by default
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
        return new int[] {TokenTypes.LAMBDA};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isSwitchRuleLambda(ast)) {
            final DetailAST body = ast.getLastChild();
            if (body.getType() == TokenTypes.SLIST
                    && isSingleLineLambda(ast)) {
                final DetailAST statement =
                        findSingleStatement(body);
                if (statement != null
                        && isConvertibleToExpressionLambda(
                                statement)) {
                    log(ast, MSG_KEY);
                }
            }
        }
    }

    /**
     * Checks if the lambda is a switch rule lambda.
     *
     * @param lambda the lambda AST node
     * @return true if the lambda is part of a switch rule
     */
    private static boolean isSwitchRuleLambda(DetailAST lambda) {
        return lambda.getParent().getType() == TokenTypes.SWITCH_RULE;
    }

    /**
     * Checks if a lambda is single-line.
     *
     * @param lambda the lambda AST node
     * @return true if the lambda fits on a single line
     */
    private static boolean isSingleLineLambda(DetailAST lambda) {
        final DetailAST lastLambdaToken = getLastLambdaToken(lambda);
        return TokenUtil.areOnSameLine(lambda, lastLambdaToken);
    }

    /**
     * Gets the last token in a lambda.
     *
     * @param lambda the lambda AST node
     * @return the last token in the lambda
     */
    private static DetailAST getLastLambdaToken(DetailAST lambda) {
        DetailAST node = lambda;
        do {
            node = node.getLastChild();
        } while (node.getLastChild() != null);
        return node;
    }

    /**
     * Finds the single statement in a block lambda body, or returns null
     * if there are zero or multiple statements.
     *
     * @param slist the SLIST node (lambda body)
     * @return the single statement, or null if not exactly one
     */
    private static DetailAST findSingleStatement(DetailAST slist) {
        DetailAST singleStatement = null;
        for (DetailAST child = slist.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            final int type = child.getType();
            if (type == TokenTypes.RCURLY
                    || type == TokenTypes.SEMI) {
                continue;
            }
            if (singleStatement != null) {
                singleStatement = null;
                break;
            }
            singleStatement = child;
        }
        return singleStatement;
    }

    /**
     * Checks if the statement in a block lambda can be rewritten
     * as an expression lambda.
     *
     * @param statement the statement node
     * @return true if the statement can be converted to an expression lambda
     */
    private static boolean isConvertibleToExpressionLambda(DetailAST statement) {
        boolean convertible = false;
        if (statement.getType() == TokenTypes.EXPR) {
            convertible = true;
        }
        else if (statement.getType() == TokenTypes.LITERAL_RETURN) {
            convertible = hasReturnExpression(statement);
        }
        return convertible;
    }

    /**
     * Checks if a return statement has an expression (not bare return).
     *
     * @param literalReturn the LITERAL_RETURN node
     * @return true if the return statement has an expression
     */
    private static boolean hasReturnExpression(DetailAST literalReturn) {
        boolean hasExpression = false;
        for (DetailAST child = literalReturn.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.EXPR) {
                hasExpression = true;
                break;
            }
        }
        return hasExpression;
    }

}
