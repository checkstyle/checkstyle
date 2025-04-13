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

import com.puppycrawl.tools.checkstyle.xpath.iterators.DescendantIterator;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.GenericTreeInfo;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;
import net.sf.saxon.type.Type;

/**
 * Represents root node of Xpath-tree.
 */
public abstract class AbstractRootNode extends AbstractNode {

    /** Name of the root element. */
    private static final String ROOT_NAME = "ROOT";

    /** Constant for optimization. */
    private static final AbstractNode[] EMPTY_ABSTRACT_NODE_ARRAY = new AbstractNode[0];

    /**
     * Creates a new {@code AbstractRootNode} instance.
     */
    protected AbstractRootNode() {
        super(new GenericTreeInfo(Configuration.newConfiguration()));
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
     * Returns attribute value. Throws {@code UnsupportedOperationException} because root node
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
        return ROOT_NAME;
    }

    /**
     * Returns type of the node.
     *
     * @return node kind
     */
    @Override
    public int getNodeKind() {
        return Type.DOCUMENT;
    }

    /**
     * Returns parent.
     *
     * @return parent
     */
    @Override
    public NodeInfo getParent() {
        return null;
    }

    /**
     * Returns root of the tree.
     *
     * @return root of the tree
     */
    @Override
    public NodeInfo getRoot() {
        return this;
    }

    /**
     * Determines axis iteration algorithm. Throws {@code UnsupportedOperationException} in case,
     * when there is no axis iterator for given axisNumber.
     *
     * @param axisNumber element from {@code AxisInfo}
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(int axisNumber) {
        final AxisIterator result;
        switch (axisNumber) {
            case AxisInfo.ANCESTOR:
            case AxisInfo.PARENT:
            case AxisInfo.FOLLOWING:
            case AxisInfo.FOLLOWING_SIBLING:
            case AxisInfo.PRECEDING:
            case AxisInfo.PRECEDING_SIBLING:
                result = EmptyIterator.ofNodes();
                break;
            case AxisInfo.ANCESTOR_OR_SELF:
            case AxisInfo.SELF:
                result = SingleNodeIterator.makeIterator(this);
                break;
            case AxisInfo.CHILD:
                if (hasChildNodes()) {
                    result = new ArrayIterator.OfNodes<>(
                        getChildren().toArray(EMPTY_ABSTRACT_NODE_ARRAY));
                }
                else {
                    result = EmptyIterator.ofNodes();
                }
                break;
            case AxisInfo.DESCENDANT:
                if (hasChildNodes()) {
                    result = new DescendantIterator(this, DescendantIterator.StartWith.CHILDREN);
                }
                else {
                    result = EmptyIterator.ofNodes();
                }
                break;
            case AxisInfo.DESCENDANT_OR_SELF:
                result = new DescendantIterator(this, DescendantIterator.StartWith.CURRENT_NODE);
                break;
            default:
                throw throwUnsupportedOperationException();
        }
        return result;
    }

    /**
     * Getter method for node depth.
     *
     * @return always {@code 0}
     */
    @Override
    public int getDepth() {
        return 0;
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
