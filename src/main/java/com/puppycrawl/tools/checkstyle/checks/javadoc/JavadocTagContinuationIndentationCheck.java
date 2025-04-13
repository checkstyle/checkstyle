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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
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
 * Property {@code offset} - Specify how many spaces to use for new indentation level.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * <li>
 * {@code tag.continuation.indent}
 * </li>
 * </ul>
 *
 * @since 6.0
 *
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
        return new int[] {JavadocTokenTypes.HTML_TAG, JavadocTokenTypes.DESCRIPTION};

    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isBlockDescription(ast) && !isInlineDescription(ast)) {
            final List<DetailNode> textNodes = getAllNewlineNodes(ast);
            for (DetailNode newlineNode : textNodes) {
                final DetailNode textNode = JavadocUtil.getNextSibling(newlineNode);
                if (textNode.getType() != JavadocTokenTypes.NEWLINE && isViolation(textNode)) {
                    log(textNode.getLineNumber(), MSG_KEY, offset);
                }
            }
        }
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
                final DetailNode nextNode = JavadocUtil.getNextSibling(textNode);
                if (nextNode != null && nextNode.getType() != JavadocTokenTypes.NEWLINE) {
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
     * Finds and collects all NEWLINE nodes inside DESCRIPTION node.
     *
     * @param descriptionNode DESCRIPTION node.
     * @return List with NEWLINE nodes.
     */
    private static List<DetailNode> getAllNewlineNodes(DetailNode descriptionNode) {
        final List<DetailNode> textNodes = new ArrayList<>();
        DetailNode node = JavadocUtil.getFirstChild(descriptionNode);
        while (JavadocUtil.getNextSibling(node) != null) {
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                final DetailNode descriptionNodeChild = JavadocUtil.getFirstChild(node);
                textNodes.addAll(getAllNewlineNodes(descriptionNodeChild));
            }
            else if (node.getType() == JavadocTokenTypes.HTML_ELEMENT_START
                || node.getType() == JavadocTokenTypes.ATTRIBUTE) {
                textNodes.addAll(getAllNewlineNodes(node));
            }
            if (node.getType() == JavadocTokenTypes.LEADING_ASTERISK) {
                textNodes.add(node);
            }
            node = JavadocUtil.getNextSibling(node);
        }
        return textNodes;
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
            if (currentNode.getType() == JavadocTokenTypes.JAVADOC_TAG) {
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
            if (currentNode.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                isInline = true;
                break;
            }
            currentNode = currentNode.getParent();
        }
        return isInline;
    }

}
