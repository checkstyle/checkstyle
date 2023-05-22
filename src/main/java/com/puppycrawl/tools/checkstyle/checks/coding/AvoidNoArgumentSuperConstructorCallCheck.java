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
 * Checks if call to superclass constructor without arguments is present.
 * Such invocation is redundant because constructor body implicitly
 * begins with a superclass constructor invocation {@code super();}
 * See <a href="https://docs.oracle.com/javase/specs/jls/se13/html/jls-8.html#jls-8.8.7">
 * specification</a> for detailed information.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;AvoidNoArgumentSuperConstructorCall&quot;/&gt;
 * </pre>
 * <p>
 * Example of violations
 * </p>
 * <pre>
 * class MyClass extends SomeOtherClass {
 *     MyClass() {
 *         super(); // violation
 *     }
 *
 *     MyClass(int arg) {
 *         super(arg); // OK, call with argument have to be explicit
 *     }
 *
 *     MyClass(long arg) {
 *         // OK, call is implicit
 *     }
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
 * {@code super.constructor.call}
 * </li>
 * </ul>
 *
 * @since 8.29
 */
@StatelessCheck
public final class AvoidNoArgumentSuperConstructorCallCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CTOR = "super.constructor.call";

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
            TokenTypes.SUPER_CTOR_CALL,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getFirstChild().getType() == TokenTypes.LPAREN
                && ast.findFirstToken(TokenTypes.ELIST).getChildCount() == 0) {
            log(ast, MSG_CTOR);
        }
    }
}
