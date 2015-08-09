////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import javax.swing.tree.TreePath;

import antlr.ASTFactory;
import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * The model that backs the parse tree in the GUI.
 *
 * @author Lars Kühne
 */
public class ParseTreeModel extends AbstractTreeTableModel {

    /**
     * Names for the table columns.
     */
    private static final String[] COLUMN_NAMES = {
        "Tree", "Type", "Line", "Column", "Text",
    };
    /**
     * Ordinal index for the 'Tree' column.
     */
    private static final int TREE_COLUMN_INDEX = 0;
    /**
     * Ordinal index for the 'Type' column.
     */
    private static final int TYPE_COLUMN_INDEX = 1;
    /**
     * Ordinal index for the 'Line' column.
     */
    private static final int LINE_COLUMN_INDEX = 2;
    /**
     * Ordinal index for the 'Column' column.
     */
    private static final int COLUMN_COLUMN_INDEX = 3;
    /**
     * Ordinal index for the 'Text' column.
     */
    private static final int TEXT_COLUMN_INDEX = 4;

    public ParseTreeModel(DetailAST parseTree) {
        super(createArtificialTreeRoot());
        setParseTree(parseTree);
    }

    private static DetailAST createArtificialTreeRoot() {
        final ASTFactory factory = new ASTFactory();
        factory.setASTNodeClass(DetailAST.class.getName());
        return (DetailAST) factory.create(TokenTypes.EOF, "ROOT");
    }

    final void setParseTree(DetailAST parseTree) {
        final DetailAST root = (DetailAST) getRoot();
        root.setFirstChild(parseTree);
        final Object[] path = {root};
        // no need to setup remaining info, as the call results in a
        // table structure changed event anyway - we just pass nulls
        fireTreeStructureChanged(this, path, null, (Object[]) null);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case TREE_COLUMN_INDEX:
                return TreeTableModel.class;
            case TYPE_COLUMN_INDEX:
                return String.class;
            case LINE_COLUMN_INDEX:
                return Integer.class;
            case COLUMN_COLUMN_INDEX:
                return Integer.class;
            case TEXT_COLUMN_INDEX:
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(Object node, int column) {
        final DetailAST ast = (DetailAST) node;
        switch (column) {
            case TREE_COLUMN_INDEX:
                return null;
            case TYPE_COLUMN_INDEX:
                return Utils.getTokenName(ast.getType());
            case LINE_COLUMN_INDEX:
                return ast.getLineNo();
            case COLUMN_COLUMN_INDEX:
                return ast.getColumnNo();
            case TEXT_COLUMN_INDEX:
                return ast.getText();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {
        // No code, tree is read-only
    }

    @Override
    public Object getChild(Object parent, int index) {
        final DetailAST ast = (DetailAST) parent;
        int i = 0;
        AST child = ast.getFirstChild();
        while (i < index) {
            child = child.getNextSibling();
            i++;
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
}
