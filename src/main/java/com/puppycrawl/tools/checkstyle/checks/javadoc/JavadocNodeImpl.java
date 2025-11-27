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

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import org.antlr.v4.runtime.Token;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 * Implementation of DetailNode interface that is mutable.
 *
 * @noinspection FieldNotUsedInToString
 * @noinspectionreason FieldNotUsedInToString - We require a specific string format for
 *       printing to CLI.
 */
public class JavadocNodeImpl implements DetailNode {

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
    private int lineNumber;

    /**
     * Column number.
     */
    private int columnNumber;

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

    /**
     * Initializes the node from the given token.
     *
     * @param token the token to initialize from.
     */
    public void initialize(Token token) {
        type = token.getType();
        text = token.getText();
        lineNumber = token.getLine() - 1;
        columnNumber = token.getCharPositionInLine();
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
        final JavadocNodeImpl node = firstChild;
        if (node != null) {
            lineNumber = node.getLineNumber();
        }
        return lineNumber;
    }

    @Override
    public int getColumnNumber() {
        final JavadocNodeImpl node = firstChild;
        if (node != null) {
            columnNumber = node.getColumnNumber();
        }
        return columnNumber;
    }

    @Override
    public DetailNode getParent() {
        return parent;
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
     * Sets parent node.
     *
     * @param node Parent node.
     */
    public void setParent(DetailNode node) {
        JavadocNodeImpl instance = this;
        final JavadocNodeImpl newParent = (JavadocNodeImpl) node;
        do {
            instance.parent = newParent;
            instance = instance.nextSibling;
        } while (instance != null);
    }

    /**
     * Sets next sibling node.
     *
     * @param nextSibling Next sibling node.
     */
    public void setNextSibling(DetailNode nextSibling) {
        this.nextSibling = (JavadocNodeImpl) nextSibling;
        ((JavadocNodeImpl) nextSibling).setParent(parent);
        ((JavadocNodeImpl) nextSibling).previousSibling = this;
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
        }
        else {
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

    @Nullable
    @Override
    public DetailNode findFirstToken(int tokenType) {
        DetailNode returnValue = null;
        for (DetailNode ast = firstChild; ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == tokenType) {
                returnValue = ast;
                break;
            }
        }
        return returnValue;
    }

}
