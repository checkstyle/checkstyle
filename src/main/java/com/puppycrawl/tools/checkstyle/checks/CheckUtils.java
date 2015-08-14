////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.List;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for the checks.
 *
 * @author Oliver Burn
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public final class CheckUtils {
    // constants for parseDouble()
    /** octal radix */
    private static final int BASE_8 = 8;

    /** decimal radix */
    private static final int BASE_10 = 10;

    /** hex radix */
    private static final int BASE_16 = 16;

    /** prevent instances */
    private CheckUtils() {
    }

    /**
     * Tests whether a method definition AST defines an equals covariant.
     * @param ast the method definition AST to test.
     * Precondition: ast is a TokenTypes.METHOD_DEF node.
     * @return true if ast defines an equals covariant.
     */
    public static boolean isEqualsMethod(DetailAST ast) {
        if (ast.getType() != TokenTypes.METHOD_DEF) {
            // A node must be method def
            return false;
        }

        // non-static, non-abstract?
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.branchContains(TokenTypes.LITERAL_STATIC)
            || modifiers.branchContains(TokenTypes.ABSTRACT)) {
            return false;
        }

        // named "equals"?
        final DetailAST nameNode = ast.findFirstToken(TokenTypes.IDENT);
        final String name = nameNode.getText();
        if (!"equals".equals(name)) {
            return false;
        }

        // one parameter?
        final DetailAST paramsNode = ast.findFirstToken(TokenTypes.PARAMETERS);
        return paramsNode.getChildCount() == 1;
    }

    /**
     * Returns whether a token represents an ELSE as part of an ELSE / IF set.
     * @param ast the token to check
     * @return whether it is
     */
    public static boolean isElseIf(DetailAST ast) {
        final DetailAST parentAST = ast.getParent();

        return ast.getType() == TokenTypes.LITERAL_IF
            && (isElse(parentAST) || isElseWithCurlyBraces(parentAST));
    }

    /**
     * Returns whether a token represents an ELSE.
     * @param ast the token to check
     * @return whether the token represents an ELSE
     */
    private static boolean isElse(DetailAST ast) {
        return ast.getType() == TokenTypes.LITERAL_ELSE;
    }

    /**
     * Returns whether a token represents an SLIST as part of an ELSE
     * statement.
     * @param ast the token to check
     * @return whether the toke does represent an SLIST as part of an ELSE
     */
    private static boolean isElseWithCurlyBraces(DetailAST ast) {
        return ast.getType() == TokenTypes.SLIST
            && ast.getChildCount() == 2
            && isElse(ast.getParent());
    }

    /**
     * Creates {@code FullIdent} for given type node.
     * @param typeAST a type node.
     * @return {@code FullIdent} for given type.
     */
    public static FullIdent createFullType(DetailAST typeAST) {
        final DetailAST arrayDeclAST =
            typeAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR);

        return createFullTypeNoArrays(arrayDeclAST == null ? typeAST
                                                           : arrayDeclAST);
    }

    /**
     * @param typeAST a type node (no array)
     * @return {@code FullIdent} for given type.
     */
    private static FullIdent createFullTypeNoArrays(DetailAST typeAST) {
        return FullIdent.createFullIdent(typeAST.getFirstChild());
    }

    /**
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param text the string to be parsed.
     * @param type the token type of the text. Should be a constant of
     * {@link TokenTypes}.
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        String txt = text.replaceAll("_", "");
        double result = 0;
        switch (type) {
            case TokenTypes.NUM_FLOAT:
            case TokenTypes.NUM_DOUBLE:
                result = Double.parseDouble(txt);
                break;
            case TokenTypes.NUM_INT:
            case TokenTypes.NUM_LONG:
                int radix = BASE_10;
                if (txt.startsWith("0x") || txt.startsWith("0X")) {
                    radix = BASE_16;
                    txt = txt.substring(2);
                }
                else if (txt.charAt(0) == '0') {
                    radix = BASE_8;
                    txt = txt.substring(1);
                }
                if (Utils.endsWithChar(txt, 'L') || Utils.endsWithChar(txt, 'l')) {
                    txt = txt.substring(0, txt.length() - 1);
                }
                if (!txt.isEmpty()) {
                    if (type == TokenTypes.NUM_INT) {
                        result = parseInt(txt, radix);
                    }
                    else {
                        result = parseLong(txt, radix);
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Parses the string argument as a signed integer in the radix specified by
     * the second argument. The characters in the string must all be digits of
     * the specified radix. Handles negative values, which method
     * java.lang.Integer.parseInt(String, int) does not.
     * @param text the String containing the integer representation to be
     * parsed. Precondition: text contains a parsable int.
     * @param radix the radix to be used while parsing text.
     * @return the integer represented by the string argument in the specified
     * radix.
     */
    public static int parseInt(String text, int radix) {
        int result = 0;
        final int max = text.length();
        for (int i = 0; i < max; i++) {
            final int digit = Character.digit(text.charAt(i), radix);
            result *= radix;
            result += digit;
        }
        return result;
    }

    /**
     * Parses the string argument as a signed long in the radix specified by
     * the second argument. The characters in the string must all be digits of
     * the specified radix. Handles negative values, which method
     * java.lang.Integer.parseInt(String, int) does not.
     * @param text the String containing the integer representation to be
     * parsed. Precondition: text contains a parsable int.
     * @param radix the radix to be used while parsing text.
     * @return the long represented by the string argument in the specified
     * radix.
     */
    public static long parseLong(String text, int radix) {
        long result = 0;
        final int max = text.length();
        for (int i = 0; i < max; i++) {
            final int digit = Character.digit(text.charAt(i), radix);
            result *= radix;
            result += digit;
        }
        return result;
    }

    /**
     * Finds sub-node for given node minimal (line, column) pair.
     * @param node the root of tree for search.
     * @return sub-node with minimal (line, column) pair.
     */
    public static DetailAST getFirstNode(final DetailAST node) {
        DetailAST currentNode = node;
        DetailAST child = node.getFirstChild();
        while (child != null) {
            final DetailAST newNode = getFirstNode(child);
            if (newNode.getLineNo() < currentNode.getLineNo()
                || newNode.getLineNo() == currentNode.getLineNo()
                    && newNode.getColumnNo() < currentNode.getColumnNo()) {
                currentNode = newNode;
            }
            child = child.getNextSibling();
        }

        return currentNode;
    }

    /**
     * Retrieves the names of the type parameters to the node.
     * @param node the parameterised AST node
     * @return a list of type parameter names
     */
    public static List<String> getTypeParameterNames(final DetailAST node) {
        final DetailAST typeParameters =
            node.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final List<String> typeParanames = Lists.newArrayList();
        if (typeParameters != null) {
            final DetailAST typeParam =
                typeParameters.findFirstToken(TokenTypes.TYPE_PARAMETER);
            typeParanames.add(
                typeParam.findFirstToken(TokenTypes.IDENT).getText());

            DetailAST sibling = typeParam.getNextSibling();
            while (sibling != null) {
                if (sibling.getType() == TokenTypes.TYPE_PARAMETER) {
                    typeParanames.add(
                        sibling.findFirstToken(TokenTypes.IDENT).getText());
                }
                sibling = sibling.getNextSibling();
            }
        }

        return typeParanames;
    }

    /**
     * Retrieves the type parameters to the node.
     * @param node the parameterised AST node
     * @return a list of type parameter names
     */
    public static List<DetailAST> getTypeParameters(final DetailAST node) {
        final DetailAST typeParameters =
            node.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final List<DetailAST> typeParams = Lists.newArrayList();
        if (typeParameters != null) {
            final DetailAST typeParam =
                typeParameters.findFirstToken(TokenTypes.TYPE_PARAMETER);
            typeParams.add(typeParam);

            DetailAST sibling = typeParam.getNextSibling();
            while (sibling != null) {
                if (sibling.getType() == TokenTypes.TYPE_PARAMETER) {
                    typeParams.add(sibling);
                }
                sibling = sibling.getNextSibling();
            }
        }

        return typeParams;
    }
}
