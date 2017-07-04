////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that there is no whitespace before a token.
 * More specifically, it checks that it is not preceded with whitespace,
 * or (if line breaks are allowed) all characters on the line before are
 * whitespace. To allow line breaks before a token, set property
 * allowLineBreaks to true.
 * </p>
 * <p> By default the check will check the following operators:
 *  {@link TokenTypes#COMMA COMMA},
 *  {@link TokenTypes#SEMI SEMI},
 *  {@link TokenTypes#POST_DEC POST_DEC},
 *  {@link TokenTypes#POST_INC POST_INC},
 *  {@link TokenTypes#ELLIPSIS ELLIPSIS}.
 * {@link TokenTypes#DOT DOT} is also an acceptable token in a configuration
 * of this check.
 * </p>
 *
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceBefore"/&gt;
 * </pre>
 * <p> An example of how to configure the check to allow line breaks before
 * a {@link TokenTypes#DOT DOT} token is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceBefore"&gt;
 *     &lt;property name="tokens" value="DOT"/&gt;
 *     &lt;property name="allowLineBreaks" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @author lkuehne
 */
public class NoWhitespaceBeforeCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.preceded";

    /** Whether whitespace is allowed if the AST is at a linebreak. */
    private boolean allowLineBreaks;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.ELLIPSIS,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.DOT,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
            TokenTypes.ELLIPSIS,
            TokenTypes.METHOD_REF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;

        if ((before < 0 || Character.isWhitespace(line.charAt(before)))
                && !isInEmptyForInitializer(ast)) {

            boolean flag = !allowLineBreaks;
            // verify all characters before '.' are whitespace
            for (int i = 0; !flag && i < before; i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                log(ast.getLineNo(), before, MSG_KEY, ast.getText());
            }
        }
    }

    /**
     * Checks that semicolon is in empty for initializer.
     * @param semicolonAst DetailAST of semicolon.
     * @return true if semicolon is in empty for initializer.
     */
    private static boolean isInEmptyForInitializer(DetailAST semicolonAst) {
        boolean result = false;
        if (semicolonAst.getType() == TokenTypes.SEMI) {
            final DetailAST sibling = semicolonAst.getPreviousSibling();
            if (sibling != null
                    && sibling.getType() == TokenTypes.FOR_INIT
                    && sibling.getChildCount() == 0) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Control whether whitespace is flagged at line breaks.
     * @param allowLineBreaks whether whitespace should be
     *     flagged at line breaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }
}
