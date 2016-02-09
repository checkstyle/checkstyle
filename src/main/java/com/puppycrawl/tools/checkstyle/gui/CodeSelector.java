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

import java.awt.Color;
import java.util.List;

import javax.swing.JTextArea;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Helper class to select a code.
 * @author unknown
 */
public class CodeSelector {
    /** Editor. */
    private final JTextArea editor;
    /** Presentation model. */
    private final CodeSelectorPModel pModel;

    /**
     * Constructor.
     * @param ast ast node.
     * @param editor text area editor.
     * @param lines2position list to map lines.
     */
    public CodeSelector(final DetailAST ast, final JTextArea editor,
                        final List<Integer> lines2position) {
        this.editor = editor;
        pModel = new CodeSelectorPModel(ast, lines2position);
    }

    /**
     * Set selection.
     */
    public void select() {
        pModel.findSelectionPositions();
        editor.setSelectedTextColor(Color.blue);
        editor.requestFocusInWindow();
        editor.setCaretPosition(pModel.getSelectionStart());
        editor.moveCaretPosition(pModel.getSelectionEnd());
    }
}
