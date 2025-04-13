///
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

import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.Mockito.mock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;

public class TreeTableTest extends AbstractGuiTestSupport {

    private static final String TEST_FILE_NAME = "InputTreeTable.java";

    private TreeTable treeTable;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/treetable";
    }

    @BeforeEach
    public void prepare() throws Exception {
        final MainFrameModel model = new MainFrameModel();
        model.openFile(new File(getPath(TEST_FILE_NAME)));
        treeTable = new TreeTable(model.getParseTreeTableModel());
        treeTable.setLinePositionList(model.getLinesToPosition());
        treeTable.setEditor(mock(JTextArea.class));
        treeTable.setXpathEditor(mock(JTextArea.class));
        treeTable.getTree().setSelectionPath(
                new TreePath(treeTable.getTree().getModel().getRoot()));
    }

    @Test
    public void testExpandOnMouseDoubleClick() {
        final MouseEvent mouseDoubleClickEvent =
                new MouseEvent(treeTable, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, 2, false);
        assertWithMessage("The tree should be initially expanded")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
        treeTable.dispatchEvent(mouseDoubleClickEvent);
        assertWithMessage("The tree should be collapsed after action")
                .that(treeTable.getTree().isExpanded(0))
                .isFalse();
        treeTable.dispatchEvent(mouseDoubleClickEvent);
        assertWithMessage("The tree should be expanded after action")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
    }

    @Test
    public void testNothingChangedOnMouseSingleClick() {
        final MouseEvent mouseSingleClickEvent =
                new MouseEvent(treeTable, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, 1, false);
        assertWithMessage("The tree should be initially expanded")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
        treeTable.dispatchEvent(mouseSingleClickEvent);
        assertWithMessage("The tree should be still expanded")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
    }

    @Test
    public void testExpandOnEnterKey() {
        final ActionEvent expandCollapseActionEvent =
                new ActionEvent(treeTable, ActionEvent.ACTION_PERFORMED, "expand/collapse");
        final ActionListener actionForEnter =
                treeTable.getActionForKeyStroke(KeyStroke.getKeyStroke("ENTER"));
        assertWithMessage("The tree should be initially expanded")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
        actionForEnter.actionPerformed(expandCollapseActionEvent);
        assertWithMessage("The tree should be collapsed after action")
                .that(treeTable.getTree().isExpanded(0))
                .isFalse();
        actionForEnter.actionPerformed(expandCollapseActionEvent);
        assertWithMessage("The tree should be expanded after action")
                .that(treeTable.getTree().isExpanded(0))
                .isTrue();
    }

    @Test
    public void testFindNodesAllClassDefs() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        xpathTextArea.setText("//CLASS_DEF");
        findNodeButton.doClick();

        final String expected = "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputTreeTableXpathAreaPanel']]\n"
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputTreeTableXpathAreaPanel']]"
                + "/OBJBLOCK/CLASS_DEF"
                + "[./IDENT[@text='Inner']]\n";

        assertWithMessage("Expected and actual XPath queries should match.")
                .that(xpathTextArea.getText())
                .isEqualTo(expected);
    }

    @Test
    public void testFindNodesIdent() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        xpathTextArea.setText("//IDENT");
        findNodeButton.doClick();

        final String expected = "/COMPILATION_UNIT/CLASS_DEF/IDENT"
                + "[@text='InputTreeTableXpathAreaPanel']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT/IDENT[@text='treetable']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT[@text='treetable']]/DOT/IDENT"
                + "[@text='gui']\n"
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputTreeTableXpathAreaPanel']]"
                + "/OBJBLOCK/CLASS_DEF/IDENT[@text='Inner']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT[@text='treetable']]/DOT[./IDENT"
                + "[@text='gui']]/DOT/IDENT[@text='checkstyle']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT[@text='treetable']]/DOT[./IDENT"
                + "[@text='gui']]/DOT[./IDENT[@text='checkstyle']]/DOT/IDENT[@text='tools']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT[@text='treetable']]/DOT[./IDENT"
                + "[@text='gui']]"
                + "/DOT[./IDENT[@text='checkstyle']]/DOT[./IDENT[@text='tools']]/DOT/IDENT"
                + "[@text='com']\n"
                + "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT[@text='treetable']]/DOT[./IDENT"
                + "[@text='gui']]"
                + "/DOT[./IDENT[@text='checkstyle']]/DOT[./IDENT[@text='tools']]/DOT[./IDENT"
                + "[@text='com']]/IDENT[@text='puppycrawl']\n";

        assertWithMessage("Expected and actual XPath queries should match.")
                .that(xpathTextArea.getText())
                .isEqualTo(expected);
    }

    @Test
    public void testFindNodesMissingElements() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        xpathTextArea.setText("//LITERAL_TRY");
        findNodeButton.doClick();

        final String expected = "No elements matching XPath query '//LITERAL_TRY' found.";

        assertWithMessage("Unexpected XPath text area text")
                .that(xpathTextArea.getText())
                .isEqualTo(expected);
    }

    @Test
    public void testFindNodesUnexpectedTokenAtStart() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        xpathTextArea.setText("!*7^");
        findNodeButton.doClick();

        final String expected = "!*7^\nUnexpected token \"!\" at start of expression";

        assertWithMessage("Unexpected XPath text area text")
                .that(xpathTextArea.getText())
                .isEqualTo(expected);
    }

    @Test
    public void testFindNodesInvalidCharacterInExpression() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        xpathTextArea.setText("//CLASS_DEF^");
        findNodeButton.doClick();

        final String expected = "//CLASS_DEF^\nInvalid character '^' (x5e) in expression";

        assertWithMessage("Unexpected XPath text area text")
                .that(xpathTextArea.getText())
                .isEqualTo(expected);
    }

    @Test
    public void testTreeModelAdapterMethods() throws IOException {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.openFile(new File(getPath("InputTreeTableXpathAreaPanel.java")));

        assertWithMessage("Value at Column (0, 3) expected to equal 0")
                .that(treeTable.getValueAt(0, 3).equals(0))
                .isEqualTo(true);

        assertWithMessage("getColumn class expected to return string class")
                .that(treeTable.getColumnClass(4).equals(String.class))
                .isEqualTo(true);

        assertWithMessage("Selected cell expected not be editable")
                .that(treeTable.isCellEditable(1, 0))
                .isEqualTo(false);
    }
}
