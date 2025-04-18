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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

import static com.puppycrawl.tools.checkstyle.utils.JavadocUtil.isJavadocComment;
import static com.puppycrawl.tools.checkstyle.utils.JavadocUtil.getBlockCommentContent;

/**
 * <div>
 * Requires user defined Javadoc tag to be present in Javadoc comment with defined format.
 * To define the format for a tag, set property tagFormat to a regular expression.
 * Property tagSeverity is used for severity of events when the tag exists.
 * No violation reported in case there is no javadoc.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code tag} - Specify the name of tag.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tagFormat} - Specify the regexp to match tag content.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tagSeverity} - Specify the severity level when tag is found and printed.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.SeverityLevel}.
 * Default value is {@code info}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BLOCK_COMMENT_BEGIN">
 * BLOCK_COMMENT_BEGIN</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.writeTag}
 * </li>
 * <li>
 * {@code type.missingTag}
 * </li>
 * <li>
 * {@code type.tagFormat}
 * </li>
 * </ul>
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

    /** Compiled regexp to match tag. */
    private Pattern tagRegExp;
    /** Specify the regexp to match tag content. */
    private Pattern tagFormat;

    /** Line split pattern. */
    private static final Pattern LINE_SPLIT_PATTERN = Pattern.compile("\\R");

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
            TokenTypes.BLOCK_COMMENT_BEGIN
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
            TokenTypes.BLOCK_COMMENT_BEGIN
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
        if (isJavadocComment(ast)) {
            final int associatedType = associatedTypeDef(ast);

            if (!isTokenConfigured(associatedType)) {
                return;
            }

            final String commentContent = getBlockCommentContent(ast);
            final String[] cmtLines = LINE_SPLIT_PATTERN.split(commentContent);

            checkTag(ast.getLineNo(),
                    ast.getLineNo() + countCommentLines(ast), cmtLines);
        }
    }

    /**
     * Determines the token type (e.g., class, method, constructor) that a Javadoc
     * block comment is associated with.
     *
     * @param ast the AST node representing the block comment.
     * @return the token type ID (such as {@code TokenTypes.METHOD_DEF},
     *         {@code TokenTypes.CLASS_DEF}, etc.), or
     *         {@code 0} if no associated definition is found.
     */
    private int associatedTypeDef(DetailAST ast) {
        int type = 0;

        if (BlockCommentPosition.isOnAnnotationField(ast)) {
            type = TokenTypes.ANNOTATION_FIELD_DEF;
        } else if (BlockCommentPosition.isOnMethod(ast)) {
            type = TokenTypes.METHOD_DEF;
        } else if (BlockCommentPosition.isOnClass(ast)) {
            type = TokenTypes.CLASS_DEF;
        } else if (BlockCommentPosition.isOnEnum(ast)) {
            type = TokenTypes.ENUM_DEF;
        } else if (BlockCommentPosition.isOnRecord(ast)) {
            type = TokenTypes.RECORD_DEF;
        } else if (BlockCommentPosition.isOnAnnotationDef(ast)) {
            type = TokenTypes.ANNOTATION_DEF;
        } else if (BlockCommentPosition.isOnConstructor(ast)) {
            type = TokenTypes.CTOR_DEF;
        } else if (BlockCommentPosition.isOnInterface(ast)) {
            type = TokenTypes.INTERFACE_DEF;
        } else if (BlockCommentPosition.isOnEnumConstant(ast)) {
            type = TokenTypes.ENUM_CONSTANT_DEF;
        } else if (BlockCommentPosition.isOnCompactConstructor(ast)) {
            type = TokenTypes.COMPACT_CTOR_DEF;
        }

        return type;
    }

    /**
     * Checks whether the given token type is configured for this check.
     *
     * @param type the token type ID to check.
     * @return {@code true} if the token type is accepted by this check,
     *         {@code false} otherwise.
     */
    private boolean isTokenConfigured(int type) {
        final Set<String> tokens = getTokenNames();
        return tokens.isEmpty()
                ? Arrays.stream(getDefaultTokens()).anyMatch(t -> t == type)
                : tokens.contains(TokenUtil.getTokenName(type));
    }

    /**
     * Counts the number of lines in a block comment.
     *
     * @param blockComment the AST node representing the block comment.
     * @return the number of lines in the comment.
     */
    private int countCommentLines(DetailAST blockComment) {
        final String content = getBlockCommentContent(blockComment);
        return LINE_SPLIT_PATTERN.split(content).length;
    }

    /**
     * Validates the Javadoc comment against the configured requirements.
     *
     * @param javadocLineNo the starting line number of the Javadoc comment block.
     * @param astLineNo the line number of the type definition.
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
     * @see MessageFormat
     */
    private void logTag(int line, String tagName, String tagValue) {
        final String originalSeverity = getSeverity();
        setSeverity(tagSeverity.getName());

        log(line, MSG_WRITE_TAG, tagName, tagValue);

        setSeverity(originalSeverity);
    }
}
