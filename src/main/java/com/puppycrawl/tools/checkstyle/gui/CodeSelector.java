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

package com.puppycrawl.tools.checkstyle.gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JTextArea;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Helper class to select a code.
 */
public class CodeSelector {
    /** DetailAST node*/
    private final DetailAST ast;
    /** editor */
    private final JTextArea editor;
    /** mapping */
    private final List<Integer> lines2position;

    /**
     * Constructor.
     * @param ast ast node.
     * @param editor text area editor.
     * @param lines2position list to map lines.
     */
    public CodeSelector(final DetailAST ast, final JTextArea editor,
                        final List<Integer> lines2position) {
        this.ast = ast;
        this.editor = editor;
        this.lines2position = ImmutableList.copyOf(lines2position);
    }

    /**
     * Set a selection position from AST line and Column.
     */
    public void select() {
        final int start = lines2position.get(ast.getLineNo()) + ast.getColumnNo();
        final int end = findLastPosition(ast);

        editor.setSelectedTextColor(Color.blue);
        editor.requestFocusInWindow();
        editor.setSelectionStart(start);
        editor.setSelectionEnd(end);
        editor.transferFocusBackward();
    }

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
