////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
     * List of token types which support text attribute.
     * These token types were selected based on analysis that all others do not match required
     * criteria - text attribute of the token must be useful and help to retrieve more precise
     * results.
     * There are three types of AST tokens:
     * 1. Tokens for which the texts are equal to the name of the token. Or in other words,
     * nodes for which the following expression is always true:
     * <pre>
     *     detailAst.getText().equals(TokenUtil.getTokenName(detailAst.getType()))
     * </pre>
     * For example:
     * <pre>
     *     //MODIFIERS[@text='MODIFIERS']
     *     //OBJBLOCK[@text='OBJBLOCK']
     * </pre>
     * These tokens do not match required criteria because their texts do not carry any additional
     * information, they do not affect the xpath requests and do not help to get more accurate
     * results. The texts of these nodes are useless. No matter what code you analyze, these
     * texts are always the same.
     * In addition, they make xpath queries more complex, less readable and verbose.
     * 2. Tokens for which the texts differ from token names, but texts are always constant.
     * For example:
     * <pre>
     *     //LITERAL_VOID[@text='void']
     *     //RCURLY[@text='}']
     * </pre>
     * These tokens are not used for the same reasons as were described in the previous part.
     * 3. Tokens for which texts are not constant. The texts of these nodes are closely related
     * to a concrete class, method, variable and so on.
     * For example:
     * <pre>
     *     String greeting = "HelloWorld";
     *     //STRING_LITERAL[@text='HelloWorld']
     * </pre>
     * <pre>
     *     int year = 2017;
     *     //NUM_INT[@text=2017]
     * </pre>
     * <pre>
     *     int age = 23;
     *     //NUM_INT[@text=23]
     * </pre>
     * As you can see same {@code NUM_INT} token type can have different texts, depending on
     * context.
     * <pre>
     *     public class MyClass {}
     *     //IDENT[@text='MyClass']
     * </pre>
     * Only these tokens support text attribute because they make our xpath queries more accurate.
     * These token types are listed below.
     * */
    private static final List<Integer> TOKEN_TYPES_WITH_TEXT_ATTRIBUTE = Arrays.asList(
            TokenTypes.IDENT, TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
            TokenTypes.NUM_LONG, TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT);

    /** Stop instances being created. **/
    private XpathUtil() {
    }

    /**
     * Checks, if specified node can have {@code @text} attribute.
     *
     * @param ast {@code DetailAst} element
     * @return true if element supports {@code @text} attribute, false otherwise
     */
    public static boolean supportsTextAttribute(DetailAST ast) {
        return TOKEN_TYPES_WITH_TEXT_ATTRIBUTE.contains(ast.getType());
    }

    /**
     * Returns content of the text attribute of the ast element.
     *
     * @param ast {@code DetailAst} element
     * @return text attribute of the ast element
     */
    public static String getTextAttributeValue(DetailAST ast) {
        String text = ast.getText();
        if (ast.getType() == TokenTypes.STRING_LITERAL) {
            text = text.substring(1, text.length() - 1);
        }
        return text;
    }

}
