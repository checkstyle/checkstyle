////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
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
    /** Octal radix. */
    private static final int BASE_8 = 8;

    /** Decimal radix. */
    private static final int BASE_10 = 10;

    /** Hex radix. */
    private static final int BASE_16 = 16;

    /** Maximum children allowed in setter/getter. */
    private static final int SETTER_GETTER_MAX_CHILDREN = 7;

    /** Maximum nodes allowed in a body of setter. */
    private static final int SETTER_BODY_SIZE = 3;

    /** Maximum nodes allowed in a body of getter. */
    private static final int GETTER_BODY_SIZE = 2;

    /** Pattern matching underscore characters ('_'). */
    private static final Pattern UNDERSCORE_PATTERN = Pattern.compile("_");

    /** Pattern matching names of setter methods. */
    private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z].*");

    /** Pattern matching names of getter methods. */
    private static final Pattern GETTER_PATTERN = Pattern.compile("^(is|get)[A-Z].*");

    /** Prevent instances. */
    private CheckUtils() {
    }

    /**
     * Creates {@code FullIdent} for given type node.
     * @param typeAST a type node.
     * @return {@code FullIdent} for given type.
     */
    public static FullIdent createFullType(DetailAST typeAST) {
        final DetailAST arrayDeclaratorAST =
            typeAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR);
        final FullIdent fullType;

        if (arrayDeclaratorAST == null) {
            fullType = createFullTypeNoArrays(typeAST);
        }
        else {
            fullType = createFullTypeNoArrays(arrayDeclaratorAST);
        }
        return fullType;
    }

    /**
     * Tests whether a method definition AST defines an equals covariant.
     * @param ast the method definition AST to test.
     *     Precondition: ast is a TokenTypes.METHOD_DEF node.
     * @return true if ast defines an equals covariant.
     */
    public static boolean isEqualsMethod(DetailAST ast) {
        boolean equalsMethod = false;

        if (ast.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean staticOrAbstract = modifiers.branchContains(TokenTypes.LITERAL_STATIC)
                    || modifiers.branchContains(TokenTypes.ABSTRACT);

            if (!staticOrAbstract) {
                final DetailAST nameNode = ast.findFirstToken(TokenTypes.IDENT);
                final String name = nameNode.getText();

                if ("equals".equals(name)) {
                    // one parameter?
                    final DetailAST paramsNode = ast.findFirstToken(TokenTypes.PARAMETERS);
                    equalsMethod = paramsNode.getChildCount() == 1;
                }
            }
        }
        return equalsMethod;
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
        String txt = UNDERSCORE_PATTERN.matcher(text).replaceAll("");
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
                if (CommonUtils.endsWithChar(txt, 'L') || CommonUtils.endsWithChar(txt, 'l')) {
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
     *     parsed. Precondition: text contains a parsable int.
     * @param radix the radix to be used while parsing text.
     * @return the integer represented by the string argument in the specified radix.
     */
    private static int parseInt(String text, int radix) {
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
     *     parsed. Precondition: text contains a parsable int.
     * @param radix the radix to be used while parsing text.
     * @return the long represented by the string argument in the specified radix.
     */
    private static long parseLong(String text, int radix) {
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
     * @param node the parameterized AST node
     * @return a list of type parameter names
     */
    public static List<String> getTypeParameterNames(final DetailAST node) {
        final DetailAST typeParameters =
            node.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final List<String> typeParameterNames = Lists.newArrayList();
        if (typeParameters != null) {
            final DetailAST typeParam =
                typeParameters.findFirstToken(TokenTypes.TYPE_PARAMETER);
            typeParameterNames.add(
                    typeParam.findFirstToken(TokenTypes.IDENT).getText());

            DetailAST sibling = typeParam.getNextSibling();
            while (sibling != null) {
                if (sibling.getType() == TokenTypes.TYPE_PARAMETER) {
                    typeParameterNames.add(
                            sibling.findFirstToken(TokenTypes.IDENT).getText());
                }
                sibling = sibling.getNextSibling();
            }
        }

        return typeParameterNames;
    }

    /**
     * Retrieves the type parameters to the node.
     * @param node the parameterized AST node
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

    /**
     * Returns whether an AST represents a setter method.
     * @param ast the AST to check with
     * @return whether the AST represents a setter method
     */
    public static boolean isSetterMethod(final DetailAST ast) {
        boolean setterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper setter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {

            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesSetterFormat = SETTER_PATTERN.matcher(name).matches();
            final boolean voidReturnType = type.getChildCount(TokenTypes.LITERAL_VOID) > 0;

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean singleParam = params.getChildCount(TokenTypes.PARAMETER_DEF) == 1;

            if (matchesSetterFormat && voidReturnType && singleParam) {
                // Now verify that the body consists of:
                // SLIST -> EXPR -> ASSIGN
                // SEMI
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null && slist.getChildCount() == SETTER_BODY_SIZE) {
                    final DetailAST expr = slist.getFirstChild();
                    setterMethod = expr.getFirstChild().getType() == TokenTypes.ASSIGN;
                }
            }
        }
        return setterMethod;
    }

    /**
     * Returns whether an AST represents a getter method.
     * @param ast the AST to check with
     * @return whether the AST represents a getter method
     */
    public static boolean isGetterMethod(final DetailAST ast) {
        boolean getterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper getter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {

            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesGetterFormat = GETTER_PATTERN.matcher(name).matches();
            final boolean noVoidReturnType = type.getChildCount(TokenTypes.LITERAL_VOID) == 0;

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean noParams = params.getChildCount(TokenTypes.PARAMETER_DEF) == 0;

            if (matchesGetterFormat && noVoidReturnType && noParams) {
                // Now verify that the body consists of:
                // SLIST -> RETURN
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null && slist.getChildCount() == GETTER_BODY_SIZE) {
                    final DetailAST expr = slist.getFirstChild();
                    getterMethod = expr.getType() == TokenTypes.LITERAL_RETURN;
                }
            }
        }
        return getterMethod;
    }

    /**
     * Checks whether a method is a not void one.
     *
     * @param methodDefAst the method node.
     * @return true if method is a not void one.
     */
    public static boolean isNonVoidMethod(DetailAST methodDefAst) {
        boolean returnValue = false;
        if (methodDefAst.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST typeAST = methodDefAst.findFirstToken(TokenTypes.TYPE);
            if (typeAST.findFirstToken(TokenTypes.LITERAL_VOID) == null) {
                returnValue = true;
            }
        }
        return returnValue;
    }
}
