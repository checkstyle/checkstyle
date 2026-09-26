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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks for missing Javadoc comments on module declarations.
 * </div>
 *
 * <p>
 * A module's Javadoc describes its purpose and can document the services it uses or provides.
 * This check accepts Javadoc before annotations on a module declaration, and before
 * unannotated module declarations, including open modules. Comments on module directives
 * do not document the module itself.
 * </p>
 *
 * @since 14.2.0
 */
@StatelessCheck
public class MissingJavadocModuleCheck extends AbstractCheck {

    /** A key pointing to the missing module Javadoc message. */
    public static final String MSG_MODULE_JAVADOC_MISSING = "module.javadoc.missing";

    /** Creates a new {@code MissingJavadocModuleCheck} instance. */
    public MissingJavadocModuleCheck() {
        // no code by default
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
        return new int[] {TokenTypes.MODULE_DEF};
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (JavadocUtil.getAttachedJavadocComment(ast) == null) {
            log(ast, MSG_MODULE_JAVADOC_MISSING);
        }
    }

}
