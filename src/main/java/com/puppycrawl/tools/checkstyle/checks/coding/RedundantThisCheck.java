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
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that references to instance variables and methods of the present object
 * explicitly avoid unnecessary use of "this", unless required to resolve ambiguity with
 * a shadowed field.
 * </div>
 *
 * <p>
 * Rationale: modern IDEs(e.g. IDEA, ECLIPSE, NetBeans) show what an entity is
 * (class variable, local variable etc.) so there is no need to put redundant
 * "this" keyword.
 * </p>
 *
 * @since 13.5.0
 */
@FileStatefulCheck
public class RedundantThisCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_METHOD = "redundant.this.method";

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_VARIABLE = "redundant.this.variable";

    /**
     * List of parameters in a method.
     */
    private final Set<String> parametersInMethod = new HashSet<>();

    /**
     * Tracks local variables per scope.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    /**
     * Control to checking method calls.
     */
    private boolean checkMethodCall;

    /**
     * Setter to check whether to check redundant "this" with method call.
     *
     * @param checkMethodCall should we check method call
     * @since 13.5.0
     */
    public void setCheckMethodCall(boolean checkMethodCall) {
        this.checkMethodCall = checkMethodCall;
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.PARAMETERS,
            TokenTypes.LITERAL_THIS,
            TokenTypes.SLIST,
            TokenTypes.VARIABLE_DEF,
        };
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
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SLIST -> {
                final Set<String> last = scopeStack.peek();
                if (last == null) {
                    scopeStack.add(new HashSet<>());
                }
                else {
                    scopeStack.add(new HashSet<>(last));
                }
            }

            case TokenTypes.VARIABLE_DEF -> {
                if (scopeStack.peek() != null) {
                    final String variable = ast.findFirstToken(TokenTypes.IDENT).getText();
                    scopeStack.peek().add(variable);
                }
            }

            case TokenTypes.PARAMETERS -> collectParameters(ast);

            default -> {
                if (ast.getNextSibling() != null) {
                    checkUnnecessaryThis(ast);
                }
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF
                || ast.getType() == TokenTypes.CTOR_DEF) {
            parametersInMethod.clear();
        }
        else if (ast.getType() == TokenTypes.SLIST) {
            scopeStack.pop();
        }
    }

    /**
     * Checks if the use of "this" is redundant and logs a violation if so.
     *
     * @param ast the token to check
     */
    private void checkUnnecessaryThis(DetailAST ast) {
        final DetailAST nextSibling = ast.getNextSibling();
        final DetailAST grandParent = ast.getParent().getParent();
        final DetailAST parent = ast.getParent();
        final boolean isVariable = nextSibling.getType() == TokenTypes.IDENT
                && grandParent.getType() != TokenTypes.METHOD_CALL
                && parent.getType() != TokenTypes.EQUAL
                && parent.getType() != TokenTypes.NOT_EQUAL
                && parent.getType() != TokenTypes.METHOD_REF;

        if (isVariable) {
            if (isThisVariableRedundant(nextSibling)) {
                log(ast, MSG_KEY_VARIABLE, nextSibling.getText());
            }
        }
        else if (checkMethodCall && grandParent.getType() == TokenTypes.METHOD_CALL) {
            final String method = nextSibling.getText();
            log(ast, MSG_KEY_METHOD, method);
        }
    }

    /**
     * Checks if "this" used before a variable reference is redundant
     * by verifying the variable is not shadowed by a parameter or local variable.
     *
     * @param nextSibling the {@code IDENT} token following "this"
     * @return true if "this" is redundant, false otherwise
     */
    private boolean isThisVariableRedundant(DetailAST nextSibling) {
        final Set<String> currentScope = scopeStack.peek();
        final String variable = nextSibling.getText();
        return !parametersInMethod.contains(variable)
                && (currentScope == null || !currentScope.contains(variable));
    }

    /**
     * Collects the parameter variables of the current method.
     *
     * @param parameters list of parameters
     */
    private void collectParameters(DetailAST parameters) {
        DetailAST child = parameters.getFirstChild();
        while (child != null) {
            final DetailAST param = child.findFirstToken(TokenTypes.IDENT);
            if (param != null) {
                parametersInMethod.add(param.getText());
            }
            child = child.getNextSibling();
        }
    }

}
