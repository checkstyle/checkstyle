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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for long anonymous inner classes.
 * </div>
 *
 * <p>
 * Rationale: If an anonymous inner class becomes very long
 * it is hard to understand and to see the flow of the method
 * where the class is defined. Therefore, long anonymous inner
 * classes should usually be refactored into a named inner class.
 * See also Bloch, Effective Java, p. 93.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of lines allowed.
 * Type is {@code int}.
 * Default value is {@code 20}.
 * </li>
 * </ul>
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
 * {@code maxLen.anonInner}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class AnonInnerLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.anonInner";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX = 20;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX;

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
        return new int[] {TokenTypes.LITERAL_NEW};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                openingBrace.findFirstToken(TokenTypes.RCURLY);
            final int length =
                closingBrace.getLineNo() - openingBrace.getLineNo() + 1;
            if (length > max) {
                log(ast, MSG_KEY, length, max);
            }
        }
    }

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of an anonymous inner class.
     * @since 3.2
     */
    public void setMax(int length) {
        max = length;
    }

}
