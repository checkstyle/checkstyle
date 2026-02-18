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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Detects empty statements (standalone {@code ";"} semicolon).
 * Empty statements often introduce bugs that are hard to spot
 * </div>
 *
 * @since 3.1
 */
@StatelessCheck
public class EmptyStatementCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "empty.statement";

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
        return new int[] {TokenTypes.EMPTY_STAT, TokenTypes.SEMI};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.EMPTY_STAT) {
            log(ast, MSG_KEY);
        }
        else if (isEmptyStatementInTypeBody(ast)) {
            log(ast, MSG_KEY);
        }
        else if (isEmptyStatementAtCompilationUnitLevel(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if a semicolon token represents an empty statement at compilation unit level.
     *
     * @param ast semicolon token
     * @return {@code true} if semicolon is at compilation unit level
     */
    private static boolean isEmptyStatementAtCompilationUnitLevel(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.COMPILATION_UNIT;
    }

    /**
     * Checks if a semicolon token represents an empty statement in a type body.
     *
     * @param ast semicolon token
     * @return {@code true} if semicolon is inside a type body except enum block
     */
    private static boolean isEmptyStatementInTypeBody(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.OBJBLOCK
            && !ScopeUtil.isInEnumBlock(ast);
    }

}
