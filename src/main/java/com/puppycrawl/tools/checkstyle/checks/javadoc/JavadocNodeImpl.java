///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;
import org.antlr.v4.runtime.Token;

import java.util.Optional;

/**
 * Implementation of DetailNode interface that is mutable.
 */
public class JavadocNodeImpl implements DetailNode {

    /**
     * Constant to indicate if not calculated the child count.
     */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /**
     * Empty array of {@link DetailNode} type.
     */
    public static final JavadocNodeImpl[] EMPTY_DETAIL_NODE_ARRAY = new JavadocNodeImpl[0];

    /**
     * Node index among parent's children.
     */
    private int index;

    /**
     * Node type.
     */
    private int type;

    /**
     * Node's text content.
     */
    private String text;

    /**
     * Line number.
     */
    private int lineNumber = NOT_INITIALIZED;

    /**
     * Column number.
     */
    private int columnNumber = NOT_INITIALIZED;

    /**
     * Array of child nodes.
     */
    private DetailNode[] children;

    /**
     * Parent node.
     */
    private JavadocNodeImpl parent;

    /**
     * Next sibling node.
     */
    private JavadocNodeImpl nextSibling;

    /**
     * Previous sibling.
     */
    private JavadocNodeImpl previousSibling;

    /**
     * First child of this DetailAST.
     */
    private JavadocNodeImpl firstChild;


    public void initialize(Token token) {
        this.type = token.getType();
        this.text = token.getText();
        this.lineNumber = token.getLine() - 1;
        this.columnNumber = token.getCharPositionInLine();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getLineNumber() {
        if (lineNumber == NOT_INITIALIZED) {
            JavadocNodeImpl node = this.firstChild;
            while (node != null && node.getLineNumber() == NOT_INITIALIZED) {
                node = (JavadocNodeImpl) node.getNextSibling();
            }
            if (node != null) {
                lineNumber = node.getLineNumber();
            }
        }
        return this.lineNumber;
    }

    @Override
    public int getColumnNumber() {
        if (columnNumber == NOT_INITIALIZED) {
            JavadocNodeImpl node = this.firstChild;
            while (node != null && node.getColumnNumber() == NOT_INITIALIZED) {
                node = (JavadocNodeImpl) node.getNextSibling();
            }
            if (node != null) {
                columnNumber = node.getColumnNumber();
            }
        }
        return this.columnNumber;
    }

    @Override
    public DetailNode[] getChildren() {
        return Optional.ofNullable(children)
                .map(array -> UnmodifiableCollectionUtil.copyOfArray(array, array.length))
                .orElse(EMPTY_DETAIL_NODE_ARRAY);
    }

    @Override
    public DetailNode getParent() {
        return parent;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public DetailNode getNextSibling() {
        return nextSibling;
    }

    @Override
    public DetailNode getPreviousSibling() {
        return previousSibling;
    }

    @Override
    public JavadocNodeImpl getFirstChild() {
        return firstChild;
    }

    /**
     * Sets node's type.
     *
     * @param type Node's type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Sets node's text content.
     *
     * @param text Node's text content.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets line number.
     *
     * @param lineNumber Line number.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Sets column number.
     *
     * @param columnNumber Column number.
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Sets array of child nodes.
     *
     * @param children Array of child nodes.
     */
    public void setChildren(DetailNode... children) {
        this.children = UnmodifiableCollectionUtil.copyOfArray(children, children.length);
    }

    /**
     * Sets parent node.
     *
     * @param node Parent node.
     */
    public void setParent(DetailNode node) {
        JavadocNodeImpl instance = this;
        JavadocNodeImpl parent = (JavadocNodeImpl) node;
        do {
            instance.parent = parent;
            instance = instance.nextSibling;
        } while (instance != null);
    }

    /**
     * Sets node's index among parent's children.
     *
     * @param index Node's index among parent's children.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets next sibling node.
     *
     * @param nextSibling Next sibling node.
     */
    public void setNextSibling(DetailNode nextSibling) {
        this.nextSibling = (JavadocNodeImpl) nextSibling;
        if (nextSibling != null && parent != null) {
            ((JavadocNodeImpl) nextSibling).setParent(parent);
        }
        if (nextSibling != null) {
            ((JavadocNodeImpl) nextSibling).previousSibling = this;
        }
    }

    /**
     * Adds a child node to this node.
     *
     * @param newChild Child node to be added.
     */
    public void addChild(DetailNode newChild) {
        final JavadocNodeImpl astImpl = (JavadocNodeImpl) newChild;
        astImpl.setParent(this);

        DetailNode temp = firstChild;
        if (temp == null) {
            firstChild = (JavadocNodeImpl) newChild;
        } else {
            while (temp.getNextSibling() != null) {
                temp = temp.getNextSibling();
            }

            ((JavadocNodeImpl) temp).setNextSibling(newChild);
        }
    }

    @Override
    public String toString() {
        return text + "[" + getLineNumber() + "x" + getColumnNumber() + "]";
    }

}
