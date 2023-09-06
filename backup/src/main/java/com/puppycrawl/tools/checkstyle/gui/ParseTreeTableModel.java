///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

/**
 * The model that backs the parse tree in the GUI.
 *
 */
public class ParseTreeTableModel implements TreeModel {

    /** Presentation model. */
    private final ParseTreeTablePresentation pModel;

    /**
     * A list of event listeners for the tree model.
     */
    private final EventListenerList listenerList = new EventListenerList();

    /**
     * Initialise pModel.
     *
     * @param parseTree DetailAST parse tree.
     */
    public ParseTreeTableModel(DetailAST parseTree) {
        pModel = new ParseTreeTablePresentation(parseTree);
        setParseTree(parseTree);
    }

    /**
     * Sets parse tree.
     *
     * @param parseTree DetailAST parse tree.
     */
    protected final void setParseTree(DetailAST parseTree) {
        pModel.setRoot(parseTree);
        final Object[] path = {pModel.getRoot()};
        // no need to setup remaining info, as the call results in a
        // table structure changed event anyway - we just pass nulls
        fireTreeStructureChanged(this, path, null, (Object[]) null);
    }

    /**
     * Set parse mode.
     *
     * @param mode ParseMode enum
     */
    protected void setParseMode(ParseMode mode) {
        pModel.setParseMode(mode);
    }

    /**
     * Returns number of available column.
     *
     * @return the number of available column.
     */
    public int getColumnCount() {
        return pModel.getColumnCount();
    }

    /**
     * Returns column name of specified column number.
     *
     * @param column the column number
     * @return the name for column number {@code column}.
     */
    public String getColumnName(int column) {
        return pModel.getColumnName(column);
    }

    /**
     * Returns type of specified column number.
     *
     * @param column the column number
     * @return the type for column number {@code column}.
     */
    // -@cs[ForbidWildcardAsReturnType] We need to satisfy javax.swing.table.AbstractTableModel
    // public Class<?> getColumnClass(int columnIndex) {...}
    public Class<?> getColumnClass(int column) {
        return pModel.getColumnClass(column);
    }

    /**
     * Returns the value to be displayed for node at column number.
     *
     * @param node the node
     * @param column the column number
     * @return the value to be displayed for node {@code node},
     *     at column number {@code column}.
     */
    public Object getValueAt(Object node, int column) {
        return pModel.getValueAt(node, column);
    }

    @Override
    public Object getChild(Object parent, int index) {
        return pModel.getChild(parent, index);
    }

    @Override
    public int getChildCount(Object parent) {
        return pModel.getChildCount(parent);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // No Code, as tree is read-only
    }

    @Override
    public Object getRoot() {
        return pModel.getRoot();
    }

    @Override
    public boolean isLeaf(Object node) {
        return pModel.isLeaf(node);
    }

    // This is not called in the JTree's default mode: use a naive implementation.
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return pModel.getIndexOfChild(parent, child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        listenerList.add(TreeModelListener.class, listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listenerList.remove(TreeModelListener.class, listener);
    }

    /**
     * Notify all listeners that have registered interest in
     * 'tree structure changed' event.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source The Object responsible for generating the event.
     * @param path An array of Object identifying the path to the parent of the modified items.
     * @param childIndices An array of int that specifies the index values of the removed items.
     * @param children An array of Object containing the inserted, removed, or changed objects.
     * @see EventListenerList
     */
    private void fireTreeStructureChanged(Object source, Object[] path,
                                  int[] childIndices,
                                  Object... children) {
        // Guaranteed to return a non-null array
        final Object[] listeners = listenerList.getListenerList();
        TreeModelEvent event = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (event == null) {
                    event = new TreeModelEvent(source, path,
                        childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(event);
            }
        }
    }

    /**
     * Indicates whether the value for node {@code node},
     * at column number {@code column} is editable.
     *
     * @param column the column number
     * @return true if editable
     */
    public boolean isCellEditable(int column) {
        return pModel.isCellEditable(column);
    }

}
