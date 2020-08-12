////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreePath;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

/**
 * This example shows how to create a simple TreeTable component,
 * by using a JTree as a renderer (and editor) for the cells in a
 * particular column in the JTable.
 *
 * <a href=
 * "https://docs.oracle.com/cd/E48246_01/apirefs.1111/e13403/oracle/ide/controls/TreeTableModel.html">
 * Original&nbsp;Source&nbsp;Location</a>
 *
 * @noinspection ThisEscapedInObjectConstruction
 */
public final class TreeTable extends JTable {

    private static final long serialVersionUID = -8493693409423365387L;
    /** A subclass of JTree. */
    private final TreeTableCellRenderer tree;
    /** JTextArea editor. */
    private JTextArea editor;
    /** JTextArea xpathEditor. */
    private JTextArea xpathEditor;
    /** Line position map. */
    private List<Integer> linePositionMap;

    /**
     * Creates TreeTable base on TreeTableModel.
     *
     * @param treeTableModel Tree table model
     */
    public TreeTable(ParseTreeTableModel treeTableModel) {
        // Create the tree. It will be used as a renderer and editor.
        tree = new TreeTableCellRenderer(this, treeTableModel);

        // Install a tableModel representing the visible rows in the tree.
        setModel(new TreeTableModelAdapter(treeTableModel, tree));

        // Force the JTable and JTree to share their row selection models.
        final ListToTreeSelectionModelWrapper selectionWrapper = new
                ListToTreeSelectionModelWrapper(this);
        tree.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        // Install the tree editor renderer and editor.
        setDefaultRenderer(ParseTreeTableModel.class, tree);
        setDefaultEditor(ParseTreeTableModel.class, new TreeTableCellEditor());

        // No grid.
        setShowGrid(false);

        // No intercell spacing
        setIntercellSpacing(new Dimension(0, 0));

        // And update the height of the trees row to match that of
        // the table.
        if (tree.getRowHeight() < 1) {
            // Metal looks better like this.
            setRowHeight(getRowHeight());
        }

        setColumnsInitialWidth();

        final Action expand = new AbstractAction() {
            private static final long serialVersionUID = -5859674518660156121L;

            @Override
            public void actionPerformed(ActionEvent event) {
                expandSelectedNode();
            }
        };
        final KeyStroke stroke = KeyStroke.getKeyStroke("ENTER");
        final String command = "expand/collapse";
        getInputMap().put(stroke, command);
        getActionMap().put(command, expand);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    expandSelectedNode();
                }
            }
        });
    }

    /**
     * Do expansion of a tree node.
     */
    private void expandSelectedNode() {
        final TreePath selected = tree.getSelectionPath();
        makeCodeSelection();
        generateXpath();

        if (tree.isExpanded(selected)) {
            tree.collapsePath(selected);
        }
        else {
            tree.expandPath(selected);
        }
        tree.setSelectionPath(selected);
    }

    /**
     * Make selection of code in a text area.
     */
    private void makeCodeSelection() {
        new CodeSelector(tree.getLastSelectedPathComponent(), editor, linePositionMap).select();
    }

    /**
     * Generate Xpath.
     */
    private void generateXpath() {
        if (tree.getLastSelectedPathComponent() instanceof DetailAST) {
            final DetailAST ast = (DetailAST) tree.getLastSelectedPathComponent();
            final int beginPos = 4;
            String xpath = XpathQueryGenerator.generateXpathQuery(ast);
            final int length = xpath.length();
            xpath = xpath.substring(beginPos, length);
            xpathEditor.setText(xpath);
        }
        else {
            xpathEditor.setText("Xpath is not supported yet for javadoc nodes");
        }
    }

    /**
     * Set initial value of width for columns in table.
     */
    private void setColumnsInitialWidth() {
        final FontMetrics fontMetrics = getFontMetrics(getFont());
        // Six character string to contain "Column" column.
        final int widthOfSixCharacterString = fontMetrics.stringWidth("XXXXXX");
        // Padding must be added to width for columns to make them fully
        // visible in table header.
        final int padding = 10;
        final int widthOfColumnContainingSixCharacterString =
                widthOfSixCharacterString + padding;
        getColumn("Line").setMaxWidth(widthOfColumnContainingSixCharacterString);
        getColumn("Column").setMaxWidth(widthOfColumnContainingSixCharacterString);
        final int preferredTreeColumnWidth =
                Math.toIntExact(Math.round(getPreferredSize().getWidth() * 0.6));
        getColumn("Tree").setPreferredWidth(preferredTreeColumnWidth);
        // Twenty eight character string to contain "Type" column
        final int widthOfTwentyEightCharacterString =
                fontMetrics.stringWidth("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        final int preferredTypeColumnWidth = widthOfTwentyEightCharacterString + padding;
        getColumn("Type").setPreferredWidth(preferredTypeColumnWidth);
    }

    /**
     * Search node by Xpath.
     *
     * @param root {@code DetailAST} root ast element
     * @param xpath {@code String} xpath query
     * @param nodes {@code Deque<DetailAST>} stack of nodes in selection path
     * @return {@code boolean} node found or not
     */
    private static boolean search(DetailAST root, String xpath, Deque<DetailAST> nodes) {
        boolean result = false;
        if (xpath.equals(XpathQueryGenerator.generateXpathQuery(root))) {
            nodes.push(root);
            result = true;
        }
        else {
            DetailAST child = root.getFirstChild();
            while (child != null) {
                if (search(child, xpath, nodes)) {
                    nodes.push(root);
                    result = true;
                    break;
                }
                child = child.getNextSibling();
            }
        }
        return result;
    }

    /**
     * Select Node by Xpath.
     */
    public void selectNodeByXpath() {
        final DetailAST rootAST = (DetailAST) tree.getModel().getRoot();
        if (rootAST.hasChildren()) {
            final String xpath = "/EOF" + xpathEditor.getText();
            final Deque<DetailAST> nodes = new ArrayDeque<>();
            if (search(rootAST, xpath, nodes)) {
                TreePath path = new TreePath(nodes.pop());
                while (!nodes.isEmpty()) {
                    path = path.pathByAddingChild(nodes.pop());
                    if (!tree.isExpanded(path)) {
                        tree.expandPath(path);
                    }
                    tree.setSelectionPath(path);
                    makeCodeSelection();
                }
            }
            else {
                xpathEditor.setText(xpathEditor.getText() + "\n^ wrong xpath query");
            }
        }
        else {
            xpathEditor.setText("No file Opened");
        }
    }

    /**
     * Overridden to message super and forward the method to the tree.
     * Since the tree is not actually in the component hierarchy it will
     * never receive this unless we forward it in this manner.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        if (tree != null) {
            tree.updateUI();
        }
        // Use the tree's default foreground and background colors in the
        // table.
        LookAndFeel.installColorsAndFont(this, "Tree.background",
                "Tree.foreground", "Tree.font");
    }

    /* Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to
     * paint the renderers and editors and overriding setBounds() below
     * is not the right thing to do for an editor. Returning -1 for the
     * editing row in this case, ensures the editor is never painted.
     */
    @Override
    public int getEditingRow() {
        int rowIndex = -1;
        final Class<?> editingClass = getColumnClass(editingColumn);
        if (editingClass != ParseTreeTableModel.class) {
            rowIndex = editingRow;
        }
        return rowIndex;
    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    @Override
    public void setRowHeight(int newRowHeight) {
        super.setRowHeight(newRowHeight);
        if (tree != null && tree.getRowHeight() != newRowHeight) {
            tree.setRowHeight(getRowHeight());
        }
    }

    /**
     * Returns tree.
     *
     * @return the tree that is being shared between the model.
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * Sets text area editor.
     *
     * @param textArea JTextArea component.
     */
    public void setEditor(JTextArea textArea) {
        editor = textArea;
    }

    /**
     * Sets text area xpathEditor.
     *
     * @param xpathTextArea JTextArea component.
     */
    public void setXpathEditor(JTextArea xpathTextArea) {
        xpathEditor = xpathTextArea;
    }

    /**
     * Sets line position map.
     *
     * @param linePositionMap Line position map.
     */
    public void setLinePositionMap(List<Integer> linePositionMap) {
        this.linePositionMap = new ArrayList<>(linePositionMap);
    }

    /**
     * TreeTableCellEditor implementation. Component returned is the
     * JTree.
     */
    private class TreeTableCellEditor extends BaseCellEditor implements
            TableCellEditor {

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value,
                boolean isSelected,
                int row, int column) {
            return tree;
        }

        /**
         * Overridden to return false, and if the event is a mouse event
         * it is forwarded to the tree.
         *
         * <p>The behavior for this is debatable, and should really be offered
         * as a property. By returning false, all keyboard actions are
         * implemented in terms of the table. By returning true, the
         * tree would get a chance to do something with the keyboard
         * events. For the most part this is ok. But for certain keys,
         * such as left/right, the tree will expand/collapse where as
         * the table focus should really move to a different column. Page
         * up/down should also be implemented in terms of the table.
         * By returning false this also has the added benefit that clicking
         * outside of the bounds of the tree node, but still in the tree
         * column will select the row, whereas if this returned true
         * that wouldn't be the case.
         *
         * <p>By returning false we are also enforcing the policy that
         * the tree will never be editable (at least by a key sequence).
         *
         * @see TableCellEditor
         */
        @Override
        public boolean isCellEditable(EventObject event) {
            if (event instanceof MouseEvent) {
                for (int counter = getColumnCount() - 1; counter >= 0;
                     counter--) {
                    if (getColumnClass(counter) == ParseTreeTableModel.class) {
                        final MouseEvent mouseEvent = (MouseEvent) event;
                        final MouseEvent newMouseEvent = new MouseEvent(tree, mouseEvent.getID(),
                                mouseEvent.getWhen(), mouseEvent.getModifiersEx(),
                                mouseEvent.getX() - getCellRect(0, counter, true).x,
                                mouseEvent.getY(), mouseEvent.getClickCount(),
                                mouseEvent.isPopupTrigger());
                        tree.dispatchEvent(newMouseEvent);
                        break;
                    }
                }
            }

            return false;
        }

    }

}
