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
        return new int[] {
            TokenTypes.EMPTY_STAT,
            TokenTypes.SEMI,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isEmptyStatement(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if token represents an empty statement that should be logged.
     *
     * @param ast semicolon or empty statement token
     * @return {@code true} if token represents an empty statement
     */
    private static boolean isEmptyStatement(DetailAST ast) {
        final boolean result;
        if (ast.getType() == TokenTypes.EMPTY_STAT) {
            result = true;
        }
        else {
            final DetailAST parent = ast.getParent();
            final int parentType = parent.getType();
            result = parentType == TokenTypes.COMPILATION_UNIT
                || parentType == TokenTypes.OBJBLOCK
                    && !isEnumConstantsTerminator(ast);
        }
        return result;
    }

    /**
     * Checks if a semicolon in enum block is the enum constants terminator.
     *
     * @param semicolon semicolon token
     * @return {@code true} if semicolon is enum constants terminator
     */
    private static boolean isEnumConstantsTerminator(DetailAST semicolon) {
        boolean result = false;

        if (semicolon != null) {
            final DetailAST parent = semicolon.getParent();
            final DetailAST grandParent = parent == null ? null : parent.getParent();
            final boolean isSemicolonInEnumObjectBlock = parent != null
                && parent.getType() == TokenTypes.OBJBLOCK
                && grandParent != null
                && grandParent.getType() == TokenTypes.ENUM_DEF;

            if (isSemicolonInEnumObjectBlock) {
                final DetailAST previousSibling = semicolon.getPreviousSibling();
                final DetailAST nextSibling = semicolon.getNextSibling();
                result = previousSibling != null
                    && (previousSibling.getType() == TokenTypes.ENUM_CONSTANT_DEF
                        || previousSibling.getType() == TokenTypes.COMMA
                        || previousSibling.getType() == TokenTypes.LCURLY
                            && nextSibling != null
                            && nextSibling.getType() != TokenTypes.RCURLY);
            }
        }
        return result;
    }

}
