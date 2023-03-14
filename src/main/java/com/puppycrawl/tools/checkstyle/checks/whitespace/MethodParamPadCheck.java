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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the padding between the identifier of a method definition,
 * constructor definition, method call, or constructor invocation;
 * and the left parenthesis of the parameter list.
 * That is, if the identifier and left parenthesis are on the same line,
 * checks whether a space is required immediately after the identifier or
 * such a space is forbidden.
 * If they are not on the same line, reports a violation, unless configured to
 * allow line breaks. To allow linebreaks after the identifier, set property
 * {@code allowLineBreaks} to {@code true}.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowLineBreaks} - Allow a line break between the identifier
 * and left parenthesis.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code option} - Specify policy on how to pad method parameter.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption}.
 * Default value is {@code nospace}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NEW">
 * LITERAL_NEW</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_CALL">
 * METHOD_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SUPER_CTOR_CALL">
 * SUPER_CTOR_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MethodParamPad"/&gt;
 * </pre>
 * <pre>
 * public class Test {
 *  public Test() { // OK
 *     super(); // OK
 *   }
 *
 *   public Test (int aParam) { // Violation - '(' is preceded with whitespace
 *     super (); // Violation - '(' is preceded with whitespace
 *   }
 *
 *   public void method() {} // OK
 *
 *   public void methodWithVeryLongName
 *     () {} // Violation - '(' is preceded with whitespace
 *
 * }
 * </pre>
 * <p>
 * To configure the check to require a space
 * after the identifier of a method definition, except if the left
 * parenthesis occurs on a new line:
 * </p>
 * <pre>
 * &lt;module name="MethodParamPad"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *   &lt;property name="option" value="space"/&gt;
 *   &lt;property name="allowLineBreaks" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public class Test {
 *   public Test() { // OK
 *     super(); // OK
 *   }
 *
 *   public Test (int aParam) { // OK
 *     super (); // OK
 *   }
 *
 *   public void method() {} // Violation - '(' is NOT preceded with whitespace
 *
 *   public void methodWithVeryLongName
 *     () {} // OK, because allowLineBreaks is true
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
 * {@code line.previous}
 * </li>
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
public class MethodParamPadCheck
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
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * Allow a line break between the identifier and left parenthesis.
     */
    private boolean allowLineBreaks;

    /** Specify policy on how to pad method parameter. */
    private PadOption option = PadOption.NOSPACE;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parenAST;
        if (ast.getType() == TokenTypes.METHOD_CALL) {
            parenAST = ast;
        }
        else {
            parenAST = ast.findFirstToken(TokenTypes.LPAREN);
            // array construction => parenAST == null
        }

        if (parenAST != null) {
            final int[] line = getLineCodePoints(parenAST.getLineNo() - 1);
            if (CodePointUtil.hasWhitespaceBefore(parenAST.getColumnNo(), line)) {
                if (!allowLineBreaks) {
                    log(parenAST, MSG_LINE_PREVIOUS, parenAST.getText());
                }
            }
            else {
                final int before = parenAST.getColumnNo() - 1;
                if (option == PadOption.NOSPACE
                    && CommonUtil.isCodePointWhitespace(line, before)) {
                    log(parenAST, MSG_WS_PRECEDED, parenAST.getText());
                }
                else if (option == PadOption.SPACE
                         && !CommonUtil.isCodePointWhitespace(line, before)) {
                    log(parenAST, MSG_WS_NOT_PRECEDED, parenAST.getText());
                }
            }
        }
    }

    /**
     * Setter to allow a line break between the identifier and left parenthesis.
     *
     * @param allowLineBreaks whether whitespace should be
     *     flagged at line breaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

    /**
     * Setter to specify policy on how to pad method parameter.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

}
