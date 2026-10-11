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

    /**
     * Creates a new {@code EmptyStatementCheck} instance.
     */
    public EmptyStatementCheck() {
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
        return new int[] {TokenTypes.EMPTY_STAT, TokenTypes.SEMI};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.EMPTY_STAT || isEmptyDeclaration(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks whether a semicolon is a standalone empty declaration, i.e. it
     * appears at the top level of a compilation unit or directly in a type body.
     *
     * @param semi semicolon token
     * @return {@code true} if semicolon is an empty declaration
     */
    private static boolean isEmptyDeclaration(DetailAST semi) {
        final int parentType = semi.getParent().getType();
        return parentType == TokenTypes.COMPILATION_UNIT
            || parentType == TokenTypes.COMPACT_COMPILATION_UNIT
            || parentType == TokenTypes.OBJBLOCK && !isEnumConstantsTerminator(semi);
    }

    /**
     * Checks whether a semicolon in a type body terminates the list of enum constants.
     * Such a semicolon is part of the enum syntax and is not an empty declaration.
     *
     * @param semi semicolon token whose parent is an object block
     * @return {@code true} if semicolon terminates the enum constants list
     */
    private static boolean isEnumConstantsTerminator(DetailAST semi) {
        final int typeDefType = semi.getParent().getParent().getType();
        final int previousType = semi.getPreviousSibling().getType();
        return typeDefType == TokenTypes.ENUM_DEF
            && (previousType == TokenTypes.LCURLY
                || previousType == TokenTypes.ENUM_CONSTANT_DEF
                || previousType == TokenTypes.COMMA);
    }

}
