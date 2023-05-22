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

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that each top-level class, interface, enum
 * or annotation resides in a source file of its own.
 * Official description of a 'top-level' term:
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-7.html#jls-7.6">
 * 7.6. Top Level Type Declarations</a>. If file doesn't contain
 * public class, interface, enum or annotation, top-level type is the first type in file.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;OneTopLevelClass&quot;/&gt;
 * </pre>
 * <p>
 * <b>ATTENTION:</b> This Check does not support customization of validated tokens,
 * so do not use the "tokens" property.
 * </p>
 * <p>
 * An example of code with violations:
 * </p>
 * <pre>
 * public class Foo { // OK, first top-level class
 *   // methods
 * }
 *
 * class Foo2 { // violation, second top-level class
 *   // methods
 * }
 *
 * record Foo3 { // violation, third top-level "class"
 *     // methods
 * }
 * </pre>
 * <p>
 * An example of code without public top-level type:
 * </p>
 * <pre>
 * class Foo { // OK, first top-level class
 *   // methods
 * }
 *
 * class Foo2 { // violation, second top-level class
 *   // methods
 * }
 * </pre>
 * <p>
 * An example of code without violations:
 * </p>
 * <pre>
 * public class Foo { // OK, only one top-level class
 *   // methods
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
 * {@code one.top.level.class}
 * </li>
 * </ul>
 *
 * @since 5.8
 */
@StatelessCheck
public class OneTopLevelClassCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "one.top.level.class";

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
            TokenTypes.COMPILATION_UNIT,
        };
    }

    @Override
    public void visitToken(DetailAST compilationUnit) {
        DetailAST currentNode = compilationUnit.getFirstChild();

        boolean publicTypeFound = false;
        DetailAST firstType = null;

        while (currentNode != null) {
            if (isTypeDef(currentNode)) {
                if (isPublic(currentNode)) {
                    // log the first type later
                    publicTypeFound = true;
                }
                if (firstType == null) {
                    // first type is set aside
                    firstType = currentNode;
                }
                else if (!isPublic(currentNode)) {
                    // extra non-public type, log immediately
                    final String typeName = currentNode
                        .findFirstToken(TokenTypes.IDENT).getText();
                    log(currentNode, MSG_KEY, typeName);
                }
            }
            currentNode = currentNode.getNextSibling();
        }

        // if there was a public type and first type is non-public, log it
        if (publicTypeFound && !isPublic(firstType)) {
            final String typeName = firstType
                .findFirstToken(TokenTypes.IDENT).getText();
            log(firstType, MSG_KEY, typeName);
        }
    }

    /**
     * Checks if an AST node is a type definition.
     *
     * @param node AST node to check.
     * @return true if the node is a type (class, enum, interface, annotation) definition.
     */
    private static boolean isTypeDef(DetailAST node) {
        return TokenUtil.isTypeDeclaration(node.getType());
    }

    /**
     * Checks if a type is public.
     *
     * @param typeDef type definition node.
     * @return true if a type has a public access level modifier.
     */
    private static boolean isPublic(DetailAST typeDef) {
        final DetailAST modifiers =
                typeDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;
    }

}
