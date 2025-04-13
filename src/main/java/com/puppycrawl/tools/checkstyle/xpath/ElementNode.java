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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import com.puppycrawl.tools.checkstyle.utils.XpathUtil;

/**
 * Represents DetailAST's element node of Xpath-tree.
 */
public class ElementNode extends AbstractElementNode {

    /** The ast node. */
    private final DetailAST detailAst;

    /**
     * Creates a new {@code ElementNode} instance.
     *
     * @param root {@code Node} root of the tree
     * @param parent {@code Node} parent of the current node
     * @param detailAst reference to {@code DetailAST}
     * @param depth the current node depth in the hierarchy
     * @param indexAmongSiblings the current node index among the parent children nodes
     */
    public ElementNode(AbstractNode root, AbstractNode parent, DetailAST detailAst,
                       int depth, int indexAmongSiblings) {
        super(root, parent, depth, indexAmongSiblings);
        this.detailAst = detailAst;
    }

    /**
     * Iterates children of the current node and
     * recursively creates new Xpath-nodes.
     *
     * @return children list
     */
    @Override
    protected List<AbstractNode> createChildren() {
        return XpathUtil.createChildren(getRoot(), this, detailAst.getFirstChild());
    }

    /**
     * Determine whether the node has any children.
     *
     * @return {@code true} is the node has any children.
     */
    @Override
    public boolean hasChildNodes() {
        return detailAst.hasChildren();
    }

    /**
     * Returns local part.
     *
     * @return local part
     */
    @Override
    public String getLocalPart() {
        return TokenUtil.getTokenName(detailAst.getType());
    }

    /**
     * Returns line number.
     *
     * @return line number
     */
    @Override
    public int getLineNumber() {
        return detailAst.getLineNo();
    }

    /**
     * Returns column number.
     *
     * @return column number
     */
    @Override
    public int getColumnNumber() {
        return detailAst.getColumnNo();
    }

    /**
     * Getter method for token type.
     *
     * @return token type
     */
    @Override
    public int getTokenType() {
        return detailAst.getType();
    }

    /**
     * Returns underlying node.
     *
     * @return underlying node
     */
    @Override
    public DetailAST getUnderlyingNode() {
        return detailAst;
    }

    @Override
    protected AttributeNode createAttributeNode() {
        final AttributeNode result;

        if (XpathUtil.supportsTextAttribute(detailAst)) {
            result = new AttributeNode(TEXT_ATTRIBUTE_NAME,
                XpathUtil.getTextAttributeValue(detailAst));
        }
        else {
            result = null;
        }

        return result;
    }
}
