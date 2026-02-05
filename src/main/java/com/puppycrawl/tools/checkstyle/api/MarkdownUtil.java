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

package com.puppycrawl.tools.checkstyle.api;

/**
 * Utility class for working with Markdown comments.
 */
public final class MarkdownUtil {

    /** Private constructor to prevent instantiation. */
    private MarkdownUtil() {
    }

    /**
     * Extracts the content from a Markdown comment AST node.
     *
     * @param ast the AST node to extract content from
     * @return the Markdown comment content, or null if not a Markdown comment
     */
    public static String getMarkdownContent(DetailAST ast) {
        final String result;
        if (ast.getType() == TokenTypes.MARKDOWN_COMMENT) {
            final DetailAST contentAst = ast.findFirstToken(TokenTypes.COMMENT_CONTENT);
            if (contentAst != null) {
                result = contentAst.getText();
            }
            else {
                result = null;
            }
        }
        else {
            result = null;
        }
        return result;
    }
}
