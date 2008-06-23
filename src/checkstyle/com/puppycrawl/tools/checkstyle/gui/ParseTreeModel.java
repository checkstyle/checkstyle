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

import antlr.ASTFactory;
import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * The model that backs the parse tree in the GUI.
 *
 * @author Lars Kühne
 */
public class ParseTreeModel extends AbstractTreeTableModel
{
    private static final String[] COLUMN_NAMES = new String[]{
        "Tree", "Type", "Line", "Column", "Text"
    };

    public ParseTreeModel(DetailAST parseTree)
    {
        super(createArtificialTreeRoot());
        setParseTree(parseTree);
    }

    private static DetailAST createArtificialTreeRoot()
    {
        final ASTFactory factory = new ASTFactory();
        factory.setASTNodeClass(DetailAST.class.getName());
        // TODO: Need to resolve if need a fake root node....
        return (DetailAST) factory.create(TokenTypes.EOF, "ROOT");
    }

    void setParseTree(DetailAST parseTree)
    {
        final DetailAST root = (DetailAST) getRoot();
        root.setFirstChild(parseTree);
        final Object[] path = {root};
        // no need to setup remaining info, as the call results in a
        // table structure changed event anyway - we just pass nulls
        fireTreeStructureChanged(this, path, null, null);
    }

    public int getColumnCount()
    {
        return COLUMN_NAMES.length;
    }

    public String getColumnName(int column)
    {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        switch (column) {
            case 0:
                return TreeTableModel.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return String.class;
        }
        return Object.class;
    }

    public Object getValueAt(Object node, int column)
    {
        final DetailAST ast = (DetailAST) node;
        switch (column) {
            case 0:
                return null;
            case 1:
                return TokenTypes.getTokenName(ast.getType());
            case 2:
                return ast.getLineNo();
            case 3:
                return ast.getColumnNo();
            case 4:
                return ast.getText();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column)
    {
    }

    public Object getChild(Object parent, int index)
    {
        final DetailAST ast = (DetailAST) parent;
        int i = 0;
        AST child = ast.getFirstChild();
        while (i < index) {
            child = child.getNextSibling();
            i++;
        }
        return child;
    }

    public int getChildCount(Object parent)
    {
        final DetailAST ast = (DetailAST) parent;
        return ast.getChildCount();
    }

}
