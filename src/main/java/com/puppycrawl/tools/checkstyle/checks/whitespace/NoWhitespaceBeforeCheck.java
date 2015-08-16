////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that there is no whitespace before a token.
 * More specifically, it checks that it is not preceded with whitespace,
 * or (if linebreaks are allowed) all characters on the line before are
 * whitespace. To allow linebreaks before a token, set property
 * allowLineBreaks to true.
 * </p>
 * <p> By default the check will check the following operators:
 *  {@link TokenTypes#SEMI SEMI},
 *  {@link TokenTypes#POST_DEC POST_DEC},
 *  {@link TokenTypes#POST_INC POST_INC}.
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
 * <p> An example of how to configure the check to allow linebreaks before
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
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.preceded";

    /** Whether whitespace is allowed if the AST is at a linebreak */
    private boolean allowLineBreaks;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.DOT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;

        if (before < 0 || Character.isWhitespace(line.charAt(before))) {

            // empty FOR initializer?
            if (ast.getType() == TokenTypes.SEMI) {
                final DetailAST sibling = ast.getPreviousSibling();
                if (sibling != null
                        && sibling.getType() == TokenTypes.FOR_INIT
                        && sibling.getChildCount() == 0) {
                    return;
                }
            }

            boolean flag = !allowLineBreaks;
            // verify all characters before '.' are whitespace
            for (int i = 0; !flag && i < before; i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    flag = true;
                }
            }
            if (flag) {
                log(ast.getLineNo(), before, MSG_KEY, ast.getText());
            }
        }
    }

    /**
     * Control whether whitespace is flagged at linebreaks.
     * @param allowLineBreaks whether whitespace should be
     * flagged at linebreaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }
}
