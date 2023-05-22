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

/**
 * <p>
 * Checks that an overriding {@code finalize()} method invokes {@code super.finalize()}.
 * Does not check native methods, as they have no possible java defined implementation.
 * </p>
 * <p>
 * References:
 * <a href="https://www.oracle.com/technical-resources/articles/javase/finalization.html">
 * How to Handle Java Finalization's Memory-Retention Issues</a>;
 * <a href="https://javarevisited.blogspot.com/2012/03/finalize-method-in-java-tutorial.html">
 * 10 points on finalize method in Java</a>.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuperFinalize&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class A {
 *      protected void finalize() throws Throwable {
 *         System.out.println("In finalize block");
 *          super.finalize(); // OK, calls super.finalize()
 *      }
 * }
 * public class B {
 *      protected void finalize() throws Throwable { // violation
 *          System.out.println("In finalize block");
 *      }
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
 * {@code missing.super.call}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class SuperFinalizeCheck extends AbstractSuperCheck {

    @Override
    protected String getMethodName() {
        return "finalize";
    }

}
