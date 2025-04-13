///
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * Implementation of DetailNode interface that is mutable.
 *
 */
public class JavadocNodeImpl implements DetailNode {

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
    private int lineNumber;

    /**
     * Column number.
     */
    private int columnNumber;

    /**
     * Array of child nodes.
     */
    private DetailNode[] children;

    /**
     * Parent node.
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
     * @param parent Parent node.
     */
    public void setParent(DetailNode parent) {
        this.parent = parent;
    }

    /**
     * Sets node's index among parent's children.
     *
     * @param index Node's index among parent's children.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "JavadocNodeImpl["
                + "index=" + index
                + ", type=" + JavadocUtil.getTokenName(type)
                + ", text='" + text + '\''
                + ", lineNumber=" + lineNumber
                + ", columnNumber=" + columnNumber
                + ", children=" + Arrays.hashCode(children)
                + ", parent=" + parent + ']';
    }

}
