////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Represents a full identifier, including dots, with associated
 * position information.
 *
 * <p>
 * Identifiers such as {@code java.util.HashMap} are spread across
 * multiple AST nodes in the syntax tree (three IDENT nodes, two DOT nodes).
 * A FullIdent represents the whole String (excluding any intermediate
 * whitespace), which is often easier to work with in Checks.
 * </p>
 *
 * @see TokenTypes#DOT
 * @see TokenTypes#IDENT
 **/
public final class FullIdent {

    /** The list holding subsequent elements of identifier. **/
    private final List<String> elements = new ArrayList<>();
    /** The topmost and leftmost AST of the full identifier. */
    private DetailAST detailAst;

    /** Hide default constructor. */
    private FullIdent() {
    }

    /**
     * Creates a new FullIdent starting from the child of the specified node.
     *
     * @param ast the parent node from where to start from
     * @return a {@code FullIdent} value
     */
    public static FullIdent createFullIdentBelow(DetailAST ast) {
        return createFullIdent(ast.getFirstChild());
    }

    /**
     * Creates a new FullIdent starting from the specified node.
     *
     * @param ast the node to start from
     * @return a {@code FullIdent} value
     */
    public static FullIdent createFullIdent(DetailAST ast) {
        final FullIdent ident = new FullIdent();
        extractFullIdent(ident, ast);
        return ident;
    }

    /**
     * Extracts a FullIdent.
     *
     * @param full the FullIdent to add to
     * @param ast the node
     */
    private static void extractFullIdent(FullIdent full, DetailAST ast) {
        final Deque<DetailAST> identStack = new ArrayDeque<>();
        pushToIdentStack(identStack, ast);
        boolean bracketsExist = false;
        int dotCounter = 0;
        while (!identStack.isEmpty()) {
            final DetailAST currentAst = identStack.pop();

            final DetailAST nextSibling = currentAst.getNextSibling();

            // Here we want type declaration, but not initialization
            final boolean isArrayTypeDeclarationStart = nextSibling != null
                && (nextSibling.getType() == TokenTypes.ARRAY_DECLARATOR
                || nextSibling.getType() == TokenTypes.ANNOTATIONS)
                && isArrayTypeDeclaration(nextSibling);

            final int typeOfAst = currentAst.getType();
            bracketsExist = bracketsExist || isArrayTypeDeclarationStart;
            final DetailAST firstChild = currentAst.getFirstChild();

            if (typeOfAst == TokenTypes.LITERAL_NEW && currentAst.hasChildren()) {
                pushToIdentStack(identStack, firstChild);
            }
            else if (typeOfAst == TokenTypes.DOT) {
                pushToIdentStack(identStack, firstChild.getNextSibling());
                pushToIdentStack(identStack, firstChild);
                dotCounter++;
            }
            else {
                dotCounter = appendToFull(full, currentAst, dotCounter,
                    bracketsExist, isArrayTypeDeclarationStart);
            }
        }
    }

    /**
     * Populates the FullIdent node.
     *
     * @param full the FullIdent to add to
     * @param ast the node
     * @param dotCounter no of dots
     * @param bracketsExist yes if true
     * @param isArrayTypeDeclarationStart true if array type declaration start
     * @return updated value of dotCounter
     */
    private static int appendToFull(FullIdent full, DetailAST ast,
                                    int dotCounter, boolean bracketsExist, boolean isArrayTypeDeclarationStart) {
        int result = dotCounter;
        if (isArrayTypeDeclarationStart) {
            full.append(ast);
            appendBrackets(full, ast);
        }
        else if (ast.getType() != TokenTypes.ANNOTATIONS) {
            full.append(ast);
            if (dotCounter > 0) {
                full.append(".");
                result--;
            }
            if (bracketsExist) {
                appendBrackets(full, ast.getParent());
            }
        }
        return result;
    }

    /**
     * Pushes to stack if ast is not null.
     *
     * @param stack stack to push into
     * @param ast node to push into stack
     */
    private static void pushToIdentStack(Deque<DetailAST> stack, DetailAST ast) {
        if (ast != null) {
            stack.push(ast);
        }
    }

    /**
     * Checks an `ARRAY_DECLARATOR` ast to verify that it is not an
     * array initialization, i.e. 'new int [2][2]'. We do this by
     * making sure that no 'EXPR' token exists in this branch.
     *
     * @param arrayDeclarator the first ARRAY_DECLARATOR token in the ast
     * @return true if ast is an array type declaration
     */
    private static boolean isArrayTypeDeclaration(DetailAST arrayDeclarator) {
        DetailAST expression = arrayDeclarator;
        while (expression != null) {
            if (expression.getType() == TokenTypes.EXPR) {
                break;
            }
            expression = expression.getFirstChild();
        }
        return expression == null;
    }

    /**
     * Appends the brackets of an array type to a {@code FullIdent}.
     *
     * @param full the FullIdent to append brackets to
     * @param ast the type ast we are building a {@code FullIdent} for
     */
    private static void appendBrackets(FullIdent full, DetailAST ast) {
        final int bracketCount =
            ast.getParent().getChildCount(TokenTypes.ARRAY_DECLARATOR);
        for (int i = 0; i < bracketCount; i++) {
            full.append("[]");
        }
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return String.join("", elements);
    }

    /**
     * Gets the topmost leftmost DetailAST for this FullIdent.
     *
     * @return the topmost leftmost ast
     */
    public DetailAST getDetailAst() {
        return detailAst;
    }

    /**
     * Gets the line number.
     *
     * @return the line number
     */
    public int getLineNo() {
        return detailAst.getLineNo();
    }

    /**
     * Gets the column number.
     *
     * @return the column number
     */
    public int getColumnNo() {
        return detailAst.getColumnNo();
    }

    @Override
    public String toString() {
        return String.join("", elements)
            + "[" + detailAst.getLineNo() + "x" + detailAst.getColumnNo() + "]";
    }

    /**
     * Append the specified text.
     *
     * @param text the text to append
     */
    private void append(String text) {
        elements.add(text);
    }

    /**
     * Append the specified token and also recalibrate the first line and
     * column.
     *
     * @param ast the token to append
     */
    private void append(DetailAST ast) {
        elements.add(ast.getText());
        if (detailAst == null) {
            detailAst = ast;
        }
    }

}
