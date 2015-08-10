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

package com.puppycrawl.tools.checkstyle.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
 * @author Oliver Burn
 * @see TokenTypes#DOT
 * @see TokenTypes#IDENT
 **/
public final class FullIdent {
    /** the list holding subsequent elements of identifier **/
    private final List<String> elements = new ArrayList<>();
    /** the line number **/
    private int lineNo;
    /** the column number **/
    private int colNo;

    /** hide default constructor */
    private FullIdent() {
    }

    /** @return the text **/
    public String getText() {
        return StringUtils.join(elements, "");
    }

    /** @return the line number **/
    public int getLineNo() {
        return lineNo;
    }

    /** @return the column number **/
    public int getColumnNo() {
        return colNo;
    }

    /**
     * Append the specified text.
     * @param text the text to append
     */
    private void append(String text) {
        elements.add(text);
    }

    /**
     * Append the specified token and also recalibrate the first line and
     * column.
     * @param ast the token to append
     */
    private void append(DetailAST ast) {
        elements.add(ast.getText());
        if (lineNo == 0) {
            lineNo = ast.getLineNo();
        }
        else if (ast.getLineNo() > 0) {
            lineNo = Math.min(lineNo, ast.getLineNo());
        }
        if (colNo == 0) {
            colNo = ast.getColumnNo();
        }
        else if (ast.getColumnNo() > 0) {
            colNo = Math.min(colNo, ast.getColumnNo());
        }
    }

    /**
     * Creates a new FullIdent starting from the specified node.
     * @param ast the node to start from
     * @return a {@code FullIdent} value
     */
    public static FullIdent createFullIdent(DetailAST ast) {
        final FullIdent fi = new FullIdent();
        extractFullIdent(fi, ast);
        return fi;
    }

    /**
     * Creates a new FullIdent starting from the child of the specified node.
     * @param ast the parent node from where to start from
     * @return a {@code FullIdent} value
     */
    public static FullIdent createFullIdentBelow(DetailAST ast) {
        return createFullIdent(ast.getFirstChild());
    }

    /**
     * Recursively extract a FullIdent.
     *
     * @param full the FullIdent to add to
     * @param ast the node to recurse from
     */
    private static void extractFullIdent(FullIdent full, DetailAST ast) {
        if (ast == null) {
            return;
        }

        if (ast.getType() == TokenTypes.DOT) {
            extractFullIdent(full, ast.getFirstChild());
            full.append(".");
            extractFullIdent(
                full, ast.getFirstChild().getNextSibling());
        }
        else {
            full.append(ast);
        }
    }

    @Override
    public String toString() {
        return getText() + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

}
