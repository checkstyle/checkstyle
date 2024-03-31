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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

/**
 * <p>
 * Checks that overloaded constructors are grouped together.
 * If there is anything between overloaded constructors ( obviously, expect comments )
 * then this check will give an error.
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
 * @since 10.15.0
 */

@FileStatefulCheck
public class ConstructorsDeclarationGroupingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "constructors.declaration.grouping";

    /** Specifies if the constructor has occurred before. */
    private boolean isCtorOccurred;

    /** Specifies the previous IDENT name. */
    private String previousIdent;

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
        return new int[]{
            TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST previousSibling = ast.getPreviousSibling();

        if (previousSibling != null) {
            final DetailAST parent = ast.getParent().getParent();
            final String ident = parent.findFirstToken(TokenTypes.IDENT).getText();

            if (!Objects.equals(ident, previousIdent)) {
                isCtorOccurred = false;
                previousIdent = ident;
            }

            final int siblingType = previousSibling.getType();

            if (siblingType != TokenTypes.CTOR_DEF && !isCtorOccurred) {
                isCtorOccurred = true;
            }
            else if (siblingType != TokenTypes.CTOR_DEF) {
                log(ast, MSG_KEY);
            }
        }

    }
}
