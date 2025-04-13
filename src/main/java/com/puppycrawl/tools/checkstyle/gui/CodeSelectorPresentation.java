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

package com.puppycrawl.tools.checkstyle.gui;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Presentation model for CodeSelector.
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
     *
     * @param ast ast node.
     * @param lines2position positions of lines.
     * @noinspection AssignmentOrReturnOfFieldWithMutableType
     * @noinspectionreason AssignmentOrReturnOfFieldWithMutableType - mutability is
     *      expected in list of lines of code
     */
    public CodeSelectorPresentation(DetailAST ast, List<Integer> lines2position) {
        node = ast;
        this.lines2position = lines2position;
    }

    /**
     * Constructor.
     *
     * @param node DetailNode node.
     * @param lines2position list to map lines.
     * @noinspection AssignmentOrReturnOfFieldWithMutableType
     * @noinspectionreason AssignmentOrReturnOfFieldWithMutableType - mutability is expected
     *      in list of lines of code
     */
    public CodeSelectorPresentation(DetailNode node, List<Integer> lines2position) {
        this.node = node;
        this.lines2position = lines2position;
    }

    /**
     * Returns selection start position.
     *
     * @return selection start position.
     */
    public int getSelectionStart() {
        return selectionStart;
    }

    /**
     * Returns selection end position.
     *
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
     *
     * @param ast DetailAST node for which selection finds
     */
    private void findSelectionPositions(DetailAST ast) {
        selectionStart = lines2position.get(ast.getLineNo()) + ast.getColumnNo();

        if (ast.hasChildren() || !TokenUtil.getTokenName(ast.getType()).equals(ast.getText())) {
            selectionEnd = findLastPosition(ast);
        }
        else {
            selectionEnd = selectionStart;
        }
    }

    /**
     * Find start and end selection positions from DetailNode line and Column.
     *
     * @param detailNode DetailNode node for which selection finds
     */
    private void findSelectionPositions(DetailNode detailNode) {
        selectionStart = lines2position.get(detailNode.getLineNumber())
            + detailNode.getColumnNumber();

        selectionEnd = findLastPosition(detailNode);
    }

    /**
     * Finds the last position of node without children.
     *
     * @param astNode DetailAST node.
     * @return Last position of node without children.
     */
    private int findLastPosition(final DetailAST astNode) {
        final int lastPosition;
        if (astNode.hasChildren()) {
            lastPosition = findLastPosition(astNode.getLastChild());
        }
        else {
            lastPosition = lines2position.get(astNode.getLineNo()) + astNode.getColumnNo()
                + astNode.getText().length();
        }
        return lastPosition;
    }

    /**
     * Finds the last position of node without children.
     *
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
