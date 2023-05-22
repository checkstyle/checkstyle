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
 * Checks that an overriding {@code clone()} method invokes {@code super.clone()}.
 * Does not check native methods, as they have no possible java defined implementation.
 * </p>
 * <p>
 * Reference:
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html#clone%28%29">
 * Object.clone()</a>.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuperClone&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class A {
 *
 *  public Object clone() { // OK
 *   return super.clone();
 *  }
 * }
 *
 * class B {
 * private int b;
 *
 *  public B clone() { // violation, does not call super.clone()
 *   B other = new B();
 *   other.b = this.b;
 *   return other;
 *  }
 * }
 *
 * class C {
 *
 *  public C clone() { // OK
 *   return (C) super.clone();
 *  }
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
public class SuperCloneCheck extends AbstractSuperCheck {

    @Override
    protected String getMethodName() {
        return "clone";
    }

}
