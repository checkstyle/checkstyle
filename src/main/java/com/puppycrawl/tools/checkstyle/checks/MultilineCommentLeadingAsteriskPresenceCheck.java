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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks if the
 * <a href="https://www.oracle.com/java/technologies/javase/codeconventions-comments.html">
 * multi-line comments</a> have leading asterisks on each line.
 * </div>
 *
 * <p>
 * Every line in multi-line comment should have leading asterisk including blank line.
 * </p>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class MultilineCommentLeadingAsteriskPresenceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_ASTERISK = "multiline.comment.missing.asterisk";

    /**
     * Creates a new {@code MultilineCommentLeadingAsteriskPresenceCheck} instance.
     */
    public MultilineCommentLeadingAsteriskPresenceCheck() {
        // no code by default
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
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
        if (!JavadocUtil.isJavadocComment(ast)) {
            final String commentText = JavadocUtil.getBlockCommentContent(ast);
            int lineNumber = ast.getLineNo();
            final int commentEndLineNumber = ast.getLastChild().getLineNo();
            final List<String> lines = commentText.lines().toList();
            final List<Integer> nonAsteriskLineNumbers = new ArrayList<>();
            for (int cur = 1; cur < lines.size(); cur++) {
                lineNumber++;
                final String line = lines.get(cur).trim();
                // is block comment end
                if (lineNumber == commentEndLineNumber && line.isEmpty()) {
                    continue;
                }
                if (!line.startsWith("*")) {
                    nonAsteriskLineNumbers.add(lineNumber);
                }
            }

            if (!nonAsteriskLineNumbers.isEmpty()) {
                final StringBuilder allLineNumbers =
                        new StringBuilder(nonAsteriskLineNumbers.size());
                for (int index = 0; index < nonAsteriskLineNumbers.size(); index++) {
                    if (index > 0) {
                        allLineNumbers.append(", ");
                    }
                    allLineNumbers.append(nonAsteriskLineNumbers.get(index));
                }
                log(ast, MSG_MISSING_ASTERISK, allLineNumbers);
            }
        }
    }

}
