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

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the padding policy for type bodies. Ensures that a blank line is present
 * immediately after the opening brace and/or immediately before the closing brace
 * of type definitions: ({@code class}, {@code interface}, {@code enum},
 * {@code record}, and {@code annotation} types).
 * </div>
 *
 * @since 12.2.0
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
     * Whether to skip local types (types defined inside methods or constructors).
     */
    private boolean skipLocal = true;

    /**
     * Setter to control whether padding is required at the start of the body.
     *
     * @param starting {@code true} to require padding at the start of the body.
     * @since 12.2.0
     */
    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    /**
     * Setter to control whether padding is required at the end of the body.
     *
     * @param ending {@code true} to require padding at the end of the body.
     * @since 12.2.0
     */
    public void setEnding(boolean ending) {
        this.ending = ending;
    }

    /**
     * Setter to control whether to allow empty type bodies without padding.
     *
     * @param allowEmpty {@code true} to allow empty type bodies.
     * @since 12.2.0
     */
    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    /**
     * Setter to control whether to skip checking of inner types.
     *
     * @param skipInner {@code false} to also check inner types.
     * @since 12.2.0
     */
    public void setSkipInner(boolean skipInner) {
        this.skipInner = skipInner;
    }

    /**
     * Setter to control whether to skip checking of local types.
     *
     * @param skipLocal {@code false} to also check local types.
     * @since 12.2.0
     */
    public void setSkipLocal(boolean skipLocal) {
        this.skipLocal = skipLocal;
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
        final DetailAST objBlock = getObjBlock(ast);
        if (!shouldSkip(ast) && hasBodyToProcess(objBlock)) {
            if (hasStartViolation(objBlock)) {
                log(getLeftCurly(objBlock), MSG_START_REQUIRED);
            }
            if (hasEndViolation(objBlock)) {
                log(getRightCurly(objBlock), MSG_END_REQUIRED);
            }
        }
    }

    @Override
    public boolean isCommentNodesRequired() {
        // Comments should not count as "blank"
        return true;
    }

    /**
     * Determines if the check should be applied to the given AST node.
     *
     * @param ast the AST node representing the type definition
     * @return {@code true} if the check should be applied, {@code false} otherwise
     */
    private boolean hasBodyToProcess(DetailAST ast) {
        final DetailAST leftCurly = getLeftCurly(ast);
        final DetailAST nextNode = getNextSibling(leftCurly);
        final boolean bodyIsEmpty = nextNode.getType() == TokenTypes.RCURLY;
        return !bodyIsEmpty || !allowEmpty;
    }

    /**
     * Checks for a violation of the starting padding rule.
     *
     * @param objBlock the AST node representing the type definition
     * @return {@code true} if there is a violation, {@code false} otherwise
     */
    private boolean hasStartViolation(DetailAST objBlock) {
        boolean violation = false;
        if (starting) {
            final DetailAST leftCurly = getLeftCurly(objBlock);
            if (TokenUtil.areOnSameLine(leftCurly, getRightCurly(objBlock))) {
                violation = true;
            }
            else if (!nextLineIsBlankFor(leftCurly)) {
                violation = true;
            }
        }
        return violation;
    }

    /**
     * Checks for a violation of the ending padding rule.
     *
     * @param ast the AST node representing the type definition
     * @return {@code true} if there is a violation, {@code false} otherwise
     */
    private boolean hasEndViolation(DetailAST ast) {
        boolean violation = false;
        if (ending) {
            final DetailAST rightCurly = getRightCurly(ast);
            if (TokenUtil.areOnSameLine(getLeftCurly(ast), rightCurly)) {
                violation = true;
            }
            else if (!previousLineIsBlankFor(rightCurly)) {
                violation = true;
            }
        }
        return violation;
    }

    /**
     * Determines whether to skip checking the given AST node.
     *
     * @param ast the AST node to check
     * @return {@code true} if the node should be skipped, {@code false} otherwise
     */
    private boolean shouldSkip(DetailAST ast) {
        final boolean isMemberOrNested = hasEnclosingType(ast) && !isLocal(ast);
        return skipInner && isMemberOrNested || skipLocal && isLocal(ast);
    }

    /**
     * Determines if the given AST node represents a local type.
     *
     * @param ast the AST node to check
     * @return {@code true} if the node is a local type, {@code false} otherwise
     */
    private static boolean isLocal(DetailAST ast) {
        boolean result = false;
        DetailAST parent = ast.getParent();
        while (parent.getType() != TokenTypes.COMPILATION_UNIT) {
            if (TokenUtil.isOfType(parent.getType(),
                    TokenTypes.METHOD_DEF,
                    TokenTypes.CTOR_DEF,
                    TokenTypes.LAMBDA,
                    TokenTypes.STATIC_INIT,
                    TokenTypes.INSTANCE_INIT)) {
                result = true;
                break;
            }
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * Determines if the given AST node has an enclosing type definition.
     *
     * @param ast the AST node to check
     * @return {@code true} if the node has an enclosing type, {@code false} otherwise
     */
    private static boolean hasEnclosingType(DetailAST ast) {
        boolean result = false;
        DetailAST parent = ast.getParent();
        while (parent.getType() != TokenTypes.COMPILATION_UNIT) {
            if (TokenUtil.isOfType(parent.getType(),
                    TokenTypes.CLASS_DEF,
                    TokenTypes.INTERFACE_DEF,
                    TokenTypes.ENUM_DEF,
                    TokenTypes.RECORD_DEF,
                    TokenTypes.ANNOTATION_DEF)) {
                result = true;
                break;
            }
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * Checks if the line following the line of the given AST node is blank.
     *
     * @param ast the AST node to check
     * @return {@code true} if the next line is blank, {@code false} otherwise
     */
    private boolean nextLineIsBlankFor(DetailAST ast) {
        return CommonUtil.isBlank(getLine(ast.getLineNo()));
    }

    /**
     * Checks if the line preceding the line of the given AST node is blank.
     *
     * @param ast the AST node to check
     * @return {@code true} if the previous line is blank, {@code false} otherwise
     */
    private boolean previousLineIsBlankFor(DetailAST ast) {
        return CommonUtil.isBlank(getLine(ast.getLineNo() - 2));
    }

    /**
     * Retrieves the OBJBLOCK child of the given AST node.
     *
     * @param ast the AST node to retrieve from
     * @return the OBJBLOCK child node
     */
    private static DetailAST getObjBlock(DetailAST ast) {
        return Objects.requireNonNull(ast.findFirstToken(TokenTypes.OBJBLOCK));
    }

    /**
     * Retrieves the left curly brace token from the given AST node.
     *
     * @param ast the AST node to retrieve from
     * @return the left curly brace token
     */
    private static DetailAST getLeftCurly(DetailAST ast) {
        return Objects.requireNonNull(ast.findFirstToken(TokenTypes.LCURLY));
    }

    /**
     * Retrieves the right curly brace token from the given AST node.
     *
     * @param ast the AST node to retrieve from
     * @return the right curly brace token
     */
    private static DetailAST getRightCurly(DetailAST ast) {
        return Objects.requireNonNull(ast.findFirstToken(TokenTypes.RCURLY));
    }

    /**
     * Retrieves the next sibling of the given AST node.
     *
     * @param ast the AST node to retrieve from
     * @return the next sibling node
     */
    private static DetailAST getNextSibling(DetailAST ast) {
        return Objects.requireNonNull(ast.getNextSibling());
    }
}
