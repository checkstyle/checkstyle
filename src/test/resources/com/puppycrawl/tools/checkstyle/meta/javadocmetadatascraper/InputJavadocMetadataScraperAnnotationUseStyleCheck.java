/*
com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper
writeXmlOutput = false


*/

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;



/**
 * <p>
 * Checks the style of elements in annotations.
 * </p>
 * <p>
 * Annotations have three element styles starting with the least verbose.
 * </p>
 * <ul>
 * <li>
 * {@code ElementStyleOption.COMPACT_NO_ARRAY}
 * </li>
 * <li>
 * {@code ElementStyleOption.COMPACT}
 * </li>
 * <li>
 * {@code ElementStyleOption.EXPANDED}
 * </li>
 * </ul>
 * <p>
 * To not enforce an element style a {@code ElementStyleOption.IGNORE} type is provided.
 * The desired style can be set through the {@code elementStyle} property.
 * </p>
 * <p>
 * Using the {@code ElementStyleOption.EXPANDED} style is more verbose.
 * The expanded version is sometimes referred to as "named parameters" in other languages.
 * </p>
 * <p>
 * Using the {@code ElementStyleOption.COMPACT} style is less verbose.
 * This style can only be used when there is an element called 'value' which is either
 * the sole element or all other elements have default values.
 * </p>
 * <p>
 * Using the {@code ElementStyleOption.COMPACT_NO_ARRAY} style is less verbose.
 * It is similar to the {@code ElementStyleOption.COMPACT} style but single value arrays are
 * flagged.
 * With annotations a single value array does not need to be placed in an array initializer.
 * </p>
 * <p>
 * The ending parenthesis are optional when using annotations with no elements.
 * To always require ending parenthesis use the {@code ClosingParensOption.ALWAYS} type.
 * To never have ending parenthesis use the {@code ClosingParensOption.NEVER} type.
 * To not enforce a closing parenthesis preference a {@code ClosingParensOption.IGNORE} type is
 * provided.
 * Set this through the {@code closingParens} property.
 * </p>
 * <p>
 * Annotations also allow you to specify arrays of elements in a standard format.
 * As with normal arrays, a trailing comma is optional.
 * To always require a trailing comma use the {@code TrailingArrayCommaOption.ALWAYS} type.
 * To never have a trailing comma use the {@code TrailingArrayCommaOption.NEVER} type.
 * To not enforce a trailing array comma preference a {@code TrailingArrayCommaOption.IGNORE} type
 * is provided. Set this through the {@code trailingArrayComma} property.
 * </p>
 * <p>
 * By default, the {@code ElementStyleOption} is set to {@code COMPACT_NO_ARRAY},
 * the {@code TrailingArrayCommaOption} is set to {@code NEVER},
 * and the {@code ClosingParensOption} is set to {@code NEVER}.
 * </p>
 * <p>
 * According to the JLS, it is legal to include a trailing comma
 * in arrays used in annotations but Sun's Java 5 &amp; 6 compilers will not
 * compile with this syntax. This may in be a bug in Sun's compilers
 * since eclipse 3.4's built-in compiler does allow this syntax as
 * defined in the JLS. Note: this was tested with compilers included with
 * JDK versions 1.5.0.17 and 1.6.0.11 and the compiler included with eclipse 3.4.1.
 * </p>
 * <p>
 * See <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-9.html#jls-9.7">
 * Java Language specification, &#167;9.7</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code elementStyle} - Define the annotation element styles.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$ElementStyleOption}.
 * Default value is {@code compact_no_array}.
 * </li>
 * <li>
 * Property {@code closingParens} - Define the policy for ending parenthesis.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$ClosingParensOption}.
 * Default value is {@code never}.
 * </li>
 * <li>
 * Property {@code trailingArrayComma} - Define the policy for trailing comma in arrays.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$TrailingArrayCommaOption}.
 * Default value is {@code never}.
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
 * {@code annotation.incorrect.style}
 * </li>
 * <li>
 * {@code annotation.parens.missing}
 * </li>
 * <li>
 * {@code annotation.parens.present}
 * </li>
 * <li>
 * {@code annotation.trailing.comma.missing}
 * </li>
 * <li>
 * {@code annotation.trailing.comma.present}
 * </li>
 * </ul>
 *
 * @since 5.0
 *
 */
@StatelessCheck
public abstract class InputJavadocMetadataScraperAnnotationUseStyleCheck extends AbstractCheck {
}
