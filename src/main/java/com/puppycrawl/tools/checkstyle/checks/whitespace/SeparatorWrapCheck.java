////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks line wrapping with separators.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to wrap lines.
 * Default value is {@code eol}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DOT">
 * DOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMMA">
 * COMMA</a>.
 * </li>
 * </ul>
 * <p>
 * Code example for comma and dot at the new line:
 * </p>
 * <pre>
 * s
 *     .isEmpty();
 * foo(i
 *     ,s);
 * </pre>
 *  <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SeparatorWrap&quot;/&gt;
 * </pre>
 * <p>
 * Code example for comma and dot at the previous line:
 * </p>
 * <pre>
 * s.
 *     isEmpty();
 * foo(i,
 *     s);
 * </pre>
 * <p>
 * Example for checking method reference at new line (good case and bad case):
 * </p>
 * <pre>
 * Arrays.sort(stringArray, String:: // violation
 *     compareToIgnoreCase);
 * Arrays.sort(stringArray, String
 *     ::compareToIgnoreCase); // good
 * </pre>
 * <p>
 * To configure the check for
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_REF">
 * METHOD_REF</a> at new line:
 * </p>
 * <pre>
 * &lt;module name=&quot;SeparatorWrap&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_REF&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;nl&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for comma at the new line:
 * </p>
 * <pre>
 * &lt;module name=&quot;SeparatorWrap&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;COMMA&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;nl&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 5.8
 */
@StatelessCheck
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

    /** Specify policy on how to wrap lines. */
    private WrapOption option = WrapOption.EOL;

    /**
     * Setter to specify policy on how to wrap lines.
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        option = WrapOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
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
            TokenTypes.METHOD_REF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
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
            log(ast, MSG_LINE_PREVIOUS, text);
        }
        else if (option == WrapOption.NL
                 && substringAfterToken.isEmpty()) {
            log(ast, MSG_LINE_NEW, text);
        }
    }

}
