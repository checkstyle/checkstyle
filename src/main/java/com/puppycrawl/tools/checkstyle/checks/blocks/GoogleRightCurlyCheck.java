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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the placement of right curly braces (<code>'}'</code>) for code blocks, following
 * the <a href="https://google.github.io/styleguide/javaguide.html#s4.1-braces">
 *     Google Java Style Guide </a>.
 * <p>
 * For nonempty blocks the right curly brace must begin its own line,
 * unless it is followed by {@code else}, {@code catch}, {@code finally}, or a comma,
 * in which case no line break follows it.
 * </p>
 * <p>
 * For empty blocks, either K&amp;R style or the concise {@code {}} form is
 * allowed, except within a multi-block statement ({@code if/else}, {@code try/catch/finally}).
 * </p>
 * </div>
 *
 * @since 13.11.0
 */
@StatelessCheck
public class GoogleRightCurlyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_ALONE = "line.alone";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_BREAK_AFTER = "line.break.after";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_BREAK_BEFORE = "line.break.before";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_CONCISE_BLOCK = "empty.block.concise";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_SAME = "line.same";

    /**
     * Creates a new {@code GoogleRightCurlyCheck} instance.
     */
    public GoogleRightCurlyCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_DO,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST rightCurly = null;
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
                 TokenTypes.ANNOTATION_DEF, TokenTypes.RECORD_DEF -> {
                final DetailAST child =
                        NullUtil.notNull(ast.findFirstToken(TokenTypes.OBJBLOCK));
                rightCurly = NullUtil.notNull(child.getLastChild());
            }

            case TokenTypes.OBJBLOCK -> rightCurly = NullUtil.notNull(ast.getLastChild());

            case TokenTypes.LITERAL_SWITCH -> rightCurly = ast.getLastChild();

            case TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_DEFAULT -> handleCaseAndDefault(ast);

            default -> {
                final DetailAST child = ast.findFirstToken(TokenTypes.SLIST);
                if (child != null) {
                    rightCurly = child.getLastChild();
                }
            }
        }
        if (rightCurly != null) {
            checkRightBrace(ast, rightCurly);
        }
    }

    /**
     * Checks the right curly brace placement for {@code case} and
     * {@code default} blocks, covering both old style {@code case X:}
     * and new style {@code case X ->} switch syntax.
     *
     * <p>For old-style syntax, a case label may be followed by multiple
     * {@code {}} blocks in sequence, and each such block's right curly
     * brace is checked. For new-style syntax, the block following the
     * arrow (e.g. {@code case X -> { ... }}) is checked,
     * expression with no block (e.g. {@code case X -> expr;}) is skipped.
     *
     * @param ast the {@code case} or {@code default} token
     */
    private void handleCaseAndDefault(DetailAST ast) {
        DetailAST startToken = ast;
        if (ast.getParent().getType() == TokenTypes.CASE_GROUP) {
            final DetailAST nextSibling = startToken.getNextSibling();
            if (nextSibling != null) {
                startToken = nextSibling.findFirstToken(TokenTypes.SLIST);
            }
        }
        for (DetailAST current = startToken; current != null;
             current = current.getNextSibling()) {
            if (current.getType() == TokenTypes.SLIST) {
                final DetailAST rightBrace = NullUtil.notNull(current.getLastChild());
                checkRightBrace(ast, rightBrace);
            }
        }
    }

    /**
     * Logs violation message for given brace token.
     *
     * @param message the violation message key.
     * @param brace the right curly brace.
     */
    private void logViolations(String message, DetailAST brace) {
        if (MSG_KEY_CONCISE_BLOCK.equals(message)) {
            log(brace, message);
        }
        else {
            log(brace, message, brace.getText(), brace.getColumnNo() + 1);
        }
    }

    /**
     * Checks that a right curly brace is placed correctly, per K&amp;R style.
     *
     * <p>If the block is part of a multi-block statement (e.g. {@code if/else},
     * {@code try/catch/finally}, or {@code do/while}), the closing brace must be
     * on the same line as the next block's starting keyword. Otherwise, the
     * brace must be alone on its own line, unless the block is empty, in which
     * case the concise {@code {}} form is allowed.
     *
     * @param currentBlock the block whose right curly brace is being checked
     * @param brace the right curly brace token
     */
    private void checkRightBrace(DetailAST currentBlock, DetailAST brace) {
        final DetailAST nextToken = getNextToken(brace);
        final boolean hasContentAround = contentAround(currentBlock, brace, nextToken);
        if (nextToken != null && isPartOfMultiBlock(currentBlock, nextToken)) {
            checkMultiBlockStatement(currentBlock, brace, nextToken);
        }
        else if (currentBlock.getParent().getType() == TokenTypes.LITERAL_ELSE
                || TokenUtil.isOfType(currentBlock, TokenTypes.LITERAL_ELSE,
                TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_FINALLY)) {
            if (hasContentAround) {
                logViolations(MSG_KEY_LINE_ALONE, brace);
            }
        }
        else if (isEmpty(brace)) {
            verifyEmptyBlock(currentBlock, brace, nextToken);
        }
        else if (hasContentAround) {
            logViolations(MSG_KEY_LINE_ALONE, brace);
        }
    }

    /**
     * Checks that the right curly brace of a multi-block statement (e.g. {@code if/else},
     * {@code try/catch/finally}, {@code do/while}) is placed correctly relative to the next block.
     *
     * @param currentBlock the current block
     * @param brace the right curly brace
     * @param nextBlock the next block in multi-block statement
     */
    private void checkMultiBlockStatement(DetailAST currentBlock, DetailAST brace,
        DetailAST nextBlock) {
        if (TokenUtil.areOnSameLine(brace, nextBlock)) {
            if (hasContentOnLeftSide(brace) && !(currentBlock.getType() == TokenTypes.LITERAL_DO
                    && isEmpty(brace))) {
                logViolations(MSG_KEY_LINE_BREAK_BEFORE, brace);
            }
        }
        else {
            logViolations(MSG_KEY_LINE_SAME, brace);
        }
    }

    /**
     * Checks empty block which should be concise and alone.
     *
     * @param currentBlock the current empty block.
     * @param brace the right curly token.
     * @param nextToken the token after right curly brace.
     */
    private void verifyEmptyBlock(DetailAST currentBlock, DetailAST brace,
        @Nullable DetailAST nextToken) {
        if (isNotConcise(brace)) {
            logViolations(MSG_KEY_CONCISE_BLOCK, brace);
        }
        else if (nextToken != null
                && hasContentOnRightSide(currentBlock, brace, nextToken)) {
            logViolations(MSG_KEY_LINE_BREAK_AFTER, brace);
        }
    }

    /**
     * Checks if the right curly has content around.
     *
     * @param currentBlock the current block
     * @param brace the right curly brace
     * @param nextToken the next token after right curly
     * @return {@code true} if right curly has content on its left or right.
     */
    private static boolean contentAround(DetailAST currentBlock, DetailAST brace,
        @Nullable DetailAST nextToken) {
        return nextToken != null
                && hasContentOnRightSide(currentBlock, brace, nextToken)
                || hasContentOnLeftSide(brace);
    }

    /**
     * Checks whether the current block is part
     * of a multi-block statement ({@code if/else},
     * {@code try/catch/finally}, or {@code do/while}).
     *
     * @param currentBlock the current block
     * @param nextBlock the block following {@code ast}
     * @return {@code true} if {@code ast} and {@code nextBlock} belong to
     *         the same multi-block statement
     */
    private static boolean isPartOfMultiBlock(DetailAST currentBlock, DetailAST nextBlock) {
        final int nextBlockType = nextBlock.getType();
        return switch (currentBlock.getType()) {
            case TokenTypes.LITERAL_IF ->
                nextBlockType == TokenTypes.LITERAL_ELSE;
            case TokenTypes.LITERAL_TRY, TokenTypes.LITERAL_CATCH ->
                nextBlockType == TokenTypes.LITERAL_CATCH
                    || nextBlockType == TokenTypes.LITERAL_FINALLY;
            case TokenTypes.LITERAL_DO -> true;
            default -> false;
        };
    }

    /**
     * Checks if the block is not concise and has content on left side of right brace.
     *
     * @param brace the right curly brace token
     * @return {@code true} if the brace has content on left.
     */
    private static boolean hasContentOnLeftSide(DetailAST brace) {
        DetailAST previousToken = brace.getPreviousSibling();
        if (previousToken == null) {
            previousToken = brace.getParent();
        }
        if (previousToken.getType() != TokenTypes.SLIST) {
            while (previousToken.hasChildren()) {
                previousToken = previousToken.getLastChild();
            }
        }
        return TokenUtil.areOnSameLine(brace, previousToken);
    }

    /**
     * Checks if the right curly brace is part multi-block statement or no
     * content on right side of right brace.
     *
     * @param block the current block
     * @param brace the right curly brace token
     * @param nextToken the next token of right curly.
     * @return {@code true} if the brace is on the same line as the previous sibling
     *     or parent if no sibling exists
     */
    private static boolean hasContentOnRightSide(DetailAST block, DetailAST brace,
            DetailAST nextToken) {
        final DetailAST afterNext = getNextToken(nextToken);
        final boolean onSameNext = TokenUtil.areOnSameLine(brace, nextToken);
        final boolean onSameAfterNext = afterNext != null
                && TokenUtil.areOnSameLine(brace, afterNext);
        final boolean nextIsValid =
                TokenUtil.isOfType(nextToken, TokenTypes.SEMI, TokenTypes.COMMA)
                && (block.getType() == TokenTypes.OBJBLOCK
                || nextToken.getParent().getType() == TokenTypes.SLIST);
        return onSameNext && !nextIsValid || onSameAfterNext;
    }

    /**
     * Checks if block is empty.
     *
     * @param brace the right curly brace.
     * @return {@code true} if the block is empty.
     */
    private static boolean isEmpty(DetailAST brace) {
        final DetailAST previousSibling = brace.getPreviousSibling();
        return previousSibling == null || previousSibling.getType() == TokenTypes.LCURLY;
    }

    /**
     * Checks if the block not is concise.
     *
     * @param brace right curly token
     * @return {@code true} if block is not concise.
     */
    private static boolean isNotConcise(DetailAST brace) {
        final DetailAST lcurly = brace.getParent();
        return lcurly.getLineNo() != brace.getLineNo()
                || lcurly.getColumnNo() + 1 != brace.getColumnNo();
    }

    /**
     * Traverses up the AST to find the next sibling token after the right curly brace.
     *
     * @param node ast token
     * @return the next sibling token, or {@code null} if none exists
     */
    @Nullable
    private static DetailAST getNextToken(DetailAST node) {
        DetailAST current = node;
        DetailAST nextToken = null;
        while (current != null && nextToken == null) {
            nextToken = current.getNextSibling();
            current = current.getParent();
        }
        return nextToken;
    }

}
