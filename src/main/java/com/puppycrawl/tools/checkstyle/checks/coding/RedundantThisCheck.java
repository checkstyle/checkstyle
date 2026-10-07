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
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that references to instance variables and methods of the current object
 * avoid unnecessary use of {@code this}, unless it is required to resolve
 * ambiguity with a shadowed field.
 * </div>
 *
 * <p>
 * By default, a redundant {@code this} is tolerated when the same statement, or the
 * statement immediately before or after it, contains a {@code this} that is required
 * to resolve a shadowed field. This keeps groups of assignments consistent, for example
 * constructor bodies where only some parameters shadow fields. Set
 * {@code allowAdjacentToRequiredThis} to {@code false} to report every redundant
 * {@code this}.
 * </p>
 *
 * @since 14.4.0
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
     * Statements containing a {@code this} that is required to resolve shadowing.
     */
    private final Set<DetailAST> requiredStatements = new HashSet<>();

    /**
     * Redundant {@code this} usages whose reporting is decided in {@code finishTree}.
     */
    private final List<RedundantUsage> redundantUsages = new ArrayList<>();

    /**
     * Control to checking method calls.
     */
    private boolean checkMethods;

    /**
     * Allow a redundant {@code this} when the same or an adjacent statement
     * contains a required {@code this}.
     */
    private boolean allowAdjacentToRequiredThis = true;

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
     * @since 14.4.0
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
    }

    /**
     * Setter to allow redundant "this" when the same statement, or the statement
     * immediately before or after it, contains a required "this".
     *
     * @param allowAdjacentToRequiredThis whether to allow such usages
     * @since 14.4.0
     */
    public void setAllowAdjacentToRequiredThis(boolean allowAdjacentToRequiredThis) {
        this.allowAdjacentToRequiredThis = allowAdjacentToRequiredThis;
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
    public void beginTree(DetailAST rootAST) {
        redundantUsages.clear();
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
        if (TokenUtil.isOfType(ast,
                TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_TRY,
                TokenTypes.SLIST)) {
            scopeStack.pop();
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (RedundantUsage usage : redundantUsages) {
            if (!allowAdjacentToRequiredThis || !isNearRequiredThis(usage.statement())) {
                log(usage.literalThis(), usage.messageKey(), usage.name());
            }
        }
    }

    /**
     * Classifies the use of "this" as required (shadowed) or redundant. Required
     * usages are recorded by their enclosing statement; redundant usages are
     * deferred so that {@code finishTree} can decide whether to report them.
     *
     * @param literalThis the {@code LITERAL_THIS} token to check
     */
    private void checkUnnecessaryThis(DetailAST literalThis) {
        final DetailAST parent = literalThis.getParent();
        if (parent.getType() == TokenTypes.DOT) {
            final DetailAST grandParent = parent.getParent();
            final String name = literalThis.getNextSibling().getText();
            final DetailAST statement = findEnclosingStatement(literalThis);

            if (grandParent.getType() == TokenTypes.METHOD_CALL) {
                if (checkMethods) {
                    redundantUsages.add(
                        new RedundantUsage(literalThis, statement, MSG_KEY_METHOD, name));
                }
            }
            else if (isShadowedByLocalName(name)) {
                if (statement != null) {
                    requiredStatements.add(statement);
                }
            }
            else {
                redundantUsages.add(
                    new RedundantUsage(literalThis, statement, MSG_KEY_FIELD, name));
            }
        }
    }

    /**
     * Finds the statement (a direct child of an {@code SLIST}) enclosing the given node.
     *
     * @param node the node
     * @return the statement, or {@code null} if the node is not inside a block
     *     (for example, a field initializer)
     */
    private static DetailAST findEnclosingStatement(DetailAST node) {
        DetailAST statement = node;
        while (!TokenUtil.isOfType(statement.getParent(),
                    TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            statement = statement.getParent();
        }

        DetailAST result = null;
        if (statement.getParent().getType() == TokenTypes.SLIST) {
            result = statement;
        }

        return result;
    }

    /**
     * Checks whether the given statement, or the statement immediately before or
     * after it, contains a required {@code this}.
     *
     * @param statement the statement to check, may be {@code null}
     * @return {@code true} if a required {@code this} is in the same or an adjacent statement
     */
    private boolean isNearRequiredThis(DetailAST statement) {
        return statement != null
                && (requiredStatements.contains(statement)
                    || requiredStatements.contains(previousStatement(statement))
                    || requiredStatements.contains(nextStatement(statement)));
    }

    /**
     * Gets the statement before the given one, stepping over the {@code SEMI}
     * that terminates the previous statement.
     *
     * @param statement the statement
     * @return the previous statement, or {@code null} if there is none
     */
    private static DetailAST previousStatement(DetailAST statement) {
        DetailAST result = statement.getPreviousSibling();
        if (result != null && result.getType() == TokenTypes.SEMI) {
            result = result.getPreviousSibling();
        }
        return result;
    }

    /**
     * Gets the statement after the given one, stepping over the {@code SEMI}
     * that terminates the given statement.
     *
     * @param statement the statement
     * @return the next statement, or {@code null} if there is none
     */
    private static DetailAST nextStatement(DetailAST statement) {
        DetailAST result = statement.getNextSibling();
        if (result.getType() == TokenTypes.SEMI) {
            result = result.getNextSibling();
        }
        return result;
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
                scopeStack.peek().add(ident.getText());
            }
            child = child.getNextSibling();
        }
    }

    /**
     * A redundant {@code this} usage waiting for a reporting decision.
     *
     * @param literalThis the {@code LITERAL_THIS} token
     * @param statement the enclosing statement, or {@code null} if outside a block
     * @param messageKey the violation message key
     * @param name the member name following "this"
     */
    private record RedundantUsage(
            DetailAST literalThis, DetailAST statement, String messageKey, String name) {
    }

}
