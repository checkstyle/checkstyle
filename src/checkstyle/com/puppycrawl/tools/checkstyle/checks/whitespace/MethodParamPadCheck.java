////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * <p>
 * Checks the padding between the identifier of a method definition,
 * constructor definition, method call, or constructor invocation;
 * and the left parenthesis of the parameter list.
 * That is, if the identifier and left parenthesis are on the same line,
 * checks whether a space is required immediately after the identifier or
 * such a space is forbidden.
 * If they are not on the same line, reports an error, unless configured to
 * allow line breaks.
 * </p>
 * <p> By default the check will check the following tokens:
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF},
 *  {@link TokenTypes#LITERAL_NEW LITERAL_NEW},
 *  {@link TokenTypes#METHOD_CALL METHOD_CALL},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#SUPER_CTOR_CALL SUPER_CTOR_CALL}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MethodParamPad"/&gt;
 * </pre>
 * <p> An example of how to configure the check to require a space
 * after the identifier of a method definition, except if the left
 * parenthesis occurs on a new line, is:
 * </p>
 * <pre>
 * &lt;module name="MethodParamPad"&gt;
 *     &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *     &lt;property name="option" value="space"/&gt;
 *     &lt;property name="allowLineBreaks" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */

public class MethodParamPadCheck
    extends AbstractOptionCheck
{
    /**
     * Sets the pad otion to nospace.
     */
    public MethodParamPadCheck()
    {
        super(PadOption.NOSPACE);
    }

    /** Whether whitespace is allowed if the method identifier is at a
     * linebreak */
    private boolean mAllowLineBreaks;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST parenAST;
        if ((aAST.getType() == TokenTypes.METHOD_CALL)
            || (aAST.getType() == TokenTypes.SUPER_CTOR_CALL))
        {
            parenAST = aAST;
        }
        else {
            parenAST = aAST.findFirstToken(TokenTypes.LPAREN);
            // array construction => parenAST == null
            if (parenAST == null) {
                return;
            }
        }
        int parenColumnNo = parenAST.getColumnNo();
        final String[] lines = getLines();
        int identLineNo = -1;
        int identColumnNo = -1;
        final String identText;
        if (aAST.getType() == TokenTypes.SUPER_CTOR_CALL) {
            identText = "super";
            final String parenLine = lines[parenAST.getLineNo() - 1];
            final int superIndex =
                parenLine.lastIndexOf("super", parenAST.getColumnNo());
            if (superIndex != -1) {
                identLineNo = aAST.getLineNo();
                identColumnNo = superIndex;
            }
        }
        else {
            final DetailAST identAST;
            final DetailAST dotAST = aAST.findFirstToken(TokenTypes.DOT);
            if (dotAST != null) {
                identAST = dotAST.getLastChild();
            }
            else {
                identAST = aAST.findFirstToken(TokenTypes.IDENT);
            }
            identLineNo = identAST.getLineNo();
            identColumnNo = identAST.getColumnNo();
            identText = identAST.getText();
        }

        if (identLineNo == parenAST.getLineNo()) {
            final int after = identColumnNo + identText.length();
            final String line = lines[identLineNo - 1];
            if ((PadOption.NOSPACE == getAbstractOption())
                && (Character.isWhitespace(line.charAt(after))))
            {
                log(identLineNo, after, "ws.followed", identText);
            }
            else if ((PadOption.SPACE == getAbstractOption())
                     && !Character.isWhitespace(line.charAt(after)))
            {
                log(identLineNo, after, "ws.notFollowed", identText);
            }
        }
        else if (!mAllowLineBreaks) {
            log(
                parenAST.getLineNo(),
                parenColumnNo,
                "line.previous",
                parenAST.getText());
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
