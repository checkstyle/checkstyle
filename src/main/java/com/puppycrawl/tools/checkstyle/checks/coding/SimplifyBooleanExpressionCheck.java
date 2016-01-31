////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for overly complicated boolean expressions. Currently finds code like
 * {@code if (b == true)}, {@code b || true}, {@code !false},
 * etc.
 * </p>
 * <p>
 * Rationale: Complex boolean logic makes code hard to understand and maintain.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="SimplifyBooleanExpression"/&gt;
 * </pre>
 * @author lkuehne
 */
public class SimplifyBooleanExpressionCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "simplify.expression";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_FALSE};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        switch (parent.getType()) {
            case TokenTypes.NOT_EQUAL:
            case TokenTypes.EQUAL:
            case TokenTypes.LNOT:
            case TokenTypes.LOR:
            case TokenTypes.LAND:
                log(parent.getLineNo(), parent.getColumnNo(),
                    MSG_KEY);
                break;
            default:
                break;
        }
    }
}
