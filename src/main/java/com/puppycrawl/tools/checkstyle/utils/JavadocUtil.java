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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTags;
import com.puppycrawl.tools.checkstyle.checks.javadoc.utils.BlockTagUtil;
import com.puppycrawl.tools.checkstyle.checks.javadoc.utils.InlineTagUtil;
import com.puppycrawl.tools.checkstyle.checks.javadoc.utils.TagInfo;

/**
 * Contains utility methods for working with Javadoc.
 */
public final class JavadocUtil {

    /**
     * The type of Javadoc tag we want returned.
     */
    public enum JavadocTagType {

        /** Block type. */
        BLOCK,
        /** Inline type. */
        INLINE,
        /** All validTags. */
        ALL,

    }

    /** Maps from a token name to value. */
    private static final Map<String, Integer> TOKEN_NAME_TO_VALUE;
    /** Maps from a token value to name. */
    private static final Map<Integer, String> TOKEN_VALUE_TO_NAME;

    /** Exception message for unknown JavaDoc token id. */
    private static final String UNKNOWN_JAVADOC_TOKEN_ID_EXCEPTION_MESSAGE = "Unknown javadoc"
            + " token id. Given id: ";

    /** Newline pattern. */
    private static final Pattern NEWLINE = Pattern.compile("\n");

    /** Return pattern. */
    private static final Pattern RETURN = Pattern.compile("\r");

    /** Tab pattern. */
    private static final Pattern TAB = Pattern.compile("\t");

    // initialise the constants
    static {
        TOKEN_NAME_TO_VALUE = TokenUtil.nameToValueMapFromPublicIntFields(JavadocTokenTypes.class);
        TOKEN_VALUE_TO_NAME = TokenUtil.invertMap(TOKEN_NAME_TO_VALUE);
    }

    /** Prevent instantiation. */
    private JavadocUtil() {
    }

    /**
     * Gets validTags from a given piece of Javadoc.
     *
     * @param textBlock
     *        the Javadoc comment to process.
     * @param tagType
     *        the type of validTags we're interested in
     * @return all standalone validTags from the given javadoc.
     */
    public static JavadocTags getJavadocTags(TextBlock textBlock,
            JavadocTagType tagType) {
        final List<TagInfo> tags = new ArrayList<>();
        final boolean isBlockTags = tagType == JavadocTagType.ALL
                                        || tagType == JavadocTagType.BLOCK;
        if (isBlockTags) {
            tags.addAll(BlockTagUtil.extractBlockTags(textBlock.getText()));
        }
        final boolean isInlineTags = tagType == JavadocTagType.ALL
                                        || tagType == JavadocTagType.INLINE;
        if (isInlineTags) {
            tags.addAll(InlineTagUtil.extractInlineTags(textBlock.getText()));
        }

        final List<JavadocTag> validTags = new ArrayList<>();
        final List<InvalidJavadocTag> invalidTags = new ArrayList<>();

        for (TagInfo tag : tags) {
            final int col = tag.getPosition().getColumn();

            // Add the starting line of the comment to the line number to get the actual line number
            // in the source.
            // Lines are one-indexed, so need an off-by-one correction.
            final int line = textBlock.getStartLineNo() + tag.getPosition().getLine() - 1;

            if (JavadocTagInfo.isValidName(tag.getName())) {
                validTags.add(
                    new JavadocTag(line, col, tag.getName(), tag.getValue()));
            }
            else {
                invalidTags.add(new InvalidJavadocTag(line, col, tag.getName()));
            }
        }

        return new JavadocTags(validTags, invalidTags);
    }

    /**
     * Checks that commentContent starts with '*' javadoc comment identifier.
     *
     * @param commentContent
     *        content of block comment
     * @return true if commentContent starts with '*' javadoc comment
     *         identifier.
     */
    public static boolean isJavadocComment(String commentContent) {
        boolean result = false;

        if (!commentContent.isEmpty()) {
            final char docCommentIdentifier = commentContent.charAt(0);
            result = docCommentIdentifier == '*';
        }

        return result;
    }

    /**
     * Checks block comment content starts with '*' javadoc comment identifier.
     *
     * @param blockCommentBegin
     *        block comment AST
     * @return true if block comment content starts with '*' javadoc comment
     *         identifier.
     */
    public static boolean isJavadocComment(DetailAST blockCommentBegin) {
        final String commentContent = getBlockCommentContent(blockCommentBegin);
        return isJavadocComment(commentContent) && isCorrectJavadocPosition(blockCommentBegin);
    }

    /**
     * Gets content of block comment.
     *
     * @param blockCommentBegin
     *        block comment AST.
     * @return content of block comment.
     */
    public static String getBlockCommentContent(DetailAST blockCommentBegin) {
        final DetailAST commentContent = blockCommentBegin.getFirstChild();
        return commentContent.getText();
    }

    /**
     * Get content of Javadoc comment.
     *
     * @param javadocCommentBegin
     *        Javadoc comment AST
     * @return content of Javadoc comment.
     */
    public static String getJavadocCommentContent(DetailAST javadocCommentBegin) {
        final DetailAST commentContent = javadocCommentBegin.getFirstChild();
        return commentContent.getText().substring(1);
    }

    /**
     * Searches for a Javadoc comment node within the given AST.
     *
     * <p>This method uses a stack to simulate depth-first traversal
     * and returns the first Javadoc comment node it finds.</p>
     *
     * @param ast the root AST node to start the search from.
     * @return the {@code DetailAST} node representing the Javadoc comment,
     *     or {@code null} if none is found.
     */
    public static DetailAST findJavadocComment(DetailAST ast) {
        DetailAST result = null;

        if (ast != null) {
            Deque<DetailAST> stack = new ArrayDeque<>();
            stack.push(ast);

            while (!stack.isEmpty() && result == null) {
                DetailAST current = stack.pop();

                if (current.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                        && JavadocUtil.isJavadocComment(current)) {
                    result = current;
                }
                else {
                    DetailAST child = current.getLastChild();
                    while (child != null) {
                        stack.push(child);
                        child = child.getPreviousSibling();
                    }
                }
            }
        }

        return result;
    }

    /**
     * Returns the first child token that has a specified type.
     *
     * @param detailNode
     *        Javadoc AST node
     * @param type
     *        the token type to match
     * @return the matching token, or null if no match
     */
    public static DetailNode findFirstToken(DetailNode detailNode, int type) {
        DetailNode returnValue = null;
        DetailNode node = getFirstChild(detailNode);
        while (node != null) {
            if (node.getType() == type) {
                returnValue = node;
                break;
            }
            node = getNextSibling(node);
        }
        return returnValue;
    }

    /**
     * Gets first child node of specified node.
     *
     * @param node DetailNode
     * @return first child
     */
    public static DetailNode getFirstChild(DetailNode node) {
        DetailNode resultNode = null;

        if (node.getChildren().length > 0) {
            resultNode = node.getChildren()[0];
        }
        return resultNode;
    }

    /**
     * Gets next sibling of specified node.
     *
     * @param node DetailNode
     * @return next sibling.
     */
    public static DetailNode getNextSibling(DetailNode node) {
        DetailNode nextSibling = null;
        final DetailNode parent = node.getParent();
        if (parent != null) {
            final int nextSiblingIndex = node.getIndex() + 1;
            final DetailNode[] children = parent.getChildren();
            if (nextSiblingIndex <= children.length - 1) {
                nextSibling = children[nextSiblingIndex];
            }
        }
        return nextSibling;
    }

    /**
     * Gets next sibling of specified node with the specified type.
     *
     * @param node DetailNode
     * @param tokenType javadoc token type
     * @return next sibling.
     */
    public static DetailNode getNextSibling(DetailNode node, int tokenType) {
        DetailNode nextSibling = getNextSibling(node);
        while (nextSibling != null && nextSibling.getType() != tokenType) {
            nextSibling = getNextSibling(nextSibling);
        }
        return nextSibling;
    }

    /**
     * Gets previous sibling of specified node.
     *
     * @param node DetailNode
     * @return previous sibling
     */
    public static DetailNode getPreviousSibling(DetailNode node) {
        DetailNode previousSibling = null;
        final int previousSiblingIndex = node.getIndex() - 1;
        if (previousSiblingIndex >= 0) {
            final DetailNode parent = node.getParent();
            final DetailNode[] children = parent.getChildren();
            previousSibling = children[previousSiblingIndex];
        }
        return previousSibling;
    }

    /**
     * Returns the name of a token for a given ID.
     *
     * @param id
     *        the ID of the token name to get
     * @return a token name
     * @throws IllegalArgumentException if an unknown token ID was specified.
     */
    public static String getTokenName(int id) {
        final String name = TOKEN_VALUE_TO_NAME.get(id);
        if (name == null) {
            throw new IllegalArgumentException(UNKNOWN_JAVADOC_TOKEN_ID_EXCEPTION_MESSAGE + id);
        }
        return name;
    }

    /**
     * Returns the ID of a token for a given name.
     *
     * @param name
     *        the name of the token ID to get
     * @return a token ID
     * @throws IllegalArgumentException if an unknown token name was specified.
     */
    public static int getTokenId(String name) {
        final Integer id = TOKEN_NAME_TO_VALUE.get(name);
        if (id == null) {
            throw new IllegalArgumentException("Unknown javadoc token name. Given name " + name);
        }
        return id;
    }

    /**
     * Gets tag name from javadocTagSection.
     *
     * @param javadocTagSection to get tag name from.
     * @return name, of the javadocTagSection's tag.
     */
    public static String getTagName(DetailNode javadocTagSection) {
        final String javadocTagName;
        if (javadocTagSection.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
            javadocTagName = getNextSibling(
                    getFirstChild(javadocTagSection)).getText();
        }
        else {
            javadocTagName = getFirstChild(javadocTagSection).getText();
        }
        return javadocTagName;
    }

    /**
     * Replace all control chars with escaped symbols.
     *
     * @param text the String to process.
     * @return the processed String with all control chars escaped.
     */
    public static String escapeAllControlChars(String text) {
        final String textWithoutNewlines = NEWLINE.matcher(text).replaceAll("\\\\n");
        final String textWithoutReturns = RETURN.matcher(textWithoutNewlines).replaceAll("\\\\r");
        return TAB.matcher(textWithoutReturns).replaceAll("\\\\t");
    }

    /**
     * Checks Javadoc comment it's in right place.
     *
     * <p>From Javadoc util documentation:
     * "Placement of comments - Documentation comments are recognized only when placed
     * immediately before class, interface, constructor, method, field or annotation field
     * declarations -- see the class example, method example, and field example.
     * Documentation comments placed in the body of a method are ignored."</p>
     *
     * <p>If there are many documentation comments per declaration statement,
     * only the last one will be recognized.</p>
     *
     * @param blockComment Block comment AST
     * @return true if Javadoc is in right place
     * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html">
     *     Javadoc util documentation</a>
     */
    public static boolean isCorrectJavadocPosition(DetailAST blockComment) {
        // We must be sure that after this one there are no other documentation comments.
        DetailAST sibling = blockComment.getNextSibling();
        while (sibling != null) {
            if (sibling.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                if (isJavadocComment(getBlockCommentContent(sibling))) {
                    // Found another javadoc comment, so this one should be ignored.
                    break;
                }
                sibling = sibling.getNextSibling();
            }
            else if (sibling.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
                sibling = sibling.getNextSibling();
            }
            else {
                // Annotation, declaration or modifier is here. Do not check further.
                sibling = null;
            }
        }
        return sibling == null
            && (BlockCommentPosition.isOnType(blockComment)
                || BlockCommentPosition.isOnMember(blockComment)
                || BlockCommentPosition.isOnPackage(blockComment));
    }

}
