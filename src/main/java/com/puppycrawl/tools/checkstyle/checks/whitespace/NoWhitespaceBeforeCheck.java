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

import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Checks that there is no whitespace before specific tokens.
 */
@StatelessCheck
public class NoWhitespaceBeforeCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "ws.preceded";

    /**
     * Common whitespace patterns to check for.
     */
    private static final List<String> COMMON_WHITESPACE_PATTERNS = Arrays.asList(
        " ;",
		"	;",
        " (",
		"	(",
        "  = ",
        " =  ",
        ". (",
		".	(",
        ".  (",
        "\" ."
    );

    /** Control whether whitespace is allowed if the token is at a linebreak. */
    private boolean allowLineBreaks;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.ELLIPSIS,
            TokenTypes.LABELED_STAT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.DOT,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
            TokenTypes.ELLIPSIS,
            TokenTypes.LABELED_STAT,
            TokenTypes.METHOD_REF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int[] line = getLineCodePoints(ast.getLineNo() - 1);
        final int columnBeforeToken = ast.getColumnNo() - 1;
        final boolean isFirstToken = columnBeforeToken == -1;

        if (containsInvalidWhitespacePattern(ast)) {
            log(ast, MSG_KEY, ast.getText());
        }

        if (!isInEmptyForInitializerOrCondition(ast.getPreviousSibling())
            && isWhitespaceOrLineStart(isFirstToken, line, columnBeforeToken)
            && requiresLeadingWhitespace(isFirstToken, columnBeforeToken, line)) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

    /**
     * Checks if the AST contains any invalid whitespace patterns.
     */
    private boolean containsInvalidWhitespacePattern(DetailAST ast) {
        final int type = ast.getType();
        return (type == TokenTypes.VARIABLE_DEF
            || type == TokenTypes.METHOD_REF
            || type == TokenTypes.METHOD_CALL
            || type == TokenTypes.METHOD_DEF)
            && containsWhitespacePattern(ast);
    }

    /**
     * Checks if the AST text contains any common whitespace patterns.
     */
    private boolean containsWhitespacePattern(DetailAST ast) {
        return COMMON_WHITESPACE_PATTERNS.stream().anyMatch(ast.toString()::contains);
    }

    /**
     * Checks if the position before the token is whitespace or line start.
     */
    private static boolean isWhitespaceOrLineStart(boolean isFirstToken, int[] line, int columnBeforeToken) {
        return isFirstToken || CommonUtil.isCodePointWhitespace(line, columnBeforeToken);
    }

    /**
     * Determines if whitespace is required before the token.
     */
    private boolean requiresLeadingWhitespace(boolean isFirstToken, int columnBeforeToken, int[] line) {
        return !allowLineBreaks
            || (!isFirstToken && !CodePointUtil.hasWhitespaceBefore(columnBeforeToken, line));
    }

    /**
     * Checks if semicolon is in empty for initializer or condition.
     */
    private static boolean isInEmptyForInitializerOrCondition(DetailAST sibling) {
        return sibling != null
            && !sibling.hasChildren()
            && (sibling.getType() == TokenTypes.FOR_INIT
            || sibling.getType() == TokenTypes.FOR_CONDITION);
    }

    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }
}
