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

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc inline tags are preferred over HTML equivalents.
 * </div>
 *
 * <p>
 * Per <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html">
 * OpenJDK Style Guidelines v6</a>
 * Javadoc inline tags should be preferred over their HTML equivalents.
 * </p>
 *
 * <p>
 * <b>Violations (HTML tags that should be replaced):</b>
 * </p>
 * <ul>
 * <li>{@code {@code ...}} over {@code &lt;code&gt;...&lt;/code&gt;}</li>
 * <li>{@code {@link ...}} over {@code &lt;a href="#..."&gt;} for internal references</li>
 * <li>{@code {@literal &lt;}} over {@code &amp;lt;}</li>
 * <li>{@code {@literal &gt;}} over {@code &amp;gt;}</li>
 * </ul>
 *
 * <p>
 * <b>Not flagged:</b>
 * </p>
 * <ul>
 * <li>Block-level HTML tags with no Javadoc equivalent:
 *     {@code &lt;p&gt;}, {@code &lt;pre&gt;},
 *     {@code &lt;ul&gt;}, {@code &lt;ol&gt;},
 *     {@code &lt;li&gt;}, {@code &lt;table&gt;},
 *     {@code &lt;div&gt;}, {@code &lt;blockquote&gt;}, etc.</li>
 * <li>Content inside {@code &lt;pre&gt;} blocks (code examples)</li>
 * <li>Content inside {@code {@code}} and {@code {@literal}} inline tags</li>
 * </ul>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class PreferJavadocInlineTagsCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "prefer.javadoc.inline.tag";

    /** HTML entity for less-than symbol. */
    private static final String HTML_ENTITY_LT = "&lt;";

    /** HTML entity for greater-than symbol. */
    private static final String HTML_ENTITY_GT = "&gt;";

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.HTML_ELEMENT,
            JavadocCommentsTokenTypes.TEXT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (!isInsidePreTag(ast)) {
            if (ast.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                checkForCodeOrAnchorTag(ast);
            }
            else if (!isInsideCodeOrLiteralTag(ast)) {
                checkForAngleBracketEntities(ast);
            }
        }
    }

    /**
     * Checks if the HTML element is a {@code <code>} or {@code <a>} tag
     * that should be replaced with a Javadoc inline tag.
     *
     * @param htmlElement the HTML_ELEMENT node to check
     */
    private void checkForCodeOrAnchorTag(DetailNode htmlElement) {
        final String tagName = getHtmlTagName(htmlElement);
        if ("code".equalsIgnoreCase(tagName)) {
            log(htmlElement.getLineNumber(), htmlElement.getColumnNumber(),
                MSG_KEY, "{@code ...}", "<code>");
        }
        else if ("a".equalsIgnoreCase(tagName)) {
            checkAnchorTag(htmlElement);
        }
    }

    /**
     * Checks anchor tags and flags internal references that should use {@code {@link ...}}.
     *
     * @param htmlElement the HTML_ELEMENT node for anchor tag
     */
    private void checkAnchorTag(DetailNode htmlElement) {
        final String hrefValue = getHrefValue(htmlElement);

        if (hrefValue != null && hrefValue.startsWith("\"#")) {
            log(htmlElement.getLineNumber(), htmlElement.getColumnNumber(),
                MSG_KEY, "{@link ...}", "<a href=\"#...\">");
        }
    }

    /**
     * Checks Javadoc TEXT nodes for {@code &lt;} and {@code &gt;} HTML entities
     * that should be replaced with {@code {@literal <}} and {@code {@literal >}}.
     *
     * @param textNode the TEXT node to check
     */
    private void checkForAngleBracketEntities(DetailNode textNode) {
        final String text = textNode.getText();

        int ltIndex = text.indexOf(HTML_ENTITY_LT);
        while (ltIndex >= 0) {
            log(textNode.getLineNumber(), textNode.getColumnNumber() + ltIndex,
                MSG_KEY, "{@literal <}", HTML_ENTITY_LT);
            ltIndex = text.indexOf(HTML_ENTITY_LT, ltIndex + HTML_ENTITY_LT.length());
        }

        int gtIndex = text.indexOf(HTML_ENTITY_GT);
        while (gtIndex >= 0) {
            log(textNode.getLineNumber(), textNode.getColumnNumber() + gtIndex,
                MSG_KEY, "{@literal >}", HTML_ENTITY_GT);
            gtIndex = text.indexOf(HTML_ENTITY_GT, gtIndex + HTML_ENTITY_GT.length());
        }
    }

    /**
     * Gets the tag name from an HTML_ELEMENT node.
     *
     * @param htmlElement the HTML_ELEMENT node
     * @return the tag name (e.g., "code", "a")
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

    /**
     * Gets the href attribute value from an HTML element.
     *
     * @param htmlElement the HTML_ELEMENT node
     * @return the href value, or null if not found
     */
    @Nullable
    private static String getHrefValue(DetailNode htmlElement) {
        final DetailNode htmlTagStart = JavadocUtil.findFirstToken(
            htmlElement, JavadocCommentsTokenTypes.HTML_TAG_START);

        String result = null;
        final DetailNode attributes = JavadocUtil.findFirstToken(
            htmlTagStart, JavadocCommentsTokenTypes.HTML_ATTRIBUTES);

        if (attributes != null) {
            result = findHrefAttributeValue(attributes);
        }
        return result;
    }

    /**
     * Extracts the href attribute value from the HTML_ATTRIBUTES of an HTML element.
     *
     * @param attributes the HTML_ELEMENT node containing HTML_ATTRIBUTES
     * @return the href attribute value, or null if not found
     */
    @Nullable
    private static String findHrefAttributeValue(DetailNode attributes) {
        String result = null;
        DetailNode attr = attributes.getFirstChild();
        while (attr != null) {
            result = extractValueIfMatches(attr, "href");
            if (result != null) {
                break;
            }
            attr = attr.getNextSibling();
        }
        return result;
    }

    /**
     * Extracts attribute value if attribute name matches.
     *
     * @param attr the HTML_ATTRIBUTE node
     * @param attrName the attribute name to match (case-insensitive)
     * @return the attribute value if name matches, null otherwise
     */
    @Nullable
    private static String extractValueIfMatches(DetailNode attr, String attrName) {
        String result = null;
        final DetailNode attrNameNode = JavadocUtil.findFirstToken(
            attr, JavadocCommentsTokenTypes.TAG_ATTR_NAME);
        if (attrName.equalsIgnoreCase(attrNameNode.getText())) {
            final DetailNode valueNode = JavadocUtil.findFirstToken(
                attr, JavadocCommentsTokenTypes.ATTRIBUTE_VALUE);
            if (valueNode != null) {
                result = valueNode.getText();
            }
        }
        return result;
    }

    /**
     * Checks if the node is inside a PRE tag {@code <pre>}.
     * Content inside PRE tags is skipped to avoid false positives in code examples.
     *
     * @param node the node to check
     * @return true if inside a PRE block
     */
    private static boolean isInsidePreTag(DetailNode node) {
        boolean insidePre = false;
        DetailNode current = node;
        while (current != null) {
            final String tagName = getHtmlTagName(current);
            if ("pre".equalsIgnoreCase(tagName)) {
                insidePre = true;
                break;
            }
            current = current.getParent();
        }
        return insidePre;
    }

    /**
     * Checks if the node is inside a {@code {@code}} or {@code {@literal}} inline tag.
     * Content inside these tags is meant to be displayed literally, so HTML patterns
     * within them are intentional examples and should not be flagged.
     *
     * @param node the node to check
     * @return true if inside a code or literal inline tag
     */
    private static boolean isInsideCodeOrLiteralTag(DetailNode node) {
        return node.getParent().getType() == JavadocCommentsTokenTypes.CODE_INLINE_TAG
                    || node.getParent().getType() == JavadocCommentsTokenTypes.LITERAL_INLINE_TAG;
    }

}
