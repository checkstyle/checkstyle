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
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

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
@FileStatefulCheck
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

    /** Whether the target tag was found in the current Javadoc tree. */
    private boolean tagFound;

    /** Parent line number for the missing tag report in the current Javadoc tree. */
    private int parentLineNo;

    /**
     * Creates a new {@code WriteTagCheck} instance.
     */
    public WriteTagCheck() {
        // no code by default
    }

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

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 8.3
     * @propertySince 13.9.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
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
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST javadocComment = findJavadoc(ast);
        if (javadocComment != null) {
            parentLineNo = ast.getLineNo();
            super.visitToken(javadocComment);
        }
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        tagFound = false;
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String tagName = "@" + JavadocUtil.getTagName(ast);
        if (tagName.equals(tag)) {
            tagFound = true;
            final String content = getTagContent(ast);

            if (tagFormat == null || tagFormat.matcher(content).find()) {
                logTag(ast.getLineNumber(), tag, content);
            }
            else {
                log(ast.getLineNumber(), MSG_TAG_FORMAT, tag, tagFormat.pattern());
            }
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        if (tag != null && !tagFound) {
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

        return rawTextBuilder.toString().stripLeading();
    }

    /**
     * Finds the Javadoc comment associated with a structural AST node.
     *
     * @param ast the structural node (e.g., CLASS_DEF, METHOD_DEF)
     * @return the Javadoc block comment if found, or null
     */
    @Nullable
    private static DetailAST findJavadoc(DetailAST ast) {
        DetailAST cmt = ast.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
        if (cmt == null) {
            final DetailAST modifiers = NullUtil.notNull(ast.findFirstToken(TokenTypes.MODIFIERS));
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);

            final DetailAST annotation = modifiers.findFirstToken(TokenTypes.ANNOTATION);
            cmt = modifiers.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
            if (annotation != null) {
                cmt = annotation.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
            }
            if (cmt == null && type != null) {
                cmt = type.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
            }
        }
        return cmt;
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
