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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the presence or absence of empty lines at the top and bottom of code blocks
 * (e.g. class, method, if, for). Configurable per block kind via {@code tokens}.
 * </div>
 *
 * <p>
 * For each configured block type, {@code topSeparator} controls the empty line
 * between the opening {@code '{'} and the first statement; {@code bottomSeparator}
 * controls the empty line between the last statement and the closing {@code '}'}.
 * </p>
 *
 * <p>
 * Policy values: {@code empty_line} (exactly one required), {@code empty_line_allowed}
 * (no validation), {@code no_empty_line} (forbidden).
 * </p>
 *
 * <p>
 * See <a href="https://github.com/checkstyle/checkstyle/issues/5313">#5313</a>.
 * </p>
 *
 * @since 11.0.0
 */
@StatelessCheck
public class EmptyLineWrappingInBlockCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message empty.line.wrapping.top.no in "messages.properties"
     * file.
     */
    public static final String MSG_EMPTY_LINE_WRAPPING_TOP_NO = "empty.line.wrapping.top.no";

    /**
     * A key is pointing to the warning message empty.line.wrapping.top.one in "messages.properties"
     * file.
     */
    public static final String MSG_EMPTY_LINE_WRAPPING_TOP_ONE = "empty.line.wrapping.top.one";

    /**
     * A key is pointing to the warning message empty.line.wrapping.bottom.no in
     * "messages.properties" file.
     */
    public static final String MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO = "empty.line.wrapping.bottom.no";

    /**
     * A key is pointing to the warning message empty.line.wrapping.bottom.one in
     * "messages.properties" file.
     */
    public static final String MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE =
            "empty.line.wrapping.bottom.one";

    /** Policy for empty lines after the opening brace of the block. */
    private SeparatorOption topSeparator = SeparatorOption.EMPTY_LINE;

    /** Policy for empty lines before the closing brace of the block. */
    private SeparatorOption bottomSeparator = SeparatorOption.EMPTY_LINE;

    /**
     * Set the policy for empty lines after the opening brace.
     *
     * @param value one of {@code empty_line}, {@code empty_line_allowed}, {@code no_empty_line}
     * @throws IllegalArgumentException if the value is not recognized
     * @since 11.0.0
     */
    public void setTopSeparator(String value) {
        topSeparator = SeparatorOption.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Set the policy for empty lines before the closing brace.
     *
     * @param value one of {@code empty_line}, {@code empty_line_allowed}, {@code no_empty_line}
     * @throws IllegalArgumentException if the value is not recognized
     * @since 11.0.0
     */
    public void setBottomSeparator(String value) {
        bottomSeparator = SeparatorOption.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Returns the default token set that this check validates.
     *
     * @return the default tokens
     */
    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    /**
     * Returns the set of tokens that can be configured for this check.
     *
     * @return the acceptable tokens
     */
    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_ARRAY_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ARRAY_INIT,
            TokenTypes.CASE_GROUP,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INSTANCE_INIT,
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
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.STATIC_INIT,
        };
    }

    /**
     * Returns the tokens that this check must be registered for.
     *
     * @return the empty array as this check has no required tokens
     */
    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * Called to process a token. Validates empty line wrapping after the opening brace
     * and before the closing brace of the block according to the configured policies.
     *
     * @param ast the token to process
     */
    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST leftCurly = findLeftBrace(ast);
        final DetailAST rightCurly = findRightBrace(ast);

        if (rightCurly != null && leftCurly != null) {
            final boolean onSameLine = leftCurly.getLineNo() == rightCurly.getLineNo();
            if (onSameLine) {
                checkSameLineBlock(leftCurly, rightCurly);
            }
            else {
                checkTopSeparator(leftCurly);
                checkBottomSeparator(rightCurly);
            }
        }
    }

    /**
     * Logs violations when the block is on a single line and policy requires empty lines.
     *
     * @param leftCurly the opening brace of the block
     * @param rightCurly the closing brace of the block
     */
    private void checkSameLineBlock(DetailAST leftCurly, DetailAST rightCurly) {
        if (topSeparator == SeparatorOption.EMPTY_LINE) {
            log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_ONE);
        }
        if (bottomSeparator == SeparatorOption.EMPTY_LINE) {
            log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE);
        }
    }

    /**
     * Checks and logs violations for the empty line after the opening brace.
     *
     * @param leftCurly the opening brace of the block
     */
    private void checkTopSeparator(DetailAST leftCurly) {
        if (hasEmptyLineAfter(leftCurly)) {
            if (topSeparator == SeparatorOption.NO_EMPTY_LINE) {
                log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_NO);
            }
        }
        else if (topSeparator == SeparatorOption.EMPTY_LINE) {
            log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_ONE);
        }
    }

    /**
     * Checks and logs violations for the empty line before the closing brace.
     *
     * @param rightCurly the closing brace of the block
     */
    private void checkBottomSeparator(DetailAST rightCurly) {
        if (hasEmptyLineBefore(rightCurly)) {
            if (bottomSeparator == SeparatorOption.NO_EMPTY_LINE) {
                log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO);
            }
        }
        else if (bottomSeparator == SeparatorOption.EMPTY_LINE) {
            log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE);
        }
    }

    /**
     * Finds the left (opening) brace of the block for the given AST node.
     * The result depends on the token type (e.g. SLIST for statements,
     * OBJBLOCK for type bodies, or the node itself for array inits).
     *
     * @param ast the AST node representing a block or block-containing construct
     * @return the AST of the opening brace, or null if not found
     */
    @Nullable
    private DetailAST findLeftBrace(DetailAST ast) {
        DetailAST leftBrace = null;
        switch (ast.getType()) {
            case TokenTypes.ARRAY_INIT, TokenTypes.ANNOTATION_ARRAY_INIT -> leftBrace = ast;
            case TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.ANNOTATION_DEF,
                TokenTypes.ENUM_DEF, TokenTypes.ENUM_CONSTANT_DEF, TokenTypes.LITERAL_NEW -> {
                // OBJBLOCK is optional: absent for enum constant without body (e.g. RED),
                // and for LITERAL_NEW without anonymous class body (e.g. new Foo()).
                final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
                if (objBlock != null) {
                    leftBrace = objBlock.getFirstChild();
                }
            }
            case TokenTypes.LITERAL_ELSE -> {
                // Special handling for the "else if" case.
                final DetailAST candidate = ast.getFirstChild();
                if (candidate.getType() == TokenTypes.SLIST) {
                    leftBrace = candidate;
                }
            }
            case TokenTypes.SLIST -> {
                // The scope block is a SLIST with another SLIST as the parent.
                if (ast.getParent().getType() == TokenTypes.SLIST) {
                    leftBrace = ast;
                }
            }
            case TokenTypes.CASE_GROUP ->
                // The CASE_GROUP is the parent of 'case', 'default' and SLIST
                // parent.
                leftBrace = ast.getLastChild().findFirstToken(TokenTypes.SLIST);
            case TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_DEFAULT ->
                // The 'case' and 'default' are siblings for SLIST parent.
                leftBrace = ast.getParent().getLastChild().findFirstToken(TokenTypes.SLIST);
            default ->
                // All the rest: for/while/if/STATIC_INIT/MethodDef,lambda,etc.
                leftBrace = ast.findFirstToken(TokenTypes.SLIST);
        }
        return leftBrace;
    }

    /**
     * Finds the right (closing) brace of the block for the given AST node.
     *
     * @param ast the AST node representing a block or block-containing construct
     * @return the AST of the closing brace, or null if not found
     */
    @Nullable
    private DetailAST findRightBrace(DetailAST ast) {
        final DetailAST block = switch (ast.getType()) {
            case TokenTypes.ARRAY_INIT, TokenTypes.ANNOTATION_ARRAY_INIT,
                TokenTypes.LITERAL_SWITCH ->
                ast;
            case TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.ANNOTATION_DEF,
                TokenTypes.ENUM_DEF, TokenTypes.ENUM_CONSTANT_DEF, TokenTypes.LITERAL_NEW ->
                ast.findFirstToken(TokenTypes.OBJBLOCK);
            case TokenTypes.LITERAL_ELSE -> {
                final DetailAST candidate = ast.getFirstChild();
                final DetailAST result;
                if (candidate.getType() == TokenTypes.SLIST) {
                    result = candidate;
                }
                else {
                    result = null;
                }
                yield result;
            }
            case TokenTypes.SLIST -> {
                final DetailAST result;
                if (ast.getParent().getType() == TokenTypes.SLIST) {
                    result = ast;
                }
                else {
                    result = null;
                }
                yield result;
            }
            case TokenTypes.CASE_GROUP ->
                ast.getLastChild().findFirstToken(TokenTypes.SLIST);
            case TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_DEFAULT ->
                ast.getParent().getLastChild().findFirstToken(TokenTypes.SLIST);
            default -> ast.findFirstToken(TokenTypes.SLIST);
        };
        final DetailAST rightBrace;
        if (block != null) {
            rightBrace = block.getLastChild();
        }
        else {
            rightBrace = null;
        }
        return rightBrace;
    }

    /**
     * Checks whether the line immediately after the given token's line is blank.
     *
     * @param leftCurly the token (e.g. opening brace) whose next line is checked
     * @return true if the next line is blank
     */
    private boolean hasEmptyLineAfter(DetailAST leftCurly) {
        // getLine is 0-based, getLineNo() is 1-based. Only called when !onSameLine, so the block
        // spans at least 2 lines and the next line after the opening brace always exists.
        return CommonUtil.isBlank(getLine(leftCurly.getLineNo()));
    }

    /**
     * Checks whether the line immediately before the given token's line is blank.
     *
     * @param rightCurly the token (e.g. closing brace) whose previous line is checked
     * @return true if the previous line is blank
     */
    private boolean hasEmptyLineBefore(DetailAST rightCurly) {
        // getLine function is 0 based, rightCurly is 1 based.
        // No out-of-bounds: this is only called when !onSameLine, so the block spans at least
        // 2 lines and the closing brace is on line 2 or later.
        return CommonUtil.isBlank(getLine(rightCurly.getLineNo() - 2));
    }

}
