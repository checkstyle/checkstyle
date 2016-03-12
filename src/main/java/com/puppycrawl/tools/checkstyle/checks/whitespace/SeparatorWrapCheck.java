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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks line wrapping with separators.
 * The policy to verify is specified using the {@link WrapOption} class
 * and defaults to {@link WrapOption#EOL}.
 * </p>
 * <p> By default the check will check the following separators:
 *  {@link TokenTypes#DOT DOT},
 *  {@link TokenTypes#COMMA COMMA},
 * Other acceptable tokens are
 *  {@link TokenTypes#SEMI SEMI},
 *  {@link TokenTypes#ELLIPSIS ELLIPSIS},
 *  {@link TokenTypes#AT AT},
 *  {@link TokenTypes#LPAREN LPAREN},
 *  {@link TokenTypes#RPAREN RPAREN},
 *  {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR},
 *  {@link TokenTypes#RBRACK RBRACK},
 * </p>
 * <p>
 * Code example for comma and dot at the new line:
 * </p>
 * <pre>
 * s
 *    .isEmpty();
 * foo(i
 *    ,s);
 * </pre>
 *  <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="SeparatorWrap"/&gt;
 * </pre>
 * <p>
 * Code example for comma and dot at the previous line:
 * </p>
 * <pre>
 * s.
 *    isEmpty();
 * foo(i,
 *    s);
 * </pre>
 * <p> An example of how to configure the check for comma at the
 * new line is:
 * </p>
 * <pre>
 * &lt;module name="SeparatorWrap"&gt;
 *     &lt;property name="tokens" value="COMMA"/&gt;
 *     &lt;property name="option" value="nl"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 */
public class SeparatorWrapCheck
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
    public static final String MSG_LINE_NEW = "line.new";

    /** The policy to enforce. */
    private WrapOption option = WrapOption.EOL;

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = WrapOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + optionStr, iae);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.COMMA,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.ELLIPSIS,
            TokenTypes.AT,
            TokenTypes.LPAREN,
            TokenTypes.RPAREN,
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.RBRACK,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        final int colNo = ast.getColumnNo();
        final int lineNo = ast.getLineNo();
        final String currentLine = getLines()[lineNo - 1];
        final String substringAfterToken =
                currentLine.substring(colNo + text.length()).trim();
        final String substringBeforeToken =
                currentLine.substring(0, colNo).trim();

        if (option == WrapOption.EOL
                && substringBeforeToken.isEmpty()) {
            log(lineNo, colNo, MSG_LINE_PREVIOUS, text);
        }
        else if (option == WrapOption.NL
                 && substringAfterToken.isEmpty()) {
            log(lineNo, colNo, MSG_LINE_NEW, text);
        }
    }
}
