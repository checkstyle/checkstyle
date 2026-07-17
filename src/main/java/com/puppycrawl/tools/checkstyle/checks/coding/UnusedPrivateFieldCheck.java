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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
 * @since 13.9.0
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
     * Stack of used field sets, one per class nesting level.
     */
    private final Deque<Set<String>> usedFieldsStack = new ArrayDeque<>();

    /**
     * Global set of fields accessed via dot notation anywhere in the file.
     */
    private final Set<String> globalUsedFields = new HashSet<>();

    /**
     * Accumulated pending fields (each carrying its own used-name set),
     * reported at finishTree.
     */
    private final List<PendingField> pendingFields = new ArrayList<>();

    /**
     * Scope stack tracking local variable and parameter names per block.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    /**
     * Snapshots of scope stack saved when entering a nested class,
     * restored when leaving it.
     */
    private final Deque<Deque<Set<String>>> scopeStackSnapshots = new ArrayDeque<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.PARAMETERS,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
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
        usedFieldsStack.clear();
        globalUsedFields.clear();
        pendingFields.clear();
        scopeStack.clear();
        scopeStackSnapshots.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK -> {
                privateFields.push(new HashMap<>());
                usedFieldsStack.push(new HashSet<>());
                scopeStackSnapshots.push(new ArrayDeque<>(scopeStack));
                scopeStack.clear();
            }
            case TokenTypes.PARAMETERS, TokenTypes.SLIST -> scopeStack.push(new HashSet<>());
            case TokenTypes.PARAMETER_DEF -> {
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                if (ident != null) {
                    scopeStack.peek().add(ident.getText());
                }
            }
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
            case TokenTypes.OBJBLOCK -> {
                final Map<String, DetailAST> classFields = privateFields.pop();
                final Set<String> classUsed = usedFieldsStack.pop();
                for (final Map.Entry<String, DetailAST> entry : classFields.entrySet()) {
                    pendingFields.add(new PendingField(entry, classUsed));
                }
                final Deque<Set<String>> snapshot = scopeStackSnapshots.pop();
                snapshot.forEach(scopeStack::push);
            }
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                 TokenTypes.SLIST -> scopeStack.pop();

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
            final String localName =
                ast.findFirstToken(TokenTypes.IDENT).getText();
            if (!scopeStack.isEmpty()) {
                scopeStack.peek().add(localName);
            }
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
            parent.getType() == TokenTypes.VARIABLE_DEF;
        if (!isDeclarationIdent) {
            final String name = ast.getText();
            final boolean shadowed =
                scopeStack.stream().anyMatch(scope -> scope.contains(name));
            final boolean isDotAccess = parent.getType() == TokenTypes.DOT;
            if (!shadowed || isDotAccess) {
                usedFieldsStack.forEach(set -> set.add(name));
                if (isDotAccess) {
                    globalUsedFields.add(name);
                }
            }
        }
    }

    /**
     * Reports all unused private fields at the end of the file.
     *
     * @param rootAST the root of the AST
     */
    @Override
    public void finishTree(final DetailAST rootAST) {
        for (final PendingField pending : pendingFields) {
            if (!pending.usedNames.contains(pending.entry.getKey())
                && !globalUsedFields.contains(pending.entry.getKey())) {
                log(pending.entry.getValue(), MSG_PRIVATE_FIELD, pending.entry.getKey());
            }
        }
    }

    /**
     * Checks whether a field is private.
     *
     * @param modifiers for isPrivate method.
     * @return modifiers of literal_private.
     */
    private static boolean isPrivate(final DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

    /**
     * Checks whether a field is a static final constant.
     *
     * @param modifiers for isStaticFinal method.
     * @return modifiers of literal_static and final.
     */
    private static boolean isStaticFinal(final DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                && modifiers.findFirstToken(TokenTypes.FINAL) != null;
    }

    /**
     * Holds a private field entry together with the set of names used
     * in the class scope where the field was declared.
     *
     * @param entry     The field name and its AST node.
     * @param usedNames The set of identifiers used within the declaring class scope.
     */
    private record PendingField(Map.Entry<String, DetailAST> entry, Set<String> usedNames) {

    }
}
