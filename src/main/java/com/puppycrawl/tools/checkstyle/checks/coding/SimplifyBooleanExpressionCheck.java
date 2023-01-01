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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for over-complicated boolean expressions. Currently, it finds code like
 * {@code if (b == true)}, {@code b || true}, {@code !false},
 * {@code boolean a = q > 12 ? true : false},
 * etc.
 * </p>
 * <p>
 * Rationale: Complex boolean logic makes code hard to understand and maintain.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SimplifyBooleanExpression&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void bar() {
 *
 *     boolean a, b;
 *     Foo c, d, e;
 *
 *     if (!false) {};       // violation, can be simplified to true
 *
 *     if (a == true) {};    // violation, can be simplified to a
 *     if (a == b) {};       // OK
 *     if (a == false) {};   // violation, can be simplified to !a
 *     if (!(a != true)) {}; // violation, can be simplified to a
 *
 *     e = (a || b) ? c : d;     // OK
 *     e = (a || false) ? c : d; // violation, can be simplified to a
 *     e = (a &amp;&amp; b) ? c : d;     // OK
 *
 *     int s = 12;
 *     boolean m = s &gt; 1 ? true : false; // violation, can be simplified to s &gt; 1
 *     boolean f = c == null ? false : c.someMethod(); // OK
 *  }
 *
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
 * {@code simplify.expression}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class SimplifyBooleanExpressionCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "simplify.expression";

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
        return new int[] {TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_FALSE};
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
                log(parent, MSG_KEY);
                break;
            case TokenTypes.QUESTION:
                final DetailAST nextSibling = ast.getNextSibling();
                if (TokenUtil.isBooleanLiteralType(parent.getFirstChild().getType())
                        || nextSibling != null
                        && TokenUtil.isBooleanLiteralType(
                        nextSibling.getNextSibling().getType())) {
                    log(parent, MSG_KEY);
                }
                break;
            default:
                break;
        }
    }

}
