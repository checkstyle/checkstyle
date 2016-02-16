////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
public class ParseTreeTablePModel {

    /** Column names. */
    private static final String[] COLUMN_NAMES = {
        "Tree", "Type", "Line", "Column", "Text",
    };

    /**
     * The root node of the tree table model.
     */
    private final Object root;

    /**
     * @param parseTree DetailAST parse tree.
     */
    public ParseTreeTablePModel(DetailAST parseTree) {
        root = createArtificialTreeRoot();
        setParseTree(parseTree);
    }

    /**
     * Creates artificial tree root.
     * @return artificial tree root.
     */
    private static DetailAST createArtificialTreeRoot() {
        final ASTFactory factory = new ASTFactory();
        factory.setASTNodeClass(DetailAST.class.getName());
        return (DetailAST) factory.create(TokenTypes.EOF, "ROOT");
    }

    /**
     * Set parse tree.
     * @param parseTree DetailAST parse tree.
     */
    protected final void setParseTree(DetailAST parseTree) {
        ((AST) root).setFirstChild(parseTree);
    }

    /**
     * @return the number of available columns.
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
        final Class<?> columnClass;

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
        final Object value;

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

    /**
     * Returns the child of parent at index.
     * @param parent the node to get a child from.
     * @param index the index of a child.
     * @return the child of parent at index.
     */
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

    /**
     * Returns the number of children of parent.
     * @param parent the node to count children for.
     * @return the number of children of the node parent.
     */
    public int getChildCount(Object parent) {
        final DetailAST ast = (DetailAST) parent;
        return ast.getChildCount();
    }

    /**
     * @return the root.
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Whether the node is a leaf.
     * @param node the node to check.
     * @return true if the node is a leaf.
     */
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    /**
     * Return the index of child in parent.  If either <code>parent</code>
     * or <code>child</code> is <code>null</code>, returns -1.
     * If either <code>parent</code> or <code>child</code> don't
     * belong to this tree model, returns -1.
     *
     * @param parent a node in the tree, obtained from this data source.
     * @param child the node we are interested in.
     * @return the index of the child in the parent, or -1 if either
     *     <code>child</code> or <code>parent</code> are <code>null</code>
     *     or don't belong to this tree model.
     */
    public int getIndexOfChild(Object parent, Object child) {
        int index = -1;
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChild(parent, i).equals(child)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Indicates whether the the value for node {@code node},
     * at column number {@code column} is editable.
     *
     * @param column the column number
     * @return true if editable
     */
    public boolean isCellEditable(int column) {
        return getColumnClass(column).equals(ParseTreeTablePModel.class);
    }
}
