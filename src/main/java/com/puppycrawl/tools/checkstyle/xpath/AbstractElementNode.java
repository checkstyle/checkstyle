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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.xpath.iterators.DescendantIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.FollowingIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.PrecedingIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.ReverseListIterator;
import net.sf.saxon.om.GNode;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.pattern.nodetest.NodePredicate;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.Type;

/**
 * Represents element node of Xpath-tree.
 */
public abstract class AbstractElementNode extends AbstractNode {

    /** String literal for text attribute. */
    protected static final String TEXT_ATTRIBUTE_NAME = "text";

    /** Constant for optimization. */
    private static final AbstractNode[] EMPTY_ABSTRACT_NODE_ARRAY = new AbstractNode[0];

    /** Holder value for lazy creation of attribute node. */
    private static final AttributeNode ATTRIBUTE_NODE_UNINITIALIZED = new AttributeNode(null, null);

    /** The root node. */
    private final AbstractNode root;

    /** The parent of the current node. */
    private final AbstractNode parent;

    /** Depth of the node. */
    private final int depth;

    /** Represents index among siblings. */
    private final int indexAmongSiblings;

    /** The text attribute node. */
    private AttributeNode attributeNode = ATTRIBUTE_NODE_UNINITIALIZED;

    /**
     * Creates a new {@code AbstractElementNode} instance.
     *
     * @param root {@code Node} root of the tree
     * @param parent {@code Node} parent of the current node
     * @param depth the current node depth in the hierarchy
     * @param indexAmongSiblings the current node index among the parent children nodes
     */
    protected AbstractElementNode(AbstractNode root, AbstractNode parent,
            int depth, int indexAmongSiblings) {
        super(root.getTreeInfo());
        this.parent = parent;
        this.root = root;
        this.depth = depth;
        this.indexAmongSiblings = indexAmongSiblings;
    }

    /**
     * Creates {@code AttributeNode} object and returns it.
     *
     * @return attribute node if possible, otherwise the {@code null} value
     */
    protected abstract AttributeNode createAttributeNode();

    /**
     * Compares current object with specified for order.
     *
     * @param other another {@code GNode} object
     * @return number representing order of current object to specified one
     */
    @Override
    public int compareOrder(GNode other) {
        int result = 0;
        if (other instanceof final AbstractNode node) {
            result = Integer.compare(depth, node.getDepth());
            if (result == 0) {
                result = compareCommonAncestorChildrenOrder(this, other);
            }
        }
        return result;
    }

    /**
     * Walks up the hierarchy until a common ancestor is found.
     * Then compares topmost sibling nodes.
     *
     * @param first {@code GNode} to compare
     * @param second {@code GNode} to compare
     * @return the value {@code 0} if {@code first == second}; a value less than
     *         {@code 0} if {@code first} should be first; a value greater than
     *         {@code 0} if {@code second} should be first.
     */
    private static int compareCommonAncestorChildrenOrder(GNode first, GNode second) {
        GNode child1 = first;
        GNode child2 = second;
        while (!child1.getParent().equals(child2.getParent())) {
            child1 = child1.getParent();
            child2 = child2.getParent();
        }
        final int index1 = ((AbstractElementNode) child1).indexAmongSiblings;
        final int index2 = ((AbstractElementNode) child2).indexAmongSiblings;
        return Integer.compare(index1, index2);
    }

    /**
     * Getter method for node depth.
     *
     * @return depth
     */
    @Override
    public int getDepth() {
        return depth;
    }

    /**
     * Returns attribute value.
     *
     * @param namespace namespace
     * @param localPart actual name of the attribute
     * @return attribute value or null if the attribute was not found
     */
    @Override
    public String getAttributeValue(NamespaceUri namespace, String localPart) {
        final String result;
        if (TEXT_ATTRIBUTE_NAME.equals(localPart)) {
            result = Optional.ofNullable(getAttributeNode())
                .map(AttributeNode::getStringValue)
                .orElse(null);
        }
        else {
            result = null;
        }
        return result;
    }

    /**
     * Returns type of the node.
     *
     * @return node kind
     */
    @Override
    public int getNodeKind() {
        return Type.ELEMENT;
    }

    /**
     * Returns parent.
     *
     * @return parent
     */
    @Override
    public NodeInfo getParent() {
        return parent;
    }

    /**
     * Returns root.
     *
     * @return root
     */
    @Override
    public AbstractNode getRoot() {
        return root;
    }

    /**
     * Returns attribute axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the {@code @text} attribute node, or an empty iterator
     */
    @Override
    public SequenceIterator iterateAttributeAxis(NodePredicate predicate) {
        final AttributeNode attribute = getAttributeNode();
        final SequenceIterator result;
        if (attribute == null) {
            result = EmptyIterator.getInstance();
        }
        else {
            result = Navigator.filteredSingleton(attribute, predicate);
        }
        return result;
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
            result = EmptyIterator.getInstance();
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
            result = EmptyIterator.getInstance();
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
     * Returns following-sibling axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the following siblings
     */
    @Override
    public SequenceIterator iterateFollowingSiblingAxis(NodePredicate predicate) {
        return filter(getFollowingSiblingsIterator(), predicate);
    }

    /**
     * Returns preceding-sibling axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the preceding siblings
     */
    @Override
    public SequenceIterator iteratePrecedingSiblingAxis(NodePredicate predicate) {
        return filter(getPrecedingSiblingsIterator(), predicate);
    }

    /**
     * Returns following axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the following nodes
     */
    @Override
    public SequenceIterator iterateFollowingAxis(NodePredicate predicate) {
        return filter(new FollowingIterator(this), predicate);
    }

    /**
     * Returns preceding axis iterator.
     *
     * @param predicate filter for the iterator, may be null
     * @return iterator over the preceding nodes
     */
    @Override
    public SequenceIterator iteratePrecedingAxis(NodePredicate predicate) {
        return filter(new PrecedingIterator(this), predicate);
    }

    /**
     * Returns preceding sibling axis iterator.
     *
     * @return iterator
     */
    private SequenceIterator getPrecedingSiblingsIterator() {
        final SequenceIterator result;
        if (indexAmongSiblings == 0) {
            result = EmptyIterator.getInstance();
        }
        else {
            result = new ReverseListIterator(getPrecedingSiblings());
        }
        return result;
    }

    /**
     * Returns following sibling axis iterator.
     *
     * @return iterator
     */
    private SequenceIterator getFollowingSiblingsIterator() {
        final SequenceIterator result;
        if (indexAmongSiblings == parent.getChildren().size() - 1) {
            result = EmptyIterator.getInstance();
        }
        else {
            result = new ArrayIterator.OfNodes<>(
                    getFollowingSiblings().toArray(EMPTY_ABSTRACT_NODE_ARRAY));
        }
        return result;
    }

    /**
     * Returns following siblings of the current node.
     *
     * @return siblings
     */
    private List<AbstractNode> getFollowingSiblings() {
        final List<AbstractNode> siblings = parent.getChildren();
        return siblings.subList(indexAmongSiblings + 1, siblings.size());
    }

    /**
     * Returns preceding siblings of the current node.
     *
     * @return siblings
     */
    private List<AbstractNode> getPrecedingSiblings() {
        final List<AbstractNode> siblings = parent.getChildren();
        return Collections.unmodifiableList(siblings.subList(0, indexAmongSiblings));
    }

    /**
     * Checks if token type supports {@code @text} attribute,
     * extracts its value, creates {@code AttributeNode} object and returns it.
     * Value can be accessed using {@code @text} attribute.
     *
     * @return attribute node if possible, otherwise the {@code null} value
     */
    @SuppressWarnings("ReferenceEquality")
    private AttributeNode getAttributeNode() {
        if (attributeNode == ATTRIBUTE_NODE_UNINITIALIZED) {
            attributeNode = createAttributeNode();
        }
        return attributeNode;
    }

    /**
     * Namespace axis is not supported for element nodes.
     *
     * @param predicate filter for the iterator, may be null
     * @return never
     */
    @Override
    public SequenceIterator iterateNamespaceAxis(NodePredicate predicate) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

}
