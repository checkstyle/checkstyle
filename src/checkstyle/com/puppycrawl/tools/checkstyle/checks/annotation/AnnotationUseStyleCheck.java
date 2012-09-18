////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This check controls the style with the usage of annotations.
 *
 * <p>
 * Annotations have three element styles starting with the least verbose.
 * <ul>
 * <li>{@link ElementStyle#COMPACT_NO_ARRAY COMPACT_NO_ARRAY}</li>
 * <li>{@link ElementStyle#COMPACT COMPACT}</li>
 * <li>{@link ElementStyle#EXPANDED EXPANDED}</li>
 * </ul>
 * To not enforce an element style
 * a {@link ElementStyle#IGNORE IGNORE} type is provided.  The desired style
 * can be set through the <code>elementStyle</code> property.
 * </p>
 *
 * <p>
 * Using the EXPANDED style is more verbose. The expanded version
 * is sometimes referred to as "named parameters" in other languages.
 * </p>
 *
 * <p>
 * Using the COMPACT style is less verbose. This style can only
 * be used when there is an element called 'value' which is  either
 * the sole element or all other elements have default valuess.
 * </p>
 *
 * <p>
 * Using the COMPACT_NO_ARRAY style is less verbose. It is similar
 * to the COMPACT style but single value arrays are flagged. With
 * annotations a single value array does not need to be placed in an
 * array initializer. This style can only be used when there is an
 * element called 'value' which is either the sole element or all other
 * elements have default values.
 * </p>
 *
 * <p>
 * The ending parenthesis are optional when using annotations with no elements.
 * To always require ending parenthesis use the
 * {@link ClosingParens#ALWAYS ALWAYS} type.  To never have ending parenthesis
 * use the {@link ClosingParens#NEVER NEVER} type. To not enforce a
 * closing parenthesis preference a {@link ClosingParens#IGNORE IGNORE} type is
 * provided. Set this through the <code>closingParens</code> property.
 * </p>
 *
 * <p>
 * Annotations also allow you to specify arrays of elements in a standard
 * format.  As with normal arrays, a trailing comma is optional. To always
 * require a trailing comma use the {@link TrailingArrayComma#ALWAYS ALWAYS}
 * type. To never have a trailing  comma use the
 * {@link TrailingArrayComma#NEVER NEVER} type. To not enforce a trailing
 * array comma preference a {@link TrailingArrayComma#IGNORE IGNORE} type
 * is provided.  Set this through the <code>trailingArrayComma</code> property.
 * </p>
 *
 * <p>
 * By default the ElementStyle is set to EXPANDED, the TrailingArrayComma
 * is set to NEVER, and the ClosingParans is set to ALWAYS.
 * </p>
 *
 * <p>
 * According to the JLS, it is legal to include a trailing comma
 * in arrays used in annotations but Sun's Java 5 & 6 compilers will not
 * compile with this syntax. This may in be a bug in Sun's compilers
 * since eclipse 3.4's built-in compiler does allow this syntax as
 * defined in the JLS. Note: this was tested with compilers included with
 * JDK versions 1.5.0.17 and 1.6.0.11 and the compiler included with eclipse
 * 3.4.1.
 *
 * See <a
 * href="http://java.sun.com/docs/books/jls/third_edition/html/j3TOC.html">
 * Java Language specification, sections 9.7</a>.
 * </p>
 *
 * <p>
 * An example shown below is set to enforce an EXPANDED style, with a
 * trailing array comma set to NEVER and always including the closing
 * parenthesis.
 * </p>
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
public final class AnnotationUseStyleCheck extends Check
{
    /**
     * the element name used to receive special linguistic support
     * for annotation use.
     */
    private static final String ANNOTATION_ELEMENT_SINGLE_NAME =
        "value";

    //not extending AbstractOptionCheck because check
    //has more than one option type.

    /** @see #setElementStyle(String) */
    private ElementStyle mStyle = ElementStyle.COMPACT_NO_ARRAY;

    //defaulting to NEVER because of the strange compiler behavior
    /** @see #setTrailingArrayComma(String) */
    private TrailingArrayComma mComma = TrailingArrayComma.NEVER;

    /** @see #setClosingParans(String) */
    private ClosingParens mParens = ClosingParens.NEVER;

    /**
     * Sets the ElementStyle from a string.
     *
     * @param aStyle string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setElementStyle(final String aStyle)
    {
        this.mStyle = this.getOption(ElementStyle.class, aStyle);
    }

    /**
     * Sets the TrailingArrayComma from a string.
     *
     * @param aComma string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setTrailingArrayComma(final String aComma)
    {
        this.mComma = this.getOption(TrailingArrayComma.class, aComma);
    }

    /**
     * Sets the ClosingParens from a string.
     *
     * @param aParens string representation
     * @throws ConversionException if cannot convert string.
     */
    public void setClosingParens(final String aParens)
    {
        this.mParens = this.getOption(ClosingParens.class, aParens);
    }

    /**
     * Retrieves an {@link Enum Enum} type from a @{link String String}.
     * @param <T> the enum type
     * @param aEnumClass the enum class
     * @param aString the string representing the enum
     * @return the enum type
     */
    private <T extends Enum<T>> T getOption(final Class<T> aEnumClass,
        final String aString)
    {
        try {
            return Enum.valueOf(aEnumClass, aString.trim().toUpperCase());
        }
        catch (final IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + aString, iae);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int[] getDefaultTokens()
    {
        return this.getRequiredTokens();
    }

    /** {@inheritDoc} */
    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.ANNOTATION,
        };
    }

    /** {@inheritDoc} */
    @Override
    public int[] getAcceptableTokens()
    {
        return this.getRequiredTokens();
    }

    /** {@inheritDoc} */
    @Override
    public void visitToken(final DetailAST aAST)
    {
        this.checkStyleType(aAST);
        this.checkCheckClosingParens(aAST);
        this.checkTrailingComma(aAST);
    }

    /**
     * Checks to see if the
     * {@link ElementStyle AnnotationElementStyle}
     * is correct.
     *
     * @param aAnnotation the annotation token
     */
    private void checkStyleType(final DetailAST aAnnotation)
    {
        if (ElementStyle.IGNORE.equals(this.mStyle)
            || this.mStyle == null)
        {
            return;
        }

        if (ElementStyle.COMPACT_NO_ARRAY.equals(this.mStyle)) {
            this.checkCompactNoArrayStyle(aAnnotation);
        }
        else if (ElementStyle.COMPACT.equals(this.mStyle)) {
            this.checkCompactStyle(aAnnotation);
        }
        else if (ElementStyle.EXPANDED.equals(this.mStyle)) {
            this.checkExpandedStyle(aAnnotation);
        }
    }

    /**
     * Checks for expanded style type violations.
     *
     * @param aAnnotation the annotation token
     */
    private void checkExpandedStyle(final DetailAST aAnnotation)
    {
        final int valuePairCount =
            aAnnotation.getChildCount(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        if (valuePairCount == 0
            && aAnnotation.branchContains(TokenTypes.EXPR))
        {
            this.log(aAnnotation.getLineNo(), "annotation.incorrect.style",
                ElementStyle.EXPANDED);
        }
    }

    /**
     * Checks for compact style type violations.
     *
     * @param aAnnotation the annotation token
     */
    private void checkCompactStyle(final DetailAST aAnnotation)
    {
        final int valuePairCount =
            aAnnotation.getChildCount(
                TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        final DetailAST valuePair =
            aAnnotation.findFirstToken(
                TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        if (valuePairCount == 1
            && AnnotationUseStyleCheck.ANNOTATION_ELEMENT_SINGLE_NAME.equals(
                valuePair.getFirstChild().getText()))
        {
            this.log(aAnnotation.getLineNo(), "annotation.incorrect.style",
                ElementStyle.COMPACT);
        }
    }

    /**
     * Checks for compact no array style type violations.
     *
     * @param aAnnotation the annotation token
     */
    private void checkCompactNoArrayStyle(final DetailAST aAnnotation)
    {
        final DetailAST arrayInit =
            aAnnotation.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);

        final int valuePairCount =
            aAnnotation.getChildCount(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        final DetailAST valuePair =
            aAnnotation.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

        //in compact style with one value
        if (arrayInit != null
            && arrayInit.getChildCount(TokenTypes.EXPR) == 1)
        {
            this.log(aAnnotation.getLineNo(), "annotation.incorrect.style",
                ElementStyle.COMPACT_NO_ARRAY);
        }
        //in expanded style with one value and the correct element name
        else if (valuePairCount == 1) {
            final DetailAST nestedArrayInit =
                valuePair.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);

            if (nestedArrayInit != null
                && AnnotationUseStyleCheck.
                    ANNOTATION_ELEMENT_SINGLE_NAME.equals(
                    valuePair.getFirstChild().getText())
                    && nestedArrayInit.getChildCount(TokenTypes.EXPR) == 1)
            {
                this.log(aAnnotation.getLineNo(), "annotation.incorrect.style",
                    ElementStyle.COMPACT_NO_ARRAY);
            }
        }
    }

    /**
     * Checks to see if the trailing comma is present if required or
     * prohibited.
     *
     * @param aAnnotation the annotation token
     */
    private void checkTrailingComma(final DetailAST aAnnotation)
    {
        if (TrailingArrayComma.IGNORE.equals(this.mComma)
            || this.mComma == null)
        {
            return;
        }

        DetailAST child = aAnnotation.getFirstChild();

        while (child != null) {
            DetailAST arrayInit = null;

            if (child.getType()
                == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR)
            {
                arrayInit =
                    child.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
            }
            else if (child.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) {
                arrayInit = child;
            }

            if (arrayInit != null) {
                this.logCommaViolation(arrayInit);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * logs a trailing array comma violation if one exists.
     *
     * @param aAST the array init
     * {@link TokenTypes#ANNOTATION_ARRAY_INIT ANNOTATION_ARRAY_INIT}.
     */
    private void logCommaViolation(final DetailAST aAST)
    {
        final DetailAST rCurly = aAST.findFirstToken(TokenTypes.RCURLY);

        //comma can be null if array is empty
        final DetailAST comma = rCurly.getPreviousSibling();

        if (TrailingArrayComma.ALWAYS.equals(this.mComma)
            && (comma == null || comma.getType() != TokenTypes.COMMA))
        {
            this.log(rCurly.getLineNo(),
                rCurly.getColumnNo(), "annotation.trailing.comma.missing");
        }
        else if (TrailingArrayComma.NEVER.equals(this.mComma)
            && comma != null && comma.getType() == TokenTypes.COMMA)
        {
            this.log(comma.getLineNo(),
                comma.getColumnNo(), "annotation.trailing.comma.present");
        }
    }

    /**
     * Checks to see if the closing parenthesis are present if required or
     * prohibited.
     *
     * @param aAST the annotation token
     */
    private void checkCheckClosingParens(final DetailAST aAST)
    {
        if (ClosingParens.IGNORE.equals(this.mParens)
            || this.mParens == null)
        {
            return;
        }

        final DetailAST paren = aAST.getLastChild();
        final boolean parenExists = paren.getType() == TokenTypes.RPAREN;

        if (ClosingParens.ALWAYS.equals(this.mParens)
            && !parenExists)
        {
            this.log(aAST.getLineNo(), "annotation.parens.missing");
        }
        else if (ClosingParens.NEVER.equals(this.mParens)
            && !aAST.branchContains(TokenTypes.EXPR)
            && parenExists)
        {
            this.log(aAST.getLineNo(), "annotation.parens.present");
        }
    }

    /**
     * Defines the styles for defining elements in an annotation.
     * @author Travis Schneeberger
     */
    public static enum ElementStyle {

        /**
         * expanded example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused",})</pre>.
         */
        EXPANDED,

        /**
         * compact example
         *
         * <pre>@SuppressWarnings({"unchecked","unused",})</pre>
         * <br/>or<br/>
         * <pre>@SuppressWarnings("unchecked")</pre>.
         */
        COMPACT,

        /**
         * compact example.]
         *
         * <pre>@SuppressWarnings("unchecked")</pre>.
         */
        COMPACT_NO_ARRAY,

        /**
         * mixed styles.
         */
        IGNORE,
    }

    /**
     * Defines the two styles for defining
     * elements in an annotation.
     *
     * @author Travis Schneeberger
     */
    public static enum TrailingArrayComma {

        /**
         * with comma example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused",})</pre>.
         */
        ALWAYS,

        /**
         * without comma example
         *
         * <pre>@SuppressWarnings(value={"unchecked","unused"})</pre>.
         */
        NEVER,

        /**
         * mixed styles.
         */
        IGNORE,
    }

    /**
     * Defines the two styles for defining
     * elements in an annotation.
     *
     * @author Travis Schneeberger
     */
    public static enum ClosingParens {

        /**
         * with parens example
         *
         * <pre>@Deprecated()</pre>.
         */
        ALWAYS,

        /**
         * without parens example
         *
         * <pre>@Deprecated</pre>.
         */
        NEVER,

        /**
         * mixed styles.
         */
        IGNORE,
    }
}
