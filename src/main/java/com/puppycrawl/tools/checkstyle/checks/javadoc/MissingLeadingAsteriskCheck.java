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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks if the javadoc has
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisk
 * </a> on each line.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MissingLeadingAsterisk"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * &#47;** // violation, javadoc has lines without leading asterisk.
 *  * Some description here.
 *    Another line of description.
 *  *&#47;
 * public class Test {
 *   &#47;** // violation, javadoc has lines without leading asterisk.
 *    * Some description here.
 *    * Another line of description.
 *      Another line of description.
 *    *&#47;
 *   public void test() {}
 *
 *   &#47;** Some description here. *&#47; // OK
 *   public void test1() {}
 *
 *   &#47;** // OK
 *    * Some description here.
 *    * Another line of description.
 *    *&#47;
 *   public void test2() {}
 *
 *   &#47;** // violation, javadoc has lines without leading asterisk.
 *    * Some description here.
 *
 *    * Another line of description.
 *    *&#47;
 *   public void test3() {}
 * }
 * </pre>
 *
 * @since 8.33
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
    private static final Pattern MATCH_COMMENT_SEPARATOR = CommonUtil.createPattern("\r\n?|\n");

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
        if (JavadocUtil.isJavadocComment(commentContent)) {
            final String[] commentLines = MATCH_COMMENT_SEPARATOR.split(commentContent);

            for (int i = 0; i < commentLines.length - 1; i++) {
                final String commentLine = commentLines[i];
                if (!isValidLine(commentLine)) {
                    log(ast, MSG_MISSING_ASTERISK);
                }
            }

            // last line of javadoc could be blank
            final String lastCommentLine = commentLines[commentLines.length - 1];
            if (!CommonUtil.isBlank(lastCommentLine) && lastCommentLine.trim().charAt(0) != '*') {
                log(ast, MSG_MISSING_ASTERISK);
            }
        }
    }

    /**
     * Checks whether the given line is a valid comment line in javadoc.
     *
     * @param line a single comment line.
     * @return true if the given line is a valid comment line in javadoc.
     */
    private static boolean isValidLine(String line) {
        return !CommonUtil.isBlank(line) && line.trim().charAt(0) == '*';
    }
}
