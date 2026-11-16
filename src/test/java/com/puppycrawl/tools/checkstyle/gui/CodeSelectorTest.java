///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;

import java.awt.Color;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

public class CodeSelectorTest extends AbstractGuiTestSupport {

    private MainFrameModel model;

    private DetailAST classDef;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/codeselector";
    }

    @BeforeEach
    public void loadFile() throws Exception {
        model = new MainFrameModel();
        model.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        model.openFile(new File(getPath("InputCodeSelector.java")));
        classDef = ((DetailAST) model.getParseTreeTableModel().getRoot())
                .findFirstToken(TokenTypes.CLASS_DEF);
    }

    @Test
    public void testJavaSelection() throws Exception {
        final DetailAST method = classDef.findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final String expectedText = String.join(System.lineSeparator(), "void method() {", "    }");
        SwingUtilities.invokeAndWait(() -> verifySelection(method, expectedText));
    }

    @Test
    public void testJavadocSelection() throws Exception {
        final DetailAST comment = classDef.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN)
                .findFirstToken(TokenTypes.COMMENT_CONTENT);
        final DetailNode javadoc = (DetailNode) model.getParseTreeTableModel().getChild(comment, 0);
        SwingUtilities.invokeAndWait(() -> verifySelection(javadoc, " Select this Javadoc. "));
    }

    /**
     * Verifies the selected text and caret positions on the Swing event dispatch thread.
     *
     * @param node Java or Javadoc node to select
     * @param expectedText expected selected text
     */
    private void verifySelection(Object node, String expectedText) {
        final JTextArea editor = new JTextArea(model.getText());
        editor.setSelectedTextColor(Color.red);
        final CodeSelector selector = new CodeSelector(node, editor, model.getLinesToPosition());
        selector.select();

        final int expectedStart = model.getText().indexOf(expectedText);
        assertWithMessage("Selected text")
                .that(editor.getSelectedText())
                .isEqualTo(expectedText);
        assertWithMessage("Selection start")
                .that(editor.getSelectionStart())
                .isEqualTo(expectedStart);
        assertWithMessage("Caret position at selection end")
                .that(editor.getCaretPosition())
                .isEqualTo(expectedStart + expectedText.length());
        assertWithMessage("Selected text color")
                .that(editor.getSelectedTextColor())
                .isEqualTo(Color.blue);
    }

}
