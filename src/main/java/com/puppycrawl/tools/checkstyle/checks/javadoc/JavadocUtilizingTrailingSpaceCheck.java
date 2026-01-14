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
        lines.clear();
        processJavadocTree(rootAst);
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        validateLines();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
    }

    /**
     * Validates all collected lines for violations.
     */
    private void validateLines() {
        for (int i = 0; i < lines.size(); i++) {
            final JavadocLine line = lines.get(i);

            if (line.hasContent) {
                boolean tooLongCondition = line.length > lineLimit && !line.startsWithUnbreakable;
                if (tooLongCondition) {
                    log(line.lineNumber, MSG_TOO_LONG, lineLimit, line.length);
                }
                boolean tooShortCondition = false;

                if(canPullFromNextLine(i))
                {
                    tooShortCondition = true;
                }
                
                if (tooShortCondition) {
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
        DetailNode child = root.getFirstChild();
        while (child != null) {
            processNode(child);
            child = child.getNextSibling();
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
            case JavadocCommentsTokenTypes.USES_BLOCK_TAG:
                handleBlockTag(node);
                break;
            case JavadocCommentsTokenTypes.JAVADOC_CONTENT:
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
        initCurrentLine(node.getLineNumber());
    }

    /**
     * Handles a block tag like @param or @return.
     *
     * @param node the block tag node
     */
    private void handleBlockTag(DetailNode node) {
        processChildren(node);
    }

    /**
     * Handles a TAG_NAME or PARAMETER_NAME node within a block tag.
     *
     * @param node the tag content node
     */
    private void handleTagContent(DetailNode node) {
        updateLineLength(node.getColumnNumber(), node.getText().length());

        if (!currentLine.hasContent) {
            currentLine.hasContent = true;
            currentLine.startsWithUnbreakable = (node.getType() == JavadocCommentsTokenTypes.TAG_NAME);
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
            } else {
                setFirstWord(trimmedText);
            }
        } else {
            setFirstWord(trimmedText);
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
            currentLine.firstWord = text;
            if (spaceIndex > 0) {
                currentLine.firstWord = text.substring(0, spaceIndex);
            }
        }
    }

    /**
     * Handles an inline tag like {@code {@link ...}}.
     *
     * @param node the inline tag node
    */
    private void handleInlineTag(DetailNode node) {
        final String tagText = collectAllText(node);
        if (!tagText.isEmpty()) {
            updateLineLength(node.getColumnNumber(), tagText.length());

            if (!currentLine.hasContent) {
                currentLine.startsWithUnbreakable = true;
            }
        }
    }

    /**
     * Handles an HTML element.
     *
     * @param node the HTML element node
     */
    private void handleHtmlElement(DetailNode node) {
        initCurrentLine(node.getLineNumber());
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

            if (nextLine.startsWithUnbreakable) {
                return false;
            }

            if (nextLine.firstWord != null) {
                final int potentialLength = currentLength + 1 + nextLine.firstWord.length();
                boolean canPull = potentialLength <= lineLimit;
                return canPull;
            }
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
     * Recursively appends text from nodes to a StringBuilder.
     *
     * @param node the node to process
     * @param builder the StringBuilder to append to
     * @param visibleOnly if true, only append TEXT nodes; otherwise append all leaf text
     */
    private static void appendNodeText(DetailNode node, StringBuilder builder, boolean visibleOnly) {
        final DetailNode firstChild = node.getFirstChild();
        if (firstChild == null) {
            if (!visibleOnly) {
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
