////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the padding of an empty for initializer; that is whether a white
 * space is required at an empty for initializer, or such white space is
 * forbidden.  No check occurs if there is a line wrap at the initializer, as in
 * </p>
 * <pre>
 * for (
 *     ; i &lt; j; i++, j--)
 *  </pre>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to pad an empty for iterator.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption}.
 * Default value is {@code nospace}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyForInitializerPad&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check to require white space at an empty for iterator:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyForInitializerPad&quot;&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;space&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code ws.notPreceded}
 * </li>
 * <li>
 * {@code ws.preceded}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
@StatelessCheck
public class EmptyForInitializerPadCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_NOT_PRECEDED = "ws.notPreceded";

    /** Semicolon literal. */
    private static final String SEMICOLON = ";";

    /** Specify policy on how to pad an empty for iterator. */
    private PadOption option = PadOption.NOSPACE;

    /**
     * Setter to specify policy on how to pad an empty for iterator.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
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
        return new int[] {TokenTypes.FOR_INIT};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!ast.hasChildren()) {
            // empty for initializer. test pad before semi.
            final DetailAST semi = ast.getNextSibling();
            final int semiLineIdx = semi.getLineNo() - 1;
            final int[] line = getLineCodePoints(semiLineIdx);
            final int before = semi.getColumnNo() - 1;
            // don't check if semi at beginning of line
            if (!CodePointUtil.hasWhitespaceBefore(before, line)) {
                if (option == PadOption.NOSPACE
                    && CommonUtil.isCodePointWhitespace(line, before)) {
                    log(ast, MSG_PRECEDED, SEMICOLON);
                }
                else if (option == PadOption.SPACE
                         && !CommonUtil.isCodePointWhitespace(line, before)) {
                    log(ast, MSG_NOT_PRECEDED, SEMICOLON);
                }
            }
        }
    }

}
