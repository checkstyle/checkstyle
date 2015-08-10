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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * This example shows how to create a simple JTreeTable component,
 * by using a JTree as a renderer (and editor) for the cells in a
 * particular column in the JTable.
 *
 * <a href="https://docs.oracle.com/cd/E48246_01/apirefs.1111/e13403/oracle/ide/controls/TreeTableModel.html">
 * Original&nbsp;Source&nbsp;Location</a>
 *
 * @author Philip Milne
 * @author Scott Violet
 * @author Lars Kühne
 */
public class JTreeTable extends JTable {
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -8493693409423365387L;
    /** A subclass of JTree. */
    protected final TreeTableCellRenderer tree;
    private JTextArea editor;
    private List<Integer> linePositionMap;

    public JTreeTable(TreeTableModel treeTableModel) {

        // Create the tree. It will be used as a renderer and editor.
        tree = new TreeTableCellRenderer(treeTableModel);

        // Install a tableModel representing the visible rows in the tree.
        setModel(new TreeTableModelAdapter(treeTableModel, tree));

        // Force the JTable and JTree to share their row selection models.
        final ListToTreeSelectionModelWrapper selectionWrapper = new
                ListToTreeSelectionModelWrapper(this);
        tree.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        // Install the tree editor renderer and editor.
        setDefaultRenderer(TreeTableModel.class, tree);
        setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

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

        final Action expand = new AbstractAction() {
            private static final long serialVersionUID = -5859674518660156121L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    final TreePath selected = tree.getSelectionPath();
                    final DetailAST ast = (DetailAST) selected.getLastPathComponent();
                    new CodeSelector(ast, editor, linePositionMap).select();

                    if (tree.isExpanded(selected)) {
                        tree.collapsePath(selected);
                    }
                    else {
                        tree.expandPath(selected);
                    }
                    tree.setSelectionPath(selected);
                }
            };
        final KeyStroke stroke = KeyStroke.getKeyStroke("ENTER");
        final String command = "expand/collapse";
        getInputMap().put(stroke, command);
        getActionMap().put(command, expand);
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
        final Class<?> editingClass = getColumnClass(editingColumn);
        return editingClass == TreeTableModel.class ? -1 : editingRow;
    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    @Override
    public final void setRowHeight(int newRowHeight) {
        super.setRowHeight(newRowHeight);
        if (tree != null && tree.getRowHeight() != newRowHeight) {
            tree.setRowHeight(getRowHeight());
        }
    }

    /**
     * @return the tree that is being shared between the model.
     */
    public JTree getTree() {
        return tree;
    }

    public void setEditor(JTextArea mJTextArea) {
        editor = mJTextArea;
    }

    public void setLinePositionMap(List<Integer> linePositionMap) {
        this.linePositionMap = ImmutableList.copyOf(linePositionMap);
    }

    /**
     * A TreeCellRenderer that displays a JTree.
     */
    class TreeTableCellRenderer extends JTree implements
            TableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = 4324031590789321581L;
        /** Last table/tree row asked to renderer. */
        protected int visibleRow;

        /** creates a new instance */
        public TreeTableCellRenderer(TreeModel model) {
            super(model);
        }

        /**
         * updateUI is overridden to set the colors of the Tree's renderer
         * to match that of the table.
         */
        @Override
        public void updateUI() {
            super.updateUI();
            // Make the tree's cell renderer use the table's cell selection
            // colors.
            final TreeCellRenderer tcr = getCellRenderer();
            if (tcr instanceof DefaultTreeCellRenderer) {
                final DefaultTreeCellRenderer dtcr = (DefaultTreeCellRenderer) tcr;
                // For 1.1 uncomment this, 1.2 has a bug that will cause an
                // exception to be thrown if the border selection color is
                // null.
                // dtcr.setBorderSelectionColor(null);
                dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
                dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
            }
        }

        /**
         * Sets the row height of the tree, and forwards the row height to
         * the table.
         */
        @Override
        public void setRowHeight(int newRowHeight) {
            if (newRowHeight > 0) {
                super.setRowHeight(newRowHeight);
                if (JTreeTable.this != null
                        && JTreeTable.this.getRowHeight() != newRowHeight) {
                    JTreeTable.this.setRowHeight(getRowHeight());
                }
            }
        }

        /**
         * This is overridden to set the height to match that of the JTable.
         */
        @Override
        public void setBounds(int x, int y, int w, int h) {
            super.setBounds(x, 0, w, JTreeTable.this.getHeight());
        }

        /**
         * Sublcassed to translate the graphics such that the last visible
         * row will be drawn at 0,0.
         */
        @Override
        public void paint(Graphics g) {
            g.translate(0, -visibleRow * getRowHeight());
            super.paint(g);
        }

        /**
         * TreeCellRenderer method. Overridden to update the visible row.
         * @see TableCellRenderer
         */
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            }
            else {
                setBackground(table.getBackground());
            }

            visibleRow = row;
            return this;
        }
    }

    /**
     * TreeTableCellEditor implementation. Component returned is the
     * JTree.
     */
    public class TreeTableCellEditor extends AbstractCellEditor implements
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
         * it is forwarded to the tree.<p>
         * The behavior for this is debatable, and should really be offered
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
         * <p>By returning false we are also enforcing the policy that
         * the tree will never be editable (at least by a key sequence).
         *
         * @see TableCellEditor
         */
        @Override
        public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                for (int counter = getColumnCount() - 1; counter >= 0;
                     counter--) {
                    if (getColumnClass(counter) == TreeTableModel.class) {
                        final MouseEvent me = (MouseEvent) e;
                        final MouseEvent newME = new MouseEvent(tree, me.getID(),
                                me.getWhen(), me.getModifiers(),
                                me.getX() - getCellRect(0, counter, true).x,
                                me.getY(), me.getClickCount(),
                                me.isPopupTrigger());
                        tree.dispatchEvent(newME);
                        break;
                    }
                }
            }

            return false;
        }
    }
}
