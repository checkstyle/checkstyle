///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the padding between the identifier of a method definition,
 * constructor definition, method call, constructor invocation, record, or record pattern;
 * and the left parenthesis of the parameter list.
 * That is, if the identifier and left parenthesis are on the same line,
 * checks whether a space is required immediately after the identifier or
 * such a space is forbidden.
 * If they are not on the same line, reports a violation, unless configured to
 * allow line breaks. To allow linebreaks after the identifier, set property
 * {@code allowLineBreaks} to {@code true}.
 * </div>
 *
 * @since 3.4
 */

@StatelessCheck
public class MethodParamPadCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_PREVIOUS = "line.previous";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * Allow a line break between the identifier and left parenthesis.
     */
    private boolean allowLineBreaks;

    /** Specify policy on how to pad method parameter. */
    private PadOption option = PadOption.NOSPACE;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_PATTERN_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
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
        }

        if (parenAST != null) {
            final int[] line = getLineCodePoints(parenAST.getLineNo() - 1);
            if (CodePointUtil.hasWhitespaceBefore(parenAST.getColumnNo(), line)) {
                if (!allowLineBreaks) {
                    log(parenAST, MSG_LINE_PREVIOUS, parenAST.getText());
                }
            }
            else {
                final int before = parenAST.getColumnNo() - 1;
                if (option == PadOption.NOSPACE
                    && CommonUtil.isCodePointWhitespace(line, before)) {
                    log(parenAST, MSG_WS_PRECEDED, parenAST.getText());
                }
                else if (option == PadOption.SPACE
                         && !CommonUtil.isCodePointWhitespace(line, before)) {
                    log(parenAST, MSG_WS_NOT_PRECEDED, parenAST.getText());
                }
            }
        }
    }

    /**
     * Setter to allow a line break between the identifier and left parenthesis.
     *
     * @param allowLineBreaks whether whitespace should be
     *     flagged at line breaks.
     * @since 3.4
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

    /**
     * Setter to specify policy on how to pad method parameter.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 3.4
     */
    public void setOption(String optionStr) {
        option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

}
