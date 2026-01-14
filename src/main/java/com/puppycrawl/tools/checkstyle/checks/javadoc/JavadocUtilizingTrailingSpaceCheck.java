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
 * <li>It flags lines that exceed {@code lineLimit}, except when the line contains
 * only an unbreakable element such as a Javadoc tag or URL that starts the line.</li>
 * </ul>
 *
 * @since 13.3.0
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

    /** Configurable line length limit. */
    private int lineLimit = DEFAULT_LINE_LIMIT;

    /** Collects logical Javadoc lines for the current Javadoc comment. */
    private final List<JavadocLine> lines = new ArrayList<>();

    /** Current line being built. */
    private JavadocLine currentLine;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {JavadocCommentsTokenTypes.JAVADOC_CONTENT};
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    /**
     * Setter to specify the line length limit.
     *
     * @param limit the maximum length to target for Javadoc lines
     * @since 13.3.0
     */
    public void setLineLimit(int limit) {
        lineLimit = limit;
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        lines.clear();
        currentLine = null;
        processNode(rootAst);
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        commitCurrentLine();
        validateLines();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // processing is done in beginJavadocTree/finishJavadocTree via processNode
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

            if (line.length > lineLimit && !line.startsWithUnbreakable) {
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
     * @param lineNumber the line number of the violation
     * @param columnNumber the column number of the violation
     * @param key the message key
     * @param args the message arguments
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
        boolean tooShort = false;
        final boolean canPullNext = canPullFromNextLine(index);

        if (canPullNext) {
            final JavadocLine prev = lines.get(index - 1);
            if (!prev.hasContent || !canPullFromNextLine(index - 1)) {
                tooShort = true;
            }
        }

        return tooShort;
    }

    /**
     * Processes a single node in the Javadoc tree.
     *
     * @param node the node to process
     */
    private void processNode(DetailNode node) {
        switch (node.getType()) {
            case JavadocCommentsTokenTypes.NEWLINE -> handleNewline(node);
            case JavadocCommentsTokenTypes.TEXT -> handleText(node);
            case JavadocCommentsTokenTypes.TAG_NAME -> handleTagContent();
            case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG -> handleInlineTag(node);
            default -> {
                if (node.getType() != JavadocCommentsTokenTypes.HTML_ELEMENT) {
                    processChildren(node);
                }
            }
        }
    }

    /**
     * Processes all children of a node.
     *
     * @param node the parent node
     */
    private void processChildren(DetailNode node) {
        for (DetailNode child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            processNode(child);
        }
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
     * Handles a TAG_NAME or PARAMETER_NAME node within a block tag.
     */
    private void handleTagContent() {
        currentLine.startsWithUnbreakable = true;
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
            processNonEmptyText(trimmed, node.getColumnNumber());
        }
    }

    /**
     * Processes non-empty text content, updating line state accordingly.
     *
     * @param trimmedText the trimmed text content
     * @param columnNumber the column number of the text node
     */
    private void processNonEmptyText(String trimmedText, int columnNumber) {
        if (!currentLine.hasContent) {
            currentLine.initContent(trimmedText, URL_PATTERN, columnNumber);
        }
    }

    /**
     * Handles an inline tag like {@code {@link ...}}.
     *
     * @param node the inline tag node
     */
    private void handleInlineTag(DetailNode node) {
        final String tagText = collectAllText(node);
        currentLine.updateLength(node.getColumnNumber(), tagText.length());

        if (!currentLine.hasContent) {
            currentLine.initContent("", URL_PATTERN, node.getColumnNumber());
            currentLine.startsWithUnbreakable = true;
        }
    }

    /**
     * Checks if content can be pulled from the next line.
     *
     * @param currentIndex current line index
     * @return true if the first word from the next content line fits within lineLimit
     */
    private boolean canPullFromNextLine(int currentIndex) {
        final int currentLength = lines.get(currentIndex).length;
        boolean pullNext = false;

        if (currentIndex + 1 < lines.size()) {
            final JavadocLine nextLine = lines.get(currentIndex + 1);

            if (nextLine.hasContent && !nextLine.startsWithUnbreakable) {
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
     * Collects all text from a node and its descendants.
     *
     * @param node the starting node
     * @return the concatenated text
     */
    private static String collectAllText(DetailNode node) {
        final StringBuilder builder = new StringBuilder();
        appendNodeText(node, builder, false);
        return builder.toString();
    }

    /**
     * Recursively appends text from nodes to a StringBuilder.
     *
     * @param node the node to process
     * @param builder the StringBuilder to append to
     * @param visibleOnly if true, only append TEXT nodes; otherwise
     *     append all leaf text
     */
    private static void appendNodeText(
        DetailNode node, StringBuilder builder, boolean visibleOnly) {

        final DetailNode firstChild = node.getFirstChild();
        if (firstChild == null) {
            builder.append(node.getText());
        }
        else {
            for (DetailNode child = firstChild; child != null; child = child.getNextSibling()) {
                appendNodeText(child, builder, visibleOnly);
            }
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

        /** Whether the line starts with an unbreakable element (tag or URL). */
        private boolean startsWithUnbreakable;

        /** The first word of the line, used for pull calculations. */
        private String firstWord;

        /**
         * Creates a JavadocLine.
         *
         * @param lineNumber the source line number
         */
        private JavadocLine(int lineNumber) {
            this.lineNumber = lineNumber;
            this.length = 0;
            this.firstWord = "";
        }

        /**
         * Updates the line length based on column position and content length.
         *
         * @param startColumn the starting column (0-indexed)
         * @param contentLength the length of the content
         */
        private void updateLength(int startColumn, int contentLength) {
            length = startColumn + contentLength;
        }

        /**
         * Initializes content state for the first non-empty text on this line.
         *
         * @param trimmedText the trimmed text content
         * @param urlPattern pattern used to detect URLs
         * @param column the starting column (0-indexed)
         */
        private void initContent(String trimmedText, java.util.regex.Pattern urlPattern,
                int column) {
            hasContent = true;
            columnNumber = column;
            if (urlPattern.matcher(trimmedText).lookingAt()) {
                startsWithUnbreakable = true;
            }
            else {
                final int spaceIndex = trimmedText.indexOf(' ');
                firstWord = trimmedText;
                if (spaceIndex != -1) {
                    firstWord = trimmedText.substring(0, spaceIndex);
                }
            }
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
