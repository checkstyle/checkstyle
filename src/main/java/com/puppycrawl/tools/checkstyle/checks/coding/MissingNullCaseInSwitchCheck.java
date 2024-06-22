///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
 * Checks that switch statement or expression that uses reference type in case labels has
 * a {@code null} case label.
 * </p>
 * <p>
 * Rationale: switch statements and expressions in Java throw a
 * {@code NullPointerException} if the selector expression evaluates to {@code null}.
 * With the introduction of patterns in case label,
 * It is now possible to integrate a null check within the switch,
 * eliminating the risk of {@code NullPointerException} and simplifies the code
 * as there is no need for an external null check before entering the switch.
 * </p>
 * <p>
 * Limitations due to not being type-aware:
 * </p>
 * <ul>
 * <li>
 * This check does not differentiate between primitive types and
 * their boxed counterparts in case labels.
 * Therefore, any case label with an integer will not violate this check,
 * even if it is a reference type {@code Integer}.
 * </li>
 * <li>
 * This check will not violate case labels with idents even if
 * this is an identifier of Enums. We can't tell if the identifier
 * is an Enum or just a normal final variable.
 * </li>
 * </ul>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-15.html#jls-15.28">
 * Java Language Specification</a> for more information about switch statements and expressions.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code missing.switch.nullcase}
 * </li>
 * </ul>
 *
 * @since 10.18.0
 */

@StatelessCheck
public class MissingNullCaseInSwitchCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.switch.nullcase";

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
        if (!hasNullCaseLabel(ast) && (hasPatternCaseLabel(ast) || hasStringCaseLabel(ast))) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the given switch AST node has a case null label.
     *
     * @param detailAST the AST node representing {@code LITERAL_SWITCH}
     * @return true if the switch has {@code case null}, false otherwise
     */
    private static boolean hasNullCaseLabel(DetailAST detailAST) {
        return TokenUtil.findFirstTokenByPredicate(detailAST, ast -> {
            final DetailAST caseLabel = ast.findFirstToken(TokenTypes.LITERAL_CASE);
            DetailAST expr = null;
            if (caseLabel != null) {
                expr = caseLabel.findFirstToken(TokenTypes.EXPR);

            }
            return expr != null && expr.findFirstToken(TokenTypes.LITERAL_NULL) != null;
        }).isPresent();
    }

    /**
     * Checks if a switch contains a case label with a pattern variable definition
     * or record pattern definition.
     * In this case, if the switch selector evaluates to null,
     * a {@code NullPointerException} will be thrown.
     *
     * @param detailAST the AST node representing {@code LITERAL_SWITCH}
     * @return true if switch block contains a pattern case label
     */
    private static boolean hasPatternCaseLabel(DetailAST detailAST) {
        return TokenUtil.findFirstTokenByPredicate(detailAST, ast -> {
            final DetailAST caseLabel = ast.findFirstToken(TokenTypes.LITERAL_CASE);
            return caseLabel != null
                    && (caseLabel.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF) != null
                    || caseLabel.findFirstToken(TokenTypes.RECORD_PATTERN_DEF) != null
                    || caseLabel.findFirstToken(TokenTypes.PATTERN_DEF) != null);
        }).isPresent();
    }

    /**
     * Checks if a switch contains a string in its case labels.
     * In this case, if the switch selector evaluates to null,
     * a {@code NullPointerException} will be thrown.
     *
     * @param detailAST the AST node representing {@code LITERAL_SWITCH}
     * @return true if switch block contains a string case label
     */
    private static boolean hasStringCaseLabel(DetailAST detailAST) {
        return TokenUtil.findFirstTokenByPredicate(detailAST, ast -> {
            final DetailAST caseLabel = ast.findFirstToken(TokenTypes.LITERAL_CASE);

            return caseLabel != null && (hasStringLiteral(caseLabel)
                    || hasStringConcatenatedExpression(caseLabel));
        }).isPresent();
    }

    /**
     * Checks if the case label has a string literal.
     *
     * @param caseLabel the AST node representing {@code LITERAL_CASE}
     * @return true if the case label has a string literal
     */
    private static boolean hasStringLiteral(DetailAST caseLabel) {
        DetailAST expr = null;
            if (caseLabel != null) {
                expr = caseLabel.findFirstToken(TokenTypes.EXPR);
        }
        return expr != null && expr.getFirstChild().getType() == TokenTypes.STRING_LITERAL;
    }

    /**
     * Checks if the case label has a string in a concatenated expression.
     *
     * @param caseLabel the AST node representing {@code LITERAL_CASE}
     * @return true if the case label has a string in a concatenated expression
     */
    private static boolean hasStringConcatenatedExpression(DetailAST caseLabel) {
        DetailAST ast = caseLabel.getFirstChild().findFirstToken(TokenTypes.PLUS);
        boolean hasStringInConcatenatedExpression = false;
        while (ast != null) {
            if (ast.findFirstToken(TokenTypes.STRING_LITERAL) != null) {
                hasStringInConcatenatedExpression = true;
                break;
            }
            if (ast.getFirstChild() != null
                    && ast.getFirstChild().getType() == TokenTypes.PLUS) {
                ast = ast.getFirstChild();
            }
            else {
                ast = ast.getNextSibling();
            }
        }
        return hasStringInConcatenatedExpression;
    }
}
