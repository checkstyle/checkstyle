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
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Ensures that lambda parameters that are not used are declared as an unnamed variable.
 * </div>
 *
 * <p>
 * Rationale:
 * </p>
 * <ul>
 *     <li>
 *         Improves code readability by clearly indicating which parameters are unused.
 *     </li>
 *     <li>
 *         Follows Java conventions for denoting unused parameters with an underscore ({@code _}).
 *     </li>
 * </ul>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/unnamed-jls.html">
 * Java Language Specification</a> for more information about unnamed variables.
 * </p>
 *
 * <p>
 * <b>Attention</b>: Unnamed variables are available as a preview feature in Java 21,
 * and became an official part of the language in Java 22.
 * This check should be activated only on source code which meets those requirements.
 * </p>
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
 * {@code unused.lambda.parameter}
 * </li>
 * </ul>
 *
 * @since 10.18.0
 */
@FileStatefulCheck
public class UnusedLambdaParameterShouldBeUnnamedCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LAMBDA_PARAMETER = "unused.lambda.parameter";

    /**
     * Invalid parents of the lambda parameter identifier.
     * These are tokens that can not be parents for a lambda
     * parameter identifier.
     */
    private static final int[] INVALID_LAMBDA_PARAM_IDENT_PARENTS = {
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * Keeps track of the lambda parameters in a block.
     */
    private final Deque<LambdaParameterDetails> lambdaParameters = new ArrayDeque<>();

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
            TokenTypes.LAMBDA,
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lambdaParameters.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LAMBDA) {
            final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);
            if (parameters != null) {
                // we have multiple lambda parameters
                TokenUtil.forEachChild(parameters, TokenTypes.PARAMETER_DEF, parameter -> {
                    final DetailAST identifierAst = parameter.findFirstToken(TokenTypes.IDENT);
                    final LambdaParameterDetails lambdaParameter =
                            new LambdaParameterDetails(ast, identifierAst);
                    lambdaParameters.push(lambdaParameter);
                });
            }
            else if (ast.getChildCount() != 0) {
                // we are not switch rule and have a single parameter
                final LambdaParameterDetails lambdaParameter =
                            new LambdaParameterDetails(ast, ast.findFirstToken(TokenTypes.IDENT));
                lambdaParameters.push(lambdaParameter);
            }
        }
        else if (isLambdaParameterIdentifierCandidate(ast) && !isLeftHandOfAssignment(ast)) {
            // we do not count reassignment as usage
            lambdaParameters.stream()
                    .filter(parameter -> parameter.getName().equals(ast.getText()))
                    .findFirst()
                    .ifPresent(LambdaParameterDetails::registerAsUsed);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        while (lambdaParameters.peek() != null
                    && ast.equals(lambdaParameters.peek().enclosingLambda)) {

            final Optional<LambdaParameterDetails> unusedLambdaParameter =
                    Optional.ofNullable(lambdaParameters.peek())
                            .filter(parameter -> !parameter.isUsed())
                            .filter(parameter -> !"_".equals(parameter.getName()));

            unusedLambdaParameter.ifPresent(parameter -> {
                log(parameter.getIdentifierAst(),
                        MSG_UNUSED_LAMBDA_PARAMETER,
                        parameter.getName());
            });
            lambdaParameters.pop();
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}
     * and check if it is a candidate for a lambda parameter identifier.
     *
     * @param identifierAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} could be a lambda parameter identifier
     */
    private static boolean isLambdaParameterIdentifierCandidate(DetailAST identifierAst) {
        // we should ignore the ident if it is in the lambda parameters declaration
        final boolean isLambdaParameterDeclaration =
                identifierAst.getParent().getType() == TokenTypes.LAMBDA
                    || identifierAst.getParent().getType() == TokenTypes.PARAMETER_DEF;

        return !isLambdaParameterDeclaration
                 && (hasValidParentToken(identifierAst) || isMethodInvocation(identifierAst));
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} has a valid parent token.
     * A valid parent token is a token that can be a parent for a lambda parameter identifier.
     *
     * @param identifierAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} has a valid parent token
     */
    private static boolean hasValidParentToken(DetailAST identifierAst) {
        return !TokenUtil.isOfType(identifierAst.getParent(), INVALID_LAMBDA_PARAM_IDENT_PARENTS);
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} is a child of a dot operator
     * and is a candidate for lambda parameter.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} is a child of a dot operator
     *     and a candidate for lambda parameter.
     */
    private static boolean isMethodInvocation(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.DOT
                && identAst.equals(parent.getFirstChild());
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} is a left hand side value.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} is a left hand side value.
     */
    private static boolean isLeftHandOfAssignment(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.ASSIGN
                && !identAst.equals(parent.getLastChild());
    }

    /**
     * Maintains information about the lambda parameter.
     */
    private static final class LambdaParameterDetails {

        /**
         * Ast of type {@link TokenTypes#LAMBDA} enclosing the lambda
         * parameter.
         */
        private final DetailAST enclosingLambda;

        /**
         * Ast of type {@link TokenTypes#IDENT} of the given
         * lambda parameter.
         */
        private final DetailAST identifierAst;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * Create a new lambda parameter instance.
         *
         * @param enclosingLambda ast of type {@link TokenTypes#LAMBDA}
         * @param identifierAst ast of type {@link TokenTypes#IDENT}
         */
        private LambdaParameterDetails(DetailAST enclosingLambda, DetailAST identifierAst) {
            this.enclosingLambda = enclosingLambda;
            this.identifierAst = identifierAst;
        }

        /**
         * Register the lambda parameter as used.
         */
        private void registerAsUsed() {
            used = true;
        }

        /**
         * Get the name of the lambda parameter.
         *
         * @return the name of the lambda parameter
         */
        private String getName() {
            return identifierAst.getText();
        }

        /**
         * Get ast of type {@link TokenTypes#IDENT} of the given
         * lambda parameter.
         *
         * @return ast of type {@link TokenTypes#IDENT} of the given lambda parameter
         */
        private DetailAST getIdentifierAst() {
            return identifierAst;
        }

        /**
         * Check if the lambda parameter is used.
         *
         * @return true if the lambda parameter is used
         */
        private boolean isUsed() {
            return used;
        }
    }
}
