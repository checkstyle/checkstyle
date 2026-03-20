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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Requires user defined Javadoc tag to be present in Javadoc comment with defined format.
 * To define the format for a tag, set property tagFormat to a regular expression.
 * Property tagSeverity is used for severity of events when the tag exists.
 * No violation reported in case there is no javadoc.
 * </div>
 *
 * @since 4.2
 */
@StatelessCheck
public class WriteTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_TAG = "type.missingTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRITE_TAG = "javadoc.writeTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_FORMAT = "type.tagFormat";

    /** Specify the regexp to match tag content. */
    private Pattern tagFormat;

    /** Specify the name of tag. */
    private String tag;

    /** Specify the severity level when tag is found and printed. */
    private SeverityLevel tagSeverity = SeverityLevel.INFO;

    /**
     * Setter to specify the name of tag.
     *
     * @param tag tag to check
     * @since 4.2
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Setter to specify the regexp to match tag content.
     *
     * @param pattern a {@code String} value
     * @since 4.2
     */
    public void setTagFormat(Pattern pattern) {
        tagFormat = pattern;
    }

    /**
     * Setter to specify the severity level when tag is found and printed.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     * @since 4.2
     */
    public final void setTagSeverity(SeverityLevel severity) {
        tagSeverity = severity;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return super.getRequiredTokens();
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        super.visitToken(ast);
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST parent = getParent(getBlockCommentAst());

        if (tag != null
            && getParentTokenNamesToCheck().contains(TokenUtil.getTokenName(parent.getType()))) {
            checkTag(ast, parent.getLineNo());
        }
    }

    /**
     * Returns a set of token names to check their Javadoc tags.
     *
     * @return Custom token names if specified by user; default token names otherwise.
     */
    private Set<String> getParentTokenNamesToCheck() {
        Set<String> checkTokens = getTokenNames();
        if (checkTokens.isEmpty()) {
            checkTokens = IntStream.of(getDefaultTokens())
                .mapToObj(TokenUtil::getTokenName)
                .collect(Collectors.toUnmodifiableSet());
        }
        return checkTokens;
    }

    /**
     * Returns the parent node of a block comment.
     *
     * @param commentBlock The block comment.
     * @return The parent node.
     */
    private static DetailAST getParent(DetailAST commentBlock) {
        final DetailAST parentNode = commentBlock.getParent();
        DetailAST result = parentNode;
        if (result.getType() == TokenTypes.TYPE || result.getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent();
        }
        else if (parentNode.getParent() != null
            && parentNode.getParent().getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent().getParent();
        }
        return result;
    }

    /**
     * Validates the Javadoc comment against the configured requirements.
     *
     * @param javadoc Javadoc root node to check.
     * @param parentLineNo The line number of the parent of the Javadoc.
     */
    private void checkTag(DetailNode javadoc, int parentLineNo) {
        boolean hasTag = false;
        DetailNode node = javadoc.getFirstChild();

        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                final String tagText = "@" + JavadocUtil.getTagName(node);
                if (tagText.equals(tag)) {
                    hasTag = true;
                    final String content = getTagContent(node);

                    if (tagFormat == null || tagFormat.matcher(content).find()) {
                        logTag(node.getLineNumber(), tag, content);
                    }
                    else {
                        log(node.getLineNumber(), MSG_TAG_FORMAT, tag, tagFormat.pattern());
                    }
                }
            }
            node = node.getNextSibling();
        }
        if (!hasTag) {
            log(parentLineNo, MSG_MISSING_TAG, tag);
        }
    }

    /**
     * Returns the raw content of the tag.
     *
     * @param javadocBlockTagNode The node representing a Javadoc block tag.
     *       This node must be of type {@link JavadocCommentsTokenTypes#JAVADOC_BLOCK_TAG}
     * @return The raw content of the tag.
     */
    private static String getTagContent(DetailNode javadocBlockTagNode) {
        final DetailNode tagNodeNextSibling = JavadocUtil.findFirstToken(
            javadocBlockTagNode.getFirstChild(),
            JavadocCommentsTokenTypes.TAG_NAME).getNextSibling();

        final int stringBuilderCapacity = 128;
        final StringBuilder rawTextBuilder = new StringBuilder(stringBuilderCapacity);
        if (tagNodeNextSibling != null) {
            // DFS to extract texts of all leaf nodes
            final Deque<DetailNode> stack = new ArrayDeque<>();
            stack.push(tagNodeNextSibling);

            while (!stack.isEmpty()) {
                final DetailNode currentNode = stack.pop();

                // append text if node is a leaf
                if (currentNode.getFirstChild() == null) {
                    rawTextBuilder.append(currentNode.getText());
                }

                final DetailNode nextSibling = currentNode.getNextSibling();
                final DetailNode firstChild = currentNode.getFirstChild();

                if (nextSibling != null) {
                    stack.push(nextSibling);
                }
                if (firstChild != null) {
                    stack.push(firstChild);
                }
            }
        }

        return rawTextBuilder
            .toString()
            .replaceAll("^\\s+", "");
    }

    /**
     * Log a message.
     *
     * @param line the line number where the violation was found
     * @param tagName the Javadoc tag to be logged
     * @param tagValue the contents of the tag
     *
     * @see java.text.MessageFormat
     */
    private void logTag(int line, String tagName, String tagValue) {
        final String originalSeverity = getSeverity();
        setSeverity(tagSeverity.getName());

        log(line, MSG_WRITE_TAG, tagName, tagValue);

        setSeverity(originalSeverity);
    }
}
