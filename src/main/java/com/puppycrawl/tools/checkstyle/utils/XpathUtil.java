////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for xpath.
 *
 */
public final class XpathUtil {

    /**
     * List of token types which support value attribute.
     * These token types were selected based on analysis that all others do not match required
     * criteria - value of the token must be useful and help to retrieve more precise results.
     * There are three types of AST tokens:
     * 1. Tokens for which the values are equal to the name of the token. Or in other words,
     * nodes for which the following expression is always true:
     * @{code detailAst.getText().equals(TokenUtil.getTokenName(detailAst.getType()))}
     * For example:
     * <pre>
     *     //MODIFIERS[@value='MODIFIERS']
     *     //OBJBLOCK[@value='OBJBLOCK']
     * </pre>
     * These tokens do not match required criteria because their values do not carry any
     * information, they do not affect the xpath requests and do not help to get more accurate
     * results. The values of these nodes are useless. No matter which class you analyze, these
     * values are always the same.
     * In addition, they make xpath queries more complex, less readable and less obvious.
     * 2. Tokens for which the values differ from token names, but values are always constant.
     * For example:
     * <pre>
     *     //LITERAL_VOID[@value='void']
     *     //RCURLY[@value='}']
     * </pre>
     * These tokens are not used for the same reasons as were described in the previous part.
     * 3. Tokens for which values are not constant. The values of these nodes are closely related
     * to a concrete class, method, variable and so on.
     * For example:
     * <pre>
     *     String greeting = "HelloWorld";
     *     //STRING_LITERAL[@value='HelloWorld']
     * </pre>
     * <pre>
     *     int year = 2017;
     *     //NUM_INT[@value=2017]
     * </pre>
     * <pre>
     *     int age = 23;
     *     //NUM_INT[@value=23]
     * </pre>
     * As you can see same {@code NUM_INT} token type can have different values, depending on
     * context.
     * <pre>
     *     public class MyClass {}
     *     //IDENT[@value='MyClass']
     * </pre>
     * Only these tokens support value attribute because they make our xpath queries more accurate.
     * These token types are listed below.
     * */
    private static final List<Integer> TOKEN_TYPES_WITH_VALUE_ATTRIBUTE = Arrays.asList(
            TokenTypes.IDENT, TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
            TokenTypes.NUM_LONG, TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT);

    /** Stop instances being created. **/
    private XpathUtil() {
    }

    /**
     * Checks, if specified node can have {@code @value} attribute.
     *
     * @param ast {@code DetailAst} element
     * @return true if element supports {@code @value} attribute, false otherwise
     */
    public static boolean supportsValueAttribute(DetailAST ast) {
        return TOKEN_TYPES_WITH_VALUE_ATTRIBUTE.contains(ast.getType());
    }

    /**
     * Returns value of the ast element.
     *
     * @param ast {@code DetailAst} element
     * @return value of the ast element
     */
    public static String getValue(DetailAST ast) {
        String value = ast.getText();
        if (ast.getType() == TokenTypes.STRING_LITERAL) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

}
