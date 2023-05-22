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

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Checks that classes and records which define a covariant {@code equals()} method
 * also override method {@code equals(Object)}.
 * </p>
 * <p>
 * Covariant {@code equals()} - method that is similar to {@code equals(Object)},
 * but with a covariant parameter type (any subtype of Object).
 * </p>
 * <p>
 * <strong>Notice</strong>: the enums are also checked,
 * even though they cannot override {@code equals(Object)}.
 * The reason is to point out that implementing {@code equals()} in enums
 * is considered an awful practice: it may cause having two different enum values
 * that are equal using covariant enum method, and not equal when compared normally.
 * </p>
 * <p>
 * Inspired by <a href="https://www.cs.jhu.edu/~daveho/pubs/oopsla2004.pdf">
 * Finding Bugs is Easy, chapter '4.5 Bad Covariant Definition of Equals (Eq)'</a>:
 * </p>
 * <p>
 * Java classes and records may override the {@code equals(Object)} method to define
 * a predicate for object equality. This method is used by many of the Java
 * runtime library classes; for example, to implement generic containers.
 * </p>
 * <p>
 * Programmers sometimes mistakenly use the type of their class {@code Foo}
 * as the type of the parameter to {@code equals()}:
 * </p>
 * <pre>
 * public boolean equals(Foo obj) {...}
 * </pre>
 * <p>
 * This covariant version of {@code equals()} does not override the version in
 * the {@code Object} class, and it may lead to unexpected behavior at runtime,
 * especially if the class is used with one of the standard collection classes
 * which expect that the standard {@code equals(Object)} method is overridden.
 * </p>
 * <p>
 * This kind of bug is not obvious because it looks correct, and in circumstances
 * where the class is accessed through the references of the class type (rather
 * than a supertype), it will work correctly. However, the first time it is used
 * in a container, the behavior might be mysterious. For these reasons, this type
 * of bug can elude testing and code inspections.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;CovariantEquals&quot;/&gt;
 * </pre>
 * <p>
 * For example:
 * </p>
 * <pre>
 * class Test {
 *   public boolean equals(Test i) {  // violation
 *     return false;
 *   }
 * }
 * </pre>
 * <p>
 * The same class without violations:
 * </p>
 * <pre>
 * class Test {
 *   public boolean equals(Test i) {  // no violation
 *     return false;
 *   }
 *
 *   public boolean equals(Object i) {
 *     return false;
 *   }
 * }
 * </pre>
 * <p>
 * Another example:
 * </p>
 * <pre>
 * record Test(String str) {
 *   public boolean equals(Test r) {  // violation
 *     return false;
 *   }
 * }
 * </pre>
 * <p>
 * The same record without violations:
 * </p>
 * <pre>
 * record Test(String str) {
 *   public boolean equals(Test r) {  // no violation
 *     return false;
 *   }
 *
 *   public boolean equals(Object r) {
 *     return false;
 *   }
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
 * {@code covariant.equals}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class CovariantEqualsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "covariant.equals";

    /** Set of equals method definitions. */
    private final Set<DetailAST> equalsMethods = new HashSet<>();

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        equalsMethods.clear();

        // examine method definitions for equals methods
        final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = objBlock.getFirstChild();
            boolean hasEqualsObject = false;
            while (child != null) {
                if (CheckUtil.isEqualsMethod(child)) {
                    if (isFirstParameterObject(child)) {
                        hasEqualsObject = true;
                    }
                    else {
                        equalsMethods.add(child);
                    }
                }
                child = child.getNextSibling();
            }

            // report equals method definitions
            if (!hasEqualsObject) {
                for (DetailAST equalsAST : equalsMethods) {
                    final DetailAST nameNode = equalsAST
                            .findFirstToken(TokenTypes.IDENT);
                    log(nameNode, MSG_KEY);
                }
            }
        }
    }

    /**
     * Tests whether a method's first parameter is an Object.
     *
     * @param methodDefAst the method definition AST to test.
     *     Precondition: ast is a TokenTypes.METHOD_DEF node.
     * @return true if ast has first parameter of type Object.
     */
    private static boolean isFirstParameterObject(DetailAST methodDefAst) {
        final DetailAST paramsNode = methodDefAst.findFirstToken(TokenTypes.PARAMETERS);

        // parameter type "Object"?
        final DetailAST paramNode =
            paramsNode.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST typeNode = paramNode.findFirstToken(TokenTypes.TYPE);
        final FullIdent fullIdent = FullIdent.createFullIdentBelow(typeNode);
        final String name = fullIdent.getText();
        return "Object".equals(name) || "java.lang.Object".equals(name);
    }

}
