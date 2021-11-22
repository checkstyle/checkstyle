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
 * public void method(int b) {
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

    /**
     * An array of increment and decrement tokens.
     */
    private static final int[] INCREMENT_AND_DECREMENT_TOKENS = {
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
        TokenTypes.INC,
        TokenTypes.DEC,
    };

    /**
     * An array of scope tokens.
     */
    private static final int[] SCOPES = {
        TokenTypes.SLIST,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE,
    };

    /**
     * An array of unacceptable children of ast of type {@link TokenTypes#DOT}.
     */
    private static final int[] UNACCEPTABLE_CHILD_OF_DOT = {
        TokenTypes.DOT,
        TokenTypes.METHOD_CALL,
        TokenTypes.LITERAL_NEW,
        TokenTypes.LITERAL_SUPER,
        TokenTypes.LITERAL_CLASS,
        TokenTypes.LITERAL_THIS,
    };

    /**
     * Keeps VariableDesc objects inside stack of declared variables.
     */
    private Deque<VariableDesc> variables;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
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
                final boolean checkIdentToken = !TokenUtil.findFirstTokenByPredicate(ast,
                                dotAst -> {
                                    return TokenUtil.isOfType(dotAst.getType(),
                                            UNACCEPTABLE_CHILD_OF_DOT);
                                })
                        .isPresent();
                if (checkIdentToken) {
                    checkIdentifierAst(ast.findFirstToken(TokenTypes.IDENT));
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK) {
                    final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                    final VariableDesc desc = new VariableDesc(ident.getText(), ast);
                    setScopeOfVariable(ast, desc);
                    variables.push(desc);
                }
                break;
            case TokenTypes.IDENT:
                if (!TokenUtil.isOfType(ast.getParent().getType(),
                        TokenTypes.VARIABLE_DEF, TokenTypes.DOT,
                        TokenTypes.LITERAL_NEW, TokenTypes.PATTERN_VARIABLE_DEF)) {
                    checkIdentifierAst(ast);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast.getType(), SCOPES)) {
            while (!variables.isEmpty() && variables.peek().getScope() == ast) {
                final VariableDesc variableDesc = variables.pop();
                if (!variableDesc.isUsed()) {
                    final DetailAST currAst = variableDesc.getVariableAst();
                    log(currAst, MSG_UNUSED_LOCAL_VARIABLE, variableDesc.name);
                }
            }
        }
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     */
    private void checkIdentifierAst(DetailAST identAst) {
        for (VariableDesc variableDesc : variables) {
            if (identAst.getText().equals(variableDesc.getName())
                    && !isLeftHandSideValue(identAst)) {
                variableDesc.registerAsUsed();
                break;
            }
        }
    }

    /**
     * Set the scope of variable.
     *
     * @param variableDef DetailAst of type {@link TokenTypes#VARIABLE_DEF}
     * @param variableDesc information about the local variable
     */
    private static void setScopeOfVariable(DetailAST variableDef, VariableDesc variableDesc) {
        final DetailAST parentAst = variableDef.getParent();
        if (parentAst.getType() == TokenTypes.SLIST) {
            variableDesc.setScope(parentAst);
        }
        else {
            final DetailAST grandParent = parentAst.getParent();
            final DetailAST scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);
            if (scopeAst == null) {
                variableDesc.setScope(grandParent);
            }
            else {
                variableDesc.setScope(scopeAst);
            }
        }
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is
     * used as left-hand side value. An identifier is being used as a left-hand side
     * value if it is used as the left operand of an assignment or as the
     * operand of a stand-alone increment
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return True if identAst is used as a left-hand side value otherwise false.
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        final DetailAST grandParent = parent.getParent();
        return TokenUtil.isOfType(parent.getType(), INCREMENT_AND_DECREMENT_TOKENS)
                && TokenUtil.isOfType(grandParent.getType(), TokenTypes.EXPR)
                && !TokenUtil.isOfType(grandParent.getParent().getType(),
                TokenTypes.ELIST, TokenTypes.INDEX_OP)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Maintains information about the local variable.
     */
    private static final class VariableDesc {

        /**
         * The name of the variable.
         */
        private final String name;

        /**
         * DetailAst of type {@link TokenTypes#VARIABLE_DEF}.
         */
        private final DetailAST variableAst;

        /**
         * The scope of variable is determined by the DetailAst of type
         * {@link TokenTypes#SLIST} which is enclosing {@link VariableDesc#variableAst}.
         */
        private DetailAST scope;

        /**
         * Is the variable used again.
         */
        private boolean used;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name        name of the variable
         * @param variableAst DetailAst of type {@link TokenTypes#VARIABLE_DEF}
         */
        /* package */ VariableDesc(String name, DetailAST variableAst) {
            this.name = name;
            this.variableAst = variableAst;
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
         * Get the associated {@link VariableDesc#scope}.
         *
         * @return {@link VariableDesc#scope}.
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Is the variable used again or not.
         *
         * @return true if variable is used again else false;
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Register the variable as used again.
         */
        public void registerAsUsed() {
            this.used = true;
        }

        /**
         * Set the scope of variable.
         *
         * @param scope {@link VariableDesc#scope}
         */
        public void setScope(DetailAST scope) {
            this.scope = scope;
        }
    }
}
