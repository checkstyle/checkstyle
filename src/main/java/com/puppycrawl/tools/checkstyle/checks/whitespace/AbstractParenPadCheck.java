///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * <p>Abstract class for checking the padding of parentheses. That is whether a
 * space is required after a left parenthesis and before a right parenthesis,
 * or such spaces are forbidden.
 * </p>
 */
@StatelessCheck
public abstract class AbstractParenPadCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

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

    /** Open parenthesis literal. */
    private static final char OPEN_PARENTHESIS = '(';

    /** Close parenthesis literal. */
    private static final char CLOSE_PARENTHESIS = ')';

    /** The policy to enforce. */
    private PadOption option = PadOption.NOSPACE;

    /**
     * Set the option to enforce.
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
                log(ast, MSG_WS_FOLLOWED, OPEN_PARENTHESIS);
            }
            else if (option == PadOption.SPACE && !hasWhitespaceAfter
                     && line[after] != CLOSE_PARENTHESIS) {
                log(ast, MSG_WS_NOT_FOLLOWED, OPEN_PARENTHESIS);
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
                log(ast, MSG_WS_PRECEDED, CLOSE_PARENTHESIS);
            }
            else if (option == PadOption.SPACE && !hasPrecedingWhitespace
                && line[before] != OPEN_PARENTHESIS) {
                log(ast, MSG_WS_NOT_PRECEDED, CLOSE_PARENTHESIS);
            }
        }
    }

}
