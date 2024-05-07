///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
 * <p>
 * Checks that all constructors are grouped together.
 * If there is any code between the constructors
 * then this check will give an error.
 * The check identifies and flags any constructors that are not grouped together.
 * The error message will specify the line number from where the constructors are separated.
 * Comments between constructors are allowed.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code constructors.declaration.grouping}
 * </li>
 * </ul>
 *
 * @since 10.17.0
 */

@StatelessCheck
public class ConstructorsDeclarationGroupingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "constructors.declaration.grouping";

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
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST currentToken = ast.findFirstToken(TokenTypes.OBJBLOCK).getFirstChild();
        DetailAST previousCtor = null;
        int firstNonCtorSiblingLineNo = -1;
        boolean ctorOccured = false;
        boolean isViolation = false;

        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.CTOR_DEF
                    || currentToken.getType() == TokenTypes.COMPACT_CTOR_DEF) {
                final DetailAST previousSibling = currentToken.getPreviousSibling();
                final boolean isCtor = previousSibling.getType() == TokenTypes.CTOR_DEF
                        || previousSibling.getType() == TokenTypes.COMPACT_CTOR_DEF;

                if (ctorOccured && !isViolation && !isCtor) {
                    firstNonCtorSiblingLineNo = previousCtor.getNextSibling().getLineNo();
                    isViolation = true;
                }

                if (isViolation) {
                    log(currentToken, MSG_KEY, firstNonCtorSiblingLineNo);
                }

                previousCtor = currentToken;
                ctorOccured = true;
            }

            currentToken = currentToken.getNextSibling();
        }
    }
}
