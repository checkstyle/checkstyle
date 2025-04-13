////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the style of elements in annotations.
 * </div>
 *
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
 *
 * <p>
 * To not enforce an element style a {@code ElementStyleOption.IGNORE} type is provided.
 * The desired style can be set through the {@code elementStyle} property.
 * </p>
 *
 * <p>
 * Using the {@code ElementStyleOption.EXPANDED} style is more verbose.
 * The expanded version is sometimes referred to as "named parameters" in other languages.
 * </p>
 *
 * <p>
 * Using the {@code ElementStyleOption.COMPACT} style is less verbose.
 * This style can only be used when there is an element called 'value' which is either
 * the sole element or all other elements have default values.
 * </p>
 *
 * <p>
 * Using the {@code ElementStyleOption.COMPACT_NO_ARRAY} style is less verbose.
 * It is similar to the {@code ElementStyleOption.COMPACT} style but single value arrays are
 * flagged.
 * With annotations a single value array does not need to be placed in an array initializer.
 * </p>
 *
 * <p>
 * The ending parenthesis are optional when using annotations with no elements.
 * To always require ending parenthesis use the {@code ClosingParensOption.ALWAYS} type.
 * To never have ending parenthesis use the {@code ClosingParensOption.NEVER} type.
 * To not enforce a closing parenthesis preference a {@code ClosingParensOption.IGNORE} type is
 * provided.
 * Set this through the {@code closingParens} property.
 * </p>
 *
 * <p>
 * Annotations also allow you to specify arrays of elements in a standard format.
 * As with normal arrays, a trailing comma is optional.
 * To always require a trailing comma use the {@code TrailingArrayCommaOption.ALWAYS} type.
 * To never have a trailing comma use the {@code TrailingArrayCommaOption.NEVER} type.
 * To not enforce a trailing array comma preference a {@code TrailingArrayCommaOption.IGNORE} type
 * is provided. Set this through the {@code trailingArrayComma} property.
 * </p>
 *
 * <p>
 * By default, the {@code ElementStyleOption} is set to {@code COMPACT_NO_ARRAY},
 * the {@code TrailingArrayCommaOption} is set to {@code NEVER},
 * and the {@code ClosingParensOption} is set to {@code NEVER}.
 * </p>
 *
 * <p>
 * According to the JLS, it is legal to include a trailing comma
 * in arrays used in annotations but Sun's Java 5 &amp; 6 compilers will not
 * compile with this syntax. This may in be a bug in Sun's compilers
 * since eclipse 3.4's built-in compiler does allow this syntax as
 * defined in the JLS. Note: this was tested with compilers included with
 * JDK versions 1.5.0.17 and 1.6.0.11 and the compiler included with eclipse 3.4.1.
 * </p>
 *
 * <p>
 * See <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-9.html#jls-9.7">
 * Java Language specification, &#167;9.7</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code closingParens} - Define the policy for ending parenthesis.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$ClosingParensOption}.
 * Default value is {@code never}.
 * </li>
 * <li>
 * Property {@code elementStyle} - Define the annotation element styles.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$ElementStyleOption}.
 * Default value is {@code compact_no_array}.
 * </li>
 * <li>
 * Property {@code trailingArrayComma} - Define the policy for trailing comma in arrays.
 * Type is {@code
 * com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck$TrailingArrayCommaOption}.
 * Default value is {@code never}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
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
public final class AnnotationUseStyleCheck extends AbstractCheck {

    /**
     * Defines the styles for defining elements in an annotation.
     */
    public enum ElementStyleOption {

        /**
         * Expanded example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused",})</pre>.
         */
        EXPANDED,

        /**
         * Compact example
         *
         * <pre>@SuppressWarnings({"unchecked","unused",})</pre>
         * <br>or<br>
         * <pre>@SuppressWarnings("unchecked")</pre>.
         */
        COMPACT,

        /**
         * Compact example
         *
         * <pre>@SuppressWarnings("unchecked")</pre>.
         */
        COMPACT_NO_ARRAY,

        /**
         * Mixed styles.
         */
        IGNORE,

    }

    /**
     * Defines the two styles for defining
     * elements in an annotation.
     *
     */
    public enum TrailingArrayCommaOption {

        /**
         * With comma example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused",})</pre>.
         */
        ALWAYS,

        /**
         * Without comma example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused"})</pre>.
         */
        NEVER,

        /**
         * Mixed styles.
         */
        IGNORE,

    }

    /**
     * Defines the two styles for defining
     * elements in an annotation.
     *
     */
    public enum ClosingParensOption {

        /**
         * With parens example
         *
         * <pre>@Deprecated()</pre>.
         */
        ALWAYS,

        /**
         * Without parens example
         *
         * <pre>@Deprecated</pre>.
         */
        NEVER,

        /**
         * Mixed styles.
         */
        IGNORE,

    }

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_INCORRECT_STYLE =
        "annotation.incorrect.style";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_PARENS_MISSING =
        "annotation.parens.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_PARENS_PRESENT =
        "annotation.parens.present";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING =
        "annotation.trailing.comma.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT =
        "annotation.trailing.comma.present";

    /**
     * The element name used to receive special linguistic support
     * for annotation use.
     */
    private static final String ANNOTATION_ELEMENT_SINGLE_NAME =
        "value";

    /**
     * Define the annotation element styles.
     */
    private ElementStyleOption elementStyle = ElementStyleOption.COMPACT_NO_ARRAY;

    // defaulting to NEVER because of the strange compiler behavior
    /**
     * Define the policy for trailing comma in arrays.
     */
    private TrailingArrayCommaOption trailingArrayComma = TrailingArrayCommaOption.NEVER;

    /**
     * Define the policy for ending parenthesis.
     */
    private ClosingParensOption closingParens = ClosingParensOption.NEVER;

    /**
     * Setter to define the annotation element styles.
     *
     * @param style string representation
     * @since 5.0
     */
    public void setElementStyle(final String style) {
        elementStyle = getOption(ElementStyleOption.class, style);
    }

    /**
     * Setter to define the policy for trailing comma in arrays.
     *
     * @param comma string representation
     * @since 5.0
     */
    public void setTrailingArrayComma(final String comma) {
        trailingArrayComma = getOption(TrailingArrayCommaOption.class, comma);
    }

    /**
     * Setter to define the policy for ending parenthesis.
     *
     * @param parens string representation
     * @since 5.0
     */
    public void setClosingParens(final String parens) {
        closingParens = getOption(ClosingParensOption.class, parens);
    }

    /**
     * Retrieves an {@link Enum Enum} type from a @{link String String}.
     *
     * @param <T> the enum type
     * @param enumClass the enum class
     * @param value the string representing the enum
     * @return the enum type
     * @throws IllegalArgumentException when unable to parse value
     */
    private static <T extends Enum<T>> T getOption(final Class<T> enumClass,
                                                   final String value) {
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (final IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + value, iae);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.ANNOTATION,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(final DetailAST ast) {
        checkStyleType(ast);
        checkCheckClosingParensOption(ast);
        checkTrailingComma(ast);
    }

    /**
     * Checks to see if the
     * {@link ElementStyleOption AnnotationElementStyleOption}
     * is correct.
     *
     * @param annotation the annotation token
     */
    private void checkStyleType(final DetailAST annotation) {
        switch (elementStyle) {
            case COMPACT_NO_ARRAY:
                checkCompactNoArrayStyle(annotation);
                break;
            case COMPACT:
                checkCompactStyle(annotation);
                break;
            case EXPANDED:
                checkExpandedStyle(annotation);
                break;
            case IGNORE:
            default:
                break;
        }
    }

    /**
     * Checks for expanded style type violations.
     *
     * @param annotation the annotation token
     */
    private void checkExpandedStyle(final DetailAST annotation) {
        final int valuePairCount =
            annotation.getChildCount(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        if (valuePairCount == 0 && hasArguments(annotation)) {
            log(annotation, MSG_KEY_ANNOTATION_INCORRECT_STYLE, ElementStyleOption.EXPANDED);
        }
    }

    /**
     * Checks that annotation has arguments.
     *
     * @param annotation to check
     * @return true if annotation has arguments, false otherwise
     */
    private static boolean hasArguments(DetailAST annotation) {
        final DetailAST firstToken = annotation.findFirstToken(TokenTypes.LPAREN);
        return firstToken != null && firstToken.getNextSibling().getType() != TokenTypes.RPAREN;
    }

    /**
     * Checks for compact style type violations.
     *
     * @param annotation the annotation token
     */
    private void checkCompactStyle(final DetailAST annotation) {
        final int valuePairCount =
            annotation.getChildCount(
                TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        final DetailAST valuePair =
            annotation.findFirstToken(
                TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        if (valuePairCount == 1
            && ANNOTATION_ELEMENT_SINGLE_NAME.equals(
            valuePair.getFirstChild().getText())) {
            log(annotation, MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                ElementStyleOption.COMPACT);
        }
    }

    /**
     * Checks for compact no array style type violations.
     *
     * @param annotation the annotation token
     */
    private void checkCompactNoArrayStyle(final DetailAST annotation) {
        final DetailAST arrayInit =
            annotation.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);

        // in compact style with one value
        if (arrayInit != null
            && arrayInit.getChildCount(TokenTypes.EXPR) == 1) {
            log(annotation, MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                ElementStyleOption.COMPACT_NO_ARRAY);
        }
        // in expanded style with pairs
        else {
            DetailAST ast = annotation.getFirstChild();
            while (ast != null) {
                final DetailAST nestedArrayInit =
                    ast.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
                if (nestedArrayInit != null
                    && nestedArrayInit.getChildCount(TokenTypes.EXPR) == 1) {
                    log(annotation, MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                        ElementStyleOption.COMPACT_NO_ARRAY);
                }
                ast = ast.getNextSibling();
            }
        }
    }

    /**
     * Checks to see if the trailing comma is present if required or
     * prohibited.
     *
     * @param annotation the annotation token
     */
    private void checkTrailingComma(final DetailAST annotation) {
        if (trailingArrayComma != TrailingArrayCommaOption.IGNORE) {
            DetailAST child = annotation.getFirstChild();

            while (child != null) {
                DetailAST arrayInit = null;

                if (child.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
                    arrayInit = child.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
                }
                else if (child.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) {
                    arrayInit = child;
                }

                if (arrayInit != null) {
                    logCommaViolation(arrayInit);
                }
                child = child.getNextSibling();
            }
        }
    }

    /**
     * Logs a trailing array comma violation if one exists.
     *
     * @param ast the array init
     *     {@link TokenTypes#ANNOTATION_ARRAY_INIT ANNOTATION_ARRAY_INIT}.
     */
    private void logCommaViolation(final DetailAST ast) {
        final DetailAST rCurly = ast.findFirstToken(TokenTypes.RCURLY);

        // comma can be null if array is empty
        final DetailAST comma = rCurly.getPreviousSibling();

        if (trailingArrayComma == TrailingArrayCommaOption.NEVER) {
            if (comma != null && comma.getType() == TokenTypes.COMMA) {
                log(comma, MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT);
            }
        }
        else if (comma == null || comma.getType() != TokenTypes.COMMA) {
            log(rCurly, MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING);
        }
    }

    /**
     * Checks to see if the closing parenthesis are present if required or
     * prohibited.
     *
     * @param ast the annotation token
     */
    private void checkCheckClosingParensOption(final DetailAST ast) {
        if (closingParens != ClosingParensOption.IGNORE) {
            final DetailAST paren = ast.getLastChild();

            if (closingParens == ClosingParensOption.NEVER) {
                if (paren.getPreviousSibling().getType() == TokenTypes.LPAREN) {
                    log(ast, MSG_KEY_ANNOTATION_PARENS_PRESENT);
                }
            }
            else if (paren.getType() != TokenTypes.RPAREN) {
                log(ast, MSG_KEY_ANNOTATION_PARENS_MISSING);
            }
        }
    }

}
