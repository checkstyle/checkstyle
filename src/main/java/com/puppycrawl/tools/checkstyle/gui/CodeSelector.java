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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 * Helper class to select a code.
 */
public class CodeSelector {

    /** Editor. */
    private final JTextArea editor;
    /** Presentation model. */
    private final CodeSelectorPresentation pModel;

    /**
     * Constructor.
     *
     * @param node ast node.
     * @param editor text area editor.
     * @param lines2position positions of lines.
     */
    public CodeSelector(final Object node, final JTextArea editor,
                        final Collection<Integer> lines2position) {
        this.editor = editor;
        if (node instanceof DetailAST) {
            pModel = new CodeSelectorPresentation((DetailAST) node,
                new ArrayList<>(lines2position));
        }
        else {
            pModel = new CodeSelectorPresentation((DetailNode) node,
                new ArrayList<>(lines2position));
        }
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
