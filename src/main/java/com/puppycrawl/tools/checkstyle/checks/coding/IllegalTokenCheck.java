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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for illegal tokens. By default, labels are prohibited.
 * </div>
 *
 * <p>
 * Rationale: Certain language features can harm readability, lead to
 * confusion or are not obvious to novice developers. Other features
 * may be discouraged in certain frameworks, such as not having
 * native methods in Enterprise JavaBeans components.
 * </p>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code anyTokenTypesSet}.
 * Default value is
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LABELED_STAT">
 * LABELED_STAT</a>.
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
 * {@code illegal.token}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class IllegalTokenCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.token";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LABELED_STAT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return TokenUtil.getAllTokenIds();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        log(
            ast,
            MSG_KEY,
            convertToString(ast)
        );
    }

    /**
     * Converts given AST node to string representation.
     *
     * @param ast node to be represented as string
     * @return string representation of AST node
     */
    private static String convertToString(DetailAST ast) {
        final String tokenText;
        switch (ast.getType()) {
            case TokenTypes.LABELED_STAT:
                tokenText = ast.getFirstChild().getText() + ast.getText();
                break;
            // multiline tokens need to become singlelined
            case TokenTypes.COMMENT_CONTENT:
                tokenText = JavadocUtil.escapeAllControlChars(ast.getText());
                break;
            default:
                tokenText = ast.getText();
                break;
        }
        return tokenText;
    }

}
