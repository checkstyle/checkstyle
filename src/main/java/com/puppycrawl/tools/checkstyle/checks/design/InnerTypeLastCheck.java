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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks nested (internal) classes/interfaces are declared at the bottom of the
 * primary (top-level) class after all init and static init blocks,
 * method, constructor and field declarations.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;InnerTypeLast&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Test {
 *     private String s; // OK
 *     class InnerTest1 {}
 *     public void test() {} // violation, method should be declared before inner types.
 * }
 *
 * class Test2 {
 *     static {}; // OK
 *     class InnerTest1 {}
 *     public Test2() {} // violation, constructor should be declared before inner types.
 * }
 *
 * class Test3 {
 *     private String s; // OK
 *     public void test() {} // OK
 *     class InnerTest1 {}
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
 * {@code arrangement.members.before.inner}
 * </li>
 * </ul>
 *
 * @since 5.2
 */
@FileStatefulCheck
public class InnerTypeLastCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "arrangement.members.before.inner";

    /** List of class member tokens. */
    private static final List<Integer> CLASS_MEMBER_TOKENS = Arrays.asList(
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.COMPACT_CTOR_DEF
    );

    /** Meet a root class. */
    private boolean rootClass = true;

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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        rootClass = true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // First root class
        if (rootClass) {
            rootClass = false;
        }
        else {
            DetailAST nextSibling = ast.getNextSibling();
            while (nextSibling != null) {
                if (!ScopeUtil.isInCodeBlock(ast)
                    && CLASS_MEMBER_TOKENS.contains(nextSibling.getType())) {
                    log(nextSibling, MSG_KEY);
                }
                nextSibling = nextSibling.getNextSibling();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        // Is this a root class
        if (ast.getParent() == null) {
            rootClass = true;
        }
    }

}
