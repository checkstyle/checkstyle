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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc inline tags {@code {@code ...}} and {@code {@snippet ...}}
 * are preferred over HTML tags {@code <code>} and {@code <pre>}.
 * </div>
 *
 * <p>
 * This check enforces using either {@code {@code ...}} or {@code {@snippet ...}} inline tags
 * instead of single-line {@code <code>} and {@code <pre>} HTML tags, and using
 * {@code {@snippet ...}} inline tags instead of multi-line {@code <code>} and {@code <pre>}
 * HTML tags.
 * </p>
 *
 * <p>
 * Per <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html">
 * OpenJDK Style Guidelines v6</a>,
 * Javadoc inline tags should be preferred over their HTML equivalents.
 * </p>
 *
 * <p>
 * To suppress violation for snippet inline tag:
 * </p>
 *
 * <div class="wrapper"><pre class="prettyprint"><code class="language-xml">
 *     &lt;module name="SuppressionSingleFilter"&gt;
 *       &lt;property name="checks" value="PreferCodeOrSnippetJavadocInlineTag"/&gt;
 *       &lt;property name="files" value="file-name"/&gt;
 *       &lt;property name="message" value="Use snippet inline tag instead of.*"/&gt;
 *     &lt;/module&gt;
 * </code></pre></div>
 *
 * <b>Not Flagged :</b>
 * <ul>
 *     <li>Tags which have unbalanced curly braces</li>
 *     <li>Tags which have content that starts with star.</li>
 *     <li>Tags which are inside other tags</li>
 * </ul>
 *
 * @since 13.11.0
 */
@StatelessCheck
public class PreferCodeOrSnippetJavadocInlineTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SINGLE_LINE = "prefer.code.javadoc.singleline.tag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MULTI_LINE = "prefer.code.javadoc.multiline.tag";

    /**
     * Creates a new {@code PreferCodeOrSnippetJavadocInlineTagCheck} instance.
     */
    public PreferCodeOrSnippetJavadocInlineTagCheck() {
        // no code by default
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.HTML_ELEMENT,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode node) {
        if (isCodeOrPreTag(node) && !isNested(node)) {
            final List<DetailNode> textNodes = collectTextNodes(node);

            if (isSingleLineTag(node) && containsBalancedBraces(textNodes)) {
                log(node.getLineNumber(), MSG_KEY_SINGLE_LINE, getHtmlTagName(node));
            }
            else if (isConvertableInInlineTag(textNodes)) {
                log(node.getLineNumber(), MSG_KEY_MULTI_LINE, getHtmlTagName(node));
            }
        }
    }

    /**
     * Checks if the tag is code or pre tag.
     *
     * @param node the node to check
     * @return {@code true} if the tag is code or pre tag, {@code false} otherwise
     */
    private static boolean isCodeOrPreTag(DetailNode node) {
        final String tagName = getHtmlTagName(node);
        return "code".equals(tagName) || "pre".equals(tagName);
    }

    /**
     * Checks if the tag is nested inside any other code or pre tag.
     *
     * @param node the node to check
     * @return {@code true} if the tag is nested, {@code false} otherwise
     */
    private static boolean isNested(DetailNode node) {
        boolean result = false;
        DetailNode parent = node.getParent();
        while (parent != null) {
            if (isCodeOrPreTag(parent)) {
                result = true;
                break;
            }
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * Checks if the tag is convertable in inline tag.
     *
     * @param listOfTextNodes the list of text nodes to check.
     * @return {@code true} if the tag is convertable in inline tag, {@code false} otherwise.
     */
    public static boolean isConvertableInInlineTag(Iterable<DetailNode> listOfTextNodes) {
        return containsBalancedBraces(listOfTextNodes) && !isStartWithStar(listOfTextNodes);
    }

    /**
     * Checks if the text list contains balanced braces.
     *
     * @param listOfTextNodes the list of text nodes to check
     * @return {@code true} if the text list contains balanced braces, {@code false} otherwise
     */
    public static boolean containsBalancedBraces(Iterable<DetailNode> listOfTextNodes) {
        int braceCount = 0;
        boolean result = true;
        for (DetailNode node: listOfTextNodes) {
            final String text = node.getText();
            for (int idx = 0; idx < text.length(); idx++) {
                final char letter = text.charAt(idx);
                if (letter == '{') {
                    braceCount++;
                }
                else if (letter == '}') {
                    braceCount--;
                }
                if (braceCount < 0) {
                    result = false;
                    break;
                }
            }
        }
        return result && braceCount == 0;
    }

    /**
     * Checks if the first element of the text list starts with a star.
     *
     * @param listOfTextNodes the list of text nodes to check
     * @return {@code true} if the first element of the text list does not start
     *         with a star, {@code false} otherwise
     */
    private static boolean isStartWithStar(Iterable<DetailNode> listOfTextNodes) {
        boolean result = false;
        for (DetailNode node : listOfTextNodes) {
            final String text = node.getText().trim();
            if (text.startsWith("*")) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Collects all text nodes contained within the specified node, including
     * text nested inside HTML elements and inline tags.
     *
     * @param node the root {@code DetailNode} to extract text nodes from
     * @return a list of text {@code DetailNode} instances
     */
    public static List<DetailNode> collectTextNodes(DetailNode node) {
        final DetailNode rootNode = JavadocUtil.findFirstToken(node,
                JavadocCommentsTokenTypes.HTML_CONTENT);
        final List<DetailNode> textNodes = new ArrayList<>();
        final List<DetailNode> inlineTags = new ArrayList<>();
        final Deque<DetailNode> htmlElements = new ArrayDeque<>();

        if (rootNode != null) {
            textNodes.addAll(JavadocUtil.getAllNodesOfType(rootNode,
                JavadocCommentsTokenTypes.TEXT));
            inlineTags.addAll(JavadocUtil.getAllNodesOfType(
                rootNode, JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG));
            htmlElements.addAll(JavadocUtil.getAllNodesOfType(
                rootNode, JavadocCommentsTokenTypes.HTML_ELEMENT));
        }

        while (!htmlElements.isEmpty()) {
            final DetailNode currentHtmlElement = htmlElements.pop();
            final DetailNode currentHtmlContent = JavadocUtil.findFirstToken(currentHtmlElement,
                    JavadocCommentsTokenTypes.HTML_CONTENT);

            if (currentHtmlContent != null) {
                textNodes.addAll(JavadocUtil.getAllNodesOfType(
                    currentHtmlContent, JavadocCommentsTokenTypes.TEXT));
                inlineTags.addAll(JavadocUtil.getAllNodesOfType(
                    currentHtmlContent, JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG));
                htmlElements.addAll(JavadocUtil.getAllNodesOfType(
                    currentHtmlContent, JavadocCommentsTokenTypes.HTML_ELEMENT));
            }
        }

        textNodes.addAll(getTextNodesFromInlineTags(inlineTags));
        return textNodes;
    }

    /**
     * Extracts text nodes from child nodes of the provided list of inline tags.
     *
     * @param inlineTags the list of inline tag {@code DetailNode}s
     * @return a list of text {@code DetailNode} instances found inside the inline tags
     */
    private static List<DetailNode> getTextNodesFromInlineTags(Iterable<DetailNode> inlineTags) {
        final List<DetailNode> textNodes = new ArrayList<>();
        for (DetailNode inlineTag : inlineTags) {
            textNodes.addAll(JavadocUtil.getAllNodesOfType(
                inlineTag.getFirstChild(), JavadocCommentsTokenTypes.TEXT));
        }
        return textNodes;
    }

    /**
     * Checks if the tag is single-line.
     *
     * @param node the node to check
     * @return {@code true} if the tag is single-line, {@code false} otherwise
     */
    private static boolean isSingleLineTag(DetailNode node) {
        final DetailNode endOfTag =
            JavadocUtil.findFirstToken(node, JavadocCommentsTokenTypes.HTML_TAG_END);
        return node.getLineNumber() == endOfTag.getLineNumber();
    }

    /**
     * Gets the tag name from an HTML_ELEMENT node.
     *
     * @param htmlElement the HTML_ELEMENT node
     * @return the tag name (e.g., "code", "pre")
     */
    @Nullable
    private static String getHtmlTagName(DetailNode htmlElement) {
        String result = null;
        final DetailNode htmlTagStart = JavadocUtil.findFirstToken(
            htmlElement, JavadocCommentsTokenTypes.HTML_TAG_START);
        if (htmlTagStart != null) {
            final DetailNode tagName = JavadocUtil.findFirstToken(
                htmlTagStart, JavadocCommentsTokenTypes.TAG_NAME);
            result = tagName.getText();
        }
        return result;
    }

}
