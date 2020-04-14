////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks if the javadoc has leading asterisk on each line or not.
 * </p>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="MissingLeadingAsterisk"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * &#47;**
 *  * Some description here // OK
 *  * Another line of description // OK
 *    Another line of description // violation, missing leading asterisk at the start
 *  *&#47;
 * public void test() {}
 * </pre>
 *
 * @since 6.0
 *
 */
@StatelessCheck
public class MissingLeadingAsteriskCheck extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_ASTERISK = "javadoc.missing.asterisk";

    /**
     * The new line character that separates each line of javadoc.
     */
    private static final String COMMENT_SEPARATOR = "\n";

    /**
     * This character is present at the start of a javadoc.
     */
    private static final char JAVADOC_COMMENT_START = '*';

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String commentContent = JavadocUtil.getBlockCommentContent(ast);
        if (isJavaDocComment(commentContent) && !isInlineComment(commentContent)) {
            for (String singleLine : commentContent.split(COMMENT_SEPARATOR)) {
                if (!CommonUtil.isBlank(singleLine) && singleLine.trim().charAt(0) != '*') {
                    log(ast, MSG_MISSING_ASTERISK);
                }
            }
        }
    }

    /**
     * Checks, if the given comment is an inline comment or not.
     * @param comment content of javadoc.
     * @return true, if the given comment is an inline comment.
     */
    private static boolean isInlineComment(String comment) {
        return comment.split(COMMENT_SEPARATOR).length == 1;
    }

    /**
     * Checks if the given comment is an javadoc comment or not.
     * @param comment content of the comment.
     * @return true, if the given comment is an javadoc comment.
     */
    private static boolean isJavaDocComment(String comment) {
        boolean result = false;
        if (!comment.isEmpty()) {
            result = comment.charAt(0) == JAVADOC_COMMENT_START;
        }
        return result;
    }
}
