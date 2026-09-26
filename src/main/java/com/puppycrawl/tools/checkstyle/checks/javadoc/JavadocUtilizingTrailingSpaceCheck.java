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
 * The check focuses strictly on line length:
 * </p>
 * <ul>
 * <li>It flags lines that break prematurely before the configured
 * {@code lineLimit} when content from the following line could have been moved up.</li>
 * <li>It flags lines that exceed {@code lineLimit}, including lines with Javadoc
 * block tag values, except when the only content of the line is an inline
 * Javadoc tag or URL that starts the line.</li>
 * </ul>
 *
 * @since 14.2.0
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

    /** Current line being built. */
    private JavadocLine currentLine;

    /** Depth of HTML elements and inline tags whose content is being skipped. */
    private int skipDepth;

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
     * @since 14.2.0
     */
    public void setLineLimit(int limit) {
        lineLimit = limit;
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
        if (skipDepth > 0) {
            if (isSkippedSubtree(ast)) {
                skipDepth++;
            }
        }
        else {
            switch (ast.getType()) {
                case JavadocCommentsTokenTypes.NEWLINE -> handleNewline(ast);
                case JavadocCommentsTokenTypes.TEXT -> handleText(ast);
                case JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG -> handleBlockTag(ast);
                case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG -> {
                    handleInlineTag(ast);
                    skipDepth++;
                }
                // HTML_ELEMENT is the only remaining token
                default -> skipDepth++;
            }
        }
    }

    @Override
    public void leaveJavadocToken(DetailNode ast) {
        if (isSkippedSubtree(ast)) {
            skipDepth--;
        }
    }

    /**
     * Checks whether the content of the given node should not be processed.
     * The content of HTML elements and inline tags is skipped. Inline tags are
     * measured as a whole in {@link #handleInlineTag(DetailNode)}.
     *
     * @param node the node to check
     * @return true if the content of the node should be skipped
     */
    private static boolean isSkippedSubtree(DetailNode node) {
        final int type = node.getType();
        return type == JavadocCommentsTokenTypes.HTML_ELEMENT
                || type == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG;
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

            if (line.length > lineLimit && !line.isOnlyUnbreakable()) {
                logViolation(line.lineNumber, line.columnNumber,
                        MSG_TOO_LONG, lineLimit, line.length);
            }

            if (isTooShort(index)) {
                logViolation(line.lineNumber, line.columnNumber,
                        MSG_TOO_SHORT, lineLimit, line.length);
            }
        }
    }

    /**
     * Logs a violation at the given line and column number.
     *
     * @param lineNumber   the line number of the violation
     * @param columnNumber the column number of the violation
     * @param key          the message key
     * @param args         the message arguments
     */
    private void logViolation(int lineNumber, int columnNumber, String key, Object... args) {
        log(lineNumber, columnNumber, key, args);
    }

    /**
     * Decides whether the line at the given index is too short
     * (i.e. we should have pulled content from the next line).
     *
     * @param index the line index to check
     * @return true if the line is too short
     */
    private boolean isTooShort(int index) {
        return canPullFromNextLine(index)
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
        currentLine.columnNumber = node.getColumnNumber();
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
                currentLine.initContent(trimmed, URL_PATTERN, node.getColumnNumber());
            }
        }
    }

    /**
     * Handles a Javadoc inline tag node.
     *
     * @param node the inline tag node
     */
    private void handleInlineTag(DetailNode node) {
        initCurrentLine(node.getLineNumber());

        final DetailNode end = JavadocUtil.findFirstToken(node.getFirstChild(),
                JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_END);
        if (end.getLineNumber() == node.getLineNumber()) {
            currentLine.updateLength(end.getColumnNumber(), 1);
        }

        if (currentLine.hasContent) {
            currentLine.hasAdditionalContent = true;
        }
        else {
            currentLine.hasContent = true;
            if (!currentLine.startsWithBlockTag) {
                currentLine.startsWithUnbreakable = true;
                currentLine.columnNumber = node.getColumnNumber();
            }
        }
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

        if (currentIndex + 1 < lines.size()) {

            final JavadocLine nextLine = lines.get(currentIndex + 1);

            if (nextLine.hasContent && !nextLine.startsWithUnbreakable
                    && !nextLine.startsWithBlockTag) {
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

        /** The column number of the first content on this line. */
        private int columnNumber;

        /** The length of the line (end column). */
        private int length;

        /** Whether the line has meaningful content. */
        private boolean hasContent;

        /** Whether the line starts with an unbreakable element (inline tag or URL). */
        private boolean startsWithUnbreakable;

        /** Whether the line has content after its leading unbreakable element. */
        private boolean hasAdditionalContent;

        /** Whether the line starts with a Javadoc block tag. */
        private boolean startsWithBlockTag;

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
         * @param column      the starting column (0-indexed)
         */
        private void initContent(String trimmedText, Pattern urlPattern,
                int column) {
            hasContent = true;
            firstWord = trimmedText.split(" ", 2)[0];
            if (!startsWithBlockTag) {
                columnNumber = column;
                if (urlPattern.matcher(trimmedText).lookingAt()) {
                    startsWithUnbreakable = true;
                    hasAdditionalContent = !firstWord.equals(trimmedText);
                }
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
         * Checks whether the only content of this line is a single unbreakable
         * element, such as an inline tag or a URL, which may exceed the limit.
         *
         * @return true if the line contains only an unbreakable element
         */
        private boolean isOnlyUnbreakable() {
            return startsWithUnbreakable && !hasAdditionalContent;
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
