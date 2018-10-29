////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 * A TreeCellRenderer that displays a JTree.
 */
class TreeTableCellRenderer extends JTree implements
        TableCellRenderer {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 4324031590789321581L;

    /** Tree table to render. */
    private final TreeTable treeTable;

    /** Last table/tree row asked to renderer. */
    private int visibleRow;

    /**
     * Creates a new instance.
     * @param treeTable tree table to render.
     * @param model Tree model.
     */
    TreeTableCellRenderer(TreeTable treeTable, TreeModel model) {
        super(model);
        this.treeTable = treeTable;
    }

    /**
     * UpdateUI is overridden to set the colors of the Tree's renderer
     * to match that of the table.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        // Make the tree's cell renderer use the table's cell selection
        // colors.
        final TreeCellRenderer tcr = getCellRenderer();
        if (tcr instanceof DefaultTreeCellRenderer) {
            final DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tcr;
            // For 1.1 uncomment this, 1.2 has a bug that will cause an
            // exception to be thrown if the border selection color is
            // null.
            // renderer.setBorderSelectionColor(null);
            renderer.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
            renderer.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
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
            if (treeTable != null
                    && treeTable.getRowHeight() != newRowHeight) {
                treeTable.setRowHeight(getRowHeight());
            }
        }
    }

    /**
     * This is overridden to set the height to match that of the JTable.
     */
    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, 0, w, treeTable.getHeight());
    }

    /**
     * Subclassed to translate the graphics such that the last visible
     * row will be drawn at 0,0.
     */
    @Override
    public void paint(Graphics graph) {
        graph.translate(0, -visibleRow * getRowHeight());
        super.paint(graph);
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
