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
 * Checks that there is no method {@code finalize} with zero parameters.
 * </p>
 * <p>
 * See
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html#finalize()">
 * Object.finalize()</a>
 * </p>
 * <p>
 * Rationale: Finalizers are unpredictable, often dangerous, and generally unnecessary.
 * Their use can cause erratic behavior, poor performance, and portability problems.
 * For more information for the finalize method and its issues, see Effective Java:
 * Programming Language Guide Third Edition by Joshua Bloch, &#167;8.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoFinalizer&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 *  public class Test {
 *
 *      protected void finalize() throws Throwable { // violation
 *          try {
 *             System.out.println("overriding finalize()");
 *          } catch (Throwable t) {
 *             throw t;
 *          } finally {
 *             super.finalize();
 *          }
 *      }
 *  }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code avoid.finalizer.method}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
public class NoFinalizerCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "avoid.finalizer.method";

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
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST mid = ast.findFirstToken(TokenTypes.IDENT);
        final String name = mid.getText();

        if ("finalize".equals(name)) {
            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean hasEmptyParamList =
                params.findFirstToken(TokenTypes.PARAMETER_DEF) == null;

            if (hasEmptyParamList) {
                log(ast, MSG_KEY);
            }
        }
    }

}
