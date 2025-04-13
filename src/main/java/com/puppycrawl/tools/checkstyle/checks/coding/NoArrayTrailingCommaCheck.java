////
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that array initialization do not contain a trailing comma.
 * Rationale: JLS allows trailing commas in arrays and enumerations, but does not allow
 * them in other locations. To unify the coding style, the use of trailing commas should
 * be prohibited.
 * </div>
 * <pre>
 * int[] foo = new int[] {
 *   1,
 *   2
 * };
 * </pre>
 *
 * <p>
 * The check demands that there should not be any comma after the last element of an array.
 * </p>
 * <pre>
 * String[] foo = new String[] {
 *   "FOO",
 *   "BAR", //violation
 * }
 * </pre>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code no.array.trailing.comma}
 * </li>
 * </ul>
 *
 * @since 8.28
 */
@StatelessCheck
public class NoArrayTrailingCommaCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "no.array.trailing.comma";

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
        return new int[] {TokenTypes.ARRAY_INIT};
    }

    @Override
    public void visitToken(DetailAST arrayInit) {
        final DetailAST rcurly = arrayInit.findFirstToken(TokenTypes.RCURLY);
        final DetailAST previousSibling = rcurly.getPreviousSibling();
        if (previousSibling != null && previousSibling.getType() == TokenTypes.COMMA) {
            log(previousSibling, MSG_KEY);
        }
    }
}
