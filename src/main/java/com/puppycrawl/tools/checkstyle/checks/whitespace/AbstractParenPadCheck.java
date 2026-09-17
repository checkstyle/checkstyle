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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>Abstract class for checking the padding of parentheses. That is whether a
 * space is required after a left parenthesis and before a right parenthesis,
 * or such spaces are forbidden.
 * </div>
 */
@StatelessCheck
public abstract class AbstractParenPadCheck
    extends AbstractCheck {

    /** Open parenthesis literal. */
    private static final char OPEN_PARENTHESIS = '(';

    /** Close parenthesis literal. */
    private static final char CLOSE_PARENTHESIS = ')';

    /** Message key for followed whitespace. */
    private final String msgWsFollowed;

    /** Message key for not followed whitespace. */
    private final String msgWsNotFollowed;

    /** Message key for preceded whitespace. */
    private final String msgWsPreceded;

    /** Message key for not preceded whitespace. */
    private final String msgWsNotPreceded;

    /** The policy to enforce. */
    private PadOption option = PadOption.NOSPACE;

    /**
     * Creates a new {@code AbstractParenPadCheck} instance.
     *
     * @param msgWsFollowed message key for followed whitespace
     * @param msgWsNotFollowed message key for not followed whitespace
     * @param msgWsPreceded message key for preceded whitespace
     * @param msgWsNotPreceded message key for not preceded whitespace
     */
    protected AbstractParenPadCheck(String msgWsFollowed, String msgWsNotFollowed,
                                    String msgWsPreceded, String msgWsNotPreceded) {
        this.msgWsFollowed = msgWsFollowed;
        this.msgWsNotFollowed = msgWsNotFollowed;
        this.msgWsPreceded = msgWsPreceded;
        this.msgWsNotPreceded = msgWsNotPreceded;
    }

    /**
     * Specify policy on how to pad parentheses.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Process a token representing a left parentheses.
     *
     * @param ast the token representing a left parentheses
     */
    protected void processLeft(DetailAST ast) {
        final int[] line = getLineCodePoints(ast.getLineNo() - 1);
        final int after = ast.getColumnNo() + 1;

        if (after < line.length) {
            final boolean hasWhitespaceAfter =
                    CommonUtil.isCodePointWhitespace(line, after);
            if (option == PadOption.NOSPACE && hasWhitespaceAfter) {
                log(ast, msgWsFollowed, OPEN_PARENTHESIS);
            }
            else if (option == PadOption.SPACE && !hasWhitespaceAfter
                     && line[after] != CLOSE_PARENTHESIS) {
                log(ast, msgWsNotFollowed, OPEN_PARENTHESIS);
            }
        }
    }

    /**
     * Process a token representing a right parentheses.
     *
     * @param ast the token representing a right parentheses
     */
    protected void processRight(DetailAST ast) {
        final int before = ast.getColumnNo() - 1;
        if (before >= 0) {
            final int[] line = getLineCodePoints(ast.getLineNo() - 1);
            final boolean hasPrecedingWhitespace =
                    CommonUtil.isCodePointWhitespace(line, before);

            if (option == PadOption.NOSPACE && hasPrecedingWhitespace
                && !CodePointUtil.hasWhitespaceBefore(before, line)) {
                log(ast, msgWsPreceded, CLOSE_PARENTHESIS);
            }
            else if (option == PadOption.SPACE && !hasPrecedingWhitespace
                && line[before] != OPEN_PARENTHESIS) {
                log(ast, msgWsNotPreceded, CLOSE_PARENTHESIS);
            }
        }
    }

}
