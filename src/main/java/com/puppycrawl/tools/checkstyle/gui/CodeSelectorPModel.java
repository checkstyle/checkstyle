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

package com.puppycrawl.tools.checkstyle.gui;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * Presentation model for CodeSelector.
 * @author unknown
 */
public class CodeSelectorPModel {
    /** DetailAST node. */
    private final DetailAST ast;
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
    public CodeSelectorPModel(DetailAST ast, List<Integer> lines2position) {
        this.ast = ast;
        this.lines2position = ImmutableList.copyOf(lines2position);
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
     * Finds the last position of node without children.
     * @param astNode DetailAST node.
     * @return Last position of node without children.
     */
    private int findLastPosition(final DetailAST astNode) {
        if (astNode.getChildCount() == 0) {
            return lines2position.get(astNode.getLineNo()) + astNode.getColumnNo()
                    + astNode.getText().length();
        }
        else {
            return findLastPosition(astNode.getLastChild());
        }
    }
}
