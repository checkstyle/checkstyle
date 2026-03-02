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
            && !isEnumConstantsTerminator(ast);
    }

    /**
     * Checks if a semicolon in enum block is the enum constants terminator.
     *
     * @param ast semicolon token
     * @return {@code true} if semicolon is enum constants terminator
     */
    private static boolean isEnumConstantsTerminator(DetailAST ast) {
        final DetailAST semicolon = getSemicolonToken(ast);
        boolean result = false;

        if (isSemicolonInEnumObjectBlock(semicolon)) {
            final DetailAST previousSibling = semicolon.getPreviousSibling();
            final DetailAST nextSibling = semicolon.getNextSibling();
            result = isAfterEnumConstants(previousSibling)
                || isNoConstantsWithMembers(previousSibling, nextSibling);
        }

        return result;
    }

    /**
     * Checks if semicolon is directly inside enum object block.
     *
     * @param semicolon semicolon token
     * @return {@code true} if semicolon is inside enum object block
     */
    private static boolean isSemicolonInEnumObjectBlock(DetailAST semicolon) {
        return semicolon != null
            && semicolon.getParent() != null
            && semicolon.getParent().getType() == TokenTypes.OBJBLOCK
            && semicolon.getParent().getParent() != null
            && semicolon.getParent().getParent().getType() == TokenTypes.ENUM_DEF;
    }

    /**
     * Checks if previous sibling indicates enum constants list terminator.
     *
     * @param previousSibling previous sibling before semicolon
     * @return {@code true} if semicolon follows enum constants
     */
    private static boolean isAfterEnumConstants(DetailAST previousSibling) {
        return previousSibling != null
            && (previousSibling.getType() == TokenTypes.ENUM_CONSTANT_DEF
            || previousSibling.getType() == TokenTypes.COMMA);
    }

    /**
     * Checks if semicolon terminates empty enum-constants list before members.
     *
     * @param previousSibling previous sibling before semicolon
     * @param nextSibling next sibling after semicolon
     * @return {@code true} if semicolon separates constants section and members
     */
    private static boolean isNoConstantsWithMembers(DetailAST previousSibling,
                                                    DetailAST nextSibling) {
        return previousSibling != null
            && previousSibling.getType() == TokenTypes.LCURLY
            && nextSibling != null
            && nextSibling.getType() != TokenTypes.RCURLY;
    }

    /**
     * Returns semicolon token from semicolon or empty statement token.
     *
     * @param ast semicolon or empty statement token
     * @return semicolon token, or {@code null} if token has no semicolon
     */
    private static DetailAST getSemicolonToken(DetailAST ast) {
        DetailAST result = ast;
        if (ast.getType() == TokenTypes.EMPTY_STAT) {
            result = ast.findFirstToken(TokenTypes.SEMI);
        }
        return result;
    }

}
