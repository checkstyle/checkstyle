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

import java.util.Arrays;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks line wrapping with separators.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to wrap lines.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.WrapOption}.
 * Default value is {@code eol}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DOT">
 * DOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMMA">
 * COMMA</a>.
 * </li>
 * </ul>
 *  <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SeparatorWrap&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import java.io.
 *          IOException; // OK
 *
 * class Test {
 *
 *   String s;
 *
 *   public void foo(int a,
 *                     int b) { // OK
 *   }
 *
 *   public void bar(int p
 *                     , int q) { // violation, separator comma on new line
 *     if (s
 *           .isEmpty()) { // violation, separator dot on new line
 *     }
 *   }
 *
 * }
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
 * Example:
 * </p>
 * <pre>
 * import java.util.Arrays;
 *
 * class Test2 {
 *
 *   String[] stringArray = {&quot;foo&quot;, &quot;bar&quot;};
 *
 *   void fun() {
 *     Arrays.sort(stringArray, String::
 *       compareToIgnoreCase);  // violation, separator method reference on same line
 *     Arrays.sort(stringArray, String
 *       ::compareTo);  // OK
 *   }
 *
 * }
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
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test3 {
 *
 *   String s;
 *
 *   int a,
 *     b;  // violation, separator comma on same line
 *
 *   public void foo(int a,
 *                      int b) {  // violation, separator comma on the same line
 *     int r
 *       , t; // OK
 *   }
 *
 *   public void bar(int p
 *                     , int q) {  // OK
 *   }
 *
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code line.new}
 * </li>
 * <li>
 * {@code line.previous}
 * </li>
 * </ul>
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
     *
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
        final int[] currentLine = getLineCodePoints(lineNo - 1);
        final int[] substringAfterToken = CodePointUtil.trim(
                Arrays.copyOfRange(currentLine, colNo + text.length(), currentLine.length)
        );
        final int[] substringBeforeToken = CodePointUtil.trim(
                Arrays.copyOfRange(currentLine, 0, colNo)
        );

        if (option == WrapOption.EOL
                && substringBeforeToken.length == 0) {
            log(ast, MSG_LINE_PREVIOUS, text);
        }
        else if (option == WrapOption.NL
                 && substringAfterToken.length == 0) {
            log(ast, MSG_LINE_NEW, text);
        }
    }

}
