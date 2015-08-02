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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

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
    extends AbstractOptionCheck<WrapOption> {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String LINE_PREVIOUS = "line.previous";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String LINE_NEW = "line.new";

    /**
     * Sets the comma wrap option to end of the line.
     */
    public SeparatorWrapCheck() {
        super(WrapOption.EOL, WrapOption.class);
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
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        final int colNo = ast.getColumnNo();
        final int lineNo = ast.getLineNo();
        final String currentLine = getLines()[lineNo - 1];
        final String substringAfterToken =
                currentLine.substring(colNo + text.length()).trim();
        final String substringBeforeToken =
                currentLine.substring(0, colNo).trim();
        final WrapOption wSp = getAbstractOption();

        if (wSp == WrapOption.EOL
                && substringBeforeToken.isEmpty()) {
            log(lineNo, colNo, LINE_PREVIOUS, text);
        }
        else if (wSp == WrapOption.NL
                 && substringAfterToken.isEmpty()) {
            log(lineNo, colNo, LINE_NEW, text);
        }
    }
}
