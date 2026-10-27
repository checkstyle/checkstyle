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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <div>Checks for redundant modifiers on fields and methods declared directly
 * in a compact source file. Ordinary and nested declarations are checked by
 * {@code RedundantModifier}.</div>
 *
 * @since 14.4.0
 */
@FileStatefulCheck
public class RedundantModifierCompactSourceCheck extends AbstractCheck {

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_KEY = "redundantModifierCompactSource";

    /** Modifiers that are redundant on a method declared directly in a compact source file. */
    private static final Set<Integer> METHOD_MODIFIERS = Set.of(
        TokenTypes.LITERAL_PUBLIC, TokenTypes.LITERAL_PROTECTED,
        TokenTypes.LITERAL_PRIVATE, TokenTypes.LITERAL_STATIC,
        TokenTypes.FINAL, TokenTypes.STRICTFP);

    /** Modifiers that are redundant on a field declared directly in a compact source file. */
    private static final Set<Integer> FIELD_MODIFIERS = Set.of(
        TokenTypes.LITERAL_PUBLIC, TokenTypes.LITERAL_PROTECTED,
        TokenTypes.LITERAL_PRIVATE, TokenTypes.LITERAL_STATIC);

    /** One of these is mandatory on a valid @SafeVarargs method. */
    private static final Set<Integer> SAFE_VARARGS_REQUIRED = Set.of(
        TokenTypes.LITERAL_STATIC, TokenTypes.FINAL, TokenTypes.LITERAL_PRIVATE);

    /** Simple and qualified names of the {@code SafeVarargs} annotation. */
    private static final Set<String> SAFE_VARARGS_NAMES =
        Set.of("SafeVarargs", "java.lang.SafeVarargs");

    /**
     * Root of the file being processed. Only a compact source file can have a
     * field or method as a direct child of its root.
     */
    private DetailAST root;

    /**
     * Creates a new {@code RedundantModifierCompactSourceCheck} instance.
     */
    public RedundantModifierCompactSourceCheck() {
        // no code by default
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        root = rootAST;
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
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent() == root) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (ast.getType() == TokenTypes.METHOD_DEF) {
                checkModifiers(modifiers, METHOD_MODIFIERS,
                        isRequiredModifierKept(ast, modifiers));
            }
            else {
                checkModifiers(modifiers, FIELD_MODIFIERS, false);
            }
        }
    }

    /**
     * Logs every modifier that is redundant for the declaration.
     *
     * @param modifiers the MODIFIERS node of the declaration
     * @param redundant token types of modifiers that are redundant
     * @param keepRequired whether the modifier making {@code @SafeVarargs} valid must be kept
     */
    private void checkModifiers(DetailAST modifiers, Set<Integer> redundant,
                                boolean keepRequired) {
        for (DetailAST modifier = modifiers.getFirstChild();
                modifier != null; modifier = modifier.getNextSibling()) {
            final int type = modifier.getType();
            if (redundant.contains(type)
                    && !(keepRequired && SAFE_VARARGS_REQUIRED.contains(type))) {
                log(modifier, MSG_KEY, modifier.getText());
            }
        }
    }

    /**
     * Checks whether a modifier is the single one making {@code @SafeVarargs} valid.
     *
     * @param methodDef the METHOD_DEF node
     * @param modifiers the MODIFIERS node of the method
     * @return true if the required modifier must not be reported
     */
    private static boolean isRequiredModifierKept(DetailAST methodDef, DetailAST modifiers) {
        return AnnotationUtil.containsAnnotation(methodDef, SAFE_VARARGS_NAMES)
                && countRequiredModifiers(modifiers) == 1;
    }

    /**
     * Counts the modifiers that can make a {@code @SafeVarargs} method valid.
     *
     * @param modifiers the MODIFIERS node
     * @return number of such modifiers
     */
    private static int countRequiredModifiers(DetailAST modifiers) {
        int count = 0;
        for (DetailAST modifier = modifiers.getFirstChild();
                modifier != null; modifier = modifier.getNextSibling()) {
            if (SAFE_VARARGS_REQUIRED.contains(modifier.getType())) {
                count++;
            }
        }
        return count;
    }

}
