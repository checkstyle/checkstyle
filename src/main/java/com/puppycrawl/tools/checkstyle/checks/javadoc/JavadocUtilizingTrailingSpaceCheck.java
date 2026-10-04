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
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc lines efficiently utilize the available horizontal space.
 * </div>
 *
 * <p>
 * Every line of a Javadoc comment is compared with {@code lineLimit}, the preferred line
 * length. A line is reported when it is longer than the limit, or when it stops early although
 * the first word of the next line would still have fit. Blank lines are never reported.
 * The examples on this page use the default limit of 80 characters, and show the Javadoc
 * of a method, indented by four spaces.
 * </p>
 *
 * <p>
 * <b>Lines that stop too early.</b> Words are expected to fill a line up to the limit,
 * so a line is reported when the first word of the next line would still fit on it.
 * The fix is to move words up until the next word does not fit. Words are never moved up
 * from a line that is blank, or that starts with a block tag, an inline tag, a URL or an
 * HTML tag, so the line before such a line is not reported.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // violation, "not" would fit on the first line
 *     &#47;**
 *      * Not part of the API. This is returned when a type
 *      * not known to this wrapper is returned.
 *      *&#47;
 *
 *     // ok
 *     &#47;**
 *      * Not part of the API. This is returned when a type not known to this
 *      * wrapper is returned.
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>Lines that are too long.</b> A line is reported when it is longer than the limit.
 * The fix is to wrap it at a word.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // violation, longer than 80 characters
 *     &#47;**
 *      * Creates a client for the service and loads its settings from the given file path.
 *      *&#47;
 *
 *     // ok
 *     &#47;**
 *      * Creates a client for the service and loads its settings from the given
 *      * file path.
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>Block tags.</b> The value of a block tag, such as {@code @param}, {@code @return}
 * or {@code @throws}, is checked like any other text. A line that has only the name of
 * the block tag is not reported, so the value can start on the next line. A line that
 * starts with a block tag is never joined to the line before it.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // violation, longer than 80 characters
 *     &#47;**
 *      * &#64;return Date the company was closed or dissolved, see {&#64;link #getStatus()}.
 *      *&#47;
 *
 *     // ok, the value starts on the next line
 *     &#47;**
 *      * &#64;return
 *      *     Date the company was closed or dissolved, see {&#64;link #getStatus()}.
 *      *&#47;
 *
 *     // ok, wrapped before the inline tag
 *     &#47;**
 *      * &#64;return Date the company was closed or dissolved, see
 *      * {&#64;link #getStatus()}.
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>Inline tags.</b> An inline tag, such as a link to a class, cannot be wrapped. A line
 * that starts with an inline tag and has nothing else on it is not reported, even when it is
 * longer than the limit. When other words follow on the same line, the line is reported,
 * because those words can move to the next line. A block tag never gets this exception for
 * its value.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // ok, a tag alone on a line may be longer than 80 characters
 *     &#47;**
 *      * Refer to the method
 *      * {&#64;link com.example.registry.status.CompanyStatusRegistry#findStatus(String)}
 *      *&#47;
 *
 *     // violation, other words follow the tag
 *     &#47;**
 *      * {&#64;link com.example.registry.status.CompanyStatusRegistry#findStatus(String)} finds it.
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>URLs and links.</b> A URL cannot be wrapped, so a line that matches
 * {@code ignorePattern} is not reported as too long. By default this is every line that has
 * a URL or the {@code href} attribute of a link. A block tag line whose value starts with a
 * link, such as {@code @see <a href="...">}, is therefore not reported, whether the link ends
 * on that line or on a later one. Such lines can still be reported as stopping too early.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // ok, lines that have a URL or a link may be longer than 80 characters
 *     &#47;**
 *      * See https://example.com/documentation/company-registry/api/v2/status-endpoints/details
 *      *
 *      * &#64;see &lt;a href="https://example.com/documentation/company-registry/api/v2/status"&gt;
 *      *     Status endpoints&lt;/a&gt;
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>HTML.</b> A line that starts with an HTML tag, such as {@code <p>} or {@code </li>}, is
 * not reported as too short, and it is not joined to the line before it, so tags can be
 * placed on their own lines. Such a line, and the text inside an HTML element, must still
 * not be longer than the limit.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // ok, a line that starts with an HTML tag is never too short
 *     &#47;**
 *      * &lt;p&gt;Companies House allows you to query
 *      * information on registered companies in Britain.&lt;/p&gt;
 *      *&#47;
 *
 *     // violation, text inside an HTML element is checked
 *     &#47;**
 *      * &lt;ul&gt;
 *      * &lt;li&gt;Static configuration reads files from the classpath or a given path.&lt;/li&gt;
 *      * &lt;/ul&gt;
 *      *&#47;
 *
 *     // ok
 *     &#47;**
 *      * &lt;ul&gt;
 *      * &lt;li&gt;Static configuration reads files from the classpath or a given
 *      * path.&lt;/li&gt;
 *      * &lt;/ul&gt;
 *      *&#47;
 * </code></pre></div>
 *
 * <p>
 * <b>Pre and code elements.</b> The content of {@code <pre>} and {@code <code>} elements,
 * and HTML comments, is ignored, so code samples can keep their own formatting.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 *     // ok, the content of a pre element is ignored
 *     &#47;**
 *      * Example:
 *      * &lt;pre&gt;
 *      * new CompanyRegistry().findStatus("00000006").orElseThrow(NotRegisteredException::new);
 *      * &lt;/pre&gt;
 *      *&#47;
 * </code></pre></div>
 *
 * @since 14.4.0
 */
@FileStatefulCheck
public class JavadocUtilizingTrailingSpaceCheck extends AbstractJavadocCheck {

    /** Message key for too short Javadoc line. */
    public static final String MSG_TOO_SHORT = "javadoc.utilizing.trailing.space.too.short";

    /** Message key for too long Javadoc line. */
    public static final String MSG_TOO_LONG = "javadoc.utilizing.trailing.space.too.long";

    /** Pattern that recognizes URLs. */
    private static final Pattern URL_PATTERN = Pattern.compile("https?://|ftp://");

    /** Default maximum line length. */
    private static final int DEFAULT_LINE_LIMIT = 80;

    /** Collects logical Javadoc lines for the current Javadoc comment. */
    private final List<JavadocLine> lines = new ArrayList<>();

    /** Configurable line length limit. */
    private int lineLimit = DEFAULT_LINE_LIMIT;

    /** Pattern for lines that are not reported as too long. */
    private Pattern ignorePattern =
            Pattern.compile("href\\s*=\\s*\"[^\"]*\"|http://|https://|ftp://");

    /** Current line being built. */
    private JavadocLine currentLine;

    /** The node whose subtree is currently being skipped, or null. */
    private DetailNode skipRoot;

    /**
     * Creates a new {@code JavadocUtilizingTrailingSpaceCheck} instance.
     */
    public JavadocUtilizingTrailingSpaceCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.NEWLINE,
            JavadocCommentsTokenTypes.TEXT,
            JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG,
            JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG,
            JavadocCommentsTokenTypes.HTML_ELEMENT,
            JavadocCommentsTokenTypes.HTML_COMMENT,
            JavadocCommentsTokenTypes.HTML_TAG_START,
            JavadocCommentsTokenTypes.HTML_TAG_END,
            JavadocCommentsTokenTypes.VOID_ELEMENT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getDefaultJavadocTokens();
    }

    /**
     * Setter to specify the line length limit.
     *
     * @param limit the maximum length to target for Javadoc lines
     * @since 14.4.0
     */
    public void setLineLimit(int limit) {
        lineLimit = limit;
    }

    /**
     * Setter to specify pattern for lines that are not reported as too long.
     *
     * @param pattern a pattern.
     * @since 14.4.0
     */
    public void setIgnorePattern(Pattern pattern) {
        ignorePattern = pattern;
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        lines.clear();
        currentLine = null;
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        commitCurrentLine();
        validateLines();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (skipRoot == null) {
            switch (ast.getType()) {
                case JavadocCommentsTokenTypes.NEWLINE -> handleNewline(ast);
                case JavadocCommentsTokenTypes.TEXT -> handleText(ast);
                case JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG -> handleBlockTag(ast);
                case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG -> handleInlineTag(ast);
                case JavadocCommentsTokenTypes.HTML_ELEMENT -> handleHtmlElement(ast);
                case JavadocCommentsTokenTypes.HTML_COMMENT -> skipNode(ast);
                default -> handleHtmlTag(ast);
            }
        }
    }

    @Override
    public void leaveJavadocToken(DetailNode ast) {
        if (ast == skipRoot) {
            skipRoot = null;
        }
    }

    /**
     * Validates all collected lines for violations.
     */
    private void validateLines() {
        for (int index = 0; index < lines.size(); index++) {
            final JavadocLine line = lines.get(index);

            if (!line.hasContent) {
                continue;
            }

            if (line.length > lineLimit && !line.isOnlyInlineTag()
                    && !ignorePattern.matcher(getLine(line.lineNumber - 1)).find()) {
                log(line.firstNode, MSG_TOO_LONG, lineLimit, line.length);
            }

            if (isTooShort(index)) {
                log(line.firstNode, MSG_TOO_SHORT, lineLimit, line.length);
            }
        }
    }

    /**
     * Decides whether the line at the given index is too short
     * (i.e. we should have pulled content from the next line).
     *
     * @param index the line index to check
     * @return true if the line is too short
     */
    private boolean isTooShort(int index) {
        return !lines.get(index).startsWithHtml
                && canPullFromNextLine(index)
                && (index == 0
                    || !lines.get(index - 1).hasContent
                    || !canPullFromNextLine(index - 1));
    }

    /**
     * Handles a newline token.
     *
     * @param node the newline node
     */
    private void handleNewline(DetailNode node) {
        initCurrentLine(node.getLineNumber());
    }

    /**
     * Handles a Javadoc block tag node. A line that starts a block tag cannot
     * be merged into the previous line, but its value is still validated.
     *
     * @param node the block tag node
     */
    private void handleBlockTag(DetailNode node) {
        initCurrentLine(node.getLineNumber());
        currentLine.startsWithBlockTag = true;
        currentLine.firstNode = node;
    }

    /**
     * Handles a TEXT node.
     *
     * @param node the text node
     */
    private void handleText(DetailNode node) {
        final String text = node.getText();

        initCurrentLine(node.getLineNumber());
        currentLine.updateLength(node.getColumnNumber(), text.length());

        final String trimmed = text.trim();
        if (!trimmed.isEmpty()) {
            if (currentLine.hasContent) {
                currentLine.markAdditionalContent(text);
            }
            else {
                currentLine.initContent(trimmed, URL_PATTERN, node);
            }
        }
    }

    /**
     * Handles a Javadoc inline tag node. The tag is measured as a whole and
     * its content is skipped.
     *
     * @param node the inline tag node
     */
    private void handleInlineTag(DetailNode node) {
        initCurrentLine(node.getLineNumber());
        updateLengthToEnd(node);

        if (currentLine.hasContent) {
            currentLine.hasAdditionalContent = true;
        }
        else {
            currentLine.hasContent = true;
            if (!currentLine.startsWithBlockTag) {
                currentLine.startsWithUnbreakable = true;
                currentLine.startsWithInlineTag = true;
                currentLine.firstNode = node;
            }
        }
        skipRoot = node;
    }

    /**
     * Handles an HTML element. The content of {@code pre} and {@code code}
     * elements is skipped.
     *
     * @param element the HTML element node
     */
    private void handleHtmlElement(DetailNode element) {
        if (JavadocUtil.isTag(element, "pre") || JavadocUtil.isTag(element, "code")) {
            skipNode(element);
        }
    }

    /**
     * Handles an HTML start tag, end tag or void element. A line that starts
     * with such a tag is never reported as too short, but it can still be too long.
     *
     * @param node the HTML tag node
     */
    private void handleHtmlTag(DetailNode node) {
        initCurrentLine(node.getLineNumber());
        if (!currentLine.hasContent) {
            currentLine.hasContent = true;
            if (!currentLine.startsWithBlockTag) {
                currentLine.startsWithHtml = true;
                currentLine.firstNode = node;
            }
        }
        updateLengthToEnd(node);
        skipRoot = node;
    }

    /**
     * Skips the subtree of the given node. If the node spans several lines, the
     * line where it starts is marked, because the lines after it are not adjacent
     * to the lines that were skipped.
     *
     * @param node the node to skip
     */
    private void skipNode(DetailNode node) {
        initCurrentLine(node.getLineNumber());

        final DetailNode end = lastLeaf(node);
        if (end.getLineNumber() == node.getLineNumber()) {
            currentLine.updateLength(end.getColumnNumber(), end.getText().length());
        }
        else {
            currentLine.skippedLinesFollow = true;
        }
        skipRoot = node;
    }

    /**
     * Extends the current line to the end of the given node, if the node ends on
     * the line where it starts.
     *
     * @param node the node to measure
     */
    private void updateLengthToEnd(DetailNode node) {
        final DetailNode end = lastLeaf(node);
        if (end.getLineNumber() == node.getLineNumber()) {
            currentLine.updateLength(end.getColumnNumber(), end.getText().length());
        }
    }

    /**
     * Finds the last leaf of the subtree of the given node.
     *
     * @param node the root of the subtree
     * @return the last leaf, which is the node itself if it has no children
     */
    private static DetailNode lastLeaf(DetailNode node) {
        DetailNode leaf = node;
        DetailNode child = leaf.getFirstChild();
        while (child != null) {
            while (child.getNextSibling() != null) {
                child = child.getNextSibling();
            }
            leaf = child;
            child = leaf.getFirstChild();
        }
        return leaf;
    }

    /**
     * Checks if content can be pulled from the next line.
     *
     * @param currentIndex current line index
     * @return true if the first word from the next content line fits within
     *         lineLimit
     */
    private boolean canPullFromNextLine(int currentIndex) {
        boolean pullNext = false;

        if (currentIndex + 1 < lines.size() && !lines.get(currentIndex).skippedLinesFollow) {

            final JavadocLine nextLine = lines.get(currentIndex + 1);

            if (nextLine.hasContent && !nextLine.startsWithUnbreakable
                    && !nextLine.startsWithBlockTag && !nextLine.startsWithHtml) {
                final int currentLength = lines.get(currentIndex).length;
                final int potentialLength = currentLength + 1 + nextLine.firstWordLength();
                pullNext = potentialLength <= lineLimit;
            }
        }

        return pullNext;
    }

    /**
     * Initializes the current line if needed.
     *
     * @param lineNumber the source line number
     */
    private void initCurrentLine(int lineNumber) {
        if (currentLine == null || currentLine.lineNumber != lineNumber) {
            commitCurrentLine();
            currentLine = new JavadocLine(lineNumber);
        }
    }

    /**
     * Commits the current line to the lines list and clears it.
     */
    private void commitCurrentLine() {
        if (currentLine != null) {
            lines.add(currentLine);
        }
    }

    /**
     * Represents a logical line in a Javadoc comment.
     */
    private static final class JavadocLine {

        /** The source line number. */
        private final int lineNumber;

        /** The first node with content on this line, the location of violations. */
        private DetailNode firstNode;

        /** The length of the line (end column). */
        private int length;

        /** Whether the line has meaningful content. */
        private boolean hasContent;

        /** Whether the line starts with an unbreakable element (inline tag or URL). */
        private boolean startsWithUnbreakable;

        /** Whether the line starts with an inline tag. */
        private boolean startsWithInlineTag;

        /** Whether the line has content after its leading inline tag. */
        private boolean hasAdditionalContent;

        /** Whether the line starts with a Javadoc block tag. */
        private boolean startsWithBlockTag;

        /** Whether the line starts with an HTML tag. */
        private boolean startsWithHtml;

        /** Whether skipped lines follow this line, so the next line is not adjacent. */
        private boolean skippedLinesFollow;

        /** The first word of the line, used for pull calculations. */
        private String firstWord;

        /**
         * Creates a JavadocLine.
         *
         * @param lineNumber the source line number
         */
        private JavadocLine(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        /**
         * Updates the line length based on column position and content length.
         *
         * @param startColumn   the starting column (0-indexed)
         * @param contentLength the length of the content
         */
        private void updateLength(int startColumn, int contentLength) {
            length = startColumn + contentLength;
        }

        /**
         * Initializes content state for the first non-empty text on this line.
         *
         * @param trimmedText the trimmed text content
         * @param urlPattern  pattern used to detect URLs
         * @param textNode    the node of the text content
         */
        private void initContent(String trimmedText, Pattern urlPattern,
                DetailNode textNode) {
            hasContent = true;
            firstWord = trimmedText.split(" ", 2)[0];
            if (!startsWithBlockTag) {
                firstNode = textNode;
                startsWithUnbreakable = urlPattern.matcher(trimmedText).lookingAt();
            }
        }

        /**
         * Marks the line as having additional content when the given text,
         * which follows the first content of the line, contains a separate word.
         * Text attached to the preceding element, such as trailing punctuation,
         * is not additional content.
         *
         * @param text the text that follows the first content of the line
         */
        private void markAdditionalContent(String text) {
            if (text.stripTrailing().chars().anyMatch(Character::isWhitespace)) {
                hasAdditionalContent = true;
            }
        }

        /**
         * Checks whether the only content of this line is a single inline tag,
         * which may exceed the limit.
         *
         * @return true if the line contains only an inline tag
         */
        private boolean isOnlyInlineTag() {
            return startsWithInlineTag && !hasAdditionalContent;
        }

        /**
         * Returns the length of the first word on this line.
         *
         * @return the first word length
         */
        private int firstWordLength() {
            return firstWord.length();
        }
    }

}
