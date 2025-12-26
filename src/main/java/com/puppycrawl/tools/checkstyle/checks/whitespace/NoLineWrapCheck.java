///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>Checks that chosen statements are not line-wrapped.
 * By default, this Check restricts wrapping import and package statements,
 * but it's possible to check any statement.
 * </div>
 *
 * @since 5.8
 */
@StatelessCheck
public class NoLineWrapCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "no.line.wrap";

    /**
     * Property that defines whether annotations on the previous line should be
     * checked as violation.
     */
    private boolean skipAnnotations = true;

    /**
     * Setter to specify whether to skip annotations to be part of target token.
     *
     * @param shouldSkipAnnotations whether to skip annotations to be part of target token.
     * @since 12.4.0
     */
    public void setSkipAnnotations(boolean shouldSkipAnnotations) {
        skipAnnotations = shouldSkipAnnotations;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST detailAST = ast;
        if (skipAnnotations && AnnotationUtil.containsAnnotation(ast)) {
            if (TokenUtil.isTypeDeclaration(ast.getType())) {
                detailAST = ast.getFirstChild().getNextSibling();
            }
            else if (!TokenUtil.isOfType(ast, TokenTypes.PACKAGE_DEF)) {
                detailAST = ast.getFirstChild().getFirstChild().getNextSibling();
            }
        }
        if (!TokenUtil.areOnSameLine(detailAST, ast.getLastChild())) {
            log(ast, MSG_KEY, ast.getText());
        }
    }
}
