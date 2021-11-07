////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a local variable is declared and/or assigned, but not used.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnusedLocalVariable&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public void method(int a) {
 *     int a = 10; // violation
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unused.local.var}
 * </li>
 * </ul>
 *
 * @since 9.2
 */
@FileStatefulCheck
public class UnusedLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LOCAL_VARIABLE = "unused.local.var";

    /** An array of increment tokens. */
    static final int[] INCREMENT_KINDS = {
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
        TokenTypes.INC,
        TokenTypes.DEC,
    };

    /** Keeps VariableDesc objects inside stack of declared variables. */
    private Deque<VariableDesc> variables;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST root) {
        variables = new ArrayDeque<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.DOT:
                final DetailAST firstDotOrMethodCallAst = getFirstDotOrMethodCallToken(ast);
                if (firstDotOrMethodCallAst == null
                        && ast.findFirstToken(TokenTypes.LITERAL_THIS) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_SUPER) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_CLASS) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_NEW) == null) {
                    checkIdentifierAst(ast.findFirstToken(TokenTypes.IDENT));
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK) {
                    final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                    final VariableDesc desc = new VariableDesc(
                            ident.getText(), ast, ast.getParent());
                    variables.push(desc);
                }
                break;
            case TokenTypes.IDENT:
                if (!TokenUtil.isOfType(ast.getParent().getType(),
                        TokenTypes.VARIABLE_DEF, TokenTypes.DOT)) {
                    checkIdentifierAst(ast);
                }
                break;
            case TokenTypes.SLIST:
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.SLIST) {
            int size = variables.size();
            while (size > 0 && variables.peek().getScopeOfVariable() == ast) {
                final VariableDesc variableDesc = variables.pop();
                if (!variableDesc.isUsedAgain()) {
                    final DetailAST currAst = variableDesc.getVariableAst();
                    log(currAst.getLineNo(), currAst.getColumnNo(),
                            MSG_UNUSED_LOCAL_VARIABLE, variableDesc.name);
                }
                size--;
            }
        }
    }

    /**
     * Gets the first DetailAst of type {@link TokenTypes#DOT} or
     * {@link TokenTypes#METHOD_CALL} token which is the
     * child of {@link TokenTypes#EXPR}.
     *
     * @param exprAst DetailAst of type {@link TokenTypes#EXPR}
     * @return DetailAst of type {@link TokenTypes#DOT} or {@link TokenTypes#METHOD_CALL}
     *         if present, otherwise null
     */
    private static DetailAST getFirstDotOrMethodCallToken(DetailAST exprAst) {
        final DetailAST result;
        final DetailAST dotToken = exprAst.findFirstToken(TokenTypes.DOT);
        if (dotToken == null) {
            result = exprAst.findFirstToken(TokenTypes.METHOD_CALL);
        }
        else {
            result = dotToken;
        }
        return result;
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     */
    private void checkIdentifierAst(DetailAST identAst) {
        for (VariableDesc variableDesc : variables) {
            if (identAst.getText().equals(variableDesc.getName())
                    && isRightHandSideValue(identAst)) {
                variableDesc.registerAsUsedAgain();
                break;
            }
        }
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is
     * used as right-hand side value. An identifier is being used as a right-hand side
     * value if it is not used as the left operand of an assignment nor as the
     * operand of a stand-alone increment
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return True if identAst is used as a right-hand side value otherwise false.
     */
    private static boolean isRightHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return !TokenUtil.isOfType(parent.getType(), INCREMENT_KINDS)
                && (parent.getType() != TokenTypes.ASSIGN
                || identAst != parent.findFirstToken(TokenTypes.IDENT)
                || identAst == parent.getLastChild());
    }

    /** Maintains information about the local variable. */
    private static final class VariableDesc {

        /** The name of the variable. */
        private final String name;

        /** DetailAst of type {@link TokenTypes#VARIABLE_DEF}. */
        private final DetailAST variableAst;

        /**
         * The scope of variable is determined by the DetailAst of type
         * {@link TokenTypes#SLIST} which is the parent of {@link VariableDesc#variableAst}.
         */
        private final DetailAST scopeOfVariable;

        /** Is the variable used again. */
        private boolean usedAgain;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         * @param variableAst DetailAst of type {@link TokenTypes#VARIABLE_DEF}
         * @param scopeOfVariable DetailAst of type {@link TokenTypes#SLIST} which is
         *                        the parent of {@link VariableDesc#variableAst}
         */
        /* package */ VariableDesc(String name, DetailAST variableAst, DetailAST scopeOfVariable) {
            this.name = name;
            this.variableAst = variableAst;
            this.scopeOfVariable = scopeOfVariable;
        }

        /**
         * Get the name of variable.
         *
         * @return name of variable
         */
        public String getName() {
            return name;
        }

        /**
         * Get the associated {@link VariableDesc#variableAst}.
         *
         * @return {@link VariableDesc#variableAst}
         */
        public DetailAST getVariableAst() {
            return variableAst;
        }

        /**
         * Is the variable used again or not.
         *
         * @return true if variable is used again else false;
         */
        public boolean isUsedAgain() {
            return usedAgain;
        }

        /** Register the variable as used again. */
        public void registerAsUsedAgain() {
            this.usedAgain = true;
        }

        /**
         * Get the associated {@link VariableDesc#scopeOfVariable}.
         *
         * @return {@link VariableDesc#scopeOfVariable}.
         */
        public DetailAST getScopeOfVariable() {
            return scopeOfVariable;
        }
    }
}
