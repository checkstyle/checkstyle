///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import com.puppycrawl.tools.checkstyle.xpath.iterators.DescendantIterator;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.GNode;
import net.sf.saxon.om.GenericTreeInfo;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.pattern.nodetest.NodePredicate;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingletonIterator;
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
     * @param other another {@code GNode} object
     * @return number representing order of current object to specified one
     */
    @Override
    public int compareOrder(GNode other) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns attribute value. Throws {@code UnsupportedOperationException} because root node has
     * no attributes.
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
     * Returns ancestor axis iterator. Root has no ancestors.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iterateAncestorAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns parent axis iterator. Root has no parent.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iterateParentAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns following axis iterator. Root has no following nodes.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iterateFollowingAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns following-sibling axis iterator. Root has no siblings.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iterateFollowingSiblingAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns preceding axis iterator. Root has no preceding nodes.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iteratePrecedingAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns preceding-sibling axis iterator. Root has no siblings.
     *
     * @param predicate filter for the iterator, may be null
     * @return empty iterator
     */
    @Override
    public SequenceIterator iteratePrecedingSiblingAxis(NodePredicate predicate) {
        return createEmptyIterator();
    }

    /**
     * Returns ancestor-or-self axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the current node
     */
    @Override
    public SequenceIterator iterateAncestorOrSelfAxis(NodePredicate predicate) {
        return filter(SingletonIterator.makeIterator(this), predicate);
    }

    /**
     * Returns self axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the current node
     */
    @Override
    public SequenceIterator iterateSelfAxis(NodePredicate predicate) {
        return filter(SingletonIterator.makeIterator(this), predicate);
    }

    /**
     * Returns child axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the children
     */
    @Override
    public SequenceIterator iterateChildAxis(NodePredicate predicate) {
        final SequenceIterator result;
        if (hasChildNodes()) {
            result = filter(new ArrayIterator.OfNodes<>(
                    getChildren().toArray(EMPTY_ABSTRACT_NODE_ARRAY)), predicate);
        }
        else {
            result = createEmptyIterator();
        }
        return result;
    }

    /**
     * Returns descendant axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the descendants
     */
    @Override
    public SequenceIterator iterateDescendantAxis(NodePredicate predicate) {
        final SequenceIterator result;
        if (hasChildNodes()) {
            result = filter(
                new DescendantIterator(this, DescendantIterator.StartWith.CHILDREN), predicate);
        }
        else {
            result = createEmptyIterator();
        }
        return result;
    }

    /**
     * Returns descendant-or-self axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the current node and its descendants
     */
    @Override
    public SequenceIterator iterateDescendantOrSelfAxis(NodePredicate predicate) {
        return filter(
            new DescendantIterator(this, DescendantIterator.StartWith.CURRENT_NODE), predicate);
    }

    /**
     * Attribute axis is not supported for the root node.
     *
     * @param predicate filter for the iterator, may be null
     * @return never
     */
    @Override
    public SequenceIterator iterateAttributeAxis(NodePredicate predicate) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Namespace axis is not supported for the root node.
     *
     * @param predicate filter for the iterator, may be null
     * @return never
     */
    @Override
    public SequenceIterator iterateNamespaceAxis(NodePredicate predicate) {
        throw throwUnsupportedOperationException();
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
     * Creates an iterator without items.
     *
     * @return the shared empty iterator
     */
    private static SequenceIterator createEmptyIterator() {
        return EmptyIterator.getInstance();
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
