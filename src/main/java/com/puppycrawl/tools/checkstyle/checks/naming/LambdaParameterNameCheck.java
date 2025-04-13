////
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks lambda parameter names.
 * </div>
 * <ul>
 * <li>
 * Property {@code format} - Sets the pattern to match valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^([a-z][a-zA-Z0-9]*|_)$"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 8.11
 */
public class LambdaParameterNameCheck extends AbstractNameCheck {

    /** Creates new instance of {@code LambdaParameterNameCheck}. */
    public LambdaParameterNameCheck() {
        super("^([a-z][a-zA-Z0-9]*|_)$");
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
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final boolean isInSwitchRule = ast.getParent().getType() == TokenTypes.SWITCH_RULE;

        if (Objects.nonNull(ast.findFirstToken(TokenTypes.PARAMETERS))) {
            final DetailAST parametersNode = ast.findFirstToken(TokenTypes.PARAMETERS);
            TokenUtil.forEachChild(parametersNode, TokenTypes.PARAMETER_DEF, super::visitToken);
        }
        else if (!isInSwitchRule) {
            super.visitToken(ast);
        }
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return true;
    }

}
