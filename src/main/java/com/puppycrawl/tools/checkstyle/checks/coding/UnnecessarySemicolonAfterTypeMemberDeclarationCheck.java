///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
 * <div>
 * Checks if unnecessary semicolon is used after type member declaration.
 * </div>
 *
 * <p>
 * Notes:
 * This check is not applicable to empty statements (unnecessary semicolons inside methods or
 * init blocks),
 * <a href="https://checkstyle.org/checks/coding/emptystatement.html">EmptyStatement</a>
 * is responsible for it.
 * </p>
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
            case TokenTypes.CLASS_DEF,
                 TokenTypes.INTERFACE_DEF,
                 TokenTypes.ENUM_DEF,
                 TokenTypes.ANNOTATION_DEF,
                 TokenTypes.RECORD_DEF -> checkTypeDefinition(ast);
            case TokenTypes.VARIABLE_DEF -> checkVariableDefinition(ast);
            case TokenTypes.ENUM_CONSTANT_DEF -> checkEnumConstant(ast);
            default -> checkTypeMember(ast);
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
        return ast != null && ast.getType() == TokenTypes.SEMI;
    }
}
