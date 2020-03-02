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
 * <p>Example for violation:</p>
 * <pre>
 * public class Test {
 *
 *
 *     public static void main(String[] args) {
 *         Test test = new Test();
 *         test.finalize(); //usage of the function does not affect the violation trigger, only triggered by overriding finalize() in Object
 *
 *         System.gc(); //used to trigger Java garbage collector to clear the memory using java garbage collector thread
 *     }
 *
 *     public void finalize() { //causes NoFinalize violation
 *
 *         System.out.println("Finalize() is overridden");//just to show that the function got overridden
 *     }
 * }
 *  * </pre>
 *
 * <p>Example for non-violation:</p>
 * <pre>
 * public class Test2 {
 *     public static void main(String[] args) throws Throwable { //"throws throwable" as finalize itself throws exception
 *         Test2 test2 = new Test2();
 *         test2.finalize();//the usage of finalize() does not trigger the NoFinalizer violation
 *
 *         System.gc();
 *     }
 *
 *     public void finalize(Object object) { //overloading does not cause any NoFinalizer violation
 *         System.out.println("overloading finalize() in Object ");
 *     }
 * }
 * </pre>
 *
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
        return new int[]{TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST aAST) {
        final DetailAST mid = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = mid.getText();

        if ("finalize".equals(name)) {
            final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
            final boolean hasEmptyParamList = params.findFirstToken(TokenTypes.PARAMETER_DEF) == null;
            if (hasEmptyParamList) {
                log(aAST.getLineNo(), MSG_KEY);
            }
        }
    }

}
