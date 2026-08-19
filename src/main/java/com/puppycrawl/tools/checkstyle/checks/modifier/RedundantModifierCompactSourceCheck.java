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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for redundant modifiers on methods and fields declared directly in compact source files.
 * </div>
 *
 * @since 13.11.0
 */
@StatelessCheck
public class RedundantModifierCompactSourceCheck extends AbstractCheck {

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_KEY = "redundantModifierCompactSource";

    /** Simple and fully-qualified {@code SafeVarargs} annotation names. */
    private static final Set<String> SAFE_VARARGS_ANNOTATIONS =
            Set.of("SafeVarargs", "java.lang.SafeVarargs");

    /** Creates a new instance. */
    public RedundantModifierCompactSourceCheck() {
        // No code.
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.COMPACT_COMPILATION_UNIT) {
            final DetailAST modifiers =
                    NullUtil.notNull(ast.findFirstToken(TokenTypes.MODIFIERS));
            for (DetailAST child = modifiers.getFirstChild(); child != null;
                    child = child.getNextSibling()) {
                final boolean isRedundant;
                if (ast.getType() == TokenTypes.METHOD_DEF) {
                    isRedundant = isRedundantMethodModifier(ast, child);
                }
                else {
                    isRedundant = isRedundantFieldModifier(child);
                }
                if (isRedundant) {
                    log(child, MSG_KEY, child.getText());
                }
            }
        }
    }

    /**
     * Checks whether the modifier is redundant for a direct compact-source method.
     *
     * @param methodAst direct compact-source method
     * @param modifierAst modifier to check
     * @return whether the modifier is redundant
     */
    private static boolean isRedundantMethodModifier(DetailAST methodAst, DetailAST modifierAst) {
        return TokenUtil.isOfType(modifierAst, TokenTypes.LITERAL_PUBLIC,
                TokenTypes.LITERAL_PROTECTED, TokenTypes.LITERAL_PRIVATE,
                TokenTypes.LITERAL_STATIC, TokenTypes.FINAL, TokenTypes.STRICTFP)
                && !isModifierRequiredForSafeVarargs(methodAst, modifierAst);
    }

    /**
     * Checks whether the modifier is redundant for a direct compact-source field.
     *
     * @param modifierAst modifier to check
     * @return whether the modifier is redundant
     */
    private static boolean isRedundantFieldModifier(DetailAST modifierAst) {
        return TokenUtil.isOfType(modifierAst, TokenTypes.LITERAL_PUBLIC,
                TokenTypes.LITERAL_PROTECTED, TokenTypes.LITERAL_PRIVATE,
                TokenTypes.LITERAL_STATIC);
    }

    /**
     * Checks whether the modifier is necessary for a variable-arity method annotated
     * with {@code SafeVarargs}.
     *
     * @param methodDef method definition
     * @param modifierAst modifier to check
     * @return whether the modifier is necessary
     */
    private static boolean isModifierRequiredForSafeVarargs(DetailAST methodDef,
            DetailAST modifierAst) {
        final DetailAST modifiers =
                NullUtil.notNull(methodDef.findFirstToken(TokenTypes.MODIFIERS));
        final DetailAST parameters =
                NullUtil.notNull(methodDef.findFirstToken(TokenTypes.PARAMETERS));
        final DetailAST lastParameter = parameters.getLastChild();
        final boolean isVariableArity = lastParameter != null
                && lastParameter.findFirstToken(TokenTypes.ELLIPSIS) != null;
        final int modifierType = modifierAst.getType();
        final boolean hasOtherEligibilityModifier =
                modifierType != TokenTypes.LITERAL_STATIC
                    && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                || modifierType != TokenTypes.LITERAL_PRIVATE
                    && modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null
                || modifierType != TokenTypes.FINAL
                    && modifiers.findFirstToken(TokenTypes.FINAL) != null;
        return isVariableArity
                && !hasOtherEligibilityModifier
                && TokenUtil.isOfType(modifierAst, TokenTypes.LITERAL_PRIVATE,
                        TokenTypes.LITERAL_STATIC, TokenTypes.FINAL)
                && AnnotationUtil.containsAnnotation(methodDef, SAFE_VARARGS_ANNOTATIONS);
    }

}
