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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

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
public class WriteTagCheck
    extends AbstractCheck {

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

    /** Line split pattern. */
    private static final Pattern LINE_SPLIT_PATTERN = Pattern.compile("\\R");

    /** Compiled regexp to match tag. */
    private Pattern tagRegExp;
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
        tagRegExp = CommonUtil.createPattern(tag + "\\s*(.*$)");
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
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST javadoc = getJavadoc(ast);

        if (javadoc != null) {
            final String[] cmtLines = LINE_SPLIT_PATTERN
                    .split(JavadocUtil.getJavadocCommentContent(javadoc));

            checkTag(javadoc.getLineNo(),
                    javadoc.getLineNo() + countCommentLines(javadoc),
                    cmtLines);
        }
    }

    /**
     * Retrieves the Javadoc comment associated with a given AST node.
     *
     * @param ast the AST node (e.g., class, method, constructor) to search above.
     * @return the {@code DetailAST} representing the Javadoc comment if found and
     *          valid; {@code null} otherwise.
     */
    @Nullable
    private static DetailAST getJavadoc(DetailAST ast) {
        // Prefer Javadoc directly above the node
        DetailAST cmt = ast.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
        if (cmt == null) {
            // Check MODIFIERS and TYPE block for comments
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);

            cmt = modifiers.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
            if (cmt == null && type != null) {
                cmt = type.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
            }
        }

        final DetailAST javadoc;
        if (cmt != null && JavadocUtil.isJavadocComment(cmt)) {
            javadoc = cmt;
        }
        else {
            javadoc = null;
        }

        return javadoc;
    }

    /**
     * Counts the number of lines in a block comment.
     *
     * @param blockComment the AST node representing the block comment.
     * @return the number of lines in the comment.
     */
    private static int countCommentLines(DetailAST blockComment) {
        final String content = JavadocUtil.getBlockCommentContent(blockComment);
        return LINE_SPLIT_PATTERN.split(content).length;
    }

    /**
     * Validates the Javadoc comment against the configured requirements.
     *
     * @param astLineNo the line number of the type definition.
     * @param javadocLineNo the starting line number of the Javadoc comment block.
     * @param comment the lines of the Javadoc comment block.
     */
    private void checkTag(int astLineNo, int javadocLineNo, String... comment) {
        if (tagRegExp != null) {
            boolean hasTag = false;
            for (int i = 0; i < comment.length; i++) {
                final String commentValue = comment[i];
                final Matcher matcher = tagRegExp.matcher(commentValue);
                if (matcher.find()) {
                    hasTag = true;
                    final int contentStart = matcher.start(1);
                    final String content = commentValue.substring(contentStart);
                    if (tagFormat == null || tagFormat.matcher(content).find()) {
                        logTag(astLineNo + i, tag, content);
                    }
                    else {
                        log(astLineNo + i, MSG_TAG_FORMAT, tag, tagFormat.pattern());
                    }
                }
            }
            if (!hasTag) {
                log(javadocLineNo, MSG_MISSING_TAG, tag);
            }
        }
    }

    /**
     * Log a message.
     *
     * @param line the line number where the violation was found
     * @param tagName the javadoc tag to be logged
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
