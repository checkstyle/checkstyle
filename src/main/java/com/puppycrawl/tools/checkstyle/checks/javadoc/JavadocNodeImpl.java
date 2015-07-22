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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 *
 * Implementation of DetailNode interface that is mutable.
 *
 * @author Baratali Izmailov
 *
 */
public class JavadocNodeImpl implements DetailNode {
    /**
     * Node index among parent's children
     */
    private int index;

    /**
     * Node type
     */
    private int type;

    /**
     * Node's text content
     */
    private String text;

    /**
     * Line number
     */
    private int lineNumber;

    /**
     * Column number
     */
    private int columnNumber;

    /**
     * Array of child nodes
     */
    private DetailNode[] children;

    /**
     * Parent node
     */
    private DetailNode parent;

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
        return lineNumber;
    }

    @Override
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public DetailNode[] getChildren() {
        if (children == null) {
            return new DetailNode[0];
        }
        else {
            return Arrays.copyOf(children, children.length);
        }
    }

    @Override
    public DetailNode getParent() {
        return parent;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setChildren(DetailNode... children) {
        this.children = Arrays.copyOf(children, children.length);
    }

    public void setParent(DetailNode parent) {
        this.parent = parent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return JavadocUtils.getTokenName(getType())
                + "[" + getLineNumber() + "x" + getColumnNumber() + "]";
    }

}
