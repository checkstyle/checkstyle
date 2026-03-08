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
 * at the start of the block (before the first statement);
 * {@code bottomSeparator} controls the empty line at the end (after the last statement).
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
 * @since 13.4.0
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
     * @since 13.4.0
     */
    public void setTopSeparator(String value) {
        topSeparator = SeparatorOption.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Set the policy for empty lines before the closing brace.
     *
     * @param value one of {@code empty_line}, {@code empty_line_allowed}, {@code no_empty_line}
     * @throws IllegalArgumentException if the value is not recognized
     * @since 13.4.0
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
        if (leftCurly != null) {
            final DetailAST rightCurly = findRightBrace(leftCurly);
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
            log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_ONE, leftCurly.getText());
        }
        if (bottomSeparator == SeparatorOption.EMPTY_LINE) {
            log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, rightCurly.getText());
        }
    }

    /**
     * Checks and logs violations for the empty line after the opening brace.
     *
     * @param leftCurly the opening brace of the block
     */
    private void checkTopSeparator(DetailAST leftCurly) {
        if (topSeparator == SeparatorOption.NO_EMPTY_LINE) {
            if (hasBlankLineAfter(leftCurly)) {
                log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_NO, leftCurly.getText());
            }
        }
        else if (topSeparator == SeparatorOption.EMPTY_LINE
            && !hasOneEmptyLineAfter(leftCurly)) {
            log(leftCurly, MSG_EMPTY_LINE_WRAPPING_TOP_ONE, leftCurly.getText());
        }
    }

    /**
     * Checks and logs violations for the empty line before the closing brace.
     *
     * @param rightCurly the closing brace of the block
     */
    private void checkBottomSeparator(DetailAST rightCurly) {
        if (bottomSeparator == SeparatorOption.NO_EMPTY_LINE) {
            if (hasBlankLineBefore(rightCurly)) {
                log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO, rightCurly.getText());
            }
        }
        else if (bottomSeparator == SeparatorOption.EMPTY_LINE
            && !hasOneEmptyLineBefore(rightCurly)) {
            log(rightCurly, MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, rightCurly.getText());
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
    private static DetailAST findLeftBrace(DetailAST ast) {
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
     * Finds the right (closing) brace of the block for the given opening brace.
     * The result depends on the token type: for SLIST/array-init the closing brace
     * is the last child; for OBJBLOCK etc. it is the last child of the parent.
     *
     * @param leftBrace the opening brace AST, non-null
     * @return the closing brace AST
     */
    private static DetailAST findRightBrace(DetailAST leftBrace) {
        return switch (leftBrace.getType()) {
            case TokenTypes.ARRAY_INIT, TokenTypes.ANNOTATION_ARRAY_INIT, TokenTypes.SLIST ->
                leftBrace.getLastChild();
            default -> leftBrace.getParent().getLastChild();
        };
    }

    // Line-index convention: getLine(int) uses 0-based index; getLineNo() is 1-based.
    // The following helpers are only called when the block spans at least two lines (!onSameLine).

    /**
     * Checks whether the line immediately after the opening brace is blank.
     * Used when topSeparator is NO_EMPTY_LINE.
     *
     * @param leftCurly the opening brace token
     * @return true if the next line is blank
     */
    private boolean hasBlankLineAfter(DetailAST leftCurly) {
        return CommonUtil.isBlank(getLine(leftCurly.getLineNo()));
    }

    /**
     * Checks whether there is exactly one empty line after the opening brace.
     * Used when topSeparator is EMPTY_LINE. Two or more consecutive empty lines are rejected.
     *
     * @param leftCurly the opening brace token
     * @return true if the next line is blank and the line after that is not blank
     */
    private boolean hasOneEmptyLineAfter(DetailAST leftCurly) {
        boolean result = false;
        final int nextLineIndex = leftCurly.getLineNo();
        if (CommonUtil.isBlank(getLine(nextLineIndex))) {
            final int lineAfterNext = nextLineIndex + 1;
            result = !CommonUtil.isBlank(getLine(lineAfterNext));
        }
        return result;
    }

    /**
     * Checks whether the line immediately before the closing brace is blank.
     * Used when bottomSeparator is NO_EMPTY_LINE.
     *
     * @param rightCurly the closing brace token
     * @return true if the previous line is blank
     */
    private boolean hasBlankLineBefore(DetailAST rightCurly) {
        return CommonUtil.isBlank(getLine(rightCurly.getLineNo() - 2));
    }

    /**
     * Checks whether there is exactly one empty line before the closing brace.
     * Used when bottomSeparator is EMPTY_LINE. Two or more consecutive empty lines are rejected.
     *
     * @param rightCurly the closing brace token
     * @return true if the previous line is blank and the line before that is not blank
     */
    private boolean hasOneEmptyLineBefore(DetailAST rightCurly) {
        boolean result = false;
        final int prevLineIndex = rightCurly.getLineNo() - 2;
        if (CommonUtil.isBlank(getLine(prevLineIndex))) {
            result = !CommonUtil.isBlank(getLine(prevLineIndex - 1));
        }
        return result;
    }

}
