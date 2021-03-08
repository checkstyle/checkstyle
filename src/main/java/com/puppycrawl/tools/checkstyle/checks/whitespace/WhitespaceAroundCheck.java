////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
 * Checks that a token is surrounded by whitespace. Empty constructor,
 * method, class, enum, interface, loop bodies (blocks), lambdas of the form
 * </p>
 * <pre>
 * public MyClass() {}      // empty constructor
 * public void func() {}    // empty method
 * public interface Foo {} // empty interface
 * public class Foo {} // empty class
 * public enum Foo {} // empty enum
 * MyClass c = new MyClass() {}; // empty anonymous class
 * while (i = 1) {} // empty while loop
 * for (int i = 1; i &gt; 1; i++) {} // empty for loop
 * do {} while (i = 1); // empty do-while loop
 * Runnable noop = () -&gt; {}; // empty lambda
 * public @interface Beta {} // empty annotation type
 * </pre>
 * <p>
 * may optionally be exempted from the policy using the {@code allowEmptyMethods},
 * {@code allowEmptyConstructors}, {@code allowEmptyTypes}, {@code allowEmptyLoops},
 * {@code allowEmptyLambdas} and {@code allowEmptyCatches} properties.
 * </p>
 * <p>
 * This check does not flag as violation double brace initialization like:
 * </p>
 * <pre>
 * new Properties() {{
 *     setProperty("key", "value");
 * }};
 * </pre>
 * <p>
 * Parameter allowEmptyCatches allows to suppress violations when token list
 * contains SLIST to check if beginning of block is surrounded by whitespace
 * and catch block is empty, for example:
 * </p>
 * <pre>
 * try {
 *     k = 5 / i;
 * } catch (ArithmeticException ex) {}
 * </pre>
 * <p>
 * With this property turned off, this raises violation because the beginning
 * of the catch block (left curly bracket) is not separated from the end
 * of the catch block (right curly bracket).
 * </p>
 * <ul>
 * <li>
 * Property {@code allowEmptyConstructors} - Allow empty constructor bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowEmptyMethods} - Allow empty method bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowEmptyTypes} - Allow empty class, interface and enum bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowEmptyLoops} - Allow empty loop bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowEmptyLambdas} - Allow empty lambda bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowEmptyCatches} - Allow empty catch bodies.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreEnhancedForColon} - Ignore whitespace around colon in
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
 * enhanced for</a> loop.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ASSIGN">
 * ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND">
 * BAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND_ASSIGN">
 * BAND_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR">
 * BOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR_ASSIGN">
 * BOR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR">
 * BSR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR_ASSIGN">
 * BSR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR">
 * BXOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR_ASSIGN">
 * BXOR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COLON">
 * COLON</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV">
 * DIV</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV_ASSIGN">
 * DIV_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DO_WHILE">
 * DO_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EQUAL">
 * EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GE">
 * GE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GT">
 * GT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAND">
 * LAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LCURLY">
 * LCURLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LE">
 * LE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">
 * LITERAL_DO</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">
 * LITERAL_ELSE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">
 * LITERAL_FINALLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">
 * LITERAL_FOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_RETURN">
 * LITERAL_RETURN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">
 * LITERAL_SWITCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">
 * LITERAL_SYNCHRONIZED</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">
 * LITERAL_TRY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">
 * LITERAL_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LOR">
 * LOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LT">
 * LT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS">
 * MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS_ASSIGN">
 * MINUS_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD">
 * MOD</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD_ASSIGN">
 * MOD_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NOT_EQUAL">
 * NOT_EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS">
 * PLUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS_ASSIGN">
 * PLUS_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RCURLY">
 * RCURLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL">
 * SL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SLIST">
 * SLIST</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL_ASSIGN">
 * SL_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR">
 * SR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR_ASSIGN">
 * SR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR">
 * STAR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR_ASSIGN">
 * STAR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ASSERT">
 * LITERAL_ASSERT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#TYPE_EXTENSION_AND">
 * TYPE_EXTENSION_AND</a>.
 * </li>
 * </ul>
 * <p>To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;/&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public Test(){} // 2 violations, '{' is not followed and preceded by whitespace.
 *     public static void main(String[] args) {
 *         if (foo) { // ok
 *             // body
 *         }
 *         else{ // violation
 *             // body
 *         }
 *
 *         for (int i = 1; i &gt; 1; i++) {} // violation, '{' is not followed by whitespace.
 *
 *         Runnable noop = () -&gt;{}; // 2 violations,
 *                                     // '{' is not followed and preceded by whitespace.
 *         try {
 *             // body
 *         } catch (Exception e){} // 2 violations,
 *                                 // '{' is not followed and preceded by whitespace.
 *
 *         char[] vowels = {'a', 'e', 'i', 'o', 'u'};
 *         for (char item: vowels) { // ok, because ignoreEnhancedForColon is true by default
 *             // body
 *         }
 *     }
 * }
 * </pre>
 * <p>To configure the check for whitespace only around
 * assignment operators:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;tokens&quot;
 *     value=&quot;ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,
 *            MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,
 *            BOR_ASSIGN,BAND_ASSIGN&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public static void main(String[] args) {
 *         int b=10; // violation
 *         int c = 10; // ok
 *         b+=10; // violation
 *         b += 10; // ok
 *         c*=10; // violation
 *         c *= 10; // ok
 *         c-=5; // violation
 *         c -= 5; // ok
 *         c/=2; // violation
 *         c /= 2; // ok
 *         c%=1; // violation
 *         c %= 1; // ok
 *         c&gt;&gt;=1; // violation
 *         c &gt;&gt;= 1; // ok
 *         c&gt;&gt;&gt;=1; // violation
 *         c &gt;&gt;&gt;= 1; // ok
 *     }
 *     public void myFunction() {
 *         c^=1; // violation
 *         c ^= 1; // ok
 *         c|=1; // violation
 *         c |= 1; // ok
 *         c&amp;=1; // violation
 *         c &amp;= 1; // ok
 *         c&lt;&lt;=1; // violation
 *         c &lt;&lt;= 1; // ok
 *     }
 * }
 * </pre>
 * <p>To configure the check for whitespace only around curly braces:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LCURLY,RCURLY&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public void myFunction() {} // violation
 *     public void myFunction() { } // ok
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty method bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyMethods&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public void muFunction() {} // ok
 *     int a=4; // 2 violations, '=' is not followed and preceded by whitespace.
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty constructor bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyConstructors&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public Test(){} // ok
 *     public void muFunction() {} // violation, '{' is not followed by whitespace.
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty type bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyTypes&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {} // ok
 * interface testInterface{} // ok
 * class anotherTest {
 *     int a=4; // 2 violations, '=' is not followed and preceded by whitespace.
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty loop bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyLoops&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public static void main(String[] args) {
 *         for (int i = 100;i &gt; 10; i--){} // ok
 *         do {} while (i = 1); // ok
 *         int a=4; // 2 violations, '=' is not followed and preceded by whitespace.
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty lambda bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyLambdas&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public static void main(String[] args) {
 *         Runnable noop = () -&gt; {}; // ok
 *         int a=4; // 2 violations, '=' is not followed and preceded by whitespace.
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check to allow empty catch bodies:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;allowEmptyCatches&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public static void main(String[] args) {
 *         int a=4; // 2 violations, '=' is not followed and preceded by whitespace.
 *         try {
 *             // body
 *         } catch (Exception e){} // ok
 *     }
 * }
 * </pre>
 * <p>
 * Also, this check can be configured to ignore the colon in an enhanced for
 * loop. The colon in an enhanced for loop is ignored by default.
 * </p>
 * <p>
 * To configure the check to ignore the colon:
 * </p>
 * <pre>
 * &lt;module name=&quot;WhitespaceAround&quot;&gt;
 *   &lt;property name=&quot;ignoreEnhancedForColon&quot; value=&quot;false&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:
 * </p>
 * <pre>
 * class Test {
 *     public static void main(String[] args) {
 *         int a=4; // 2 violations , '=' is not followed and preceded by whitespace.
 *         char[] vowels = {'a', 'e', 'i', 'o', 'u'};
 *         for (char item: vowels) { // violation, ':' is not preceded by whitespace.
 *             // body
 *         }
 *     }
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
 * {@code ws.notFollowed}
 * </li>
 * <li>
 * {@code ws.notPreceded}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class WhitespaceAroundCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /** Allow empty constructor bodies. */
    private boolean allowEmptyConstructors;
    /** Allow empty method bodies. */
    private boolean allowEmptyMethods;
    /** Allow empty class, interface and enum bodies. */
    private boolean allowEmptyTypes;
    /** Allow empty loop bodies. */
    private boolean allowEmptyLoops;
    /** Allow empty lambda bodies. */
    private boolean allowEmptyLambdas;
    /** Allow empty catch bodies. */
    private boolean allowEmptyCatches;
    /**
     * Ignore whitespace around colon in
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for</a> loop.
     */
    private boolean ignoreEnhancedForColon = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ASSIGN,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.DO_WHILE,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAMBDA,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ASSIGN,
            TokenTypes.ARRAY_INIT,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.DO_WHILE,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAMBDA,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.WILDCARD_TYPE,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
            TokenTypes.ELLIPSIS,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * Setter to allow empty method bodies.
     *
     * @param allow {@code true} to allow empty method bodies.
     */
    public void setAllowEmptyMethods(boolean allow) {
        allowEmptyMethods = allow;
    }

    /**
     * Setter to allow empty constructor bodies.
     *
     * @param allow {@code true} to allow empty constructor bodies.
     */
    public void setAllowEmptyConstructors(boolean allow) {
        allowEmptyConstructors = allow;
    }

    /**
     * Setter to ignore whitespace around colon in
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for</a> loop.
     *
     * @param ignore {@code true} to ignore enhanced for colon.
     */
    public void setIgnoreEnhancedForColon(boolean ignore) {
        ignoreEnhancedForColon = ignore;
    }

    /**
     * Setter to allow empty class, interface and enum bodies.
     *
     * @param allow {@code true} to allow empty type bodies.
     */
    public void setAllowEmptyTypes(boolean allow) {
        allowEmptyTypes = allow;
    }

    /**
     * Setter to allow empty loop bodies.
     *
     * @param allow {@code true} to allow empty loops bodies.
     */
    public void setAllowEmptyLoops(boolean allow) {
        allowEmptyLoops = allow;
    }

    /**
     * Setter to allow empty lambda bodies.
     *
     * @param allow {@code true} to allow empty lambda expressions.
     */
    public void setAllowEmptyLambdas(boolean allow) {
        allowEmptyLambdas = allow;
    }

    /**
     * Setter to allow empty catch bodies.
     *
     * @param allow {@code true} to allow empty catch blocks.
     */
    public void setAllowEmptyCatches(boolean allow) {
        allowEmptyCatches = allow;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int currentType = ast.getType();
        if (!isNotRelevantSituation(ast, currentType)) {
            final String line = getLine(ast.getLineNo() - 1);
            final int before = ast.getColumnNo() - 1;
            final int after = ast.getColumnNo() + ast.getText().length();

            if (before >= 0) {
                final char prevChar = line.charAt(before);
                if (shouldCheckSeparationFromPreviousToken(ast)
                        && !Character.isWhitespace(prevChar)) {
                    log(ast, MSG_WS_NOT_PRECEDED, ast.getText());
                }
            }

            if (after < line.length()) {
                final char nextChar = line.charAt(after);
                if (shouldCheckSeparationFromNextToken(ast, nextChar)
                        && !Character.isWhitespace(nextChar)) {
                    log(ast, MSG_WS_NOT_FOLLOWED, ast.getText());
                }
            }
        }
    }

    /**
     * Is ast not a target of Check.
     *
     * @param ast ast
     * @param currentType type of ast
     * @return true is ok to skip validation
     */
    private boolean isNotRelevantSituation(DetailAST ast, int currentType) {
        final int parentType = ast.getParent().getType();
        final boolean starImport = currentType == TokenTypes.STAR
                && parentType == TokenTypes.DOT;
        final boolean insideCaseGroup = parentType == TokenTypes.CASE_GROUP;

        final boolean starImportOrSlistInsideCaseGroup = starImport || insideCaseGroup;
        final boolean colonOfCaseOrDefaultOrForEach =
                isColonOfCaseOrDefault(parentType)
                        || isColonOfForEach(parentType);
        final boolean emptyBlockOrType =
                isEmptyBlock(ast, parentType)
                    || allowEmptyTypes && isEmptyType(ast);

        return starImportOrSlistInsideCaseGroup
                || colonOfCaseOrDefaultOrForEach
                || emptyBlockOrType
                || isArrayInitialization(currentType, parentType);
    }

    /**
     * Check if it should be checked if previous token is separated from current by
     * whitespace.
     * This function is needed to recognise double brace initialization as valid,
     * unfortunately its not possible to implement this functionality
     * in isNotRelevantSituation method, because in this method when we return
     * true(is not relevant) ast is later doesn't check at all. For example:
     * new Properties() {{setProperty("double curly braces", "are not a style violation");
     * }};
     * For second left curly brace in first line when we would return true from
     * isNotRelevantSituation it wouldn't later check that the next token(setProperty)
     * is not separated from previous token.
     *
     * @param ast current AST.
     * @return true if it should be checked if previous token is separated by whitespace,
     *      false otherwise.
     */
    private static boolean shouldCheckSeparationFromPreviousToken(DetailAST ast) {
        return !isPartOfDoubleBraceInitializerForPreviousToken(ast);
    }

    /**
     * Check if it should be checked if next token is separated from current by
     * whitespace. Explanation why this method is needed is identical to one
     * included in shouldCheckSeparationFromPreviousToken method.
     *
     * @param ast current AST.
     * @param nextChar next character.
     * @return true if it should be checked if next token is separated by whitespace,
     *      false otherwise.
     */
    private static boolean shouldCheckSeparationFromNextToken(DetailAST ast, char nextChar) {
        return !(ast.getType() == TokenTypes.LITERAL_RETURN
                    && ast.getFirstChild().getType() == TokenTypes.SEMI)
                && ast.getType() != TokenTypes.ARRAY_INIT
                && !isAnonymousInnerClassEnd(ast.getType(), nextChar)
                && !isPartOfDoubleBraceInitializerForNextToken(ast);
    }

    /**
     * Check for "})" or "};" or "},". Happens with anon-inners
     *
     * @param currentType token
     * @param nextChar next symbol
     * @return true is that is end of anon inner class
     */
    private static boolean isAnonymousInnerClassEnd(int currentType, char nextChar) {
        return currentType == TokenTypes.RCURLY
                && (nextChar == ')'
                        || nextChar == ';'
                        || nextChar == ','
                        || nextChar == '.');
    }

    /**
     * Is empty block.
     *
     * @param ast ast
     * @param parentType parent
     * @return true is block is empty
     */
    private boolean isEmptyBlock(DetailAST ast, int parentType) {
        return isEmptyMethodBlock(ast, parentType)
                || isEmptyCtorBlock(ast, parentType)
                || isEmptyLoop(ast, parentType)
                || isEmptyLambda(ast, parentType)
                || isEmptyCatch(ast, parentType);
    }

    /**
     * Tests if a given {@code DetailAST} is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   public void myMethod(int val) {}</pre>
     * </p>
     * In the above, the method body is an empty block ("{}").
     *
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @param match the parent token type we're looking to match.
     * @return {@code true} if {@code ast} makes up part of an
     *         empty block contained under a {@code match} token type
     *         node.
     */
    private static boolean isEmptyBlock(DetailAST ast, int parentType, int match) {
        final boolean result;
        final int type = ast.getType();
        if (type == TokenTypes.RCURLY) {
            final DetailAST parent = ast.getParent();
            final DetailAST grandParent = ast.getParent().getParent();
            result = parent.getFirstChild().getType() == TokenTypes.RCURLY
                    && grandParent.getType() == match;
        }
        else {
            result = type == TokenTypes.SLIST
                && parentType == match
                && ast.getFirstChild().getType() == TokenTypes.RCURLY;
        }
        return result;
    }

    /**
     * Whether colon belongs to cases or defaults.
     *
     * @param parentType parent
     * @return true if current token in colon of case or default tokens
     */
    private static boolean isColonOfCaseOrDefault(int parentType) {
        return parentType == TokenTypes.LITERAL_DEFAULT
                    || parentType == TokenTypes.LITERAL_CASE;
    }

    /**
     * Whether colon belongs to for-each.
     *
     * @param parentType parent
     * @return true if current token in colon of for-each token
     */
    private boolean isColonOfForEach(int parentType) {
        return parentType == TokenTypes.FOR_EACH_CLAUSE
                && ignoreEnhancedForColon;
    }

    /**
     * Is array initialization.
     *
     * @param currentType current token
     * @param parentType parent token
     * @return true is current token inside array initialization
     */
    private static boolean isArrayInitialization(int currentType, int parentType) {
        return currentType == TokenTypes.RCURLY
                && (parentType == TokenTypes.ARRAY_INIT
                        || parentType == TokenTypes.ANNOTATION_ARRAY_INIT);
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * method block.
     *
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty method block.
     */
    private boolean isEmptyMethodBlock(DetailAST ast, int parentType) {
        return allowEmptyMethods
                && isEmptyBlock(ast, parentType, TokenTypes.METHOD_DEF);
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * constructor (ctor) block.
     *
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty constructor block.
     */
    private boolean isEmptyCtorBlock(DetailAST ast, int parentType) {
        return allowEmptyConstructors
                && (isEmptyBlock(ast, parentType, TokenTypes.CTOR_DEF)
                    || isEmptyBlock(ast, parentType, TokenTypes.COMPACT_CTOR_DEF));
    }

    /**
     * Checks if loop is empty.
     *
     * @param ast ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty loop block.
     */
    private boolean isEmptyLoop(DetailAST ast, int parentType) {
        return allowEmptyLoops
                && (isEmptyBlock(ast, parentType, TokenTypes.LITERAL_FOR)
                        || isEmptyBlock(ast, parentType, TokenTypes.LITERAL_WHILE)
                        || isEmptyBlock(ast, parentType, TokenTypes.LITERAL_DO));
    }

    /**
     * Test if the given {@code DetailAST} is part of an allowed empty
     * lambda block.
     *
     * @param ast the {@code DetailAST} to test.
     * @param parentType the token type of {@code ast}'s parent.
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty lambda block.
     */
    private boolean isEmptyLambda(DetailAST ast, int parentType) {
        return allowEmptyLambdas && isEmptyBlock(ast, parentType, TokenTypes.LAMBDA);
    }

    /**
     * Tests if the given {@code DetailAst} is part of an allowed empty
     * catch block.
     *
     * @param ast the {@code DetailAst} to test.
     * @param parentType the token type of {@code ast}'s parent
     * @return {@code true} if {@code ast} makes up part of an
     *         allowed empty catch block.
     */
    private boolean isEmptyCatch(DetailAST ast, int parentType) {
        return allowEmptyCatches && isEmptyBlock(ast, parentType, TokenTypes.LITERAL_CATCH);
    }

    /**
     * Test if the given {@code DetailAST} is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   class Foo {}</pre>
     * </p>
     *
     * @param ast ast the {@code DetailAST} to test.
     * @return {@code true} if {@code ast} makes up part of an
     *         empty block contained under a {@code match} token type
     *         node.
     */
    private static boolean isEmptyType(DetailAST ast) {
        final int type = ast.getType();
        final DetailAST nextSibling = ast.getNextSibling();
        final DetailAST previousSibling = ast.getPreviousSibling();
        return type == TokenTypes.LCURLY
                    && nextSibling.getType() == TokenTypes.RCURLY
                || previousSibling != null
                    && previousSibling.getType() == TokenTypes.LCURLY;
    }

    /**
     * Check if given ast is part of double brace initializer and if it
     * should omit checking if previous token is separated by whitespace.
     *
     * @param ast ast to check
     * @return true if it should omit checking for previous token, false otherwise
     */
    private static boolean isPartOfDoubleBraceInitializerForPreviousToken(DetailAST ast) {
        final boolean initializerBeginsAfterClassBegins =
                ast.getParent().getType() == TokenTypes.INSTANCE_INIT;
        final boolean classEndsAfterInitializerEnds = ast.getPreviousSibling() != null
                && ast.getPreviousSibling().getType() == TokenTypes.INSTANCE_INIT;
        return initializerBeginsAfterClassBegins || classEndsAfterInitializerEnds;
    }

    /**
     * Check if given ast is part of double brace initializer and if it
     * should omit checking if next token is separated by whitespace.
     * See <a href="https://github.com/checkstyle/checkstyle/pull/2845">
     * PR#2845</a> for more information why this function was needed.
     *
     * @param ast ast to check
     * @return true if it should omit checking for next token, false otherwise
     */
    private static boolean isPartOfDoubleBraceInitializerForNextToken(DetailAST ast) {
        final boolean classBeginBeforeInitializerBegin = ast.getType() == TokenTypes.LCURLY
            && ast.getNextSibling().getType() == TokenTypes.INSTANCE_INIT;
        final boolean initializerEndsBeforeClassEnds =
            ast.getParent().getParent().getType() == TokenTypes.INSTANCE_INIT
            && ast.getParent().getParent().getNextSibling().getType() == TokenTypes.RCURLY;
        return classBeginBeforeInitializerBegin || initializerEndsBeforeClassEnds;
    }

}
