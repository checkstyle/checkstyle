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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Locale;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for the placement of left curly braces (<code>'{'</code>) for code blocks.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code ignoreEnums} - Allow to ignore enums when left curly brace policy is EOL.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code option} - Specify the policy on placement of a left curly brace
 * (<code>'{'</code>).
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyOption}.
 * Default value is {@code eol}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CASE">
 * LITERAL_CASE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DEFAULT">
 * LITERAL_DEFAULT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">
 * LITERAL_DO</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">
 * LITERAL_ELSE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">
 * LITERAL_FINALLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">
 * LITERAL_FOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">
 * LITERAL_SWITCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">
 * LITERAL_SYNCHRONIZED</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">
 * LITERAL_TRY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">
 * LITERAL_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#OBJBLOCK">
 * OBJBLOCK</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
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
 * {@code line.break.after}
 * </li>
 * <li>
 * {@code line.new}
 * </li>
 * <li>
 * {@code line.previous}
 * </li>
 * </ul>
 *
 * @since 3.0
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

    /** Allow to ignore enums when left curly brace policy is EOL. */
    private boolean ignoreEnums = true;

    /**
     * Specify the policy on placement of a left curly brace (<code>'{'</code>).
     */
    private LeftCurlyOption option = LeftCurlyOption.EOL;

    /**
     * Setter to specify the policy on placement of a left curly brace (<code>'{'</code>).
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 3.0
     */
    public void setOption(String optionStr) {
        option = LeftCurlyOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Setter to allow to ignore enums when left curly brace policy is EOL.
     *
     * @param ignoreEnums check's option for ignoring enums.
     * @since 6.9
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * Visits token.
     *
     * @param ast the token to process
     * @noinspection SwitchStatementWithTooManyBranches
     * @noinspectionreason SwitchStatementWithTooManyBranches - we cannot reduce
     *      the number of branches in this switch statement, since many tokens
     *      require specific methods to find the first left curly
     */
    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST startToken;
        final DetailAST brace;

        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.COMPACT_CTOR_DEF:
                startToken = skipModifierAnnotations(ast);
                brace = ast.findFirstToken(TokenTypes.SLIST);
                break;
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.CLASS_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ENUM_CONSTANT_DEF:
            case TokenTypes.RECORD_DEF:
                startToken = skipModifierAnnotations(ast);
                brace = ast.findFirstToken(TokenTypes.OBJBLOCK);
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
                brace = getBraceFromSwitchMember(ast);
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
     * Gets the brace of a switch statement/ expression member.
     *
     * @param ast {@code DetailAST}.
     * @return {@code DetailAST} if the first child is {@code TokenTypes.SLIST},
     *     {@code null} otherwise.
     */
    @Nullable
    private static DetailAST getBraceFromSwitchMember(DetailAST ast) {
        final DetailAST brace;
        final DetailAST parent = ast.getParent();
        if (parent.getType() == TokenTypes.SWITCH_RULE) {
            brace = parent.findFirstToken(TokenTypes.SLIST);
        }
        else {
            brace = getBraceAsFirstChild(ast.getNextSibling());
        }
        return brace;
    }

    /**
     * Gets a SLIST if it is the first child of the AST.
     *
     * @param ast {@code DetailAST}.
     * @return {@code DetailAST} if the first child is {@code TokenTypes.SLIST},
     *     {@code null} otherwise.
     */
    @Nullable
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
     * Skip all {@code TokenTypes.ANNOTATION}s to the first non-annotation.
     *
     * @param ast {@code DetailAST}.
     * @return {@code DetailAST}.
     */
    private static DetailAST skipModifierAnnotations(DetailAST ast) {
        DetailAST resultNode = ast;
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

        if (modifiers != null) {
            final DetailAST lastAnnotation = findLastAnnotation(modifiers);

            if (lastAnnotation != null) {
                if (lastAnnotation.getNextSibling() == null) {
                    resultNode = modifiers.getNextSibling();
                }
                else {
                    resultNode = lastAnnotation.getNextSibling();
                }
            }
        }
        return resultNode;
    }

    /**
     * Find the last token of type {@code TokenTypes.ANNOTATION}
     * under the given set of modifiers.
     *
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
     *
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
            else if (!TokenUtil.areOnSameLine(startToken, brace)) {
                validateNewLinePosition(brace, startToken, braceLine);
            }
        }
    }

    /**
     * Validate EOL case.
     *
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
     *
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
     *
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
            || !TokenUtil.areOnSameLine(leftCurly, nextToken);
    }

}
