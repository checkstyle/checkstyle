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
import java.util.Set;
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
 * @since 10.19
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

    /** Set of structural HTML tags that should be ignored when they start a line. */
    private static final Set<String> STRUCTURAL_TAGS = Set.of(
            "p", "div", "ul", "ol", "li", "pre", "table", "tr", "td", "th",
            "blockquote", "h1", "h2", "h3", "h4", "h5", "h6"
    );

    /** Configurable line length limit. */
    private int lineLimit = DEFAULT_LINE_LIMIT;

    /** Tracks whether we are inside a pre block. */
    private boolean insidePreBlock;

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
     */
    public void setLineLimit(int limit) {
        lineLimit = limit;
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        insidePreBlock = false;
        lines.clear();
        currentLine = null;
        processJavadocTree(rootAst);
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        commitCurrentLine();
        validateLines();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // Processing is done in beginJavadocTree/finishJavadocTree
    }

    /**
     * Validates all collected lines for violations.
     */
    private void validateLines() {
        for (int i = 0; i < lines.size(); i++) {
            final JavadocLine line = lines.get(i);

            if (line.shouldBeChecked && line.hasContent) {
                if (line.length > lineLimit && !line.startsWithUnbreakable) {
                    log(line.lineNumber, MSG_TOO_LONG, lineLimit, line.length);
                }
                else if (canPullFromNextLine(i)) {
                    log(line.lineNumber, MSG_TOO_SHORT, lineLimit, line.length);
                }
            }
        }
    }

    /**
     * Processes the Javadoc tree starting from the root.
     *
     * @param root the root node of the Javadoc tree
     */
    private void processJavadocTree(DetailNode root) {
        for (DetailNode child = root.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            processNode(child);
        }
    }

    /**
     * Processes a single node in the Javadoc tree.
     *
     * @param node the node to process
     */
    private void processNode(DetailNode node) {
        switch (node.getType()) {
            case JavadocCommentsTokenTypes.NEWLINE:
                handleNewline(node);
                break;
            case JavadocCommentsTokenTypes.LEADING_ASTERISK:
                // Ignore leading asterisks
                break;
            case JavadocCommentsTokenTypes.TEXT:
                handleText(node);
                break;
            case JavadocCommentsTokenTypes.TAG_NAME:
            case JavadocCommentsTokenTypes.PARAMETER_NAME:
                handleTagContent(node);
                break;
            case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG:
                handleInlineTag(node);
                break;
            case JavadocCommentsTokenTypes.HTML_ELEMENT:
                handleHtmlElement(node);
                break;
            case JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG:
                handleBlockTag(node);
                break;
            default:
                processChildren(node);
                break;
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
        if (currentLine == null) {
            initCurrentLine(node.getLineNumber());
        }
        commitCurrentLine();
    }

    /**
     * Handles a block tag like @param or @return.
     *
     * @param node the block tag node
     */
    private void handleBlockTag(DetailNode node) {
        commitCurrentLine();
        initCurrentLine(node.getLineNumber());
        currentLine.isBlockTagStart = true;

        processChildren(node);

        // If block tag has no content after the tag itself, don't check it
        if (!currentLine.hasContentAfterUnbreakable) {
            currentLine.shouldBeChecked = false;
        }
    }

    /**
     * Handles a TAG_NAME or PARAMETER_NAME node within a block tag.
     *
     * @param node the tag content node
     */
    private void handleTagContent(DetailNode node) {
        initCurrentLine(node.getLineNumber());
        updateLineLength(node.getColumnNumber(), node.getText().length());

        if (!currentLine.hasContent) {
            currentLine.hasContent = true;
            currentLine.startsWithUnbreakable = true;
        }
    }

    /**
     * Handles a TEXT node.
     *
     * @param node the text node
     */
    private void handleText(DetailNode node) {
        final String text = node.getText();
        initCurrentLine(node.getLineNumber());
        updateLineLength(node.getColumnNumber(), text.length());

        final String trimmed = text.trim();
        if (!trimmed.isEmpty()) {
            processNonEmptyText(trimmed);
        }
    }

    /**
     * Processes non-empty text content, updating line state accordingly.
     *
     * @param trimmedText the trimmed text content
     */
    private void processNonEmptyText(String trimmedText) {
        if (!currentLine.hasContent) {
            currentLine.hasContent = true;
            if (URL_PATTERN.matcher(trimmedText).lookingAt()) {
                currentLine.startsWithUnbreakable = true;
                currentLine.firstWord = trimmedText;
            }
            else {
                setFirstWord(trimmedText);
            }
        }
        else {
            if (currentLine.startsWithUnbreakable) {
                currentLine.hasContentAfterUnbreakable = true;
            }
            if (currentLine.firstWord == null) {
                setFirstWord(trimmedText);
            }
        }
    }

    /**
     * Extracts and sets the first word from text for pull calculations.
     *
     * @param text text to extract from
     */
    private void setFirstWord(String text) {
        if (currentLine.firstWord == null) {
            final int spaceIndex = text.indexOf(' ');
            currentLine.firstWord = spaceIndex > 0 ? text.substring(0, spaceIndex) : text;
        }
    }

    /**
     * Handles an inline tag like {@code {@link ...}}.
     *
     * @param node the inline tag node
     */
    private void handleInlineTag(DetailNode node) {
        initCurrentLine(node.getLineNumber());

        final String tagText = collectAllText(node);
        updateLineLength(node.getColumnNumber(), tagText.length());

        if (!currentLine.hasContent) {
            currentLine.startsWithUnbreakable = true;
            currentLine.firstWord = tagText;
        }
        else {
            currentLine.hasContentAfterUnbreakable = true;
        }
        currentLine.hasContent = true;
    }

    /**
     * Handles an HTML element.
     *
     * @param node the HTML element node
     */
    private void handleHtmlElement(DetailNode node) {
        final String tagName = extractHtmlTagName(node);

        if ("pre".equalsIgnoreCase(tagName)) {
            insidePreBlock = !insidePreBlock;
        }

        initCurrentLine(node.getLineNumber());

        // Lines starting with structural tags are not checked
        if (!currentLine.hasContent && isStructuralTag(tagName)) {
            currentLine.shouldBeChecked = false;
        }

        final String visibleText = collectVisibleText(node);
        if (!visibleText.isEmpty()) {
            updateLineLength(node.getColumnNumber(), collectAllText(node).length());

            if (!isStructuralTag(tagName)) {
                currentLine.hasContent = true;
                setFirstWord(visibleText.trim());
            }
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

        for (int i = currentIndex + 1; i < lines.size(); i++) {
            final JavadocLine nextLine = lines.get(i);

            if (!nextLine.hasContent || nextLine.isBlockTagStart) {
                return false;
            }

            if (nextLine.firstWord != null) {
                final int potentialLength = currentLength + 1 + nextLine.firstWord.length();
                return potentialLength <= lineLimit;
            }

            return false;
        }

        return false;
    }

    /**
     * Initializes the current line if needed.
     *
     * @param lineNumber the source line number
     */
    private void initCurrentLine(int lineNumber) {
        if (currentLine == null || currentLine.lineNumber != lineNumber) {
            commitCurrentLine();
            currentLine = new JavadocLine(lineNumber, !insidePreBlock);
        }
    }

    /**
     * Commits the current line to the lines list and clears it.
     */
    private void commitCurrentLine() {
        if (currentLine != null) {
            lines.add(currentLine);
            currentLine = null;
        }
    }

    /**
     * Updates the line length based on column position and content length.
     * @param columnNumber the starting column (0-indexed)
     * @param length the length of the content
     */
    private void updateLineLength(int columnNumber, int length) {
        final int endColumn = columnNumber + length;
        if (endColumn > currentLine.length) {
            currentLine.length = endColumn;
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
     * Collects only visible text (TEXT nodes) from a node and its descendants.
     *
     * @param node the starting node
     * @return the visible text
     */
    private static String collectVisibleText(DetailNode node) {
        final StringBuilder builder = new StringBuilder();
        appendNodeText(node, builder, true);
        return builder.toString();
    }

    /**
     * Recursively appends text from nodes to a StringBuilder.
     *
     * @param node the node to process
     * @param builder the StringBuilder to append to
     * @param visibleOnly if true, only append TEXT nodes; otherwise append all leaf text
     */
    private static void appendNodeText(DetailNode node, StringBuilder builder, boolean visibleOnly) {
        final DetailNode firstChild = node.getFirstChild();
        if (firstChild == null) {
            if (!visibleOnly || node.getType() == JavadocCommentsTokenTypes.TEXT) {
                builder.append(node.getText());
            }
        }
        else {
            for (DetailNode child = firstChild; child != null; child = child.getNextSibling()) {
                appendNodeText(child, builder, visibleOnly);
            }
        }
    }

    /**
     * Extracts the HTML tag name from an HTML element node.
     *
     * @param node the HTML element node
     * @return the tag name, or empty string if not found
     */
    private static String extractHtmlTagName(DetailNode node) {
        for (DetailNode child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            final int type = child.getType();
            if (type == JavadocCommentsTokenTypes.HTML_TAG_START
                    || type == JavadocCommentsTokenTypes.HTML_TAG_END) {
                for (DetailNode tagChild = child.getFirstChild(); tagChild != null;
                        tagChild = tagChild.getNextSibling()) {
                    if (tagChild.getType() == JavadocCommentsTokenTypes.TAG_NAME) {
                        return tagChild.getText();
                    }
                }
            }
        }
        return "";
    }

    /**
     * Checks if a tag name represents a structural HTML element.
     *
     * @param tagName the tag name to check
     * @return true if the tag is structural
     */
    private static boolean isStructuralTag(String tagName) {
        return STRUCTURAL_TAGS.contains(tagName.toLowerCase());
    }

    /**
     * Represents a logical line in a Javadoc comment.
     */
    private static final class JavadocLine {

        /** The source line number. */
        private final int lineNumber;

        /** Whether the line should be checked for violations. */
        private boolean shouldBeChecked;

        /** The length of the line (end column). */
        private int length;

        /** Whether the line has meaningful content. */
        private boolean hasContent;

        /** Whether the line starts with an unbreakable element (tag or URL). */
        private boolean startsWithUnbreakable;

        /** Whether there is content after the unbreakable element. */
        private boolean hasContentAfterUnbreakable;

        /** The first word of the line, used for pull calculations. */
        private String firstWord;

        /** Whether this line starts a new block tag. */
        private boolean isBlockTagStart;

        /**
         * Creates a JavadocLine.
         *
         * @param lineNumber the source line number
         * @param shouldBeChecked whether this line should be validated
         */
        private JavadocLine(int lineNumber, boolean shouldBeChecked) {
            this.lineNumber = lineNumber;
            this.shouldBeChecked = shouldBeChecked;
        }
    }
}
