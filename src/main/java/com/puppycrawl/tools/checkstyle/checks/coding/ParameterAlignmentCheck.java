///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

/**
 * <div>
 * Checks alignment of parameters.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_CALL">
 * METHOD_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_CALL">
 * CTOR_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SUPER_CTOR_CALL">
 * SUPER_CTOR_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NEW">
 * LITERAL_NEW</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION">
 * ANNOTATION</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code parameter.alignment}
 * </li>
 * </ul>
 *
 * @since 10.18.3
 */
@StatelessCheck
public class ParameterAlignmentCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "parameter.alignment";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.METHOD_CALL,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.ANNOTATION,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
                findOffendingTokens(ast, TokenTypes.PARAMETERS, TokenTypes.PARAMETER_DEF);
                break;
            case TokenTypes.RECORD_DEF:
                findOffendingTokens(
                    ast,
                    TokenTypes.RECORD_COMPONENTS,
                    TokenTypes.RECORD_COMPONENT_DEF);
                break;
            case TokenTypes.ANNOTATION:
                findOffendingTokens(
                    ast,
                    TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR,
                    TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);
                break;
            default:
                // case TokenTypes.METHOD_CALL:
                // case TokenTypes.CTOR_CALL:
                // case TokenTypes.SUPER_CTOR_CALL:
                // case TokenTypes.LITERAL_NEW:
                findOffendingTokens(ast, TokenTypes.ELIST, TokenTypes.EXPR);
                break;
        }
    }

    /**
     * Called to find offending tokens.
     *
     * @param ast the token to process
     * @param listType the token group type to process
     * @param itemType the token type to process within listType group
     */
    private void findOffendingTokens(DetailAST ast, int listType, int itemType) {
        final DetailAST params = ast.findFirstToken(listType);
        if (listType == itemType) {
            findOffendingTokens(itemType, params);
        }
        if (params != null) {
            final DetailAST param = params.findFirstToken(itemType);
            if (param != null) {
                findOffendingTokens(itemType, param);
            }
        }
    }

    /**
     * Called to find offending tokens within token group.
     *
     * @param itemType token type to process
     * @param ast starting token
     */
    private void findOffendingTokens(int itemType, DetailAST ast) {
        DetailAST param = ast;
        int columnNo = -1;
        int lastLineNo = -1;
        while (param != null) {
            if (columnNo == -1) {
                columnNo = param.getColumnNo();
                lastLineNo = param.getLineNo();
            }
            if (param.getLineNo() > lastLineNo) {
                if (param.getColumnNo() != columnNo) {
                    log(param, MSG_KEY);
                }
                lastLineNo = param.getLineNo();
            }
            do {
                param = param.getNextSibling();
            } while (param != null && param.getType() != itemType);
        }
    }

}
