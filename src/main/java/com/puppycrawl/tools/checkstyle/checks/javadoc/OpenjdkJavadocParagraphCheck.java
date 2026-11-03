///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Checks paragraph formatting required by the OpenJDK Java Style Guidelines.
 *
 * Paragraph tags must be standalone, the first paragraph must not start with a tag,
 * paragraphs must be separated with paragraph tags, and closing paragraph tags are forbidden.
 *
 * @since 14.4.0
 */
@StatelessCheck
public class OpenjdkJavadocParagraphCheck extends AbstractCheck {

    /** Message key for a missing paragraph tag. */
    public static final String MSG_MISSING_TAG = "openjdk.javadoc.paragraph.missing";

    /** Message key for a paragraph tag on the first paragraph. */
    public static final String MSG_FIRST_TAG = "openjdk.javadoc.paragraph.first";

    /** Message key for a paragraph tag that is not standalone. */
    public static final String MSG_TAG_NOT_STANDALONE =
            "openjdk.javadoc.paragraph.not.standalone";

    /** Message key for a closing paragraph tag. */
    public static final String MSG_CLOSING_TAG = "openjdk.javadoc.paragraph.closing";

    private static final Pattern CLOSING_TAG = Pattern.compile("</p\\s*>");

    private static final Set<String> BLOCK_TAGS =
            Set.of("address", "blockquote", "div", "dl", "h1", "h2", "h3", "h4",
                    "h5", "h6", "hr", "ol", "pre", "table", "ul");

    /**
     * Creates a new {@code OpenjdkJavadocParagraphCheck} instance.
     */
    public OpenjdkJavadocParagraphCheck() {
        // no code by default
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (JavadocUtil.isJavadocComment(ast)) {
            final String commentContent = JavadocUtil.getJavadocCommentContent(ast);
            final String[] lines = commentContent.split("\\R", -1);
            boolean hasText = false;
            boolean blankLineAfterText = false;
            for (int index = 0; index < lines.length; index++) {
                final String line = lines[index].trim();
                String content = line;
                if (line.startsWith("*")) {
                    content = line.substring(1).trim();
                }
                final int lineNumber = ast.getLineNo() + index;

                if (CLOSING_TAG.matcher(content).find()) {
                    log(lineNumber, MSG_CLOSING_TAG);
                }
                if (content.startsWith("<p>")) {
                    if (!hasText) {
                        log(lineNumber, MSG_FIRST_TAG);
                    }
                    if (!content.equals("<p>")) {
                        log(lineNumber, MSG_TAG_NOT_STANDALONE);
                    }
                    blankLineAfterText = false;
                }
                else if (content.isBlank()) {
                    if (hasText) {
                        blankLineAfterText = true;
                    }
                }
                else if (content.startsWith("@") || isBlockTag(content)) {
                    blankLineAfterText = false;
                }
                else {
                    if (blankLineAfterText) {
                        log(lineNumber, MSG_MISSING_TAG);
                    }
                    hasText = true;
                    blankLineAfterText = false;
                }

            }
        }
    }

    /**
     * Checks whether a line starts with a supported HTML block tag.
     *
     * @param content normalized Javadoc line
     * @return whether the line starts with an HTML block tag
     */
    private static boolean isBlockTag(String content) {
        boolean result = false;
        if (content.startsWith("<")) {
            for (String tag : BLOCK_TAGS) {
                if (content.startsWith("<" + tag)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
