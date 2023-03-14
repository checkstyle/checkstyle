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
 * Checks if unnecessary semicolon is used after type member declaration.
 * </p>
 * <p>
 * This check is not applicable to empty statements (unnecessary semicolons inside methods or
 * init blocks),
 * <a href="https://checkstyle.org/config_coding.html#EmptyStatement">EmptyStatement</a>
 * is responsible for it.
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
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessarySemicolonAfterTypeMemberDeclaration&quot;/&gt;
 * </pre>
 * <p>
 * Results in following:
 * </p>
 * <pre>
 * class A {
 *     ; // violation, standalone semicolon
 *     {}; // violation, extra semicolon after init block
 *     static {}; // violation, extra semicolon after static init block
 *     A(){}; // violation, extra semicolon after constructor definition
 *     void method() {}; // violation, extra semicolon after method definition
 *     int field = 10;; // violation, extra semicolon after field declaration
 *
 *     {
 *         ; // no violation, it is empty statement inside init block
 *     }
 *
 *     static {
 *         ; // no violation, it is empty statement inside static init block
 *     }
 *
 *     void anotherMethod() {
 *         ; // no violation, it is empty statement
 *         if(true); // no violation, it is empty statement
 *     }
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
 * {@code unnecessary.semicolon}
 * </li>
 * </ul>
 *
 * @since 8.24
 */
@StatelessCheck
public final class UnnecessarySemicolonAfterTypeMemberDeclarationCheck extends AbstractCheck {

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
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.RECORD_DEF:
                checkTypeDefinition(ast);
                break;
            case TokenTypes.VARIABLE_DEF:
                checkVariableDefinition(ast);
                break;
            case TokenTypes.ENUM_CONSTANT_DEF:
                checkEnumConstant(ast);
                break;
            default:
                checkTypeMember(ast);
                break;
        }
    }

    /**
     * Checks if type member has unnecessary semicolon.
     *
     * @param ast type member
     */
    private void checkTypeMember(DetailAST ast) {
        if (isSemicolon(ast.getNextSibling())) {
            log(ast.getNextSibling(), MSG_SEMI);
        }
    }

    /**
     * Checks if type definition has unnecessary semicolon.
     *
     * @param ast type definition
     */
    private void checkTypeDefinition(DetailAST ast) {
        if (!ScopeUtil.isOuterMostType(ast) && isSemicolon(ast.getNextSibling())) {
            log(ast.getNextSibling(), MSG_SEMI);
        }
        final DetailAST firstMember =
            ast.findFirstToken(TokenTypes.OBJBLOCK).getFirstChild().getNextSibling();
        if (isSemicolon(firstMember) && !ScopeUtil.isInEnumBlock(firstMember)) {
            log(firstMember, MSG_SEMI);
        }
    }

    /**
     * Checks if variable definition has unnecessary semicolon.
     *
     * @param variableDef variable definition
     */
    private void checkVariableDefinition(DetailAST variableDef) {
        if (isSemicolon(variableDef.getLastChild()) && isSemicolon(variableDef.getNextSibling())) {
            log(variableDef.getNextSibling(), MSG_SEMI);
        }
    }

    /**
     * Checks if enum constant has unnecessary semicolon.
     *
     * @param ast enum constant
     */
    private void checkEnumConstant(DetailAST ast) {
        final DetailAST next = ast.getNextSibling();
        if (isSemicolon(next) && isSemicolon(next.getNextSibling())) {
            log(next.getNextSibling(), MSG_SEMI);
        }
    }

    /**
     * Checks that {@code ast} is a semicolon.
     *
     * @param ast token to check
     * @return true if ast is semicolon, false otherwise
     */
    private static boolean isSemicolon(DetailAST ast) {
        return ast.getType() == TokenTypes.SEMI;
    }
}
