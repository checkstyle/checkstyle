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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Check that a private field is declared, but never used.
 * </div>
 *
 * @since 13.5.0
 */
@FileStatefulCheck
public class UnusedPrivateFieldCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_PRIVATE_FIELD = "unused.private.field";

    /**
     * Stack of private field maps, one per class nesting level.
     */
    private final Deque<Map<String, DetailAST>> privateFields = new ArrayDeque<>();

    /**
     * Set of private fields that are used somewhere in the file.
     */
    private final Set<String> usedFields = new HashSet<>();

    /**
     * Scope stack tracking local variable and parameter names per block.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        privateFields.clear();
        usedFields.clear();
        scopeStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK -> privateFields.push(new HashMap<>());
            case TokenTypes.SLIST -> scopeStack.push(new HashSet<>());
            case TokenTypes.VARIABLE_DEF -> handleVariableDef(ast);
            case TokenTypes.IDENT -> handleIdent(ast);
            default -> {
                // no action needed for other token types
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK -> reportUnusedFields(privateFields.pop());
            case TokenTypes.SLIST -> scopeStack.pop();
            default -> {
                // no action needed for other token types
            }
        }

    }

    /**
     * Collects private field declarations.
     *
     * @param ast for this method.
     */
    private void handleVariableDef(DetailAST ast) {
        final DetailAST parent = ast.getParent();

        if (parent.getType() == TokenTypes.OBJBLOCK) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean isPrivateField = isPrivate(modifiers);
            final boolean isConstant = isStaticFinal(modifiers);
            if (isPrivateField && !isConstant) {
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                privateFields.peek().put(ident.getText(), ast);
            }
        }
        else {
            scopeStack.peek().add(
                ast.findFirstToken(TokenTypes.IDENT).getText());
        }

    }

    /**
     * Marks field usage, respecting local variable and parameter shadowing.
     *
     * @param ast for handleIdent
     */
    private void handleIdent(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final boolean isDeclarationIdent =
            parent.getType() == TokenTypes.VARIABLE_DEF
            || parent.getType() == TokenTypes.PARAMETER_DEF;

        if (!isDeclarationIdent) {
            final String name = ast.getText();
            final boolean shadowed =
                scopeStack.stream().anyMatch(scope -> scope.contains(name));
            if (!shadowed) {
                usedFields.add(name);
            }
        }
    }

    /**
     * Reports unused private fields for the class that is being left.
     *
     * @param classFields the field map for the class just finished
     */
    private void reportUnusedFields(Map<String, DetailAST> classFields) {
        for (Map.Entry<String, DetailAST> entry : classFields.entrySet()) {
            if (!usedFields.contains(entry.getKey())) {
                log(entry.getValue(), MSG_PRIVATE_FIELD, entry.getKey());
            }
        }
    }

    /**
     * Checks whether a field is private.
     *
     * @param modifiers for isPrivate method.
     * @return modifiers of literal_private.
     */
    private static boolean isPrivate(DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

    /**
     * Checks whether a field is a static final constant.
     *
     * @param modifiers for isStaticFinal method.
     * @return modifiers of literal_static and final.
     */
    private static boolean isStaticFinal(DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                && modifiers.findFirstToken(TokenTypes.FINAL) != null;
    }

}
