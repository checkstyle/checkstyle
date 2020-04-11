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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that there is no whitespace before a token.
 * More specifically, it checks that it is not preceded with whitespace,
 * or (if linebreaks are allowed) all characters on the line before are
 * whitespace. To allow linebreaks before a token, set property
 * {@code allowLineBreaks} to {@code true}. No check occurs before semi-colons in empty
 * for loop initializers or conditions. Check warns only on colons from case and default.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowLineBreaks} - Control whether whitespace is allowed
 * if the token is at a linebreak.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMMA">
 * COMMA</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SEMI">
 * SEMI</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#POST_INC">
 * POST_INC</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#POST_DEC">
 * POST_DEC</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ELLIPSIS">
 * ELLIPSIS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COLON">
 * COLON</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LABELED_STAT">
 * LABELED_STAT</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBefore&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * int foo;
 * foo ++; // violation, whitespace before '++' is not allowed
 * foo++; // OK
 * for (int i = 0 ; i &lt; 5; i++) {}  // violation
 *            // ^ whitespace before ';' is not allowed
 * for (int i = 0; i &lt; 5; i++) {} // OK
 * int[][] array = { { 1, 2 }
 *                 , { 3, 4 } }; // violation, whitespace before ',' is not allowed
 * int[][] array2 = { { 1, 2 },
 *                    { 3, 4 } }; // OK
 * Lists.charactersOf("foo").listIterator()
 *        .forEachRemaining(System.out::print)
 *        ; // violation, whitespace before ';' is not allowed
 * </pre>
 * <p>To configure the check to allow linebreaks before default tokens:</p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBefore&quot;&gt;
 *   &lt;property name=&quot;allowLineBreaks&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * int[][] array = { { 1, 2 }
 *                 , { 3, 4 } }; // OK, linebreak is allowed before ','
 * int[][] array2 = { { 1, 2 },
 *                    { 3, 4 } }; // OK, ideal code
 * void ellipsisExample(String ...params) {}; // violation, whitespace before '...' is not allowed
 * void ellipsisExample2(String
 *                         ...params) {}; //OK, linebreak is allowed before '...'
 * Lists.charactersOf("foo")
 *        .listIterator()
 *        .forEachRemaining(System.out::print); // OK
 * </pre>
 * <p>
 *     To Configure the check to restrict the use of whitespace before METHOD_REF and DOT tokens:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBefore&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_REF&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;DOT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * Lists.charactersOf("foo").listIterator()
 *        .forEachRemaining(System.out::print); // violation, whitespace before '.' is not allowed
 * Lists.charactersOf("foo").listIterator().forEachRemaining(System.out ::print); // violation,
 *                           // whitespace before '::' is not allowed  ^
 * Lists.charactersOf("foo").listIterator().forEachRemaining(System.out::print); // OK
 * </pre>
 * <p>
 *     To configure the check to allow linebreak before METHOD_REF and DOT tokens:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBefore&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_REF&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;DOT&quot;/&gt;
 *   &lt;property name=&quot;allowLineBreaks&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * Lists .charactersOf("foo") //violation, whitespace before '.' is not allowed
 *         .listIterator()
 *         .forEachRemaining(System.out ::print); // violation,
 *                                  // ^ whitespace before '::' is not allowed
 * Lists.charactersOf("foo")
 *        .listIterator()
 *        .forEachRemaining(System.out::print); // OK
 * </pre>
 * <p>
 *     To Configure the check to restrict the use of whitespace before
 *     LABELED_STAT and COLON tokens:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBefore&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LABELED_STAT&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;COLON&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Foo {
 *   {
 *     label1 : // violation, whitespace before ':' is not allowed
 *       switch(1) {
 *         case 1 : // violation, whitespace before ':' is not allowed
 *           break;
 *         case 2: // OK
 *           break;
 *         default : // violation, whitespace before ':' is not allowed
 *           break;
 *       }
 *   }
 *   {
 *     label2: // OK
 *     System.out.println();
 *     int a  = true ? 1 : 0; // OK
 *     for(String s : new String[]{}) {} // OK
 *   }
 * }
 * </pre>
 *
 * @since 3.0
 */
@StatelessCheck
public class NoWhitespaceBeforeCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.preceded";

    /** Control whether whitespace is allowed if the token is at a linebreak. */
    private boolean allowLineBreaks;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.ELLIPSIS,
            TokenTypes.COLON,
            TokenTypes.LABELED_STAT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.DOT,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
            TokenTypes.ELLIPSIS,
            TokenTypes.COLON,
            TokenTypes.LABELED_STAT,
            TokenTypes.METHOD_REF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;

        if ((before == -1 || Character.isWhitespace(line.charAt(before)))
                && !isInEmptyForInitializerOrCondition(ast)
                && (ast.getType() != TokenTypes.COLON || isColonOfCaseOrDefault(ast))) {
            boolean flag = !allowLineBreaks;
            // verify all characters before '.' are whitespace
            for (int i = 0; i <= before - 1; i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                log(ast, MSG_KEY, ast.getText());
            }
        }
    }

    /**
     * Checks that ast is a colon of switch case or default.
     * @param colonAst DetailAST to check.
     * @return true if ast is a COLON and is child of LITERAL_CASE or LITERAL_DEFAULT.
     */
    private static boolean isColonOfCaseOrDefault(DetailAST colonAst) {
        final DetailAST parent = colonAst.getParent();
        return parent.getType() == TokenTypes.LITERAL_CASE
                || parent.getType() == TokenTypes.LITERAL_DEFAULT;
    }

    /**
     * Checks that semicolon is in empty for initializer or condition.
     * @param semicolonAst DetailAST of semicolon.
     * @return true if semicolon is in empty for initializer or condition.
     */
    private static boolean isInEmptyForInitializerOrCondition(DetailAST semicolonAst) {
        boolean result = false;
        final DetailAST sibling = semicolonAst.getPreviousSibling();
        if (sibling != null
                && (sibling.getType() == TokenTypes.FOR_INIT
                        || sibling.getType() == TokenTypes.FOR_CONDITION)
                && !sibling.hasChildren()) {
            result = true;
        }
        return result;
    }

    /**
     * Setter to control whether whitespace is allowed if the token is at a linebreak.
     * @param allowLineBreaks whether whitespace should be
     *     flagged at line breaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

}
