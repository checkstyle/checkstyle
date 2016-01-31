////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>Abstract class for checking the padding of parentheses. That is whether a
 * space is required after a left parenthesis and before a right parenthesis,
 * or such spaces are forbidden.
 * </p>
 * @author Oliver Burn
 */
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
     * @param optionStr string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + optionStr, iae);
        }
    }

    /**
     * Process a token representing a left parentheses.
     * @param ast the token representing a left parentheses
     */
    protected void processLeft(DetailAST ast) {
        final String line = getLines()[ast.getLineNo() - 1];
        final int after = ast.getColumnNo() + 1;
        if (after < line.length()) {
            if (option == PadOption.NOSPACE
                && Character.isWhitespace(line.charAt(after))) {
                log(ast.getLineNo(), after, MSG_WS_FOLLOWED, OPEN_PARENTHESIS);
            }
            else if (option == PadOption.SPACE
                     && !Character.isWhitespace(line.charAt(after))
                     && line.charAt(after) != CLOSE_PARENTHESIS) {
                log(ast.getLineNo(), after, MSG_WS_NOT_FOLLOWED, OPEN_PARENTHESIS);
            }
        }
    }

    /**
     * Process a token representing a right parentheses.
     * @param ast the token representing a right parentheses
     */
    protected void processRight(DetailAST ast) {
        final String line = getLines()[ast.getLineNo() - 1];
        final int before = ast.getColumnNo() - 1;
        if (before >= 0) {
            if (option == PadOption.NOSPACE
                && Character.isWhitespace(line.charAt(before))
                && !CommonUtils.hasWhitespaceBefore(before, line)) {
                log(ast.getLineNo(), before, MSG_WS_PRECEDED, CLOSE_PARENTHESIS);
            }
            else if (option == PadOption.SPACE
                && !Character.isWhitespace(line.charAt(before))
                && line.charAt(before) != OPEN_PARENTHESIS) {
                log(ast.getLineNo(), ast.getColumnNo(),
                    MSG_WS_NOT_PRECEDED, CLOSE_PARENTHESIS);
            }
        }
    }
}
