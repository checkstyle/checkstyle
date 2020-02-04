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
 * Checks the policy on how to wrap lines on operators.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to wrap lines.
 * Default value is {@code nl}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COLON">
 * COLON</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EQUAL">
 * EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NOT_EQUAL">
 * NOT_EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV">
 * DIV</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS">
 * PLUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS">
 * MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR">
 * STAR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD">
 * MOD</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR">
 * SR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR">
 * BSR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GE">
 * GE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GT">
 * GT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL">
 * SL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LE">
 * LE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LT">
 * LT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR">
 * BXOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR">
 * BOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LOR">
 * LOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND">
 * BAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAND">
 * LAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#TYPE_EXTENSION_AND">
 * TYPE_EXTENSION_AND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_INSTANCEOF">
 * LITERAL_INSTANCEOF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="OperatorWrap"/&gt;
 * </pre>
 * <p>
 * To configure the check for assignment operators at the end of a line:
 * </p>
 * <pre>
 * &lt;module name="OperatorWrap"&gt;
 *   &lt;property name="tokens"
 *     value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,MOD_ASSIGN,
 *            SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,BAND_ASSIGN"/&gt;
 *   &lt;property name="option" value="eol"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 3.0
 */
@StatelessCheck
public class OperatorWrapCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_NEW = "line.new";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_PREVIOUS = "line.previous";

    /** Specify policy on how to wrap lines. */
    private WrapOption option = WrapOption.NL;

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
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              // '+' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.LITERAL_INSTANCEOF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              // '+' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      // "-="
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.BAND_ASSIGN,       // "&="
            TokenTypes.METHOD_REF,        // "::"
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        // we do not want to check colon for cases and defaults
        if (parent.getType() != TokenTypes.LITERAL_DEFAULT
                && parent.getType() != TokenTypes.LITERAL_CASE) {
            final String text = ast.getText();
            final int colNo = ast.getColumnNo();
            final int lineNo = ast.getLineNo();
            final String currentLine = getLine(lineNo - 1);

            // Check if rest of line is whitespace, and not just the operator
            // by itself. This last bit is to handle the operator on a line by
            // itself.
            if (option == WrapOption.NL
                    && !text.equals(currentLine.trim())
                    && CommonUtil.isBlank(currentLine.substring(colNo + text.length()))) {
                log(ast, MSG_LINE_NEW, text);
            }
            else if (option == WrapOption.EOL
                    && CommonUtil.hasWhitespaceBefore(colNo - 1, currentLine)) {
                log(ast, MSG_LINE_PREVIOUS, text);
            }
        }
    }

}
