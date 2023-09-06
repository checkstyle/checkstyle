/*
com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper
writeXmlOutput = false


*/

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper;

import java.util.Arrays;
import java.util.Locale;


import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;






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
 * skipped as not relevant for UTs
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
public abstract class InputJavadocMetadataScraperRightCurlyCheck extends AbstractCheck {

}
