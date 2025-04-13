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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the policy on the padding of parentheses for typecasts. That is, whether a space
 * is required after a left parenthesis and before a right parenthesis, or such
 * spaces are forbidden.
 * </div>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to pad parentheses.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption}.
 * Default value is {@code nospace}.
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
 * {@code ws.followed}
 * </li>
 * <li>
 * {@code ws.notFollowed}
 * </li>
 * <li>
 * {@code ws.notPreceded}
 * </li>
 * <li>
 * {@code ws.preceded}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
public class TypecastParenPadCheck extends AbstractParenPadCheck {

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.RPAREN, TokenTypes.TYPECAST};
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
    public void visitToken(DetailAST ast) {
        // Strange logic in this method to guard against checking RPAREN tokens
        // that are not associated with a TYPECAST token.
        if (ast.getType() == TokenTypes.TYPECAST) {
            processLeft(ast);
        }
        else if (ast.getParent().getType() == TokenTypes.TYPECAST
            && ast.getParent().findFirstToken(TokenTypes.RPAREN) == ast) {
            processRight(ast);
        }
    }

}
