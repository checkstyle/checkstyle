////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import com.puppycrawl.tools.checkstyle.utils.XpathUtil;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.Type;

/**
 * Represents element node of Xpath-tree.
 *
 */
public class ElementNode extends AbstractNode {

    /** String literal for text attribute. */
    private static final String TEXT_ATTRIBUTE_NAME = "text";

    /** Constant for optimization. */
    private static final AbstractNode[] EMPTY_ABSTRACT_NODE_ARRAY = new AbstractNode[0];

    /** The root node. */
    private final AbstractNode root;

    /** The parent of the current node. */
    private final AbstractNode parent;

    /** The ast node. */
    private final DetailAST detailAst;

    /** Represents text of the DetailAST. */
    private final String text;

    /** Represents index among siblings. */
    private final int indexAmongSiblings;

    /** The text attribute node. */
    private AttributeNode attributeNode;

    /**
     * Creates a new {@code ElementNode} instance.
     *
     * @param root {@code Node} root of the tree
     * @param parent {@code Node} parent of the current node
     * @param detailAst reference to {@code DetailAST}
     */
    public ElementNode(AbstractNode root, AbstractNode parent, DetailAST detailAst) {
        super(root.getTreeInfo());
        this.parent = parent;
        this.root = root;
        this.detailAst = detailAst;
        text = TokenUtil.getTokenName(detailAst.getType());
        indexAmongSiblings = parent.getChildren().size();
        createTextAttribute();
        createChildren();
    }

    /**
     * Iterates children of the current node and
     * recursively creates new Xpath-nodes.
     */
    private void createChildren() {
        DetailAST currentChild = detailAst.getFirstChild();
        while (currentChild != null) {
            final AbstractNode child = new ElementNode(root, this, currentChild);
            addChild(child);
            currentChild = currentChild.getNextSibling();
        }
    }

    /**
     * Returns attribute value. Throws {@code UnsupportedOperationException} in case,
     * when name of the attribute is not equal to 'text'.
     * @param namespace namespace
     * @param localPart actual name of the attribute
     * @return attribute value
     */
    @Override
    public String getAttributeValue(String namespace, String localPart) {
        final String result;
        if (TEXT_ATTRIBUTE_NAME.equals(localPart)) {
            if (attributeNode == null) {
                result = null;
            }
            else {
                result = attributeNode.getStringValue();
            }
        }
        else {
            result = null;
        }
        return result;
    }

    /**
     * Returns local part.
     * @return local part
     */
    @Override
    public String getLocalPart() {
        return text;
    }

    /**
     * Returns type of the node.
     * @return node kind
     */
    @Override
    public int getNodeKind() {
        return Type.ELEMENT;
    }

    /**
     * Returns parent.
     * @return parent
     */
    @Override
    public NodeInfo getParent() {
        return parent;
    }

    /**
     * Returns root.
     * @return root
     */
    @Override
    public NodeInfo getRoot() {
        return root;
    }

    /**
     * Determines axis iteration algorithm. Throws {@code UnsupportedOperationException} in case,
     * when there is no axis iterator for given axisNumber.
     *
     * @param axisNumber element from {@code AxisInfo}
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(byte axisNumber) {
        final AxisIterator result;
        switch (axisNumber) {
            case AxisInfo.ANCESTOR:
                try (AxisIterator iterator = new Navigator.AncestorEnumeration(this, false)) {
                    result = iterator;
                }
                break;
            case AxisInfo.ANCESTOR_OR_SELF:
                try (AxisIterator iterator = new Navigator.AncestorEnumeration(this, true)) {
                    result = iterator;
                }
                break;
            case AxisInfo.ATTRIBUTE:
                try (AxisIterator iterator = SingleNodeIterator.makeIterator(attributeNode)) {
                    result = iterator;
                }
                break;
            case AxisInfo.CHILD:
                if (hasChildNodes()) {
                    try (AxisIterator iterator = new ArrayIterator.OfNodes(
                            getChildren().toArray(EMPTY_ABSTRACT_NODE_ARRAY))) {
                        result = iterator;
                    }
                }
                else {
                    result = EmptyIterator.OfNodes.THE_INSTANCE;
                }
                break;
            case AxisInfo.DESCENDANT:
                if (hasChildNodes()) {
                    try (AxisIterator iterator =
                                 new Navigator.DescendantEnumeration(this, false, true)) {
                        result = iterator;
                    }
                }
                else {
                    result = EmptyIterator.OfNodes.THE_INSTANCE;
                }
                break;
            case AxisInfo.DESCENDANT_OR_SELF:
                try (AxisIterator iterator =
                             new Navigator.DescendantEnumeration(this, true, true)) {
                    result = iterator;
                }
                break;
            case AxisInfo.PARENT:
                try (AxisIterator iterator = SingleNodeIterator.makeIterator(parent)) {
                    result = iterator;
                }
                break;
            case AxisInfo.SELF:
                try (AxisIterator iterator = SingleNodeIterator.makeIterator(this)) {
                    result = iterator;
                }
                break;
            case AxisInfo.FOLLOWING_SIBLING:
                result = getFollowingSiblingsIterator();
                break;
            case AxisInfo.PRECEDING_SIBLING:
                result = getPrecedingSiblingsIterator();
                break;
            case AxisInfo.FOLLOWING:
                try (AxisIterator iterator = new FollowingEnumeration(this)) {
                    result = iterator;
                }
                break;
            case AxisInfo.PRECEDING:
                try (AxisIterator iterator = new Navigator.PrecedingEnumeration(this, true)) {
                    result = iterator;
                }
                break;
            default:
                throw throwUnsupportedOperationException();
        }
        return result;
    }

    /**
     * Returns line number.
     * @return line number
     */
    @Override
    public int getLineNumber() {
        return detailAst.getLineNo();
    }

    /**
     * Returns column number.
     * @return column number
     */
    @Override
    public int getColumnNumber() {
        return detailAst.getColumnNo();
    }

    /**
     * Getter method for token type.
     * @return token type
     */
    @Override
    public int getTokenType() {
        return detailAst.getType();
    }

    /**
     * Returns underlying node.
     * @return underlying node
     */
    @Override
    public DetailAST getUnderlyingNode() {
        return detailAst;
    }

    /**
     * Returns preceding sibling axis iterator.
     * @return iterator
     */
    private AxisIterator getPrecedingSiblingsIterator() {
        final AxisIterator result;
        if (indexAmongSiblings == 0) {
            result = EmptyIterator.OfNodes.THE_INSTANCE;
        }
        else {
            try (AxisIterator iterator = new ArrayIterator.OfNodes(
                    getPrecedingSiblings().toArray(EMPTY_ABSTRACT_NODE_ARRAY))) {
                result = iterator;
            }
        }
        return result;
    }

    /**
     * Returns following sibling axis iterator.
     * @return iterator
     */
    private AxisIterator getFollowingSiblingsIterator() {
        final AxisIterator result;
        if (indexAmongSiblings == parent.getChildren().size() - 1) {
            result = EmptyIterator.OfNodes.THE_INSTANCE;
        }
        else {
            try (AxisIterator iterator = new ArrayIterator.OfNodes(
                    getFollowingSiblings().toArray(EMPTY_ABSTRACT_NODE_ARRAY))) {
                result = iterator;
            }
        }
        return result;
    }

    /**
     * Returns following siblings of the current node.
     * @return siblings
     */
    private List<AbstractNode> getFollowingSiblings() {
        final List<AbstractNode> siblings = parent.getChildren();
        return siblings.subList(indexAmongSiblings + 1, siblings.size());
    }

    /**
     * Returns preceding siblings of the current node.
     * @return siblings
     */
    private List<AbstractNode> getPrecedingSiblings() {
        final List<AbstractNode> siblings = parent.getChildren();
        return siblings.subList(0, indexAmongSiblings);
    }

    /**
     * Checks if token type supports {@code @text} attribute,
     * extracts its value, creates {@code AttributeNode} object and returns it.
     * Value can be accessed using {@code @text} attribute.
     */
    private void createTextAttribute() {
        AttributeNode attribute = null;
        if (XpathUtil.supportsTextAttribute(detailAst)) {
            attribute = new AttributeNode(TEXT_ATTRIBUTE_NAME,
                    XpathUtil.getTextAttributeValue(detailAst));
        }
        attributeNode = attribute;
    }

    /**
     * Returns UnsupportedOperationException exception.
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }

    /**
     * Implementation of the following axis, in terms of the child and following-sibling axes.
     */
    private static final class FollowingEnumeration implements AxisIterator {
        /** Following-sibling axis iterator. */
        private AxisIterator siblingEnum;
        /** Child axis iterator. */
        private AxisIterator descendEnum;

        /**
         * Create an iterator over the "following" axis.
         * @param start the initial context node.
         */
        /* package */ FollowingEnumeration(NodeInfo start) {
            siblingEnum = start.iterateAxis(AxisInfo.FOLLOWING_SIBLING);
        }

        /**
         * Get the next item in the sequence.
         * @return the next Item. If there are no more nodes, return null.
         */
        @Override
        public NodeInfo next() {
            NodeInfo result = null;
            if (descendEnum != null) {
                result = descendEnum.next();
            }

            if (result == null) {
                descendEnum = null;
                result = siblingEnum.next();
                if (result == null) {
                    siblingEnum = null;
                }
                else {
                    descendEnum = new Navigator.DescendantEnumeration(result, true, false);
                    result = next();
                }
            }
            return result;
        }
    }

}
