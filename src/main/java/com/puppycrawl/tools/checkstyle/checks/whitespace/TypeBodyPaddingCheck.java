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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the padding of the body of type definitions (classes, interfaces,
 * enums, records).
 * That is, whether a blank line is required immediately after the opening brace
 * and/or immediately before the closing brace of type bodies.
 * Empty type bodies are exempt from this check by default.
 * </div>
 *
 * <p>
 * A blank line (padding) line is a line containing only whitespace characters.
 * </p>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class TypeBodyPaddingCheck extends AbstractCheck {

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when a blank line is missing after the opening brace of a type body.
     */
    public static final String MSG_AFTER_LCURLY = "type.body.padding.after.lcurly";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when a blank line is missing before the closing brace of a type body.
     */
    public static final String MSG_BEFORE_RCURLY = "type.body.padding.before.rcurly";

    /**
     * Require a blank line after the type body opening brace.
     */
    private boolean atStartOfBody = true;

    /**
     * Require a blank line before the type body closing brace.
     */
    private boolean atEndOfBody = true;

    /**
     * Allow empty type bodies (those with no members) to omit blank line padding.
     */
    private boolean allowEmpty = true;

    /**
     * Whether to skip inner types.
     */
    private boolean skipInner = true;

    /**
     * Whether to skip local types (types defined inside methods or constructors).
     */
    private boolean skipLocal = true;

    /**
     * Creates a new {@code TypeBodyPaddingCheck} instance.
     */
    public TypeBodyPaddingCheck() {
        // no code by default
    }

    /**
     * Setter to require a blank line after the type body opening brace.
     *
     * @param atStartOfBody the value to set.
     * @since 13.9.0
     */
    public void setAtStartOfBody(boolean atStartOfBody) {
        this.atStartOfBody = atStartOfBody;
    }

    /**
     * Setter to require a blank line before the type body closing brace.
     *
     * @param atEndOfBody the value to set.
     * @since 13.9.0
     */
    public void setAtEndOfBody(boolean atEndOfBody) {
        this.atEndOfBody = atEndOfBody;
    }

    /**
     * Setter to allow empty type bodies to omit blank line padding.
     *
     * @param allowEmpty the value to set.
     * @since 13.9.0
     */
    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    /**
     * Setter to control whether to skip checking of inner types.
     *
     * @param skipInner {@code false} to also check inner types.
     * @since 13.9.0
     */
    public void setSkipInner(boolean skipInner) {
        this.skipInner = skipInner;
    }

    /**
     * Setter to control whether to skip checking of local types.
     *
     * @param skipLocal {@code false} to also check local types.
     * @since 13.9.0
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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST lcurly = objBlock.findFirstToken(TokenTypes.LCURLY);
        final DetailAST rcurly = objBlock.findFirstToken(TokenTypes.RCURLY);

        // A type body is "empty" when LCURLY and RCURLY are the only children
        // (OBJBLOCK's only children are LCURLY and RCURLY).
        final boolean isEmpty = lcurly.getNextSibling() == rcurly;

        if (!shouldSkipType(ast, isEmpty)) {
            if (requirePaddingAfterLcurly(lcurly, rcurly)) {
                log(lcurly, MSG_AFTER_LCURLY);
            }
            if (requirePaddingBeforeRcurly(lcurly, rcurly)) {
                log(rcurly, MSG_BEFORE_RCURLY);
            }
        }
    }

    /**
     * Checks if padding is required after the left curly brace.
     *
     * @param lcurly the left curly brace.
     * @param rcurly the right curly brace.
     * @return true if padding is required.
     */
    private boolean requirePaddingAfterLcurly(DetailAST lcurly, DetailAST rcurly) {
        return atStartOfBody
                && (TokenUtil.areOnSameLine(lcurly, rcurly)
                        || !haveBlankLineAfterLeftCurly(lcurly));
    }

    /**
     * Checks if padding is required before the right curly brace.
     *
     * @param lcurly the left curly brace.
     * @param rcurly the right curly brace.
     * @return true if padding is required.
     */
    private boolean requirePaddingBeforeRcurly(DetailAST lcurly, DetailAST rcurly) {
        return atEndOfBody
                && (TokenUtil.areOnSameLine(lcurly, rcurly)
                        || !haveBlankLineBeforeRightCurly(rcurly));
    }

    /**
     * Checks that a blank line exists after the opening brace of the type body.
     * The line immediately following the LCURLY line must be blank (contain only
     * whitespace).
     *
     * @param lcurly the LCURLY token.
     * @return true if there is a blank line after the opening brace.
     */
    private boolean haveBlankLineAfterLeftCurly(DetailAST lcurly) {
        final int nextLineIndex = lcurly.getLineNo();

        return CommonUtil.isBlank(getLine(nextLineIndex));
    }

    /**
     * Checks that a blank line exists before the closing brace of the type body.
     * The line immediately preceding the RCURLY line must be blank.
     *
     * @param rcurly the RCURLY token.
     * @return true if there is a blank line before the closing brace.
     */
    private boolean haveBlankLineBeforeRightCurly(DetailAST rcurly) {
        // The line right before the closing brace must be blank.
        // getLine uses 0-based index; rcurlyLine is 1-based.
        // Line before rcurly has 0-based index: rcurlyLine - 2.
        final int prevLineIndex = rcurly.getLineNo() - 2;
        return CommonUtil.isBlank(getLine(prevLineIndex));
    }

    /**
     * Determines whether to skip checking the given AST node.
     *
     * @param ast the AST node to check
     * @param isEmpty whether the type body is empty
     * @return {@code true} if the node should be skipped, {@code false} otherwise
     */
    private boolean shouldSkipType(DetailAST ast, boolean isEmpty) {
        final boolean result;
        if (allowEmpty && isEmpty) {
            result = true;
        }
        else if (ScopeUtil.isOuterMostType(ast)) {
            result = false;
        }
        else if (ScopeUtil.isInCodeBlock(ast)) {
            result = skipLocal;
        }
        else {
            result = skipInner;
        }
        return result;
    }

}
