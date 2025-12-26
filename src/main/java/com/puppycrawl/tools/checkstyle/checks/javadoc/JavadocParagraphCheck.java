///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Set;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks the Javadoc paragraph.
 * </div>
 *
 * <p>
 * Checks that:
 * </p>
 * <ul>
 * <li>There is one blank line between each of two paragraphs.</li>
 * <li>Each paragraph but the first has &lt;p&gt; immediately
 * before the first word, with no space after.</li>
 * <li>The outer most paragraph tags should not precede
 * <a href="https://www.w3schools.com/html/html_blocks.asp">HTML block-tag</a>.
 * Nested paragraph tags are allowed to do that. This check only supports following block-tags:
 * &lt;address&gt;,&lt;blockquote&gt;
 * ,&lt;div&gt;,&lt;dl&gt;
 * ,&lt;h1&gt;,&lt;h2&gt;,&lt;h3&gt;,&lt;h4&gt;,&lt;h5&gt;,&lt;h6&gt;,&lt;hr&gt;
 * ,&lt;ol&gt;,&lt;p&gt;,&lt;pre&gt;
 * ,&lt;table&gt;,&lt;ul&gt;.
 * </li>
 * </ul>
 *
 * <p><b>ATTENTION:</b></p>
 *
 * <p>This Check ignores HTML comments.</p>
 *
 * <p>The Check ignores all the nested paragraph tags,
 * it will not give any kind of violation if the paragraph tag is nested.</p>
 *
 * @since 6.0
 */
@StatelessCheck
public class JavadocParagraphCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_AFTER = "javadoc.paragraph.tag.after";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_BEFORE = "javadoc.paragraph.line.before";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REDUNDANT_PARAGRAPH = "javadoc.paragraph.redundant.paragraph";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISPLACED_TAG = "javadoc.paragraph.misplaced.tag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRECEDED_BLOCK_TAG = "javadoc.paragraph.preceded.block.tag";

    /**
     * Constant for the paragraph tag name.
     */
    private static final String PARAGRAPH_TAG = "p";

    /**
     * Set of block tags supported by this check.
     */
    private static final Set<String> BLOCK_TAGS =
            Set.of("address", "blockquote", "div", "dl",
                   "h1", "h2", "h3", "h4", "h5", "h6", "hr",
                   "ol", PARAGRAPH_TAG, "pre", "table", "ul");

    /**
     * Control whether the &lt;p&gt; tag should be placed immediately before the first word.
     */
    private boolean allowNewlineParagraph = true;

    /**
     * Setter to control whether the &lt;p&gt; tag should be placed
     * immediately before the first word.
     *
     * @param value value to set.
     * @since 6.9
     */
    public void setAllowNewlineParagraph(boolean value) {
        allowNewlineParagraph = value;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.NEWLINE,
            JavadocCommentsTokenTypes.HTML_ELEMENT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocCommentsTokenTypes.NEWLINE && isEmptyLine(ast)) {
            checkEmptyLine(ast);
        }
        else if (JavadocUtil.isTag(ast, PARAGRAPH_TAG)) {
            checkParagraphTag(ast);
        }
    }

    /**
     * Determines whether or not the next line after empty line has paragraph tag in the beginning.
     *
     * @param newline NEWLINE node.
     */
    private void checkEmptyLine(DetailNode newline) {
        final DetailNode nearestToken = getNearestNode(newline);
        if (nearestToken != null && nearestToken.getType() == JavadocCommentsTokenTypes.TEXT
                && !CommonUtil.isBlank(nearestToken.getText())) {
            log(newline.getLineNumber(), newline.getColumnNumber(), MSG_TAG_AFTER);
        }
    }

    /**
     * Determines whether or not the line with paragraph tag has previous empty line.
     *
     * @param tag html tag.
     */
    private void checkParagraphTag(DetailNode tag) {
        if (!isNestedParagraph(tag)) {
            final DetailNode newLine = getNearestEmptyLine(tag);
            if (isFirstParagraph(tag)) {
                log(tag.getLineNumber(), tag.getColumnNumber(), MSG_REDUNDANT_PARAGRAPH);
            }
            else if (newLine == null || tag.getLineNumber() - newLine.getLineNumber() != 1) {
                log(tag.getLineNumber(), tag.getColumnNumber(), MSG_LINE_BEFORE);
            }

            final String blockTagName = findFollowedBlockTagName(tag);
            if (blockTagName != null) {
                log(tag.getLineNumber(), tag.getColumnNumber(),
                        MSG_PRECEDED_BLOCK_TAG, blockTagName);
            }

            if (!allowNewlineParagraph && isImmediatelyFollowedByNewLine(tag)) {
                log(tag.getLineNumber(), tag.getColumnNumber(), MSG_MISPLACED_TAG);
            }
            if (isImmediatelyFollowedByText(tag)) {
                log(tag.getLineNumber(), tag.getColumnNumber(), MSG_MISPLACED_TAG);
            }
        }
    }

    /**
     * Determines whether the paragraph tag is nested.
     *
     * @param tag html tag.
     * @return true, if the paragraph tag is nested.
     */
    private static boolean isNestedParagraph(DetailNode tag) {
        var nested = false;
        DetailNode parent = tag.getParent();

        while (parent != null) {
            if (parent.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                nested = true;
                break;
            }
            parent = parent.getParent();
        }

        return nested;
    }

    /**
     * Determines whether or not the paragraph tag is followed by block tag.
     *
     * @param tag html tag.
     * @return block tag if the paragraph tag is followed by block tag or null if not found.
     */
    @Nullable
    private static String findFollowedBlockTagName(DetailNode tag) {
        final DetailNode htmlElement = findFirstHtmlElementAfter(tag);
        String blockTagName = null;

        if (htmlElement != null) {
            blockTagName = getHtmlElementName(htmlElement);
        }

        return blockTagName;
    }

    /**
     * Finds and returns first html element after the tag.
     *
     * @param tag html tag.
     * @return first html element after the paragraph tag or null if not found.
     */
    @Nullable
    private static DetailNode findFirstHtmlElementAfter(DetailNode tag) {
        DetailNode htmlElement = getNextSibling(tag);

        while (htmlElement != null
                && htmlElement.getType() != JavadocCommentsTokenTypes.HTML_ELEMENT) {
            if (htmlElement.getType() == JavadocCommentsTokenTypes.HTML_CONTENT) {
                htmlElement = htmlElement.getFirstChild();
            }
            else if (htmlElement.getType() == JavadocCommentsTokenTypes.TEXT
                    && !CommonUtil.isBlank(htmlElement.getText())) {
                htmlElement = null;
                break;
            }
            else {
                htmlElement = htmlElement.getNextSibling();
            }
        }
        if (htmlElement != null
                && JavadocUtil.findFirstToken(htmlElement,
                        JavadocCommentsTokenTypes.HTML_TAG_END) == null) {
            htmlElement = null;
        }

        return htmlElement;
    }

    /**
     * Finds and returns first block-level html element name.
     *
     * @param htmlElement block-level html tag.
     * @return block-level html element name or null if not found.
     */
    @Nullable
    private static String getHtmlElementName(DetailNode htmlElement) {
        final DetailNode htmlTagStart = htmlElement.getFirstChild();
        final DetailNode htmlTagName =
                JavadocUtil.findFirstToken(htmlTagStart, JavadocCommentsTokenTypes.TAG_NAME);
        String blockTagName = null;
        if (BLOCK_TAGS.contains(htmlTagName.getText())) {
            blockTagName = htmlTagName.getText();
        }

        return blockTagName;
    }

    /**
     * Returns nearest node.
     *
     * @param node DetailNode node.
     * @return nearest node.
     */
    private static DetailNode getNearestNode(DetailNode node) {
        DetailNode currentNode = node;
        while (currentNode != null
                && (currentNode.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK
                    || currentNode.getType() == JavadocCommentsTokenTypes.NEWLINE)) {
            currentNode = currentNode.getNextSibling();
        }
        if (currentNode != null
                && currentNode.getType() == JavadocCommentsTokenTypes.HTML_CONTENT) {
            currentNode = currentNode.getFirstChild();
        }
        return currentNode;
    }

    /**
     * Determines whether or not the line is empty line.
     *
     * @param newLine NEWLINE node.
     * @return true, if line is empty line.
     */
    private static boolean isEmptyLine(DetailNode newLine) {
        var result = false;
        DetailNode previousSibling = newLine.getPreviousSibling();
        if (previousSibling != null && (previousSibling.getParent().getType()
                == JavadocCommentsTokenTypes.JAVADOC_CONTENT
                || insideNonTightHtml(previousSibling))) {
            if (previousSibling.getType() == JavadocCommentsTokenTypes.TEXT
                    && CommonUtil.isBlank(previousSibling.getText())) {
                previousSibling = previousSibling.getPreviousSibling();
            }
            result = previousSibling != null
                    && previousSibling.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK;
        }
        return result;
    }

    /**
     * Checks whether the given node is inside a non-tight HTML element.
     *
     * @param previousSibling the node to check
     * @return true if inside non-tight HTML, false otherwise
     */
    private static boolean insideNonTightHtml(DetailNode previousSibling) {
        final DetailNode parent = previousSibling.getParent();
        DetailNode htmlElement = parent;
        if (parent.getType() == JavadocCommentsTokenTypes.HTML_CONTENT) {
            htmlElement = parent.getParent();
        }
        return htmlElement.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT
                && JavadocUtil.findFirstToken(htmlElement,
                    JavadocCommentsTokenTypes.HTML_TAG_END) == null;
    }

    /**
     * Determines whether or not the line with paragraph tag is first line in javadoc.
     *
     * @param paragraphTag paragraph tag.
     * @return true, if line with paragraph tag is first line in javadoc.
     */
    private static boolean isFirstParagraph(DetailNode paragraphTag) {
        var result = true;
        DetailNode previousNode = paragraphTag.getPreviousSibling();
        while (previousNode != null) {
            if (previousNode.getType() == JavadocCommentsTokenTypes.TEXT
                    && !CommonUtil.isBlank(previousNode.getText())
                || previousNode.getType() != JavadocCommentsTokenTypes.LEADING_ASTERISK
                    && previousNode.getType() != JavadocCommentsTokenTypes.NEWLINE
                    && previousNode.getType() != JavadocCommentsTokenTypes.TEXT) {
                result = false;
                break;
            }
            previousNode = previousNode.getPreviousSibling();
        }
        return result;
    }

    /**
     * Finds and returns nearest empty line in javadoc.
     *
     * @param node DetailNode node.
     * @return Some nearest empty line in javadoc.
     */
    private static DetailNode getNearestEmptyLine(DetailNode node) {
        DetailNode newLine = node;
        while (newLine != null) {
            final DetailNode previousSibling = newLine.getPreviousSibling();
            if (newLine.getType() == JavadocCommentsTokenTypes.NEWLINE && isEmptyLine(newLine)) {
                break;
            }
            newLine = previousSibling;
        }
        return newLine;
    }

    /**
     * Tests whether the paragraph tag is immediately followed by the text.
     *
     * @param tag html tag.
     * @return true, if the paragraph tag is immediately followed by the text.
     */
    private static boolean isImmediatelyFollowedByText(DetailNode tag) {
        final DetailNode nextSibling = getNextSibling(tag);

        return nextSibling == null || nextSibling.getText().startsWith(" ");
    }

    /**
     * Tests whether the paragraph tag is immediately followed by the new line.
     *
     * @param tag html tag.
     * @return true, if the paragraph tag is immediately followed by the new line.
     */
    private static boolean isImmediatelyFollowedByNewLine(DetailNode tag) {
        final DetailNode sibling = getNextSibling(tag);
        return sibling != null && sibling.getType() == JavadocCommentsTokenTypes.NEWLINE;
    }

    /**
     * Custom getNextSibling method to handle different types of paragraph tag.
     * It works for both {@code <p>} and {@code <p></p>} tags.
     *
     * @param tag HTML_ELEMENT tag.
     * @return next sibling of the tag.
     */
    private static DetailNode getNextSibling(DetailNode tag) {
        DetailNode nextSibling;
        final DetailNode paragraphStartTagToken = tag.getFirstChild();
        final DetailNode nextNode = paragraphStartTagToken.getNextSibling();

        if (nextNode == null) {
            nextSibling = tag.getNextSibling();
        }
        else if (nextNode.getType() == JavadocCommentsTokenTypes.HTML_CONTENT) {
            nextSibling = nextNode.getFirstChild();
        }
        else {
            nextSibling = nextNode;
        }

        if (nextSibling != null
                && nextSibling.getType() == JavadocCommentsTokenTypes.HTML_COMMENT) {
            nextSibling = nextSibling.getNextSibling();
        }
        return nextSibling;
    }
}
