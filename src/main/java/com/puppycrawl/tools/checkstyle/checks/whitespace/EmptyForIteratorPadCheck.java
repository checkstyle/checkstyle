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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the padding of an empty for iterator; that is whether a white
 * space is required at an empty for iterator, or such white space is
 * forbidden. No check occurs if there is a line wrap at the iterator, as in
 * </div>
 * <pre>
 * for (Iterator foo = very.long.line.iterator();
 *     foo.hasNext();
 *    )
 * </pre>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to pad an empty for iterator.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption}.
 * Default value is {@code nospace}.
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
 * {@code ws.followed}
 * </li>
 * <li>
 * {@code ws.notFollowed}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class EmptyForIteratorPadCheck
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

    /** Semicolon literal. */
    private static final String SEMICOLON = ";";

    /** Specify policy on how to pad an empty for iterator. */
    private PadOption option = PadOption.NOSPACE;

    /**
     * Setter to specify policy on how to pad an empty for iterator.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 3.0
     */
    public void setOption(String optionStr) {
        option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.FOR_ITERATOR};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!ast.hasChildren()) {
            // empty for iterator. test pad after semi.
            final DetailAST semi = ast.getPreviousSibling();
            final int[] line = getLineCodePoints(semi.getLineNo() - 1);
            final int after = semi.getColumnNo() + 1;
            // don't check if at end of line
            if (after < line.length) {
                if (option == PadOption.NOSPACE
                    && CommonUtil.isCodePointWhitespace(line, after)) {
                    log(ast, MSG_WS_FOLLOWED, SEMICOLON);
                }
                else if (option == PadOption.SPACE
                    && !CommonUtil.isCodePointWhitespace(line, after)) {
                    log(ast, MSG_WS_NOT_FOLLOWED, SEMICOLON);
                }
            }
        }
    }

}
