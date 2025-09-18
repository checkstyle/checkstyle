///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the padding policy for type bodies. Ensures that a blank line is present
 * immediately after the opening brace and/or immediately before the closing brace
 * of type definitions ({@code class}, {@code interface}, {@code enum},
 * {@code record}, and annotation types). Optionally, inner type definitions can
 * be ignored via the skipInner property.
 * </div>
 *
 * @since 11.1.0
 */

@StatelessCheck
public class TypeBodyPaddingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_START_REQUIRED = "type.body.padding.start.required";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_END_REQUIRED = "type.body.padding.end.required";

    /**
     *  Require a blank line after '{'.
     */
    private boolean starting = true;

    /**
     *  Require a blank line before '}'.
     */
    private boolean ending;

    /**
     *  Skip enforcement when the type body is empty ('{}').
     */
    private boolean allowEmpty = true;

    /**
     * Whether to also check inner types.
     */
    private boolean skipInner = true;

    /**
     * Setter to control whether padding is required at the start of the body.
     *
     * @param starting {@code true} to require padding at the start of the body.
     * @since 11.1.0
     */
    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    /**
     * Setter to control whether padding is required at the end of the body.
     *
     * @param ending {@code true} to require padding at the end of the body.
     * @since 11.1.0
     */
    public void setEnding(boolean ending) {
        this.ending = ending;
    }

    /**
     * Setter to control whether to allow empty type bodies without padding.
     *
     * @param allowEmpty {@code true} to allow empty type bodies.
     * @since 11.1.0
     */
    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    /**
     * Setter to control whether to skip checking of inner types.
     *
     * @param skipInner {@code false} to also check inner types.
     * @since 11.1.0
     */
    public void setSkipInner(boolean skipInner) {
        this.skipInner = skipInner;
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
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {

        if (!shouldSkip(ast)) {

            final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
            final DetailAST leftCurly = objBlock.findFirstToken(TokenTypes.LCURLY);
            final DetailAST rightCurly = objBlock.findFirstToken(TokenTypes.RCURLY);
            final boolean isEmptyBody = leftCurly.getNextSibling() == rightCurly;

            if (isEmptyBody) {
                handleEmptyBody(leftCurly, rightCurly);
            }
            else {
                handleNonEmptyBody(leftCurly, rightCurly);
            }
        }
    }

    @Override
    public boolean isCommentNodesRequired() {
        // Comments should not count as "blank"
        return true;
    }

    /**
     * Determines whether to skip checking the given AST node.
     *
     * @param ast the AST node to check
     * @return {@code true} if the node should be skipped, {@code false} otherwise
     */
    private boolean shouldSkip(DetailAST ast) {
        return skipInner && isInner(ast);
    }

    /**
     * Handles the case of an empty type body.
     *
     * @param leftCurly the type definition's opening brace AST
     * @param rightCurly the type definition's closing brace AST
     */
    private void handleEmptyBody(DetailAST leftCurly, DetailAST rightCurly) {
        if (!allowEmpty) {
            if (starting) {
                log(leftCurly, MSG_START_REQUIRED);
            }
            if (ending) {
                log(rightCurly, MSG_END_REQUIRED);
            }
        }
    }

    /**
     * Handles the case of a non-empty type body.
     *
     * @param leftCurly the type definition's opening brace AST
     * @param rightCurly the type definition's closing brace AST
     */
    private void handleNonEmptyBody(DetailAST leftCurly, DetailAST rightCurly) {
        if (starting && !isNextLineBlank(leftCurly)) {
            log(leftCurly.getNextSibling(), MSG_START_REQUIRED);
        }
        if (ending && !isPreviousLineBlank(rightCurly)) {
            log(rightCurly, MSG_END_REQUIRED);
        }
    }

    /**
     * Checks whether the given AST represents an inner type definition.
     *
     * @param ast the AST to check
     * @return {@code true} if the AST is an inner type definition, {@code false} otherwise
     */
    private static boolean isInner(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.OBJBLOCK;
    }

    /**
     * Checks if the line following the line of the given AST node is blank.
     *
     * @param ast the AST node to check
     * @return {@code true} if the next line is blank, {@code false} otherwise
     */
    private boolean isNextLineBlank(DetailAST ast) {
        return CommonUtil.isBlank(getLines()[ast.getLineNo()]);
    }

    /**
     * Checks if the line preceding the line of the given AST node is blank.
     *
     * @param ast the AST node to check
     * @return {@code true} if the previous line is blank, {@code false} otherwise
     */
    private boolean isPreviousLineBlank(DetailAST ast) {
        return CommonUtil.isBlank(getLines()[ast.getLineNo() - 2]);
    }
}
