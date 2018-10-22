////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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

    /** String literal for text attribute. */
    private static final String VALUE_ATTRIBUTE_NAME = "value";

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

    /** The attributes map. */
    private final Map<String, AttributeNode> attributes = new HashMap<>();

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
        createAttributes();
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
        final AttributeNode attribute = attributes.get(localPart);
        final String result;
        if (attribute == null) {
            result = null;
        }
        else {
            result = attribute.getStringValue();
        }
        return result;
    }

    /**
     * Returns local part.
     * @return local part
     */
    // -@cs[SimpleAccessorNameNotation] Overrides method from the base class.
    // Issue: https://github.com/sevntu-checkstyle/sevntu.checkstyle/issues/166
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
     * Returns string value.
     * @return string value
     */
    // -@cs[SimpleAccessorNameNotation] Overrides method from the base class.
    // Issue: https://github.com/sevntu-checkstyle/sevntu.checkstyle/issues/166
    @Override
    public String getStringValue() {
        return text;
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
                try (AxisIterator iterator = new ArrayIterator.OfNodes(attributes.values().toArray(
                        new AttributeNode[0]
                ))) {
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
    // -@cs[SimpleAccessorNameNotation] Overrides method from the base class.
    // Issue: https://github.com/sevntu-checkstyle/sevntu.checkstyle/issues/166
    @Override
    public DetailAST getUnderlyingNode() {
        return detailAst;
    }

    /**
     * Finds child element with {@link TokenTypes#IDENT},
     * extracts its value, creates {@code AttributeNode} object and returns it.
     * Value can be accessed using {@code @text} attribute.
     *
     * @return attribute object
     */
    private AttributeNode createTextAttribute() {
        AttributeNode attribute = null;
        if (detailAst.getChildCount(TokenTypes.IDENT) == 1) {
            final String ident = detailAst.findFirstToken(TokenTypes.IDENT).getText();
            attribute = new AttributeNode(TEXT_ATTRIBUTE_NAME, ident);
        }
        return attribute;
    }

    /**
     * Extracts value of the current node using {@link DetailAST#getText()}
     * method if exists, creates {@code AttributeNode} object and returns it.
     * Value can be accessed using {@code @value} attribute.
     *
     * @return attribute object
     */
    private AttributeNode createValueAttribute() {
        AttributeNode attribute = null;
        if (XpathUtil.supportsValueAttribute(detailAst)) {
            attribute = new AttributeNode(VALUE_ATTRIBUTE_NAME, XpathUtil.getValue(detailAst));
        }
        return attribute;
    }

    /**
     * Fills map of attributes.
     */
    private void createAttributes() {
        attributes.put(TEXT_ATTRIBUTE_NAME, createTextAttribute());
        attributes.put(VALUE_ATTRIBUTE_NAME, createValueAttribute());
    }

    /**
     * Returns UnsupportedOperationException exception.
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }

}
