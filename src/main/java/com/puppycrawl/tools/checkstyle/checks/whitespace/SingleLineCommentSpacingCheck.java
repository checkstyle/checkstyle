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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks spacing around trailing single-line comments.
 * </div>
 *
 * @since 12.2
 */
@StatelessCheck
public class SingleLineCommentSpacingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_BEFORE = "single.line.comment.spacing.before";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_AFTER = "single.line.comment.spacing.after";

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
        return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        final int commentColumnNo = ast.getColumnNo();
        final String textBeforeComment = line.substring(0, commentColumnNo);
        final boolean isTrailingComment = !textBeforeComment.isBlank();

        if (isTrailingComment && !isSpacingExempt(line, commentColumnNo)) {
            if (!Character.isWhitespace(line.charAt(commentColumnNo - 1))) {
                log(ast, MSG_KEY_BEFORE);
            }
        }

        if (hasMissingWhitespaceAfterCommentMarker(line, commentColumnNo)) {
            log(ast.getLineNo(), commentColumnNo + 3, MSG_KEY_AFTER);
        }
    }

    /**
     * Checks whether visible comment text starts immediately after {@code //}.
     *
     * @param line source line that contains the comment
     * @param commentColumnNo zero-based column of the {@code //} token
     * @return true when whitespace is missing after {@code //}
     */
    private static boolean hasMissingWhitespaceAfterCommentMarker(String line,
                                                                  int commentColumnNo) {
        final String textAfterCommentMarker = line.substring(commentColumnNo + 2);
        final String trimmedCommentText = textAfterCommentMarker.trim();
        return !trimmedCommentText.isEmpty() && trimmedCommentText.charAt(0) != '/'
                && !Character.isWhitespace(textAfterCommentMarker.charAt(0));
    }

    /**
     * Checks whether the comment should be ignored for this prototype.
     *
     * @param line source line that contains the comment
     * @param commentColumnNo zero-based column of the {@code //} token
     * @return true when the comment is bare or starts with slash-only content
     */
    private static boolean isSpacingExempt(String line, int commentColumnNo) {
        final String textAfterCommentMarker = line.substring(commentColumnNo + 2);
        final String trimmedCommentText = textAfterCommentMarker.trim();
        return trimmedCommentText.isEmpty() || trimmedCommentText.charAt(0) == '/';
    }
}
