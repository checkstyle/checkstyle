////
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that string literals are not used with <code>==</code> or <code>&#33;=</code>.
 * Since <code>==</code> will compare the object references, not the actual value of the strings,
 * <code>String.equals()</code> should be used.
 * More information can be found
 * <a href="https://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java/">
 * in this article</a>.
 * </div>
 *
 * <p>
 * Rationale: Novice Java programmers often use code like:
 * </p>
 * <pre>
 * if (x == "something")
 * </pre>
 *
 * <p>
 * when they mean
 * </p>
 * <pre>
 * if ("something".equals(x))
 * </pre>
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
 * {@code string.literal.equality}
 * </li>
 * </ul>
 *
 * @since 3.2
 * @noinspection HtmlTagCanBeJavadocTag
 * @noinspectionreason HtmlTagCanBeJavadocTag - encoded symbols were not decoded
 *      when replaced with Javadoc tag
 */
@StatelessCheck
public class StringLiteralEqualityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "string.literal.equality";

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
        return new int[] {TokenTypes.EQUAL, TokenTypes.NOT_EQUAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final boolean hasStringLiteralChild = hasStringLiteralChild(ast);

        if (hasStringLiteralChild) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

    /**
     * Checks whether string literal or text block literals are concatenated.
     *
     * @param ast ast
     * @return {@code true} if string literal or text block literals are concatenated
     */
    private static boolean hasStringLiteralChild(DetailAST ast) {
        DetailAST currentAst = ast;
        boolean result = false;
        while (currentAst != null) {
            if (currentAst.findFirstToken(TokenTypes.STRING_LITERAL) == null
                && currentAst.findFirstToken(TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) == null) {
                currentAst = currentAst.findFirstToken(TokenTypes.PLUS);
            }
            else {
                result = true;
                break;
            }
        }
        return result;
    }

}
