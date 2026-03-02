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
 * Checks for assignment of pattern variables.
 * </div>
 *
 * <p>
 * Pattern variable assignment is considered bad programming practice. The pattern variable
 * is meant to be a direct reference to the object being matched. Reassigning it can break this
 * connection and mislead readers.
 * </p>
 *
 * @since 13.4.0
 */
@FileStatefulCheck
public class PatternVariableAssignmentCheck extends AbstractCheck {

    /**
     * Message key for pattern variable assignment violation.
     */
    public static final String MSG_KEY = "pattern.variable.assignment";

    /**
     * Stack of active scopes.
     */
    private final Deque<Set<String>> scopes = new ArrayDeque<>();

    @Override
    public int[] getRequiredTokens() {
        return new int[]{
            TokenTypes.SLIST,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.BOR_ASSIGN,
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
    public void beginTree(DetailAST rootAST) {
        scopes.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        if (type == TokenTypes.SLIST) {
            scopes.push(new HashSet<>());
        }
        else if (type == TokenTypes.PATTERN_VARIABLE_DEF) {
            addPatternVariable(ast);
        }
        else if (type == TokenTypes.VARIABLE_DEF) {
            removeShadowedVariable(ast);
        }
        else {
            // Must be an assignment token (ASSIGN, PLUS_ASSIGN, etc.)
            checkAssignment(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.SLIST) {
            scopes.pop();
        }
    }

    /**
     * Adds a pattern variable to the current scope.
     *
     * @param ast the AST node representing the pattern variable definition
     */
    private void addPatternVariable(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (!scopes.isEmpty()) {
            scopes.peek().add(ident.getText());
        }
    }

    /**
     * Removes a shadowed variable from all active scopes.
     * This prevents false positives when a local variable
     * has the same name as a pattern variable.
     *
     * @param ast the AST node representing the variable definition
     */
    private void removeShadowedVariable(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        final String name = ident.getText();
        for (Set<String> scope : scopes) {
            scope.remove(name);
        }
    }

    /**
     * Checks whether the assignment is to a pattern variable.
     *
     * @param ast the AST node representing the assignment
     */
    private void checkAssignment(DetailAST ast) {
        if (!scopes.isEmpty()) {
            final DetailAST lhs = ast.getFirstChild();
            if (lhs.getType() == TokenTypes.IDENT) {
                final String name = lhs.getText();

                for (Set<String> scope : scopes) {
                    if (scope.contains(name)) {
                        log(lhs, MSG_KEY, name);
                        break;
                    }
                }
            }
        }
    }
}
