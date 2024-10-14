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

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks placement of parameters.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy for parameter placing.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.coding.ParameterPlacementOption}.
 * Default value is {@code own_line_allow_single_line}.
 * </li>
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
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code parameter.placement}
 * </li>
 * </ul>
 *
 * @since 10.18.3
 */
@StatelessCheck
public class ParameterPlacementCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "parameter.placement";

    /**
     * Specify policy for parameter placing.
     */
    private ParameterPlacementOption option = ParameterPlacementOption.OWN_LINE_ALLOW_SINGLE_LINE;

    /**
     * Setter to specify policy for parameter placing.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 10.18.3
     */
    public void setOption(String optionStr) {
        option = ParameterPlacementOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

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
            findOffendingTokens(ast, itemType, params);
        }
        if (params != null) {
            final DetailAST param = params.findFirstToken(itemType);
            if (param != null) {
                findOffendingTokens(ast, itemType, param);
            }
        }
    }

    /**
     * Called to find offending tokens.
     *
     * @param containerToken container token, i.e method, constructor, ...
     * @param itemType the token type to process within listType group
     * @param ast the token to process
     */
    private void findOffendingTokens(DetailAST containerToken, int itemType, DetailAST ast) {
        final DetailAST firstToken = ast;
        final boolean isMultiLine = areTokensOnMultipleLines(itemType, firstToken);
        int lastLineNo = -1;
        int lastErrorLine = -1;
        final DetailAST param = firstToken;

        switch (option) {
            case OWN_LINE:
                // first param must be on its own line
                if (containerToken.getLineNo() == firstToken.getLineNo()) {
                    log(firstToken, MSG_KEY);
                    lastLineNo = firstToken.getLineNo();
                    lastErrorLine = lastLineNo;
                }
                logOffendingTokens(itemType, lastLineNo, lastErrorLine, param);
                break;
            case OWN_LINE_ALLOW_SINGLE_LINE:
                if (isMultiLine) {
                    // first param must be on its own line
                    if (containerToken.getLineNo() == firstToken.getLineNo()) {
                        log(firstToken, MSG_KEY);
                        lastLineNo = firstToken.getLineNo();
                        lastErrorLine = lastLineNo;
                    }
                    logOffendingTokens(itemType, lastLineNo, lastErrorLine, param);
                }
                break;
            case SEPARATE_LINE:
                // all params must have their own line no
                logOffendingTokens(itemType, lastLineNo, lastErrorLine, param);
                break;
            default:
                // case SEPARATE_LINE_ALLOW_SINGLE_LINE:
                if (isMultiLine) {
                    logOffendingTokens(itemType, lastLineNo, lastErrorLine, param);
                }
                break;
        }
    }

    /**
     * Logs violations for offending tokens.
     *
     * @param itemType the token type to process within listType group
     * @param pLastLineNo last line no with processed token
     * @param pLastErrorLine last line no with invalid token
     * @param ast the token to process
     */
    private void logOffendingTokens(
            int itemType,
            int pLastLineNo,
            int pLastErrorLine,
            DetailAST ast) {
        DetailAST param = ast;
        int lastLineNo = pLastLineNo;
        int lastErrorLine = pLastErrorLine;
        while (param != null) {
            if (lastLineNo == param.getLineNo()) {
                if (lastErrorLine == -1) {
                    log(param, MSG_KEY);
                }
                lastErrorLine = param.getLineNo();
            }
            else {
                lastLineNo = param.getLineNo();
                lastErrorLine = -1;
            }
            do {
                param = param.getNextSibling();
            } while (param != null && param.getType() != itemType);
        }
    }

    /**
     * Called to determine whether tokens span multiple lines.
     *
     * @param itemType the token type to process within listType group
     * @param ast first token in a group of tokens to process
     * @return true when tokens in group are on multiple lines
     */
    private boolean areTokensOnMultipleLines(int itemType, DetailAST ast) {
        int firstLineNo = -1;
        if (ast != null) {
            firstLineNo = ast.getLineNo();
        }
        int lastLineNo = firstLineNo;
        DetailAST param = ast;

        while (param != null) {
            if (param.getLineNo() != firstLineNo) {
                lastLineNo = param.getLineNo();
                break;
            }
            do {
                param = param.getNextSibling();
            } while (param != null && param.getType() != itemType);
        }

        return firstLineNo != lastLineNo;
    }

}
