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
import java.util.Optional;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that references to instance variables and methods of the current object
 * avoid unnecessary use of {@code this}, unless it is required to resolve
 * ambiguity with a shadowed field.
 * </div>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class RedundantThisCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_FIELD = "redundant.this.field";

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_METHOD = "redundant.this.method";

    /**
     * Tracks names (parameters and local variables) that can shadow a field.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    /**
     * Control to checking method calls.
     */
    private boolean checkMethods;

    /**
     * Creates a new {@code RedundantThisCheck} instance.
     */
    public RedundantThisCheck() {
        // no code by default
    }

    /**
     * Setter to check whether to check redundant "this" with method call.
     *
     * @param checkMethods should we check method call
     * @since 14.1.0
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_THIS,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.SLIST,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(NullUtil.notNull(ast.findFirstToken(TokenTypes.PARAMETERS)));
            }
            case TokenTypes.LITERAL_CATCH -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(ast);
            }
            case TokenTypes.LITERAL_TRY -> {
                scopeStack.push(new HashSet<>());
                final Optional<DetailAST> resourcesNode = Optional.of(ast.getFirstChild())
                    .map(child -> child.findFirstToken(TokenTypes.RESOURCES));

                if (resourcesNode.isPresent()) {
                    addParametersToScope(resourcesNode.orElseThrow());
                }
            }
            case TokenTypes.SLIST -> scopeStack.push(new HashSet<>());
            case TokenTypes.VARIABLE_DEF, TokenTypes.PATTERN_VARIABLE_DEF -> {
                final Set<String> currentScope = scopeStack.peek();
                if (currentScope != null) {
                    final String variable =
                        NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT)).getText();
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
        if (TokenUtil.isOfType(ast,
                TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_TRY,
                TokenTypes.SLIST)) {
            scopeStack.pop();
        }
    }

    /**
     * Checks if the use of "this" is redundant and logs a violation if so.
     *
     * @param literalThis the {@code LITERAL_THIS} token to check
     */
    private void checkUnnecessaryThis(DetailAST literalThis) {
        final DetailAST parent = literalThis.getParent();
        if (parent.getType() != TokenTypes.LITERAL_INSTANCEOF) {
            final DetailAST grandParent = parent.getParent();
            final boolean isVariable = !TokenUtil.isOfType(parent,
                    TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.METHOD_REF)
                    && grandParent.getType() != TokenTypes.METHOD_CALL;

            final DetailAST nextSibling = literalThis.getNextSibling();
            final String name = nextSibling.getText();
            if (isVariable && !isShadowedByLocalName(name)) {
                log(literalThis, MSG_KEY_FIELD, name);
            }
            else if (checkMethods && grandParent.getType() == TokenTypes.METHOD_CALL) {
                log(literalThis, MSG_KEY_METHOD, name);
            }
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
            final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                NullUtil.notNull(scopeStack.peek()).add(ident.getText());
            }
            child = child.getNextSibling();
        }
    }

}
