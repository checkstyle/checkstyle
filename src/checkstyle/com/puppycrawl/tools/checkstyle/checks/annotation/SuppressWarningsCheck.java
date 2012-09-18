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

import java.util.regex.Matcher;

import com.puppycrawl.tools.checkstyle.api.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p>
 * This check allows you to specify what warnings that
 * {@link SuppressWarnings SuppressWarnings} is not
 * allowed to suppress.  You can also specify a list
 * of TokenTypes that the configured warning(s) cannot
 * be suppressed on.
 * </p>
 *
 * <p>
 * The {@link AbstractFormatCheck#setFormat warnings} property is a
 * regex pattern.  Any warning being suppressed matching
 * this pattern will be flagged.
 * </p>
 *
 * <p>
 * By default, any warning specified will be disallowed on
 * all legal TokenTypes unless otherwise specified via
 * the
 * {@link com.puppycrawl.tools.checkstyle.api.Check#setTokens(String[]) tokens}
 * property.
 *
 * Also, by default warnings that are empty strings or all
 * whitespace (regex: ^$|^\s+$) are flagged.  By specifying,
 * the format property these defaults no longer apply.
 * </p>
 *
 * <p>
 * Limitations:  This check does not consider conditionals
 * inside the SuppressWarnings annotation. <br/>
 * For example:
 * {@code @SupressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")}
 * According to the above example, the "unused" warning is being suppressed
 * not the "unchecked" or "foo" warnings.  All of these warnings will be
 * considered and matched against regardless of what the conditional
 * evaluates to.
 * </p>
 *
 * <p>
 * This check can be configured so that the "unchecked"
 * and "unused" warnings cannot be suppressed on
 * anything but variable and parameter declarations.
 * See below of an example.
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;SuppressWarnings&quot;&gt;
 *    &lt;property name=&quot;format&quot;
 *        value=&quot;^unchecked$|^unused$&quot;/&gt;
 *    &lt;property name=&quot;tokens&quot;
 *        value=&quot;
 *        CLASS_DEF,INTERFACE_DEF,ENUM_DEF,
 *        ANNOTATION_DEF,ANNOTATION_FIELD_DEF,
 *        ENUM_CONSTANT_DEF,METHOD_DEF,CTOR_DEF
 *        &quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Travis Schneeberger
 */
public class SuppressWarningsCheck extends AbstractFormatCheck
{
    /** {@link SuppressWarnings SuppressWarnings} annotation name */
    private static final String SUPPRESS_WARNINGS = "SuppressWarnings";

    /**
     * fully-qualified {@link SuppressWarnings SuppressWarnings}
     * annotation name
     */
    private static final String FQ_SUPPRESS_WARNINGS =
        "java.lang." + SUPPRESS_WARNINGS;

    /**
     * Ctor that specifies the default for the format property
     * as specified in the class javadocs.
     */
    public SuppressWarningsCheck()
    {
        super("^$|^\\s+$");
    }

    /** {@inheritDoc} */
    @Override
    public final int[] getDefaultTokens()
    {
        return this.getAcceptableTokens();
    }

    /** {@inheritDoc} */
    @Override
    public final int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    /** {@inheritDoc} */
    @Override
    public void visitToken(final DetailAST aAST)
    {
        final DetailAST annotation = this.getSuppressWarnings(aAST);

        if (annotation == null) {
            return;
        }

        final DetailAST warningHolder =
            this.findWarningsHolder(annotation);

        DetailAST warning = warningHolder.findFirstToken(TokenTypes.EXPR);

        //rare case with empty array ex: @SuppressWarnings({})
        if (warning == null) {
            //check to see if empty warnings are forbidden -- are by default
            this.logMatch(warningHolder.getLineNo(),
                warningHolder.getColumnNo(), "");
            return;
        }

        while (warning != null) {
            if (warning.getType() == TokenTypes.EXPR) {
                final DetailAST fChild = warning.getFirstChild();

                //typical case
                if (fChild.getType() == TokenTypes.STRING_LITERAL) {
                    final String warningText =
                        this.removeQuotes(warning.getFirstChild().getText());
                    this.logMatch(warning.getLineNo(),
                        warning.getColumnNo(), warningText);

     //conditional case
     //ex: @SupressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")
                }
                else if (fChild.getType() == TokenTypes.QUESTION) {
                    this.walkConditional(fChild);
                }
                else {
                    assert false : "Should never get here, type: "
                        + fChild.getType() + " text: " + fChild.getText();
                }
            }
            warning = warning.getNextSibling();
        }
    }

    /**
     * Gets the {@link SuppressWarnings SuppressWarnings} annotation
     * that is annotating the AST.  If the annotation does not exist
     * this method will return {@code null}.
     *
     * @param aAST the AST
     * @return the {@link SuppressWarnings SuppressWarnings} annotation
     */
    private DetailAST getSuppressWarnings(DetailAST aAST)
    {
        final DetailAST annotation = AnnotationUtility.getAnnotation(
            aAST, SuppressWarningsCheck.SUPPRESS_WARNINGS);

        return (annotation != null) ? annotation
            : AnnotationUtility.getAnnotation(
                aAST, SuppressWarningsCheck.FQ_SUPPRESS_WARNINGS);
    }

    /**
     * This method looks for a warning that matches a configured expression.
     * If found it logs a violation at the given line and column number.
     *
     * @param aLineNo the line number
     * @param aColNum the column number
     * @param aWarningText the warning.
     */
    private void logMatch(final int aLineNo,
        final int aColNum, final String aWarningText)
    {
        final Matcher matcher = this.getRegexp().matcher(aWarningText);
        if (matcher.matches()) {
            this.log(aLineNo, aColNum,
                "suppressed.warning.not.allowed", aWarningText);
        }
    }

    /**
     * Find the parent (holder) of the of the warnings (Expr).
     *
     * @param aAnnotation the annotation
     * @return a Token representing the expr.
     */
    private DetailAST findWarningsHolder(final DetailAST aAnnotation)
    {
        final DetailAST annValuePair =
            aAnnotation.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);
        final DetailAST annArrayInit;

        if (annValuePair != null) {
            annArrayInit =
                annValuePair.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
        }
        else {
            annArrayInit =
                aAnnotation.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
        }

        if (annArrayInit != null) {
            return annArrayInit;
        }

        return aAnnotation;
    }

    /**
     * Strips a single double quote from the front and back of a string.
     *
     * For example:
     * <br/>
     * Input String = "unchecked"
     * <br/>
     * Output String = unchecked
     *
     * @param aWarning the warning string
     * @return the string without two quotes
     */
    private String removeQuotes(final String aWarning)
    {
        assert aWarning != null : "the aWarning was null";
        assert aWarning.charAt(0) == '"';
        assert aWarning.charAt(aWarning.length() - 1) == '"';

        return aWarning.substring(1, aWarning.length() - 1);
    }

    /**
     * Recursively walks a conditional expression checking the left
     * and right sides, checking for matches and
     * logging violations.
     *
     * @param aCond a Conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     */
    private void walkConditional(final DetailAST aCond)
    {
        if (aCond.getType() != TokenTypes.QUESTION) {
            final String warningText =
                this.removeQuotes(aCond.getText());
            this.logMatch(aCond.getLineNo(), aCond.getColumnNo(), warningText);
            return;
        }

        this.walkConditional(this.getCondLeft(aCond));
        this.walkConditional(this.getCondRight(aCond));
    }

    /**
     * Retrieves the left side of a conditional.
     *
     * @param aCond aCond a conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     * @return either the value
     * or another conditional
     */
    private DetailAST getCondLeft(final DetailAST aCond)
    {
        final DetailAST colon = aCond.findFirstToken(TokenTypes.COLON);
        return colon.getPreviousSibling();
    }

    /**
     * Retrieves the right side of a conditional.
     *
     * @param aCond a conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     * @return either the value
     * or another conditional
     */
    private DetailAST getCondRight(final DetailAST aCond)
    {
        final DetailAST colon = aCond.findFirstToken(TokenTypes.COLON);
        return colon.getNextSibling();
    }
}
