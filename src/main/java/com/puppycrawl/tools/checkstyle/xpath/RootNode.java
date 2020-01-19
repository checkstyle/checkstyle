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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.GenericTreeInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.Type;

/**
 * Represents root node of Xpath-tree.
 *
 */
public class RootNode extends AbstractNode {

    /** Name of the root element. */
    private static final String ROOT_NAME = "ROOT";

    /** Constant for optimization. */
    private static final AbstractNode[] EMPTY_ABSTRACT_NODE_ARRAY = new AbstractNode[0];

    /** The ast node. */
    private final DetailAST detailAst;

    /**
     * Creates a new {@code RootNode} instance.
     *
     * @param detailAst reference to {@code DetailAST}
     */
    public RootNode(DetailAST detailAst) {
        super(new GenericTreeInfo(Configuration.newConfiguration()));
        this.detailAst = detailAst;

        createChildren();
    }

    /**
     * Iterates siblings of the current node and
     * recursively creates new Xpath-nodes.
     */
    private void createChildren() {
        DetailAST currentChild = detailAst;
        while (currentChild != null) {
            final ElementNode child = new ElementNode(this, this, currentChild);
            addChild(child);
            currentChild = currentChild.getNextSibling();
        }
    }

    /**
     * Returns attribute value. Throws {@code UnsupportedOperationException} because root node
     * has no attributes.
     * @param namespace namespace
     * @param localPart actual name of the attribute
     * @return attribute value
     */
    @Override
    public String getAttributeValue(String namespace, String localPart) {
        throw throwUnsupportedOperationException();
    }

    /**
     * Returns local part.
     * @return local part
     */
    @Override
    public String getLocalPart() {
        return ROOT_NAME;
    }

    /**
     * Returns type of the node.
     * @return node kind
     */
    @Override
    public int getNodeKind() {
        return Type.DOCUMENT;
    }

    /**
     * Returns parent.
     * @return parent
     */
    @Override
    public NodeInfo getParent() {
        return null;
    }

    /**
     * Returns root of the tree.
     * @return root of the tree
     */
    @Override
    public NodeInfo getRoot() {
        return this;
    }

    /**
     * Determines axis iteration algorithm. Throws {@code UnsupportedOperationException} in case,
     * when there is no axis iterator for given axisNumber.
     * @param axisNumber element from {@code AxisInfo}
     * @return {@code AxisIterator} object
     */
    @Override
    public AxisIterator iterateAxis(byte axisNumber) {
        final AxisIterator result;
        switch (axisNumber) {
            case AxisInfo.ANCESTOR:
            case AxisInfo.ATTRIBUTE:
            case AxisInfo.PARENT:
            case AxisInfo.FOLLOWING:
            case AxisInfo.FOLLOWING_SIBLING:
            case AxisInfo.PRECEDING:
            case AxisInfo.PRECEDING_SIBLING:
                result = EmptyIterator.OfNodes.THE_INSTANCE;
                break;
            case AxisInfo.ANCESTOR_OR_SELF:
            case AxisInfo.SELF:
                try (AxisIterator iterator = SingleNodeIterator.makeIterator(this)) {
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
        return TokenTypes.EOF;
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
     * Returns UnsupportedOperationException exception.
     * @return UnsupportedOperationException exception
     */
    private static UnsupportedOperationException throwUnsupportedOperationException() {
        return new UnsupportedOperationException("Operation is not supported");
    }

}
