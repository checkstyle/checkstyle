////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifier;

/**
 * Contains utility methods for the checks.
 *
 */
public final class CheckUtil {

    // constants for parseDouble()
    /** Binary radix. */
    private static final int BASE_2 = 2;

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
    private CheckUtil() {
    }

    /**
     * Creates {@code FullIdent} for given type node.
     * @param typeAST a type node.
     * @return {@code FullIdent} for given type.
     */
    public static FullIdent createFullType(final DetailAST typeAST) {
        DetailAST ast = typeAST;

        // ignore array part of type
        while (ast.findFirstToken(TokenTypes.ARRAY_DECLARATOR) != null) {
            ast = ast.findFirstToken(TokenTypes.ARRAY_DECLARATOR);
        }

        return FullIdent.createFullIdent(ast.getFirstChild());
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
            final boolean staticOrAbstract =
                    modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                    || modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;

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
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param text the string to be parsed.
     * @param type the token type of the text. Should be a constant of
     *     {@link TokenTypes}.
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        String txt = UNDERSCORE_PATTERN.matcher(text).replaceAll("");
        final double result;
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
                else if (txt.startsWith("0b") || txt.startsWith("0B")) {
                    radix = BASE_2;
                    txt = txt.substring(2);
                }
                else if (CommonUtil.startsWithChar(txt, '0')) {
                    radix = BASE_8;
                    txt = txt.substring(1);
                }
                result = parseNumber(txt, radix, type);
                break;
            default:
                result = Double.NaN;
                break;
        }
        return result;
    }

    /**
     * Parses the string argument as an integer or a long in the radix specified by
     * the second argument. The characters in the string must all be digits of
     * the specified radix.
     * @param text the String containing the integer representation to be
     *     parsed. Precondition: text contains a parsable int.
     * @param radix the radix to be used while parsing text.
     * @param type the token type of the text. Should be a constant of
     *     {@link TokenTypes}.
     * @return the number represented by the string argument in the specified radix.
     */
    private static double parseNumber(final String text, final int radix, final int type) {
        String txt = text;
        if (CommonUtil.endsWithChar(txt, 'L') || CommonUtil.endsWithChar(txt, 'l')) {
            txt = txt.substring(0, txt.length() - 1);
        }
        final double result;
        if (txt.isEmpty()) {
            result = 0.0;
        }
        else {
            final boolean negative = txt.charAt(0) == '-';
            if (type == TokenTypes.NUM_INT) {
                if (negative) {
                    result = Integer.parseInt(txt, radix);
                }
                else {
                    result = Integer.parseUnsignedInt(txt, radix);
                }
            }
            else {
                if (negative) {
                    result = Long.parseLong(txt, radix);
                }
                else {
                    result = Long.parseUnsignedLong(txt, radix);
                }
            }
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
            if (isBeforeInSource(newNode, currentNode)) {
                currentNode = newNode;
            }
            child = child.getNextSibling();
        }

        return currentNode;
    }

    /**
     * Retrieves whether ast1 is located before ast2.
     * @param ast1 the first node.
     * @param ast2 the second node.
     * @return true, if ast1 is located before ast2.
     */
    public static boolean isBeforeInSource(DetailAST ast1, DetailAST ast2) {
        return ast1.getLineNo() < ast2.getLineNo()
            || TokenUtil.areOnSameLine(ast1, ast2)
                && ast1.getColumnNo() < ast2.getColumnNo();
    }

    /**
     * Retrieves the names of the type parameters to the node.
     * @param node the parameterized AST node
     * @return a list of type parameter names
     */
    public static List<String> getTypeParameterNames(final DetailAST node) {
        final DetailAST typeParameters =
            node.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final List<String> typeParameterNames = new ArrayList<>();
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

        final List<DetailAST> typeParams = new ArrayList<>();
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
            final boolean voidReturnType = type.findFirstToken(TokenTypes.LITERAL_VOID) != null;

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
            final boolean noVoidReturnType = type.findFirstToken(TokenTypes.LITERAL_VOID) == null;

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

    /**
     * Checks whether a parameter is a receiver.
     *
     * @param parameterDefAst the parameter node.
     * @return true if the parameter is a receiver.
     */
    public static boolean isReceiverParameter(DetailAST parameterDefAst) {
        return parameterDefAst.getType() == TokenTypes.PARAMETER_DEF
                && parameterDefAst.findFirstToken(TokenTypes.IDENT) == null;
    }

    /**
     * Returns {@link AccessModifier} based on the information about access modifier
     * taken from the given token of type {@link TokenTypes#MODIFIERS}.
     * @param modifiersToken token of type {@link TokenTypes#MODIFIERS}.
     * @return {@link AccessModifier}.
     * @throws IllegalArgumentException when expected non-null modifiersToken with type 'MODIFIERS'
     */
    public static AccessModifier getAccessModifierFromModifiersToken(DetailAST modifiersToken) {
        if (modifiersToken == null || modifiersToken.getType() != TokenTypes.MODIFIERS) {
            throw new IllegalArgumentException("expected non-null AST-token with type 'MODIFIERS'");
        }

        // default access modifier
        AccessModifier accessModifier = AccessModifier.PACKAGE;
        for (DetailAST token = modifiersToken.getFirstChild(); token != null;
             token = token.getNextSibling()) {
            final int tokenType = token.getType();
            if (tokenType == TokenTypes.LITERAL_PUBLIC) {
                accessModifier = AccessModifier.PUBLIC;
            }
            else if (tokenType == TokenTypes.LITERAL_PROTECTED) {
                accessModifier = AccessModifier.PROTECTED;
            }
            else if (tokenType == TokenTypes.LITERAL_PRIVATE) {
                accessModifier = AccessModifier.PRIVATE;
            }
        }
        return accessModifier;
    }

    /**
     * Create set of class names and short class names.
     *
     * @param classNames array of class names.
     * @return set of class names and short class names.
     */
    public static Set<String> parseClassNames(String... classNames) {
        final Set<String> illegalClassNames = new HashSet<>();
        for (final String name : classNames) {
            illegalClassNames.add(name);
            final int lastDot = name.lastIndexOf('.');
            if (lastDot != -1 && lastDot < name.length() - 1) {
                final String shortName = name
                        .substring(name.lastIndexOf('.') + 1);
                illegalClassNames.add(shortName);
            }
        }
        return illegalClassNames;
    }

}
