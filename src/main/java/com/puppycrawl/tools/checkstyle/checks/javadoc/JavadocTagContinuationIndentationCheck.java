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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks the indentation of the continuation lines in block tags. That is whether the continued
 * description of at clauses should be indented or not. If the text is not properly indented it
 * throws a violation. A continuation line is when the description starts/spans past the line with
 * the tag. Default indentation required is at least 4, but this can be changed with the help of
 * properties below.
 * </div>
 * <ul>
 * <li>
 * Notes:
 * This check does not validate the indentation of lines inside {@code pre} tags.
 * </li>
 * </ul>
 *
 * @since 6.0
 */
@StatelessCheck
public class JavadocTagContinuationIndentationCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "tag.continuation.indent";

    /** Default tag continuation indentation. */
    private static final int DEFAULT_INDENTATION = 4;

    /**
     * Constant for the pre tag name.
     */
    private static final String PRE_TAG = "pre";

    /**
     * Specify how many spaces to use for new indentation level.
     */
    private int offset = DEFAULT_INDENTATION;

    /**
     * Setter to specify how many spaces to use for new indentation level.
     *
     * @param offset custom value.
     * @since 6.0
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.HTML_ELEMENT,
            JavadocCommentsTokenTypes.DESCRIPTION,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isBlockDescription(ast) && !isInlineDescription(ast)) {
            final List<DetailNode> textNodes = getTargetedTextNodes(ast);
            for (DetailNode textNode : textNodes) {
                if (isViolation(textNode)) {
                    log(textNode.getLineNumber(), MSG_KEY, offset);
                }
            }
        }
    }

    /**
     * Returns all targeted text nodes from the given AST node.
     * This method decides whether to process the node as a description node
     * or as an HTML element node and delegates to the appropriate helper method.
     *
     * @param ast the AST node to process
     * @return list of targeted text nodes
     */
    private static List<DetailNode> getTargetedTextNodes(DetailNode ast) {
        final List<DetailNode> textNodes;
        if (ast.getType() == JavadocCommentsTokenTypes.DESCRIPTION) {
            textNodes = getTargetedTextNodesInsideDescription(ast);
        }
        else {
            textNodes = getTargetedTextNodesInsideHtmlElement(ast);
        }
        return textNodes;
    }

    /**
     * Returns all targeted text nodes within an HTML element subtree.
     *
     * @param ast the HTML element AST node
     * @return list of targeted text nodes inside the HTML element
     */
    private static List<DetailNode> getTargetedTextNodesInsideHtmlElement(DetailNode ast) {
        final List<DetailNode> textNodes = new ArrayList<>();

        if (!JavadocUtil.isTag(ast, PRE_TAG) && !isInsidePreTag(ast)) {
            DetailNode node = ast.getFirstChild();
            while (node != null) {
                if (node.getType() == JavadocCommentsTokenTypes.HTML_CONTENT) {
                    // HTML_CONTENT contain text nodes only, so it can be treated as
                    // DESCRIPTION node
                    textNodes.addAll(getTargetedTextNodesInsideDescription(node));
                }
                else if (subtreeContainsAttributeValue(node)) {
                    textNodes.addAll(getTargetedTextNodesInsideHtmlElement(node));
                }
                else if (isTargetTextNode(node)) {
                    textNodes.add(node);
                }
                node = node.getNextSibling();
            }
        }
        return textNodes;
    }

    /**
     * Checks whether the given subtree node represents part of an HTML tag
     * structure that may contain attribute values.
     *
     * @param node the AST node to check
     * @return true if the subtree may contain attribute values, false otherwise
     */
    private static boolean subtreeContainsAttributeValue(DetailNode node) {
        return node.getType() == JavadocCommentsTokenTypes.HTML_TAG_START
            || node.getType() == JavadocCommentsTokenTypes.HTML_ATTRIBUTES
            || node.getType() == JavadocCommentsTokenTypes.HTML_ATTRIBUTE;
    }

    /**
     * Returns all targeted text nodes inside a description node.
     *
     * @param descriptionNode the DESCRIPTION node to process
     * @return list of targeted text nodes inside the description node
     */
    private static List<DetailNode> getTargetedTextNodesInsideDescription(
            DetailNode descriptionNode) {
        final List<DetailNode> textNodes = new ArrayList<>();
        DetailNode node = descriptionNode.getFirstChild();
        final DetailNode previousSibling = descriptionNode.getPreviousSibling();

        // special case if the text node is previous sibling of the description node
        if (isTargetTextNode(previousSibling)) {
            textNodes.add(previousSibling);
        }

        // special case for the first child, because leading asterisk
        // will be previous sibling of the parent (description node) not the node itself
        if (descriptionNode.getPreviousSibling().getType()
                == JavadocCommentsTokenTypes.LEADING_ASTERISK) {
            textNodes.add(node);
        }

        while (node != null) {
            if (isTargetTextNode(node)) {
                textNodes.add(node);
            }
            node = node.getNextSibling();
        }

        return textNodes;
    }

    /**
     * Determines whether the given node is a targeted node.
     *
     * @param node the AST node to check
     * @return true if the node is a targeted node, false otherwise
     */
    private static boolean isTargetTextNode(DetailNode node) {
        final DetailNode previousSibling = node.getPreviousSibling();

        return previousSibling != null
            && isTextOrAttributeValueNode(node)
            && !isBeforePreTag(node)
            && previousSibling.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Checks if a node is located before a {@code pre} tag.
     *
     * @param node the node to check
     * @return true if the node is before a pre tag, false otherwise
     */
    private static boolean isBeforePreTag(DetailNode node) {
        final DetailNode nextSibling = node.getNextSibling();
        final boolean isBeforePreTag;
        if (nextSibling != null
                && nextSibling.getType() == JavadocCommentsTokenTypes.DESCRIPTION) {
            isBeforePreTag = JavadocUtil.isTag(nextSibling.getFirstChild(), PRE_TAG);
        }
        else if (nextSibling != null) {
            isBeforePreTag = JavadocUtil.isTag(nextSibling, PRE_TAG);
        }
        else {
            isBeforePreTag = false;
        }
        return isBeforePreTag;
    }

    /**
     * Checks if a node is inside a {@code pre} tag.
     *
     * @param node the node to check
     * @return true if the node is inside a pre tag, false otherwise
     */
    private static boolean isInsidePreTag(DetailNode node) {
        final DetailNode htmlElementParent = node.getParent().getParent();
        return JavadocUtil.isTag(htmlElementParent, PRE_TAG);
    }

    /**
     * Checks whether the given node is either a TEXT node or an ATTRIBUTE_VALUE node.
     *
     * @param node the AST node to check
     * @return true if the node is a TEXT or ATTRIBUTE_VALUE node, false otherwise
     */
    private static boolean isTextOrAttributeValueNode(DetailNode node) {
        return node.getType() == JavadocCommentsTokenTypes.TEXT
            || node.getType() == JavadocCommentsTokenTypes.ATTRIBUTE_VALUE;
    }

    /**
     * Checks if a text node meets the criteria for a violation.
     * If the text is shorter than {@code offset} characters, then a violation is
     * detected if the text is not blank or the next node is not a newline.
     * If the text is longer than {@code offset} characters, then a violation is
     * detected if any of the first {@code offset} characters are not blank.
     *
     * @param textNode the node to check.
     * @return true if the node has a violation.
     */
    private boolean isViolation(DetailNode textNode) {
        boolean result = false;
        final String text = textNode.getText();
        if (text.length() <= offset) {
            if (CommonUtil.isBlank(text)) {
                final DetailNode nextNode = textNode.getNextSibling();
                if (nextNode.getType() != JavadocCommentsTokenTypes.NEWLINE) {
                    // text is blank but line hasn't ended yet
                    result = true;
                }
            }
            else {
                // text is not blank
                result = true;
            }
        }
        else if (!CommonUtil.isBlank(text.substring(1, offset + 1))) {
            // first offset number of characters are not blank
            result = true;
        }
        return result;
    }

    /**
     * Checks if the given description node is part of a block Javadoc tag.
     *
     * @param description the node to check
     * @return {@code true} if the node is inside a block tag, {@code false} otherwise
     */
    private static boolean isBlockDescription(DetailNode description) {
        boolean isBlock = false;
        DetailNode currentNode = description;
        while (currentNode != null) {
            if (currentNode.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                isBlock = true;
                break;
            }
            currentNode = currentNode.getParent();
        }
        return isBlock;
    }

    /**
     * Checks, if description node is a description of in-line tag.
     *
     * @param description DESCRIPTION node.
     * @return true, if description node is a description of in-line tag.
     */
    private static boolean isInlineDescription(DetailNode description) {
        boolean isInline = false;
        DetailNode currentNode = description;
        while (currentNode != null) {
            if (currentNode.getType() == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG) {
                isInline = true;
                break;
            }
            currentNode = currentNode.getParent();
        }
        return isInline;
    }
}
