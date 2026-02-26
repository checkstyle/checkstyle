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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that all lines in a multi-line block comment
 * (&#47;* ... *&#47;) start with a leading asterisk (*), as required by the
 * <a href="https://google.github.io/styleguide/javaguide.html#s4.8.6-comments">
 * Google Java Style Guide §4.8.6</a>.
 * </div>
 *
 * <p>
 * For multi-line block comments, subsequent lines must start with * aligned
 * with the * on the previous line. This check does not apply to single-line
 * block comments (&#47;* comment *&#47;).
 * </p>
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
 * {@code multiline.comment.missing.asterisk}
 * </li>
 * </ul>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class MultilineCommentLeadingAsteriskPresenceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_ASTERISK = "multiline.comment.missing.asterisk";

    /**
     * Pattern to split comment text into lines, supporting all line terminators.
     */
    private static final Pattern LINE_SPLIT_PATTERN =
            Pattern.compile("\\R");

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Skip Javadoc comments - only check regular block comments
        if (!JavadocUtil.isJavadocComment(ast)) {
            final String commentText = ast.getFirstChild().getText();

            // Only check multi-line comments
            if (isMultiLineComment(commentText)) {
                checkCommentLines(ast, commentText);
            }
        }
    }

    /**
     * Checks if the comment is multi-line.
     *
     * @param commentText the text of the comment
     * @return {@code true} if the comment spans multiple lines
     */
    private static boolean isMultiLineComment(String commentText) {
        return commentText.contains("\n");
    }

    /**
     * Checks each line of the comment for leading asterisks.
     *
     * @param ast the block comment AST node
     * @param commentText the text of the comment
     */
    private void checkCommentLines(DetailAST ast, String commentText) {
        final String[] lines = LINE_SPLIT_PATTERN.split(commentText);

        for (int cur = 1; cur < lines.length; cur++) {
            final String trimmedLine = lines[cur].trim();

            if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("*")) {
                log(ast, MSG_MISSING_ASTERISK);
                break;
            }
        }
    }
}
