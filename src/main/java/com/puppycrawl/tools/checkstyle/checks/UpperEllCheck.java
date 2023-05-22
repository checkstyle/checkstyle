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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that long constants are defined with an upper ell. That is {@code 'L'}
 * and not {@code 'l'}. This is in accordance with the Java Language Specification,
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.1">
 * Section 3.10.1</a>.
 * </p>
 * <p>
 * Rationale: The lower-case ell {@code 'l'} looks a lot like {@code 1}.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UpperEll&quot;/&gt;
 * </pre>
 * <pre>
 * class Test {
 *   long var1 = 508987; // OK
 *   long var2 = 508987l; // violation
 *   long var3 = 508987L; // OK
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
 * {@code upperEll}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class UpperEllCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "upperEll";

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
        return new int[] {TokenTypes.NUM_LONG};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (CommonUtil.endsWithChar(ast.getText(), 'l')) {
            log(ast, MSG_KEY);
        }
    }

}
