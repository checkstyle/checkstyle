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
 * @since 13.1.0
 */
@FileStatefulCheck
public class UnusedPrivateFieldCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRIVATE_FIELD = "unused.private.field";

    /** Map of private field name to its declaration AST. */
    private final Map<String, DetailAST> privateFields = new HashMap<>();

    /** Set of private fields that are used somewhere in the file. */
    private final Set<String> usedFields = new HashSet<>();

    /**
     * Property to enable or disable the check.
     * Default is true (check is active).
     *
     */
    private boolean checkUnusedPrivateField = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
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
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (checkUnusedPrivateField) {

            if (ast.getType() == TokenTypes.VARIABLE_DEF) {
                handleVariableDef(ast);
            }
            else if (ast.getType() == TokenTypes.IDENT) {
                handleIdent(ast);
            }
        }
    }

    /**
     * Setter to allow private field by default it is true.
     *
     * @param checkUnusedPrivateField true or false.
     * @since 13.1.0
     */
    public void setCheckUnusedPrivateField(boolean checkUnusedPrivateField) {
        this.checkUnusedPrivateField = checkUnusedPrivateField;

    }

    /**
     * Collects private field declarations.
     *
     * @param ast for handlevariabledef
     */
    private void handleVariableDef(DetailAST ast) {
        final boolean isField = ast.getParent().getType() == TokenTypes.OBJBLOCK;
        if (isField) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean isPrivateField = isPrivate(modifiers);
            final boolean isConstant = isStaticFinal(modifiers);

            if (isPrivateField && !isConstant) {
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                final String fieldName = ident.getText();
                privateFields.put(fieldName, ident);
            }
        }
    }

    /**
     * Marks field usage.
     *
     * @param ast for handleIdent
     */
    private void handleIdent(DetailAST ast) {

        final DetailAST parent = ast.getParent();
        final boolean isFieldDeclarationIdent =
                parent.getType() == TokenTypes.VARIABLE_DEF;

        if (!isFieldDeclarationIdent) {
            final String name = ast.getText();
            if (privateFields.containsKey(name)) {
                usedFields.add(name);
            }
        }
    }

    /**
     * Checks whether a field is private.
     *
     * @param modifiers for isprivate method.
     *
     * @return modifiers of literal_private.
     */
    private static boolean isPrivate(DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

    /**
     * Checks whether a field is a static final constant.
     *
     * @param modifiers for isStaticFinal method.
     *
     * @return modifiers of literal_static anf final.
     */
    private static boolean isStaticFinal(DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
            && modifiers.findFirstToken(TokenTypes.FINAL) != null;
    }

    @Override
    public void finishTree(DetailAST rootAST) {

        if (checkUnusedPrivateField) {

            for (Map.Entry<String, DetailAST> entry : privateFields.entrySet()) {
                final String fieldName = entry.getKey();

                if (!usedFields.contains(fieldName)) {
                    log(entry.getValue(),
                            MSG_PRIVATE_FIELD,
                            fieldName);
                }
            }
        }
    }
}

