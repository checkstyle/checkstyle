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
 * @since 13.10.0
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
    public int[] getAcceptableJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode node) {
        if (isCodeOrPreTag(node)
                && node.getFirstChild().getType() != JavadocCommentsTokenTypes.VOID_ELEMENT
                && !isNested(node)) {

            if (isSingleLineTag(node)) {
                log(node.getLineNumber(), MSG_KEY_SINGLE_LINE);
            }
            else {
                log(node.getLineNumber(), MSG_KEY_MULTI_LINE);
            }
        }
    }

    /**
     * Checks if the tag is code or pre tag.
     *
     * @param node the node to check
     * @return {@code true} if the tag is code or pre tag, {@code false} otherwise
     */
    private boolean isCodeOrPreTag(DetailNode node) {
        return JavadocUtil.isTag(node, "code") || JavadocUtil.isTag(node, "pre");
    }

    /**
     * Checks if the tag is nested inside any other code or pre tag.
     *
     * @param node the node to check
     * @return {@code true} if the tag is nested, {@code false} otherwise
     */
    private boolean isNested(DetailNode node) {
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
     * Checks if the tag is single-line.
     *
     * @param node the node to check
     * @return {@code true} if the tag is single-line, {@code false} otherwise
     */
    private boolean isSingleLineTag(DetailNode node) {
        final DetailNode startOfTag = node.getFirstChild();
        final DetailNode endOfTag =
            JavadocUtil.findFirstToken(node, JavadocCommentsTokenTypes.HTML_TAG_END);
        return startOfTag.getLineNumber() == endOfTag.getLineNumber();
    }

}
