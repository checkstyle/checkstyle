////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This check controls the style with the usage of annotations.
 *
 * <p>Annotations have three element styles starting with the least verbose.
 * <ul>
 * <li>{@link ElementStyle#COMPACT_NO_ARRAY COMPACT_NO_ARRAY}</li>
 * <li>{@link ElementStyle#COMPACT COMPACT}</li>
 * <li>{@link ElementStyle#EXPANDED EXPANDED}</li>
 * </ul>
 * To not enforce an element style
 * a {@link ElementStyle#IGNORE IGNORE} type is provided.  The desired style
 * can be set through the {@code elementStyle} property.
 *
 * <p>Using the EXPANDED style is more verbose. The expanded version
 * is sometimes referred to as "named parameters" in other languages.
 *
 * <p>Using the COMPACT style is less verbose. This style can only
 * be used when there is an element called 'value' which is either
 * the sole element or all other elements have default values.
 *
 * <p>Using the COMPACT_NO_ARRAY style is less verbose. It is similar
 * to the COMPACT style but single value arrays are flagged. With
 * annotations a single value array does not need to be placed in an
 * array initializer. This style can only be used when there is an
 * element called 'value' which is either the sole element or all other
 * elements have default values.
 *
 * <p>The ending parenthesis are optional when using annotations with no elements.
 * To always require ending parenthesis use the
 * {@link ClosingParens#ALWAYS ALWAYS} type.  To never have ending parenthesis
 * use the {@link ClosingParens#NEVER NEVER} type. To not enforce a
 * closing parenthesis preference a {@link ClosingParens#IGNORE IGNORE} type is
 * provided. Set this through the {@code closingParens} property.
 *
 * <p>Annotations also allow you to specify arrays of elements in a standard
 * format.  As with normal arrays, a trailing comma is optional. To always
 * require a trailing comma use the {@link TrailingArrayComma#ALWAYS ALWAYS}
 * type. To never have a trailing comma use the
 * {@link TrailingArrayComma#NEVER NEVER} type. To not enforce a trailing
 * array comma preference a {@link TrailingArrayComma#IGNORE IGNORE} type
 * is provided.  Set this through the {@code trailingArrayComma} property.
 *
 * <p>By default the ElementStyle is set to EXPANDED, the TrailingArrayComma
 * is set to NEVER, and the ClosingParens is set to ALWAYS.
 *
 * <p>According to the JLS, it is legal to include a trailing comma
 * in arrays used in annotations but Sun's Java 5 &amp; 6 compilers will not
 * compile with this syntax. This may in be a bug in Sun's compilers
 * since eclipse 3.4's built-in compiler does allow this syntax as
 * defined in the JLS. Note: this was tested with compilers included with
 * JDK versions 1.5.0.17 and 1.6.0.11 and the compiler included with eclipse
 * 3.4.1.
 *
 * <p>See <a
 * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.7">
 * Java Language specification, &sect;9.7</a>.
 *
 * <p>An example shown below is set to enforce an EXPANDED style, with a
 * trailing array comma set to NEVER and always including the closing
 * parenthesis.
 *
 * <pre>
 * &lt;module name=&quot;AnnotationUseStyle&quot;&gt;
 *    &lt;property name=&quot;ElementStyle&quot;
 *        value=&quot;EXPANDED&quot;/&gt;
 *    &lt;property name=&quot;TrailingArrayComma&quot;
 *        value=&quot;NEVER&quot;/&gt;
 *    &lt;property name=&quot;ClosingParens&quot;
 *        value=&quot;ALWAYS&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Travis Schneeberger
 */
public final class AnnotationUseStyleCheck extends AbstractCheck {

    /**
     * Defines the styles for defining elements in an annotation.
     * @author Travis Schneeberger
     */
    public enum ElementStyle {

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
         * Compact example.]
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
     * @author Travis Schneeberger
     */
    public enum TrailingArrayComma {

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
     * @author Travis Schneeberger
     */
    public enum ClosingParens {

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

    //not extending AbstractOptionCheck because check
    //has more than one option type.

    /**
     * ElementStyle option.
     * @see #setElementStyle(String)
     */
    private ElementStyle elementStyle = ElementStyle.COMPACT_NO_ARRAY;

    //defaulting to NEVER because of the strange compiler behavior
    /**
     * Trailing array comma option.
     * @see #setTrailingArrayComma(String)
     */
    private TrailingArrayComma trailingArrayComma = TrailingArrayComma.NEVER;

    /**
     * Closing parens option.
     * @see #setClosingParens(String)
     */
    private ClosingParens closingParens = ClosingParens.NEVER;

    /**
     * Sets the ElementStyle from a string.
     *
     * @param style string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setElementStyle(final String style) {
        elementStyle = getOption(ElementStyle.class, style);
    }

    /**
     * Sets the TrailingArrayComma from a string.
     *
     * @param comma string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setTrailingArrayComma(final String comma) {
        trailingArrayComma = getOption(TrailingArrayComma.class, comma);
    }

    /**
     * Sets the ClosingParens from a string.
     *
     * @param parens string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setClosingParens(final String parens) {
        closingParens = getOption(ClosingParens.class, parens);
    }

    /**
     * Retrieves an {@link Enum Enum} type from a @{link String String}.
     * @param <T> the enum type
     * @param enumClass the enum class
     * @param value the string representing the enum
     * @return the enum type
     */
    private static <T extends Enum<T>> T getOption(final Class<T> enumClass,
        final String value) {
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (final IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + value, iae);
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
        checkCheckClosingParens(ast);
        checkTrailingComma(ast);
    }

    /**
     * Checks to see if the
     * {@link ElementStyle AnnotationElementStyle}
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

        if (valuePairCount == 0
            && annotation.branchContains(TokenTypes.EXPR)) {
            log(annotation.getLineNo(), MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                ElementStyle.EXPANDED);
        }
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
            log(annotation.getLineNo(), MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                ElementStyle.COMPACT);
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

        final int valuePairCount =
            annotation.getChildCount(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        final DetailAST valuePair =
            annotation.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        //in compact style with one value
        if (arrayInit != null
            && arrayInit.getChildCount(TokenTypes.EXPR) == 1) {
            log(annotation.getLineNo(), MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                ElementStyle.COMPACT_NO_ARRAY);
        }
        //in expanded style with one value and the correct element name
        else if (valuePairCount == 1) {
            final DetailAST nestedArrayInit =
                valuePair.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);

            if (nestedArrayInit != null
                && ANNOTATION_ELEMENT_SINGLE_NAME.equals(
                    valuePair.getFirstChild().getText())
                    && nestedArrayInit.getChildCount(TokenTypes.EXPR) == 1) {
                log(annotation.getLineNo(), MSG_KEY_ANNOTATION_INCORRECT_STYLE,
                    ElementStyle.COMPACT_NO_ARRAY);
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
        if (trailingArrayComma != TrailingArrayComma.IGNORE) {
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
     * {@link TokenTypes#ANNOTATION_ARRAY_INIT ANNOTATION_ARRAY_INIT}.
     */
    private void logCommaViolation(final DetailAST ast) {
        final DetailAST rCurly = ast.findFirstToken(TokenTypes.RCURLY);

        //comma can be null if array is empty
        final DetailAST comma = rCurly.getPreviousSibling();

        if (trailingArrayComma == TrailingArrayComma.ALWAYS
            && (comma == null || comma.getType() != TokenTypes.COMMA)) {
            log(rCurly.getLineNo(),
                rCurly.getColumnNo(), MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING);
        }
        else if (trailingArrayComma == TrailingArrayComma.NEVER
            && comma != null && comma.getType() == TokenTypes.COMMA) {
            log(comma.getLineNo(),
                comma.getColumnNo(), MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT);
        }
    }

    /**
     * Checks to see if the closing parenthesis are present if required or
     * prohibited.
     *
     * @param ast the annotation token
     */
    private void checkCheckClosingParens(final DetailAST ast) {
        if (closingParens != ClosingParens.IGNORE) {
            final DetailAST paren = ast.getLastChild();
            final boolean parenExists = paren.getType() == TokenTypes.RPAREN;

            if (closingParens == ClosingParens.ALWAYS
                && !parenExists) {
                log(ast.getLineNo(), MSG_KEY_ANNOTATION_PARENS_MISSING);
            }
            else if (closingParens == ClosingParens.NEVER
                     && parenExists
                     && !ast.branchContains(TokenTypes.EXPR)
                     && !ast.branchContains(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR)
                     && !ast.branchContains(TokenTypes.ANNOTATION_ARRAY_INIT)) {
                log(ast.getLineNo(), MSG_KEY_ANNOTATION_PARENS_PRESENT);
            }
        }
    }
}
