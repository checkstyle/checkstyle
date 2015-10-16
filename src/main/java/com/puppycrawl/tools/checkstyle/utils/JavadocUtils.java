////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTags;

/**
 * Contains utility methods for working with Javadoc.
 * @author Lyle Hanson
 */
public final class JavadocUtils {
    /** Maps from a token name to value. */
    private static final ImmutableMap<String, Integer> TOKEN_NAME_TO_VALUE;
    /** Maps from a token value to name. */
    private static final String[] TOKEN_VALUE_TO_NAME;

    /** Exception message for unknown JavaDoc token id. */
    private static final String UNKNOWN_JAVADOC_TOKEN_ID_EXCEPTION_MESSAGE = "Unknown javadoc"
            + " token id. Given id: ";

    // Using reflection gets all token names and values from JavadocTokenTypes class
    // and saves to TOKEN_NAME_TO_VALUE and TOKEN_VALUE_TO_NAME collections.
    static {
        final ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        final Field[] fields = JavadocTokenTypes.class.getDeclaredFields();

        String[] tempTokenValueToName = ArrayUtils.EMPTY_STRING_ARRAY;

        for (final Field field : fields) {

            // Only process public int fields.
            if (!Modifier.isPublic(field.getModifiers())
                    || field.getType() != Integer.TYPE) {
                continue;
            }

            final String name = field.getName();

            final int tokenValue = TokenUtils.getIntFromField(field, name);
            builder.put(name, tokenValue);
            if (tokenValue > tempTokenValueToName.length - 1) {
                final String[] temp = new String[tokenValue + 1];
                System.arraycopy(tempTokenValueToName, 0, temp, 0, tempTokenValueToName.length);
                tempTokenValueToName = temp;
            }
            if (tokenValue == -1) {
                tempTokenValueToName[0] = name;
            }
            else {
                tempTokenValueToName[tokenValue] = name;
            }
        }

        TOKEN_NAME_TO_VALUE = builder.build();
        TOKEN_VALUE_TO_NAME = tempTokenValueToName;
    }

    /** Prevent instantiation. */
    private JavadocUtils() {
    }

    /**
     * Gets validTags from a given piece of Javadoc.
     * @param textBlock
     *        the Javadoc comment to process.
     * @param tagType
     *        the type of validTags we're interested in
     * @return all standalone validTags from the given javadoc.
     */
    public static JavadocTags getJavadocTags(TextBlock textBlock,
            JavadocTagType tagType) {
        final String[] text = textBlock.getText();
        final List<JavadocTag> tags = Lists.newArrayList();
        final List<InvalidJavadocTag> invalidTags = Lists.newArrayList();
        Pattern blockTagPattern = Pattern.compile("/\\*{2,}\\s*@(\\p{Alpha}+)\\s");
        for (int i = 0; i < text.length; i++) {
            final String textValue = text[i];
            final Matcher blockTagMatcher = blockTagPattern.matcher(textValue);
            if ((tagType == JavadocTagType.ALL || tagType == JavadocTagType.BLOCK)
                    && blockTagMatcher.find()) {
                final String tagName = blockTagMatcher.group(1);
                String content = textValue.substring(blockTagMatcher.end(1));
                if (content.endsWith("*/")) {
                    content = content.substring(0, content.length() - 2);
                }
                final int line = textBlock.getStartLineNo() + i;
                int col = blockTagMatcher.start(1) - 1;
                if (i == 0) {
                    col += textBlock.getStartColNo();
                }
                if (JavadocTagInfo.isValidName(tagName)) {
                    tags.add(
                            new JavadocTag(line, col, tagName, content.trim()));
                }
                else {
                    invalidTags.add(new InvalidJavadocTag(line, col, tagName));
                }
            }
            // No block tag, so look for inline validTags
            else if (tagType == JavadocTagType.ALL || tagType == JavadocTagType.INLINE) {
                lookForInlineTags(textBlock, i, tags, invalidTags);
            }
            blockTagPattern = Pattern.compile("^\\s*\\**\\s*@(\\p{Alpha}+)\\s");
        }
        return new JavadocTags(tags, invalidTags);
    }

    /**
     * Looks for inline tags in comment and adds them to the proper tags collection.
     * @param comment comment text block
     * @param lineNumber line number in the comment
     * @param validTags collection of valid tags
     * @param invalidTags collection of invalid tags
     */
    private static void lookForInlineTags(TextBlock comment, int lineNumber,
            final List<JavadocTag> validTags, final List<InvalidJavadocTag> invalidTags) {
        final String text = comment.getText()[lineNumber];
        // Match Javadoc text after comment characters
        final Pattern commentPattern = Pattern.compile("^\\s*(?:/\\*{2,}|\\*+)\\s*(.*)");
        final Matcher commentMatcher = commentPattern.matcher(text);
        final String commentContents;

        // offset including comment characters
        final int commentOffset;

        if (commentMatcher.find()) {
            commentContents = commentMatcher.group(1);
            commentOffset = commentMatcher.start(1) - 1;
        }
        else {
            // No leading asterisks, still valid
            commentContents = text;
            commentOffset = 0;
        }
        final Pattern tagPattern = Pattern.compile(".*?\\{@(\\p{Alpha}+)\\s+(.*?)\\}");
        final Matcher tagMatcher = tagPattern.matcher(commentContents);
        while (tagMatcher.find()) {
            final String tagName = tagMatcher.group(1);
            final String tagValue = tagMatcher.group(2).trim();
            final int line = comment.getStartLineNo() + lineNumber;
            int col = commentOffset + tagMatcher.start(1) - 1;
            if (lineNumber == 0) {
                col += comment.getStartColNo();
            }
            if (JavadocTagInfo.isValidName(tagName)) {
                validTags.add(new JavadocTag(line, col, tagName,
                        tagValue));
            }
            else {
                invalidTags.add(new InvalidJavadocTag(line, col,
                        tagName));
            }
        }
    }

    /**
     * The type of Javadoc tag we want returned.
     */
    public enum JavadocTagType {
        /** Block type. */
        BLOCK,
        /** Inline type. */
        INLINE,
        /** All validTags. */
        ALL
    }

    /**
     * Checks that commentContent starts with '*' javadoc comment identifier.
     * @param commentContent
     *        content of block comment
     * @return true if commentContent starts with '*' javadoc comment
     *         identifier.
     */
    public static boolean isJavadocComment(String commentContent) {
        boolean result = false;

        if (!commentContent.isEmpty()) {
            final char docCommentIdentificator = commentContent.charAt(0);
            result = docCommentIdentificator == '*';
        }

        return result;
    }

    /**
     * Checks block comment content starts with '*' javadoc comment identifier.
     * @param blockCommentBegin
     *        block comment AST
     * @return true if block comment content starts with '*' javadoc comment
     *         identifier.
     */
    public static boolean isJavadocComment(DetailAST blockCommentBegin) {
        final String commentContent = getBlockCommentContent(blockCommentBegin);
        return isJavadocComment(commentContent);
    }

    /**
     * Gets content of block comment.
     * @param blockCommentBegin
     *        block comment AST.
     * @return content of block comment.
     */
    private static String getBlockCommentContent(DetailAST blockCommentBegin) {
        final DetailAST commentContent = blockCommentBegin.getFirstChild();
        return commentContent.getText();
    }

    /**
     * Get content of Javadoc comment.
     * @param javadocCommentBegin
     *        Javadoc comment AST
     * @return content of Javadoc comment.
     */
    public static String getJavadocCommentContent(DetailAST javadocCommentBegin) {
        final DetailAST commentContent = javadocCommentBegin.getFirstChild();
        return commentContent.getText().substring(1);
    }

    /**
     * Returns the first child token that has a specified type.
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
     * Checks whether node contains any node of specified type among children on any deep level.
     *
     * @param node DetailNode
     * @param type token type
     * @return true if node contains any node of type type among children on any deep level.
     */
    public static boolean containsInBranch(DetailNode node, int type) {
        DetailNode curNode = node;
        while (true) {

            if (type == curNode.getType()) {
                return true;
            }

            DetailNode toVisit = getFirstChild(curNode);
            while (curNode != null && toVisit == null) {
                toVisit = getNextSibling(curNode);
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }

            if (curNode == toVisit) {
                break;
            }

            curNode = toVisit;
        }

        return false;
    }

    /**
     * Gets next sibling of specified node.
     *
     * @param node DetailNode
     * @return next sibling.
     */
    public static DetailNode getNextSibling(DetailNode node) {
        final DetailNode parent = node.getParent();
        if (parent != null) {
            final int nextSiblingIndex = node.getIndex() + 1;
            final DetailNode[] children = parent.getChildren();
            if (nextSiblingIndex <= children.length - 1) {
                return children[nextSiblingIndex];
            }
        }
        return null;
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
     * @param node DetailNode
     * @return previous sibling
     */
    public static DetailNode getPreviousSibling(DetailNode node) {
        final DetailNode parent = node.getParent();
        final int previousSiblingIndex = node.getIndex() - 1;
        final DetailNode[] children = parent.getChildren();
        if (previousSiblingIndex >= 0) {
            return children[previousSiblingIndex];
        }
        return null;
    }

    /**
     * Returns the name of a token for a given ID.
     * @param id
     *        the ID of the token name to get
     * @return a token name
     */
    public static String getTokenName(int id) {
        if (id == JavadocTokenTypes.EOF) {
            return "EOF";
        }
        if (id > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException(UNKNOWN_JAVADOC_TOKEN_ID_EXCEPTION_MESSAGE + id);
        }
        final String name = TOKEN_VALUE_TO_NAME[id];
        if (name == null) {
            throw new IllegalArgumentException(UNKNOWN_JAVADOC_TOKEN_ID_EXCEPTION_MESSAGE + id);
        }
        return name;
    }

    /**
     * Returns the ID of a token for a given name.
     * @param name
     *        the name of the token ID to get
     * @return a token ID
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
        String javadocTagName;
        if (javadocTagSection.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
            javadocTagName = getNextSibling(
                    getFirstChild(javadocTagSection)).getText();
        }
        else {
            javadocTagName = getFirstChild(javadocTagSection).getText();
        }
        return javadocTagName;
    }

}
