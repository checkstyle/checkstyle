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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Represents DetailAST's element node of Xpath-tree.
 */
public class ElementNode extends AbstractElementNode {

    /**
     * Token types which support text attribute.
     * These token types were selected based on analysis that all others do not match required
     * criteria - text attribute of the token must be useful and help to retrieve more precise
     * results.
     * There are three types of AST tokens:
     * 1. Tokens for which the texts are equal to the name of the token. Or in other words,
     * nodes for which the following expression is always true:
     * {@snippet lang="text" :
     *     detailAst.getText().equals(TokenUtil.getTokenName(detailAst.getType()))
     * }
     * For example:
     * {@snippet lang="text" :
     *     //MODIFIERS[@text='MODIFIERS']
     *     //OBJBLOCK[@text='OBJBLOCK']
     * }
     * These tokens do not match required criteria because their texts do not carry any additional
     * information, they do not affect the xpath requests and do not help to get more accurate
     * results. The texts of these nodes are useless. No matter what code you analyze, these
     * texts are always the same.
     * In addition, they make xpath queries more complex, less readable and verbose.
     * 2. Tokens for which the texts differ from token names, but texts are always constant.
     * For example:
     * <pre>
     *     //LITERAL_VOID[@text='void']
     *     //RCURLY[@text='}']
     * </pre>
     * These tokens are not used for the same reasons as were described in the previous part.
     * 3. Tokens for which texts are not constant. The texts of these nodes are closely related
     * to a concrete class, method, variable and so on.
     * For example:
     * {@snippet lang="text" :
     *     String greeting = "HelloWorld";
     *     //STRING_LITERAL[@text='HelloWorld']
     * }
     * {@snippet lang="text" :
     *     int year = 2017;
     *     //NUM_INT[@text=2017]
     * }
     * {@snippet lang="text" :
     *     int age = 23;
     *     //NUM_INT[@text=23]
     * }
     * As you can see same {@code NUM_INT} token type can have different texts, depending on
     * context.
     * {@snippet lang="text" :
     *     public class MyClass {}
     *     //IDENT[@text='MyClass']
     * }
     * Only these tokens support text attribute because they make our xpath queries more accurate.
     * These token types are listed below.
     */
    private static final BitSet TOKEN_TYPES_WITH_TEXT_ATTRIBUTE = TokenUtil.asBitSet(
            TokenTypes.IDENT, TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
            TokenTypes.NUM_LONG, TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT,
            TokenTypes.TEXT_BLOCK_CONTENT, TokenTypes.COMMENT_CONTENT
        );

    /**
     * This regexp is used to convert new line to newline tag.
     */
    private static final Pattern NEWLINE_TO_TAG = Pattern.compile("\n");

    /**
     * This regexp is used to convert carriage return to carriage-return tag.
     */
    private static final Pattern CARRIAGE_RETURN_TO_TAG = Pattern.compile("\r");

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
     * Iterates children of the current node and recursively creates new Xpath-nodes.
     *
     * @return children list
     */
    @Override
    protected List<AbstractNode> createChildren() {
        return createChildren(getRoot(), this, detailAst.getFirstChild());
    }

    /**
     * Iterates siblings of the given node and creates new Xpath-nodes.
     *
     * @param root the root node
     * @param parent the parent node
     * @param firstChild the first DetailAST
     * @return children list
     */
    /* package */ static List<AbstractNode> createChildren(AbstractNode root, AbstractNode parent,
                                                           DetailAST firstChild) {
        DetailAST currentChild = firstChild;
        final int depth = parent.getDepth() + 1;
        final List<AbstractNode> result = new ArrayList<>();
        while (currentChild != null) {
            final int index = result.size();
            final ElementNode child = new ElementNode(root, parent, currentChild, depth, index);
            result.add(child);
            currentChild = currentChild.getNextSibling();
        }
        return result;
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

        if (supportsTextAttribute(detailAst)) {
            result = new AttributeNode(TEXT_ATTRIBUTE_NAME,
                    getTextAttributeValue(detailAst));
        }
        else {
            result = null;
        }

        return result;
    }

    /**
     * Checks, if specified node can have {@code @text} attribute.
     *
     * @param ast {@code DetailAst} element
     * @return true if element supports {@code @text} attribute, false otherwise
     */
    public static boolean supportsTextAttribute(DetailAST ast) {
        return TOKEN_TYPES_WITH_TEXT_ATTRIBUTE.get(ast.getType());
    }

    /**
     * Returns content of the text attribute of the ast element.
     *
     * @param ast {@code DetailAst} element
     * @return text attribute of the ast element
     */
    public static String getTextAttributeValue(DetailAST ast) {
        String text = ast.getText();
        if (ast.getType() == TokenTypes.STRING_LITERAL) {
            text = text.substring(1, text.length() - 1);
        }
        text = CARRIAGE_RETURN_TO_TAG.matcher(text).replaceAll("\\\\r");
        return NEWLINE_TO_TAG.matcher(text).replaceAll("\\\\n");
    }

}
