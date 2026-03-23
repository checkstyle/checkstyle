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

import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the placement of right curly braces (<code>'}'</code>) for code blocks.
 * The right curly brace must be alone on a line. Empty blocks must follow
 * K and R style or use {@code {}} immediately, except in multi-block statements
 * ({@code if/else}, {@code try/catch/finally}) where {@code {}} is not allowed.
 *
 * Follows <a href="https://google.github.io/styleguide/javaguide.html#s4.1-braces">
 * Google Java Style Guide</a>.
 * </div>
 *
 * @since 13.6.0
 */
@StatelessCheck
public class RightCurlyAloneOrEmptyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ALONE_OR_EMPTY = "alone.or.empty";

    /**
     * Token types that do not allow concise empty blocks ({@code {}}).
     * Multi-block statements like {@code if/else} and {@code try/catch/finally}
     * must use K and R style braces instead.
     */
    private static final Set<Integer> NON_CONCISE_BLOCKS = Set.of(
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_ELSE,
        TokenTypes.LITERAL_TRY,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.LITERAL_FINALLY
    );

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_CASE,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST rcurly = null;
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ANNOTATION_DEF,
                 TokenTypes.ENUM_DEF, TokenTypes.RECORD_DEF -> {
                final DetailAST child =
                        NullUtil.notNull(ast.findFirstToken(TokenTypes.OBJBLOCK));
                rcurly = NullUtil.notNull(child.getLastChild());
            }

            case TokenTypes.LITERAL_SWITCH -> rcurly = ast.getLastChild();

            case TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_DEFAULT -> {
                final DetailAST parent = ast.getParent();
                if (parent.getType() == TokenTypes.SWITCH_RULE) {
                    rcurly = parent.findFirstToken(TokenTypes.SLIST);
                    if (rcurly != null) {
                        rcurly = rcurly.getLastChild();
                    }
                }
            }

            default -> {
                final DetailAST child = ast.findFirstToken(TokenTypes.SLIST);
                if (child != null) {
                    rcurly = child.getLastChild();
                }
            }
        }
        if (rcurly != null && rcurly.getType() == TokenTypes.RCURLY) {
            checkRcurly(rcurly, ast);
        }
    }

    /**
     * Checks the placement of a right curly brace and logs a violation if needed.
     * The brace must be alone on its line, or if the block is empty,
     * concise form ({@code {}}) is allowed only for non-multi-block statements.
     *
     * @param rcurly the right curly brace token
     * @param ast the block's parent token
     */
    private void checkRcurly(DetailAST rcurly, DetailAST ast) {
        if (checkOnRightSide(rcurly)) {
            log(rcurly, MSG_KEY_ALONE_OR_EMPTY,
                    rcurly.getText(), rcurly.getColumnNo() + 1);
        }
        else if (checkOnLeftSide(rcurly)
                && !isBlockEmptyAndAllowedConcise(rcurly, ast)) {
            log(rcurly, MSG_KEY_ALONE_OR_EMPTY,
                    rcurly.getText(), rcurly.getColumnNo() + 1);
        }
    }

    /**
     * Checks if the block is empty and concise form ({@code {}}) is allowed.
     * A block is empty if it contains no statements or comments.
     * Concise form is allowed only when the braces are on the same line
     * and the block is not a multi-block statement.
     *
     * @param rcurly the right curly brace token
     * @param block the block's parent token
     * @return {@code true} if the block is empty and concise form is permitted
     */
    private static boolean isBlockEmptyAndAllowedConcise(DetailAST rcurly, DetailAST block) {
        final DetailAST previousSibling = rcurly.getPreviousSibling();
        final boolean empty = previousSibling == null
                || previousSibling.getType() == TokenTypes.LCURLY;
        boolean isConsise = false;
        if (empty) {
            if (previousSibling != null) {
                isConsise = TokenUtil.areOnSameLine(rcurly, previousSibling);
            }
            else {
                isConsise = TokenUtil.areOnSameLine(rcurly, rcurly.getParent());
            }
        }
        return isConsise && !NON_CONCISE_BLOCKS.contains(block.getType());
    }

    /**
     * Checks if the right curly brace shares a line with the previous sibling,
     * or the parent token if no sibling exists.
     *
     * @param rcurly the right curly brace token
     * @return {@code true} if the brace is on the same line as the previous sibling or parent
     */
    private static boolean checkOnLeftSide(DetailAST rcurly) {
        final boolean whitespacePresent;
        if (rcurly.getPreviousSibling() != null) {
            whitespacePresent = TokenUtil.areOnSameLine(rcurly,
                    rcurly.getPreviousSibling());
        }
        else {
            whitespacePresent = TokenUtil.areOnSameLine(rcurly, rcurly.getParent());
        }
        return whitespacePresent;
    }

    /**
     * Checks if the right curly brace is on the same line as the preceding token.
     *
     * @param rcurly the right curly brace token
     * @return {@code true} if the brace is on the same line as the previous sibling
     *     or parent if no sibling exists
     */
    private static boolean checkOnRightSide(DetailAST rcurly) {
        final DetailAST nextToken = getNextToken(rcurly);
        return nextToken != null
                && nextToken.getType() != TokenTypes.SEMI
                && (TokenUtil.areOnSameLine(rcurly, nextToken)
                || checkIfFollowedByComment(rcurly, nextToken));
    }

    /**
     * Checks if the right curly brace is followed by a comment on the same line.
     *
     * @param rcurly the right curly brace token
     * @param nextToken the token following the right curly brace
     * @return {@code true} if a single-line or block comment follows on the same line
     */
    private static boolean checkIfFollowedByComment(DetailAST rcurly, DetailAST nextToken) {
        DetailAST comment = nextToken.findFirstToken(TokenTypes.SINGLE_LINE_COMMENT);
        DetailAST blockComment = nextToken.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);

        final DetailAST type = nextToken.findFirstToken(TokenTypes.TYPE);
        if (type != null) {
            comment = type.findFirstToken(TokenTypes.SINGLE_LINE_COMMENT);
            blockComment = type.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
        }
        return comment != null && TokenUtil.areOnSameLine(rcurly, comment)
                || blockComment != null && TokenUtil.areOnSameLine(rcurly, blockComment);
    }

    /**
     * Traverses up the AST to find the next sibling token after the right curly brace.
     *
     * @param rcurly the right curly brace token
     * @return the next sibling token, or {@code null} if none exists
     */
    private static DetailAST getNextToken(DetailAST rcurly) {
        DetailAST current = rcurly;
        DetailAST nextToken = null;
        while (current != null && nextToken == null) {
            nextToken = current.getNextSibling();
            current = current.getParent();
        }
        return nextToken;
    }
}
