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

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 * An abstract implementation of the TreeTableModel interface, handling
 * the list of listeners.
 *
 * <a href="https://docs.oracle.com/cd/E48246_01/apirefs.1111/e13403/oracle/ide/controls/TreeTableModel.html">Original&nbsp;Source&nbsp;Location</a>
 *
 * @author Philip Milne
 */
public abstract class AbstractTreeTableModel implements TreeTableModel {
    private final Object root;
    private final EventListenerList listenerList = new EventListenerList();

    protected AbstractTreeTableModel(Object root) {
        this.root = root;
    }

    //
    // Default implmentations for methods in the TreeModel interface.
    //

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    // This is not called in the JTree's default mode: use a naive implementation.
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChild(parent, i).equals(child)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        listenerList.add(TreeModelListener.class, listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listenerList.remove(TreeModelListener.class, listener);
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesChanged(Object source, Object[] path,
            int[] childIndices,
            Object... children) {
        // Guaranteed to return a non-null array
        final Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                            childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesInserted(Object source, Object[] path,
            int[] childIndices,
            Object... children) {
        // Guaranteed to return a non-null array
        final Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                            childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path,
            int[] childIndices,
            Object... children) {
        // Guaranteed to return a non-null array
        final Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                            childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    protected void fireTreeStructureChanged(Object source, Object[] path,
            int[] childIndices,
            Object... children) {
        // Guaranteed to return a non-null array
        final Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                            childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    //
    // Default impelmentations for methods in the TreeTableModel interface.
    //

    @Override
    public Class<?> getColumnClass(int column) {
        return Object.class;
    }

    /** By default, make the column with the Tree in it the only editable one.
     *  Making this column editable causes the JTable to forward mouse
     *  and keyboard events in the Tree column to the underlying JTree.
     */
    @Override
    public boolean isCellEditable(Object node, int column) {
        return getColumnClass(column) == TreeTableModel.class;
    }

    // Left to be implemented in the subclass:

    /*
     *   public Object getChild(Object parent, int index)
     *   public int getChildCount(Object parent)
     *   public int getColumnCount()
     *   public String getColumnName(Object node, int column)
     *   public Object getValueAt(Object node, int column)
     */
}
