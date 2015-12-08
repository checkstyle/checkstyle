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
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import antlr.ASTFactory;
import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * The model that backs the parse tree in the GUI.
 *
 * @author Lars Kühne
 */
public class ParseTreeTableModel implements TreeModel {

    /** Column names. */
    private static final String[] COLUMN_NAMES = {
        "Tree", "Type", "Line", "Column", "Text",
    };

    /**
     * The root node of the tree table model.
     */
    private final Object root;

    /**
     * A list of event listeners for the tree model.
     */
    private final EventListenerList listenerList = new EventListenerList();

    /**
     * @param parseTree DetailAST parse tree.
     */
    public ParseTreeTableModel(DetailAST parseTree) {
        root = createArtificialTreeRoot();
        setParseTree(parseTree);
    }

    /**
     * Creates artificial tree root.
     * @return Artificial tree root.
     */
    private static DetailAST createArtificialTreeRoot() {
        final ASTFactory factory = new ASTFactory();
        factory.setASTNodeClass(DetailAST.class.getName());
        return (DetailAST) factory.create(TokenTypes.EOF, "ROOT");
    }

    /**
     * Sets parse tree.
     * @param parseTree DetailAST parse tree.
     */
    final void setParseTree(DetailAST parseTree) {
        ((AST) root).setFirstChild(parseTree);
        final Object[] path = {root};
        // no need to setup remaining info, as the call results in a
        // table structure changed event anyway - we just pass nulls
        fireTreeStructureChanged(this, path, null, (Object[]) null);
    }

    /**
     * @return the number of available column.
     */
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * @param column the column number
     * @return the name for column number {@code column}.
     */
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    /**
     * @param column the column number
     * @return the type for column number {@code column}.
     */
    public Class<?> getColumnClass(int column) {
        Class<?> columnClass;

        switch (column) {
            case 0:
                columnClass = ParseTreeTableModel.class;
                break;
            case 1:
                columnClass = String.class;
                break;
            case 2:
                columnClass = Integer.class;
                break;
            case 3:
                columnClass = Integer.class;
                break;
            case 4:
                columnClass = String.class;
                break;
            default:
                columnClass = Object.class;
        }
        return columnClass;
    }

    /**
     * @param node the node
     * @param column the column number
     * @return the value to be displayed for node {@code node},
     *     at column number {@code column}.
     */
    public Object getValueAt(Object node, int column) {
        final DetailAST ast = (DetailAST) node;
        Object value;

        switch (column) {
            case 1:
                value = TokenUtils.getTokenName(ast.getType());
                break;
            case 2:
                value = ast.getLineNo();
                break;
            case 3:
                value = ast.getColumnNo();
                break;
            case 4:
                value = ast.getText();
                break;
            default:
                value = null;
        }
        return value;
    }

    @Override
    public Object getChild(Object parent, int index) {
        final DetailAST ast = (DetailAST) parent;
        int currentIndex = 0;
        AST child = ast.getFirstChild();
        while (currentIndex < index) {
            child = child.getNextSibling();
            currentIndex++;
        }
        return child;
    }

    @Override
    public int getChildCount(Object parent) {
        final DetailAST ast = (DetailAST) parent;
        return ast.getChildCount();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        //No Code, as tree is read-only
    }

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

    /**
     * Notify all listeners that have registered interest for
     * 'tree structure changed' event.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @param source The Object responsible for generating the event.
     * @param path An array of Object identifying the path to the parent of the modified items.
     * @param childIndices An array of int that specifies the index values of the removed items.
     * @param children An array of Object containing the inserted, removed, or changed objects.
     * @see EventListenerList
     */
    void fireTreeStructureChanged(Object source, Object[] path,
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
     * Indicates whether the the value for node {@code node},
     * at column number {@code column} is editable.
     *
     * @param column the column number
     * @return true if editable
     */
    public boolean isCellEditable(int column) {
        return getColumnClass(column) == ParseTreeTableModel.class;
    }
}
