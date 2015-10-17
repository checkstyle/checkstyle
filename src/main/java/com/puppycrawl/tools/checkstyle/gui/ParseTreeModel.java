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
public class ParseTreeModel extends AbstractTreeTableModel {
    /** Column names. */
    private static final String[] COLUMN_NAMES = {
        "Tree", "Type", "Line", "Column", "Text",
    };

    /**
     * @param parseTree DetailAST parse tree.
     */
    public ParseTreeModel(DetailAST parseTree) {
        super(createArtificialTreeRoot());
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
        Class<?> columnClass;

        switch (column) {
            case 0:
                columnClass = TreeTableModel.class;
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

    @Override
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
}
