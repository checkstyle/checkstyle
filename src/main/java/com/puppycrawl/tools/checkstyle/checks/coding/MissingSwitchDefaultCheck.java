///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that switch statement has a {@code default} clause.
 * </p>
 * <p>
 * Rationale: It's usually a good idea to introduce a
 * default case in every switch statement. Even if
 * the developer is sure that all currently possible
 * cases are covered, this should be expressed in the
 * default branch, e.g. by using an assertion. This way
 * the code is protected against later changes, e.g.
 * introduction of new types in an enumeration type.
 * </p>
 * <p>
 * This check does not validate any switch expressions. Rationale:
 * The compiler requires switch expressions to be exhaustive. This means
 * that all possible inputs must be covered.
 * </p>
 * <p>
 * This check does not validate switch statements that use pattern or null
 * labels. Rationale: Switch statements that use pattern or null labels are
 * checked by the compiler for exhaustiveness. This means that all possible
 * inputs must be covered.
 * </p>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-15.html#jls-15.28">
 *     Java Language Specification</a> for more information about switch statements
 *     and expressions.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code missing.switch.default}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class MissingSwitchDefaultCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.switch.default";

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
        return new int[] {TokenTypes.LITERAL_SWITCH};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!containsDefaultLabel(ast)
                && !containsPatternCaseLabelElement(ast)
                && !containsDefaultCaseLabelElement(ast)
                && !containsNullCaseLabelElement(ast)
                && !isSwitchExpression(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the case group or its sibling contain the 'default' switch.
     *
     * @param detailAst first case group to check.
     * @return true if 'default' switch found.
     */
    private static boolean containsDefaultLabel(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst,
                ast -> ast.findFirstToken(TokenTypes.LITERAL_DEFAULT) != null
        ).isPresent();
    }

    /**
     * Checks if a switch block contains a case label with a pattern variable definition.
     * In this situation, the compiler enforces the given switch block to cover
     * all possible inputs, and we do not need a default label.
     *
     * @param detailAst first case group to check.
     * @return true if switch block contains a pattern case label element
     */
    private static boolean containsPatternCaseLabelElement(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst, ast -> {
            return ast.getFirstChild() != null
                    && ast.getFirstChild().findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF) != null;
        }).isPresent();
    }

    /**
     * Checks if a switch block contains a default case label.
     *
     * @param detailAst first case group to check.
     * @return true if switch block contains default case label
     */
    private static boolean containsDefaultCaseLabelElement(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst, ast -> {
            return ast.getFirstChild() != null
                    && ast.getFirstChild().findFirstToken(TokenTypes.LITERAL_DEFAULT) != null;
        }).isPresent();
    }

    /**
     * Checks if a switch block contains a null case label.
     *
     * @param detailAst first case group to check.
     * @return true if switch block contains null case label
     */
    private static boolean containsNullCaseLabelElement(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst, ast -> {
            return ast.getFirstChild() != null
                     && verifyNullCaseLabel(ast.getFirstChild(), TokenTypes.LITERAL_NULL) != null;
        }).isPresent();
    }

    /**
     * Checks if this LITERAL_SWITCH token is part of a switch expression.
     *
     * @param ast the switch statement we are checking
     * @return true if part of a switch expression
     */
    private static boolean isSwitchExpression(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.EXPR
                || ast.getParent().getParent().getType() == TokenTypes.EXPR;
    }

    /**
     * Checks if the case contains null label.
     *
     * @param ast the switch statement we are checking
     * @param tokenType the tokenType of null literal
     * @return returnValue the ast of null label
     */
    private static DetailAST verifyNullCaseLabel(DetailAST ast, int tokenType) {
        DetailAST returnValue = null;
        if (ast.getFirstChild() != null) {
            final DetailAST value = ast.getFirstChild().getFirstChild();
            if (value != null && value.getType() == tokenType) {
                returnValue = ast;
            }
        }
        return returnValue;
    }
}
