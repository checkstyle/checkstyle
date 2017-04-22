////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * Presentation model for CodeSelector.
 * @author unknown
 */
public class CodeSelectorPresentation {
    /** DetailAST or DetailNode node. */
    private final Object node;
    /** Mapping. */
    private final List<Integer> lines2position;
    /** Selection start position. */
    private int selectionStart;
    /** Selection end position. */
    private int selectionEnd;

    /**
     * Constructor.
     * @param ast ast node.
     * @param lines2position list to map lines.
     */
    public CodeSelectorPresentation(DetailAST ast, List<Integer> lines2position) {
        node = ast;
        final List<Integer> copy = new ArrayList<>(lines2position);
        this.lines2position = Collections.unmodifiableList(copy);
    }

    /**
     * Constructor.
     * @param node DetailNode node.
     * @param lines2position list to map lines.
     */
    public CodeSelectorPresentation(DetailNode node, List<Integer> lines2position) {
        this.node = node;
        final List<Integer> copy = new ArrayList<>(lines2position);
        this.lines2position = Collections.unmodifiableList(copy);
    }

    /**
     * @return selection start position.
     */
    public int getSelectionStart() {
        return selectionStart;
    }

    /**
     * @return selection end position.
     */
    public int getSelectionEnd() {
        return selectionEnd;
    }

    /**
     * Find start and end selection positions from AST line and Column.
     */
    public void findSelectionPositions() {
        if (node instanceof DetailAST) {
            findSelectionPositions((DetailAST) node);
        }
        else {
            findSelectionPositions((DetailNode) node);
        }
    }

    /**
     * Find start and end selection positions from AST line and Column.
     * @param ast DetailAST node for which selection finds
     */
    private void findSelectionPositions(DetailAST ast) {
        selectionStart = lines2position.get(ast.getLineNo()) + ast.getColumnNo();

        if (ast.getChildCount() == 0
                && TokenUtils.getTokenName(ast.getType()).equals(ast.getText())) {
            selectionEnd = selectionStart;
        }
        else {
            selectionEnd = findLastPosition(ast);
        }
    }

    /**
     * Find start and end selection positions from DetailNode line and Column.
     * @param detailNode DetailNode node for which selection finds
     */
    private void findSelectionPositions(DetailNode detailNode) {
        selectionStart = lines2position.get(detailNode.getLineNumber())
                            + detailNode.getColumnNumber();

        selectionEnd = findLastPosition(detailNode);
    }

    /**
     * Finds the last position of node without children.
     * @param astNode DetailAST node.
     * @return Last position of node without children.
     */
    private int findLastPosition(final DetailAST astNode) {
        final int lastPosition;
        if (astNode.getChildCount() == 0) {
            lastPosition = lines2position.get(astNode.getLineNo()) + astNode.getColumnNo()
                    + astNode.getText().length();
        }
        else {
            lastPosition = findLastPosition(astNode.getLastChild());
        }
        return lastPosition;
    }

    /**
     * Finds the last position of node without children.
     * @param detailNode DetailNode node.
     * @return Last position of node without children.
     */
    private int findLastPosition(final DetailNode detailNode) {
        final int lastPosition;
        if (detailNode.getChildren().length == 0) {
            lastPosition = lines2position.get(detailNode.getLineNumber())
                    + detailNode.getColumnNumber() + detailNode.getText().length();
        }
        else {
            final DetailNode lastChild =
                    detailNode.getChildren()[detailNode.getChildren().length - 1];
            lastPosition = findLastPosition(lastChild);
        }
        return lastPosition;
    }
}
