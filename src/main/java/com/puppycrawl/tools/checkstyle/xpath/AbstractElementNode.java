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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.xpath.iterators.DescendantIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.FollowingIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.PrecedingIterator;
import com.puppycrawl.tools.checkstyle.xpath.iterators.ReverseListIterator;

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;
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
     * @param other another {@code NodeInfo} object
     * @return number representing order of current object to specified one
     */
    @Override
    public int compareOrder(NodeInfo other) {
        int result = 0;
        if (other instanceof AbstractNode) {
            result = Integer.compare(depth, ((AbstractNode) other).getDepth());
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
     * @param first {@code NodeInfo} to compare
     * @param second {@code NodeInfo} to compare
     * @return the value {@code 0} if {@code first == second};
     *         a value less than {@code 0} if {@code first} should be first;
     *         a value greater than {@code 0} if {@code second} should be first.
     */
    private static int compareCommonAncestorChildrenOrder(NodeInfo first, NodeInfo second) {
        NodeInfo child1 = first;
        NodeInfo child2 = second;
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
     * Determines axis iteration algorithm. Throws {@code UnsupportedOperationException} in case,
     * when there is no axis iterator for given axisNumber.
     *
     * <p>Reason of suppression for resource, IOResourceOpenedButNotSafelyClosed:
     * {@link AxisIterator} implements {@link java.io.Closeable} interface,
     * but none of the subclasses of the {@link AxisIterator}
     * class has non-empty {@code close()} method.
     *
     * @param axisNumber element from {@code AxisInfo}
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(int axisNumber) {
        final AxisIterator result;
        switch (axisNumber) {
            case AxisInfo.ANCESTOR:
                result = new Navigator.AncestorEnumeration(this, false);
                break;
            case AxisInfo.ANCESTOR_OR_SELF:
                result = new Navigator.AncestorEnumeration(this, true);
                break;
            case AxisInfo.ATTRIBUTE:
                result = SingleNodeIterator.makeIterator(getAttributeNode());
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
            case AxisInfo.PARENT:
                result = SingleNodeIterator.makeIterator(parent);
                break;
            case AxisInfo.SELF:
                result = SingleNodeIterator.makeIterator(this);
                break;
            case AxisInfo.FOLLOWING_SIBLING:
                result = getFollowingSiblingsIterator();
                break;
            case AxisInfo.PRECEDING_SIBLING:
                result = getPrecedingSiblingsIterator();
                break;
            case AxisInfo.FOLLOWING:
                result = new FollowingIterator(this);
                break;
            case AxisInfo.PRECEDING:
                result = new PrecedingIterator(this);
                break;
            default:
                throw throwUnsupportedOperationException();
        }

        return result;
    }

    /**
     * Returns preceding sibling axis iterator.
     *
     * <p>Reason of suppression for resource, IOResourceOpenedButNotSafelyClosed:
     * {@link AxisIterator} implements {@link java.io.Closeable} interface,
     * but none of the subclasses of the {@link AxisIterator}
     * class has non-empty {@code close()} method.
     *
     * @return iterator
     */
    private AxisIterator getPrecedingSiblingsIterator() {
        final AxisIterator result;
        if (indexAmongSiblings == 0) {
            result = EmptyIterator.ofNodes();
        }
        else {
            result = new ReverseListIterator(getPrecedingSiblings());
        }
        return result;
    }

    /**
     * Returns following sibling axis iterator.
     *
     * <p>Reason of suppression for resource, IOResourceOpenedButNotSafelyClosed:
     * {@link AxisIterator} implements {@link java.io.Closeable} interface,
     * but none of the subclasses of the {@link AxisIterator}
     * class has non-empty {@code close()} method.
     *
     * @return iterator
     */
    private AxisIterator getFollowingSiblingsIterator() {
        final AxisIterator result;
        if (indexAmongSiblings == parent.getChildren().size() - 1) {
            result = EmptyIterator.ofNodes();
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
    private AttributeNode getAttributeNode() {
        if (attributeNode == ATTRIBUTE_NODE_UNINITIALIZED) {
            attributeNode = createAttributeNode();
        }
        return attributeNode;
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
