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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * The model that backs the parse tree in the GUI.
 *
 */
public class ParseTreeTablePresentation {

    /** Exception message. */
    private static final String UNKNOWN_COLUMN_MSG = "Unknown column";

    /** Column names. */
    private static final String[] COLUMN_NAMES = {
        "Tree",
        "Type",
        "Line",
        "Column",
        "Text",
    };

    /** Cache to store already parsed Javadoc comments. Used for optimisation purposes. */
    private final Map<DetailAST, DetailNode> blockCommentToJavadocTree = new HashMap<>();

    /** The root node of the tree table model. */
    private DetailAST root;

    /** Parsing mode. */
    private ParseMode parseMode;

    /**
     * Constructor initialise root node.
     *
     * @param parseTree DetailAST parse tree.
     */
    public ParseTreeTablePresentation(DetailAST parseTree) {
        root = parseTree;
    }

    /**
     * Set parse tree.
     *
     * @param parseTree DetailAST parse tree.
     */
    protected final void setRoot(DetailAST parseTree) {
        root = parseTree;
    }

    /**
     * Set parse mode.
     *
     * @param mode ParseMode enum
     */
    protected void setParseMode(ParseMode mode) {
        parseMode = mode;
    }

    /**
     * Returns number of available columns.
     *
     * @return the number of available columns.
     */
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * Returns name for specified column number.
     *
     * @param column the column number
     * @return the name for column number {@code column}.
     */
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    /**
     * Returns type of specified column number.
     *
     * @param column the column number
     * @return the type for column number {@code column}.
     * @throws IllegalStateException if an unknown column index was specified.
     */
    // -@cs[ForbidWildcardAsReturnType] We need to satisfy javax.swing.table.AbstractTableModel
    // public Class<?> getColumnClass(int columnIndex) {...}
    public Class<?> getColumnClass(int column) {
        final Class<?> columnClass;

        switch (column) {
            case 0:
                columnClass = ParseTreeTableModel.class;
                break;
            case 1:
            case 4:
                columnClass = String.class;
                break;
            case 2:
            case 3:
                columnClass = Integer.class;
                break;
            default:
                throw new IllegalStateException(UNKNOWN_COLUMN_MSG);
        }
        return columnClass;
    }

    /**
     * Returns the value to be displayed for node at column number.
     *
     * @param node the node
     * @param column the column number
     * @return the value to be displayed for node {@code node}, at column number {@code column}.
     */
    public Object getValueAt(Object node, int column) {
        final Object result;

        if (node instanceof DetailNode) {
            result = getValueAtDetailNode((DetailNode) node, column);
        }
        else {
            result = getValueAtDetailAST((DetailAST) node, column);
        }

        return result;
    }

    /**
     * Returns the child of parent at index.
     *
     * @param parent the node to get a child from.
     * @param index the index of a child.
     * @return the child of parent at index.
     */
    public Object getChild(Object parent, int index) {
        final Object result;

        if (parent instanceof DetailNode) {
            result = ((DetailNode) parent).getChildren()[index];
        }
        else {
            result = getChildAtDetailAst((DetailAST) parent, index);
        }

        return result;
    }

    /**
     * Returns the number of children of parent.
     *
     * @param parent the node to count children for.
     * @return the number of children of the node parent.
     */
    public int getChildCount(Object parent) {
        final int result;

        if (parent instanceof DetailNode) {
            result = ((DetailNode) parent).getChildren().length;
        }
        else {
            if (parseMode == ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS
                && ((DetailAST) parent).getType() == TokenTypes.COMMENT_CONTENT
                && JavadocUtil.isJavadocComment(((DetailAST) parent).getParent())) {
                // getChildCount return 0 on COMMENT_CONTENT,
                // but we need to attach javadoc tree, that is separate tree
                result = 1;
            }
            else {
                result = ((DetailAST) parent).getChildCount();
            }
        }

        return result;
    }

    /**
     * Returns value of root.
     *
     * @return the root.
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Whether the node is a leaf.
     *
     * @param node the node to check.
     * @return true if the node is a leaf.
     */
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    /**
     * Return the index of child in parent.  If either {@code parent}
     * or {@code child} is {@code null}, returns -1.
     * If either {@code parent} or {@code child} don't
     * belong to this tree model, returns -1.
     *
     * @param parent a node in the tree, obtained from this data source.
     * @param child the node we are interested in.
     * @return the index of the child in the parent, or -1 if either
     *     {@code child} or {@code parent} are {@code null}
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
     * Indicates whether the value for node {@code node}, at column number {@code column} is
     * editable.
     *
     * @param column the column number
     * @return true if editable
     */
    public boolean isCellEditable(int column) {
        return false;
    }

    /**
     * Gets child of DetailAST node at specified index.
     *
     * @param parent DetailAST node
     * @param index child index
     * @return child DetailsAST or DetailNode if child is Javadoc node
     *         and parseMode is JAVA_WITH_JAVADOC_AND_COMMENTS.
     */
    private Object getChildAtDetailAst(DetailAST parent, int index) {
        final Object result;
        if (parseMode == ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS
            && parent.getType() == TokenTypes.COMMENT_CONTENT
            && JavadocUtil.isJavadocComment(parent.getParent())) {
            result = getJavadocTree(parent.getParent());
        }
        else {
            int currentIndex = 0;
            DetailAST child = parent.getFirstChild();
            while (currentIndex < index) {
                child = child.getNextSibling();
                currentIndex++;
            }
            result = child;
        }

        return result;
    }

    /**
     * Gets a value for DetailNode object.
     *
     * @param node DetailNode(Javadoc) node.
     * @param column column index.
     * @return value at specified column.
     * @throws IllegalStateException if an unknown column index was specified.
     */
    private static Object getValueAtDetailNode(DetailNode node, int column) {
        final Object value;

        switch (column) {
            case 0:
                // first column is tree model. no value needed
                value = null;
                break;
            case 1:
                value = JavadocUtil.getTokenName(node.getType());
                break;
            case 2:
                value = node.getLineNumber();
                break;
            case 3:
                value = node.getColumnNumber();
                break;
            case 4:
                value = node.getText();
                break;
            default:
                throw new IllegalStateException(UNKNOWN_COLUMN_MSG);
        }
        return value;
    }

    /**
     * Gets a value for DetailAST object.
     *
     * @param ast DetailAST node.
     * @param column column index.
     * @return value at specified column.
     * @throws IllegalStateException if an unknown column index was specified.
     */
    private static Object getValueAtDetailAST(DetailAST ast, int column) {
        final Object value;

        switch (column) {
            case 0:
                // first column is tree model. no value needed
                value = null;
                break;
            case 1:
                value = TokenUtil.getTokenName(ast.getType());
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
                throw new IllegalStateException(UNKNOWN_COLUMN_MSG);
        }
        return value;
    }

    /**
     * Gets Javadoc (DetailNode) tree of specified block comments.
     *
     * @param blockComment Javadoc comment as a block comment
     * @return root of DetailNode tree
     */
    private DetailNode getJavadocTree(DetailAST blockComment) {
        return blockCommentToJavadocTree.computeIfAbsent(blockComment,
            ParseTreeTablePresentation::parseJavadocTree);
    }

    /**
     * Parses Javadoc (DetailNode) tree of specified block comments.
     *
     * @param blockComment Javadoc comment as a block comment
     * @return root of DetailNode tree
     */
    private static DetailNode parseJavadocTree(DetailAST blockComment) {
        return new JavadocDetailNodeParser().parseJavadocAsDetailNode(blockComment).getTree();
    }

}
