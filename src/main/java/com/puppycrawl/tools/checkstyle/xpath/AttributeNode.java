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

package com.puppycrawl.tools.checkstyle.xpath;

import java.util.List;

import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.type.Type;

/**
 * Represents attribute of the element.
 *
 */
public class AttributeNode extends AbstractNode {

    /** The name of the attribute. */
    private final String name;

    /** The value of the attribute. */
    private final String value;

    /**
     * Creates a new {@code AttributeNode} instance.
     *
     * @param name name of the attribute
     * @param value value of the attribute
     */
    public AttributeNode(String name, String value) {
        super(null);
        this.name = name;
        this.value = value;
    }

    /**
     * Compares current object with specified for order.
     * Throws {@code UnsupportedOperationException} because functionality not required here.
     *
     * @param nodeInfo another {@code NodeInfo} object
     * @return number representing order of current object to specified one
     */
    @Override
    public int compareOrder(NodeInfo nodeInfo) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns attribute value. Throws {@code UnsupportedOperationException} because attribute node
     * has no attributes.
     *
     * @param namespace namespace
     * @param localPart actual name of the attribute
     * @return attribute value
     */
    @Override
    public String getAttributeValue(NamespaceUri namespace, String localPart) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns local part.
     *
     * @return local part
     */
    @Override
    public String getLocalPart() {
        return name;
    }

    /**
     * Returns type of the node.
     *
     * @return node kind
     */
    @Override
    public int getNodeKind() {
        return Type.ATTRIBUTE;
    }

    /**
     * Returns parent.  Never called for attribute node, throws
     * {@code UnsupportedOperationException}.
     * has no attributes.
     *
     * @return parent
     */
    @Override
    public NodeInfo getParent() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns root. Never called for attribute node, throws
     * {@code UnsupportedOperationException}.
     *
     * @return root
     */
    @Override
    public NodeInfo getRoot() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns string value.
     *
     * @return string value
     */
    @Override
    public String getStringValue() {
        return value;
    }

    /**
     * Determines axis iteration algorithm. Attribute node can not be iterated, throws
     * {@code UnsupportedOperationException}.
     *
     * @param axisNumber element from {@code AxisInfo}
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(int axisNumber) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns line number. Attribute node has no line number, throws
     * {@code UnsupportedOperationException}.
     *
     * @return line number
     */
    @Override
    public int getLineNumber() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns column number. Attribute node has no column number, throws
     * {@code UnsupportedOperationException}.
     *
     * @return column number
     */
    @Override
    public int getColumnNumber() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Getter method for token type. Attribute node has no token type, throws
     * {@code UnsupportedOperationException}.
     *
     * @return token type
     */
    @Override
    public int getTokenType() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns underlying node. Attribute node has no underlying node, throws
     * {@code UnsupportedOperationException}.
     *
     * @return underlying node
     */
    @Override
    public Object getUnderlyingNode() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Getter method for node depth. This method is not applicable to attribute nodes,
     * throws unsupported exception.
     *
     * @return never
     */
    @Override
    public int getDepth() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Creates nodes for children. Attribute node has no children, so
     * this method throws unsupported exception.
     *
     * @return never
     */
    @Override
    protected List<AbstractNode> createChildren() {
        throw throwUnsupportedOperationException();
    }

    /**
     * Determine whether the node has any children.
     *
     * @return always {@code false}
     */
    @Override
    public boolean hasChildNodes() {
        return false;
    }

    /**
     * Returns UnsupportedOperationException exception.
     *
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }

}
