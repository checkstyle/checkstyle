///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Ensures that catch parameters that are not used are declared as unnamed parameter.
 * </p>
 * <p>
 * Rationale:
 * </p>
 * <ul>
 *     <li>
 *         Improves code readability by clearly indicating which parameters are unused.
 *     </li>
 *     <li>
 *         Follows Java conventions for denoting unused parameters with an underscore ({@code _})
 *     </li>
 *     <li>
 *         Helps developers quickly identify parameters that are intentionally ignored,
 *         reducing potential confusion and aiding in code maintenance.
 *     </li>
 * </ul>
 * <p>
 * See the <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/unnamed-jls.html">
 * Java Language Specification</a> for more information about unnamed variables.
 * </p>
 * <p>
 * <b>Attention</b>: This check should be activated only on source code
 * that is compiled by jdk21 or higher;
 * unnamed catch parameters come out as the first preview in Java 21.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
     * An array of unacceptable parent of ast of type {@link TokenTypes#IDENT}.
     */
    private static final int[] UNACCEPTABLE_PARENT_OF_IDENT = {
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * Keeps tracks of the current catch parameter.
     */
    private CatchParameter catchParameter;

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
        catchParameter = null;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_CATCH) {
            visitCatchParameter(ast);
        }
        else {
            visitIdent(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        logUnusedCatchParameter(ast);
    }

    /**
     * Visit ast of type {@link TokenTypes#LITERAL_CATCH}
     * and set the catch parameter.
     *
     * @param catchAst token representing {@link TokenTypes#LITERAL_CATCH}
     */
    private void visitCatchParameter(DetailAST catchAst) {
        final DetailAST catchParameterAst = catchAst.findFirstToken(TokenTypes.PARAMETER_DEF);
        final String catchParameterName =
                catchParameterAst.findFirstToken(TokenTypes.IDENT).getText();

        if (!"_".equals(catchParameterName)) {
            catchParameter = new CatchParameter(catchParameterName,
                                    catchAst, catchParameterAst);
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}
     * and check if it is a candidate for a catch parameter usage.
     *
     * @param identAST token representing {@link TokenTypes#IDENT}
     */
    private void visitIdent(DetailAST identAST) {
        final boolean isCatchParameter = identAST.getParent().getParent()
                                                .getType() == TokenTypes.LITERAL_CATCH;

        if (!isCatchParameter
                && (!TokenUtil.isOfType(identAST.getParent(), UNACCEPTABLE_PARENT_OF_IDENT)
                        || shouldCheckDotChild(identAST))) {
            checkIdentifier(identAST);
        }
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} is a child of a dot operator
     * and should be checked for usage.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} is a child of a dot operator
     *     and should be checked for usage.
     */
    private boolean shouldCheckDotChild(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.DOT
                && identAst == parent.getFirstChild();
    }

    /**
     * Check the given {@link TokenTypes#IDENT} and
     * register it as used if it is the catch parameter.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     */
    private void checkIdentifier(DetailAST identAst) {
        if (catchParameter != null
                && !isLeftHandSideValue(identAst)
                && identAst.getText().equals(catchParameter.getName())) {
            catchParameter.registerAsUsed();
        }
    }

    /**
     * Check if the given {@link TokenTypes#IDENT} is a left hand side value.
     *
     * @param identAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} is a left hand side value.
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Log the catch parameter if it is not used after
     * leaving its scope.
     *
     * @param scopeAst ast of the current scope
     */
    private void logUnusedCatchParameter(DetailAST scopeAst) {
        if (catchParameter != null
                && scopeAst == catchParameter.getScope()
                && !catchParameter.isUsed()) {

            log(catchParameter.getParameterDefAst(),
                    MSG_UNUSED_CATCH_PARAMETER,
                    catchParameter.getName());
        }
    }

    /**
     * Maintains information about the catch parameter.
     */
    private static final class CatchParameter {

        /**
         * The name of the catch parameter.
         */
        private final String name;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * The scope of the catch parameter is determined by the ast of type
         * {@link TokenTypes#LITERAL_CATCH}.
         */
        private final DetailAST scope;

        /**
         * Ast of type {@link TokenTypes#PARAMETER_DEF} to use it when logging.
         */
        private final DetailAST parameterDefAst;

        /**
         * Create a new catch parameter instance.
         *
         * @param name name of the catch parameter
         * @param scope scope of the catch parameter representing {@link TokenTypes#LITERAL_CATCH}
         * @param parameterDefAst ast of type {@link TokenTypes#PARAMETER_DEF}
         */
        private CatchParameter(String name, DetailAST scope, DetailAST parameterDefAst) {
            this.name = name;
            this.scope = scope;
            this.parameterDefAst = parameterDefAst;
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
         * Get the scope of the catch parameter.
         *
         * @return the scope of the catch parameter
         */
        private DetailAST getScope() {
            return scope;
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
         * Get the ast of type {@link TokenTypes#PARAMETER_DEF}.
         *
         * @return the ast of type {@link TokenTypes#PARAMETER_DEF}
         */
        private DetailAST getParameterDefAst() {
            return parameterDefAst;
        }
    }
}
