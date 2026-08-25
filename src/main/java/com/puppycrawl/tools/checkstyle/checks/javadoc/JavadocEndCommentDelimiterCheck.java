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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that the Javadoc closing delimiter contains exactly one asterisk before
 * the slash.
 * </div>
 *
 * <p>
 * The closing delimiter of a Javadoc comment is <code>*&#47;</code>. This check reports
 * Javadoc comments whose closing delimiter is preceded by another asterisk, such as
 * <code>**&#47;</code> or <code>***&#47;</code>.
 * </p>
 *
 * @noinspection HtmlTagCanBeJavadocTag
 * @noinspectionreason HtmlTagCanBeJavadocTag - HTML code tags allow escaping the slash
 *      in Javadoc delimiter examples without rendering the entity text.
 * @since 14.1.0
 */
@StatelessCheck
public class JavadocEndCommentDelimiterCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.end.delimiter";

    /**
     * Creates a new {@code JavadocEndCommentDelimiterCheck} instance.
     */
    public JavadocEndCommentDelimiterCheck() {
        // no code by default
    }

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
        if (JavadocUtil.isJavadocComment(ast)) {
            final String commentContent = JavadocUtil.getBlockCommentContent(ast);

            if (commentContent.endsWith("*")) {
                log(ast.getLastChild(), MSG_KEY);
            }
        }
    }

}
