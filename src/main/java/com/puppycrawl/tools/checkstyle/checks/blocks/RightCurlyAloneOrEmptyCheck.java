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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the placement of right curlies for code blocks.
 * This check is specifically designed for Google Style.
 * </div>
 *
 * @since 10.22.0
 */
@StatelessCheck
public class RightCurlyAloneOrEmptyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_LINE_ALONE = "line.alone";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST rcurly = getRightCurly(ast);
        if (rcurly.getType() == TokenTypes.RCURLY && !isEmptyBlock(rcurly)) {
            final String targetSrcLine = getLine(rcurly.getLineNo() - 1);
            if (!CommonUtil.hasWhitespaceBefore(rcurly.getColumnNo(), targetSrcLine)) {
                log(rcurly, MSG_KEY_LINE_ALONE, "}", rcurly.getColumnNo() + 1);
            }
        }
    }

    /**
     * Gets the right curly brace token for the given AST node.
     *
     * @param ast the AST node to get the right curly brace for
     * @return the right curly brace token
     */
    private static DetailAST getRightCurly(DetailAST ast) {
        DetailAST result = ast.findFirstToken(TokenTypes.SLIST);
        if (result == null) {
            result = ast.findFirstToken(TokenTypes.OBJBLOCK);
        }
        if (result == null) {
            result = ast;
        }
        return result.getLastChild();
    }

    /**
     * Checks if the block is empty.
     *
     * @param rcurly the right curly brace token
     * @return true if the block is empty
     */
    private static boolean isEmptyBlock(DetailAST rcurly) {
        final DetailAST parent = rcurly.getParent();
        final boolean result;
        if (parent.getType() == TokenTypes.SLIST) {
            result = parent.getFirstChild() == rcurly;
        }
        else {
            result = rcurly.getPreviousSibling().getType() == TokenTypes.LCURLY;
        }
        return result;
    }

}
