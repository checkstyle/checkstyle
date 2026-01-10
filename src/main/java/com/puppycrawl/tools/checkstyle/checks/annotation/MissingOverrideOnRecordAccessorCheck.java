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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <div>
 * Verifies that explicitly declared record accessor methods include
 * the {@code @Override} annotation.
 * </div>
 *
 * <p>
 * Per <a href="https://openjdk.org/jeps/395">JEP 395</a>, the meaning of the
 * {@code @Override} annotation was extended to include explicitly declared
 * accessor methods for record components.
 * </p>
 *
 * <p>
 * This check focuses only on record component accessor methods. It does not
 * attempt to detect general method overrides from interfaces or superclasses,
 * due to Checkstyle's single-file analysis limitations.
 * </p>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class MissingOverrideOnRecordAccessorCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "annotation.missing.override.record.accessor";

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
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isRecordAccessorMethod(ast) && !AnnotationUtil.hasOverrideAnnotation(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the given method is an explicitly declared accessor for a record component.
     *
     * @param ast the METHOD_DEF AST node
     * @return true if this method is a record accessor
     */
    private static boolean isRecordAccessorMethod(DetailAST ast) {
        boolean result = false;
        final DetailAST grandParent = ast.getParent().getParent();
        if (grandParent.getType() == TokenTypes.RECORD_DEF) {
            final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);
            if (parameters.getChildCount() == 0) {
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                result = getRecordComponentNames(grandParent).contains(methodName);
            }
        }
        return result;
    }

    /**
     * Gets the set of record component names from a record definition.
     *
     * @param recordDef the RECORD_DEF AST node
     * @return set of component names
     */
    private static Set<String> getRecordComponentNames(DetailAST recordDef) {
        final Set<String> names = new HashSet<>();
        final DetailAST recordComponents = recordDef.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        DetailAST child = recordComponents.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.RECORD_COMPONENT_DEF) {
                names.add(child.findFirstToken(TokenTypes.IDENT).getText());
            }
            child = child.getNextSibling();
        }
        return names;
    }
}
