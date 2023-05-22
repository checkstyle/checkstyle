///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * Checks if unnecessary semicolon is in enum definitions.
 * Semicolon is not needed if enum body contains only enum constants.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessarySemicolonInEnumeration&quot;/&gt;
 * </pre>
 * <p>
 * Example of violations
 * </p>
 * <pre>
 * enum One {
 *     A,B; // violation
 * }
 * enum Two {
 *     A,B,; // violation
 * }
 * enum Three {
 *     A,B(); // violation
 * }
 * enum Four {
 *     A,B{}; // violation
 * }
 * enum Five {
 *     A,
 *     B
 *     ; // violation
 * }
 * </pre>
 * <p>
 * Example of good cases
 * </p>
 * <pre>
 * enum Normal {
 *     A,
 *     B,
 *     ; // required ";", no violation
 *     Normal(){}
 * }
 * enum NoSemicolon {
 *     A, B // only enum constants, no semicolon required
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unnecessary.semicolon}
 * </li>
 * </ul>
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
        final DetailAST semicolon = enumBlock.findFirstToken(TokenTypes.SEMI);
        if (semicolon != null && isEndOfEnumerationAfter(semicolon)) {
            log(semicolon, MSG_SEMI);
        }
    }

    /**
     * Checks if enum body has no code elements after enum constants semicolon.
     *
     * @param ast semicolon in enum constants definition end
     * @return true if there is no code elements, false otherwise.
     */
    private static boolean isEndOfEnumerationAfter(DetailAST ast) {
        return ast.getNextSibling().getType() == TokenTypes.RCURLY;
    }
}
