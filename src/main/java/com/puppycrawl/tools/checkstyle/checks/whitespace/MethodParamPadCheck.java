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

import com.puppycrawl.tools.checkstyle.Utils;
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
 */

public class MethodParamPadCheck
    extends AbstractOptionCheck<PadOption> {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String LINE_PREVIOUS = "line.previous";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_NOT_PRECEDED = "ws.notPreceded";

    /** Whether whitespace is allowed if the method identifier is at a
     * linebreak */
    private boolean allowLineBreaks;

    /**
     * Sets the pad option to nospace.
     */
    public MethodParamPadCheck() {
        super(PadOption.NOSPACE, PadOption.class);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parenAST;
        if (ast.getType() == TokenTypes.METHOD_CALL) {
            parenAST = ast;
        }
        else {
            parenAST = ast.findFirstToken(TokenTypes.LPAREN);
            // array construction => parenAST == null
            if (parenAST == null) {
                return;
            }
        }

        final String line = getLines()[parenAST.getLineNo() - 1];
        if (Utils.whitespaceBefore(parenAST.getColumnNo(), line)) {
            if (!allowLineBreaks) {
                log(parenAST, LINE_PREVIOUS, parenAST.getText());
            }
        }
        else {
            final int before = parenAST.getColumnNo() - 1;
            if (getAbstractOption() == PadOption.NOSPACE
                && Character.isWhitespace(line.charAt(before))) {
                log(parenAST , WS_PRECEDED, parenAST.getText());
            }
            else if (getAbstractOption() == PadOption.SPACE
                     && !Character.isWhitespace(line.charAt(before))) {
                log(parenAST, WS_NOT_PRECEDED, parenAST.getText());
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
