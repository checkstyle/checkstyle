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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks if unnecessary semicolon is used after type declaration.
 * </p>
 * <p>
 * This check is not applicable to nested type declarations,
 * <a
 * href="https://checkstyle.org/config_coding.html#UnnecessarySemicolonAfterTypeMemberDeclaration">
 * UnnecessarySemicolonAfterTypeMemberDeclaration</a> is responsible for it.
 * </p>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessarySemicolonAfterOuterTypeDeclaration&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class A {
 *
 *     class Nested {
 *
 *     }; // OK, nested type declarations are ignored
 *
 * }; // violation
 *
 * interface B {
 *
 * }; // violation
 *
 * enum C {
 *
 * }; // violation
 *
 * {@literal @}interface D {
 *
 * }; // violation
 * </pre>
 * <p>
 * To configure the check to detect unnecessary semicolon only after top level class definitions:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessarySemicolonAfterOuterTypeDeclaration&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;CLASS_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class A {
 *
 * }; // violation
 *
 * interface B {
 *
 * }; // OK
 *
 * enum C {
 *
 * }; // OK
 *
 * {@literal @}interface D {
 *
 * }; // OK
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unnecessary.semicolon}
 * </li>
 * </ul>
 *
 * @since 8.31
 */
@StatelessCheck
public final class UnnecessarySemicolonAfterOuterTypeDeclarationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEMI = "unnecessary.semicolon";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST nextSibling = ast.getNextSibling();
        if (nextSibling != null
                && ScopeUtil.isOuterMostType(ast)
                && nextSibling.getType() == TokenTypes.SEMI) {
            log(nextSibling, MSG_SEMI);
        }
    }
}
