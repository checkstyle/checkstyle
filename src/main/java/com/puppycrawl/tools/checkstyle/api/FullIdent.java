////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            if (ast.getType() == TokenTypes.DOT) {
                extractFullIdent(full, ast.getFirstChild());
                full.append(".");
                extractFullIdent(
                    full, ast.getFirstChild().getNextSibling());
            }
            else if (ast.getType() == TokenTypes.ARRAY_DECLARATOR) {
                extractFullIdent(full, ast.getFirstChild());
                full.append("[]");
            }
            else {
                full.append(ast);
            }
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
