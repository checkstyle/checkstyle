////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that there is no whitespace after a token.
 * More specifically, it checks that it is not followed by whitespace,
 * or (if linebreaks are allowed) all characters on the line after are
 * whitespace. To forbid linebreaks afer a token, set property
 * allowLineBreaks to false.
 * </p>
  * <p> By default the check will check the following operators:
 *  {@link TokenTypes#ARRAY_INIT ARRAY_INIT},
 *  {@link TokenTypes#BNOT BNOT},
 *  {@link TokenTypes#DEC DEC},
 *  {@link TokenTypes#DOT DOT},
 *  {@link TokenTypes#INC INC},
 *  {@link TokenTypes#LNOT LNOT},
 *  {@link TokenTypes#UNARY_MINUS UNARY_MINUS},
 *  {@link TokenTypes#UNARY_PLUS UNARY_PLUS}. It also supports the operator
 *  {@link TokenTypes#TYPECAST TYPECAST}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceAfter"/&gt;
 * </pre>
 * <p> An example of how to configure the check to forbid linebreaks after
 * a {@link TokenTypes#DOT DOT} token is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceAfter"&gt;
 *     &lt;property name="tokens" value="DOT"/&gt;
 *     &lt;property name="allowLineBreaks" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @author lkuehne
 * @version 1.0
 */
public class NoWhitespaceAfterCheck extends Check
{
    /** Whether whitespace is allowed if the AST is at a linebreak */
    private boolean mAllowLineBreaks = true;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
            TokenTypes.TYPECAST,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        DetailAST targetAST = aAST;
        if (targetAST.getType() == TokenTypes.TYPECAST) {
            targetAST = targetAST.findFirstToken(TokenTypes.RPAREN);
        }
        final String line = getLines()[aAST.getLineNo() - 1];
        final int after =
            targetAST.getColumnNo() + targetAST.getText().length();

        if ((after >= line.length())
            || Character.isWhitespace(line.charAt(after)))
        {
            boolean flag = !mAllowLineBreaks;
            for (int i = after + 1; !flag && (i < line.length()); i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    flag = true;
                }
            }
            if (flag) {
                log(targetAST.getLineNo(), after,
                    "ws.followed", targetAST.getText());
            }
        }
    }

    /**
     * Control whether whitespace is flagged at linebreaks.
     * @param aAllowLineBreaks whether whitespace should be
     * flagged at linebreaks.
     */
    public void setAllowLineBreaks(boolean aAllowLineBreaks)
    {
        mAllowLineBreaks = aAllowLineBreaks;
    }
}
