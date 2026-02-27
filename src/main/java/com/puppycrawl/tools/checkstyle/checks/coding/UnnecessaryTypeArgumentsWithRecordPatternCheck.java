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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

/**
 * <div>
 * Checks for unnecessary explicit type arguments in record patterns.
 * </div>
 *
 * <p>
 * When a record pattern declares explicit type arguments that can be inferred
 * by the compiler, those type arguments are redundant and reduce readability.
 * </p>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class UnnecessaryTypeArgumentsWithRecordPatternCheck extends AbstractCheck {

    /**
     * The error message key.
     */
    public static final String MSG_KEY = "unnecessary.type.arguments.record.pattern";

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.RECORD_PATTERN_DEF,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST typeAst =
            NullUtil.notNull(ast.findFirstToken(TokenTypes.TYPE));
        final DetailAST typeArgs = typeAst.findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        if (typeArgs != null && isUnnecessary(typeArgs)) {
            log(typeArgs, MSG_KEY);
        }
    }

    /**
     * Determines whether explicit type arguments in a record pattern
     * are unnecessary.
     *
     * @param typeArgs TYPE_ARGUMENTS ast node
     * @return true if the type arguments are necessary, false otherwise
     */
    private static boolean isUnnecessary(DetailAST typeArgs) {
        boolean violation = false;
        DetailAST typeArgument = typeArgs.findFirstToken(TokenTypes.TYPE_ARGUMENT);
        while (typeArgument != null) {
            final DetailAST firstChild = typeArgument.getFirstChild();
            if (firstChild.getType() != TokenTypes.WILDCARD_TYPE) {
                violation = true;
            }
            do {
                typeArgument = typeArgument.getNextSibling();
            } while (typeArgument != null
                    && typeArgument.getType() != TokenTypes.TYPE_ARGUMENT);
        }
        return violation;
    }
}
