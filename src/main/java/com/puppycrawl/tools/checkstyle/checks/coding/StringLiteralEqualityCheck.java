////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that string literals are not used with <code>==</code> or <code>&#33;=</code>.
 * Since <code>==</code> will compare the object references, not the actual value of the strings,
 * <code>String.equals()</code> should be used.
 * More information can be found
 * <a href="http://www.thejavageek.com/2013/07/27/string-comparison-with-equals-and-assignment-operator/">
 * in this article</a>.
 * </p>
 * <p>
 * Rationale: Novice Java programmers often use code like:
 * </p>
 * <pre>
 * if (x == "something")
 * </pre>
 * <p>
 * when they mean
 * </p>
 * <pre>
 * if ("something".equals(x))
 * </pre>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;StringLiteralEquality&quot;/&gt;
 * </pre>
 * <p>
 * Examples of violations:
 * </p>
 * <pre>
 * String status = "pending";
 *
 * if (status == "done") {} // violation
 *
 * while (status != "done") {} // violation
 *
 * boolean flag = (status == "done"); // violation
 *
 * boolean flag = (status.equals("done")); // OK
 *
 * String name = "X";
 *
 * if (name == getName()) {}
 * // OK, limitation that check cannot tell runtime type returned from method call
 * </pre>
 * @since 3.2
 * @noinspection HtmlTagCanBeJavadocTag
 */
@StatelessCheck
public class StringLiteralEqualityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "string.literal.equality";

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
        return new int[] {TokenTypes.EQUAL, TokenTypes.NOT_EQUAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // no need to check for nulls here, == and != always have two children
        final DetailAST firstChild = ast.getFirstChild();
        final DetailAST secondChild = firstChild.getNextSibling();

        if (firstChild.getType() == TokenTypes.STRING_LITERAL
                || secondChild.getType() == TokenTypes.STRING_LITERAL) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

}
