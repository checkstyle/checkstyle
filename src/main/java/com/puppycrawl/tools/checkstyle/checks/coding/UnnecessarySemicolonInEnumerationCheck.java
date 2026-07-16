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
 * Checks if unnecessary semicolon is in enum definitions.
 * Semicolon is not needed if enum body contains only enum constants.
 * </div>
 *
 * @since 8.22
 */
@StatelessCheck
public final class UnnecessarySemicolonInEnumerationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEMI = "unnecessary.semicolon";

    /**
     * Creates a new {@code UnnecessarySemicolonInEnumerationCheck} instance.
     */
    public UnnecessarySemicolonInEnumerationCheck() {
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
        return new int[] {
            TokenTypes.ENUM_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST enumBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        final boolean hasMembers = hasMembersAfterConstants(enumBlock);
        DetailAST sibling = enumBlock.getFirstChild();
        while (sibling != null) {
            if (sibling.getType() == TokenTypes.SEMI
                    && (!hasMembers || isPrecededBySemi(sibling))) {
                log(sibling, MSG_SEMI);
            }
            sibling = sibling.getNextSibling();
        }
    }

    /**
     * Checks if enum body contains any member (method, constructor, field, etc.)
     * in addition to enum constants and semicolons.
     *
     * @param enumBlock the enum body to check
     * @return true if enum body has members, false if it only has constants and semicolons.
     */
    private static boolean hasMembersAfterConstants(DetailAST enumBlock) {
        boolean result = false;
        DetailAST sibling = enumBlock.getFirstChild();
        while (sibling != null) {
            final int type = sibling.getType();
            if (type != TokenTypes.LCURLY
                    && type != TokenTypes.RCURLY
                    && type != TokenTypes.ENUM_CONSTANT_DEF
                    && type != TokenTypes.SEMI
                    && type != TokenTypes.COMMA) {
                result = true;
                break;
            }
            sibling = sibling.getNextSibling();
        }
        return result;
    }

    /**
     * Checks if semicolon is immediately preceded by another semicolon,
     * making it a redundant duplicate.
     *
     * @param ast semicolon to check
     * @return true if previous sibling is also a semicolon, false otherwise.
     */
    private static boolean isPrecededBySemi(DetailAST ast) {
        final DetailAST prev = ast.getPreviousSibling();
        return prev.getType() == TokenTypes.SEMI;
    }

}
