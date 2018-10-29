////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the placement of left curly braces.
 * The policy to verify is specified using the {@link LeftCurlyOption} class
 * and the default one being {@link LeftCurlyOption#EOL}.
 * </p>
 * <p>
 * By default the following tokens are checked:
 *  {@link TokenTypes#LAMBDA LAMBDA},
 *  {@link TokenTypes#LITERAL_CASE LITERAL_CASE},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_DEFAULT LITERAL_DEFAULT},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT}.
 * </p>
 *
 * <p>
 * The policy to verify is specified using the {@link LeftCurlyOption} class and
 * defaults to {@link LeftCurlyOption#EOL}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="LeftCurly"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check with policy
 * {@link LeftCurlyOption#NLOW} is:
 * </p>
 * <pre>
 * &lt;module name="LeftCurly"&gt;
 *      &lt;property name="option" value="nlow"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to validate enum definitions:
 * </p>
 * <pre>
 * &lt;module name="LeftCurly"&gt;
 *      &lt;property name="ignoreEnums" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 */
@StatelessCheck
public class LeftCurlyCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_NEW = "line.new";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_PREVIOUS = "line.previous";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_BREAK_AFTER = "line.break.after";

    /** Open curly brace literal. */
    private static final String OPEN_CURLY_BRACE = "{";

    /** If true, Check will ignore enums. */
    private boolean ignoreEnums = true;

    /** The policy to enforce. */
    private LeftCurlyOption option = LeftCurlyOption.EOL;

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = LeftCurlyOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + optionStr, iae);
        }
    }

    /**
     * Sets whether check should ignore enums when left curly brace policy is EOL.
     * @param ignoreEnums check's option for ignoring enums.
     */
    public void setIgnoreEnums(boolean ignoreEnums) {
        this.ignoreEnums = ignoreEnums;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.OBJBLOCK,
            TokenTypes.STATIC_INIT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST startToken;
        DetailAST brace;

        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                startToken = skipAnnotationOnlyLines(ast);
                brace = ast.findFirstToken(TokenTypes.SLIST);
                break;
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.CLASS_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ENUM_CONSTANT_DEF:
                startToken = skipAnnotationOnlyLines(ast);
                final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
                brace = objBlock;

                if (objBlock != null) {
                    brace = objBlock.getFirstChild();
                }
                break;
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_SYNCHRONIZED:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_FINALLY:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.LAMBDA:
                startToken = ast;
                brace = ast.findFirstToken(TokenTypes.SLIST);
                break;
            case TokenTypes.LITERAL_ELSE:
                startToken = ast;
                brace = getBraceAsFirstChild(ast);
                break;
            case TokenTypes.LITERAL_CASE:
            case TokenTypes.LITERAL_DEFAULT:
                startToken = ast;
                brace = getBraceAsFirstChild(ast.getNextSibling());
                break;
            default:
                // ATTENTION! We have default here, but we expect case TokenTypes.METHOD_DEF,
                // TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO only.
                // It has been done to improve coverage to 100%. I couldn't replace it with
                // if-else-if block because code was ugly and didn't pass pmd check.

                startToken = ast;
                brace = ast.findFirstToken(TokenTypes.LCURLY);
                break;
        }

        if (brace != null) {
            verifyBrace(brace, startToken);
        }
    }

    /**
     * Gets a SLIST if it is the first child of the AST.
     * @param ast {@code DetailAST}.
     * @return {@code DetailAST} if the first child is {@code TokenTypes.SLIST},
     *     {@code null} otherwise.
     */
    private static DetailAST getBraceAsFirstChild(DetailAST ast) {
        DetailAST brace = null;
        if (ast != null) {
            final DetailAST candidate = ast.getFirstChild();
            if (candidate != null && candidate.getType() == TokenTypes.SLIST) {
                brace = candidate;
            }
        }
        return brace;
    }

    /**
     * Skip lines that only contain {@code TokenTypes.ANNOTATION}s.
     * If the received {@code DetailAST}
     * has annotations within its modifiers then first token on the line
     * of the first token after all annotations is return. This might be
     * an annotation.
     * Otherwise, the received {@code DetailAST} is returned.
     * @param ast {@code DetailAST}.
     * @return {@code DetailAST}.
     */
    private static DetailAST skipAnnotationOnlyLines(DetailAST ast) {
        DetailAST resultNode = ast;
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

        if (modifiers != null) {
            final DetailAST lastAnnotation = findLastAnnotation(modifiers);

            if (lastAnnotation != null) {
                final DetailAST tokenAfterLast;

                if (lastAnnotation.getNextSibling() == null) {
                    tokenAfterLast = modifiers.getNextSibling();
                }
                else {
                    tokenAfterLast = lastAnnotation.getNextSibling();
                }

                if (tokenAfterLast.getLineNo() > lastAnnotation.getLineNo()) {
                    resultNode = tokenAfterLast;
                }
                else {
                    resultNode = getFirstAnnotationOnSameLine(lastAnnotation);
                }
            }
        }
        return resultNode;
    }

    /**
     * Returns first annotation on same line.
     * @param annotation
     *            last annotation on the line
     * @return first annotation on same line.
     */
    private static DetailAST getFirstAnnotationOnSameLine(DetailAST annotation) {
        DetailAST previousAnnotation = annotation;
        final int lastAnnotationLineNumber = previousAnnotation.getLineNo();
        while (previousAnnotation.getPreviousSibling() != null
                && previousAnnotation.getPreviousSibling().getLineNo()
                    == lastAnnotationLineNumber) {
            previousAnnotation = previousAnnotation.getPreviousSibling();
        }
        return previousAnnotation;
    }

    /**
     * Find the last token of type {@code TokenTypes.ANNOTATION}
     * under the given set of modifiers.
     * @param modifiers {@code DetailAST}.
     * @return {@code DetailAST} or null if there are no annotations.
     */
    private static DetailAST findLastAnnotation(DetailAST modifiers) {
        DetailAST annotation = modifiers.findFirstToken(TokenTypes.ANNOTATION);
        while (annotation != null && annotation.getNextSibling() != null
               && annotation.getNextSibling().getType() == TokenTypes.ANNOTATION) {
            annotation = annotation.getNextSibling();
        }
        return annotation;
    }

    /**
     * Verifies that a specified left curly brace is placed correctly
     * according to policy.
     * @param brace token for left curly brace
     * @param startToken token for start of expression
     */
    private void verifyBrace(final DetailAST brace,
                             final DetailAST startToken) {
        final String braceLine = getLine(brace.getLineNo() - 1);

        // Check for being told to ignore, or have '{}' which is a special case
        if (braceLine.length() <= brace.getColumnNo() + 1
                || braceLine.charAt(brace.getColumnNo() + 1) != '}') {
            if (option == LeftCurlyOption.NL) {
                if (!CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine)) {
                    log(brace, MSG_KEY_LINE_NEW, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
                }
            }
            else if (option == LeftCurlyOption.EOL) {
                validateEol(brace, braceLine);
            }
            else if (startToken.getLineNo() != brace.getLineNo()) {
                validateNewLinePosition(brace, startToken, braceLine);
            }
        }
    }

    /**
     * Validate EOL case.
     * @param brace brace AST
     * @param braceLine line content
     */
    private void validateEol(DetailAST brace, String braceLine) {
        if (CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine)) {
            log(brace, MSG_KEY_LINE_PREVIOUS, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
        }
        if (!hasLineBreakAfter(brace)) {
            log(brace, MSG_KEY_LINE_BREAK_AFTER, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
        }
    }

    /**
     * Validate token on new Line position.
     * @param brace brace AST
     * @param startToken start Token
     * @param braceLine content of line with Brace
     */
    private void validateNewLinePosition(DetailAST brace, DetailAST startToken, String braceLine) {
        // not on the same line
        if (startToken.getLineNo() + 1 == brace.getLineNo()) {
            if (CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine)) {
                log(brace, MSG_KEY_LINE_PREVIOUS, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
            }
            else {
                log(brace, MSG_KEY_LINE_NEW, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
            }
        }
        else if (!CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine)) {
            log(brace, MSG_KEY_LINE_NEW, OPEN_CURLY_BRACE, brace.getColumnNo() + 1);
        }
    }

    /**
     * Checks if left curly has line break after.
     * @param leftCurly
     *        Left curly token.
     * @return
     *        True, left curly has line break after.
     */
    private boolean hasLineBreakAfter(DetailAST leftCurly) {
        DetailAST nextToken = null;
        if (leftCurly.getType() == TokenTypes.SLIST) {
            nextToken = leftCurly.getFirstChild();
        }
        else {
            if (!ignoreEnums
                    && leftCurly.getParent().getParent().getType() == TokenTypes.ENUM_DEF) {
                nextToken = leftCurly.getNextSibling();
            }
        }
        return nextToken == null
                || nextToken.getType() == TokenTypes.RCURLY
                || leftCurly.getLineNo() != nextToken.getLineNo();
    }

}
