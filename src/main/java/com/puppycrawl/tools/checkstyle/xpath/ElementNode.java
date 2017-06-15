////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;
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
 * @author Timur Tibeyev
 */
public class ElementNode extends AbstractNode {
    /** String literal for text attribute. */
    private static final String TEXT_ATTRIBUTE_NAME = "text";

    /** The root node. */
    private final AbstractNode root;

    /** The parent of the current node. */
    private final AbstractNode parent;

    /** The ast node. */
    private final DetailAST detailAst;

    /** Represents text of the DetailAST. */
    private final String text;

    /** The attributes. */
    private AbstractNode[] attributes;

    /** Represents value of TokenTypes#IDENT. */
    private String ident;

    /**
     * Creates a new {@code ElementNode} instance.
     *
     * @param root {@code Node} root of the tree
     * @param parent {@code Node} parent of the current node
     * @param detailAst reference to {@code DetailAST}
     */
    public ElementNode(AbstractNode root, AbstractNode parent, DetailAST detailAst) {
        this.parent = parent;
        this.root = root;
        this.detailAst = detailAst;
        setIdent();
        createChildren();
        text = TokenUtils.getTokenName(detailAst.getType());
    }

    /**
     * Iterates children of the current node and
     * recursively creates new Xpath-nodes.
     */
    private void createChildren() {
        DetailAST currentChild = detailAst.getFirstChild();
        while (currentChild != null) {
            if (currentChild.getType() != TokenTypes.IDENT) {
                final ElementNode child = new ElementNode(root, this, currentChild);
                addChild(child);
            }
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
        if (TEXT_ATTRIBUTE_NAME.equals(localPart)) {
            return ident;
        }
        else {
            throw throwUnsupportedOperationException();
        }
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
                result = new Navigator.AncestorEnumeration(this, false);
                break;
            case AxisInfo.ANCESTOR_OR_SELF:
                result = new Navigator.AncestorEnumeration(this, true);
                break;
            case AxisInfo.ATTRIBUTE:
                if (attributes == null) {
                    result = EmptyIterator.OfNodes.THE_INSTANCE;
                }
                else {
                    result = new ArrayIterator.OfNodes(attributes);
                }
                break;
            case AxisInfo.CHILD:
                if (hasChildNodes()) {
                    result = new ArrayIterator.OfNodes(
                            getChildren().toArray(new AbstractNode[getChildren().size()]));
                }
                else {
                    result = EmptyIterator.OfNodes.THE_INSTANCE;
                }
                break;
            case AxisInfo.DESCENDANT:
                if (hasChildNodes()) {
                    result = new Navigator.DescendantEnumeration(this, false, true);
                }
                else {
                    result = EmptyIterator.OfNodes.THE_INSTANCE;
                }
                break;
            case AxisInfo.DESCENDANT_OR_SELF:
                result = new Navigator.DescendantEnumeration(this, true, true);
                break;
            case AxisInfo.PARENT:
                result = SingleNodeIterator.makeIterator(parent);
                break;
            case AxisInfo.SELF:
                result = SingleNodeIterator.makeIterator(this);
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
     * Finds child element with {@link TokenTypes#IDENT}, extracts its value and stores it.
     * Value can be accessed using {@code @text} attribute. Now {@code @text} attribute is only
     * supported attribute.
     */
    private void setIdent() {
        final DetailAST identAst = detailAst.findFirstToken(TokenTypes.IDENT);
        if (identAst != null) {
            ident = identAst.getText();
            attributes = new AbstractNode[1];
            attributes[0] = new AttributeNode(TEXT_ATTRIBUTE_NAME, ident);
        }
    }

    /**
     * Returns UnsupportedOperationException exception.
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }
}
