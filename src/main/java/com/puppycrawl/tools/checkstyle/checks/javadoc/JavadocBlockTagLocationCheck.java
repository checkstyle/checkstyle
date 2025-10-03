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

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;

/**
 * <div>
 * Checks that a
 * <a href="https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html#block-tags">
 * javadoc block tag</a> appears only at the beginning of a line, ignoring
 * leading asterisks and white space. A block tag is a token that starts with
 * {@code @} symbol and is preceded by a whitespace. This check ignores block
 * tags in comments and inside inline tags {&#64;code } and {&#64;literal }.
 * </div>
 *
 * <p>
 * Rationale: according to
 * <a href="https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html#block-tags">
 * the specification</a> all javadoc block tags should be placed at the beginning
 * of a line. Tags that are not placed at the beginning are treated as plain text.
 * To recognize intentional tag placement to text area it is better to escape the
 * {@code @} symbol, and all non-escaped tags should be located at the beginning
 * of the line. See NOTE section for details on how to escape.
 * </p>
 *
 * <p>
 * Notes:
 * To place a tag explicitly as text, escape the {@code @} symbol with HTML entity
 * &amp;#64; or place it inside {@code {@code }}, for example:
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * &#47;**
 *  * &amp;#64;serial literal in {&#64;code &#64;serial} Javadoc tag.
 *  *&#47;
 * </code></pre></div>
 *
 * @since 8.24
 */
@StatelessCheck
public class JavadocBlockTagLocationCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_BLOCK_TAG_LOCATION = "javadoc.blockTagLocation";

    /**
     * This regexp is used to extract the javadoc tags.
     */
    private static final Pattern JAVADOC_BLOCK_TAG_PATTERN = Pattern.compile("\\s@(\\w+)");

    /**
     * Block tags from Java 11
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html">
     * Documentation Comment Specification</a>.
     */
    private static final String[] DEFAULT_TAGS = {
        "author",
        "deprecated",
        "exception",
        "hidden",
        "param",
        "provides",
        "return",
        "see",
        "serial",
        "serialData",
        "serialField",
        "since",
        "throws",
        "uses",
        "version",
    };

    /**
     * Specify the javadoc tags to process.
     */
    private Set<String> tags;

    /**
     * Creates a new {@code JavadocBlockTagLocationCheck} instance with default settings.
     */
    public JavadocBlockTagLocationCheck() {
        setTags(DEFAULT_TAGS);
    }

    /**
     * Setter to specify the javadoc tags to process.
     *
     * @param values user's values.
     * @since 8.24
     */
    public final void setTags(String... values) {
        tags = Arrays.stream(values).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * The javadoc tokens that this check must be registered for. According to
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html#block-tags">
     * the specs</a> each block tag must appear at the beginning of a line, otherwise
     * it will be interpreted as a plain text. This check looks for a block tag
     * in the javadoc text, thus it needs the {@code TEXT} tokens.
     *
     * @return the javadoc token set this must be registered for.
     * @see JavadocCommentsTokenTypes
     */
    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.TEXT,
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
    public void visitJavadocToken(DetailNode ast) {
        if (!isCommentOrInlineTag(ast)) {
            final Matcher tagMatcher = JAVADOC_BLOCK_TAG_PATTERN.matcher(ast.getText());
            while (tagMatcher.find()) {
                final String tagName = tagMatcher.group(1);
                if (tags.contains(tagName)) {
                    log(ast.getLineNumber(), MSG_BLOCK_TAG_LOCATION, tagName);
                }
            }
        }
    }

    /**
     * Checks if the node can contain an unescaped block tag without violation.
     *
     * @param node to check
     * @return {@code true} if node is {@code @code}, {@code @literal} or HTML comment.
     */
    private static boolean isCommentOrInlineTag(DetailNode node) {
        boolean isInsideInlineTagOrHtmlComment = false;
        DetailNode current = node;

        while (current != null) {
            if (current.getType() == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG
                    || current.getType() == JavadocCommentsTokenTypes.HTML_COMMENT) {
                isInsideInlineTagOrHtmlComment = true;
                break;
            }
            current = current.getParent();
        }

        return isInsideInlineTagOrHtmlComment;
    }

}
