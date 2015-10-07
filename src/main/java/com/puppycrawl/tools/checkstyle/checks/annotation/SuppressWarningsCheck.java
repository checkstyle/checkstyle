////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;

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
 * <p>Limitations:  This check does not consider conditionals
 * inside the SuppressWarnings annotation. <br>
 * For example:
 * {@code @SuppressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")}.
 * According to the above example, the "unused" warning is being suppressed
 * not the "unchecked" or "foo" warnings.  All of these warnings will be
 * considered and matched against regardless of what the conditional
 * evaluates to.
 * <br>
 * The check also does not support code like {@code @SuppressWarnings("un" + "used")},
 * {@code @SuppressWarnings((String) "unused")} or
 * {@code @SuppressWarnings({('u' + (char)'n') + (""+("used" + (String)"")),})}.
 * </p>
 *
 * <p>This check can be configured so that the "unchecked"
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
public class SuppressWarningsCheck extends AbstractFormatCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED =
        "suppressed.warning.not.allowed";

    /** {@link SuppressWarnings SuppressWarnings} annotation name. */
    private static final String SUPPRESS_WARNINGS = "SuppressWarnings";

    /**
     * Fully-qualified {@link SuppressWarnings SuppressWarnings}
     * annotation name.
     */
    private static final String FQ_SUPPRESS_WARNINGS =
        "java.lang." + SUPPRESS_WARNINGS;

    /**
     * Ctor that specifies the default for the format property
     * as specified in the class javadocs.
     */
    public SuppressWarningsCheck() {
        super("^$|^\\s+$");
    }

    @Override
    public final int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
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

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final DetailAST annotation = getSuppressWarnings(ast);

        if (annotation == null) {
            return;
        }

        final DetailAST warningHolder =
            findWarningsHolder(annotation);

        final DetailAST token =
                warningHolder.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);
        DetailAST warning;

        if (token == null) {
            warning = warningHolder.findFirstToken(TokenTypes.EXPR);
        }
        else {
            // case like '@SuppressWarnings(value = UNUSED)'
            warning = token.findFirstToken(TokenTypes.EXPR);
        }

        //rare case with empty array ex: @SuppressWarnings({})
        if (warning == null) {
            //check to see if empty warnings are forbidden -- are by default
            logMatch(warningHolder.getLineNo(),
                warningHolder.getColumnNo(), "");
            return;
        }

        while (warning != null) {
            if (warning.getType() == TokenTypes.EXPR) {
                final DetailAST fChild = warning.getFirstChild();
                switch (fChild.getType()) {
                    //typical case
                    case TokenTypes.STRING_LITERAL:
                        final String warningText =
                            removeQuotes(warning.getFirstChild().getText());
                        logMatch(warning.getLineNo(),
                                warning.getColumnNo(), warningText);
                        break;
                    // conditional case
                    // ex: @SuppressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")
                    case TokenTypes.QUESTION:
                        walkConditional(fChild);
                        break;
                    // param in constant case
                    // ex: public static final String UNCHECKED = "unchecked";
                    // @SuppressWarnings(UNCHECKED) or @SuppressWarnings(SomeClass.UNCHECKED)
                    case TokenTypes.IDENT:
                    case TokenTypes.DOT:
                        break;
                    default:
                        // Known limitation: cases like @SuppressWarnings("un" + "used") or
                        // @SuppressWarnings((String) "unused") are not properly supported,
                        // but they should not cause exceptions.
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
     * @param ast the AST
     * @return the {@link SuppressWarnings SuppressWarnings} annotation
     */
    private static DetailAST getSuppressWarnings(DetailAST ast) {
        final DetailAST annotation = AnnotationUtility.getAnnotation(
            ast, SUPPRESS_WARNINGS);

        if (annotation == null) {
            return AnnotationUtility.getAnnotation(ast, FQ_SUPPRESS_WARNINGS);
        }
        else {
            return annotation;
        }
    }

    /**
     * This method looks for a warning that matches a configured expression.
     * If found it logs a violation at the given line and column number.
     *
     * @param lineNo the line number
     * @param colNum the column number
     * @param warningText the warning.
     */
    private void logMatch(final int lineNo,
        final int colNum, final String warningText) {
        final Matcher matcher = getRegexp().matcher(warningText);
        if (matcher.matches()) {
            log(lineNo, colNum,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, warningText);
        }
    }

    /**
     * Find the parent (holder) of the of the warnings (Expr).
     *
     * @param annotation the annotation
     * @return a Token representing the expr.
     */
    private static DetailAST findWarningsHolder(final DetailAST annotation) {
        final DetailAST annValuePair =
            annotation.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);
        final DetailAST annArrayInit;

        if (annValuePair == null) {
            annArrayInit =
                    annotation.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
        }
        else {
            annArrayInit =
                    annValuePair.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
        }

        if (annArrayInit != null) {
            return annArrayInit;
        }

        return annotation;
    }

    /**
     * Strips a single double quote from the front and back of a string.
     *
     * <p>For example:
     * <br/>
     * Input String = "unchecked"
     * <br/>
     * Output String = unchecked
     *
     * @param warning the warning string
     * @return the string without two quotes
     */
    private static String removeQuotes(final String warning) {
        return warning.substring(1, warning.length() - 1);
    }

    /**
     * Recursively walks a conditional expression checking the left
     * and right sides, checking for matches and
     * logging violations.
     *
     * @param cond a Conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     */
    private void walkConditional(final DetailAST cond) {
        if (cond.getType() != TokenTypes.QUESTION) {
            final String warningText =
                removeQuotes(cond.getText());
            logMatch(cond.getLineNo(), cond.getColumnNo(), warningText);
            return;
        }

        walkConditional(getCondLeft(cond));
        walkConditional(getCondRight(cond));
    }

    /**
     * Retrieves the left side of a conditional.
     *
     * @param cond cond a conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     * @return either the value
     *     or another conditional
     */
    private static DetailAST getCondLeft(final DetailAST cond) {
        final DetailAST colon = cond.findFirstToken(TokenTypes.COLON);
        return colon.getPreviousSibling();
    }

    /**
     * Retrieves the right side of a conditional.
     *
     * @param cond a conditional type
     * {@link TokenTypes#QUESTION QUESTION}
     * @return either the value
     *     or another conditional
     */
    private static DetailAST getCondRight(final DetailAST cond) {
        final DetailAST colon = cond.findFirstToken(TokenTypes.COLON);
        return colon.getNextSibling();
    }
}
