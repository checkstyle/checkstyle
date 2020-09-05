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

package com.puppycrawl.tools.checkstyle.meta.inputs.checks.blocks;

import java.util.Arrays;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks the placement of right curly braces ({@code '}'}) for code blocks. This check supports
 * if-else, try-catch-finally blocks, while-loops, for-loops,
 * method definitions, class definitions, constructor definitions,
 * instance, static initialization blocks, annotation definitions and enum definitions.
 * For right curly brace of expression blocks of arrays, lambdas and class instances
 * please follow issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/5945">#5945</a>.
 * For right curly brace of enum constant please follow issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/7519">#7519</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify the policy on placement of a right curly brace
 * (<code>'}'</code>).
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption}.
 * Default value is {@code same}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">
 * LITERAL_TRY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">
 * LITERAL_FINALLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">
 * LITERAL_ELSE</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *
 *   public void test() {
 *
 *     if (foo) {
 *       bar();
 *     }           // violation, right curly must be in the same line as the 'else' keyword
 *     else {
 *       bar();
 *     }
 *
 *     if (foo) {
 *       bar();
 *     } else {     // OK
 *       bar();
 *     }
 *
 *     if (foo) { bar(); } int i = 0; // violation
 *                   // ^^^ statement is not allowed on same line after curly right brace
 *
 *     if (foo) { bar(); }            // OK
 *     int i = 0;
 *
 *     try {
 *       bar();
 *     }           // violation, rightCurly must be in the same line as 'catch' keyword
 *     catch (Exception e) {
 *       bar();
 *     }
 *
 *     try {
 *       bar();
 *     } catch (Exception e) { // OK
 *       bar();
 *     }
 *
 *   }                         // OK
 *
 *   public void testSingleLine() { bar(); } // OK, because singleline is allowed
 * }
 * </pre>
 * <p>
 * To configure the check with policy {@code alone} for {@code else} and
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a> tokens:
 * </p>
 * <pre>
 * &lt;module name=&quot;RightCurly&quot;&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;alone&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_ELSE, METHOD_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *
 *   public void test() {
 *
 *     if (foo) {
 *       bar();
 *     } else { bar(); }   // violation, right curly must be alone on line
 *
 *     if (foo) {
 *       bar();
 *     } else {
 *       bar();
 *     }                   // OK
 *
 *     try {
 *       bar();
 *     } catch (Exception e) { // OK because config is set to token METHOD_DEF and LITERAL_ELSE
 *       bar();
 *     }
 *
 *   }                         // OK
 *
 *   public void violate() { bar; } // violation, singleline is not allowed here
 *
 *   public void ok() {
 *     bar();
 *   }                              // OK
 * }
 * </pre>
 * <p>
 * To configure the check with policy {@code alone_or_singleline} for {@code if}
 * and <a href="apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>
 * tokens:
 * </p>
 * <pre>
 * &lt;module name=&quot;RightCurly&quot;&gt;
 *  &lt;property name=&quot;option&quot; value=&quot;alone_or_singleline&quot;/&gt;
 *  &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_IF, METHOD_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *
 *   public void test() {
 *
 *     if (foo) {
 *       bar();
 *     } else {        // violation, right curly must be alone on line
 *       bar();
 *     }
 *
 *     if (foo) {
 *       bar();
 *     }               // OK
 *     else {
 *       bar();
 *     }
 *
 *     try {
 *       bar();
 *     } catch (Exception e) {        // OK because config did not set token LITERAL_TRY
 *       bar();
 *     }
 *
 *   }                                // OK
 *
 *   public void violate() { bar(); } // OK , because singleline
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
 * {@code line.alone}
 * </li>
 * <li>
 * {@code line.break.before}
 * </li>
 * <li>
 * {@code line.same}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public abstract class InputRightCurlyCheck extends AbstractCheck {

}
