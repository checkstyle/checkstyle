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

package com.puppycrawl.tools.checkstyle.api;

import java.util.ArrayList;
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
     * Recursively extract a FullIdent.
     *
     * @param full the FullIdent to add to
     * @param ast the node to recurse from
     */
    private static void extractFullIdent(FullIdent full, DetailAST ast) {
        if (ast != null) {
            final DetailAST nextSibling = ast.getNextSibling();

            // Here we want type declaration, but not initialization
            final boolean isArrayTypeDeclarationStart = nextSibling != null
                    && (nextSibling.getType() == TokenTypes.ARRAY_DECLARATOR
                        || nextSibling.getType() == TokenTypes.ANNOTATIONS)
                    && isArrayTypeDeclaration(nextSibling);

            final int typeOfAst = ast.getType();
            if (typeOfAst == TokenTypes.LITERAL_NEW
                    && ast.hasChildren()) {
                final DetailAST firstChild = ast.getFirstChild();
                extractFullIdent(full, firstChild);
            }
            else if (typeOfAst == TokenTypes.DOT) {
                final DetailAST firstChild = ast.getFirstChild();
                extractFullIdent(full, firstChild);
                full.append(".");
                extractFullIdent(full, firstChild.getNextSibling());
                appendBrackets(full, ast);
            }
            else if (isArrayTypeDeclarationStart) {
                full.append(ast);
                appendBrackets(full, ast);
            }
            else if (typeOfAst != TokenTypes.ANNOTATIONS) {
                full.append(ast);
            }
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
        DetailAST expression = arrayDeclarator.getFirstChild();
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
