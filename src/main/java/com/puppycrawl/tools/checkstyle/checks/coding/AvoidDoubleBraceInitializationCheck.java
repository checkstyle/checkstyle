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

import java.util.BitSet;
import java.util.function.Predicate;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Detects double brace initialization.
 * </p>
 * <p>
 * Rationale: Double brace initialization (set of
 * <a href="https://docs.oracle.com/javase/specs/jls/se12/html/jls-8.html#jls-8.6">
 * Instance Initializers</a> in class body) may look cool, but it is considered as anti-pattern
 * and should be avoided.
 * This is also can lead to a hard-to-detect memory leak, if the anonymous class instance is
 * returned outside and other object(s) hold reference to it.
 * Created anonymous class is not static, it holds an implicit reference to the outer class
 * instance.
 * See this
 * <a href="https://blog.jooq.org/dont-be-clever-the-double-curly-braces-anti-pattern/">
 * blog post</a> and
 * <a href="https://www.baeldung.com/java-double-brace-initialization">
 * article</a> for more details.
 * Check ignores any comments and semicolons in class body.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;AvoidDoubleBraceInitialization&quot;/&gt;
 * </pre>
 * <p>
 * Which results in the following violations:
 * </p>
 * <pre>
 * class MyClass {
 *     List&lt;Integer&gt; list1 = new ArrayList&lt;&gt;() { // violation
 *         {
 *             add(1);
 *         }
 *     };
 *     List&lt;String&gt; list2 = new ArrayList&lt;&gt;() { // violation
 *         ;
 *         // comments and semicolons are ignored
 *         {
 *             add("foo");
 *         }
 *     };
 * }
 * </pre>
 * <p>
 * Check only looks for double brace initialization and it ignores cases
 * where the anonymous class has fields or methods.
 * Though these might create the same memory issues as double brace,
 * the extra class members can produce side effects if changed incorrectly.
 * </p>
 * <pre>
 * class MyClass {
 *     List&lt;Object&gt; list = new ArrayList&lt;&gt;() { // OK, not pure double brace pattern
 *         private int field;
 *         {
 *             add(new Object());
 *         }
 *     };
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
 * {@code avoid.double.brace.init}
 * </li>
 * </ul>
 *
 * @since 8.30
 */
@StatelessCheck
public class AvoidDoubleBraceInitializationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "avoid.double.brace.init";

    /**
     * Set of token types that are used in {@link #HAS_MEMBERS} predicate.
     */
    private static final BitSet IGNORED_TYPES = TokenUtil.asBitSet(
        TokenTypes.INSTANCE_INIT,
        TokenTypes.SEMI,
        TokenTypes.LCURLY,
        TokenTypes.RCURLY
    );

    /**
     * Predicate for tokens that is used in {@link #hasOnlyInitialization(DetailAST)}.
     */
    private static final Predicate<DetailAST> HAS_MEMBERS =
        token -> !IGNORED_TYPES.get(token.getType());

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
        return new int[] {TokenTypes.OBJBLOCK};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.LITERAL_NEW
            && hasOnlyInitialization(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks that block has at least one instance init block and no other class members.
     *
     * @param objBlock token to check
     * @return true if there is least one instance init block and no other class members,
     *     false otherwise
     */
    private static boolean hasOnlyInitialization(DetailAST objBlock) {
        final boolean hasInitBlock = objBlock.findFirstToken(TokenTypes.INSTANCE_INIT) != null;
        return hasInitBlock
                  && TokenUtil.findFirstTokenByPredicate(objBlock, HAS_MEMBERS).isEmpty();
    }
}
