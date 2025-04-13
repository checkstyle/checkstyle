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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreePath;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.XpathUtil;
import com.puppycrawl.tools.checkstyle.xpath.ElementNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

import net.sf.saxon.trans.XPathException;

/**
 * This example shows how to create a simple TreeTable component,
 * by using a JTree as a renderer (and editor) for the cells in a
 * particular column in the JTable.
 * <a href=
 * "https://docs.oracle.com/cd/E48246_01/apirefs.1111/e13403/oracle/ide/controls/TreeTableModel.html">
 * Original&nbsp;Source&nbsp;Location</a>
 *
 * @noinspection ThisEscapedInObjectConstruction
 * @noinspectionreason ThisEscapedInObjectConstruction - only reference is used and not
 *      accessed until initialized
 */
public final class TreeTable extends JTable {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = -8493693409423365387L;
    /** The newline character. */
    private static final String NEWLINE = "\n";
    /** A subclass of JTree. */
    private final TreeTableCellRenderer tree;
    /** JTextArea editor. */
    private JTextArea editor;
    /** JTextArea xpathEditor. */
    private JTextArea xpathEditor;
    /** Line position map. */
    private List<Integer> linePositionList;

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
            final int height = getRowHeight();
            setRowHeight(height);
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
        new CodeSelector(tree.getLastSelectedPathComponent(), editor, linePositionList).select();
    }

    /**
     * Generate Xpath.
     */
    private void generateXpath() {
        if (tree.getLastSelectedPathComponent() instanceof DetailAST) {
            final DetailAST ast = (DetailAST) tree.getLastSelectedPathComponent();
            final String xpath = XpathQueryGenerator.generateXpathQuery(ast);
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
        // Twenty-eight character string to contain "Type" column
        final int widthOfTwentyEightCharacterString =
            fontMetrics.stringWidth("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        final int preferredTypeColumnWidth = widthOfTwentyEightCharacterString + padding;
        getColumn("Type").setPreferredWidth(preferredTypeColumnWidth);
    }

    /**
     * Select Node by Xpath.
     */
    public void selectNodeByXpath() {
        final DetailAST rootAST = (DetailAST) tree.getModel().getRoot();
        if (rootAST.hasChildren()) {
            final String xpath = xpathEditor.getText();

            try {
                final Deque<DetailAST> nodes =
                    XpathUtil.getXpathItems(xpath, new RootNode(rootAST))
                        .stream()
                        .map(ElementNode.class::cast)
                        .map(ElementNode::getUnderlyingNode)
                        .collect(Collectors.toCollection(ArrayDeque::new));
                updateTreeTable(xpath, nodes);
            }
            catch (XPathException exception) {
                xpathEditor.setText(xpathEditor.getText() + NEWLINE + exception.getMessage());
            }
        }
        else {
            xpathEditor.setText("No file Opened");
        }
    }

    /**
     * Updates the Treetable by expanding paths in the tree and highlighting
     * associated code.
     *
     * @param xpath the XPath query to show in case of no match
     * @param nodes the deque of DetailAST nodes to match in TreeTable and XPath editor
     */
    private void updateTreeTable(String xpath, Deque<DetailAST> nodes) {
        if (nodes.isEmpty()) {
            xpathEditor.setText("No elements matching XPath query '"
                + xpath + "' found.");
        }
        else {
            for (DetailAST node : nodes) {
                expandTreeTableByPath(node);
                makeCodeSelection();
            }
            xpathEditor.setText(getAllMatchingXpathQueriesText(nodes));
        }
    }

    /**
     * Expands path in tree table to given node so that user can
     * see the node.
     *
     * @param node node to expand table by
     */
    private void expandTreeTableByPath(DetailAST node) {
        TreePath path = new TreePath(node);
        path = path.pathByAddingChild(node);
        if (!tree.isExpanded(path)) {
            tree.expandPath(path);
        }
        tree.setSelectionPath(path);
    }

    /**
     * Generates a String with all matching XPath queries separated
     * by newlines.
     *
     * @param nodes deque of nodes to generate queries for
     * @return complete text of all XPath expressions separated by newlines.
     */
    private static String getAllMatchingXpathQueriesText(Deque<DetailAST> nodes) {
        return nodes.stream()
            .map(XpathQueryGenerator::generateXpathQuery)
            .collect(Collectors.joining(NEWLINE, "", NEWLINE));
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
     * Sets line positions.
     *
     * @param linePositionList positions of lines.
     */
    public void setLinePositionList(Collection<Integer> linePositionList) {
        this.linePositionList = new ArrayList<>(linePositionList);
    }

    /**
     * TreeTableCellEditor implementation. Component returned is the
     * JTree.
     */
    private final class TreeTableCellEditor extends BaseCellEditor implements
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
