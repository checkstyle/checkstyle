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
 * Ensures that catch parameters that are not used are declared as an unnamed variable.
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
 * <b>Attention</b>: This check should be activated only on source code
 * that is compiled by jdk21 or higher;
 * unnamed catch parameters came out as the first preview in Java 21.
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
 * {@code unused.catch.parameter}
 * </li>
 * </ul>
 *
 * @since 10.18.0
 *
 */

@FileStatefulCheck
public class UnusedCatchParameterShouldBeUnnamedCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_CATCH_PARAMETER = "unused.catch.parameter";

    /**
     * Invalid parents of the catch parameter identifier.
     */
    private static final int[] INVALID_CATCH_PARAM_IDENT_PARENTS = {
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * Keeps track of the catch parameters in a block.
     */
    private final Deque<CatchParameterDetails> catchParameters = new ArrayDeque<>();

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
            TokenTypes.LITERAL_CATCH,
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        catchParameters.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_CATCH) {
            final CatchParameterDetails catchParameter = new CatchParameterDetails(ast);
            catchParameters.push(catchParameter);
        }
        else if (isCatchParameterIdentifierCandidate(ast) && !isLeftHandOfAssignment(ast)) {
            // we do not count reassignment as usage
            catchParameters.stream()
                    .filter(parameter -> parameter.getName().equals(ast.getText()))
                    .findFirst()
                    .ifPresent(CatchParameterDetails::registerAsUsed);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_CATCH) {
            final Optional<CatchParameterDetails> unusedCatchParameter =
                    Optional.ofNullable(catchParameters.peek())
                            .filter(parameter -> !parameter.isUsed())
                            .filter(parameter -> !"_".equals(parameter.getName()));

            unusedCatchParameter.ifPresent(parameter -> {
                log(parameter.getParameterDefinition(),
                        MSG_UNUSED_CATCH_PARAMETER,
                        parameter.getName());
            });
            catchParameters.pop();
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}
     * and check if it is a candidate for a catch parameter identifier.
     *
     * @param identifierAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} could be a catch parameter identifier
     */
    private static boolean isCatchParameterIdentifierCandidate(DetailAST identifierAst) {
        // we should ignore the ident if it is in the exception declaration
        return identifierAst.getParent().getParent().getType() != TokenTypes.LITERAL_CATCH
            && (!TokenUtil.isOfType(identifierAst.getParent(), INVALID_CATCH_PARAM_IDENT_PARENTS)
                 || isMethodInvocation(identifierAst));
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} is a child of a dot operator
     * and is a candidate for catch parameter.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} is a child of a dot operator
     *     and a candidate for catch parameter.
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
     * Maintains information about the catch parameter.
     */
    private static final class CatchParameterDetails {

        /**
         * The name of the catch parameter.
         */
        private final String name;

        /**
         * Ast of type {@link TokenTypes#PARAMETER_DEF} to use it when logging.
         */
        private final DetailAST parameterDefinition;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * Create a new catch parameter instance.
         *
         * @param enclosingCatchClause ast of type {@link TokenTypes#LITERAL_CATCH}
         */
        private CatchParameterDetails(DetailAST enclosingCatchClause) {
            parameterDefinition =
                    enclosingCatchClause.findFirstToken(TokenTypes.PARAMETER_DEF);
            name = parameterDefinition.findFirstToken(TokenTypes.IDENT).getText();
        }

        /**
         * Register the catch parameter as used.
         */
        private void registerAsUsed() {
            used = true;
        }

        /**
         * Get the name of the catch parameter.
         *
         * @return the name of the catch parameter
         */
        private String getName() {
            return name;
        }

        /**
         * Check if the catch parameter is used.
         *
         * @return true if the catch parameter is used
         */
        private boolean isUsed() {
            return used;
        }

        /**
         * Get the parameter definition token of the catch parameter
         * represented by ast of type {@link TokenTypes#PARAMETER_DEF}.
         *
         * @return the ast of type {@link TokenTypes#PARAMETER_DEF}
         */
        private DetailAST getParameterDefinition() {
            return parameterDefinition;
        }
    }
}
