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
 * @since 13.9.0
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
    public static final String MSG_KEY_FIELD = "redundant.this.field";

    /**
     * Tracks names (parameters and local variables) that can shadow a field.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    /**
     * Control to checking method calls.
     */
    private boolean checkMethodCall;

    /**
     * Creates a new {@code RedundantThisCheck} instance.
     */
    public RedundantThisCheck() {
        // no code by default
    }

    /**
     * Setter to check whether to check redundant "this" with method call.
     *
     * @param checkMethodCall should we check method call
     * @since 13.9.0
     */
    public void setCheckMethodCall(boolean checkMethodCall) {
        this.checkMethodCall = checkMethodCall;
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_CATCH,
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
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(ast.findFirstToken(TokenTypes.PARAMETERS));
            }
            case TokenTypes.LITERAL_CATCH -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(ast);
            }
            case TokenTypes.SLIST -> scopeStack.push(new HashSet<>());
            case TokenTypes.VARIABLE_DEF -> {
                final Set<String> currentScope = scopeStack.peek();
                if (currentScope != null) {
                    final String variable = ast.findFirstToken(TokenTypes.IDENT).getText();
                    currentScope.add(variable);
                }
            }
            default -> {
                if (ast.getNextSibling() != null) {
                    checkUnnecessaryThis(ast);
                }
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        final int type = ast.getType();
        if (type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || type == TokenTypes.LITERAL_CATCH
                || type == TokenTypes.SLIST) {
            scopeStack.pop();
        }
    }

    /**
     * Checks if the use of "this" is redundant and logs a violation if so.
     *
     * @param ast the {@code LITERAL_THIS} token to check
     */
    private void checkUnnecessaryThis(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final DetailAST grandParent = parent.getParent();
        final boolean isVariable = parent.getType() != TokenTypes.EQUAL
                && parent.getType() != TokenTypes.NOT_EQUAL
                && parent.getType() != TokenTypes.METHOD_REF
                && grandParent.getType() != TokenTypes.METHOD_CALL;

        final DetailAST nextSibling = ast.getNextSibling();
        if (isVariable) {
            final String varName = nextSibling.getText();
            if (!isShadowedByLocalName(varName)) {
                log(ast, MSG_KEY_FIELD, varName);
            }
        }
        else if (checkMethodCall && grandParent.getType() == TokenTypes.METHOD_CALL) {
            final String method = nextSibling.getText();
            log(ast, MSG_KEY_METHOD, method);
        }
    }

    /**
     * Checks whether the given variable name is shadowed by any parameter or
     * local variable currently in scope, meaning that {@code this.name} is
     * necessary to distinguish the field from the shadowing name.
     *
     * @param name the variable name following "this"
     * @return {@code true} if a shadowing name exists in any enclosing scope
     */
    private boolean isShadowedByLocalName(String name) {
        boolean result = false;
        for (Set<String> scope : scopeStack) {
            if (scope.contains(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Adds all parameters found within {@code parametersNode} to the current
     * (top-of-stack) scope. {@code parametersNode} may be either a
     * {@code PARAMETERS} node (for methods and constructors) or a
     * {@code LITERAL_CATCH} node (for catch clauses); both carry their
     * parameter definitions as direct {@code PARAMETER_DEF} children.
     *
     * @param parametersNode the node whose {@code PARAMETER_DEF} children
     *     should be added to the current scope
     */
    private void addParametersToScope(DetailAST parametersNode) {
        DetailAST child = parametersNode.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                if (ident != null) {
                    scopeStack.peek().add(ident.getText());
                }
            }
            child = child.getNextSibling();
        }
    }

}
