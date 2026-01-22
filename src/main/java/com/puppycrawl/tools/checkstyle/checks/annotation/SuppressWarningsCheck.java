///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Allows to specify what warnings that
 * {@code @SuppressWarnings} is not allowed to suppress.
 * You can also specify a list of TokenTypes that
 * the configured warning(s) cannot be suppressed on.
 * </div>
 *
 * <p>
 * Limitations:  This check does not consider conditionals
 * inside the &#64;SuppressWarnings annotation.
 * </p>
 *
 * <p>
 * For example:
 * {@code @SuppressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")}.
 * According to the above example, the "unused" warning is being suppressed
 * not the "unchecked" or "foo" warnings.  All of these warnings will be
 * considered and matched against regardless of what the conditional
 * evaluates to.
 * The check also does not support code like {@code @SuppressWarnings("un" + "used")},
 * {@code @SuppressWarnings((String) "unused")} or
 * {@code @SuppressWarnings({('u' + (char)'n') + (""+("used" + (String)"")),})}.
 * </p>
 *
 * <p>
 * By default, any warning specified will be disallowed on
 * all legal TokenTypes unless otherwise specified via
 * the tokens property.
 * </p>
 *
 * <p>
 * Also, by default warnings that are empty strings or all
 * whitespace (regex: ^$|^\s+$) are flagged.  By specifying,
 * the format property these defaults no longer apply.
 * </p>
 *
 * <p>This check can be configured so that the "unchecked"
 * and "unused" warnings cannot be suppressed on
 * anything but variable and parameter declarations.
 * See below of an example.
 * </p>
 *
 * @since 5.0
 */
@StatelessCheck
public class SuppressWarningsCheck extends AbstractCheck {

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
     * Specify the RegExp to match against warnings. Any warning
     * being suppressed matching this pattern will be flagged.
     */
    private Pattern format = Pattern.compile("^\\s*+$");

    /**
     * Setter to specify the RegExp to match against warnings. Any warning
     * being suppressed matching this pattern will be flagged.
     *
     * @param pattern the new pattern
     * @since 5.0
     */
    public final void setFormat(Pattern pattern) {
        format = pattern;
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
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final DetailAST annotation = getSuppressWarnings(ast);

        if (annotation != null) {
            final DetailAST warningHolder =
                findWarningsHolder(annotation);
            final DetailAST token =
                    warningHolder.findFirstToken(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

            // case like '@SuppressWarnings(value = UNUSED)'
            final DetailAST parent = Objects.requireNonNullElse(token, warningHolder);
            final DetailAST warning = parent.findFirstToken(TokenTypes.EXPR);

            if (warning == null) {
                // check to see if empty warnings are forbidden -- are by default
                logMatch(warningHolder, "");
            }
            else {
                processWarnings(warning);
            }
        }
    }

    /**
     * Processes all warning expressions starting from the given AST node.
     *
     * @param warning the first warning expression node to process
     */
    private void processWarnings(final DetailAST warning) {
        for (DetailAST current = warning; current != null; current = current.getNextSibling()) {
            if (current.getType() == TokenTypes.EXPR) {
                processWarningExpr(current.getFirstChild(), current);
            }
        }
    }

    /**
     * Processes a single warning expression.
     *
     * @param fChild  the first child AST of the expression
     * @param warning the parent warning AST node
     */
    private void processWarningExpr(final DetailAST fChild, final DetailAST warning) {
        switch (fChild.getType()) {
            case TokenTypes.STRING_LITERAL -> logMatch(warning,
                    removeQuotes(warning.getFirstChild().getText()));

            case TokenTypes.QUESTION ->
                // ex: @SuppressWarnings((false) ? (true) ? "unchecked" : "foo" : "unused")
                walkConditional(fChild);

            default -> {
            // Known limitation: cases like @SuppressWarnings("un" + "used") or
            // @SuppressWarnings((String) "unused") are not properly supported,
            // but they should not cause exceptions.
            // Also constants as params:
            // ex: public static final String UNCHECKED = "unchecked";
            // @SuppressWarnings(UNCHECKED)
            // or
            // @SuppressWarnings(SomeClass.UNCHECKED)
            }
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
        DetailAST annotation = AnnotationUtil.getAnnotation(ast, SUPPRESS_WARNINGS);

        if (annotation == null) {
            annotation = AnnotationUtil.getAnnotation(ast, FQ_SUPPRESS_WARNINGS);
        }
        return annotation;
    }

    /**
     * This method looks for a warning that matches a configured expression.
     * If found it logs a violation at the given AST.
     *
     * @param ast the location to place the violation
     * @param warningText the warning.
     */
    private void logMatch(DetailAST ast, final String warningText) {
        final Matcher matcher = format.matcher(warningText);
        if (matcher.matches()) {
            log(ast,
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

        final DetailAST annArrayInitParent = Objects.requireNonNullElse(annValuePair, annotation);
        final DetailAST annArrayInit = annArrayInitParent
                .findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
        return Objects.requireNonNullElse(annArrayInit, annotation);
    }

    /**
     * Strips a single double quote from the front and back of a string.
     *
     * <p>For example:</p>
     * <pre>
     * Input String = "unchecked"
     * </pre>
     * Output String = unchecked
     *
     * @param warning the warning string
     * @return the string without two quotes
     */
    private static String removeQuotes(final String warning) {
        return warning.substring(1, warning.length() - 1);
    }

    /**
     * Walks a conditional expression checking the left
     * and right sides, checking for matches and
     * logging violations.
     *
     * @param cond a Conditional type
     *     {@link TokenTypes#QUESTION QUESTION}
     */
    private void walkConditional(final DetailAST cond) {
        final Deque<DetailAST> condStack = new ArrayDeque<>();
        condStack.push(cond);

        while (!condStack.isEmpty()) {
            final DetailAST currentCond = condStack.pop();
            if (currentCond.getType() == TokenTypes.QUESTION) {
                condStack.push(getCondRight(currentCond));
                condStack.push(getCondLeft(currentCond));
            }
            else {
                final String warningText = removeQuotes(currentCond.getText());
                logMatch(currentCond, warningText);
            }
        }
    }

    /**
     * Retrieves the left side of a conditional.
     *
     * @param cond cond a conditional type
     *     {@link TokenTypes#QUESTION QUESTION}
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
     *     {@link TokenTypes#QUESTION QUESTION}
     * @return either the value
     *     or another conditional
     */
    private static DetailAST getCondRight(final DetailAST cond) {
        final DetailAST colon = cond.findFirstToken(TokenTypes.COLON);
        return colon.getNextSibling();
    }

}
