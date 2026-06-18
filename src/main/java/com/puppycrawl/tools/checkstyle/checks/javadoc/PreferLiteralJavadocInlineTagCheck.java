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

import java.util.List;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc inline tags are preferred over escaping entities.
 * According to <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html">
 * OpenJDK Style Guidelines v6</a>
 * Javadoc inline tags should be preferred over their HTML equivalents.
 * Entities that are flagged by the check are:
 * <ul>
 * <li>{@literal &lt;}</li>
 * <li>{@literal &gt;}</li>
 * <li>{@literal &amp;}</li>
 * <li>{@literal &quot;}</li>
 * <li>{@literal &apos;}</li>
 * </ul>
 *
 * <p>
 * Reason of only these entities are flagged is given here :
 * <a href="https://www.w3.org/TR/WD-xml-970807#dt-escape">Predefined Entities</a>
 * </p>
 *
 * </div>
 *
 * <p>
 * <b>Not flagged:</b>
 * </p>
 * <ul>
 * <li>Content inside {@code <pre>} and {@code <code>} blocks (code examples)</li>
 * <li>Content inside {@code {@code}}, {@code {@literal}}, {@code {@snippet}} inline tags</li>
 * </ul>
 *
 * @since 13.11.0
 */
@StatelessCheck
public class PreferLiteralJavadocInlineTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "prefer.literal.javadoc.inline.tag";

    /** HTML entities to be replaced with {@code {@literal ...}}. */
    private static final List<String> HTML_ENTITIES =
            List.of("&lt;", "&gt;", "&amp;", "&quot;", "&apos;");

    /**
     * Creates a new {@code PreferJavadocInlineTagsCheck} instance.
     */
    public PreferLiteralJavadocInlineTagCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.TEXT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (!isInsidePreOrCodeTag(ast) && !isInsideInlineTag(ast)) {
            checkForHtmlEntities(ast);
        }
    }

    /**
     * Checks Javadoc TEXT nodes for HTML entities that should be replaced with
     * {@code {@literal ...}}.
     *
     * @param textNode the TEXT node to check
     */
    private void checkForHtmlEntities(DetailNode textNode) {
        final String text = textNode.getText();

        for (String htmlEntity : HTML_ENTITIES) {
            int htmlEntityIndex = text.indexOf(htmlEntity);
            while (htmlEntityIndex >= 0) {
                log(textNode.getLineNumber(), textNode.getColumnNumber() + htmlEntityIndex,
                    MSG_KEY, htmlEntity);
                htmlEntityIndex = text.indexOf(htmlEntity, htmlEntityIndex + htmlEntity.length());
            }
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
     * Checks if the node is inside a pre or code tag.
     *
     * @param node the node to check
     * @return true if inside a pre or code block
     */
    private static boolean isInsidePreOrCodeTag(DetailNode node) {
        DetailNode current = node;
        boolean insidePreOrCode = false;
        while (current != null) {
            final String tagName = getHtmlTagName(current);
            if ("pre".equalsIgnoreCase(tagName) || "code".equalsIgnoreCase(tagName)) {
                insidePreOrCode = true;
                break;
            }
            current = current.getParent();
        }
        return insidePreOrCode;
    }

    /**
     * Checks if the node is inside a {@code {@code}}, {@code {@literal}} or {@code {@snippet}}
     * inline tag.
     * Content inside these tags is meant to be displayed literally, so HTML patterns
     * within them are intentional examples and should not be flagged.
     *
     * @param node the node to check
     * @return true if inside a code, literal or snippet inline tag
     */
    private static boolean isInsideInlineTag(DetailNode node) {
        return node.getParent().getType() == JavadocCommentsTokenTypes.CODE_INLINE_TAG
                    || node.getParent().getType() == JavadocCommentsTokenTypes.LITERAL_INLINE_TAG
                    || node.getParent().getType() == JavadocCommentsTokenTypes.SNIPPET_BODY;
    }

}
